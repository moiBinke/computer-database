package com.excilys.computerDatabase.daos;

import java.util.ArrayList;

import com.excilys.computerDatabase.model.Company;

public interface CompanyDAO {

	public ArrayList<Company>getCompanyList();
	public Company getCompanyById(Long idCompany);
	
	
}
