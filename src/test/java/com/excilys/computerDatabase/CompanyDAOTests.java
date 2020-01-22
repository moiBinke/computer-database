package com.excilys.computerDatabase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computerDatabase.daos.*;
import com.excilys.computerDatabase.model.Company;
public class CompanyDAOTests {
	public static DaoFactory daoFactory;
	public static CompanyDAO companyDAO;
	@Before
	public void setUp() throws Exception {
		daoFactory=DaoFactory.getInstanceH2();
		companyDAO=CompanyDAO.getInstance(daoFactory);
		//companyDAO.createTestDatabase();
		//companyDAO.insertInTestDatabase();
	}

	@After
	public void tearDown() throws Exception {
		assertTrue(1==1);
	}

	@Test
	public void testGetListCompany() {
		assertTrue(companyDAO.getCompanyList().size()==20);
	}
	@Test
	public void testGetCompanyById() {
		Company company1=new Company( 1L,"Apple Inc.");
		Company company2=companyDAO.getCompanyById(1L).get();
		assertEquals(company1, company2);
	}

}
