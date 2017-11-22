<%--
  Created by IntelliJ IDEA.
  User: LOC
  Date: 20/11/2017
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<p>The admin login name is admin and password is password</p>
<form method="post" action="main">
    <input type="hidden" name="action" value="authenticate"/>
    <table border="0">
        <tr><td><p align="right"><b>login:</b></p></td>
            <td><p><input type="text" name="username" maxlength="10" size="15" ></p></td>
        </tr>
        <tr><td><p align="right"><b>password:</b></p></td>
            <td><p><input type="password" name="password" maxlength="10" size="15" ></p></td>
        </tr>
        <tr>
            <td colspan="2"><p align="center"><input type="submit" value="Login"></p></td>
        </tr>
    </table>
</form>
</body>
</html>
