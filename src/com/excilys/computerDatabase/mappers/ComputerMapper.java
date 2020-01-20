package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.model.Computer.ComputerBuilder;

/**
 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en Computer Bean 
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class ComputerMapper {
	
	public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
		if(dateToConvert==null)
			return null;
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}

	/**
	 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en Computer Bean 
	 *@param resultSet de type ResultSet(ligne  SQL)
	 *@throws SQLException
	 *@return company: un objet computer
	 */
	public static Computer mapComputer(ResultSet resultset) throws SQLException {
		
		Company company=new Company(resultset.getLong("company_id"),resultset.getString("company_name"));

		Computer computer = new ComputerBuilder(resultset.getString("computer_name"))
							.initializeWithId(resultset.getLong("computer_id"))
							.initializeWithIntroducedDate(convertToLocalDateViaSqlDate(resultset.getDate("introduced")))
							.initializeWithDiscontinuedDate(convertToLocalDateViaSqlDate(resultset.getDate("discontinued")))
							.initializeWithCompany(company)
							.build();	
		return computer;
	}
	
}
