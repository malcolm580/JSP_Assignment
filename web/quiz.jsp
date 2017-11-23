<%@ page import="elearning.bean.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Quiz</title>
</head>
<body>
<%
    User user = (User)request.getSession().getAttribute("userInfo");
    if(null==user){
        response.sendRedirect("login.jsp");
    }else{
        //response.sendRedirect("index.jsp?msg=You%20have%20been%20logined");
    }
%>
<jsp:include page="header.jsp" />
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">
        <!-- Left Column -->
        <jsp:include page="leftContent.jsp"/>
        <!-- End Left Column -->

        <!-- Middle Column -->


        <!-- End Middle Column -->
    </div>

</div>

<!-- End Page Container -->
</div>
<br>
<jsp:include page="footer.jsp"/>

</body>
</html>


