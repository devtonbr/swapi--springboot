package br.com.swapi.api.datasource.store.dto;

import java.util.List;

/**
 * Dados do filme guardados em memória
 */
public record FilmStoreDto(
		String title,
		Integer id,
		String openingCrawl,
		String director,
		String producer,
		String releaseDate,
		List<String> characters,
		List<String> planets,
		List<String> starships,
		List<String> vehicles,
		List<String> species,
		String created,
		String edited,
		String url,
		Integer version) {
}
