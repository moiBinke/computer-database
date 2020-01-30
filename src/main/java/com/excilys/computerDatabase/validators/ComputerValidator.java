package com.excilys.computerDatabase.validators;


import com.excilys.computerDatabase.exceptions.ValidatorException;
import com.excilys.computerDatabase.model.Computer;

public class ComputerValidator {



	public  void validateComputer(Computer computer) {
		validateDates(computer);
		validateComputerName(computer);
	}
	public void validateDates(Computer computer){
		if(computer.getIntroduced()==null || computer.getDiscontinued()==null) {
			//We do nothing because it's correct
		}
		else {
			if(computer.getDiscontinued().isAfter(computer.getIntroduced())) {
				//We do nothing because it's correct
			}
			else {
				//We put an exception
				throw new ValidatorException.DateValidator("ValidatorException: Introduced date should be before discontinuedDate");
			}
		}
	}
	public void validateComputerName(Computer computer){
		if(computer.getName()==null || computer.getName().equals("")) {
			throw new ValidatorException.NameValidator("ValidatorException: Computer Name is required");
		}
		//else we do nothing because it's correct
		
	}
}