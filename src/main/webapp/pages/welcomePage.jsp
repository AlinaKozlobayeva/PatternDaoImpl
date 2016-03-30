<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 29.03.2016
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
<h1>Hello to our page!</h1>
<form action="login" method="post">
  Login:<br> <input type="text" name="login" required>
  <br>
  Password:<br> <input type="password" name="password" required>
  <br>
  <input type="submit" value="Log In">
  <input type="button" value="Sign Up" onclick="location.href='newUser.jsp'"/>

</form>
</body>
</html>
