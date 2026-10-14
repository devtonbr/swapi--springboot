package br.com.swapi.api.datasource.store.dto;

import java.util.List;

/**
 * Lista de filmes guardada em memória
 */
public record ReturnServiceStoreDto(
		String count,
		String next,
		String previous,
		List<FilmStoreDto> results) {
}
