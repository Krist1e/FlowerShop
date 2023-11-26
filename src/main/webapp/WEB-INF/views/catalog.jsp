<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<t:basepage title="Catalog">
    <jsp:body>
        <div class="row">
            <c:forEach items="${requestScope.products.content}" var="product">
                <t:productCard product="${product}"/>
            </c:forEach>

            <c:if test="${empty requestScope.products.content}">
                <div class="col-12">
                    <div class="alert alert-info" role="alert">
                        <fmt:message key="catalog.isEmpty"/>
                    </div>
                </div>
            </c:if>
        </div>
        <div class="row">
            <t:pagination url="/controller/catalog-page?" page="${requestScope.products}"/>
        </div>
    </jsp:body>
</t:basepage>