package com.excilys.computerDatabase.daos;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.excilys.computerDatabase.exceptions.DAOConfigurationException;
import com.excilys.computerDatabase.exceptions.DAOExceptions;
import com.excilys.computerDatabase.exceptions.Logging;
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
	public static final String GET_List_COMPUTER= "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY computer.id";
	public static final String DELETE_COMPUTER= "DELETE FROM computer WHERE id=?";
	public static final String UPDATE_COMPUTER_NAME= "UPDATE computer SET name=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_INTRODUCED_DATE = "UPDATE computer SET introduced=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_DISCONTINUED_DATE = "UPDATE computer SET discontinued=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_COMPANY_ID = "UPDATE computer SET company_id=? WHERE id=?;";
	public static final String GET_PAGE_COMPUTER = "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id LIMIT ?, ?";
	public static final String COUNT = "SELECT COUNT(*) FROM computer;";
	public static final String DELETE_COMPUTER_WITH_COMPANY_ID="DELETE FROM computer WHERE company_id=?;";
	public static final String GET_COMPUTER_ORDER_BY_NAME_ASC="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY computer_name ASC LIMIT ?, ? ;";
	public static final String GET_COMPUTER_ORDER_BY_NAME_DESC="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY computer_name ASC LIMIT ?, ? ;";
	public static final String GET_COMPUTER_ORDER_BY_INTRODUCED_ASC="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY introduced ASC LIMIT ?, ? ;";
	public static final String GET_COMPUTER_ORDER_BY_INTRODUCED_DESC="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY introduced DESC LIMIT ?, ? ;";
	public static final String GET_COMPUTER_ORDER_BY_DISCONTINUED_ASC="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY discontinued ASC LIMIT ?, ? ;";
	public static final String GET_COMPUTER_ORDER_BY_DISCONTINUED_DESC="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY discontinued DESC LIMIT ?, ? ;";
	public static final String GET_COMPUTER_ORDER_BY_COMPANY_NAME_ASC="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY company_name ASC LIMIT ?, ? ;";
	public static final String GET_COMPUTER_ORDER_BY_COMPANY_NAME_DESC="SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id ORDER BY company_name DESC LIMIT ?, ? ;";
	public static final String SEARCH="SELECT computer.id as computer_id, computer.name as computer_name , computer.introduced, computer.discontinued, computer.company_id, company.name as company_name  FROM computer LEFT JOIN company on company.id=computer.company_id WHERE LOWER(computer.name) LIKE ? OR  LOWER(company.name) LIKE ? OR introduced LIKE ? OR discontinued LIKE ?;";

	/**
	 * Construction du singleton:
	 */
	//private DaoFactory daoFactory;
	
	private static ComputerDAO computerDAO;
	
	private ComputerDAO() {
		super();
	}
/**
 * En commentaire l'ancien Connexion avant hikari
 * @param daoFactory
 * @return
 */
//	private ComputerDAO(DaoFactory daoFactory) {
//		super();
//		this.daoFactory = daoFactory;
//	}
	/**
	 * En commentaire l'ancienne getInstance du DAO
	 * @param daoFactory
	 * @return
	 */
