package br.com.swapi.api.datasource.store.dto;

import java.util.Arrays;
import java.util.List;

public class ReturnServiceStoreDtoMock {

	/**
	 * Metodo que retorna dados simulados de filmes guardados em memória
	 * @return Lista de Filmes Mockados
	 */
	public ReturnServiceStoreDto listAllFilms() {

		List<FilmStoreDto> films = Arrays.asList(film(1), film(2), film(3));

		return new ReturnServiceStoreDto("3", null, null, films);
	}

	private FilmStoreDto film(Integer id) {
		return new FilmStoreDto(
				"Title test " + id,
				id,
				"Description test " + id,
				"Director test " + id,
				"Producer Test " + id,
				"2021-02-10",
				Arrays.asList("characters 1","characters 2"),
				Arrays.asList("planets 1","planets 2"),
				Arrays.asList("starships 1","starships 2"),
				Arrays.asList("vehicles 1","vehicles 2"),
				Arrays.asList("species 1","species 2"),
				"Created Test " + id,
				"Edited test " + id,
				"http://localhost/api/films/" + id,
				1);
	}

}
