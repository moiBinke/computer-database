package com.excilys.computerDatabase.daos;

import java.util.ArrayList;

import com.excilys.computerDatabase.model.Computer;

public interface ComputerDAO {
	public abstract Computer addCommputer(Computer computer);
	public abstract ArrayList<Computer> getComputerList();
	public abstract Computer getComputer(Long computerId);
	public abstract Computer deleteComputer(Long computerId);
	public abstract Computer updateComputer(Object...object);
}
