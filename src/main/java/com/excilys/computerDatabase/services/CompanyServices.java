package com.excilys.computerDatabase.services;

import java.util.ArrayList;

import com.excilys.computerDatabase.daos.CompanyDAO;
import com.excilys.computerDatabase.model.Company;

public class CompanyServices {

	private static CompanyServices companyServices;
	private CompanyDAO companyDAO;
	
	private CompanyServices(CompanyDAO companyDAO) {
		this.companyDAO=companyDAO;
	}
	public static CompanyServices getInstance(CompanyDAO companyDAO) {
		if(companyServices==null) {
			companyServices=new CompanyServices(companyDAO);
			return companyServices;
		}
		return companyServices;
	}
	public Company getById(Long companyId) {
		return null;
	}
	public ArrayList<Company> findALl() {
		return null;
	}
}
