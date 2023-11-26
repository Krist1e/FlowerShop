<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<t:basepage title="Sign In">
    <jsp:body>
        <div class="container mt-4 d-flex justify-content-center">
            <div class="col-sm-6">
                <h2><fmt:message key="signIn.title"/></h2>

                <form action="<c:url value="/controller/sign-in"/>" method="post">
                    <div class="form-group">
                        <label for="username"><fmt:message key="signIn.login"/></label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="<fmt:message key="signIn.enterLogin"/>" <c:if test="${not empty requestScope.error}">value="${requestScope.username}"</c:if>
                               required autofocus="autofocus">
                    </div>
                    <div class="form-group">
                        <label for="password"><fmt:message key="signIn.password"/></label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="<fmt:message key="signIn.enterPassword"/>"
                               required>
                    </div>

                    <c:if test="${not empty requestScope.error}">
                        <div id="error-message" class="alert alert-danger">
                            <p class="text-danger">${requestScope.error}</p>
                        </div>
                    </c:if>

                    <button type="submit" class="btn btn-primary"><fmt:message key="signIn.signIn"/></button>
                </form>

                <div class="mt-3">
                    <p><fmt:message key="signIn.notRegistered"/> <a href="<c:url value="/controller/sign-up-page"/>"><fmt:message key="signIn.signUp"/></a>.</p>
                </div>
            </div>
        </div>

    </jsp:body>
</t:basepage>