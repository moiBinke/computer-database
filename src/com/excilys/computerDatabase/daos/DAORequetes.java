package com.excilys.computerDatabase.daos;

public class DAORequetes {
	/**
	 * Les requetes utilisées pour computer
	 */
	public static final String ADD_COMPUTER= "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	public static final String GET_COMPUTER_BY_ID= "SELECT * FROM computer where id=?";
	public static final String GET_List_COMPUTER= "SELECT * FROM computer";
	public static final String DELETE_COMPUTER= "DELETE FROM computer WHERE id=?";
	public static final String UPDATE_COMPUTER_NAME= "UPDATE computer SET name=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_INTRODUCED_DATE = "UPDATE computer SET introduced=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_DISCONTINUED_DATE = "UPDATE computer SET discontinued=? WHERE id=?;";
	public static final String GET_PAGE_COMPUTER = "SELECT * FROM computer LIMIT ?, ?";
	/**
	 * Les requetes utilisées pour company
	 */
	public static final String GET_List_COMPANY="SELECT * FROM company";
	public static final String GET_COMPANY_BY_ID="SELECT * FROM company where id=? ";
	public static final String UPDATE_COMPUTER_COMPANY_ID = "UPDATE computer SET company_id=? WHERE id=?;";
	
	
}
