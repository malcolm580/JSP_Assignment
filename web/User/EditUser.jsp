<%--
  Created by IntelliJ IDEA.
  User: LOC
  Date: 20/11/2017
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<jsp:useBean id="userInfo" class="elearning.bean.User" scope="session"/>
username:<jsp:getProperty name="userInfo" property="username"/><b />
Password:<jsp:getProperty name="userInfo" property="password"/><b />
Email:<jsp:getProperty name="userInfo" property="email"/><b />
<p>Welcome to the ICT</p>
<form method="post" action="main">
    <input type="hidden" name="action" value="logout">
    <input type="submit" value="Logout" name="logoutButton">
</form>
<hr />
</body>
</html>
