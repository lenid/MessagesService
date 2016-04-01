<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- s:eval expression="T(gran.home.template.util.SecurityHelper).isAdmin()" var="isAdmin" /-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>AccessDenied page</title>

<!-- Bootstrap core CSS -->
<link href="static/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/vendors/bootstrap/css/bootstrapValidator.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="static/css/dashboard.css" rel="stylesheet">

<!-- Bootstrap core JavaScript
    ================================================== -->
<script src="static/vendors/jquery/jquery.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrapValidator.min.js"></script>

</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 main">
				<h1 align="center">
					<strong><s:message code="${ pageHeader }" text="No header" /></strong>
				</h1>
				<%@include file="include/notifications.jsp"%>
				<h2>You are not authorized to access this page!</h2>
				<a href="<c:url value="/" />">Go to home</a>
			</div>
		</div>
	</div>

</body>
</html>

