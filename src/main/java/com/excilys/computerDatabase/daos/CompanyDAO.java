package com.excilys.computerDatabase.daos;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

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
@Repository
public class CompanyDAO  {

	/**
	 * Les requetes utilisées pour company
	 */
	public static final String GET_List_COMPANY="SELECT name, id FROM company";
	public static final String GET_COMPANY_BY_ID="SELECT name, id FROM company where id=? ";
	public static final String DELETE_COMPANY="DELETE FROM company WHERE id=?";
	/**
	 * Construction du singleton:
	 */	
	
	private CompanyDAO() {
		super();
	}

	/**
	 * En paramètre l'ancienne getInstance
	 * @param daoFactory
	 * @return
	 */

//	public static CompanyDAO getInstance() {
//		if(companyDAO==null) {
//			return new CompanyDAO();
//		}
//		return companyDAO;
//	}
	/*
	 * Fonctions de connection
	 */
	/* fermeture du resultset */
	
	public static void fermetureResultset(ResultSet resultset) {
		if(resultset!=null) {
			try {
				resultset.close();
				Logging.afficherMessageDebug("ResultSet fermé avec succès");
			}catch(SQLException sqlExcept) {
				Logging.afficherMessageError("Error when closing ResultSet object");
				
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
				Logging.afficherMessageError("Statement fermé avec succès");
			}catch(SQLException sqlExcept) {
				Logging.afficherMessageError("Error when closing Statement object");
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
		Connection connexion=null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Company>listeCompany=new ArrayList<Company>(); 
		Company company;
		try {
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_List_COMPANY, false);
	        resultSet = preparedStatement.executeQuery();
	        while ( resultSet.next() ) {
				company = CompanyMapper.mapCompany(resultSet);
				listeCompany.add(company);
	        }
	    } catch ( SQLException e ) {
	       e.printStackTrace();
		Logging.afficherMessageError("Error when trying to get Company list");
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
			connexion=DaoFactoryHikary.getInstance().getConnection();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_COMPANY_BY_ID, false, idCompany );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
	        	company = Optional.ofNullable(CompanyMapper.mapCompany(resultSet));
	        }
	        else {
	        	Logging.afficherMessageError("ce computer n'existe pas!");
	        }
	    } catch ( SQLException e ) {
	       e.printStackTrace();
	       Logging.afficherMessageError("Error when trying to get Company by Id");
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
	    return company;
	}
	
	/*
	 * DELETE A COMPANY: we should delete all computer of this company.
	 */
	public void deleteComputer(Long idCompany) {
		Connection connexion = null;
	    PreparedStatement preparedStatement1 = null;
	    PreparedStatement preparedStatement2 = null;
	    int resultSet1;
	    int resultSet2;
	    connexion=DaoFactoryHikary.getInstance().getConnection();
		try {
			
			
			connexion.setAutoCommit(false);
			
	        preparedStatement1 = initialiserRequetePreparee( connexion, ComputerDAO.DELETE_COMPUTER_WITH_COMPANY_ID, false, idCompany );
	        resultSet1 = preparedStatement1.executeUpdate();
	        
	        preparedStatement2 = initialiserRequetePreparee( connexion,DELETE_COMPANY , false, idCompany);
	        resultSet2 = preparedStatement2.executeUpdate();
	        System.out.println("r1:"+resultSet1);
	        System.out.println("r1:"+resultSet2);
	        if(resultSet2==1 && resultSet1>=0) {
	        	connexion.commit();
	        	Logging.afficherMessage("company with id "+idCompany+" is deleted succesfully. Even "+resultSet1+" computer(s) is(are) deleted during this operation");
	        }
	        else if(resultSet2==0) {
	        	connexion.rollback();
	        	Logging.afficherMessage("No company with id "+idCompany+" founded");
	        }
	        else {
	        	connexion.rollback();
	        	Logging.afficherMessageError("Error when deleting company with id :"+idCompany);
	        }
	        
	    } catch ( SQLException e ) {
	       e.printStackTrace();
	       Logging.afficherMessageError("Error when deleting company with id :\"+idCompany");
	    } finally {
	    	try {
				connexion.setAutoCommit(true);
			} catch (SQLException e) {
				 Logging.afficherMessageError("Error when trying to set to true connection autoCommit property");
			}
	        fermetureStatement( preparedStatement1);
	        fermetureStatement( preparedStatement2);
	        fermetureConnection(connexion );
	    }
	}
	

}
