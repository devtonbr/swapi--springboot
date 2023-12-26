package br.com.swapi.api.starwars.service;


import br.com.swapi.api.config.properties.SwApiProperties;
import br.com.swapi.api.model.exception.FilmException;
import br.com.swapi.api.model.starwars.service.FilmService;
import br.com.swapi.api.model.test.FilmServiceMock;
import br.com.swapi.api.model.vo.ReturnService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
class FilmsServiceTest {

    @InjectMocks
    private FilmService service;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;

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
    void listAllFilmsSuccess() throws FilmException {

        ReturnService returnService = new FilmServiceMock().listAllFilmsByService();

        when(webClient.method(any(HttpMethod.class))).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(responseMock);
        when(responseMock.onStatus(any(Predicate.class), any(Function.class))).thenReturn(responseMock);
        when(responseMock.bodyToMono(eq(ReturnService.class))).thenReturn(Mono.just(returnService));
        ReturnService list = service.listAllFilmsByService();
        Assertions.assertNotNull(list);
    }

    @Test
    void listFilms_FilmException_HttpStatus400() {

        when(webClient.method(any(HttpMethod.class))).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(swapiResponseSpecMock);
        when(swapiResponseSpecMock.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);
        when(swapiResponseSpecMock.getBodyContent()).thenReturn("{json}");
        when(swapiResponseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();
        FilmException filmException = assertThrows(FilmException.class,
                () -> service.listAllFilmsByService(),
                "call fails with Internal Server Error");
        assertEquals(400, filmException.getHttpStatus().value());
    }

    @Test
    void listFilms_FilmException_HttpStatus503() {


        when(webClient.method(any(HttpMethod.class))).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(swapiResponseSpecMock);
        when(swapiResponseSpecMock.getStatus()).thenReturn(HttpStatus.SERVICE_UNAVAILABLE);
        when(swapiResponseSpecMock.getBodyContent()).thenReturn("{json}");
        when(swapiResponseSpecMock.onStatus(any(), any())).thenCallRealMethod();
        FilmException filmException = assertThrows(FilmException.class,
                () -> service.listAllFilmsByService(),
                "call fails with Internal Server Error");
        assertEquals(503, filmException.getHttpStatus().value());
    }

    @Test
    void test201Success(){
        when(webClient.method(any(HttpMethod.class))).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(anyString())).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(responseMock);
        when(responseMock.onStatus(any(Predicate.class), any(Function.class))).thenReturn(responseMock);
        when(responseMock.toEntity(eq(String.class))).thenReturn(Mono.just(ResponseEntity.status(201).body("ok")));
        String list = service.simulate201();
        Assertions.assertNotNull(list);
    }


}
