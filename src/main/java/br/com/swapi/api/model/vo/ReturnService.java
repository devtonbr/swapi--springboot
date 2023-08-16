package br.com.swapi.api.model.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ReturnService implements Serializable {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = -7067081160657621885L;
	
	private String count;
	
	private String next;
	
	private String previous;	
	
	private List<Film> results;

	public ReturnService() {
	}

	/**
	 * @param count
	 * @param next
	 * @param previus
	 * @param results
	 */
	@JsonCreator
	public ReturnService(
					@JsonProperty("count") String count, 
					@JsonProperty("next") String next, 
					@JsonProperty("previous") String previous, 
					@JsonProperty("results") List<Film> results) {
		super();
		this.count = count;
		this.next = next;
		this.previous = previous;
		this.results = results;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto next
	 */
	public String getNext() {
		return next;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto previous
	 */
	public String getPrevious() {
		return previous;
	}

	/**
	 * Retorna o atributos
	 * @return Valor do objeto results
	 */
	public List<Film> getResults() {
		return results;
	}

	/**
	 * Altera o valor do objeto
	 * @param results para o atributo results
	 */
	public void setResults(List<Film> results) {
		this.results = results;
	}
	

}
