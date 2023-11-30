<%@tag description="Header" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand" href="#">FlowerShop</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="<c:url value="/controller/catalog-page"/>"><fmt:message key="header.catalog"/></a>
                    </li>

                    <c:if test="${sessionScope.user != null}">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/controller/shopping-cart-page"/>"><fmt:message key="header.cart"/></a>
                        </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller/profile-page"/>"><fmt:message key="header.profile"/> (${sessionScope.user.username})</a>
                    </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/controller/sign-out"/>"><fmt:message key="header.signOut"/></a>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false" id="adminPanel">
                                <fmt:message key="header.adminPanel"/>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="adminPanel">
                                <li><a class="dropdown-item" href="<c:url value="/controller/users-page"/>"><fmt:message key="header.adminPanel.users"/></a></li>
                                <li><a class="dropdown-item" href="<c:url value="/controller/coupons-page"/>"><fmt:message key="header.adminPanel.coupons"/></a></li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller/sign-in-page"/>"><fmt:message key="header.signIn"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller/sign-up-page"/>"><fmt:message key="header.signUp"/></a>
                    </li>
                    </c:if>
                    <li class="nav-item dropdown border-start ms-2">
                        <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false" id="navbarLang">
                            <fmt:message key="header.language"/>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarLang">
                            <li><a class="dropdown-item" onclick="changeLocale('en-US')">English</a></li>
                            <li><a class="dropdown-item" onclick="changeLocale('ru-RU')">Русский</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<script>
    function changeLocale(locale) {
        fetch('/controller/change-locale?locale=' + locale, {
            method: 'POST'
        }).then(() => {
            location.reload()
        })
    }
</script>