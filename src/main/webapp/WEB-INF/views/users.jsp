<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/users" prefix="u" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<t:basepage title="Users">
    <jsp:body>
        <div class="container mt-4">
            <h2>Управление пользователями</h2>

            <u:users users="${requestScope.users.content}"/>

            <t:pagination url="/controller/users-page?" page="${requestScope.users}"/>
        </div>
    </jsp:body>
</t:basepage>