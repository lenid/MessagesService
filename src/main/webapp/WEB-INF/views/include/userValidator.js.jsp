<script type="text/javascript">
	function validateUserForm(formId, excludePasswdValid) {
		$(formId).formValidation({
			framework : 'bootstrap',
			icon : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {

				firstName : {
					validators : {
						notEmpty : {
							message : '<s:message code="user.valid.not_empty" />'
						}
					}
				},

				lastName : {
					validators : {
						notEmpty : {
							message : '<s:message code="user.valid.not_empty" />'
						}
					}
				},

				ssoId : {
					validators : {
						notEmpty : {
							message : '<s:message code="user.valid.not_empty" />'
						},
						stringLength : {
							message : '<s:message code="user.valid.size.4_20" />',
							min : 4,
							max : 20,
						},
						remote : {
							url : 'isDuplicatedSsoId',
							type : 'GET',
							message : '<s:message code="user.valid.sso_id.duplicate" />',
							data : function(validator, $field, value) {
								return {
									id : $field.parents('form').find('#id').val(),
									ssoId : value
								};
							}
						}
					}
				},

				email : {
					validators : {
						emailAddress : {
							message : '<s:message code="user.valid.email" />'
						},
						notEmpty : {
							message : '<s:message code="user.valid.not_empty" />'
						}
					}
				},

				oldPasswd : {
					validators : {
						notEmpty : {
							message : '<s:message code="user.valid.not_empty" />'
						}
					}
				},

				newPasswd : {
					validators : {
						notEmpty : {
							message : '<s:message code="user.valid.not_empty" />'
						},
						stringLength : {
							message : '<s:message code="user.valid.size.4_20" />',
							min : 4,
							max : 20,
						}
					}
				},

				confirmPasswd : {
					validators : {
						identical : {
							field : 'newPasswd',
							message : '<s:message code="user.valid.not_the_same" />'
						}
					}
				}

			}
		});

		if (excludePasswdValid) {
			$(formId).formValidation('removeField', 'newPasswd');
		}

	};
</script>