package br.com.swapi.api.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Classe de Dados de autenticação
 * 
 * @author Clayton Morais de Oliveira
 *
 */
public class Film implements Serializable {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = 8260132585702238499L;

	private String title;
	
	private Integer id;
	
	private String openingCrawl;
	
	private String director;
	
	private String producer;
	
	private String releaseDate;
	
	private List<String> characters;
	
	private List<String> planets;	
	
	private List<String> starships;
	
	private List<String> vehicles;
	
	private List<String> species;
	
	private String created;
	
	private String edited;
	
	private String url;
	
	private Integer version;

	/**
	 * Construtor default com parametros
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
	 * @param version Versão
	 * 
	 * 
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
		
		super();
		this.title = title;
		this.id = id;
		this.openingCrawl = openingCrawl;
		this.director = director;
		this.producer = producer;
		this.releaseDate = releaseDate;
		this.characters = characters;
		this.planets = planets;
		this.starships = starships;
		this.vehicles = vehicles;
		this.created = created;
		this.species = species;
		this.edited = edited;
		this.url = url;
		this.version = 1;
		
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto openingCrawl
	 */
	public String getOpeningCrawl() {
		return openingCrawl;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto producer
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto characters
	 */
	public List<String> getCharacters() {
		return characters;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto planets
	 */
	public List<String> getPlanets() {
		return planets;
	}	

	/**
	 * Retorna o atributos
	 * @return Valor do objeto starships
	 */
	public List<String> getStarships() {
		return starships;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto vehicles
	 */
	public List<String> getVehicles() {
		return vehicles;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto edited
	 */
	public String getEdited() {
		return edited;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto species
	 */
	public List<String> getSpecies() {
		return species;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto version
	 */
	public Integer getVersion() {
		return version;
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
		Film film = new Film(title, id, description, director, producer, releaseDate,
				characters, planets, starships, vehicles, species, created, edited, url);
		film.version = version+1;
		return film;
	}

}