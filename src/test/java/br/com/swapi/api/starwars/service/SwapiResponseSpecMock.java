package br.com.swapi.api.starwars.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class SwapiResponseSpecMock implements WebClient.ResponseSpec {

    public abstract HttpStatus getStatus();

    @Override
    public WebClient.ResponseSpec onStatus(Predicate<HttpStatus> statusPredicate, Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {
        if (statusPredicate.test(this.getStatus())) {
            exceptionFunction.apply(ClientResponse.create(this.getStatus()).build()).block();
        }
        return this;
    }


}
