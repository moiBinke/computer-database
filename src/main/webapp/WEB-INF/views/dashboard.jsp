<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code=""/>
<!DOCTYPE HTML PUBLIC  "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title><spring:message code="Cdb.projectName"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapStyle" />
<spring:url value="/resources/css/font-awesome.css" var="fontAweSomeStyle" />
<spring:url value="/resources/css/main.css" var="mainCss" />



<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"></head>
<link href="${bootstrapStyle}" rel="stylesheet" media="screen">
<link href="${fontAweSomeStyle}" rel="stylesheet" media="screen">
<link href="${mainCss}" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"><spring:message code="Cdb.projectName"/></a>
            <div class="dropdown ">
	            <button class="btn btn-danger dropdown-toggle" type="button" id="dropdownMenuButton"
	               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="app.lang.title"/></button>
	            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	            	  <a class="dropdown-item" href="?lang=fr"><spring:message code="app.lang.french"/></a>
	               	  <a class="dropdown-item" href="?lang=en"><spring:message code="app.lang.english"/></a> 
	          </div>
        </div>
        </div>
        <div>
    </header>
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
              <c:out value="${sizeComputer}"> </c:out> <spring:message code="Cbd.computerFounded"/>
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
                    <a class="btn btn-success" id="addComputer" href="addComputerPage"><spring:message code="addComputerButton"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="editButton"/></a>
                </div>
            </div>
        </div>
    
           
            </div>
        <div class=""></div>
        <form id="deleteForm" action="deleteComputer" method="POST">
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
                            
                            <p><spring:message code="computerName"/></p>
                            <p>
                                <a href="dashboard?orderBy=computer_name ASC">
                                    <span class="glyphicon glyphicon-sort-by-alphabet">&nbsp &nbsp</span>
                                </a>
                                
                                <a  href="dashboard?orderBy=computer_name DESC">
                                    <span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
                                </a>
                            </p>
                        </th>
                        <th>
                            <p><spring:message code="introduced"/></p>
                            <p>
                                <a href="dashboard?orderBy=introduced ASC">
                                    <span class=" glyphicon glyphicon-sort-by-order">&nbsp &nbsp</span>
                                </a>
                                
                                <a  href="dashboard?orderBy=introduced DESC">
                                    <span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
                                </a>    
                            </p>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                           <p> <spring:message code="discontinued"/></p>
                           <p>
                                <a href="dashboard?orderBy=discontinued ASC">
                                    <span class=" glyphicon glyphicon-sort-by-order">&nbsp &nbsp</span>
                                </a>
                                
                                <a  href="dashboard?orderBy=discontinued DESC">
                                    <span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
                                </a>    
                           </p>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <p><spring:message code="companyName"/></p>
                            <p>
                                <a href="dashboard?orderBy=company_name ASC">
                                    <span class="glyphicon glyphicon-sort-by-alphabet">&nbsp &nbsp</span>
                                </a>
                                
                                <a  href="dashboard?orderBy=company_name DESC">
                                    <span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
                                </a>
                            </p>
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
                            <a href="editComputerPage?id=${computer.id}" onclick=""><c:out value="${computer.name}"></c:out></a>
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
                        <a href="dashboard?pageIterator=${pageIterator-1}" aria-label="Previous">
                          <span aria-hidden="true">&laquo;</span>
                        </a>
                    </c:if>      
                  </li>
                  <c:forEach  var = "i" begin = "1" end = "5">
                  <li><a href="dashboard?pageIterator=${pageIterator+i}"><c:out value="${pageIterator+i}"></c:out></a></li>
                  </c:forEach>
                  <li>
                  <c:if test="${pageIterator<maxPage}">
                    <a href="dashboard?pageIterator=${pageIterator+1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                    </c:if>
                </li>
            </ul>
            </c:if>

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default"><a href="dashboard?taillePage=10">10</a></button>
            <button type="button" class="btn btn-default"><a href="dashboard?taillePage=50">50</a></button>
            <button type="button" class="btn btn-default"><a href="dashboard?taillePage=100">100</a></button>
        </div>
        </div>
    </footer>
<spring:url value="/resources/js/jquery.min.js" var="jqueryMinJs" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootsrapJs" />
<spring:url value="/resources/js/dashboard.js" var="dashboardJs" />

<script src="${jqueryMinJs }"></script>
<script src="${bootsrapJs }"></script>
<script src="${dashboardJs }"></script>

</body>
</html>