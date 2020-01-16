package com.excilys.computerDatabase.daos;

import java.util.ArrayList;

import com.excilys.computerDatabase.model.Company;
/**
 * DAO: interface CompanyDAO.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-16 
 */
public interface CompanyDAO {

	public ArrayList<Company>getCompanyList();
	public Company getCompanyById(Long idCompany);
	
	
}
