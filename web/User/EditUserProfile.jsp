<%--
  Created by IntelliJ IDEA.
  User: youma
  Date: 23/11/2017
  Time: 0:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import=" elearning.bean.Module ,java.util.ArrayList" %>
<%@ page import="elearning.bean.Module" %>
<%@ page import="com.sun.org.apache.xpath.internal.operations.Mod" %>
<%@ page import="elearning.bean.Quiz" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<jsp:include page="../header.jsp"/>


<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">

        <!-- Left Column -->
        <jsp:include page="../leftContent.jsp"/>

        <!-- Middle Column -->
        <div class="w3-col m7">

            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">
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
                        </div>
                    </div>
                </div>
            </div>



            <!-- End Middle Column -->
        </div>


        <!-- End Grid -->
    </div>

    <!-- End Page Container -->
</div>


<jsp:include page="../footer.jsp"/>
</body>
</html>
