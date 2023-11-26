<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<t:basepage title="Sign Up">
    <jsp:body>
        <div class="container mt-4 d-flex justify-content-center">
        <div class="col-sm-6">
            <h2><fmt:message key="signUp.title"/></h2>

            <form action="<c:url value="/controller/sign-up"/>" method="post">
                <div class="form-group">
                    <label for="username"><fmt:message key="signUp.name"/></label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="<fmt:message key="signUp.enterName"/>" required autofocus="autofocus">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="<fmt:message key="signUp.enterEmail"/>" required>
                </div>
                <div class="form-group">
                    <label for="password">с</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="<fmt:message key="signUp.enterPassword"/>" required>
                </div>
                <div class="form-group">
                    <label for="confirmPassword"><fmt:message key="signUp.confirmationPassword"/></label>
                    <input type="password" class="form-control" id="confirmPassword"
                           placeholder="<fmt:message key="signUp.confirmPassword"/>" required>
                </div>

                <div id="passwordMatchError" class="alert alert-danger" style="display: none;"><fmt:message key="signUp.passwordsNotMatch"/></div>
                <c:if test="${not empty requestScope.error}">
                    <div id="error-message" class="alert alert-danger">
                        <p class="text-danger">${requestScope.error}</p>
                    </div>
                </c:if>

                <button type="submit" class="btn btn-primary" onclick="checkPasswordMatch()"><fmt:message key="signUp.signUp"/></button>
            </form>

            <div class="mt-3">
                <p><fmt:message key="signUp.alreadySignedUp"/> <a href="<c:url value="/controller/sign-in-page"/>"><fmt:message key="signUp.signIn"/></a></p>
            </div>

            <script>
                function checkPasswordMatch() {
                    var password = document.getElementById("password").value;
                    var confirmPassword = document.getElementById("confirmPassword").value;

                    if (password !== confirmPassword) {
                        document.getElementById("passwordMatchError").style.display = "block";
                        return false;
                    } else {
                        document.getElementById("passwordMatchError").style.display = "none";
                        return true;
                    }
                }
            </script>
        </div>
    </jsp:body>
</t:basepage>