package com.excilys.computerDatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;

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
       
	
	
	
	public  int pageIterator;
	private int taillePage=20;
	public int maxPage;
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
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int sizeComputer=computerService.findAll().size();
		maxPage=sizeComputer/taillePage;
		request.setAttribute("maxPage", maxPage);
		System.out.println("Max "+maxPage);
		ArrayList<ComputerDTO>computerDTOList=new ArrayList<ComputerDTO>();
		ArrayList<Computer>computerList=new ArrayList<Computer>();
		if(request.getParameter("pageIterator")!=null) {
			pageIterator=Integer.parseInt(request.getParameter("pageIterator"));
			computerList=computerService.getPage(pageIterator*taillePage,taillePage );
			for(Computer computer: computerList) {
				computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer));
			}
			request.setAttribute("sizeComputer", sizeComputer);
			request.setAttribute("computerList", computerDTOList);
			request.setAttribute("pageIterator", pageIterator);
			request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
		
			
		}
		else {
			pageIterator=0;
			computerList=computerService.getPage(pageIterator*taillePage,taillePage);
			for(Computer computer: computerList) {
				computerDTOList.add(ComputerMapper.convertFromComputerToComputerDTO(computer));
			}
			request.setAttribute("sizeComputer", sizeComputer);
			request.setAttribute("computerList", computerDTOList);
			request.setAttribute("pageIterator", pageIterator);
			request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
