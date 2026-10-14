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

        assertNull(empty.updateDescription(films.results().get(0), input("nova")));
        assertNull(empty.listAllFilms());
    }

    @Test
    void listAllFilms() {
        assertSame(films, store.listAllFilms());
    }

    @Test
    void getById() {
        assertEquals("Director test 1", store.getById(1).director());
        assertNull(store.getById(99));
    }

    @Test
    void updateDescriptionCreatesNewVersion() {
        Film original = store.getById(1);

        Film returned = store.updateDescription(original, input("nova"));

        Film updated = store.getById(1);
        assertSame(updated, returned);
        assertNotSame(original, updated);
        assertEquals("nova", updated.openingCrawl());
        assertEquals(2, updated.version());
        assertEquals("Director test 1", updated.director());

        // o objeto e a lista anteriores não são alterados
        assertEquals("Description test 1", original.openingCrawl());
        assertEquals(1, original.version());
        assertNotSame(films, store.listAllFilms());
        assertEquals("3", store.listAllFilms().count());
        assertEquals(3, store.listAllFilms().results().size());
    }

    @Test
    void updateWithSameDescriptionKeepsVersion() {
        Film original = store.getById(1);

        Film returned = store.updateDescription(original, input("Description test 1"));

        assertSame(original, returned);
        assertEquals(1, store.getById(1).version());
        assertSame(films, store.listAllFilms());
    }

    @Test
    void repeatedUpdateIncrementsOnlyOnce() {
        Film original = store.getById(1);

        assertEquals(2, store.updateDescription(original, input("nova")).version());
        assertEquals(2, store.updateDescription(original, input("nova")).version());
        assertEquals(3, store.updateDescription(original, input("outra")).version());
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

        assertEquals(1 + threads * updatesPerThread, store.getById(1).version());
    }

    private InputParameter input(String description) {
        InputParameter input = new InputParameter(description);
        return input;
    }

}
