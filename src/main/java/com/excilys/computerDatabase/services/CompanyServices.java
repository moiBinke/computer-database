package com.excilys.computerDatabase.services;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.daos.CompanyDAO;
import com.excilys.computerDatabase.daos.DaoFactoryHikary;
import com.excilys.computerDatabase.dto.CompanyDTO;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.model.Company;
@Service
public class CompanyServices {

	@Autowired
	private  CompanyDAO companyDAO;
	
	private CompanyServices() {
		
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
