package com.excilys.computerDatabase.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.dto.CompanyDTO;
import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.exceptions.Logging;
import com.excilys.computerDatabase.exceptions.ComputerValidatorException;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.services.CompanyServices;
import com.excilys.computerDatabase.services.ComputerServices;
import com.excilys.computerDatabase.validators.ComputerValidator;

/**
 * Servlet implementation class CompanyController
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private CompanyServices companyServices;
       private ComputerServices computerServices;
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		companyServices=CompanyServices.getInstance();
		computerServices=ComputerServices.getInstance();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				response.getWriter().append("Served at: ").append(request.getContextPath());
		ArrayList<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();
		ArrayList<Company> companyList=new ArrayList<Company>();
		companyList=companyServices.findALl();
		companyList.stream()
				   .forEach(company->companyDtoList.add(
						   CompanyMapper.mapFromCompanyToCompanyDto(company)));
		request.setAttribute("companies", companyDtoList);
		request.getRequestDispatcher("views/addComputer.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompanyDTO companyDTO=new CompanyDTO(Long.parseLong(request.getParameter("companyId")));
		ComputerDTO computerDTO=new ComputerDTO(request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),companyDTO);
		
		try {
			StringBuilder erreur=new StringBuilder();
			Computer newComputer =ComputerMapper.convertFromComputerDtoToComputer(computerDTO);
			ComputerValidator computerValidator=new ComputerValidator();
			
			try {
				computerValidator.validateComputer(newComputer);
				try {
					newComputer=computerServices.create(newComputer);
					computerDTO=ComputerMapper.convertFromComputerToComputerDTO(newComputer);
					request.setAttribute("newComputer",computerDTO );
				} catch (ParseException e) {
					Logging.afficherMessage("Cannot convert computer date type: "+newComputer.getDiscontinued());
					e.printStackTrace();
				}
			}catch(ComputerValidatorException.DateValidator dateValidator) {
				erreur.append("Vérifier que la date discontinued est après introduced");
				request.setAttribute("erreur", erreur);
				request.setAttribute("failedComputer", newComputer);
				
			}catch(ComputerValidatorException.NameValidator nameValidator) {
				erreur.append("\n Vérifier que le nom existe et n'est pas vide ");
				request.setAttribute("erreur", erreur);
				request.setAttribute("failedComputer", newComputer);
			}
			finally {
				doGet(request, response);
			}
			
		} catch (ParseException e1) {
			Logging.afficherMessageError("Cannot convert From ComputerDto To Computer");
			e1.printStackTrace();
		}
		
	}

}
