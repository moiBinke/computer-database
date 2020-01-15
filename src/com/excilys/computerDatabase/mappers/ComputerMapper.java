package com.excilys.computerDatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerDatabase.model.Computer;

public class ComputerMapper {

	public static Computer mapComputer(ResultSet resultset) throws SQLException {
		Computer computer=new Computer();
		computer.setName(resultset.getString("name"));
		computer.setIntroduced(resultset.getTimestamp("introduced"));
		computer.setDiscontinued(resultset.getTimestamp("discontinued"));
		computer.setCompany_id(resultset.getLong("company_id"));
		return computer;
	}
	
}
