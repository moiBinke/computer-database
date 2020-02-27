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
<!-- Bootstrap -->
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapStyle" />
<spring:url value="/resources/css/font-awesome.css" var="fontAweSomeStyle" />
<spring:url value="/resources/css/main.css" var="mainCss" />


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"></head>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
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
	            	  <a class="dropdown-item" href="?id=${computerToUpdate.id }&lang=fr"><spring:message code="app.lang.french"/></a>
	               	  <a class="dropdown-item" href="?id=${computerToUpdate.id }&lang=en"><spring:message code="app.lang.english"/></a> 
	          </div>
        </div>
         <div>
        	</b><a href="${pageContext.request.contextPath}/logout" style="color:white"><i class="fa fa-sign-out" aria-hidden="true"></i>
        	Logout</a>
        </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                     <form action="addComputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="computerName"/></label>
                                <input type="text" class="form-control" id="computerName" name="name" value="${failedComputer.name}" placeholder="Computer name"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced" ><spring:message code="introduced"/></label>
                                <input type="date" class="form-control" id="introduced" value="${failedComputer.introduced}" name="introduced" placeholder="Introduced date" />
                            </div>
                            <div class="form-group">
                                <label for="discontinued" path="discontinued"><spring:message code="discontinued"/></label>
                                <input type="date" class="form-control" id="discontinued" value="${failedComputer.discontinued}" name="discontinued" placeholder="Discontinued date" />
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="companyName"/></label>
                                <select class="form-control" id="companyId" name="company.id">
                                <option value="${failedComputer.company.name}" ><c:out value="${failedComputer.name}"></c:out> </option>
                                <c:forEach items="${companies}" var="company">
                                    <option value="${company.id}"><c:out value="${company.name}"></c:out></option>
                                </c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="form-group error">
                        	<c:out value="${erreur}"></c:out>
                        </div>
                        
                        <div class="actions pull-right">
                            <input type="submit" value="Add"  class="btn btn-primary" />
                            or
                           <input type="reset" class="btn btn-default" value="Cancel"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
     <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
				    <section class="text-center">
				    	<c:if test="${newComputer!=null}">
				    		<div class="card" style="width: 18rem;">
							  <img class="card-img-top" src="..." alt="Card image cap">
							  <div class="card-body">
							    <h5 class="card-title">Nom: <c:out value="${newComputer.name}"></c:out></h5>
							    <h5 class="card-title">Introoduced Date<c:out value="${newComputer.introduced}"></c:out></h5>
							    <h5 class="card-title">Discontinued Date<c:out value="${newComputer.discontinued }"></c:out></h5>
							    <h5 class="card-title">Company<c:out value="${newComputer.company.name}"></c:out></h5>
							    <p class="card-text">You can show it in computers list by clicking on below link</p>
							    <a href="dashboard" class="btn btn-primary">show list</a>
							  </div>
							</div>
				    	</c:if>
				    </section>
				</div>
		</div>
	</div>
	
	<spring:url value="/resources/js/jquery.min.js" var="jqueryMinJs" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootsrapJs" />
	<spring:url value="/resources/js/dashboard.js" var="dashboardJs" />

	<script src="${dashboardJs }"></script>
	<script src="${jqueryMinJs }"></script>
	<script src="${bootsrapJs }"></script>	   
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
</body>
</html>