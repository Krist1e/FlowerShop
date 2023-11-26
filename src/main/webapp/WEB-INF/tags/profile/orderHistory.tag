<%@tag description="Profile Order History" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="profile" tagdir="/WEB-INF/tags/profile" %>

<%@attribute name="orders" required="true"
             type="by.bsuir.alekseeva.flowershop.beans.Page<by.bsuir.alekseeva.flowershop.beans.Order>" %>
<h3 class="mt-4">История заказов</h3>

<c:if test="${orders.content.size() == 0}">
    <div class="alert alert-info mt-3" role="alert">
        У вас еще нет заказов
    </div>
</c:if>
<c:if test="${orders.content.size() > 0}">
    <div class="alert alert-info mt-3" role="alert">
        Всего заказов: ${orders.totalElements}
    </div>
    <ul class="list-group mt-3">
        <c:forEach items="${orders.content}" var="order">
            <profile:order order="${order}"/>
        </c:forEach>

        <t:pagination url="/controller/profile-page?" page="${orders}"/>
    </ul>
</c:if>