<%@tag description="Cart Item" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<%@attribute name="cart" required="true" type="by.bsuir.alekseeva.flowershop.beans.ShoppingCart" %>

<c:if test="${cart.coupon != null}">
    <div class="alert alert-success" role="alert">
        Купон ${cart.coupon.code} применен
    </div>
</c:if>
<c:if test="${cart.coupon == null}">
    <div class="input-group mb-3" style="width: 300px">
        <div class="input-group-prepend">
            <span class="input-group-text">Купон</span>
        </div>
        <input type="text" class="form-control" id="coupon" placeholder="Код купона" required pattern="^[0-9]{5}$">
        <div class="input-group-append">
            <button class="btn btn-outline-secondary" type="button" onclick="applyCoupon()">Применить</button>
        </div>
    </div>
    <script>
        function applyCoupon() {
            const coupon = document.getElementById("coupon").value;
            const url = "/controller/apply-coupon?coupon=" + coupon;
            fetch(url)
                .then(response => {
                    if (response.ok) {
                        location.reload();
                    } else {
                        const alert = document.createElement("div");
                        alert.classList.add("alert");
                        alert.classList.add("alert-danger");
                        alert.setAttribute("role", "alert");
                        alert.innerText = "Купон не найден";
                        document.getElementById("coupon").parentElement.appendChild(alert);

                        setTimeout(() => {
                            alert.remove();
                        }, 3000);
                    }
                });
        }
    </script>
</c:if>
