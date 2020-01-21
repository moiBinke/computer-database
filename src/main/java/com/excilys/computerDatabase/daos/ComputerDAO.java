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
	public static final String GET_List_COMPUTER= "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id";
	public static final String DELETE_COMPUTER= "DELETE FROM computer WHERE id=?";
	public static final String UPDATE_COMPUTER_NAME= "UPDATE computer SET name=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_INTRODUCED_DATE = "UPDATE computer SET introduced=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_DISCONTINUED_DATE = "UPDATE computer SET discontinued=? WHERE id=?;";
	public static final String UPDATE_COMPUTER_COMPANY_ID = "UPDATE computer SET company_id=? WHERE id=?;";
	public static final String GET_PAGE_COMPUTER = "SELECT computer.id as computer_id, computer.name as computer_name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company on company.id=computer.company_id LIMIT ?, ?";
	
	
	/**
	 * Variables pour les fichiers de creations des bases de données tests
	 */
	public static  String fichierCreationTableTesT="src/test/resources/schema-creation.sql" ;
	public static  String fichierInsertTable="src/test/resources/initialisation-table.sql" ;	
	
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
				Logging.afficherMessage("Error when trying to close ResultSet Object");
			}
		}
	}
	/* fermeture du resultset */
	public static void fermetureStatement(Statement statement) {
		if(statement!=null) {
			try {
				statement.close();
			}catch(SQLException sqlExcept) {
				Logging.afficherMessage("Error when trying to close Statement Object");
			}
		}
	}
	/* fermeture de connexion */
	public static void fermetureConnection(Connection connection) {
		if(connection!=null) {
			try {
				connection.close();
			}catch(SQLException sqlExcept) {
				Logging.afficherMessage("Error when trying to close Connection Object");
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
	        connexion = daoFactory.getConnexion();
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
	        } else {
	        	Logging.afficherMessage("Error when trying to add new Computer Object");
	            throw new DAOExceptions( );
	        }
	    } catch ( SQLException e ) {
	    	Logging.afficherMessage("Error when trying to add new Computer Object");
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
        	Logging.afficherMessage("Resquest of all Computer list is succed");
	    } catch ( SQLException e ) {
        	Logging.afficherMessage("Error when trying to select all Computer Object in database");
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
	        	Logging.afficherMessage("a computer is selected from database");
	        }
	        else {
	        	System.out.println("ce computer n'existe pas!");
	        }
	    } catch ( SQLException e ) {
        	Logging.afficherMessage("Error when trying to get Computer by its Id");
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
        	Logging.afficherMessage("A computer with id= "+computerId+" is deleted");
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

	
	
	/**
	 * Les méthodes utiles pour les tests
	 */
	
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
