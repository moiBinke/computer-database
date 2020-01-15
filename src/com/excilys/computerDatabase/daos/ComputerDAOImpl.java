package com.excilys.computerDatabase.daos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.computerDatabase.exceptions.DAOExceptions;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Computer;

public class ComputerDAOImpl implements ComputerDAO {
	private DaoFactory daoFactory;
	
	
	public ComputerDAOImpl() {
		super();
	}

	public ComputerDAOImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
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
	/* fermeture du resultset */
	public static void fermetureStatement(Statement statement) {
		if(statement!=null) {
			try {
				statement.close();
			}catch(SQLException sqlExcept) {
				System.out.println("Echec de fermeture du statement: "+sqlExcept.getMessage());
			}
		}
	}
	/* fermeture de connexion */
	public static void fermetureConnection(Connection connection) {
		if(connection!=null) {
			try {
				connection.close();
			}catch(SQLException sqlExcept) {
				System.out.println("Echec de fermeture de connection: "+sqlExcept.getMessage());
			}
		}
	}
	/* fermeture de tous */
	public static void fermeture(ResultSet resultset,Statement statement,Connection connection) {
		fermetureResultset( resultset);
		fermetureStatement( statement);
		fermetureConnection( connection);
	}
	
	
	/*
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

	/*
	 * Les méthodes implémentées
	 */
	
	@Override
	public Computer addCommputer(Computer computer) {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, DAORequetes.ADD_COMPUTER, true, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(),computer.getCompany_id() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOExceptions( "Échec de la création du computer, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du l'objet computer */
	            computer.setId(valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DAOExceptions( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOExceptions( e );
	    } finally {
	        fermeture( valeursAutoGenerees, preparedStatement, connexion );
	    }

		return computer;
	}

	@Override
	public ArrayList<Computer> getComputerList() {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Computer>listeComputer=new ArrayList<Computer>(); 
		Computer computer;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, DAORequetes.GET_List_COMPUTER, false);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
				computer = ComputerMapper.mapComputer(resultSet);
				listeComputer.add(computer);
	        }
	    } catch ( SQLException e ) {
	       e.printStackTrace();
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }

	    return listeComputer;
	}

	@Override
	public Computer getComputer(Long computerId) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Computer computer = null;

		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, DAORequetes.GET_COMPUTER_BY_ID, false, computerId );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
				computer = ComputerMapper.mapComputer(resultSet);
	        }
	        else {
	        	System.out.println("ce computer n'existe pas!");
	        }
	    } catch ( SQLException e ) {
	       e.printStackTrace();
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
		
	    return computer;
	}
	
	@Override
	public Computer deleteComputer(Long computerId) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Computer computer = null;
	    boolean estSupprime=false;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, DAORequetes.GET_COMPUTER_BY_ID, false, computerId );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
				computer = ComputerMapper.mapComputer(resultSet);
				preparedStatement = initialiserRequetePreparee( connexion, DAORequetes.DELETE_COMPUTER, false, computerId );
		        preparedStatement.executeUpdate();
		        estSupprime=true;
	        }
	    } catch ( SQLException e ) {
	    	e.printStackTrace();
	     
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
		if(estSupprime) {
			return computer;
		}
		return null;
	}
	@Override
	public Computer updateComputer(Object... object) {
		// TODO Auto-generated method stub
		return null;
	}

	



}
