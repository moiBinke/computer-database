package com.excilys.computerDatabase;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computerDatabase.daos.CompanyDAO;
import com.excilys.computerDatabase.daos.ComputerDAO;
import com.excilys.computerDatabase.daos.DaoFactory;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;

import junit.framework.TestCase;
public class ComputerDAOTest {
	public static DaoFactory daoFactory;
	public static ComputerDAO computerDAO;
	
	@Before
	public void setUp() throws Exception {
		daoFactory=DaoFactory.getInstanceH2();
		computerDAO=computerDAO.getInstance(daoFactory);
		computerDAO.createTestDatabase();
		computerDAO.insertInTestDatabase();
	}

	@After
	public void tearDown() throws Exception {
		assertTrue(1==1);
	}
	
	@Test
	public void testAddComputer() throws ParseException {
		Computer newComputer=new Computer.ComputerBuilder("Del-Inspiron")
										 .initializeWithIntroducedDate(LocalDate.now().minusYears(10L))
										 .initializeWithDiscontinuedDate(LocalDate.now().minusYears(8L))
										 .initializeWithCompany(new Company(1L,"Apple Inc."))
										 .build();
		assertTrue(computerDAO.addCommputer(newComputer)!=null);
	}
	@Test
	public void testGetComputerById(){
		assertTrue(computerDAO.getComputer(1L)!=null);
	}
	@Test
	public void testGetListComputer() {
		assertTrue(computerDAO.getComputerList().size()==20);
	}
	@Test
	public void testDeleteComputer() {
		assertTrue(computerDAO.deleteComputer(1L)!=null);
	}

	
}
