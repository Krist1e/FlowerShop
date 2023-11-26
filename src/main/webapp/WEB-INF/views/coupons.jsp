<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/coupons" prefix="coupons" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<t:basepage title="Coupons">
    <jsp:body>
        <h2>Управление купонами</h2>
        <coupons:addButton/>

        <coupons:table coupons="${requestScope.coupons.content}"/>

        <t:pagination url="/controller/coupons-page?" page="${requestScope.coupons}"/>
    </jsp:body>
</t:basepage>
