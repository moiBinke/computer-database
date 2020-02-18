package com.excilys.computerDatabase.daos;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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


	@PersistenceContext
	private EntityManager entityManager;

	

	public ArrayList<Company> getCompanyList() {
	
		CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = criteriabuilder.createQuery(Company.class);
		
		Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(root);
		
		TypedQuery<Company> companys = entityManager.createQuery(criteriaQuery);
		return (ArrayList<Company>) companys.getResultList();
	
	}


	@Transactional
	public Optional<Company> getCompanyById(Long idCompany) {
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery=criteriaBuilder.createQuery(Company.class);
		
		Root<Company>root=criteriaQuery.from(Company.class);
		Predicate byId = criteriaBuilder.equal(root.get("id"),idCompany);
		
		criteriaQuery.where(byId);
		 
		TypedQuery<Company> typedQuery = entityManager.createQuery(criteriaQuery);
	    ArrayList<Company> companies=(ArrayList) typedQuery.getResultList();
		if(companies!=null && companies.size()==1) {
			return Optional.of(companies.get(0));
		}
		return Optional.empty();
	}
	
	/*
	 * DELETE A COMPANY: we should delete all computer of this company.
	 */
	@Transactional
	public void deleteComputer(Long idCompany) {
		CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
		CriteriaDelete< Company>criteriaDelete=criteriaBuilder.createCriteriaDelete(Company.class);
		
		Root<Company>root=criteriaDelete.from(Company.class);
		Predicate byId=criteriaBuilder.equal(root.get("id"),idCompany);
		
		criteriaDelete.where(byId);
		entityManager.createQuery(criteriaDelete).executeUpdate();

	}
	

}
