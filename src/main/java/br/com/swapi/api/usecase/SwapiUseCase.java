package br.com.swapi.api.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.swapi.api.logger.aspects.LevelLogger;
import br.com.swapi.api.logger.aspects.InjectLog;
import br.com.swapi.api.repository.FilmRepository;
import br.com.swapi.api.repository.SwapiRepository;
import br.com.swapi.api.entity.ReturnService;

/**
 * Classe UseCase - Swapi
 *
 * @author Clayton Morais de Oliveira
 *
 */
@Component
public class SwapiUseCase {

	@Autowired
	private transient SwapiRepository swapiRepository;

	@Autowired
	private transient FilmRepository filmRepository;

	/**
	 * Carrega a lista de filmes a partir da api do StarWars e guarda em memória
	 *
	 * @return Lista do Objeto Filme
	 */
	@InjectLog(LevelLogger.DEBUG)
	public ReturnService loadAll() {
		ReturnService films = swapiRepository.loadAll();
		filmRepository.saveAll(films);
		return films;
	}

}
