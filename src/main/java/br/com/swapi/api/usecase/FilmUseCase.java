package br.com.swapi.api.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.swapi.api.logger.aspects.LevelLogger;
import br.com.swapi.api.logger.aspects.InjectLog;
import br.com.swapi.api.repository.FilmRepository;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;

/**
 * Classe UseCase - Filme
 * 
 * @author Clayton Morais de Oliveira
 *
 */
@Component
public class FilmUseCase {
	
	@Autowired
	private transient FilmRepository filmRepository;

	/**
	 * Retorna a lista de todos os Filmes
	 * 
	 * @return Lista do Objeto Filme
	 */
	@InjectLog(LevelLogger.DEBUG)
    public ReturnService listAll() {
    	  return filmRepository.listAll();    	  
	}


	/**
	 * Retorna os detalhes do filme
	 * 
	 * @param id Identificador do film
	 * 
	 * @return Objeto com as informações do filme
	 */
	@InjectLog(LevelLogger.DEBUG)
	public Film findById(final Integer id) {
		return filmRepository.findById(id);
		
	}


	/**
	 * Altera a descrição do filme e Atualiza a versão
	 * 
	 * @param id Identificador do film
	 * 
	 * @param inputParameter Parametros a serem atualizados
	 * 
	 * @return Filme após a alteração
	 */
	@InjectLog(LevelLogger.DEBUG)
	public Film updateDescription(final Integer id, final InputParameter inputParameter) {
		return filmRepository.updateDescription(filmRepository.findById(id), inputParameter);
	}

}
