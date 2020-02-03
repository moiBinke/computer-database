<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC  "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="DashboardComputerServlet"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
              <c:out value="${sizeComputer}"> </c:out> Computers found 
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="AddComputerServlet">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>
         <div class="row text-center">
            	<div class="col-md-3">
            		<h5>Order by computer name</h5>
            		<a href="DashboardComputerServlet?order-by=name">
            			<span class="glyphicon glyphicon-sort-by-alphabet">&nbsp &nbsp</span>
            		</a>
            		
            		<a  href="DashboardComputerServlet?order-by=name-alt">
            			<span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
            		</a>
            	</div>
            	<div class="col-md-3">
            		<h5>Order by computer introduced date</h5>
            		<a href="DashboardComputerServlet?order-by=introduced">
            			<span class=" glyphicon glyphicon-sort-by-order">&nbsp &nbsp</span>
            		</a>
            		
            		<a  href="DashboardComputerServlet?order-by=introduced-alt">
            			<span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
            		</a>	
            	</div>
            	<div class="col-md-3">
            		<h5>Order by computer Discontinued</h5>
            		<a href="DashboardComputerServlet?order-by=discontinued">
            			<span class=" glyphicon glyphicon-sort-by-order">&nbsp &nbsp</span>
            		</a>
            		
            		<a  href="DashboardComputerServlet?order-by=discontinued-alt">
            			<span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
            		</a>	
            	</div>
            	<div class="col-md-3">
            		<h5>Order by company Name</h5>
            		<a href="DashboardComputerServlet?order-by=company-name">
            			<span class="glyphicon glyphicon-sort-by-alphabet">&nbsp &nbsp</span>
            		</a>
            		
            		<a  href="DashboardComputerServlet?order-by=company-name-alt">
            			<span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
            		</a>
            	</div>
            </div>
		<div class=""></div>
        <form id="deleteForm" action="DashboardComputerServlet?DashboardComputerServlet" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
             
                  <c:forEach items="${computerList}" var="computer">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        <td>
                            <a href="editComputerServlet?id=${computer.id}" onclick=""><c:out value="${computer.name}"></c:out></a>
                        </td>
                        <td><c:out value="${computer.introduced}"></c:out> </td>
                        <td><c:out value="${computer.discontinued}"></c:out> </td>
                        <td><c:out value="${computer.company.name}"></c:out></td>
                    </tr>
				  </c:forEach>
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
	        <c:if test="${pageIterator!=null}">
	            <ul class="pagination">
	              <li>
	              	<c:if test="${pageIterator>0}">
	              		<a href="DashboardComputerServlet?pageIterator=${pageIterator-1}" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	                    </a>
					</c:if>      
	              </li>
	              <c:forEach  var = "i" begin = "1" end = "5">
	              <li><a href="DashboardComputerServlet?pageIterator=${pageIterator+i}"><c:out value="${pageIterator+i}"></c:out></a></li>
				  </c:forEach>
	              <li>
	              <c:if test="${pageIterator<maxPage}">
	                <a href="DashboardComputerServlet?pageIterator=${pageIterator+1}" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
	                </c:if>
	            </li>
	        </ul>
	        </c:if>

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default"><a href="DashboardComputerServlet?taillePage=10">10</a></button>
            <button type="button" class="btn btn-default"><a href="DashboardComputerServlet?taillePage=50">50</a></button>
            <button type="button" class="btn btn-default"><a href="DashboardComputerServlet?taillePage=100">100</a></button>
        </div>

    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>