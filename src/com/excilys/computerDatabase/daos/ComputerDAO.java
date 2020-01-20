package com.excilys.computerDatabase.daos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computerDatabase.exceptions.DAOExceptions;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Computer;
/**
 *DAO: implementation de ComputerDAO.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-16 
 */
public class ComputerDAO {
	
	/**
	 * Les requetes utilisées pour computer
	 */
	
	public static final String ADD_COMPUTER= "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	public static final String GET_COMPUTER_BY_ID= "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id WHERE computer.id=?";
	public static final String GET_List_COMPUTER= "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id";
	public static final String DELETE_COMPUTER= "DELETE FROM computer WHERE id=?";
	public static final String UPDATE_COMPUTER_NAME= "UPDATE computer SET name=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_INTRODUCED_DATE = "UPDATE computer SET introduced=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_DISCONTINUED_DATE = "UPDATE computer SET discontinued=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_COMPANY_ID = "UPDATE computer SET company_id=? WHERE id=?;";
	public static final String GET_PAGE_COMPUTER = "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id LIMIT ?, ?";
	
	/**
	 * Construction du singleton:
	 */
	private DaoFactory daoFactory;
	
	private static ComputerDAO computerDAO;
	
	private ComputerDAO() {
		super();
	}

	private ComputerDAO(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	public static ComputerDAO getInstance(DaoFactory daoFactory) {
		if(computerDAO==null) {
			return new ComputerDAO(daoFactory);
		}
	    return computerDAO; 
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
	
	
	/**
	 * Initialise la requête préparée basée sur la connexion passée en argument,
	 * avec la requête SQL et les objets donnés.
	 * @param connexion
	 * @param requeteSql
	 * @param returnGeneratedKeys
	 * @param objets
	 * @return
	 * @throws SQLException
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
	
	 
	public Computer addCommputer(Computer computer) {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, ADD_COMPUTER, true, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(),computer.getCompany().getId() );
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

	 
	public ArrayList<Computer> getComputerList() {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Computer>listeComputer=new ArrayList<Computer>(); 
		Computer computer;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_List_COMPUTER, false);
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

	 
	public Optional<Computer> getComputer(Long computerId) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Optional<Computer> computer=null;

		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_BY_ID, false, computerId );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
				computer = Optional.ofNullable(ComputerMapper.mapComputer(resultSet));
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
	
	 
	public Optional<Computer> deleteComputer(Long computerId) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Computer computer = null;
	    boolean estSupprime=false;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_BY_ID, false, computerId );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
				computer = ComputerMapper.mapComputer(resultSet);
				preparedStatement = initialiserRequetePreparee( connexion, DELETE_COMPUTER, false, computerId );
		        preparedStatement.executeUpdate();
		        estSupprime=true;
	        }
	    } catch ( SQLException e ) {
	    	e.printStackTrace();
	     
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
		if(estSupprime) {
			return Optional.of(computer);
		}
		return   Optional.empty();
	}
	 
	public Optional<Computer> updateComputerName(Long idComputer,String name) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Optional<Computer> computer = null;
	    int estMisAjour=0;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, UPDATE_COMPUTER_NAME, false,name, idComputer  );
	        estMisAjour=preparedStatement.executeUpdate();
	       System.out.println(estMisAjour);
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (estMisAjour!=0) {
				computer = getComputer(idComputer);
				
		        
	        }
	    } catch ( SQLException e ) {
	    	e.printStackTrace();
	     
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
		if(estMisAjour!=0) {
			return computer;
		}
		return Optional.empty();
	}

	 
	public Optional<Computer> updateComputerIntroducedDate(Long computerToUpdateIntroducedDateId, LocalDate newIntroducedDate) {
		Optional<Computer> computer = getComputer(computerToUpdateIntroducedDateId);
		computer.orElse(null).setIntroduced(newIntroducedDate);
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    int estMisAjour=0;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, UPDATE_COMPUTER_INTRODUCED_DATE, false,computer.orElse(null).getIntroduced(), computer.orElse(null).getId()  );
	        estMisAjour=preparedStatement.executeUpdate();
	       System.out.println(estMisAjour);
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (estMisAjour!=0) {
				computer = getComputer(computerToUpdateIntroducedDateId);
				
		        
	        }
	    } catch ( SQLException e ) {
	    	e.printStackTrace();
	     
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
		if(estMisAjour!=0) {
			return computer;
		}
		return Optional.empty();
	}

	 
	public Optional<Computer> updateComputerDiscontinuedDate(Long computerToUpdateDiscontinuedDateId,
		LocalDate newDiscontinuedDate) {
		Optional<Computer> computer = getComputer(computerToUpdateDiscontinuedDateId);
		computer.orElse(null).setDiscontinued(newDiscontinuedDate);
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    int estMisAjour=0;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, UPDATE_COMPUTER_DISCONTINUED_DATE, false,computer.orElse(null).getDiscontinued(), computer.orElse(null).getId()  );
	        estMisAjour=preparedStatement.executeUpdate();
	       System.out.println(estMisAjour);
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (estMisAjour!=0) {
				computer = getComputer(computerToUpdateDiscontinuedDateId);
				
		        
	        }
	    } catch ( SQLException e ) {
	    	e.printStackTrace();
	     
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
		if(estMisAjour!=0) {
			return computer;
		}
		return Optional.empty();
	}

	 
	public Optional<Computer> updateComputerCompany(Long computerToUpdateCompanyId, Long newCompanyId) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Optional<Computer> computer = null;
	    int estMisAjour=0;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, UPDATE_COMPUTER_COMPANY_ID, false,newCompanyId, computerToUpdateCompanyId  );
	        estMisAjour=preparedStatement.executeUpdate();
	       System.out.println(estMisAjour);
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (estMisAjour!=0) {
				computer = getComputer(computerToUpdateCompanyId);
				
		        
	        }
	    } catch ( SQLException e ) {
	    	e.printStackTrace();
	     
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }
		if(estMisAjour!=0) {
			return computer;
		}
		return Optional.empty();
	}

	 
	public ArrayList<Computer> getComputerListPage(int ligneDebut, int  taillePage ) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Computer>listeComputer=new ArrayList<Computer>(); 
		Computer computer;
		try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnexion();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_PAGE_COMPUTER,false,ligneDebut,taillePage);
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

	



}
