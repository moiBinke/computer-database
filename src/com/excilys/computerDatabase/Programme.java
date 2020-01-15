package com.excilys.computerDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.excilys.computerDatabase.daos.CompanyDAO;
import com.excilys.computerDatabase.daos.CompanyDAOImpl;
import com.excilys.computerDatabase.daos.ComputerDAO;
import com.excilys.computerDatabase.daos.ComputerDAOImpl;
import com.excilys.computerDatabase.daos.DaoFactory;
import com.excilys.computerDatabase.exceptions.DAOConfigurationException;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;

/*
 * <h1>Projet computer Data Base</h1>
 * Classe Main pour executer le projet.
 *@author COULIBALY Issa
 *@version 1.0
 *@since   2020-01-14 
 */
//public class Programme {
//
//	public static void main(String[] args) throws DAOConfigurationException, ClassNotFoundException {
//		// TODO Auto-generated method stub
//		DaoFactory daoFactory=DaoFactory.getInstance();
//		
//		ComputerDAO computerDAO=new ComputerDAOImpl(daoFactory);
//		Long computerId=12L;
//		Computer computer=computerDAO.getComputer(computerId);
//		System.out.println(computer.getName());
//	}
//
//}

public class Programme{
	public static int menu()throws IOException, InterruptedException {
      int Choix=0; //efface l'écran

         	
         	
            System.out.println("\n**************************************************************Computer Data Base**********************************************************************************\n\n");

            System.out.println("\n               ****************************************************COULIBALY*************************************************************************\n");
            System.out.println("\n                                       *********************Stage-Excilys-janvier 2020*************************\n");
            System.out.println("                                                  *************************************");
            System.out.println("                                                  *                                   *");
            System.out.println("                                                  *           Menu Principal          *");
            System.out.println("                                                  *                                   *");
            System.out.println("                                                  *************************************");
            System.out.println("\n                                                      1- Add  a computer");
            System.out.println("\n                                                      2- Update Computer Name by id");
            System.out.println("\n                                                      3- select a computer by id");
            System.out.println("\n                                                      4- Display all computers");
            System.out.println("\n                                                      5- Delete computer");
            System.out.println("\n                                                      6- Display all companies ");
            System.out.println("\n                                                      7- Select a company by id ");
            System.out.println("\n                                                      8- Quitter\n");
            System.out.println("\n\n\n\n\n\n Saisissez votre Choix :");
            System.out.print( "Enter votre choix " );
            Scanner lecteur = new Scanner( System.in );
            Choix=lecteur.nextInt();
     
      return Choix;

    }
	public static Timestamp convertToTimeStamp(String dateString) throws ParseException {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        LocalDateTime formatLocalDateTime = LocalDateTime.parse(dateString, formatter);
//        Timestamp timeStamp = Timestamp.valueOf(formatLocalDateTime.toLocalDate().atStartOfDay());
        
        String sDate1="31-12-1998";  
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);  
        Timestamp timeStamp=new Timestamp(date1.getTime()); 
	    return timeStamp;
	}
	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		DaoFactory daoFactory=DaoFactory.getInstance();
		ComputerDAO computerDAO=new ComputerDAOImpl(daoFactory);

		CompanyDAO companyDAO=new CompanyDAOImpl(daoFactory);
		Scanner lecteur = new Scanner( System.in );
		int choix=0;
		while(choix!=8) {
			choix=menu();
			switch(choix) {
			case 1:
				Computer newComputer=new Computer();
				System.out.println("Entrer le nom du nouveau ordinateur");
				newComputer.setName(lecteur.next());
				System.out.println("Enter le id du company");
				newComputer.setCompany_id(lecteur.nextLong());
//			    System.out.println("Enter la date introduced");
//			    String dateStringIntroduced=lecteur.next();
//			    newComputer.setIntroduced(convertToTimeStamp(dateStringIntroduced));
//			    System.out.println("Enter la date discontinued");
//			    String dateStringDiscontinued=lecteur.next();
//			    newComputer.setDiscontinued(convertToTimeStamp(dateStringDiscontinued));
			    System.out.println(computerDAO.addCommputer(newComputer));
			    System.out.println("Taper sur n'importe quel chiffre puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 2:
//				Long computerToUpdate;
//				System.out.println("Entrer id du computer");
//				computerToUpdateId=lecteur.nextLong();
//				System.out.println("Entrer id du computer");
//				computerToUpdate=lecteur.nextLong();
//				Computer ComputerUpdated=computerDAO.updateComputer( name);
//				System.out.println(ComputerUpdated);
//				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
//				lecteur.nextInt();
				System.out.println("cette fonctionnalité est en cours de developpement. Il la reste 15 minutes");
			case 3:
				Long computerId;
				System.out.println("Entrer id du computer");
				computerId=lecteur.nextLong();
				Computer Lookcomputer=computerDAO.getComputer(computerId);
				System.out.println(Lookcomputer);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 4:
				ArrayList<Computer>listComputer=new ArrayList<Computer>();
				listComputer=computerDAO.getComputerList();
				System.out.println(listComputer);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 5:
				Long computerToDeleteId;
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				computerToDeleteId=lecteur.nextLong();
				Computer computerDeleted=computerDAO.deleteComputer(computerToDeleteId);
				System.out.println(computerDeleted);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 6:
				ArrayList<Company>listCompany=new ArrayList<Company>();
				listCompany=companyDAO.getCompanyList();
				System.out.println(listCompany);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 7:
				Long companyId;
				System.out.println("Entrer id du company");
				companyId=lecteur.nextLong();
				Company Lookcompany=companyDAO.getCompanyById(companyId);
				System.out.println(Lookcompany);
				System.out.println("taper 9  puis 'Entrer' pour revenir au menu");
				lecteur.nextInt();
			case 8:
				System.out.println("******************************************Fin Programme***********************************************");
				return ;
			default:
				System.out.println(" ce choix n'existe pas!");
				
			}
		}
		
	}
}