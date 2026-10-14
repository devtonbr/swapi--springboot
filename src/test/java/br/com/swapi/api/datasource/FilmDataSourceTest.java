package br.com.swapi.api.datasource;

import br.com.swapi.api.datasource.store.FilmMemoryStore;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmDataSourceTest {

    @InjectMocks
    private FilmDataSource dataSource;

    @Mock
    private FilmMemoryStore store;

    private final ReturnService films = new FilmMock().listAllFilms();

    @Test
    void saveAllSuccess() {
        dataSource.saveAll(films);

        verify(store).load(films);
    }

    @Test
    void listAllSuccess() {
        when(store.listAllFilms()).thenReturn(films);

        assertSame(films, dataSource.listAll());
    }

    @Test
    void listAllWithoutFilmsLoaded() {
        when(store.listAllFilms()).thenReturn(null);

        assertNull(dataSource.listAll());
    }

    @Test
    void findByIdSuccess() {
        Film film = films.results().get(0);
        when(store.getById(1)).thenReturn(film);

        assertSame(film, dataSource.findById(1));
    }

    @Test
    void findByIdNotFound() {
        when(store.getById(99)).thenReturn(null);

        assertNull(dataSource.findById(99));
    }

    @Test
    void updateDescriptionSuccess() {
        Film film = films.results().get(0);
        InputParameter input = new InputParameter("nova descriçao");
        Film updated = film.updateDescription(input.description());
        when(store.updateDescription(film, input)).thenReturn(updated);

        assertSame(updated, dataSource.updateDescription(film, input));
        verify(store).updateDescription(film, input);
    }

    @Test
    void updateDescriptionFilmNotFound() {
        Film unknown = new Film("", 99, "", "", "", "", null, null, null, null, null, "", "", "");
        InputParameter input = new InputParameter("nova descriçao");
        when(store.updateDescription(unknown, input)).thenReturn(null);

        assertNull(dataSource.updateDescription(unknown, input));
    }

}
