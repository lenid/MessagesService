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

<title><s:message code="global.project.name" /></title>

<link href="static/vendors/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="static/vendors/bootstrap/css/bootstrapValidator.min.css" type="text/css" rel="stylesheet">
<link href="static/css/dashboard.css" type="text/css" rel="stylesheet">

<script src="static/vendors/jquery/jquery.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrapValidator.min.js"></script>
<script src="static/vendors/jquery/formValidation.min.js"></script>
<script src="static/vendors/jquery/framework/bootstrap.min.js"></script>
</head>

<c:set var="col" value="col-lg-7 col-lg-offset-3" />
<c:set var="col_1" value="col-lg-3" />
<c:set var="col_offset_1" value="col-lg-offset-3" />
<c:set var="col_2" value="col-lg-6" />
<c:set var="col_3" value="col-lg-2" />
<c:set var="dateFormat"><s:message code="global.dateFormat" /></c:set>

<c:if test="${ includeAccountData }">
	<c:set var="action" value="account" />
</c:if>
<c:if test="${ not includeAccountData }">
	<c:set var="action" value="signup" />
</c:if>

<body>
	<%@include file="include/top.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 main">
				<h3 align="center">
					<s:message code="${ pageHeader }" text="No header" />
				</h3>
				<%@include file="include/notifications.jsp"%>

				<div class="${ col }">

					<form:form id="userForm" class="form-horizontal" method="POST" action="${ action }" modelAttribute="user" role="form">
						<form:hidden path="id" />
						<form:hidden path="state" />
						<form:hidden path="passwd" />

						<div class="form-group required">
							<form:label class="${ col_1 } control-label" path="firstName">
								<s:message code="user.label.name_first" />
							</form:label>
							<div class="${ col_2 }">
								<form:input class="form-control" path="firstName" />
							</div>
						</div>
						<div class="form-group required">
							<form:label class="${ col_1 } control-label" path="lastName">
								<s:message code="user.label.name_last" />
							</form:label>
							<div class="${ col_2 }">
								<form:input class="form-control" path="lastName" />
							</div>
						</div>
						<div class="form-group required">
							<form:label class="${ col_1 } control-label" path="email">
								<s:message code="user.label.email" />
							</form:label>
							<div class="${ col_2 }">
								<form:input class="form-control" path="email" />
							</div>
						</div>

						<c:if test="${ not includeAccountData }">
							<div class="form-group required">
								<form:label class="${ col_1 } control-label" path="ssoId">
									<s:message code="user.label.sso_id" />
								</form:label>
								<div class="${ col_2 }">
									<form:input class="form-control" path="ssoId" autocomplete="off" />
								</div>
							</div>

							<div class="form-group required">
								<label class="${ col_1 } control-label"><s:message code="user.label.passwd" /></label>
								<div class="${ col_2 }">
									<form:password class="form-control" path="newPasswd" />
								</div>
							</div>
							<div class="form-group required">
								<label class="${ col_1 } control-label"><s:message code="user.label.passwd_confirm" /></label>
								<div class="${ col_2 }">
									<input id="confirmPasswd" class="form-control" name="confirmPasswd" type="password" />
								</div>
							</div>
						</c:if>

						<c:if test="${ includeAccountData }">
							<!-- Sso no edit -->
							<div id="noEditSso" class="form-group" style="display: block">
								<label class="${ col_1 } control-label"><s:message code="user.label.sso_id" /></label>
								<div class="${ col_2 }">
									<div class="input-group">
										<input id="ssoId" class="form-control" value="${ user.ssoId }" readonly />
										<div class="input-group-btn">
											<button class="btn btn-default" type="button" onclick="showSsoForm()">
												<span class="glyphicon glyphicon-pencil" aria-hidden="true" title="<s:message code="user.title.edit.sso_id" />"></span>
											</button>
										</div>
									</div>
								</div>
							</div>

							<!-- Sso EDIT -->
							<div id="editSso" style="display: none">
								<div class="form-group required">
									<label class="${ col_1 } control-label"><s:message code="user.label.sso_id" /></label>
									<div class="${ col_2 }">
										<form:input class="form-control" path="ssoId" />
									</div>
									<div class="${ col_3 }" align="right">
										<button class="btn btn-primary" name="action" type="submit" value="sso_id" onclick="editSsoId()">
											<s:message code="user.button.edit.sso_id" />
										</button>
									</div>
								</div>
								<div class="form-group required">
									<label class="${ col_1 } control-label"><s:message code="user.label.passwd" /></label>
									<div class="${ col_2 }">
										<form:password id="oldPasswdSso" class="form-control" path="oldPasswd" />
									</div>
								</div>
								<div class="form-group">
									<div class="${ col_offset_1 } ${ col_2 }" align="right">
										<button class="btn btn-link" type="button" onclick="hideSsoForm()">
											<s:message code="user.button.cancel.sso" />
										</button>
									</div>
								</div>
							</div>

							<!-- Pass no edit -->
							<div id="noEditPasswd" class="form-group" style="display: block">
								<label class="${ col_1 } control-label"><s:message code="user.label.passwd" /></label>
								<div class="${ col_2 }">
									<div class="input-group">
										<input id="passwd" class="form-control" value="********" readonly />
										<div class="input-group-btn">
											<button class="btn btn-default" type="button" onclick="showPasswdForm()">
												<span class="glyphicon glyphicon-pencil" aria-hidden="true" title="<s:message code="user.title.edit.passwd" />"></span>
											</button>
										</div>
									</div>
								</div>
							</div>

							<!-- Pass EDIT -->
							<div id="editPasswd" style="display: none">
								<div class="form-group required">
									<label class="${ col_1 } control-label"><s:message code="user.label.passwd_old" /></label>
									<div class="${ col_2 }">
										<form:password id="oldPasswdPass" class="form-control" path="oldPasswd" />
									</div>
									<div class="${ col_3 }" align="right">
										<button class="btn btn-primary" name="action" type="submit" value="passwd" onclick="editPasswd()">
											<s:message code="user.button.edit.passwd" />
										</button>
									</div>
								</div>
								<div class="form-group required">
									<label class="${ col_1 } control-label"><s:message code="user.label.passwd_new" /></label>
									<div class="${ col_2 }">
										<form:password class="form-control" path="newPasswd" />
									</div>
								</div>
								<div class="form-group required">
									<label class="${ col_1 } control-label"><s:message code="user.label.passwd_confirm" /></label>
									<div class="${ col_2 }">
										<input id="confirmPasswd" class="form-control" name="confirmPasswd" type="password" />
									</div>
								</div>
								<div class="form-group">
									<div class="${ col_offset_1 } ${ col_2 }" align="right">
										<button class="btn btn-link" type="button" onclick="hidePasswdForm()">
											<s:message code="user.button.cancel.passwd" />
										</button>
									</div>
								</div>
							</div>
							
							<!-- 
							<div class="form-group">
								<label class="${ col_1 } control-lable" for="userProfiles"><s:message code="user.label.role" /></label>
								<div class="${ col_2 }">
									<form:select class="form-control" path="userProfiles" items="${ roles }" itemValue="id" itemLabel="type" multiple="true" />
									<div class="has-error">
										<form:errors path="userProfiles" class="help-inline" />
									</div>
								</div>
							</div>
							 -->

							<div class="form-group">
								<label class="${ col_1 } control-label"><s:message code="user.label.created" /></label>
								<div class="${ col_2 }">
									<label class="control-label"><nobr>
											<fmt:formatDate value="${ user.created }" pattern='${ dateFormat }' />
										</nobr></label>
								</div>
							</div>

						</c:if>

						<div class="form-group">
							<div class="col-lg-12">
								<p>&nbsp;</p>
							</div>
						</div>

						<div id="mainButtons" class="form-group" style="display: block">
							<div class="${ col_offset_1 } ${ col_2 }" align="right">
								<button class="btn btn-default" type="button" onclick="location.href='/';">
									<s:message code="global.button.home" />
								</button>
								<button class="btn btn-primary" name="action" type="submit" value="account">
									<s:message code="global.button.submit" />
								</button>
							</div>
						</div>
					</form:form>

				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function showSsoForm() {
			hidePasswdForm();
			document.getElementById('noEditSso').style.display = "none";
			document.getElementById('editSso').style.display = "block";
			document.getElementById('mainButtons').style.display = "none";
		}

		function showPasswdForm() {
			hideSsoForm();
			document.getElementById('noEditPasswd').style.display = "none";
			document.getElementById('editPasswd').style.display = "block";
			document.getElementById('mainButtons').style.display = "none";
		}

		function hideSsoForm() {
			var validator = $('#userForm').data('formValidation');
			validator.resetField('ssoId');
			validator.resetField('oldPasswd');
			document.getElementById('noEditSso').style.display = "block";
			document.getElementById('editSso').style.display = "none";
			document.getElementById('mainButtons').style.display = "block";
		}

		function hidePasswdForm() {
			var validator = $('#userForm').data('formValidation');
			validator.resetField('oldPasswdPass');
			validator.resetField('newPasswd');
			validator.resetField('confirmPasswd');

			$('#oldPasswdPass').val('');
			$('#newPasswd').val('');
			$('#confirmPasswd').val('');

			document.getElementById('noEditPasswd').style.display = "block";
			document.getElementById('editPasswd').style.display = "none";
			document.getElementById('mainButtons').style.display = "block";
		}

		function editSsoId() {
			$('#oldPasswdPass').prop('disabled', true);
		}

		function editPasswd() {
			$('#oldPasswdSso').prop('disabled', true);
		}
		
		$(document).ready(function() {
			validateUserForm('#userForm');
		});

	</script>
	<%@include file="include/userValidator.js.jsp"%>
</body>
</html>