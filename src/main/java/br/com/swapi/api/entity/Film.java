package br.com.swapi.api.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Dados do filme
 *
 * @author Clayton Morais de Oliveira
 *
 * @param title Título do filme
 *
 * @param id Identificador do Filme
 *
 * @param openingCrawl Descrição do Filme
 *
 * @param director Diretor do Filme
 *
 * @param producer Produtora do Filme
 *
 * @param releaseDate Data de lançamento
 *
 * @param characters Elenco do filme
 *
 * @param planets Planetas
 *
 * @param starships Nave espacial
 *
 * @param vehicles Veiculos utilizados no filme
 *
 * @param species Especies
 *
 * @param created Criado em
 *
 * @param edited Editado em
 *
 * @param url Url
 *
 * @param version Versão, incrementada a cada alteração da descrição
 *
 */
public record Film(
		String title,
		Integer id,
		String openingCrawl,
		String director,
		String producer,
		String releaseDate,
		List<String> characters,
		List<String> planets,
		List<String> starships,
		List<String> vehicles,
		List<String> species,
		String created,
		String edited,
		String url,
		Integer version) implements Serializable {

	private static final int INITIAL_VERSION = 1;

	/**
	 * O filme sem versão informada começa na versão inicial
	 */
	public Film {
		version = version == null ? INITIAL_VERSION : version;
	}

	/**
	 * Cria o filme na versão inicial
	 */
	public Film(
			String title,
			Integer id,
			String openingCrawl,
			String director,
			String producer,
			String releaseDate,
			List<String> characters,
			List<String> planets,
			List<String> starships,
			List<String> vehicles,
			List<String> species,
			String created,
			String edited,
			String url) {
		this(title, id, openingCrawl, director, producer, releaseDate,
				characters, planets, starships, vehicles, species, created, edited, url, INITIAL_VERSION);
	}

	/**
	 * Atualiza a descrição, atualizando a versão do filme
	 * A versão só é incrementada quando a descrição muda
	 * @param description Descrição do filme
	 * @return Novo objeto com a descrição e a versão atualizadas, ou o próprio objeto se a descrição for a mesma
	 */
	public Film updateDescription (String description) {
		if (Objects.equals(openingCrawl, description)) {
			return this;
		}
		return new Film(title, id, description, director, producer, releaseDate,
				characters, planets, starships, vehicles, species, created, edited, url, version+1);
	}

}
