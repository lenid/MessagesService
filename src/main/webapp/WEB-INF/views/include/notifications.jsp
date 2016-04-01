<%@page import="org.apache.taglibs.standard.tag.common.core.ForEachSupport"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<c:forEach var="notification" items="${ notifications }">
	<c:if test="${notification.type == 'SUCCESS'}">
		<div class="alert alert-success"><strong><s:message code="global.notification.success" /></strong> <s:message code="${ notification.text }" /></div>
	</c:if>
	<c:if test="${notification.type == 'WARNING'}">
		<div class="alert alert-warning"><strong><s:message code="global.notification.warning" /></strong> <s:message code="${ notification.text }" /></div>
	</c:if>
	<c:if test="${notification.type == 'ERROR'}">
		<div class="alert alert-danger"><strong><s:message code="global.notification.error" /></strong> <s:message code="${ notification.text }" /></div>
	</c:if>
	<c:if test="${notification.type == 'INFO'}">
		<div class="alert alert-info"><strong><s:message code="global.notification.info" /></strong> <s:message code="${ notification.text }" /></div>
	</c:if>
</c:forEach>