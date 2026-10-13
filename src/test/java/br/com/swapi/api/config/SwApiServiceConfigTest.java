package br.com.swapi.api.config;

import br.com.swapi.api.exception.FilmException;
import br.com.swapi.api.usecase.SwapiUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.net.ConnectException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SwApiServiceConfigTest {

    @InjectMocks
    private SwApiServiceConfig config;

    @Mock
    private SwapiUseCase swapiUseCase;

    @Test
    void loadFilmsOnStartup() {
        config.afterSingletonsInstantiated();

        verify(swapiUseCase).loadAll();
    }

    @Test
    void startupFailsWhenApiReturnsError() {
        FilmException cause = new FilmException(HttpStatus.SERVICE_UNAVAILABLE, "{json}");
        when(swapiUseCase.loadAll()).thenThrow(cause);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> config.afterSingletonsInstantiated());

        assertEquals("Erro ao listar os filmes[503 SERVICE_UNAVAILABLE]", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    void startupFailsWhenApiIsUnreachable() {
        WebClientRequestException cause = new WebClientRequestException(new ConnectException("recusada"),
                HttpMethod.GET, URI.create("http://localhost/api/films/"), HttpHeaders.EMPTY);
        when(swapiUseCase.loadAll()).thenThrow(cause);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> config.afterSingletonsInstantiated());

        assertEquals("Erro ao conectar na api de filmes[http://localhost/api/films/]", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

}
