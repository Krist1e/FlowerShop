<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
// sign in page
<head>
  <title>Sign In</title>
</head>
<body>
<h1>Sign Up</h1>
<form action="${pageContext.request.contextPath}/controller/sign-in" method="post">
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
      <td></td>
      <td><input type="submit" value="Sign In"></td>
    </tr>
  </table>
</form>
</body>
</html>