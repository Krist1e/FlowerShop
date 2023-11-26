<%@tag description="Product Card" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
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

            <a href="<c:url value="/controller/add-to-cart?product=${product.id}"/>" class="btn btn-primary"><fmt:message key="productCard.addToCart"/></a>
        </div>
    </div>
</div>