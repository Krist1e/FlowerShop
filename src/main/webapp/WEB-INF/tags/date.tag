<%@tag description="Date" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="d" uri="date" %>

<%@attribute name="date" required="true" type="java.time.LocalDateTime" %>
<c:out value="${d:formatLocalDateTime(date)}"/>
