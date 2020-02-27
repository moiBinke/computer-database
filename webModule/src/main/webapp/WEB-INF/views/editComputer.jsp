<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code=""/>
<!DOCTYPE html>
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
                    <div class="label label-default pull-right">
                        id: <c:out value="${computerToUpdate.id }"></c:out>
                    </div>
                    <h1><spring:message code="editButton"/></h1>
					<div class="form-group ">
                        	<c:out value="${successMessage}"></c:out>
                     </div>
                    <form action="editComputer" method="POST">
                        <input type="hidden" id="id" name="id" value="${computerToUpdate.id }"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="computerName"/></label>
                                <input type="text" class="form-control" id="computerName" name="name" value="${computerToUpdate.name }" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="introduced"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" value="${computerToUpdate.introduced }" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="discontinued"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" value="${computerToUpdate.discontinued }" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="companyName"/></label>
                                <select class="form-control" id="companyId" name="company.id">
	                                <c:forEach items="${companies}" var="company">
	                                	<c:if test="${ company.id==computerToUpdate.company.id}">
	                                		 <option value="${company.id}" selected><c:out value="${company.name}"></c:out></option>
	                                	</c:if>
	                                	<c:if test="${company.id!=computerToUpdate.company.id}">
  	                                   		 <option value="${company.id}"><c:out value="${company.name}"></c:out></option>
										</c:if>
	                                </c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        <div class="form-group error">
                        	<c:out value="${erreur}"></c:out>
                        </div>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><spring:message code="cancelButton"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <spring:url value="/resources/js/jquery.min.js" var="jqueryMinJs" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootsrapJs" />
<spring:url value="/resources/js/dashboard.js" var="dashboardJs" />

<script src="${jqueryMinJs }"></script>
<script src="${bootsrapJs }"></script>
<script src="${dashboardJs }"></script>
</body>
</html>