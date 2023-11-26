<%@tag description="Cart Item" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<%@attribute name="item" required="true" type="by.bsuir.alekseeva.flowershop.beans.Item" %>
<tr>
    <td>${item.product.name}</td>
    <td>${item.product.price}</td>
    <td>
        <a href="<c:url value="/controller/update-quantity?id=${item.id}&changed=-1"/>"
           class="btn btn-sm btn-primary ml-2 <c:if test="${item.quantity == 1}">disabled</c:if>"><i
                class="bi bi-dash"></i></a>
        <span class="mx-2">${item.quantity}</span>
        <a href="<c:url value="/controller/update-quantity?id=${item.id}&changed=1"/>"
           class="btn btn-sm btn-primary"><i class="bi bi-plus"></i></a>
    </td>
    <td>${item.quantity * item.product.price}</td>
    <td><a href="<c:url value="/controller/update-quantity?id=${item.id}&quantity=0"/>" class="btn btn-sm btn-danger ml-2"><i class="bi bi-trash"></i></a></td>
</tr>