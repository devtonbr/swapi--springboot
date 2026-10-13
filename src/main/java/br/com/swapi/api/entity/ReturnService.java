package br.com.swapi.api.entity;

import java.io.Serializable;
import java.util.List;


public class ReturnService implements Serializable {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = -7067081160657621885L;
	
	private String count;
	
	private String next;
	
	private String previous;	
	
	private List<Film> results;

	/**
	 * @param count
	 * @param next
	 * @param previus
	 * @param results
	 */
	public ReturnService(
					String count, 
					String next, 
					String previous, 
					List<Film> results) {
		super();
		this.count = count;
		this.next = next;
		this.previous = previous;
		this.results = results;
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

	

}
