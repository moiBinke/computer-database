<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapStyle" />
<spring:url value="/resources/css/font-awesome.css" var="fontAweSomeStyle" />
<spring:url value="/resources/css/main.css" var="mainCss" />
<spring:url value="/resources/images/excilys.png" var="homePicture" />


<link href="${bootstrapStyle}" rel="stylesheet" media="screen">
<link href="${fontAweSomeStyle}" rel="stylesheet" media="screen">
<link href="${mainCss}" rel="stylesheet" media="screen">
</head>
<body>
    
	<main role="main" class="container" style="margin-top:3%;">
      <div class=" text-center" style="font-weight:bold;font-family: Verdana;font-size:xx-large;color:">
        <img src="${homePicture }" > 
        <div>Application - Computer Database</div>
        </br>
      </div>
      
      <div class="row text-center">
        
        <div class="col-md-5 myDIV text-center" style="border-radius:30px;border:solid; border-color:#3dc218; height:100px;background-color:#ededed; font-size:larger">
          <br>
          <a href="addComputerPage"><h3>Add Computer</h3></a>
        </div>
        <div class="col-md-2" >
          
        </div>
        <div class="col-md-5 myDIV" style="border-radius:30px;border:solid; border-color:#3dc218; height:100px;background-color:#ededed; font-size:larger">
          <br>
          <a href="#">
          <h3>Update Computer</h3>
          </a>
        </div>
      </div>
      <div class="row text-center">
           <br>
           <br>
           
      </div>
      <div class="row text-center ">
        <div class="col-md-5 myDIV" style="border-radius:30px;border:solid; border-color:#3dc218; height:100px;background-color:#ededed;font-size:larger">
          <br>
          <a href="dashboard">
          <h3>Computer Dashbord</h3>
          </a>
        </div>
        <div class="col-md-2" style="background-color:">
          
        </div>
        <div class="col-md-5 myDIV" style="border-radius:30px;border:solid; border-color:#3dc218; height:100px;background-color:#ededed;font-size:larger">
          <br>
         <h3> <a href="#">Select Company</a></h3>
        </div>

      </div>

    </main><!-- /.container -->
<spring:url value="/resources/js/jquery.min.js" var="jqueryMinJs" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootsrapJs" />
<spring:url value="/resources/js/dashboard.js" var="dashboardJs" />

<script src="${jqueryMinJs }"></script>
<script src="${bootsrapJs }"></script>
<script src="${dashboardJs }"></script>

</body>
</html>