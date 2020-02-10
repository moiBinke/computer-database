package com.excilys.computerDatabase.exceptions;

/**
 * Classe pour traiter les exceptions liées à la validation des modèles construites.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 *@see RuntimeException
 */
public class ComputerValidatorException extends RuntimeException{



		public ComputerValidatorException() {
			super();
		}
		
		public ComputerValidatorException(String message, Throwable cause) {
			super(message, cause);
		}
		
		public ComputerValidatorException(String message) {
			super(message);
		}
	
		public ComputerValidatorException(Throwable cause) {
			super(cause);
		}
		
		public static class NameValidator extends ComputerValidatorException{

			public NameValidator() {
				super();
			}

			public NameValidator(String message, Throwable cause) {
				super(message, cause);
			}

			public NameValidator(String message) {
				super(message);
			}

			public NameValidator(Throwable cause) {
				super(cause);
			}
			
		}
		public static class DateValidator extends ComputerValidatorException{

			public DateValidator() {
				super();
			}

			public DateValidator(String message, Throwable cause) {
				super(message, cause);
			}

			public DateValidator(String message) {
				super(message);
			}

			public DateValidator(Throwable cause) {
				super(cause);
			}
			
		}
		
	}
