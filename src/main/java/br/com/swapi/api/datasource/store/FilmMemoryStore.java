package br.com.swapi.api.datasource.store;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.swapi.api.datasource.store.dto.FilmStoreDto;
import br.com.swapi.api.datasource.store.dto.ReturnServiceStoreDto;

/**
 * Armazena os filmes em memória
 *
 * As alterações são feitas por cópia e a lista é trocada de forma atômica
 *
 */
@Component
public class FilmMemoryStore {

	private final AtomicReference<ReturnServiceStoreDto> films = new AtomicReference<>();

	/**
	 * Carrega a lista de filmes
	 * @param returnService Lista de filmes
	 */
	public void load(ReturnServiceStoreDto returnService) {
		films.set(returnService);
	}

	/**
	 * Lista todos os filmes
	 * @return Lista de filmes
	 */
	public ReturnServiceStoreDto listAllFilms(){
		return films.get();
	}

	/**
	 * Obtém o elemento pelo id
	 * @param id Id A ser Consultado
	 * @return Objeto Filme
	 */
	public FilmStoreDto getById(Integer id) {
		return getById(films.get(), id);
	}

	/**
	 * Altera o filme de forma atômica
	 *
	 * A alteração pode ser reaplicada em caso de concorrência, por isso não deve ter efeitos colaterais
	 *
	 * @param id Id do filme
	 *
	 * @param change Alteração a ser aplicada sobre o filme guardado
	 *
	 * @return Filme após a alteração
	 */
	public FilmStoreDto update(Integer id, UnaryOperator<FilmStoreDto> change) {
		ReturnServiceStoreDto updated = films.updateAndGet(current -> {
			if (current == null) {
				return null;
			}
			List<FilmStoreDto> nwFilms = current.results().stream().map(
					item -> item.id().equals(id) ? change.apply(item) : item
			).collect(Collectors.toList());
			if (nwFilms.equals(current.results())) {
				return current;
			}
			return new ReturnServiceStoreDto(current.count(), current.next(), current.previous(), nwFilms);
		});
		return getById(updated, id);
	}

	private FilmStoreDto getById(ReturnServiceStoreDto current, Integer id) {
		if (current == null) {
			return null;
		}
		return current.results().stream().filter(item -> item.id().equals(id)).findAny()
				  .orElse(null);
	}

}
