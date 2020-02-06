package com.excilys.computerDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.excilys.computerDatabase.daos.CompanyDAO;
import com.excilys.computerDatabase.daos.CompanyDAO;
import com.excilys.computerDatabase.daos.ComputerDAO;
import com.excilys.computerDatabase.daos.DaoFactory;
import com.excilys.computerDatabase.exceptions.DAOConfigurationException;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.services.CompanyServices;
import com.excilys.computerDatabase.services.ComputerServices;
import com.excilys.computerDatabase.util.Pages;

/**
 * <h1>Projet computer Data Base</h1>
 * Classe Main pour executer le projet.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */

public class Programme{
	public static int menu()throws IOException, InterruptedException {
      int Choix=0; //efface l'écran

         	
         	
            System.out.println("\n**************************************************************Computer Data Base**********************************************************************************\n\n");

            System.out.println("\n               *************************************************COULIBALY ISSA*************************************************************************\n");
            System.out.println("\n                                       *********************Stage-Excilys-janvier 2020*************************\n");
            System.out.println("                                                  *************************************");
            System.out.println("                                                  *                                   *");
            System.out.println("                                                  *           Menu Principal          *");
            System.out.println("                                                  *                                   *");
            System.out.println("                                                  *************************************");
            System.out.println("\n                                                      1-  Add  a computer");
            System.out.println("\n                                                      21- Update Computer Name ");
            System.out.println("\n                                                      22- Update Computer introduced date");
            System.out.println("\n                                                      23- Update Computer discontinued date");
            System.out.println("\n                                                      24- Update Computer company_id");
            System.out.println("\n                                                      3-  Select a computer By Id");
            System.out.println("\n                                                      41- Display all computers");
            System.out.println("\n                                                      42- Display all computers by page");
            System.out.println("\n                                                      43- paginer");
            System.out.println("\n                                                      5-  Delete computer");
            System.out.println("\n                                                      6-  Display all companies ");
            System.out.println("\n                                                      7-  Select a company by id ");
            System.out.println("\n                                                      71-  Delete Company by id ");
            System.out.println("\n                                                      8-  Quitter\n");
            System.out.println("\n\n\n\nSaisissez votre Choix :");
            System.out.print( "Enter votre choix en tapant le numéro correspondant: \n" );
            Scanner lecteur = new Scanner( System.in );
            Choix=lecteur.nextInt();
            return Choix;

    }
	/**
	 * La methode de conversion d'un string en date TimeStamp
	 * @param dateString: qui est un string
	 * @return timestamp qui  est de type Timestamp
	 * @exception ParseException
	 */
	@Deprecated
	public static Timestamp convertToTimeStamp(String dateString) throws ParseException {
        Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(dateString);  
        Timestamp timeStamp=new Timestamp(date1.getTime()); 
        System.out.println(timeStamp);
	    return timeStamp;
	}
	public static LocalDate convertToLocalDateViaSqlDate(String dateString) throws ParseException {
        Date dateToConvert=new SimpleDateFormat("dd-MM-yyyy").parse(dateString);  
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}
	/**
	 * La methode de conversion d'un string en date TimeStamp
	 * @param dateString: qui est un string
	 * @return timestamp qui  est de type Timestamp
	 * @exception ParseException
	 */
	
	/**
	 * La methode de conversion d'un string en date TimeStamp
	 * @exception InterruptedException
	 * @exception ParseException
	 * @exception ParseException
	 * @param args non utilisés.
	 * @return pas de retour.
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		/**
		 * Appel des 3 singletons:
		 */
		DaoFactory daoFactory=DaoFactory.getInstance();
		ComputerDAO computerDAO=ComputerDAO.getInstance(daoFactory);
		CompanyDAO companyDAO=CompanyDAO.getInstance(daoFactory);
		CompanyServices companyServices=CompanyServices.getInstance();
		
		Scanner lecteur = new Scanner( System.in );
		int choix=0;
		String orderByAny="any";
		
