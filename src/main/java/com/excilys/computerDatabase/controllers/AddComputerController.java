package com.excilys.computerDatabase.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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

@Controller
public class AddComputerController {

		
		@Autowired
	    private CompanyServices companyServices;
		@Autowired
		private ComputerServices computerServices;
	    
		@GetMapping("addComputerPage") 
		public String doGet(ModelMap dataMap) throws ServletException, IOException {
			ArrayList<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();
			ArrayList<Company> companyList=new ArrayList<Company>();
			companyList=companyServices.findALl();
			companyList.stream()
					   .forEach(company->companyDtoList.add(
							   CompanyMapper.mapFromCompanyToCompanyDto(company)));
			dataMap.put("companies", companyDtoList);
			dataMap.put("failedComputer", new ComputerDTO());
			return "addComputer";
		}
		
		@PostMapping("addComputer")
		public void addComputer(@ModelAttribute("failedComputer") ComputerDTO computerDTO,ModelMap dataMap)  {
		
			try {
				StringBuilder erreur=new StringBuilder();
				Computer newComputer =ComputerMapper.convertFromComputerDtoToComputer(computerDTO);
				ComputerValidator computerValidator=new ComputerValidator();
				
				try {
					computerValidator.validateComputer(newComputer);
					try {
						newComputer=computerServices.create(newComputer);
						computerDTO=ComputerMapper.convertFromComputerToComputerDTO(newComputer);
						dataMap.put("newComputer",computerDTO );
					} catch (ParseException e) {
						Logging.afficherMessage("Cannot convert computer date type: "+newComputer.getDiscontinued());
						e.printStackTrace();
					}
				}catch(ComputerValidatorException.DateValidator dateValidator) {
					erreur.append("Vérifier que la date discontinued est après introduced");
					dataMap.put("erreur", erreur);
					dataMap.put("failedComputer", newComputer);
					
				}catch(ComputerValidatorException.NameValidator nameValidator) {
					erreur.append("\n Vérifier que le nom existe et n'est pas vide ");
					dataMap.put("erreur", erreur);
					dataMap.put("failedComputer", newComputer);
				}
				finally {
					//doGet(request, response);
				}
				
			} catch (ParseException e1) {
				Logging.afficherMessageError("Cannot convert From ComputerDto To Computer");
				e1.printStackTrace();
			}
			
		}
	}