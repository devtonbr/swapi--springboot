package br.com.swapi.api.model.starwars.service;

import br.com.swapi.api.config.properties.SwApiProperties;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import br.com.swapi.api.model.constants.Constants;
import br.com.swapi.api.model.exception.FilmException;
import br.com.swapi.api.model.vo.ReturnService;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;

@Component
public class FilmService implements Constants {


    @Autowired
    private WebClient webClient;

    @Autowired
    private SwApiProperties swApiProperties;

    /**
     * Serviço que retorna a lista de filmes
     *
     * @return
     * @throws FilmException
     */
    @CircuitBreaker(name = "listAllFilmsByService")
    public ReturnService listAllFilmsByService() throws FilmException {
        Mono<ReturnService> responseService = this.webClient
                .method(HttpMethod.GET)
                .uri(swApiProperties.getEndpoint())
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(body -> Mono.error(
                                new FilmException(clientResponse.statusCode(), body)
                        ))
                )
                .bodyToMono(ReturnService.class);
        return responseService.block();
    }


}
