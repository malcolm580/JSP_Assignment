
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<p>Incorrect Password</p>
<p>
    <% out.println("<a href='"+request.getContextPath()+"/main'>Login again</a>"); %>--%>

<script src="JS/jquery.js"></script>
<script>
    $(function () {
        alert("The username or password is incorrect\nPlease Try Again");
        window.location.replace("./");
    });
</script>
</p>
</body>
</html>
