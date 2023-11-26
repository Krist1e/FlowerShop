<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/catalog" prefix="catalog" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<t:basepage title="Catalog">
    <jsp:body>
        <div class="row">
            <div class="col-md-12 d-flex justify-content-between">
                <h4>Каталог товаров</h4>
                <catalog:addButton/>
            </div>
        </div>
        <div class="row">
            <catalog:catalog products="${requestScope.products.content}"/>
        </div>
        <div class="row">
            <t:pagination url="/controller/catalog-page?" page="${requestScope.products}"/>
        </div>
    </jsp:body>
</t:basepage>