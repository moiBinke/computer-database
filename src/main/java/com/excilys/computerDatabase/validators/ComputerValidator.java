package com.excilys.computerDatabase.validators;

import java.time.LocalDate;

import com.excilys.computerDatabase.exceptions.ValidatorException;
import com.excilys.computerDatabase.model.Computer;

public class ComputerValidator {


	
	public Computer validateComputer(Computer computer) {
		if(validateDates(computer) && validateComputerName(computer)) {}
		return computer;
	}
	public boolean validateDates(Computer computer) {
		if(computer.getIntroduced()==null || computer.getDiscontinued()==null) {
			return true;
		}
		else {
			if(computer.getDiscontinued().isAfter(computer.getIntroduced())) {
				return true;
			}
			else {
				throw new ValidatorException("ValidatorException: Introduced date should be before discontinuedDate");
			}
		}
	}
	public boolean validateComputerName(Computer computer) {
		if(computer.getName()==null)return false;
		throw new ValidatorException("ValidatorException: Computer Name is required");
	}
}
