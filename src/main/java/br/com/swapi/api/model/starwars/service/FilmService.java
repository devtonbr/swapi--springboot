package br.com.swapi.api.model.starwars.service;

import java.io.IOException;

import br.com.swapi.api.config.properties.SwApiProperties;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.swapi.api.model.constants.Constants;
import br.com.swapi.api.model.exception.FilmException;
import br.com.swapi.api.model.vo.ReturnService;

@Component
public class FilmService implements Constants{
	
	
	 @Autowired
	 private SwApiProperties swApiProperties;

	/**
	 * Serviço que retorna a lista de filmes
	 * @return
	 * @throws FilmException 
	 */
	@CircuitBreaker(name = "listAllFilmsByService")
	public ReturnService listAllFilmsByService() throws FilmException{
		
		 try {
			HttpClient client = HttpClientBuilder.create().build();   
			HttpResponse resp = client.execute(new HttpGet(swApiProperties.getEndpoint()));
		    int statusCode = resp.getStatusLine().getStatusCode();
		    if (statusCode != 200) {
		    	throw new FilmException( new StringBuilder("Erro ao listar os filmes: HTTP STATUS: [").append(statusCode).append(']').toString());
		    }			 
		    ObjectMapper mapper = new ObjectMapper();		    
		    return mapper.readValue(EntityUtils.toString(resp.getEntity()),ReturnService.class);
			
		} catch (IOException e) {
			 throw new FilmException("Erro ao invocar o serviço de listar Filmes",e);
		}
		


	}

}
