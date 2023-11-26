<%@tag description="Cart Item" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%@attribute name="item" required="true" type="by.bsuir.alekseeva.flowershop.beans.Item" %>
<%@attribute name="coupon" required="true" type="by.bsuir.alekseeva.flowershop.beans.Coupon" %>
<tr>
    <td>${item.product.name}</td>
    <td><fmt:formatNumber value="${item.product.discountedPrice}" type="currency"/></td>
    <td>
        <a href="<c:url value="/controller/update-quantity?id=${item.id}&changed=-1"/>"
           class="btn btn-sm btn-primary ml-2 <c:if test="${item.quantity == 1}">disabled</c:if>"><i
                class="bi bi-dash"></i></a>
        <span class="mx-2">${item.quantity}</span>
        <a href="<c:url value="/controller/update-quantity?id=${item.id}&changed=1"/>"
           class="btn btn-sm btn-primary"><i class="bi bi-plus"></i></a>
    </td>
    <td><fmt:message key="product.price"/><fmt:formatNumber value="${item.price}" type="currency"/></td>
    <td><a href="<c:url value="/controller/update-quantity?id=${item.id}&quantity=0"/>" class="btn btn-sm btn-danger ml-2"><i class="bi bi-trash"></i></a></td>
</tr>