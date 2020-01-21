package com.excilys.computerDatabase.daos;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.excilys.computerDatabase.exceptions.DAOConfigurationException;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;

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
	 * Variables pour les fichiers de creations des bases de données tests
	 */
	public static  String fichierCreationTableTesT="src/test/resources/schema-creation.sql" ;
	public static  String fichierInsertTable="src/test/resources/initialisation-table.sql" ;	
	
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
			}catch(SQLException sqlExcept) {
				System.out.println("Echec de fermeture du resulset: "+sqlExcept.getMessage());
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
			}catch(SQLException sqlExcept) {
				System.out.println("Echec de fermeture du statement: "+sqlExcept.getMessage());
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
				System.out.println("Echec de fermeture de connection: "+sqlExcept.getMessage());
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
				System.out.println("hahaaha");
	        }
	    } catch ( SQLException e ) {
	       e.printStackTrace();
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
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
	    return company;
	}
	
	
	/**
	 * Lire le fichier de creation et d'initialisation
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + System.lineSeparator());
		}

		return sb.toString();
	}
	public Optional<String> lireRequeteTestH2(String fichier) {
		Optional<String> requeteCreation;
		File file = new File(fichier);
		try {
			requeteCreation =Optional.ofNullable(readFile(file));
		}catch(IOException ioException) {
			throw new DAOConfigurationException("le fichier creations de la base SQL. qui contient les paramètres de connection est introuvable");
		}

		return requeteCreation;
	}
	
	
	
	public void createTestDatabase(){
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        /**
	         *  Récupération d'une connexion depuis la Factory 
	         *  */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, lireRequeteTestH2(fichierCreationTableTesT).orElse(null) , false );
	        preparedStatement.executeUpdate();
	    } catch ( SQLException e ) {
	       e.printStackTrace();
	    } finally {
	        fermetureStatement(preparedStatement );
	        fermetureConnection(connexion );
	    }
	}
	
	public void insertInTestDatabase(){
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        /**
	         *  Récupération d'une connexion depuis la Factory 
	         *  */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, lireRequeteTestH2(fichierInsertTable).orElse(null) , false );
	        preparedStatement.executeUpdate();
	    } catch ( SQLException e ) {
	       e.printStackTrace();
	    } finally {
	    	 fermetureStatement(preparedStatement );
		     fermetureConnection(connexion );
	    }
	}

}
