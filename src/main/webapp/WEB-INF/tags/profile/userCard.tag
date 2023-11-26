<%@tag description="Profile User Card" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<%@attribute name="user" required="true" type="by.bsuir.alekseeva.flowershop.beans.User" %>
<div class="card">
    <div class="card-body">
        <h5 class="card-title">${user.username}</h5>
        <p class="card-text">Email: ${user.email}</p>
    </div>
</div>