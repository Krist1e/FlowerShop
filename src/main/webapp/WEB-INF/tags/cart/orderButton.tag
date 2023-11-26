<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>


<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#checkoutModal"><fmt:message
        key="orderButton.placeOrder"/></button>

<div class="modal fade" id="checkoutModal" tabindex="-1" role="dialog" aria-labelledby="checkoutModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="checkoutModalLabel"><fmt:message key="orderButton.orderPlacement"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="<c:url value="/controller/place-order"/>" method="post">
                    <div class="form-group">
                        <label for="address"><fmt:message key="orderButton.address"/><span class="required-field"
                                                                                           style="color: red"> *</span></label>
                        <input type="text" class="form-control" id="address" name="address"
                               placeholder="<fmt:message key="orderButton.enterAddress"/>"
                               required>
                    </div>
                    <div class="form-group">
                        <label for="phone"><fmt:message key="orderButton.telephone"/><span class="required-field"
                                                                                           style="color: red"> *</span></label>
                        <input type="tel" class="form-control" id="phone" name="phone"
                               placeholder="<fmt:message key="orderButton.enterTelephone"/>"
                               required>
                    </div>
                    <div class="form-group">
                        <label for="comments"><fmt:message key="orderButton.comment"/></label>
                        <textarea class="form-control" id="comments" name="comments"
                                  placeholder="<fmt:message key="orderButton.enterComment"/>"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                                key="orderButton.close"/></button>
                        <button type="submit" class="btn btn-primary"><fmt:message
                                key="orderButton.finishOrder"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>