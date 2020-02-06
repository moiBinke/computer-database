package com.excilys.computerDatabase.exceptions;
/**
 * Classe pour traiter les exceptions liées au jdbc à sql.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 *@see RuntimeException
 */
public class DAOExceptions extends RuntimeException{

	
	public DAOExceptions() {
		super();
	}
	
	public DAOExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOExceptions(String message) {
		super(message);
	}

	public DAOExceptions(Throwable cause) {
		super(cause);
	}
	
	
}
