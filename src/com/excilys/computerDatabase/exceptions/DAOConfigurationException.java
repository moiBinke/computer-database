package com.excilys.computerDatabase.exceptions;

/**
 *DAOConfigurationException: exception qui va gerer les runtimeException 
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class DAOConfigurationException extends RuntimeException {


	/**
	   * Constructeur sans argument
	   * 
	   */
	public DAOConfigurationException() {
		super();
	}
	/**
	   * Constructeur avec  argument et l'erreur 
	   */
	public DAOConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	   * Constructeur avec un message en argument
	   * @param message qui est le message à envoyer en cas d'exception liée à sql ou au jdbc
	   */
	public DAOConfigurationException(String message) {
		super(message);
	}
	/**
	   * Constructeur avec une erreur en argument
	   * @param cause: erreur levéee
	   */
	public DAOConfigurationException(Throwable cause) {
		super(cause);
	}

	
}
