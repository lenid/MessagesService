<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="confirmDialogModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="modalLabel" class="modal-title">
					<span id="dialogHeader"></span>
				</h4>
			</div>

			<div class="modal-body">
				<h4 id="modalLabel" class="modal-title">
					<span id="dialogBody"></span>
				</h4>
			</div>

			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<s:message code="global.button.cancel" />
				</button>
				<button id="dialogConfirmButton" class="btn btn-default" type="button"></button>
			</div>
		</div>
	</div>
</div>

<script>
	function deleteConfirm(dialogHeader, dialogBody, foo) {
		$("#dialogHeader").text(dialogHeader);
		$("#dialogBody").text(dialogBody);
		$("#dialogConfirmButton").text('<s:message code="global.button.delete" />');
		$("#dialogConfirmButton").prop("class", "btn btn-danger");
		$("#confirmDialogModal").modal();
		$("#dialogConfirmButton").click(function() {
			foo();
		});
	}
</script>