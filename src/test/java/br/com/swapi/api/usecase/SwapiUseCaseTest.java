package br.com.swapi.api.usecase;

import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.entity.ReturnService;
import br.com.swapi.api.exception.FilmException;
import br.com.swapi.api.repository.FilmRepository;
import br.com.swapi.api.repository.SwapiRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SwapiUseCaseTest {

    @InjectMocks
    private SwapiUseCase useCase;

    @Mock
    private SwapiRepository swapiRepository;

    @Mock
    private FilmRepository filmRepository;

    @Test
    void loadAllSuccess() {
        ReturnService films = new FilmMock().listAllFilms();
        when(swapiRepository.loadAll()).thenReturn(films);

        assertSame(films, useCase.loadAll());
        InOrder order = inOrder(swapiRepository, filmRepository);
        order.verify(swapiRepository).loadAll();
        order.verify(filmRepository).saveAll(films);
    }

    @Test
    void loadAllFail() {
        when(swapiRepository.loadAll()).thenThrow(new FilmException(HttpStatus.SERVICE_UNAVAILABLE, "{json}"));

        FilmException exception = assertThrows(FilmException.class, () -> useCase.loadAll());
        assertEquals(503, exception.getHttpStatus().value());
        verify(filmRepository, never()).saveAll(any());
    }

}
