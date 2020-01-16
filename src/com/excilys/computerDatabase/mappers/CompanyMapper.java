package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.model.Company;

/**
 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en Company Bean 
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class CompanyMapper {

	/**
	 *Le Mapping SQL==>Java Bean: permet de convertir une ligne SQL en company Bean 
	 *@param resultSet de type ResultSet(ligne  SQL)
	 *@throws SQLException
	 *@return company: un objet company
	 */
	public static Company mapCompany(ResultSet resultset) throws SQLException {
		Company company=new Company();
		company.setId(resultset.getLong("id"));
		company.setName(resultset.getString("name"));
		return company;
	}
}