		while(true) {
			choix=menu();
			switch(choix) {
			case 1:
				System.out.println("Entrer le nom du nouveau ordinateur");
				String computerName=lecteur.next();
				System.out.println("Enter le id du company");
				Long idCompany=lecteur.nextLong();
			    System.out.println("Enter la date introduced sous le format: dd-MM-yyyy");
			    String IntroducedDateString=lecteur.next();
			    System.out.println("Enter la date discontinued sous le format: dd-MM-yyyy");
			    String discontinuedDateString=lecteur.next();
			    Company company=new Company(idCompany);
			    Computer newComputer=new Computer.ComputerBuilder(computerName)
			    						.initializeWithIntroducedDate(convertToLocalDateViaSqlDate(IntroducedDateString))
			    						.initializeWithDiscontinuedDate(convertToLocalDateViaSqlDate(discontinuedDateString))
			    						.initializeWithCompany(company)
			    						.build();
			    System.out.println(computerDAO.addCommputer(newComputer));
			    System.out.println("Taper sur n'importe quel chiffre puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
			case 21:
				Long computerToUpdateNameId;
				System.out.println("Entrer id du computer");
				computerToUpdateNameId=lecteur.nextLong();
				System.out.println("Entrer le nouveau nom du computer");
				String name=lecteur.next();
				Optional<Computer> ComputerUpdated=computerDAO.updateComputerName(computerToUpdateNameId, name);
				System.out.println(ComputerUpdated);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
			case 22:
				Long computerToUpdateIntroducedDateId;
				System.out.println("Entrer id du computer");
				computerToUpdateIntroducedDateId=lecteur.nextLong();
				System.out.println("Enter la nouvelle date introduced sous le format: dd-MM-yyyy");
				String newIntroducedDateString=lecteur.next();
			    LocalDate newIntroducedDate= convertToLocalDateViaSqlDate(newIntroducedDateString);
				Optional<Computer> computerToUpdateIntroducedDate=computerDAO.updateComputerIntroducedDate(computerToUpdateIntroducedDateId,newIntroducedDate);
				System.out.println(computerToUpdateIntroducedDate);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
			case 23:
				Long computerToUpdateDiscontinuedDateId;
				System.out.println("Entrer id du computer");
				computerToUpdateDiscontinuedDateId=lecteur.nextLong();
				System.out.println("Entrer la nouvelle date discontinued sous le format: dd-MM-yyyy");
				String newDiscontinuedDateString=lecteur.next();
			    LocalDate newDiscontinuedDate= convertToLocalDateViaSqlDate(newDiscontinuedDateString);
				Optional<Computer> computerToDiscontinuedDate=computerDAO.updateComputerDiscontinuedDate(computerToUpdateDiscontinuedDateId,newDiscontinuedDate);
				System.out.println(computerToDiscontinuedDate);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
			case 24:
				Long computerToUpdateCompanyId;
				Long newCompanyId;
				System.out.println("Entrer id du computer");
				computerToUpdateCompanyId=lecteur.nextLong();
				System.out.println("Entrer l'id de la nouvelle company ");
				 newCompanyId=lecteur.nextLong();
				Optional<Computer> computerUpdatedCompany=computerDAO.updateComputerCompany(computerToUpdateCompanyId,newCompanyId);
				System.out.println(computerUpdatedCompany);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 3:
				Long computerId;
				System.out.println("Entrer id du computer");
				computerId=lecteur.nextLong();
				Optional<Computer> Lookcomputer=computerDAO.getComputer(computerId);
				System.out.println(Lookcomputer);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
			case 41:
				ArrayList<Computer>listComputer=new ArrayList<Computer>();
				listComputer=computerDAO.getComputerList();
				System.out.println(listComputer);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 42:
				int taillePage;
				int ligneDebut;
				Pages computerPage=new Pages(computerDAO);
				ArrayList<Computer>listComputerPage=new ArrayList<Computer>();
				System.out.println("Entrer le nombre de lignes dans une page");
				taillePage=lecteur.nextInt();
				System.out.println("Entrer l'index de la ligne initiale");
				ligneDebut=lecteur.nextInt();
				listComputerPage=computerPage.getPage(ligneDebut,taillePage,orderByAny);
				System.out.println(listComputerPage);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
			case 43:
				Pages computerPaginer=new Pages(computerDAO);
				ArrayList<Computer>page=new ArrayList<Computer>();
				System.out.println("entre la taille des page");
				computerPaginer.setTaillePage(lecteur.nextInt());
				String controle;
				while(!computerPaginer.getStop()) {
		            System.out.println("                                         Pour manipuler cette liste il faut utiliser les symboles suivants                                                \n");
					System.out.println("                                                                     '<':Previous                                                                                                          \n");
					System.out.println("                                                                     '>':Next                                                                                                         \n");
					System.out.println("                                                                     '!':Stop                                                                                                          \n");
					controle=lecteur.next();
					if(controle.equals("!")) {
						computerPaginer.setStop(true);
					}
					else {
						page=computerPaginer.pagination(controle,orderByAny);
					    System.out.println("\n               ****************************************************Page "+computerPaginer.getIteration()+"*************************************************************************               \n");
					    System.out.println("                                                                     < Page"+computerPaginer.getIteration()+" >                                                                                                          \n");
						System.out.println(page);
					}
				}
				break;
			case 5:
				Long computerToDeleteId;
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				computerToDeleteId=lecteur.nextLong();
				Optional<Computer> computerDeleted=computerDAO.deleteComputer(computerToDeleteId);
				System.out.println(computerDeleted);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
			case 6:
				ArrayList<Company>listCompany=new ArrayList<Company>();
				listCompany=companyDAO.getCompanyList();
				System.out.println(listCompany);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
			case 7:
				Long companyId;
				System.out.println("Entrer id du company");
				companyId=lecteur.nextLong();
				Optional<Company> Lookcompany=companyDAO.getCompanyById(companyId);
				System.out.println(Lookcompany);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 8:
				System.out.println("******************************************Fin Programme***********************************************");
				return ;
			case 71:
				Long companyToDeleteId;
				System.out.println("Entrer id du company");
				companyToDeleteId=lecteur.nextLong();
				companyServices.delete(companyToDeleteId);
			default:
				System.out.println("Ce choix n'existe pas!");
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
				break;
				
			}

		}
		
	}
}