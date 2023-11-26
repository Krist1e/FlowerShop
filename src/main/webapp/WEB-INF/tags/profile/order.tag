<%@tag description="Profile Order" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="order" required="true" type="by.bsuir.alekseeva.flowershop.beans.Order" %>
<li class="list-group-item">
    <div class="row">
        <div class="col-2">
            <p>Номер заказа: ${order.id}</p>
        </div>
        <div class="col-2">
            <p>Дата заказа: <t:date date="${order.date}"/></p>
        </div>
        <div class="col-2">
            <p>Сумма заказа: ${order.totalPrice}</p>
        </div>
        <div class="col-2">
            <p>Статус заказа: ${order.status}</p>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <p>Адрес доставки: ${order.address}</p>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <p>Товары:</p>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th scope="col">Название</th>
                    <th scope="col">Цена</th>
                    <th scope="col">Количество</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${order.orderItems}" var="orderItem">
                    <tr>
                        <td>${orderItem.product.name}</td>
                        <td>${orderItem.product.price}</td>
                        <td>${orderItem.quantity}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</li>