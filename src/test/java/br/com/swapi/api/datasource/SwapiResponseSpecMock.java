package br.com.swapi.api.datasource;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class SwapiResponseSpecMock implements WebClient.ResponseSpec {

    public abstract HttpStatus getStatus();

    public abstract String getBodyContent();

    @Override
    public WebClient.ResponseSpec onStatus(Predicate<HttpStatusCode> statusPredicate, Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {
        if (statusPredicate.test(this.getStatus())) {
            exceptionFunction.apply(ClientResponse.create(this.getStatus()).body(getBodyContent()).build()).block();
        }
        return this;
    }






}
