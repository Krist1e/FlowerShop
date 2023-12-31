<%@tag description="Add Product" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
    <a class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createProductModal">Добавить товар</a>
    <div class="modal fade" id="createProductModal" tabindex="-1" role="dialog" aria-labelledby="createProductModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="createProductModalLabel">Создание товара</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="<c:url value="/controller/add-product"/>" method="post">
                        <div class="form-group">
                            <label for="name">Название товара <span class="text-danger"> *</span></label>
                            <input type="text" class="form-control" id="name" name="name" placeholder="Введите название товара" required>
                        </div>
                        <div class="form-group">
                            <label for="description">Описание товара</label>
                            <textarea class="form-control" id="description" name="description" rows="3" placeholder="Введите описание товара"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="price">Цена товара <span class="text-danger"> *</span></label>
                            <input type="number" class="form-control" id="price" name="price" placeholder="Введите цену товара" required>
                        </div>
                        <div class="form-group">
                            <label for="image">Ссылка на изображение</label>
                            <input type="text" class="form-control" id="image" name="image" placeholder="Введите ссылку на изображение">
                        </div>
                        <div class="form-group">
                            <label for="discount">Скидка</label>
                            <input type="number" class="form-control" id="discount" name="discount" placeholder="Введите скидку" pattern="^0(\.\d+)?$|^1$" step="0.01">
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