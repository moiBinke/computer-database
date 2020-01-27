package com.excilys.computerDatabase.services;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computerDatabase.daos.CompanyDAO;
import com.excilys.computerDatabase.daos.DaoFactory;
import com.excilys.computerDatabase.dto.CompanyDTO;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.model.Company;

public class CompanyServices {

	private static CompanyServices companyServices;
	private static CompanyDAO companyDAO;
	private DaoFactory daoFactory;
	
	private CompanyServices() {
		
	}
	public static CompanyServices getInstance() {
		if(companyServices==null) {
			companyServices=new CompanyServices();
			companyServices.daoFactory=DaoFactory.getInstance();
			CompanyServices.companyDAO=CompanyDAO.getInstance(companyServices.daoFactory);
			return companyServices;
		}
		return companyServices;
	}
	public Optional<Company> getById(Long companyId) {
		return companyServices.companyDAO.getCompanyById(companyId);
	}
	public ArrayList<Company> findALl() {
		return companyServices.companyDAO.getCompanyList();
		
	}
}
