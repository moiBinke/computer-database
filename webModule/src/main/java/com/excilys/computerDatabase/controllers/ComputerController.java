package com.excilys.computerDatabase.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.services.ComputerServices;


@RestController
public class ComputerController {

	private int maxPage;

	private ComputerServices computerService;
	
	public ComputerController(ComputerServices computerService) {
		this.computerService=computerService;
	}
	
	
	@GetMapping("/computers")
	public ResponseEntity<ArrayList<ComputerDTO>> getDashbord(@RequestParam(value="search", required = false) String search,@RequestParam(value="orderBy", defaultValue = "any") String orderBy,@RequestParam(value="taillePage", defaultValue="20") int taillePage,@RequestParam(value="pageIterator", defaultValue="0")int pageIterator,ModelMap dataMap) throws ServletException, IOException {
			
			if(search!=null && !search.equals("")) {
				
				return new ResponseEntity<ArrayList<ComputerDTO>>(search(search, dataMap,taillePage), HttpStatus.OK); 
			}
			else {
				
				return new ResponseEntity<ArrayList<ComputerDTO>>(traitementDashboardWithOrderBy(orderBy,taillePage,pageIterator,dataMap), HttpStatus.OK); 
 
				
			}
	}
	
	@DeleteMapping("/computers")
	public  ResponseEntity deleteComputer(@RequestBody String listIdComputer) throws ServletException, IOException {
		String[] computerIdsAsListString=listIdComputer.split(",");
		for(String idString:computerIdsAsListString) {
			computerService.deleteComputer(Long.parseLong(idString));
		}
		return  ResponseEntity.ok(null);
		
	}

	
	
	
	
	
	
	
	
	public ArrayList<ComputerDTO> traitementDashboardWithOrderBy( String orderBy, int taillePage,int pageIterator,ModelMap dataMap) throws ServletException, IOException {
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		ArrayList<Computer>computerList=new ArrayList<Computer>();
		int sizeComputer=computerService.size();
		this.maxPage=sizeComputer/taillePage;
		dataMap.put("maxPage", this.maxPage);
		computerList=computerService.getPage(pageIterator*taillePage,taillePage,orderBy); 
		computerList.stream()
					.forEach(computer->computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer)));
		
		dataMap.put("sizeComputer", sizeComputer);
		dataMap.put("computerList", computerDTOList);
		dataMap.put("pageIterator", pageIterator);
		return computerDTOList;
	}
	
	private ArrayList<ComputerDTO> search( String search,ModelMap dataMap,int taillePage){
		int sizeComputer=computerService.size();
		maxPage=sizeComputer/taillePage;
		dataMap.put("maxPage", maxPage);
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		ArrayList<Computer>computerList=new ArrayList<Computer>();
		System.out.println(search);
		computerList=computerService.search(search);
		computerList.stream()
					.forEach(computer->computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer)));
		dataMap.put("sizeComputer", sizeComputer);
		dataMap.put("computerList", computerDTOList);
		return computerDTOList;
			}
}
