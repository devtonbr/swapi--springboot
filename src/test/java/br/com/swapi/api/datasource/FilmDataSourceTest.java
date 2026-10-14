package br.com.swapi.api.datasource;

import br.com.swapi.api.datasource.aspects.ReturnDaoElement;
import br.com.swapi.api.datasource.store.FilmMemoryStore;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;
import br.com.swapi.api.exception.DaoException;
import br.com.swapi.api.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmDataSourceTest {

    private FilmRepository repository;

    private FilmMemoryStore store;

    private ReturnService films;

    @BeforeEach
    void init() {
        films = new FilmMock().listAllFilms();
        store = new FilmMemoryStore();
        store.load(films);

        FilmDataSource dataSource = new FilmDataSource();
        ReflectionTestUtils.setField(dataSource, "store", store);

        AspectJProxyFactory factory = new AspectJProxyFactory(dataSource);
        factory.addAspect(new ReturnDaoElement());
        repository = factory.getProxy();
    }

    @Test
    void saveAllSuccess() {
        ReturnService loaded = new FilmMock().listAllFilms();

        repository.saveAll(loaded);

        assertSame(loaded, store.listAllFilms());
        assertSame(loaded, repository.listAll());
    }

    @Test
    void listAllSuccess() {
        assertSame(films, repository.listAll());
    }

    @Test
    void listAllFail() {
        repository.saveAll(null);

        assertThrows(DaoException.class, () -> repository.listAll());
    }

    @Test
    void findByIdSuccess() {
        Film ret = repository.findById(1);

        assertEquals(1, ret.id());
        assertEquals("Director test 1", ret.director());
    }

    @Test
    void findByIdWithoutFilmsLoaded() {
        repository.saveAll(null);

        assertThrows(DaoException.class, () -> repository.findById(1));
    }

    @Test
    void findByIdFail() {
        assertThrows(DaoException.class, () -> repository.findById(99));
    }

    @Test
    void updateDescriptionSuccess() {
        InputParameter input = new InputParameter("nova descriçao");

        Film ret = repository.updateDescription(repository.findById(1), input);

        assertEquals(2, ret.version());
        Film updated = repository.findById(1);
        assertEquals(1, updated.id());
        assertEquals("nova descriçao", updated.openingCrawl());
        assertEquals(2, updated.version());
    }

    @Test
    void updateDescriptionKeepsOtherFilms() {
        InputParameter input = new InputParameter("nova descriçao");

        repository.updateDescription(repository.findById(1), input);

        Film other = repository.findById(2);
        assertEquals("Description test 2", other.openingCrawl());
        assertEquals(1, other.version());
    }

    @Test
    void updateWithSameDescriptionKeepsVersion() {
        InputParameter input = new InputParameter("Description test 1");

        Film ret = repository.updateDescription(repository.findById(1), input);

        assertEquals(1, ret.version());
        assertEquals(1, repository.findById(1).version());
    }

    @Test
    void updateDescriptionFilmNotFound() {
        Film unknown = new Film("", 99, "", "", "", "", null, null, null, null, null, "", "", "");

        assertThrows(DaoException.class, () -> repository.updateDescription(unknown, new InputParameter(null)));
    }

}
