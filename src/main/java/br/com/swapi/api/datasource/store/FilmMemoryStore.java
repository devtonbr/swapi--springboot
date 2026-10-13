package br.com.swapi.api.datasource.store;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;

/**
 * Armazena os filmes em memória
 *
 * As alterações são feitas por cópia e a lista é trocada de forma atômica
 *
 */
@Component
public class FilmMemoryStore {

	private final AtomicReference<ReturnService> films = new AtomicReference<>();

	/**
	 * Carrega a lista de filmes
	 * @param returnService Lista de filmes
	 */
	public void load(ReturnService returnService) {
		films.set(returnService);
	}

	/**
	 * Lista todos os filmes
	 * @return Lista de filmes
	 */
	public ReturnService listAllFilms(){
		return films.get();
	}

	/**
	 * Obtém o elemento pelo id
	 * @param id Id A ser Consultado
	 * @return Objeto Filme
	 */
	public Film getById(Integer id) {
		return getById(films.get(), id);
	}

	/**
	 * Altera a descrição do filme e Atualiza a versão
	 *
	 * A versão só é incrementada quando a descrição muda
	 *
	 * @param film Objeto film
	 *
	 * @param input Parametros a serem atualizados
	 *
	 * @return Filme após a alteração
	 */
	public Film updateDescription(Film film, InputParameter input) {
		ReturnService updated = films.updateAndGet(current -> {
			if (current == null) {
				return null;
			}
			List<Film> nwFilms = current.getResults().stream().map(
					item -> item.getId().equals(film.getId()) ? item.updateDescription(input.getDescription()) : item
			).collect(Collectors.toList());
			if (nwFilms.equals(current.getResults())) {
				return current;
			}
			return new ReturnService(current.getCount(), current.getNext(), current.getPrevious(), nwFilms);
		});
		return getById(updated, film.getId());
	}

	private Film getById(ReturnService current, Integer id) {
		if (current == null) {
			return null;
		}
		return current.getResults().stream().filter(item -> item.getId().equals(id)).findAny()
				  .orElse(null);
	}

}
