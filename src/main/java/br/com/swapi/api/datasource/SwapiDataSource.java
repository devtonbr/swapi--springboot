package br.com.swapi.api.datasource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.swapi.api.config.properties.SwApiProperties;
import br.com.swapi.api.datasource.api.dto.ReturnServiceDto;
import br.com.swapi.api.datasource.api.mapper.SwapiApiMapper;
import br.com.swapi.api.entity.ReturnService;
import br.com.swapi.api.exception.FilmException;
import br.com.swapi.api.repository.SwapiRepository;
import reactor.core.publisher.Mono;

/**
 * Classe DataSource - Swapi
 *
 * Acessa a api do StarWars
 *
 * @author Clayton Morais de Oliveira
 *
 */
@Component
public class SwapiDataSource implements SwapiRepository {

	private final WebClient webClient;

	private final SwApiProperties swApiProperties;

	private final SwapiApiMapper swapiApiMapper;

	public SwapiDataSource(WebClient webClient, SwApiProperties swApiProperties, SwapiApiMapper swapiApiMapper) {
		this.webClient = webClient;
		this.swApiProperties = swApiProperties;
		this.swapiApiMapper = swapiApiMapper;
	}

	/**
	 * Carrega a lista de filmes a partir da api do StarWars
	 *
	 * @return Lista do Objeto Filme
	 * @throws FilmException
	 */
	@Override
	public ReturnService loadAll() throws FilmException {
		var responseService = this.webClient
				.method(HttpMethod.GET)
				.uri(swApiProperties.getEndpoint())
				.retrieve()
				.onStatus(statusCode -> statusCode.isError(), clientResponse ->
						clientResponse.bodyToMono(String.class).flatMap(body -> Mono.error(
								new FilmException(clientResponse.statusCode(), body)
						))
				)
				.bodyToMono(ReturnServiceDto.class);
		return swapiApiMapper.map(responseService.block());
	}

}
