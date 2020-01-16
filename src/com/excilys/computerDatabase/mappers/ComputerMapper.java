package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.model.Computer;

/**
 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en Computer Bean 
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class ComputerMapper {

	/**
	 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en Computer Bean 
	 *@param resultSet de type ResultSet(ligne  SQL)
	 *@throws SQLException
	 *@return company: un objet computer
	 */
	public static Computer mapComputer(ResultSet resultset) throws SQLException {
		Computer computer=new Computer();
		computer.setId(resultset.getLong("id"));
		computer.setName(resultset.getString("name"));
		computer.setIntroduced(resultset.getTimestamp("introduced"));
		computer.setDiscontinued(resultset.getTimestamp("discontinued"));
		computer.setCompany_id(resultset.getLong("company_id"));
		return computer;
	}
	
}
