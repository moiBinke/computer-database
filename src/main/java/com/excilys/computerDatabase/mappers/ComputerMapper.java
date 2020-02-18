package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.model.Computer.ComputerBuilder;

/**
 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en Computer Bean 
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class ComputerMapper implements RowMapper<Computer>{
	
	public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
		if(dateToConvert==null)
			return null;
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}
	
	public static LocalDate convertStringToLocalDateViaSqlDate(String dateString) throws ParseException {
        Date dateToConvert=new SimpleDateFormat("yyyy-MM-dd").parse(dateString);  
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
	
	public static ComputerDTO convertFromComputerToComputerDTO(Computer computer) {
		if(computer==null) {
			return null;
		}
		ComputerDTO computerDto= new ComputerDTO();
		computerDto.setId(computer.getId());
		if(computer.getIntroduced()!=null) {
			computerDto.setIntroduced(computer.getIntroduced().toString());
		}
		if(computer.getDiscontinued()!=null) {
			computerDto.setDiscontinued(computer.getDiscontinued().toString());
		}
		computerDto.setName(computer.getName());
		computerDto.setCompany(CompanyMapper.mapFromCompanyToCompanyDto(computer.getCompany()));
		return computerDto;
	}
	
	public static Computer convertFromComputerDtoToComputer(ComputerDTO computerDto) throws ParseException {
		Computer computer=new Computer.ComputerBuilder(computerDto.getName())
									  .initializeWithId(computerDto.getId())
									  .initializeWithIntroducedDate(convertStringToLocalDateViaSqlDate(computerDto.getIntroduced()))
									  .initializeWithDiscontinuedDate(convertStringToLocalDateViaSqlDate(computerDto.getDiscontinued()))
									  .initializeWithCompany(CompanyMapper.mapFromCompanyDtoToCompany(computerDto.getCompany()))
									  .build();
		return computer;
	}

	@Override
	public Computer mapRow(ResultSet resultset, int rowNum) throws SQLException {
		return mapComputer(resultset);
	}
}
