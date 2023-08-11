package br.com.swapi.api.model.exception;

import org.springframework.http.HttpStatus;

public class FilmException extends RuntimeException {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = 7344015246384446900L;

	private HttpStatus httpStatus;

	/**
	 * Construtor com parametros
	 * 
	 * @param message Mensagem
	 * 
	 * @param cause Objeto de exceção
	 * 
	 */
	public FilmException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construtor com parametros
	 * 
	 * @param message Mensagem
	 */
	public FilmException(String message) {
		super(message);
	}


	public FilmException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
