<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/cart" prefix="cart" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<t:basepage title="Catalog">
    <jsp:body>
        <div class="container mt-4">
            <h2><fmt:message key="cart.cartName"/></h2>
            <cart:cart cart="${requestScope.cart}"/>
        </div>
    </jsp:body>
</t:basepage>
