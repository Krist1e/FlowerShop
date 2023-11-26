<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<t:basepage title="500">
    <jsp:body>
        <h1 class="display-1">500</h1>
        <p class="lead"><fmt:message key="500.title"/></p>
        <p><fmt:message key="500.sorry"/></p>
        <a class="btn btn-primary" href="<c:url value="/controller/catalog-page"/>"><fmt:message key="500.return"/></a>
    </jsp:body>
</t:basepage>