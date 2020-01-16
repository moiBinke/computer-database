package com.excilys.computerDatabase;

import java.util.ArrayList;

import com.excilys.computerDatabase.daos.ComputerDAO;
import com.excilys.computerDatabase.model.Computer;
/**
 * Classe de Page .
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class Pages {
//	int iteration=0;
//	int stop=0;//boolean pour dire d'arreter la pagination
//	int taillePage=10;
	
	public Pages() {
		super();
		// TODO Auto-generated constructor stub
	}
	private ComputerDAO computerDAO;
	public Pages(ComputerDAO computerDAO) {
		super();
		this.computerDAO=computerDAO;
	}
//	public Pages(ComputerDAO computerDAO,int taillePage) {
//		super();
//		this.computerDAO=computerDAO;
//		this.taillePage=taillePage;
//	}
	
	public ArrayList<Computer> getPage(int taillePage, int ligneDebut){
		 ArrayList<Computer> page=new  ArrayList<Computer>();
		 page=computerDAO.getComputerListPage(taillePage,ligneDebut);
		 return page;
		 
	}
	/**
	 * @param paginer qui est soit '<' pour dire previous ou '>' pour dire suivant
	 */
//	public ArrayList<Computer>  pagination(String paginer) {
//		 ArrayList<Computer> page=new  ArrayList<Computer>();
//		while(stop!=1) {
//			if(paginer.equals("<") && this.iteration>0) {
//				--iteration;
//				page=getPage( taillePage,  iteration*this.taillePage);
//				--iteration;
//			}
//		}
//		
//	}
	
	
	
	
}
