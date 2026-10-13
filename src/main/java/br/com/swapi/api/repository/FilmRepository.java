package br.com.swapi.api.repository;

import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;

/**
 * Interface Repository - Filme
 *
 * Contrato a ser implementado pelo datasource
 *
 */
public interface FilmRepository {

	/**
	 * Guarda a lista de filmes
	 *
	 * @param films Lista do Objeto Filme
	 */
	void saveAll(ReturnService films);

	/**
	 * Retorna a lista de todos os Filmes
	 *
	 * @return Lista do Objeto Filme
	 */
	ReturnService listAll();

	/**
	 * Retorna os detalhes do filme
	 *
	 * @param id Id A ser Consultado
	 *
	 * @return Objeto com as informações do filme
	 */
	Film findById(Integer id);

	/**
	 * Altera a descrição do filme e Atualiza a versão
	 *
	 * @param film Objeto film
	 *
	 * @param inputParameter Parametros a serem atualizados
	 *
	 * @return Filme após a alteração
	 */
	Film updateDescription(Film film, InputParameter inputParameter);

}
