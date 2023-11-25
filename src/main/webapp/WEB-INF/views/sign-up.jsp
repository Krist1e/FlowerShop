<%--
  Created by IntelliJ IDEA.
  User: ASUS VivoBook15X512D
  Date: 11/24/2023
  Time: 6:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
// sign up page
<head>
    <title>Sign Up</title>
</head>
<body>
<h1>Sign Up</h1>
<form action="${pageContext.request.contextPath}/sign-up" method="post">
    <table>
        <tr>
            <td>Username</td>
            <td><input type="text" name="username" required></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" required></td>
        </tr>
        <tr>
            <td>Confirm Password</td>
            <td><input type="password" name="confirmPassword" required></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Sign Up"></td>
        </tr>
    </table>
</form>
</body>
</html>
