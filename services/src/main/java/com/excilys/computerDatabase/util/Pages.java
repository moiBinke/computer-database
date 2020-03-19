package com.excilys.computerDatabase.util;

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
	int iteration=0;
	boolean stop=false;//boolean pour dire d'arreter la pagination
	int taillePage=10;
	
	public Pages() {
		super();
		// TODO Auto-generated constructor stub
	}
	private ComputerDAO computerDAO;
	public Pages(ComputerDAO computerDAO) {
		super();
		this.computerDAO=computerDAO;
	}
	public Pages(ComputerDAO computerDAO,int taillePage) {
		super();
		this.computerDAO=computerDAO;
		this.taillePage=taillePage;
	}
	
	public int getIteration() {
		return iteration;
	}
	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
	public boolean getStop() {
		return stop;
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	public int getTaillePage() {
		return taillePage;
	}
	public void setTaillePage(int taillePage) {
		this.taillePage = taillePage;
	}
	public ArrayList<Computer> getPage(int taillePage, int ligneDebut, String orderBy, String search){
		 ArrayList<Computer> page=new  ArrayList<Computer>();
		 page=computerDAO.getComputerListPage(taillePage,ligneDebut,orderBy,search);
		 return page;
		 
	}
	/**
	 * @param paginer qui est soit '<' pour dire previous ou '>' pour dire suivant
	 */
	public ArrayList<Computer> pagination(String paginer,String orderBy,String search) {
		 ArrayList<Computer> page=new  ArrayList<Computer>();
		if(!stop) {
			if(paginer.equals("<") && this.iteration>0) {
				--iteration;
				page=getPage( taillePage,  iteration*this.taillePage,orderBy, search);
				System.out.println("----");
			}
			if(paginer.equals(">")) {
				iteration++;
				page=getPage( taillePage,  iteration*this.taillePage,orderBy,search);
				System.out.println(iteration);
				
			}
		}
		return page;
	}
	public ArrayList<Computer> getPageOrderByName(int taillePage2, int pageIterator) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
