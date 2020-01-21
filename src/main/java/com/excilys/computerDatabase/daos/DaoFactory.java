package com.excilys.computerDatabase.daos;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.excilys.computerDatabase.exceptions.DAOConfigurationException;
/*
 * Classe pour créer un factory de qui: -lit les informations de configuration. -Charge le driver, -fournit une connexion à la base de données
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
public class DaoFactory {

	public static final String FICHIER_PROPERTIES="dao.properties";
	public static final String PROPERTY_URL="url";
	public static final String PROPERTY_DRIVER="driver";
	public static final String PROPERTY_NOM_UTILISATEUR="nomUtilisateur";
	public static final String PROPERTY_MOT_DE_PASSE="motDePasse";
	
	public static final String PROPERTY_URL_H2="urlH2";
	public static final String PROPERTY_DRIVER_H2="driverH2";
	public static final String PROPERTY_NOM_UTILISATEUR_H2="nomUtilisateurH2";
	public static final String PROPERTY_MOT_DE_PASSE_H2="motDePasseH2";
	
	private String url;
	private String nomUtilisateur;
	private String motDePasse;
	private DaoFactory() {}
	/**
	   * Constructeur privé: POur permettre à ce qu'on ne crée pas une instance sans pourtant effectuer les traitements dans la methode getInstance
	   * 
	   * @param url qui est le url qui sera chargé depuis le fichier properties
	   * @param nomUtilisateur qui sera le nom
	   * @param motDePasse
	   */
	private  DaoFactory(String url, String nomUtilisateur, String motDePasse) {
		super();
		this.url = url;
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
	}
	
	/**
	   * FONCTION: POur permettre à ce qu'on ne crée pas une instance sans pourtant effectuer les traitements dans la methode getInstance
	   * 
	   * @param url qui est le url qui sera chargé depuis le fichier properties
	   * @param nomUtilisateur qui sera le nom
	   * @param motDePasse
	   */
	
	public static DaoFactory getInstance() throws DAOConfigurationException {
		DaoFactory instanceDao = null;
		Properties properties=new Properties();
		String driver;
		String url;
		String nomUtilisateur; 
		String motDePasse;
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties=classLoader.getResourceAsStream(FICHIER_PROPERTIES);
		if(fichierProperties==null) {
			throw new DAOConfigurationException("1-le fichier dao.properties qui contient les paramètres de connection est introuvable");
		}
		try {
			properties.load(fichierProperties);
			url=properties.getProperty(PROPERTY_URL);
			driver=properties.getProperty(PROPERTY_DRIVER);
			nomUtilisateur=properties.getProperty(PROPERTY_NOM_UTILISATEUR);
			motDePasse=properties.getProperty(PROPERTY_MOT_DE_PASSE);
			try {
				Class.forName(driver);
			    instanceDao =new DaoFactory(url,nomUtilisateur,motDePasse);
			}catch(ClassNotFoundException classNotFoundException){
				throw new DAOConfigurationException("le classpath n'a pas trouvé le driver");
			}
			
		}catch(IOException ioException) {
			throw new DAOConfigurationException("le fichier dao.properties qui contient les paramètres de connection est introuvable");
		}
		finally {
			return instanceDao;
		}
		
	}
	
	public static DaoFactory getInstanceH2() throws DAOConfigurationException {
		DaoFactory instanceDao = null;
		Properties properties=new Properties();
		String driver;
		String url;
		String nomUtilisateur; 
		String motDePasse;
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties=classLoader.getResourceAsStream(FICHIER_PROPERTIES);
		if(fichierProperties==null) {
			throw new DAOConfigurationException("1-le fichier dao.properties qui contient les paramètres de connection est introuvable");
		}
		try {
			properties.load(fichierProperties);
			url=properties.getProperty(PROPERTY_URL_H2);
			driver=properties.getProperty(PROPERTY_DRIVER_H2);
			nomUtilisateur=properties.getProperty(PROPERTY_NOM_UTILISATEUR_H2);
			motDePasse=properties.getProperty(PROPERTY_MOT_DE_PASSE_H2);
			try {
				Class.forName(driver);
			    instanceDao =new DaoFactory(url,nomUtilisateur,motDePasse);
			}catch(ClassNotFoundException classNotFoundException){
				throw new DAOConfigurationException("le classpath n'a pas trouvé le driver");
			}
			
		}catch(IOException ioException) {
			throw new DAOConfigurationException("le fichier dao.properties qui contient les paramètres de connection est introuvable");
		}
		finally {
			return instanceDao;
		}
		
	}
	
	public Connection getConnexion() throws SQLException{
		return DriverManager.getConnection(url,nomUtilisateur,motDePasse);
	}
	
	public ComputerDAO getComputerDao() {
		return ComputerDAO.getInstance(this);
	}
	
	public CompanyDAO getCompanyDao() {
		return CompanyDAO.getInstance(this);
	}
}
