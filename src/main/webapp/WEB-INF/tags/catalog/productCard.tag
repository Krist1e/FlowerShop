<%@tag description="Product Card" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="catalog" tagdir="/WEB-INF/tags/catalog" %>

<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="product" required="true" type="by.bsuir.alekseeva.flowershop.beans.Product" %>
<div class="col-md-4 mb-4">
    <div class="card h-100 rounded-4">
        <img src="${product.imagePath}" class="card-img-top" alt="${product.name}">
        <div class="card-body">
            <h5 class="card-title">${product.name}</h5>
            <p class="card-text">${product.description}</p>
            <p class="card-text">Цена: <c:if test="${product.discount != 0.0}"><span
                    class="text-muted"><s>${product.price}</s></span> <fmt:formatNumber
                    value="${product.price * (1 - product.discount)}" maxFractionDigits="2"/></c:if><c:if
                    test="${product.discount == 0.0}">${product.price}</c:if> руб.</p>
            <div class="d-flex justify-content-between align-items-center">
                <a href="<c:url value="/controller/add-to-cart?product=${product.id}"/>" class="btn btn-primary"><fmt:message key="productCard.addToCart"/></a>
                <div class="btn-group">
                    <catalog:editButton product="${product}"/>
                    <catalog:deleteButton product="${product}"/>
                </div>
            </div>
        </div>
    </div>
</div>