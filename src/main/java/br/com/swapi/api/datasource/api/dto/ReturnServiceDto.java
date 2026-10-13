package br.com.swapi.api.datasource.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Retorno da listagem de filmes da api do StarWars
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReturnServiceDto(
		String count,
		String next,
		String previous,
		List<FilmDto> results) {
}
