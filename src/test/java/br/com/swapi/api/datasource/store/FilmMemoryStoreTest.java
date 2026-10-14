package br.com.swapi.api.datasource.store;

import br.com.swapi.api.datasource.store.dto.FilmStoreDto;
import br.com.swapi.api.datasource.store.dto.ReturnServiceStoreDto;
import br.com.swapi.api.datasource.store.dto.ReturnServiceStoreDtoMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmMemoryStoreTest {

    private FilmMemoryStore store;

    private ReturnServiceStoreDto films;

    @BeforeEach
    void init() {
        films = new ReturnServiceStoreDtoMock().listAllFilms();
        store = new FilmMemoryStore();
        store.load(films);
    }

    @Test
    void emptyStore() {
        FilmMemoryStore empty = new FilmMemoryStore();

        assertNull(empty.listAllFilms());
        assertNull(empty.getById(1));

        assertNull(empty.update(1, withDescription("nova")));
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
    void updateReplacesTheFilm() {
        FilmStoreDto original = store.getById(1);

        FilmStoreDto returned = store.update(1, withDescription("nova"));

        FilmStoreDto updated = store.getById(1);
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
    void updateKeepsOtherFilms() {
        FilmStoreDto other = store.getById(2);

        store.update(1, withDescription("nova"));

        assertSame(other, store.getById(2));
    }

    @Test
    void updateWithoutChangeKeepsTheList() {
        FilmStoreDto original = store.getById(1);

        FilmStoreDto returned = store.update(1, UnaryOperator.identity());

        assertSame(original, returned);
        assertSame(films, store.listAllFilms());
    }

    @Test
    void updateUnknownFilmReturnsNull() {
        assertNull(store.update(99, withDescription("nova")));
        assertSame(films, store.listAllFilms());
    }

    @Test
    void concurrentUpdatesAreNotLost() throws Exception {
        int threads = 8;
        int updatesPerThread = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);

        for (int t = 0; t < threads; t++) {
            executor.submit(() -> {
                try {
                    start.await();
                    for (int i = 0; i < updatesPerThread; i++) {
                        store.update(1, withDescription(Thread.currentThread().getName() + "-" + i));
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

    /**
     * Alteração que troca a descrição e incrementa a versão
     */
    private UnaryOperator<FilmStoreDto> withDescription(String description) {
        return film -> new FilmStoreDto(film.title(), film.id(), description, film.director(), film.producer(),
                film.releaseDate(), film.characters(), film.planets(), film.starships(), film.vehicles(),
                film.species(), film.created(), film.edited(), film.url(), film.version() + 1);
    }

}
