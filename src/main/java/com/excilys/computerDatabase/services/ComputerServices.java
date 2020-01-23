package com.excilys.computerDatabase.services;

import java.util.ArrayList;

import com.excilys.computerDatabase.daos.ComputerDAO;
import com.excilys.computerDatabase.daos.DaoFactory;
import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.util.Pages;

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

	public ArrayList<ComputerDTO> findAll() {
		ArrayList<Computer>computerList=computerDAO.getComputerList();;
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		for(Computer computer: computerList) {
			computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer));
		}
		return computerDTOList;
	}

	public ArrayList<ComputerDTO> getPage(int taillePage, int pageIterator) {
		Pages page =new Pages(computerDAO);
		ArrayList<Computer>computerList=page.getPage(taillePage, pageIterator);
		System.out.println(computerList.size());
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		for(Computer computer: computerList) {
			computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer));
		}
		return computerDTOList;
	}
	
	
}
