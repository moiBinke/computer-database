package com.excilys.computerDatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerDatabase.daos.DaoFactory;
import com.excilys.computerDatabase.dto.ComputerDTO;
import com.excilys.computerDatabase.mappers.ComputerMapper;
import com.excilys.computerDatabase.model.Computer;
import com.excilys.computerDatabase.services.ComputerServices;
import com.excilys.computerDatabase.util.Pages;

/**
 * Servlet implementation class ComputerController
 */
@WebServlet(urlPatterns = "/DashboardComputerServlet")
public class DashboardComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ComputerServices computerService;
       
	
	
	
	private  Integer pageIterator;
	private int taillePage=20;
	private int maxPage;
	private String orderBy;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		computerService= ComputerServices.getInstance();
		orderBy="any";
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void traitementDashboardWithOrderBy(HttpServletRequest request, HttpServletResponse response, String orderBy) throws ServletException, IOException {
		
		int sizeComputer=computerService.size();
		maxPage=sizeComputer/taillePage;
		request.setAttribute("maxPage", maxPage);
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		ArrayList<Computer>computerList=new ArrayList<Computer>();
		if(request.getParameter("taillePage")!=null) {
			taillePage=Integer.parseInt(request.getParameter("taillePage"));
		}
		if(request.getParameter("pageIterator")!=null) {
			pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
			computerList=computerService.getPage(pageIterator*taillePage,taillePage,orderBy );

		}
		else {
			pageIterator=0;//Initialisation de l'iterateur : premier appel
			computerList=computerService.getPage(pageIterator*taillePage,taillePage,orderBy);// appel de computer dao 
		}

		computerList.stream()
					.forEach(computer->computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer)));
		
		request.setAttribute("sizeComputer", sizeComputer);
		request.setAttribute("computerList", computerDTOList);
		request.setAttribute("pageIterator", pageIterator);
		request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
	}
	
	/**
	 * méthode de Recherche 
	 */
	private void search(HttpServletRequest request, HttpServletResponse response, String search) throws ServletException, IOException  {
		int sizeComputer=computerService.size();
		maxPage=sizeComputer/taillePage;
		request.setAttribute("maxPage", maxPage);
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		ArrayList<Computer>computerList=new ArrayList<Computer>();
		System.out.println(search);
		computerList=computerService.search(search);
		computerList.stream()
		.forEach(computer->computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer)));
		request.setAttribute("sizeComputer", sizeComputer);
		request.setAttribute("computerList", computerDTOList);
		request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
			}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("search")!=null && !request.getParameter("search").equals("")) {
			
			search(request,response,request.getParameter("search"));
			System.out.println(request.getParameter("search"));
		}
		else {
			if(request.getParameter("order-by")!=null && !request.getParameter("order-by").equals("")) {
				orderBy=request.getParameter("order-by");
				traitementDashboardWithOrderBy(request,response,orderBy);
			}
			else{
				traitementDashboardWithOrderBy(request,response,orderBy);
			}
		}
		
		
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(request.getParameter("selection"));
		String[] computerIdsAsListString=request.getParameter("selection").split(",");
		for(String idString:computerIdsAsListString) {
			computerService.deleteComputer(Long.parseLong(idString));
		}
		
		
     	doGet(request, response);
	}

}
