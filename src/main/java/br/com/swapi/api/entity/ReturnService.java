package br.com.swapi.api.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Lista de filmes
 *
 * @param count Quantidade de filmes
 * @param next Próxima página
 * @param previous Página anterior
 * @param results Filmes
 */
public record ReturnService(
		String count,
		String next,
		String previous,
		List<Film> results) implements Serializable {
}
