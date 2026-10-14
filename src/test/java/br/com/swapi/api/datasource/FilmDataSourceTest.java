package br.com.swapi.api.datasource;

import br.com.swapi.api.datasource.store.FilmMemoryStore;
import br.com.swapi.api.datasource.store.dto.FilmStoreDto;
import br.com.swapi.api.datasource.store.dto.ReturnServiceStoreDto;
import br.com.swapi.api.datasource.store.dto.ReturnServiceStoreDtoMock;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmDataSourceTest {

    @InjectMocks
    private FilmDataSource dataSource;

    @Mock
    private FilmMemoryStore store;

    @Captor
    private ArgumentCaptor<ReturnServiceStoreDto> savedFilms;

    @Captor
    private ArgumentCaptor<UnaryOperator<FilmStoreDto>> change;

    private final ReturnServiceStoreDto stored = new ReturnServiceStoreDtoMock().listAllFilms();

    private final Film film = new FilmMock().listAllFilms().results().get(0);

    @Test
    void saveAllSuccess() {
        dataSource.saveAll(new FilmMock().listAllFilms());

        verify(store).load(savedFilms.capture());
        ReturnServiceStoreDto saved = savedFilms.getValue();
        assertEquals("3", saved.count());
        assertEquals(3, saved.results().size());
        assertEquals(1, saved.results().get(0).id());
        assertEquals("Description test 1", saved.results().get(0).openingCrawl());
        assertEquals(1, saved.results().get(0).version());
    }

    @Test
    void listAllSuccess() {
        when(store.listAllFilms()).thenReturn(stored);

        ReturnService films = dataSource.listAll();

        assertEquals("3", films.count());
        assertEquals(3, films.results().size());
        assertEquals(1, films.results().get(0).id());
        assertEquals("Title test 1", films.results().get(0).title());
        assertEquals(1, films.results().get(0).version());
    }

    @Test
    void listAllWithoutFilmsLoaded() {
        when(store.listAllFilms()).thenReturn(null);

        assertNull(dataSource.listAll());
    }

    @Test
    void findByIdSuccess() {
        when(store.getById(1)).thenReturn(stored.results().get(0));

        Film found = dataSource.findById(1);

        assertEquals(1, found.id());
        assertEquals("Director test 1", found.director());
        assertEquals(1, found.version());
    }

    @Test
    void findByIdNotFound() {
        when(store.getById(99)).thenReturn(null);

        assertNull(dataSource.findById(99));
    }

    @Test
    void updateDescriptionSuccess() {
        // o store mockado aplica a alteração recebida sobre o filme guardado
        FilmStoreDto current = stored.results().get(0);
        when(store.update(eq(1), any())).thenAnswer(invocation ->
                invocation.<UnaryOperator<FilmStoreDto>>getArgument(1).apply(current));

        Film updated = dataSource.updateDescription(film, new InputParameter("nova descriçao"));

        assertEquals(1, updated.id());
        assertEquals("nova descriçao", updated.openingCrawl());
        assertEquals(2, updated.version());
        assertEquals("Director test 1", updated.director());
    }

    @Test
    void updateWithSameDescriptionDoesNotChangeTheStoredFilm() {
        FilmStoreDto current = stored.results().get(0);

        dataSource.updateDescription(film, new InputParameter("Description test 1"));

        verify(store).update(eq(1), change.capture());
        assertEquals(current, change.getValue().apply(current));
    }

    @Test
    void updateDescriptionFilmNotFound() {
        Film unknown = new Film("", 99, "", "", "", "", null, null, null, null, null, "", "", "");
        when(store.update(eq(99), any())).thenReturn(null);

        assertNull(dataSource.updateDescription(unknown, new InputParameter("nova descriçao")));
    }

}
