package com.excilys.computerDatabase.validators;


import com.excilys.computerDatabase.exceptions.ComputerValidatorException;
import com.excilys.computerDatabase.model.Computer;
/**
 *ComputerValidator: for computer properties validation 
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-30 
 */
public class ComputerValidator {



	public  void validateComputer(Computer computer) {
		validateDates(computer);
		validateComputerName(computer);
	}
	private void validateDates(Computer computer){
		if(computer.getIntroduced()==null || computer.getDiscontinued()==null) {
			//We do nothing because it's correct
		}
		else {
			if(computer.getDiscontinued().isAfter(computer.getIntroduced())) {
				//We do nothing because it's correct
			}
			else {
				//We put an exception
				throw new ComputerValidatorException.DateValidator("ValidatorException: Introduced date should be before discontinuedDate");
			}
		}
	}
	private void validateComputerName(Computer computer){
		if(computer.getName()==null || computer.getName().equals("")) {
			throw new ComputerValidatorException.NameValidator("ValidatorException: Computer Name is required");
		}
		//else we do nothing because it's correct
		
	}
}