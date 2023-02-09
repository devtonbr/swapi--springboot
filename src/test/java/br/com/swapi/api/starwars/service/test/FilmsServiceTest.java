package br.com.swapi.api.starwars.service.test;



import br.com.swapi.api.model.exception.FilmException;
import br.com.swapi.api.model.starwars.service.FilmService;
import br.com.swapi.api.model.vo.ReturnService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class FilmsServiceTest {
	
	private static final FilmService service;
	
	static { 
		service = new FilmService();
	}
	
	@Test
	public void listAllFilmsSuccess() {
		
		ReturnService list;
		
		try {
			
			list = service.listAllFilmsByService();
			Assertions.assertNotNull(list);
		} catch (FilmException e) {
			Assertions.fail(e.getMessage());
		}
				
	}

}
