<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="sendMessageModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h5 class="modal-title">
					<s:message code="main.popup_new_message.header" />
				</h5>
			</div>

			<div class="modal-body">
				<form:form id="messageForm" class="form-horizontal" method="post" action="message/send" modelAttribute="message" role="form">

					<div class="form-group">
						<label class="col-xs-3 control-label"><s:message code="main.popup_new_message.subject" /></label>
						<div class="col-xs-9">
							<form:input class="form-control" name="subject" type="text" path="subject" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-xs-3 control-label"><s:message code="main.popup_new_message.body" /></label>
						<div class="col-xs-9">
							<form:textarea id="bodyValue" class="form-control" name="body" path="body" rows="5" cols="40" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-xs-9 col-xs-offset-3" align="right">
							<button class="btn btn-default" type="button" data-dismiss="modal">
								<s:message code="global.button.cancel" />
							</button>
							<button class="btn btn-primary" type="submit" onclick="addContact()">
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
	$(document).ready(function() {
		$('#messageForm').formValidation({
			framework : 'bootstrap',
			excluded : ':disabled',
			icon : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				subject : {
					validators : {
						notEmpty : {
							message : '<s:message code="main.popup_new_message.validator.subject_required" />'
						}
					}
				},
				body : {
					validators : {
						notEmpty : {
							message : '<s:message code="main.popup_new_message.validator.body_required" />'
						}
					}
				}
			}
		});
	});
	
</script>