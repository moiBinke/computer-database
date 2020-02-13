package com.excilys.computerDatabase.services;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.daos.CompanyDAO;

import com.excilys.computerDatabase.model.Company;
@Service
public class CompanyServices {

	
	private  CompanyDAO companyDAO;
	
	private CompanyServices(CompanyDAO companyDAO) {
		this.companyDAO=companyDAO;
	}

	public Optional<Company> getById(Long companyId) {
		return companyDAO.getCompanyById(companyId);
	}
	public ArrayList<Company> findALl() {
		return companyDAO.getCompanyList();
	}
	public void delete(Long companyToDeleteId) {
		companyDAO.deleteComputer(companyToDeleteId);
		
	}
}
