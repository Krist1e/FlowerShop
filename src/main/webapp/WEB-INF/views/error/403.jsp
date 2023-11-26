<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<t:basepage title="403">
    <jsp:body>
        <c:if test="${sessionScope.user == null}">
            <jsp:forward page="../signIn.jsp"/>
        </c:if>
        <%--<c:if test="${sessionScope.user != null}">
            <div class="container mt-5 ms-5">
                <div class="alert alert-danger" role="alert">
                    <c:if test="${sessionScope.user.role == 'BANNED'}">
                        <h4 class="alert-heading"><fmt:message key="403.banned"/></h4>
                        <p><fmt:message key="403.sorry"/></p>
                    </c:if>
                    <c:if test="${sessionScope.user.role != 'BANNED'}">
                        <h4 class="alert-heading"><fmt:message key="403.title"/></h4>
                        <p><c:choose>
                            <c:when test="${sessionScope.user.role == 'ADMIN'}">
                                <fmt:message key="403.admins"/>
                            </c:when>
                            <c:when test="${sessionScope.user.role == 'USER'}">
                                <fmt:message key="403.users"/>
                            </c:when>
                        </c:choose>
                            <fmt:message key="403.access"/>
                        </p>
                    </c:if>
                    <hr>
                    <p class="mb-0"><fmt:message key="403.contact"/></p>
                </div>
            </div>
        </c:if>--%>
    </jsp:body>
</t:basepage>