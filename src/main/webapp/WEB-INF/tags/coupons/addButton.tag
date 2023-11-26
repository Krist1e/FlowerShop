<%@tag description="Add Coupon" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#createCouponModal">
    Создать новый купон
</button>

<div class="modal" id="createCouponModal" tabindex="-1" role="dialog" aria-labelledby="createCouponModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createCouponModalLabel">Создание нового купона</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="<c:url value="/controller/add-coupon"/>" method="post">
                    <div class="form-group">
                        <label for="name">Название купона</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="code">Код купона</label>
                        <input type="text" class="form-control" id="code" name="code" required>
                    </div>
                    <div class="form-group">
                        <label for="discount">Скидка</label>
                        <input type="text" class="form-control" id="discount" name="discount" required
                               pattern="^0(\.\d+)?$|^1$" step="0.01">
                    </div>
                    <button type="submit" class="btn btn-primary">Создать купон</button>
                </form>
            </div>
        </div>
    </div>
</div>