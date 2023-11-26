<%@tag description="Coupons Table" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="coupons" tagdir="/WEB-INF/tags/coupons" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="coupons" required="true" type="java.util.List<by.bsuir.alekseeva.flowershop.beans.Coupon>" %>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Название купона</th>
        <th scope="col">Код купона</th>
        <th scope="col">Скидка</th>
        <th scope="col">Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${coupons}" var="coupon">
        <tr>
            <th scope="row">${coupon.id}</th>
            <td>${coupon.name}</td>
            <td>${coupon.code}</td>
            <td>${coupon.discount}</td>
            <td>
                <coupons:deleteButton coupon="${coupon}"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>