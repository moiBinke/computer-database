package com.excilys.computerDatabase.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.services.ComputerServices;

@Controller
public class DashboardController {
	private int maxPage;

	private ComputerServices computerService;
	
	public DashboardController(ComputerServices computerService) {
		this.computerService=computerService;
	}
	
	public String traitementDashboardWithOrderBy( String orderBy, int taillePage,int pageIterator,ModelMap dataMap) throws ServletException, IOException {
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
		return "dashboard";
	}
	
	private String search( String search,ModelMap dataMap,int taillePage){
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
		return "dashboard";
			}
	@GetMapping("/dashboard")
	public String getDashbord(@RequestParam(value="search", required = false) String search,@RequestParam(value="orderBy", defaultValue = "any") String orderBy,@RequestParam(value="taillePage", defaultValue="20") int taillePage,@RequestParam(value="pageIterator", defaultValue="0")int pageIterator,ModelMap dataMap) throws ServletException, IOException {
			
			if(search!=null && !search.equals("")) {
				
				return search(search, dataMap,taillePage);
			}
			else {
				
					return traitementDashboardWithOrderBy(orderBy,taillePage,pageIterator,dataMap);
				
			}
	}
	@PostMapping("/deleteComputer")
	public String deleteComputer(@RequestParam(value="selection")String listIdComputer) throws ServletException, IOException {
		String[] computerIdsAsListString=listIdComputer.split(",");
		for(String idString:computerIdsAsListString) {
			computerService.deleteComputer(Long.parseLong(idString));
		}
		return "redirect:dashboard";
		
	}
	
}
