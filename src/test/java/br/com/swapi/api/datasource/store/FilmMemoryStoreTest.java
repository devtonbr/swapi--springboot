package br.com.swapi.api.datasource.store;

import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmMemoryStoreTest {

    private FilmMemoryStore store;

    private ReturnService films;

    @BeforeEach
    void init() {
        films = new FilmMock().listAllFilms();
        store = new FilmMemoryStore();
        store.load(films);
    }

    @Test
    void emptyStore() {
        FilmMemoryStore empty = new FilmMemoryStore();

        assertNull(empty.listAllFilms());
        assertNull(empty.getById(1));

        assertNull(empty.updateDescription(films.getResults().get(0), input("nova")));
        assertNull(empty.listAllFilms());
    }

    @Test
    void listAllFilms() {
        assertSame(films, store.listAllFilms());
    }

    @Test
    void getById() {
        assertEquals("Director test 1", store.getById(1).getDirector());
        assertNull(store.getById(99));
    }

    @Test
    void updateDescriptionCreatesNewVersion() {
        Film original = store.getById(1);

        Film returned = store.updateDescription(original, input("nova"));

        Film updated = store.getById(1);
        assertSame(updated, returned);
        assertNotSame(original, updated);
        assertEquals("nova", updated.getOpeningCrawl());
        assertEquals(2, updated.getVersion());
        assertEquals("Director test 1", updated.getDirector());

        // o objeto e a lista anteriores não são alterados
        assertEquals("Description test 1", original.getOpeningCrawl());
        assertEquals(1, original.getVersion());
        assertNotSame(films, store.listAllFilms());
        assertEquals("3", store.listAllFilms().getCount());
        assertEquals(3, store.listAllFilms().getResults().size());
    }

    @Test
    void updateWithSameDescriptionKeepsVersion() {
        Film original = store.getById(1);

        Film returned = store.updateDescription(original, input("Description test 1"));

        assertSame(original, returned);
        assertEquals(1, store.getById(1).getVersion());
        assertSame(films, store.listAllFilms());
    }

    @Test
    void repeatedUpdateIncrementsOnlyOnce() {
        Film original = store.getById(1);

        assertEquals(2, store.updateDescription(original, input("nova")).getVersion());
        assertEquals(2, store.updateDescription(original, input("nova")).getVersion());
        assertEquals(3, store.updateDescription(original, input("outra")).getVersion());
    }

    @Test
    void updateUnknownFilmReturnsNull() {
        Film unknown = new Film("", 99, "", "", "", "", null, null, null, null, null, "", "", "");

        assertNull(store.updateDescription(unknown, input("nova")));
        assertSame(films, store.listAllFilms());
    }

    @Test
    void concurrentUpdatesAreNotLost() throws Exception {
        int threads = 8;
        int updatesPerThread = 50;
        Film film = store.getById(1);
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);

        for (int t = 0; t < threads; t++) {
            executor.submit(() -> {
                try {
                    start.await();
                    for (int i = 0; i < updatesPerThread; i++) {
                        store.updateDescription(film, input(Thread.currentThread().getName() + "-" + i));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }
        start.countDown();
        assertTrue(done.await(30, TimeUnit.SECONDS));
        executor.shutdown();

        assertEquals(1 + threads * updatesPerThread, store.getById(1).getVersion());
    }

    private InputParameter input(String description) {
        InputParameter input = new InputParameter();
        input.setDescription(description);
        return input;
    }

}
