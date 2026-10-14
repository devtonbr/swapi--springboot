package br.com.swapi.api.usecase;

import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;
import br.com.swapi.api.exception.MemoryStoreException;
import br.com.swapi.api.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.swapi.api.constants.Constants.NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmUseCaseTest {

    @InjectMocks
    private FilmUseCase useCase;

    @Mock
    private FilmRepository filmRepository;

    private final ReturnService films = new FilmMock().listAllFilms();

    @Test
    void listAllSuccess() {
        when(filmRepository.listAll()).thenReturn(films);

        assertSame(films, useCase.listAll());
    }

    @Test
    void findByIdSuccess() {
        Film film = films.results().get(0);
        when(filmRepository.findById(1)).thenReturn(film);

        assertSame(film, useCase.findById(1));
    }

    @Test
    void findByIdFail() {
        when(filmRepository.findById(99)).thenThrow(new MemoryStoreException(NOT_FOUND_MESSAGE));

        assertThrows(MemoryStoreException.class, () -> useCase.findById(99));
    }

    @Test
    void updateDescriptionSuccess() {
        Film film = films.results().get(0);
        InputParameter input = new InputParameter("nova descriçao");
        when(filmRepository.findById(1)).thenReturn(film);
        when(filmRepository.updateDescription(film, input)).thenReturn(film.updateDescription(input.description()));

        Film ret = useCase.updateDescription(1, input);

        assertEquals(2, ret.version());
        verify(filmRepository).updateDescription(film, input);
    }

    @Test
    void updateDescriptionFilmNotFound() {
        when(filmRepository.findById(99)).thenThrow(new MemoryStoreException(NOT_FOUND_MESSAGE));

        assertThrows(MemoryStoreException.class, () -> useCase.updateDescription(99, new InputParameter(null)));
        verify(filmRepository, never()).updateDescription(any(), any());
    }

}
