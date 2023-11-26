<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/cart" prefix="cart" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<t:basepage title="Catalog">
    <jsp:body>
        <div class="container mt-4">
            <h2>Корзина</h2>
            <cart:cart cart="${requestScope.cart}"/>
        </div>
    </jsp:body>
</t:basepage>
