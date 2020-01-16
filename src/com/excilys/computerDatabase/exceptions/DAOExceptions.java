package com.excilys.computerDatabase.exceptions;
/**
 * Classe pour traiter les exceptions liées au jdbc à sql.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 *@see RuntimeException
 */
public class DAOExceptions extends RuntimeException{

	/**
	   * Constructeur sans argument
	   * 
	   */
	public DAOExceptions() {
		super();
	}
	/**
	   * Constructeur avec  argument et l'erreur 
	   */
	public DAOExceptions(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	   * Constructeur avec un message en argument
	   * @param message qui est le message à envoyer en cas d'exception liée à sql ou au jdbc
	   */
	public DAOExceptions(String message) {
		super(message);
	}
	/**
	   * Constructeur avec une erreur en argument
	   * @param cause: erreur levéee
	   */
	public DAOExceptions(Throwable cause) {
		super(cause);
	}
	
	
}
