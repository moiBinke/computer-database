
package com.excilys.computerDatabase.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerDatabase.dto.CompanyDTO;
import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.logging.Logging;
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
@RestController
public class EditComputerController {
	
    private ComputerDTO computerDto;
    private CompanyServices companyServices;
	private ComputerServices computerServices;
	
	public EditComputerController(CompanyServices companyServices,ComputerServices computerServices) {
		this.companyServices=companyServices;
		this.computerServices=computerServices;
	}
	
	@CrossOrigin("*")
    @GetMapping("computer")
	public ResponseEntity<ComputerDTO> editComputerPage(@RequestParam(value="id")Long idComputer) {
	
		Computer computerToUpdate=computerServices.findById(idComputer).get();
		computerDto=ComputerMapper.convertFromComputerToComputerDTO(computerToUpdate);		
		return new ResponseEntity<ComputerDTO>(computerDto , HttpStatus.OK);
	}
	
	@CrossOrigin("*")
    @GetMapping("/companies")
    public ResponseEntity<ArrayList<CompanyDTO> > getCompanies() {
    	ArrayList<CompanyDTO> companyDtoList=new ArrayList<CompanyDTO>();
		ArrayList<Company> companyList=new ArrayList<Company>();
		companyList=companyServices.findALl();
		companyList.stream()
				   .forEach(company->companyDtoList.add(
						   CompanyMapper.mapFromCompanyToCompanyDto(company)));
		return new ResponseEntity<ArrayList<CompanyDTO>>(companyDtoList,HttpStatus.OK);
    	
    }


    @SuppressWarnings("finally")
    @CrossOrigin("*")
	@PatchMapping("editComputer")
	public ResponseEntity<ComputerDTO> editComputer(@RequestBody ComputerDTO computerToUpdateDTO)  {
    	
    	
		Map<String, String> erreurs = new HashMap<String, String>();
    	try {
			StringBuilder erreur=new StringBuilder();
			Computer computerToUpdate =ComputerMapper.convertFromComputerDtoToComputer(computerToUpdateDTO);
			ComputerValidator computerValidator=new ComputerValidator();
			try {
				computerValidator.validateComputer(computerToUpdate);
				computerToUpdate=computerServices.update(computerToUpdate);
				computerToUpdateDTO=ComputerMapper.convertFromComputerToComputerDTO(computerToUpdate);
				
			}catch(ComputerValidatorException.DateValidator dateValidator) {
				erreurs.put("dateError", "Vérifier que la date discontinued est après introduced");
				Logging.afficherMessageError("ici dateValidator"+computerToUpdateDTO);
				
			}catch(ComputerValidatorException.NameValidator nameValidator) {
				erreurs.put("nameError", "\n Vérifier que le nom existe et n'est pas vide ");
				Logging.afficherMessageError("ici NameValidators"+computerToUpdateDTO);
			}
			
			
		} catch (ParseException e1) {
			Logging.afficherMessageError("Cannot convert From ComputerDto To Computer");
			e1.printStackTrace();
		}
		finally {
			Logging.afficherMessageError("ici finally"+computerToUpdateDTO);
			return new ResponseEntity<ComputerDTO>(computerToUpdateDTO,HttpStatus.OK);
		}
	}

}
