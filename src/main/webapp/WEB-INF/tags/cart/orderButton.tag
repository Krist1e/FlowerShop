<div class="text-right">
    <button class="btn btn-primary" data-toggle="modal" data-target="#checkoutModal">Оформить заказ</button>
</div>
<div class="modal fade" id="checkoutModal" tabindex="-1" role="dialog" aria-labelledby="checkoutModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="checkoutModalLabel">Оформление заказа</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="address">Адрес доставки<span class="required-field"
                                                                 style="color: red"> *</span></label>
                        <input type="text" class="form-control" id="address" placeholder="Введите адрес доставки"
                               required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Телефон<span class="required-field" style="color: red"> *</span></label>
                        <input type="tel" class="form-control" id="phone" placeholder="Введите ваш номер телефона"
                               required>
                    </div>
                    <div class="form-group">
                        <label for="comments">Комментарий</label>
                        <textarea class="form-control" id="comments" placeholder="Введите комментарий"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                <button type="button" class="btn btn-primary" onclick="checkout()">Завершить заказ</button>
            </div>
        </div>
    </div>
</div>
<script>
    function checkout() {
        let address = $('#address').val();
        let phone = $('#phone').val();
        let comments = $('#comments').val();

        const params = new URLSearchParams();
        params.append('address', address);
        params.append('phone', phone);
        params.append('comments', comments);

        fetch('/controller/place-order?' + params.toString(), {
            method: 'POST',
        })
    }
</script>