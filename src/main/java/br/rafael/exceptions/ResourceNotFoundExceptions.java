package br.rafael.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptions extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundExceptions(String ex) {
		super(ex);
	}
}
