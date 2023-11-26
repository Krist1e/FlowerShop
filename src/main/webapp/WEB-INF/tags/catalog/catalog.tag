<%@tag description="Catalog" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="catalog" tagdir="/WEB-INF/tags/catalog" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="products" required="true" type="java.util.List<by.bsuir.alekseeva.flowershop.beans.Product>" %>

<c:forEach items="${products}" var="product">
    <catalog:productCard product="${product}"/>
</c:forEach>

<c:if test="${products.size() == 0}">
    <div class="col-12">
        <div class="alert alert-info" role="alert">
            <fmt:message key="catalog.isEmpty"/>
        </div>
    </div>
</c:if>