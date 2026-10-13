package br.com.swapi.api.exception;

import org.springframework.http.HttpStatusCode;

public class FilmException extends RuntimeException {

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = 7344015246384446900L;

	private HttpStatusCode httpStatus;

	public FilmException(HttpStatusCode httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatusCode getHttpStatus() {
		return httpStatus;
	}
}
