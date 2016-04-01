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
<link href="static/vendors/bootstrap/css/dataTables.bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="static/vendors/bootstrap/css/dataTables.bootstrap.min.css" type="text/css" rel="stylesheet">
<link href="static/vendors/cdn.datatables.net/buttons/1.1.2/css/buttons.dataTables.min.css" type="text/css" rel="stylesheet">
<link href="../static/css/dashboard.css" type="text/css" rel="stylesheet">

<script src="static/vendors/jquery/jquery.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrapValidator.min.js"></script>
<script src="static/vendors/jquery/formValidation.min.js"></script>
<script src="static/vendors/jquery/framework/bootstrap.min.js"></script>
<script src="static/vendors/jquery/jquery.dataTables.min.js"></script>
<script src="static/vendors/jquery/dataTables.bootstrap.min.js"></script>
<script src="static/vendors/cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script>
<script src="static/js/moment.js"></script>
</head>

<c:set var="dateFormat">
	<s:message code="global.dateFormat" />
</c:set>

<body>
	<%@include file="include/top.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 main">
				<h3 align="center">
					<s:message code="${ pageHeader }" text="No header" />
				</h3>
				<%@include file="include/notifications.jsp"%>

				<!-- Users table -->
				<div class="row">
					<table id="mainTable" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th><s:message code="users.table.header.name_first" /></th>
								<th><s:message code="users.table.header.name_last" /></th>
								<th><s:message code="users.table.header.email" /></th>
								<th><s:message code="users.table.header.sso_id" /></th>
								<th><s:message code="users.table.header.created" /></th>
								<th><s:message code="users.table.header.role" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${ users }">
								<tr id="userId_${ user.id }" class='clickable clickable-row' onclick="getUser(${ user.id })">
									<td>${ user.id }</td>
									<td>${ user.firstName }</td>
									<td>${ user.lastName }</td>
									<td>${ user.email }</td>
									<td>${ user.ssoId }</td>
									<td><nobr>
											<fmt:formatDate value="${ user.created }" pattern='${ dateFormat }' />
										</nobr></td>
									<td><c:forEach var="profile" items="${ user.userProfiles }">
											<span>${ profile.type }</span>
										</c:forEach></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- User modal -->
	<div id="userFormModal" class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div id="modalContentUserForm" class="modal-content"></div>
		</div>
	</div>

	<%@include file="modal/confirmDialog.jsp"%>

	<script type="text/javascript">
	
	function deleteUser() {
		deleteConfirm('<s:message code="users.popup_confirm.header" />', '<s:message code="users.popup_confirm.body" />', function () { 
			$('#userForm').attr("action", "user/delete");
			$('#userForm')[0].submit();
		});
	}
	
	function getUser(id) {
		$.ajax({
			url : "user/" + id,
			type : 'GET',
			async : 'true',
			success : function(data) {
				$('#modalContentUserForm').html(data);
				validateUserForm('#userForm', true);
				$('#userFormModal').modal('show');
			},
			error : function(e) {
				//alert("error" + e);
			}
		});
	}
	
	$(document).ready(function()
		{$('#mainTable').DataTable({
			"order" : [ 4, 'asc' ],
			"dom" : 'Bfrtp',
			"language": {
				 "aria": {
				        "sortAscending":  '<s:message code="table.sort_ascending" />',
				        "sortDescending": '<s:message code="table.sort_descending" />'
				    },
					"decimal":        '<s:message code="table.decimal" />',
				    "emptyTable":     '<s:message code="table.empty_table" />',
				    "info":           '<s:message code="table.info" />',
				    "infoEmpty":      '<s:message code="table.info_empty" />',
				    "infoFiltered":   '<s:message code="table.info_filtered" />',
				    "infoPostFix":    '<s:message code="table.info_post_fix" />',
				    "lengthMenu":     '<s:message code="table.length_menu" />',
				    "loadingRecords": '<s:message code="table.loading_records" />',
				    "thousands":      '<s:message code="table.thousands" />',
				    "paginate": {
				        "first":      '<s:message code="table.first" />',
				        "last":       '<s:message code="table.last" />',
				        "next":       '<s:message code="table.next" />',
				        "previous":   '<s:message code="table.previous" />'
				    },
				    "processing":     '<s:message code="table.processing" />',
				    "search":         '<s:message code="table.search" />',
				    "zeroRecords":    '<s:message code="table.zero_records" />'
	        },
	        "buttons" : 
				[{
					text: '<s:message code="users.button.user_create" />',
			        action: function ( e, dt, node, config ) {
			        	getUser(0); 
				}}],
			    
			"columnDefs": 
			    [
			    	 { "type": "enum", targets: 1 },
	            ],
	            
	         "pageLength": <s:message code="jsp.userListLength" />
			});
		});
	</script>
	<%@include file="include/userValidator.js.jsp"%>
</body>
</html>
