package br.com.swapi.api.datasource.api.dto;

import java.util.Arrays;
import java.util.List;


public class ReturnServiceDtoMock {

	/**
	 * Metodo que retorna dados simulados de uma requisição da api
	 * @return Lista de Filmes Mockados
	 */
	public ReturnServiceDto listAllFilms() {

		List<FilmDto> films = Arrays.asList(film(1), film(2));

		return new ReturnServiceDto("2", null, null, films);
	}

	private FilmDto film(Integer id) {
		return new FilmDto(
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
				"http://localhost/api/films/" + id);
	}

}
