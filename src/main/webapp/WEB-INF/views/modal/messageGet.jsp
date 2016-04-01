<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="messageModal" class="modal fade">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="headerMessageModal" class="modal-title"></h4>
			</div>
			<div id="bodyMessageModal" class="modal-body"></div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<s:message code="global.button.close" />
				</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	function showMessageModal(data) {
		var formated_date = moment(data.created).format('<s:message code="global.dateFormat.js" />');
		var header = '<s:message code="main.popup_message.from" />' + ": " + data.from.firstName + " " + data.from.lastName;
		header += ", " + formated_date;
		header += ", " + '<s:message code="main.popup_message.subject" />' + ": \"" + data.subject + "\"";
		$('#headerMessageModal').html(header);
		$('#bodyMessageModal').html(data.body);
		$('#messageModal').modal('show');
	}
	
</script>