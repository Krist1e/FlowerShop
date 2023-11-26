<%@tag description="Edit Product" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="product" required="true" type="by.bsuir.alekseeva.flowershop.beans.Product" %>
<c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
    <a class="btn btn-primary" data-toggle="modal" data-target="#editProductModal"><i class="bi bi-pencil"></i></a>
    <div class="modal fade" id="editProductModal" tabindex="-1" role="dialog" aria-labelledby="editProductModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editProductModalLabel">Изменение товара</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="<c:url value="/controller/update-product?id=${product.id}"/>" method="post">
                        <div class="form-group">
                            <label for="name">Название товара <span class="text-danger"> *</span></label>
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="Введите название товара" required
                                   value="${product.name}">
                        </div>
                        <div class="form-group">
                            <label for="description">Описание товара</label>
                            <textarea class="form-control" id="description" name="description" rows="3"
                                      placeholder="Введите описание товара">${product.description}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="price">Цена товара <span class="text-danger"> *</span></label>
                            <input type="number" class="form-control" id="price" name="price"
                                   placeholder="Введите цену товара" required
                                   value="${product.price}">
                        </div>
                        <div class="form-group">
                            <label for="image">Ссылка на изображение</label>
                            <input type="text" class="form-control" id="image" name="image"
                                   placeholder="Введите ссылку на изображение"
                                   value="${product.imagePath}">
                        </div>
                        <div class="form-group">
                            <label for="discount">Скидка</label>
                            <input type="number" class="form-control" id="discount" name="discount"
                                   placeholder="Введите скидку"
                                   value="${product.discount}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:if>