<%@tag description="Profile Order" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="order" required="true" type="by.bsuir.alekseeva.flowershop.beans.Order" %>
<li class="list-group-item">
    <div class="row">
        <div class="col-2">
            <p><fmt:message key="order.orderId"/> ${order.id}</p>
        </div>
        <div class="col-2">
            <p><fmt:message key="order.orderDate"/> <t:date date="${order.date}"/></p>
        </div>
        <div class="col-2">
            <p><fmt:message key="order.totalPrice"/> <fmt:formatNumber value="${order.discountedPrice}" type="currency"/></p>
        </div>
        <div class="col-2">
            <p><fmt:message key="order.orderStatus"/> <c:choose>
                <c:when test="${order.status == 'PAID'}">
                    <fmt:message key="order.status.paid"/>
                </c:when>
                <c:when test="${order.status == 'DELIVERED'}">
                    <fmt:message key="order.status.delivered"/>
                </c:when>
                <c:when test="${order.status == 'CANCELED'}">
                    <fmt:message key="order.status.canceled"/>
                </c:when>
            </c:choose></p>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <p><fmt:message key="order.orderAddress"/> ${order.address}</p>
        </div>
        <c:if test="${order.coupon != null}">
            <div class="col-12">
                <p><fmt:message key="order.coupon"/> ${order.coupon.code}</p>
                <p><fmt:message key="order.coupon.discount"/> ${order.coupon.discount}</p>
            </div>
        </c:if>
    </div>
    <div class="row">
        <div class="col-12">
            <p><fmt:message key="order.products"/></p>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="order.name"/></th>
                    <th scope="col"><fmt:message key="order.price"/></th>
                    <th scope="col"><fmt:message key="order.quantity"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${order.orderItems}" var="orderItem">
                    <tr>
                        <td>${orderItem.product.name}</td>
                        <td><fmt:formatNumber value="${orderItem.product.discountedPrice}" type="currency"/></td>
                        <td>${orderItem.quantity}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</li>