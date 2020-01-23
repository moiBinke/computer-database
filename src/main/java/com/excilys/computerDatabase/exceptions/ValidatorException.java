package com.excilys.computerDatabase.exceptions;

/**
 * Classe pour traiter les exceptions liées à la validation des modèles construites.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 *@see RuntimeException
 */
public class ValidatorException extends RuntimeException{



		/**
		   * Constructeur sans argument
		   * 
		   */
		public ValidatorException() {
			super();
		}
		/**
		   * Constructeur avec  argument et l'erreur 
		   */
		public ValidatorException(String message, Throwable cause) {
			super(message, cause);
		}
		/**
		   * Constructeur avec un message en argument
		   * @param message qui est le message à envoyer en cas d'exception liée à la création de model ne respectant les règles de création
		   */
		public ValidatorException(String message) {
			super(message);
		}
		/**
		   * Constructeur avec une erreur en argument
		   * @param cause: erreur levéee
		   */
		public ValidatorException(Throwable cause) {
			super(cause);
		}
		
		
	}
