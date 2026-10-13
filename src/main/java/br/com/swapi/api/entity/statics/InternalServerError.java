package br.com.swapi.api.entity.statics;

import br.com.swapi.api.entity.ObjectRsp;


public class InternalServerError extends ObjectRsp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3760853210902578059L;

	public InternalServerError() {		
		super("Erro interno no servidor. Contate o administrador.");		
	}

}
