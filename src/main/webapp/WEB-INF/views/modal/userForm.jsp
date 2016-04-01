<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="col_1" value="col-lg-4" />
<c:set var="col_2" value="col-lg-7" />

<div class="modal-header">
	<button class="close" type="button" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title">
		<s:message code="${ userHeader }" />
	</h4>
</div>

<form:form id="userForm" class="form-horizontal" method="POST" action="user" modelAttribute="user" role="form">
	<div class="modal-body">
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

		<div class="form-group required">
			<form:label class="${ col_1 } control-label" path="ssoId">
				<s:message code="user.label.sso_id" />
			</form:label>
			<div class="${ col_2 }">
				<form:input class="form-control" path="ssoId" autocomplete="off" />
			</div>
		</div>

		<div class="form-group">
			<label class="${ col_1 } control-label"><s:message code="user.label.passwd" /></label>
			<div class="${ col_2 }">
				<form:input class="form-control" path="newPasswd" placeholder="********" />
			</div>
		</div>

		<div class="form-group required">
			<label class="${ col_1 } control-label"><s:message code="user.label.role" /></label>
			<div class="${ col_2 }">
				<form:select class="form-control" path="userProfiles" items="${ roles }" itemValue="id" itemLabel="type" multiple="false" />
				<div class="has-error">
					<form:errors path="userProfiles" class="help-inline" />
				</div>
			</div>
		</div>

		<div class="modal-footer">
			<button class="btn btn-default" type="button" data-dismiss="modal">
				<s:message code="global.button.cancel" />
			</button>
			<button class="btn btn-danger" type="button" onclick="deleteUser()">
				<s:message code="global.button.delete" />
			</button>
			<button id="submitButton" class="btn btn-primary" type="submit">
				<s:message code="global.button.save" />
			</button>
		</div>

	</div>
</form:form>
