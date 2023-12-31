<%@tag description="Delete Product" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="product" required="true" type="by.bsuir.alekseeva.flowershop.beans.Product" %>
<c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
    <a class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteProductModal${product.id}"><i class="bi bi-trash"></i></a>
    <div class="modal fade" id="deleteProductModal${product.id}" tabindex="-1" role="dialog" aria-labelledby="deleteProductModalLabel${product.id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel${product.id}">Удаление товара</h5>
                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Вы действительно хотите удалить товар?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                    <a href="<c:url value="/controller/delete-product?id=${product.id}"/>" class="btn btn-danger">Удалить</a>
                </div>
            </div>
        </div>
    </div>
</c:if>