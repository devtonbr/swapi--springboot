package br.com.swapi.api.entity;

import java.io.Serializable;

public class InputParameter implements Serializable {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = -7931447724893736280L;
	
	private String description;

	/**
	 * Retorna o atributos
	 * @return Valor do objeto description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Altera o valor do objeto
	 * @param description para o atributo description
	 */
	public void setDescription(String description) {
		this.description = description;
	}	

}
