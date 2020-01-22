package com.excilys.computerDatabase.daos;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computerDatabase.exceptions.DAOConfigurationException;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.exceptions.Logging;;

/**
 *DAO: implementation de CompanyDAO.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class CompanyDAO  {

	/**
	 * Les requetes utilisées pour company
	 */
	public static final String GET_List_COMPANY="SELECT name, id FROM company";
	public static final String GET_COMPANY_BY_ID="SELECT name, id FROM company where id=? ";

	/**
	 * Construction du singleton:
	 */
	private DaoFactory daoFactory;
	
	private static CompanyDAO companyDAO;
	
	private CompanyDAO() {
		super();
	}

	private CompanyDAO(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	public static CompanyDAO getInstance(DaoFactory daoFactory) {
		if(companyDAO==null) {
			return new CompanyDAO(daoFactory);
		}
		return companyDAO;
	}
	/*
	 * Fonctions de connection
	 */
	/* fermeture du resultset */
	
	public static void fermetureResultset(ResultSet resultset) {
		if(resultset!=null) {
			try {
				resultset.close();
				Logging.afficherMessage("ResultSet fermé avec succès");
			}catch(SQLException sqlExcept) {
				Logging.afficherMessage("Error when closing ResultSet object");
				
			}
		}
	}
	/**
	 *  fermeture du resultset
	 *   */
	public static void fermetureStatement(Statement statement) {
		if(statement!=null) {
			try {
				statement.close();
				Logging.afficherMessage("Error when closing Statement object");
			}catch(SQLException sqlExcept) {
				Logging.afficherMessage("Error when closing Statement object");
			}
		}
	}
	/**
	 *  fermeture de connexion 
	 *  */
	public static void fermetureConnection(Connection connection) {
		if(connection!=null) {
			try {
				connection.close();
			}catch(SQLException sqlExcept) {
				Logging.afficherMessage("Error when closing Connection object");
			}
		}
	}
	/**
	 *  fermeture de tous 
	 *  */
	public static void fermeture(ResultSet resultset,Statement statement,Connection connection) {
		fermetureResultset( resultset);
		fermetureStatement( statement);
		fermetureConnection( connection);
	}
	
	/**
	 * Initialise la requête préparée basée sur la connexion passée en argument,
	 * avec la requête SQL et les objets donnés.
	 */
	public static PreparedStatement initialiserRequetePreparee( Connection connexion, String requeteSql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
	    PreparedStatement preparedStatement = connexion.prepareStatement( requeteSql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    for ( int i = 0; i < objets.length; i++ ) {
	        preparedStatement.setObject( i + 1, objets[i] );
	    }
	    return preparedStatement;
	}

	/**
	 * Les méthodes implémentées
	 */
	

	
	public ArrayList<Company> getCompanyList() {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Company>listeCompany=new ArrayList<Company>(); 
		Company company;
		try {
	        /**
	         *  Récupération d'une connexion depuis la Factory 
	         *  */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_List_COMPANY, false);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
				company = CompanyMapper.mapCompany(resultSet);
				listeCompany.add(company);
	        }
	    } catch ( SQLException e ) {
	       e.printStackTrace();
		Logging.afficherMessage("Error when trying to get Company list");
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
	    return listeCompany;
	}


	public Optional<Company> getCompanyById(Long idCompany) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Optional<Company> company = null;

		try {
	        /**
	         *  Récupération d'une connexion depuis la Factory 
	         *  */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_COMPANY_BY_ID, false, idCompany );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
	        	company = Optional.ofNullable(CompanyMapper.mapCompany(resultSet));
	        }
	        else {
	        	System.out.println("ce computer n'existe pas!");
	        }
	    } catch ( SQLException e ) {
	       e.printStackTrace();
	       Logging.afficherMessage("Error when trying to get Company by Id");
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
	    return company;
	}
	
	
	

}
