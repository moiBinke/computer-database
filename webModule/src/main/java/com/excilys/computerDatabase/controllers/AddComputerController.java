package com.excilys.computerDatabase.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerDatabase.model.Company;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.dto.CompanyDTO;
import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.logging.Logging;
import com.excilys.computerDatabase.exceptions.ComputerValidatorException;
import com.excilys.computerDatabase.mappers.CompanyMapper;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.services.CompanyServices;
import com.excilys.computerDatabase.services.ComputerServices;
import com.excilys.computerDatabase.validators.ComputerValidator;

@RestController
public class AddComputerController {

		
	    private CompanyServices companyServices;
		private ComputerServices computerServices;
		
		public AddComputerController(CompanyServices companyServices,ComputerServices computerServices) {
			this.companyServices=companyServices;
			this.computerServices=computerServices;
		}
	    
		//Ne vas plus être utilisée
		@GetMapping("addComputerPage") 
		public String doGet(ModelMap dataMap){
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
		
		@PostMapping(path="addComputer")
		public ResponseEntity<Map<String, String>> addComputer(@RequestBody ComputerDTO computerDTO)  {
			Map<String, String> erreurs = new HashMap<String, String>();
			try {
				StringBuilder erreur=new StringBuilder();
				System.out.println(computerDTO);
				Computer newComputer =ComputerMapper.convertFromComputerDtoToComputer(computerDTO);
				ComputerValidator computerValidator=new ComputerValidator();
				
				try {
					computerValidator.validateComputer(newComputer);
					try {
						newComputer=computerServices.create(newComputer);
						computerDTO=ComputerMapper.convertFromComputerToComputerDTO(newComputer);
					} catch (ParseException e) {
						Logging.afficherMessage("Cannot convert computer date type: "+newComputer.getDiscontinued());
						e.printStackTrace();
					}
				}catch(ComputerValidatorException.DateValidator dateValidator) {
					erreurs.put("dateError", "Vérifier que la date discontinued est après introduced");
					return new ResponseEntity<Map<String, String>>(erreurs, HttpStatus.OK); 
					
				}catch(ComputerValidatorException.NameValidator nameValidator) {
					erreurs.put("nameError","\n Vérifier que le nom existe et n'est pas vide ");
					return new ResponseEntity<Map<String, String>>(erreurs, HttpStatus.OK); 
				}
				finally {
					//doGet(request, response);
				}
				
			} catch (ParseException e1) {
				Logging.afficherMessageError("Cannot convert From ComputerDto To Computer");
				e1.printStackTrace();
			}
			return new ResponseEntity<Map<String, String>>(erreurs, HttpStatus.OK); 
			
		}
	}