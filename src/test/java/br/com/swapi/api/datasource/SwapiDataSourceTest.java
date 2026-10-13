package br.com.swapi.api.datasource;

import br.com.swapi.api.config.properties.SwApiProperties;
import br.com.swapi.api.datasource.api.dto.ReturnServiceDto;
import br.com.swapi.api.datasource.api.dto.ReturnServiceDtoMock;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.ReturnService;
import br.com.swapi.api.exception.FilmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SwapiDataSourceTest {

    @InjectMocks
    private SwapiDataSource dataSource;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;

    @Mock
    private WebClient.RequestBodySpec requestBodyMock;

    @Mock
    private WebClient.ResponseSpec responseMock;

    @Mock
    private SwApiProperties swapiProperties;

    @Mock
    private SwapiResponseSpecMock swapiResponseSpecMock;

    @BeforeEach
    void init() {

        when(swapiProperties.getEndpoint()).thenReturn("http://localhost/api");
    }


    @Test
    void loadAllSuccess() throws FilmException {

        ReturnServiceDto returnService = new ReturnServiceDtoMock().listAllFilms();

        when(webClient.method(any(HttpMethod.class))).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(responseMock);
        when(responseMock.onStatus(any(Predicate.class), any(Function.class))).thenReturn(responseMock);
        when(responseMock.bodyToMono(eq(ReturnServiceDto.class))).thenReturn(Mono.just(returnService));

        ReturnService ret = dataSource.loadAll();

        assertEquals("2", ret.getCount());
        assertEquals(2, ret.getResults().size());
        Film film = ret.getResults().get(0);
        assertEquals(1, film.getId());
        assertEquals("Title test 1", film.getTitle());
        assertEquals("Description test 1", film.getOpeningCrawl());
        assertEquals("2021-02-10", film.getReleaseDate());
        assertEquals(1, film.getVersion());
    }

    @Test
    void loadAll_FilmException_HttpStatus400() {

        when(webClient.method(any(HttpMethod.class))).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(swapiResponseSpecMock);
        when(swapiResponseSpecMock.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);
        when(swapiResponseSpecMock.getBodyContent()).thenReturn("{json}");
        when(swapiResponseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();
        FilmException filmException = assertThrows(FilmException.class,
                () -> dataSource.loadAll(),
                "call fails with Internal Server Error");
        assertEquals(400, filmException.getHttpStatus().value());
    }

    @Test
    void loadAll_FilmException_HttpStatus503() {

        when(webClient.method(any(HttpMethod.class))).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(swapiResponseSpecMock);
        when(swapiResponseSpecMock.getStatus()).thenReturn(HttpStatus.SERVICE_UNAVAILABLE);
        when(swapiResponseSpecMock.getBodyContent()).thenReturn("{json}");
        when(swapiResponseSpecMock.onStatus(any(), any())).thenCallRealMethod();
        FilmException filmException = assertThrows(FilmException.class,
                () -> dataSource.loadAll(),
                "call fails with Internal Server Error");
        assertEquals(503, filmException.getHttpStatus().value());
    }


}
