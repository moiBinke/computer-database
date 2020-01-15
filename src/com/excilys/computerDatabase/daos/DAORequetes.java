package com.excilys.computerDatabase.daos;

public class DAORequetes {
	/*
	 * Les requetes utilisées pour computer
	 */
	public static final String GET_COMPUTER_BY_ID="SELECT * FROM computer where id=?";
	public static final String ADD_COMPUTER="INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	public static final String GET_List_COMPUTER="SELECT * FROM computer";
	public static final String DELETE_COMPUTER="DELETE FROM computer WHERE id=?";
	/*
	 * Les requetes utilisées pour company
	 */
	public static final String GET_List_COMPANY="SELECT * FROM company";
	public static final String GET_COMPANY_BY_ID="SELECT * FROM company where id=? ";
}
