<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML PUBLIC  "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapStyle" />
<spring:url value="/resources/css/font-awesome.css" var="fontAweSomeStyle" />
<spring:url value="/resources/css/main.css" var="mainCss" />

<link href="${bootstrapStyle}" rel="stylesheet" media="screen">
<link href="${fontAweSomeStyle}" rel="stylesheet" media="screen">
<link href="${mainCss}" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
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
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="name" value="${failedComputer.name}" placeholder="Computer name"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced" >Introduced date</label>
                                <input type="date" class="form-control" id="introduced" value="${failedComputer.introduced}" name="introduced" placeholder="Introduced date" />
                            </div>
                            <div class="form-group">
                                <label for="discontinued" path="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" value="${failedComputer.discontinued}" name="discontinued" placeholder="Discontinued date" />
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
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
	<spring:url value="/resources/js/front-computer-validation.js" var="frontValidation" />
	
	<script src="${jqueryMinJs }"></script>
	<script src="${frontValidation }"></script>	   
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
</body>
</html>