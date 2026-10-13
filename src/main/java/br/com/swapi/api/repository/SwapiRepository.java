package br.com.swapi.api.repository;

import br.com.swapi.api.entity.ReturnService;

/**
 * Interface Repository - Swapi
 *
 * Contrato a ser implementado pelo datasource
 *
 */
public interface SwapiRepository {

	/**
	 * Carrega a lista de filmes a partir da api do StarWars
	 *
	 * @return Lista do Objeto Filme
	 */
	ReturnService loadAll();

}
