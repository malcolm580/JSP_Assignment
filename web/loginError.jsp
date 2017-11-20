
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>Incorrect Password</p>
<p>
    <% out.println("<a href='"+request.getContextPath()+"/main'>Login again</a>"); %>
</p>
</body>
</html>
