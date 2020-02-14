
package com.excilys.computerDatabase.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;



import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.excilys.computerDatabase.dto.CompanyDTO;
import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.exceptions.Logging;
import com.excilys.computerDatabase.exceptions.ComputerValidatorException;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.services.CompanyServices;
import com.excilys.computerDatabase.services.ComputerServices;
import com.excilys.computerDatabase.validators.ComputerValidator;

/**
 * Servlet implementation class editComputerServlet
 */
@Controller
public class EditComputerController {
    private ComputerDTO computerDto;
    private CompanyServices companyServices;
	private ComputerServices computerServices;
	
	public EditComputerController(CompanyServices companyServices,ComputerServices computerServices) {
		this.companyServices=companyServices;
		this.computerServices=computerServices;
	}
    @GetMapping("editComputerPage")
	public String editComputerPage(@RequestParam(value="id")Long idComputer,ModelMap dataMap) {
	
		Computer computerToUpdate=computerServices.findById(idComputer).get();
		computerDto=ComputerMapper.convertFromComputerToComputerDTO(computerToUpdate);

		ArrayList<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();
		ArrayList<Company> companyList=new ArrayList<Company>();
		companyList=companyServices.findALl();
		companyList.stream()
				   .forEach(company->companyDtoList.add(
						   CompanyMapper.mapFromCompanyToCompanyDto(company)));
		dataMap.put("companies", companyDtoList);
		dataMap.put("computerToUpdate", computerDto);
		return "editComputer";
	}


    @SuppressWarnings("finally")
	@PostMapping("editComputer")
	public String editComputer(@ModelAttribute("computerToUpdate")ComputerDTO computerToUpdateDTO,ModelMap dataMap)  {
//		
//		CompanyDTO companyDTO=new CompanyDTO(Long.parseLong(request.getParameter("companyId")));
//		ComputerDTO computerDTO=new ComputerDTO(Long.parseLong(request.getParameter("id")),request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),companyDTO);
    	Logging.afficherMessageError("ici debut"+computerToUpdateDTO);
    	try {
    		Logging.afficherMessageError("ici try1 -a finally"+computerToUpdateDTO);
			StringBuilder erreur=new StringBuilder();
			Logging.afficherMessageError("ici try1 -b finally"+computerToUpdateDTO);
			Computer computerToUpdate =ComputerMapper.convertFromComputerDtoToComputer(computerToUpdateDTO);
			Logging.afficherMessageError("ici try1 -c finally"+computerToUpdateDTO);
			ComputerValidator computerValidator=new ComputerValidator();
			Logging.afficherMessageError("ici try1"+computerToUpdateDTO);
			try {
				computerValidator.validateComputer(computerToUpdate);
				computerToUpdate=computerServices.update(computerToUpdate);
				computerToUpdateDTO=ComputerMapper.convertFromComputerToComputerDTO(computerToUpdate);
				dataMap.put("newComputer",computerToUpdateDTO );
				String successMessage="CI-dessous les nouvelles valleurs de ce computer";
				dataMap.put("successMessage", successMessage);
				Logging.afficherMessageError("ici try2"+computerToUpdateDTO);
				
			}catch(ComputerValidatorException.DateValidator dateValidator) {
				erreur.append("Vérifier que la date discontinued est après introduced");
				dataMap.put("erreur", erreur);
				dataMap.put("failedComputer", computerToUpdateDTO);
				Logging.afficherMessageError("ici dateValidator"+computerToUpdateDTO);
				
			}catch(ComputerValidatorException.NameValidator nameValidator) {
				erreur.append("\n Vérifier que le nom existe et n'est pas vide ");
				dataMap.put("erreur", erreur);
				dataMap.put("failedComputer", computerToUpdateDTO);
				Logging.afficherMessageError("ici NameValidators"+computerToUpdateDTO);
			}
			
			
		} catch (ParseException e1) {
			Logging.afficherMessageError("Cannot convert From ComputerDto To Computer");
			e1.printStackTrace();
			Logging.afficherMessageError("ici e1"+computerToUpdateDTO);
		}
		finally {
			Logging.afficherMessageError("ici finally"+computerToUpdateDTO);
			return "redirect:editComputerPage?id="+computerToUpdateDTO.getId();
		}
	}

}
