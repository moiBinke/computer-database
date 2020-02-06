package com.excilys.computerDatabase.exceptions;

/**
 *DAOConfigurationException: exception qui va gerer les runtimeException 
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class DAOConfigurationException extends RuntimeException {


	
	public DAOConfigurationException() {
		super();
	}
	
	public DAOConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOConfigurationException(String message) {
		super(message);
	}
	
	public DAOConfigurationException(Throwable cause) {
		super(cause);
	}

	
}
