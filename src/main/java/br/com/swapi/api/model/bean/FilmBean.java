package br.com.swapi.api.model.bean;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.swapi.api.logger.aspects.Level;
import br.com.swapi.api.logger.aspects.LoggerBean;
import br.com.swapi.api.model.constants.Constants;
import br.com.swapi.api.model.dao.FilmDao;
import br.com.swapi.api.model.vo.Film;
import br.com.swapi.api.model.vo.InputParameter;
import br.com.swapi.api.model.vo.ObjectResponse;
import br.com.swapi.api.model.vo.ReturnService;

/**
 * Classe Bean - Autenticação
 * 
 * @author Clayton Morais de Oliveira
 *
 */
@Component
public class FilmBean implements Constants {
	
	@Autowired
	private transient FilmDao dao;

	/**
	 * Retorna a lista de todos os Filmes
	 * 
	 * @param request Objeto HttpServletRequest
	 * 
	 * @return Lista do Objeto Filme
	 */
	@LoggerBean(Level.DEBUG)	
    public ReturnService listAll(final HttpServletRequest request) {
    	  return dao.listAll(request);    	  
	}


	/**
	 * Retorna os detalhes do filme
	 * 
	 * @param request Objeto HttpServletRequest
	 * 
	 * @param id Identificador do film
	 * 
	 * @return Objeto com as informações do filme
	 */
	@LoggerBean(Level.DEBUG)	
	public Film findById(final HttpServletRequest request, final Integer id) {
		return dao.findById(request, id);
		
	}


	/**
	 * Altera a descrição do filme e Atualiza a versão
	 * 
	 * @param request Objeto HttpServletRequest
	 * 
	 * @param id Identificador do film
	 * 
	 * @param inputParameter Parametros a serem atualizados
	 * 
	 * @return Objeto com as informações do filme
	 */
	@LoggerBean(Level.DEBUG)	
	public ObjectResponse updateDescription(final HttpServletRequest request,final Integer id,final InputParameter inputParameter) {		
		return dao.updateDescription(request, dao.findById(request, id), inputParameter);
	}

}
