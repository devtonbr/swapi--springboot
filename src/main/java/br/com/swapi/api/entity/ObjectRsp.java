package br.com.swapi.api.entity;

import java.io.Serializable;

/**
 * Classe para Tratar os Retornos de Negocio
 * 
 * @author Clayton Morais de Oliveira
 *
 */
public class ObjectRsp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3465927850655752542L;
	
	private String detail;

	/**
	 * Construtor com parametros
	 * @param detail Mensagem
	 */
	public ObjectRsp(String detail) {
		this.detail = detail;
	}

	/**
	 * Retorna o valor de detail
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}
	
	
	
	

}
