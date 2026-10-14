package br.com.swapi.api.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.swapi.api.datasource.store.aspects.ReturnNullObject;
import br.com.swapi.api.datasource.store.FilmMemoryStore;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;
import br.com.swapi.api.repository.FilmRepository;

/**
 * Classe DataSource - Filme
 *
 * Acessa os filmes guardados em memória
 *
 * @author Clayton Morais de Oliveira
 *
 */
@Component
public class FilmDataSource implements FilmRepository {

	@Autowired
	private FilmMemoryStore store;

	/**
	 * Guarda a lista de filmes
	 *
	 * @param films Lista do Objeto Filme
	 */
	@Override
	public void saveAll(final ReturnService films) {
		store.load(films);
	}

	/**
	 * Retorna a lista de todos os Filmes
	 *
	 * @return Lista do Objeto Filme
	 */
	@Override
	@ReturnNullObject
    public ReturnService listAll() {
    	  return store.listAllFilms();
	}


	/**
	 * Retorna os detalhes do filme
	 *
	 * @param id Id A ser Consultado
	 *
	 * @return Objeto com as informações do filme
	 */
	@Override
	@ReturnNullObject
	public Film findById(final Integer id) {
		return store.getById(id);
	}

	/**
	 * Altera a descrição do filme e Atualiza a versão
	 *
	 * @param film Objeto film
	 *
	 * @param inputParameter Parametros a serem atualizados
	 *
	 * @return Filme após a alteração
	 */
	@Override
	@ReturnNullObject
	public Film updateDescription(final Film film, final InputParameter inputParameter) {
		return store.updateDescription(film, inputParameter);
	}

}
