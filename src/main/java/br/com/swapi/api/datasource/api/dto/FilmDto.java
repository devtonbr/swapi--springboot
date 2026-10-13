package br.com.swapi.api.datasource.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Dados do filme retornados pela api do StarWars
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FilmDto(
		String title,
		@JsonProperty("episode_id") Integer episodeId,
		@JsonProperty("opening_crawl") String openingCrawl,
		String director,
		String producer,
		@JsonProperty("release_date") String releaseDate,
		List<String> characters,
		List<String> planets,
		List<String> starships,
		List<String> vehicles,
		List<String> species,
		String created,
		String edited,
		String url) {
}
