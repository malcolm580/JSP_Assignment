<%@ page import="elearning.bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="elearning.bean.Metrial" %>
<%@page buffer="16kb" autoFlush="false" %>
<%
    User user = (User) request.getSession(false).getAttribute("userInfo");
    if (user == null) {
        //response.sendRedirect("login.jsp");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
%>

<!DOCTYPE html>
<html>

<head>

    <style>
        a {
            text-decoration: none
        }
    </style>
</head>

<body>
<jsp:include page="header.jsp"/>
<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">
        <!-- Left Column -->
        <jsp:include page="leftContent.jsp"/>
        <!-- End Left Column -->

        <!-- Middle Column -->
        <div class="w3-col m7">

            <div class="w3-container w3-card w3-white w3-round w3-margin" style="text-align: center"><br>
                <h4>
                    <jsp:useBean id="moduleContent" scope="request" class="elearning.bean.Module" />
                    <%= moduleContent.getModuleName()%>

                </h4><br>
            </div>

            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">
                            <h6 class="w3-opacity">Search Material</h6>
                            <p contenteditable="true" class="w3-border w3-padding"></p>
                            <%--<button type="button" class="w3-button w3-theme" style="margin-left: 88.5%">Search</button>--%>
                        </div>
                    </div>
                </div>
            </div>


            <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <form action="upload" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="module" value="<%= moduleContent.getModuleID()%>">
                    <span class="w3-right w3-opacity"><input type="file" name="file" class="w3-input"/></span>
                    <h4>Module Material</h4><br>

                    <input type="submit" value="Upload Material" class="w3-button w3-theme" style="margin-left: 78.5%">
                </form>

                <hr class="w3-clear">

            </div>

            <jsp:useBean id="materialList" scope="request" class="java.util.ArrayList" />

            <%
                if (null != materialList) {
                    for (Object bean : materialList) {
                        Metrial metrial = (Metrial) bean;
                        out.println("<div class=\"w3-container w3-card w3-white w3-round w3-margin\"><br>");
                        out.println("<h4><a href='download?file="+ metrial.getContent()+"."+ metrial.getContentType()+ "&moduleID="+ moduleContent.getModuleID() +"'>" + metrial.getContent() + "</a></h4><br />");
                        out.println("</div>");
                    }
                }

            %>


        </div>


    </div>

    <!-- End Page Container -->
</div>
<br>
<jsp:include page="footer.jsp"/>

</body>
</html>

