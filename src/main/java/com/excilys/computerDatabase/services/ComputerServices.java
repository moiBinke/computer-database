package com.excilys.computerDatabase.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerDatabase.daos.ComputerDAO;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.util.Pages;
/**LES SERVICES RETOURNENT DES ENTITÉS AU LIEU DES DTOs
 */
@Service
public class ComputerServices {
	@Autowired
	private ComputerDAO computerDAO;
	
	public ComputerServices() {
		
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
