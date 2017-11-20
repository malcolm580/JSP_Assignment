<%--
  Created by IntelliJ IDEA.
  User: LOC
  Date: 13/11/2017
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<jsp:useBean id="userInfo" class="elearning.bean.User" scope="session"/>
<b>Hello, <jsp:getProperty name="userInfo" property="username"/>
</b>
<p>Welcome to the ICT</p>
<form method="post" action="main">
    <input type="hidden" name="action" value="logout">
    <input type="submit" value="Logout" name="logoutButton">
</form>
<hr />
<a href="brandController?action=list">getAllBrands</a><br />
</body>
</html>
