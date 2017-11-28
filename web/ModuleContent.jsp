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

        img {
            margin-right: 20px;
        }

        .check{
            width: 24px;
            height: 24px;
            position: relative;
            top: 24px;
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
                    <jsp:useBean id="moduleContent" scope="request" class="elearning.bean.Module"/>
                    <%= moduleContent.getModuleName()%>

                </h4><br>
            </div>

            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">
                            <h6 class="w3-opacity">Search Material</h6>
                            <input type="text" class="w3-input" id="search" placeholder="Enter The Material Name">
                            <%--<p contenteditable="true" class="w3-border w3-padding" id="search"></p>--%>
                            <%--<button type="button" class="w3-button w3-theme" style="margin-left: 88.5%">Search</button>--%>
                        </div>
                    </div>
                </div>
            </div>


            <%
                try {
                    if (user.getRole().equals("Teacher")) {
                        out.println("<div class=\"w3-container w3-card w3-white w3-round w3-margin\"><br>");
                        out.println("<form action=\"upload\" method=\"post\" enctype=\"multipart/form-data\">");
                        out.println("<input type=\"hidden\" name=\"module\" value=\"" + moduleContent.getModuleID() + "\">");
                        out.println(" <span class=\"w3-right w3-opacity\"><input type=\"file\" name=\"file\" class=\"w3-input\"/></span>");
                        out.println(" <h4>Module Material</h4><br>");
                        out.println(" <input type=\"submit\" value=\"Upload Material\" class=\"w3-button w3-theme\" style=\"margin-left: 78.5%\">");
                        out.println("</form>");
                        out.println(" <hr class=\"w3-clear\">");
                        out.println(" </div>");
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            %>

            <%--<div class="w3-container w3-card w3-white w3-round w3-margin"><br>--%>
                <%--<form action="upload" method="post" enctype="multipart/form-data">--%>
                    <%--<input type="hidden" name="module" value="<%= moduleContent.getModuleID()%>">--%>
                    <%--<span class="w3-right w3-opacity"><input type="file" name="file" class="w3-input"/></span>--%>
                    <%--<h4>Module Material</h4><br>--%>

                    <%--<input type="submit" value="Upload Material" class="w3-button w3-theme" style="margin-left: 78.5%">--%>
                <%--</form>--%>

                <%--<hr class="w3-clear">--%>

            <%--</div>--%>

            <%--<span class="w3-right w3-opacity"><a href=""><img src="image/x-button.png"></a></span>--%>

            <jsp:useBean id="materialList" scope="request" class="java.util.ArrayList"/>

            <%
                if (null != materialList) {
                    for (Object bean : materialList) {
                        Metrial metrial = (Metrial) bean;
                        out.println("<div class=\"w3-container w3-card w3-white w3-round w3-margin material\"><br>");
                        try {
                            if (user.getRole().equals("Teacher")) {
                                out.println(" <span class=\"w3-right w3-opacity\"><a href='delete?file=" + metrial.getContent() + "." + metrial.getContentType() + "&moduleID=" + moduleContent.getModuleID() + "&materialID=" + metrial.getMaterialID() + "'><img src=\"image/x-button.png\"></a></span>");
                                out.println(" <span class=\"w3-right w3-opacity\"><a href='block?moduleID=" + moduleContent.getModuleID() + "&materialID=" + metrial.getMaterialID() + "'><img src=\"image/editer.png\"></a></span>");
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }

                        out.println("<h6><a href='download?file=" + metrial.getContent() + "." + metrial.getContentType() + "&moduleID=" + moduleContent.getModuleID() + "'>" + metrial.getContent().toUpperCase() + "</a></h6><br />");
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

<script>
    $(document).ready(function () {
        $("#search").keyup(function () {
            $(".material").hide();
            var input = $('#search').val().toUpperCase();
            $(".material:contains(" + input + ")").show();

        });
    });
</script>

</body>
</html>

