<%@tag description="Header" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand" href="#">FlowerShop</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="<c:url value="/controller/catalog-page"/>">Каталог товаров</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/controller/shopping-cart-page"/>">Корзина</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>