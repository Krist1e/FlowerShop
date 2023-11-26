<%@tag description="Delete Coupon" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<%@attribute name="coupon" required="true" type="by.bsuir.alekseeva.flowershop.beans.Coupon" %>
<a class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteCouponModal${coupon.id}">Удалить</a>
<div class="modal fade" id="deleteCouponModal${coupon.id}" tabindex="-1" role="dialog" aria-labelledby="deleteCouponModalLabel${coupon.id}" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteCouponModalLabel${coupon.id}">Удаление купона</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Вы действительно хотите удалить купон?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                <a class="btn btn-danger" href="<c:url value="/controller/delete-coupon?id=${coupon.id}"/>">Удалить</a>
            </div>
        </div>
    </div>
</div>