<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:message code="global.project.name" /></title>

<link href="static/vendors/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="static/css/security.css" type="text/css" rel="stylesheet">

</head>

<body>
	<%@include file="include/top.jsp"%>
	
	<div class="col-lg-12 message">
		<c:if test="${error}">
			<div class="alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				<c:if test="${not empty errorMessage}">
					<s:message code="${errorMessage}" />
				</c:if>
				<c:if test="${empty errorMessage}">
					${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				</c:if>
			</div>
		</c:if>
	</div>

	<div class="col-lg-4 col-lg-offset-4 main">
		<form class="form-signin" method="POST" action="${loginUrl}" role="form">
			<div class="form-login">
				<h4><s:message code="login.label.main" /></h4>
				<input class="form-control" name="ssoId" type="text" placeholder=
					"<s:message code="login.placeholder.login" />" required autofocus /> 
				<br/> 
				<input class="form-control" name="password" type="password"
					placeholder="<s:message code="login.placeholder.passwd" />" required /> <br/>
				<div><a href="signup"><s:message code="login.href.signup" /></a></div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="wrapper">
					<button class="btn btn-primary" type="submit">
						<s:message code="login.button.signin" />
					</button>
				</div>
			</div>
		</form>
	</div>
</body>

</html>