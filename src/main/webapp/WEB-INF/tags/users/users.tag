<%@tag description="Users" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="users" tagdir="/WEB-INF/tags/users" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="users" required="true" type="java.util.List<by.bsuir.alekseeva.flowershop.beans.User>" %>
<table class="table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Имя пользователя</th>
        <th>Email</th>
        <th>Роль</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>
                <c:choose>
                    <c:when test="${user.role == 'ADMIN'}">
                        <span class="badge badge-danger">ADMIN</span>
                    </c:when>
                    <c:when test="${user.role == 'USER'}">
                        <span class="badge badge-success">USER</span>
                    </c:when>
                    <c:when test="${user.role == 'BANNED_USER'}">
                        <span class="badge badge-danger">BANNED_USER</span>
                    </c:when>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${user.role == 'USER'}">
                        <a href="<c:url value="/controller/ban?id=${user.id}"/>" class="btn btn-danger btn-ban" style="min-width: 150px;">
                            Заблокировать
                        </a>
                    </c:when>
                    <c:when test="${user.role == 'BANNED_USER'}">
                        <a href="<c:url value="/controller/unban?id=${user.id}"/>" class="btn btn-success btn-unban" style="min-width: 150px;">
                            Разблокировать
                        </a>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>