//	public static ComputerDAO getInstance(DaoFactory daoFactory) {
//		if(computerDAO==null) {
//			return new ComputerDAO(daoFactory);
//		}
//	    return computerDAO; 
//	}
	public static ComputerDAO getInstance(DaoFactory daoFactory) {
		if(computerDAO==null) {
			return new ComputerDAO();
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
				Logging.afficherMessageError("Error when trying to close ResultSet Object");
			}
		}
	}
	/* fermeture du resultset */
	public static void fermetureStatement(Statement statement) {
		if(statement!=null) {
			try {
				statement.close();
			}catch(SQLException sqlExcept) {
				Logging.afficherMessageError("Error when trying to close Statement Object");
			}
		}
	}
	/* fermeture de connexion */
	public static void fermetureConnection(Connection connection) {
		if(connection!=null) {
			try {
				connection.close();
			}catch(SQLException sqlExcept) {
				Logging.afficherMessageError("Error when trying to close Connection Object");
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
	
	/*
	 * H2 accepte les timestamp donc on doit convertir en timestamp
	 */
	private Timestamp convertToTimeStamp(LocalDate localDate) throws ParseException {
        Date date=java.sql.Date.valueOf(localDate); 
        Timestamp timeStamp=new Timestamp(date.getTime()); 
        System.out.println(timeStamp);
	    return timeStamp;
	}
	 
	public Computer addCommputer(Computer computer) throws ParseException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	    	/*
	    	 * J'ai fait ceci ppour echaper l'erreurde Date avec H2 qui prend des LocalDateTime
	    	 */
	    	 int hour=java.time.LocalTime.now().getHour();
		     int minute=java.time.LocalTime.now().getMinute();
		     int second=java.time.LocalTime.now().getSecond();
	        /* Récupération d'une connexion depuis la Factory */
		     /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
	        preparedStatement = initialiserRequetePreparee( connexion, ADD_COMPUTER, true, computer.getName(), convertToTimeStamp(computer.getIntroduced()), convertToTimeStamp(computer.getDiscontinued()),computer.getCompany().getId() );
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
	            Logging.afficherMessageDebug("Computer created successfuly");
	        } else {
	        	Logging.afficherMessageError("Error when trying to add new Computer Object");
	            throw new DAOExceptions( );
	        }
	    } catch ( SQLException e ) {
	    	Logging.afficherMessageError("Error when trying to add new Computer Object");
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
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_List_COMPUTER, false);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
				computer = ComputerMapper.mapComputer(resultSet);
				listeComputer.add(computer);
	        }
        	Logging.afficherMessageDebug("computer list got successfuly");
	    } catch ( SQLException e ) {
        	Logging.afficherMessageError("Error when trying to select all Computer Object in database");
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
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
	        preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_BY_ID, false, computerId );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
				computer = Optional.ofNullable(ComputerMapper.mapComputer(resultSet));
	        	Logging.afficherMessageDebug("a computer is selected from database");
	        }
	        else {
	        	Logging.afficherMessageError("Cannot get computer with id: "+computerId);;
	        }
	    } catch ( SQLException e ) {
        	Logging.afficherMessageError("Error when trying to get Computer by its Id");
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
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
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
        	Logging.afficherMessageDebug("A computer with id= "+computerId+" is deleted");
			return Optional.of(computer);
		}
		Logging.afficherMessageError("Cannot get computer with id: "+computerId);
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
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
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
			Logging.afficherMessage("A computer with id= "+idComputer+" come to be updated");
			return computer;
		}
		Logging.afficherMessage("error when updating computer with id= "+idComputer);
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
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
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
			Logging.afficherMessageDebug("A computer with id= "+computerToUpdateIntroducedDateId+" come to be updated");
			return computer;
		}
		Logging.afficherMessageError("Cannot update introduced date of computer with id= "+computerToUpdateIntroducedDateId+" ");
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
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
	        preparedStatement = initialiserRequetePreparee( connexion, UPDATE_COMPUTER_DISCONTINUED_DATE, false,computer.orElse(null).getDiscontinued(), computer.orElse(null).getId()  );
	        estMisAjour=preparedStatement.executeUpdate();
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
			Logging.afficherMessageDebug("A computer with id= "+computerToUpdateDiscontinuedDateId+" come to be updated");
			return computer;
		}
		Logging.afficherMessageError("Cannot update introduced date of computer with id= "+computerToUpdateDiscontinuedDateId+" ");
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
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
	        preparedStatement = initialiserRequetePreparee( connexion, UPDATE_COMPUTER_COMPANY_ID, false,newCompanyId, computerToUpdateCompanyId  );
	        estMisAjour=preparedStatement.executeUpdate();
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
			Logging.afficherMessageDebug("Computer "+computerToUpdateCompanyId+" company_id come to be updated ");
			return computer;
		}
		Logging.afficherMessageError("Cannot update Company for Computer with id "+computerToUpdateCompanyId+" ");
		return Optional.empty();
	}

	 
	public ArrayList<Computer> getComputerListPage(int ligneDebut, int  taillePage, String orderBy ) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Computer>listeComputer=new ArrayList<Computer>(); 
		Computer computer;
		try {
	        /* Récupération d'une connexion depuis la Factory */
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
			switch(orderBy) {
			case "any":
				preparedStatement = initialiserRequetePreparee( connexion, GET_PAGE_COMPUTER,false,ligneDebut,taillePage);
				break;
			case "name":
				preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_ORDER_BY_NAME_ASC,false,ligneDebut,taillePage);
				break;
			case "name-alt":
				preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_ORDER_BY_NAME_DESC,false,ligneDebut,taillePage);
				break;
			case "introduced":
				preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_ORDER_BY_INTRODUCED_ASC,false,ligneDebut,taillePage);
				break;
			case "introduced-alt":
				preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_ORDER_BY_INTRODUCED_DESC,false,ligneDebut,taillePage);
				break;
			case "discontinued":
				preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_ORDER_BY_DISCONTINUED_ASC,false,ligneDebut,taillePage);
				break;
			case "discontinued-alt":
				preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_ORDER_BY_DISCONTINUED_DESC,false,ligneDebut,taillePage);
				break;
			case "company-name":
				preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_ORDER_BY_COMPANY_NAME_ASC,false,ligneDebut,taillePage);
				break;
			case "company-name-alt":
				preparedStatement = initialiserRequetePreparee( connexion, GET_COMPUTER_ORDER_BY_COMPANY_NAME_DESC,false,ligneDebut,taillePage);
				break;
			default:
		}
	        
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
				computer = ComputerMapper.mapComputer(resultSet);
				listeComputer.add(computer);
	        }
	        Logging.afficherMessageDebug("computer list got successfuly ");
	    } catch ( SQLException e ) {
	       Logging.afficherMessageError("Cannot get computer list ");
	       e.printStackTrace();
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }

	    return listeComputer;
	}

	public int size() {
		
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    int size=0;
		try {
	        /* Récupération d'une connexion depuis la Factory */
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
	        preparedStatement = initialiserRequetePreparee( connexion, COUNT,false);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
	        	size=resultSet.getInt(1);
	        }
	    } catch ( SQLException e ) {
	       Logging.afficherMessageError("Cannot get computer list size");
	       e.printStackTrace();
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }

	    return size;
	}
	public Computer updateComputer(Computer computerToUpdate) {
		updateComputerName(computerToUpdate.getId(),computerToUpdate.getName()).get();
		updateComputerIntroducedDate(computerToUpdate.getId(),computerToUpdate.getIntroduced()).get();
		updateComputerDiscontinuedDate(computerToUpdate.getId(),computerToUpdate.getDiscontinued()).get();
		computerToUpdate=updateComputerCompany(computerToUpdate.getId(),computerToUpdate.getCompany().getId()).get();
		return computerToUpdate;
	}
	public ArrayList<Computer> search(String search) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Computer>listeComputer=new ArrayList<Computer>(); 
		Computer computer;
		try {
			 /**
		      * En commentaire l'ancienne connection AVANT hIKARI
		      */
		     //  connexion = daoFactory.getConnexion();
			connexion=(Connection) DaoFactoryHikary.getInstance().getConnection();
			search="%"+search.toLowerCase()+"%";
	        preparedStatement = initialiserRequetePreparee( connexion, SEARCH, false,search,search,search,search);
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while ( resultSet.next() ) {
				computer = ComputerMapper.mapComputer(resultSet);
				listeComputer.add(computer);
	        }
        	Logging.afficherMessageDebug("computer list got successfuly");
	    } catch ( SQLException e ) {
        	Logging.afficherMessageError("Error when trying to search Computer Object in database");
	       e.printStackTrace();
	    } finally {
	        fermeture( resultSet, preparedStatement, connexion );
	    }

	    return listeComputer;
	}

}
