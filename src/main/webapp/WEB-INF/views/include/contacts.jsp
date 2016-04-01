<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style type="text/css">
#contactList {
	height: 200px;
}
</style>

<div class="col-lg-2 sidebar">

	<div align="center">
		<span id="editContactListLink" class="clickable"> <span class="glyphicon glyphicon-pencil" title='<s:message code="contacts.title.edit" />'></span>
		</span> <label class="control-lable"> <s:message code="contacts.label.address_book" /></label> <span id="addContactLink"
			class="clickable glyphicon glyphicon-pushpin" style="display: none" title='<s:message code="contacts.title.add" />'></span>
	</div>

	<div id="addContact" style="display: none">
		<div class="bs-contactList">
			<form:form id="addContactForm" method="POST" action="contact/add">
				<input class="typeahead tt-query" name="ssoId" type="text" placeholder="string" autocomplete="off" spellcheck="false">
			</form:form>
		</div>
	</div>

	<form:form id="contactListForm" class="form-horizontal" method="POST" action="contact/delete" modelAttribute="user" role="form">

		<div class="form-group">
			<div>
				<form:select id="contactList" class="form-control" path="contacts" items="${ contactList }" itemValue="id" itemLabel="ssoId"
					multiple="true" />
			</div>
		</div>

		<div id="deleteContactsButtonWrapper" style="display: none" align="right">
			<button id="deleteContactsButton" class="btn btn-danger" type="button" onclick="deleteContacts()" disabled>
				<s:message code="global.button.delete" />
			</button>
		</div>

	</form:form>

	<div id="sendMessageButtonWrapper" style="display: block" align="right">
		<button id="sendMessageButton" class="btn btn-primary" data-toggle="modal" data-target="#sendMessageModal" disabled>
			<s:message code="main.button.send_message" />
		</button>
	</div>

	<div class="form-group">
		<div class="">
			<p>&nbsp;</p>
		</div>
	</div>

</div>

<%@include file="../modal/messageSend.jsp"%>
<%@include file="../modal/confirmDialog.jsp"%>

<script type="text/javascript">
	$('#addContactLink').click(function() {
		$('#addContactForm').submit();
	});

	$('#editContactListLink').click(function() {
		if ($("#addContact").css("display") == "none") {
			$("#sendMessageButtonWrapper").hide();
			$("#addContact").show();
			$("#addContactLink").show();
			$("#deleteContactsButtonWrapper").show();
		} else {
			$("#addContact").hide();
			$("#deleteContactsButtonWrapper").hide();
			$("#addContactLink").hide();
			$("#sendMessageButtonWrapper").show();
		}

		$("#sendMessageButton").prop('disabled', true);
		$("#deleteContactsButton").prop('disabled', true);

		$("#contactListForm option:selected").each(function() {
			$(this).prop("selected", false);
		});
	});

	$("#contactList").change(function() {
		$("#sendMessageButton").prop('disabled', false);
		$("#deleteContactsButton").prop('disabled', false);
	});
	
	$(document).ready(function() {
		$('input.typeahead').typeahead({
			remote : {
				url : 'contact/outOfContactList?token=%QUERY',
			}
		});
	});

	function addContact() {
		$('#messageForm').find('input[name="recipients"]').remove();
		$("#contactListForm option:selected").each(function() {
			$('#messageForm').append("<input type='hidden' name='recipients' value='" + $(this).val() + "'  />");
		});
	}

	function deleteContacts() {
		deleteConfirm('<s:message code="contacts.popup_confirm.header" />', 
    			'<s:message code="contacts.popup_confirm.body" />', function () { $('#contactListForm').submit(); });
	}
	
</script>

<script type="text/javascript" src="static/vendors/jquery/formValidation.min.js"></script>
<script type="text/javascript" src="static/vendors/jquery/framework/bootstrap.min.js"></script>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-3-typeahead/4.0.0/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript" src="static/js/typeahead.min.js"></script>
<!-- http://www.tutorialrepublic.com/twitter-bootstrap-tutorial/bootstrap-typeahead.php -->

<!-- 

	function insert() {
		var csrf_token = $("input[name='_csrf']").val();

		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			datatype : "json",
			type : "POST",
			url : "send",

			data : JSON.stringify({
				"contents" : "contents",
				"token" : csrf_token
			}),
			beforeSend : function(xhr) {
				xhr.setRequestHeader('X-CSRF-Token', csrf_token);
			},
			success : function(response) {
				alert("ok");
			},
			error : function(request, status, error) {
				alert("ERROR!!!!!!!");
			}
		});

	}

 -->
