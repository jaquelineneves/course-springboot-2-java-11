package com.educandoweb.course.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	//msg padrao pra erro de nao encontrado
	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}
}
