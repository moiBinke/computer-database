package com.excilys.computerDatabase.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computerDatabase.daos.ComputerDAO;
import com.excilys.computerDatabase.daos.DaoFactory;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.util.Pages;
/**lES SERVICES RETOURNENT DES eNTITÉS AU LIEU DES dtos
 */
public class ComputerServices {
	private ComputerDAO computerDAO;
	private static ComputerServices computerServices;
	private DaoFactory daoFactory;
	private ComputerServices() {
		
	}
	
	public static ComputerServices getInstance() {
		if(computerServices==null) {
			computerServices=new ComputerServices();
			computerServices.daoFactory=DaoFactory.getInstance();
			computerServices.computerDAO=ComputerDAO.getInstance(computerServices.daoFactory);
			return computerServices;
		}
		return computerServices;
	}

	public ArrayList<Computer> findAll() {
		return computerDAO.getComputerList();
	
	}

	public ArrayList<Computer> getPage(int taillePage, int pageIterator, String orderBy) {
		Pages page =new Pages(computerDAO);
		return page.getPage(taillePage, pageIterator,orderBy);
		
	}
	
	public Computer create(Computer newComputer) throws ParseException {
		
		return computerDAO.addCommputer(newComputer);
	}

	public int size() {
		return computerDAO.size();
	}

	public  Optional<Computer> findById(Long idComputer) {
		return computerDAO.getComputer(idComputer);
	}

	public Computer update(Computer computerToUpdate) {
		System.out.println(computerToUpdate);
		return computerDAO.updateComputer(computerToUpdate);
	}

	public void deleteComputer(long idComputer) {
		 computerDAO.deleteComputer(idComputer);
	}

	public ArrayList<Computer> getPageOrderByComputerName(int taillePage, int pageIterator) {
		Pages page =new Pages(computerDAO);
		return page.getPageOrderByName(taillePage, pageIterator);
	}

	public ArrayList<Computer> search(String search) {
		return computerDAO.search(search);
	}
}
