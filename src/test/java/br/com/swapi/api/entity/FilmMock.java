package br.com.swapi.api.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FilmMock {
	
	/**
	 * Metodo que retorna dados simulados de uma requisição do serviço
	 * @return Lista de Filmes Mockados
	 */
	public ReturnService listAllFilms() {
		
		List<Film> films = new ArrayList<>();
		
		films.add(new Film(
			"", 
			1, 
			"Description test 1", 
			"Director test 1", 
			"Producer Test 1", 
			"2021-02-10",
			Arrays.asList("characters 1","characters 2"), 
			Arrays.asList("planets 1","planets 2"), 
			Arrays.asList("starships 1","starships 2"), 
			Arrays.asList("vehicles 1","vehicles 2"), 
			Arrays.asList("species 1","species 2"), 
			"Created Test 1", 
			"Edited test 2",
			""));

		films.add(new Film(
				"", 
				2, 
				"Description test 2", 
				"Director test 2", 
				"Producer Test 2", 
				"2021-02-10",
				Arrays.asList("characters 1","characters 2"), 
				Arrays.asList("planets 1","planets 2"), 
				Arrays.asList("starships 1","starships 2"), 
				Arrays.asList("vehicles 1","vehicles 2"), 
				Arrays.asList("species 1","species 2"), 
				"Created Test 1", 
				"Edited test 2",
				""));
		
		films.add(new Film(
				"", 
				2, 
				"Description test 3", 
				"Director test 3", 
				"Producer Test 3", 
				"2021-02-10",
				Arrays.asList("characters 1","characters 2"), 
				Arrays.asList("planets 1","planets 2"), 
				Arrays.asList("starships 1","starships 2"), 
				Arrays.asList("vehicles 1","vehicles 2"), 
				Arrays.asList("species 1","species 2"), 
				"Created Test 1", 
				"Edited test 2",
				""));
		
		return new ReturnService("3", null, null, films);
		
		
		
	}

}
