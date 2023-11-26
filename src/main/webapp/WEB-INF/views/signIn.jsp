<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<t:basepage title="Sign In">
    <jsp:body>
        <div class="container mt-4 d-flex justify-content-center">
            <div class="col-sm-6">
                <h2>Вход в аккаунт</h2>

                <form action="<c:url value="/controller/sign-in"/>" method="post">
                    <div class="form-group">
                        <label for="username">Логин</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Введите ваш логин" <c:if test="${not empty requestScope.error}">value="${requestScope.username}"</c:if>
                               required autofocus="autofocus">
                    </div>
                    <div class="form-group">
                        <label for="password">Пароль</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Введите ваш пароль"
                               required>
                    </div>

                    <c:if test="${not empty requestScope.error}">
                        <div id="error-message" class="alert alert-danger">
                            <p class="text-danger">${requestScope.error}</p>
                        </div>
                    </c:if>

                    <button type="submit" class="btn btn-primary">Войти</button>
                </form>

                <div class="mt-3">
                    <p>Еще не зарегистрированы? <a href="<c:url value="/controller/sign-up-page"/>">Зарегистрируйтесь</a>.</p>
                </div>
            </div>
        </div>

    </jsp:body>
</t:basepage>