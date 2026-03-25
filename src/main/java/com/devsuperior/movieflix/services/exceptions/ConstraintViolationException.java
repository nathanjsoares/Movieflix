package com.devsuperior.movieflix.services.exceptions;

public class ConstraintViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConstraintViolationException(String msg) {
		super(msg);
	}
}
