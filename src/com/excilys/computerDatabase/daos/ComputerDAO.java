package com.excilys.computerDatabase.daos;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.excilys.computerDatabase.model.Computer;

/**
 * DAO: interface de ComputerDAO.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-16 
 */
public interface ComputerDAO {
	public abstract Computer addCommputer(Computer computer);
	public abstract ArrayList<Computer> getComputerList();
	public abstract Computer getComputer(Long computerId);
	public abstract Computer deleteComputer(Long computerId);
	public abstract Computer updateComputerName(Long idComputer, String name);
	public abstract Computer updateComputerIntroducedDate(Long computerToUpdateIntroducedDateId,
			Timestamp newIntroducedDate);
	public abstract Computer updateComputerDiscontinuedDate(Long computerToUpdateDiscontinuedDateId,
			Timestamp newDiscontinuedDate);
	public abstract Computer updateComputerCompany(Long computerToUpdateCompanyId,Long newCompanyId);
	public abstract ArrayList<Computer> getComputerListPage(int ligneDebut, int taillePage);
}
