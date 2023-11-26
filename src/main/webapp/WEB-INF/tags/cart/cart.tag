<%@tag description="Cart" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="cart" tagdir="/WEB-INF/tags/cart" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="cart" required="true" type="by.bsuir.alekseeva.flowershop.beans.ShoppingCart" %>
<table class="table">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="cart.name"/></th>
        <th scope="col"><fmt:message key="cart.price"/></th>
        <th scope="col"><fmt:message key="cart.quantity"/></th>
        <th scope="col"><fmt:message key="cart.inTotal"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cart.cartItems}" var="item">
        <cart:cartItem item="${item}"/>
    </c:forEach>
    </tbody>
    <c:if test="${cart.cartItems.size() > 0}">
        <tfoot>
        <tr>
            <td colspan="3" class="text-right"><fmt:message key="cart.totalPrice"/></td>
            <td>${cart.totalPrice}</td>
        </tr>
        </tfoot>
    </c:if>
    <c:if test="${cart.cartItems.size() == 0}">
        <tfoot>
        <tr>
            <td colspan="4" class="text-center"><fmt:message key="cart.isEmpty"/></td>
        </tr>
        </tfoot>
    </c:if>
</table>

<c:if test="${cart.cartItems.size() > 0}">
    <cart:orderButton/>
</c:if>
