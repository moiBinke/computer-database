package com.excilys.computerDatabase.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	private static final int Object = 0;

	private int maxPage;

	private ComputerServices computerService;
	
	public ComputerController(ComputerServices computerService) {
		this.computerService=computerService;
	}
	
	@CrossOrigin("*")
	@GetMapping("/computers")
	public ResponseEntity<ArrayList<ComputerDTO>> getDashbord(@RequestParam(value="search", defaultValue = "") String search,@RequestParam(value="orderBy", defaultValue = "any") String orderBy,@RequestParam(value="taillePage", defaultValue="20") int taillePage,@RequestParam(value="pageIterator", defaultValue="1")int pageIterator,ModelMap dataMap) throws ServletException, IOException {
		pageIterator--;		
		return new ResponseEntity<ArrayList<ComputerDTO>>(traitementDashboardWithOrderBy(orderBy,taillePage,pageIterator,dataMap,search), HttpStatus.OK); 
 
	}
	
	@CrossOrigin("*")
	@PostMapping("/computers")
	public  ResponseEntity deleteComputer(@RequestBody DeletingObject deletingObject) throws ServletException, IOException {
		String[] computerIdsAsListString=deletingObject.getSelection().split(",");
		for(String idString:computerIdsAsListString) {
			computerService.deleteComputer(Long.parseLong(idString));
		}
		return  ResponseEntity.ok(null);
		
	}


	@CrossOrigin("*")
	@GetMapping("/computers/size")
	public  ResponseEntity size() {
		Integer size=new Integer(computerService.size());
		return  ResponseEntity.ok(size);
		
	}
	
	@CrossOrigin("*")
	@GetMapping("/computers/sizeSearch")
	public  ResponseEntity sizeSearch(@RequestParam(value="search", defaultValue = "") String search) {
		Integer size=search(search).size();
		return  ResponseEntity.ok(size);
		
	}

	
	
	public ArrayList<ComputerDTO> traitementDashboardWithOrderBy( String orderBy, int taillePage,int pageIterator,ModelMap dataMap, String search) throws ServletException, IOException {
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		ArrayList<Computer>computerList=new ArrayList<Computer>();
		int sizeComputer=computerService.size();
		this.maxPage=sizeComputer/taillePage;
		dataMap.put("maxPage", this.maxPage);
		computerList=computerService.getPage(pageIterator*taillePage,taillePage,orderBy,search); 
		computerList.stream()
					.forEach(computer->computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer)));
		return computerDTOList;
	}
	
	
	
	private ArrayList<ComputerDTO> search( String search){
		
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		ArrayList<Computer>computerList=new ArrayList<Computer>();
		System.out.println(search);
		computerList=computerService.search(search);
		computerList.stream()
					.forEach(computer->computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer)));
		return computerDTOList;
			}
}
