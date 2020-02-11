package com.excilys.computerDatabase.daos;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.model.Company;

/**
 *DAO: implementation de CompanyDAO.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
@Repository
public class CompanyDAO  {

	/**
	 * Les requetes utilisées pour company
	 */
	public static final String GET_List_COMPANY="SELECT name, id FROM company";
	public static final String GET_COMPANY_BY_ID="SELECT name, id FROM company where id=:id ";
	public static final String DELETE_COMPANY="DELETE FROM company WHERE id=:id";
	/**
	 * Construction du singleton:
	 */	

	private DataSource dataSource;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	CompanyMapper companyMapper;
	
	
	private CompanyDAO(DataSource dataSource) {
		super();
		this.dataSource= dataSource;
		companyMapper=new CompanyMapper();
	}



	public ArrayList<Company> getCompanyList() {
		return (ArrayList<Company>) namedParameterJdbcTemplate.query(GET_List_COMPANY, new CompanyMapper());
	}


	public Optional<Company> getCompanyById(Long idCompany) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id",idCompany);
		List<Company> companies=namedParameterJdbcTemplate.query(GET_COMPANY_BY_ID,namedParameters,this.companyMapper);
		if(companies!=null && companies.size()==0) {
			return Optional.of(companies.get(0));
		}
		return Optional.empty();
	}
	
	/*
	 * DELETE A COMPANY: we should delete all computer of this company.
	 */
	public void deleteComputer(Long idCompany) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id",idCompany);
		namedParameterJdbcTemplate.query(DELETE_COMPANY,namedParameters,this.companyMapper);
	}
	

}
