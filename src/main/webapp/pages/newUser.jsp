<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 29.03.2016
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sign up page</title>
    <script type="text/javascript" src="../js/newUser.js"></script>
</head>
<body>
<form name="newUser" action="signUp" method="post" onsubmit="return validName()">
    Enter your name: <input type="text" name="name">
    <br>
    Login: <input type="text" name="login">
    <br>
    Password: <input type="text" name="password">
    <br>
    Confirm password: <input type="text" name="confirmPassword">
    <br>
    <input type="submit" value="Submit">
    <input type="button" value="Back" onclick="location.href='welcomePage.jsp'">
</form>


</body>
</html>
