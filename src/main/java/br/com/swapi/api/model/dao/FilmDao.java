package br.com.swapi.api.model.dao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import br.com.swapi.api.model.constants.Constants;
import br.com.swapi.api.model.dao.aspects.ReturnNullObject;
import br.com.swapi.api.model.dao.list.ReturnServiceHelper;
import br.com.swapi.api.model.vo.Film;
import br.com.swapi.api.model.vo.InputParameter;
import br.com.swapi.api.model.vo.ObjectRsp;
import br.com.swapi.api.model.vo.ReturnService;

/**
 * Classe DAO - Filme
 * 
 * @author Clayton Morais de Oliveira
 *
 */
@Component
public class FilmDao implements Constants  {
	
	

	/**
	 * Retorna a lista de todos os Filmes
	 * 
	 * @param request Objeto HttpServletRequest
	 * 
	 * @return Lista do Objeto Filme
	 */
	@ReturnNullObject
    public ReturnService listAll(final HttpServletRequest request) {
    	  return new ReturnServiceHelper(request).listAllFilms();    	  
	}


	/**
	 * Retorna os detalhes do filme
	 * 
	 * @param request Objeto HttpServletRequest
	 * 
	 * @param id Id A ser Consultado
	 * 
	 * @return Objeto com as informações do filme
	 */
	@ReturnNullObject
	public Film findById(final HttpServletRequest request, final Integer id) {
		return new ReturnServiceHelper(request).getById(id);		
	}
	
	/**
	 * Altera a descrição do filme e Atualiza a versão
	 * 
	 * @param request Objeto HttpServletRequest
	 * 
	 * @paramfilm Objeto film
	 * 
	 * @param inputParameter Parametros a serem atualizados
	 * 
	 * @return Objeto com as informações do filme
	 */	
	@ReturnNullObject
	public ObjectRsp updateDescription(final HttpServletRequest request, final Film film, final InputParameter inputParameter) {
		new ReturnServiceHelper(request).updateDescription(film, inputParameter);
		return new ObjectRsp(SUCCESS_MESSAGE);
	}

}
