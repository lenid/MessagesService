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
<link href="static/vendors/cdn.datatables.net/buttons/1.1.2/css/buttons.dataTables.min.css" type="text/css" rel="stylesheet">
<link href="static/css/dashboard.css" type="text/css" rel="stylesheet">

<script src="static/vendors/jquery/jquery.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="static/vendors/bootstrap/js/bootstrapValidator.min.js"></script>
<script src="static/vendors/jquery/jquery.dataTables.min.js"></script>
<script src="static/vendors/jquery/dataTables.bootstrap.min.js"></script>
<script src="static/vendors/cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script>
<script src="static/js/moment.js"></script>
</head>

<c:set var="dateFormat"><s:message code="global.dateFormat" /></c:set>

<body>
	<%@include file="include/top.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<%@include file="include/contacts.jsp"%>
			<div class="col-lg-10 col-lg-offset-2 main">
				<h3 align="center">
					<s:message code="${ pageHeader }" text="No header" />
				</h3>
				<%@include file="include/notifications.jsp"%>

				<!-- Root table -->
				<div class="row">
					<form:form id="messagesForm" method="POST" action="message/delete" modelAttribute="messages">
						<table id="mainTable" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th></th>
									<th>ID</th>
									<th><s:message code="main.table.header.from" /></th>
									<th><s:message code="main.table.header.to" /></th>
									<th><s:message code="main.table.header.time" /></th>
									<th><s:message code="main.table.header.subject" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="message" items="${ messages }">
									<tr>
										<td align="center"><input class="messageCheckbox" type="checkbox" name="messageIds" value="${ message.id }" /></td>
										<td class='clickable clickable-row' onclick="getMessage(${ message.id })">${ message.id }</td>
										<td class='clickable clickable-row' onclick="getMessage(${ message.id })">${ message.from.firstName } ${ message.from.lastName }</td>
										<td class='clickable clickable-row' onclick="getMessage(${ message.id })">${ message.to.firstName } ${ message.to.lastName }</td>
										<td class='clickable clickable-row' onclick="getMessage(${ message.id })"><fmt:formatDate value="${ message.created }"
												pattern='${ dateFormat }' /></td>
										<td class='clickable clickable-row' onclick="getMessage(${ message.id })">${ message.subject }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form:form>
				</div>

			</div>
		</div>
	</div>

	<%@include file="modal/messageGet.jsp"%>
	<%@include file="modal/confirmDialog.jsp"%>

	<script type="text/javascript">
	$(document).ready(function()
		{$('#mainTable').on('draw.dt', function () { eventDrawFired( ); }).DataTable({
			"order" : [ 4, 'desc' ],
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
					text: '<s:message code="main.button.select_all" />',
			        action: function ( e, dt, node, config ) {
			        	selectAll(); 
				}},
				{
			        text: '<s:message code="main.button.deselect_all" />',
			        action: function ( e, dt, node, config ) {
			   			deselectAll();
			    }},
			    {
			    	text: '<s:message code="global.button.delete" />',
	                className: 'red',
	                action: function ( e, dt, node, config ) {
	                	deleteConfirm('<s:message code="main.popup_confirm.header" />', 
	                			'<s:message code="main.popup_confirm.body" />', function () { $('#messagesForm').submit(); });
			    }}],
			    
			"columnDefs": 
			    [
			    	 { "type": "enum", targets: 2 },
			    	 { "type": "${ dateFormat }", targets: 5 }
	            ],
	            
	         "pageLength": <s:message code="jsp.messageListLength" />
			});
		});
	
	function eventDrawFired() {
		$(document).ready(function (){
			var delButton = $('#mainTable').DataTable().button( 2 );
		
			if ($( "input:checked" ).length == 0) {
	    		delButton.disable();
			} else {
				delButton.enable();
			}
		});
	}
	
	$(".messageCheckbox").change(function() {
		var delButton = $('#mainTable').DataTable().button( 2 );
		
		if(this.checked) {
			delButton.enable();
	    } else if ($( "input:checked" ).length == 0) {
	    	delButton.disable();
	    }
	});
	
	function selectAll() {
		$( ".messageCheckbox" ).each(function( index ) {
			$( this ).prop("checked", true);
		});
		
		var delButton = $('#mainTable').DataTable().button( 2 );
		delButton.enable();
	}
	
	function deselectAll() {
		$( ".messageCheckbox" ).each(function( index ) {
			$( this ).prop("checked", false);
		});
		
		var delButton = $('#mainTable').DataTable().button( 2 );
		delButton.disable();
	}
	
	function getMessage(id) {
		$.ajax({
			url : 'message',
			type : 'GET',
			dataType : 'json',
			data : ({
				id : id
			}),
			async : 'true',
			success : function(data) {
				showMessageModal(data);
			},
			error : function(e) {
				alert("error" + e);
			}
		});
	}
	</script>

</body>
</html>

