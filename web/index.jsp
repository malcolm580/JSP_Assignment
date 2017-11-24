<%@ page import="elearning.bean.Module" %>
<%@ page import="elearning.bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%@page buffer="16kb" autoFlush="false" %>
<%
    User user = (User) request.getSession(false).getAttribute("userInfo");
    if (user == null) {
        //response.sendRedirect("login.jsp");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        out.flush();
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

            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">
                            <h6 class="w3-opacity">Search Module</h6>
                            <p contenteditable="true" class="w3-border w3-padding"></p>
                            <%--<button type="button" class="w3-button w3-theme" style="margin-left: 88.5%">Search</button>--%>
                        </div>
                    </div>
                </div>
            </div>

            <%--<div class="w3-container w3-card w3-white w3-round w3-margin"><br>--%>
            <%--<img src="/w3images/avatar2.png" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">--%>
            <%--<span class="w3-right w3-opacity">1 min</span>--%>
            <%--<h4>John Doe</h4><br>--%>
            <%--<hr class="w3-clear">--%>
            <%--<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>--%>
            <%--<div class="w3-row-padding" style="margin:0 -16px">--%>
            <%--<div class="w3-half">--%>
            <%--<img src="/w3images/lights.jpg" style="width:100%" alt="Northern Lights" class="w3-margin-bottom">--%>
            <%--</div>--%>
            <%--<div class="w3-half">--%>
            <%--<img src="/w3images/nature.jpg" style="width:100%" alt="Nature" class="w3-margin-bottom">--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<button type="button" class="w3-button w3-theme-d1 w3-margin-bottom"><i class="fa fa-thumbs-up"></i>  Like</button>--%>
            <%--<button type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i>  Comment</button>--%>
            <%--</div>--%>

            <%--<div class="w3-container w3-card w3-white w3-round w3-margin"><br>--%>
            <%--<img src="/w3images/avatar5.png" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">--%>
            <%--<span class="w3-right w3-opacity">16 min</span>--%>
            <%--<h4>Jane Doe</h4><br>--%>
            <%--<hr class="w3-clear">--%>
            <%--<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>--%>
            <%--<button type="button" class="w3-button w3-theme-d1 w3-margin-bottom"><i class="fa fa-thumbs-up"></i>  Like</button>--%>
            <%--<button type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i>  Comment</button>--%>
            <%--</div>--%>

            <%
                ArrayList moduleList = (ArrayList) session.getAttribute("moduleList");
                if (null != moduleList) {
                    for (Object bean : moduleList) {
                        Module module = (Module) bean;
                        //out.println("<a href='reportMenu?action=getModuleQuiz&moduleID="+ module.getModuleID()+"'>" +  module.getModuleName()+"</a><br />");

                        out.println("<div class=\"w3-container w3-card w3-white w3-round w3-margin\"><br>");
                        out.println("<h4><a href='moduleController?action=list&moduleID=" + module.getModuleID() + "'>" + module.getModuleID() + " - " + module.getModuleName() + "</a></h4><br />");
                        out.println("</div>");
                    }
                }

            %>

            <%--<div class="w3-container w3-card w3-white w3-round w3-margin"><br>--%>
            <%--<img src="/w3images/avatar6.png" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">--%>
            <%--<span class="w3-right w3-opacity">32 min</span>--%>
            <%--<h4>Angie Jane</h4><br>--%>
            <%--<hr class="w3-clear">--%>
            <%--<p>Have you seen this?</p>--%>
            <%--<img src="/w3images/nature.jpg" style="width:100%" class="w3-margin-bottom">--%>
            <%--<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>--%>
            <%--<button type="button" class="w3-button w3-theme-d1 w3-margin-bottom"><i class="fa fa-thumbs-up"></i>  Like</button>--%>
            <%--<button type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i>  Comment</button>--%>
            <%--</div>--%>

            <!-- End Middle Column -->
        </div>

        <%--(not useful?)--%>
        <!-- Right Column -->
        <%--<div class="w3-col m2">--%>
        <%--<div class="w3-card w3-round w3-white w3-center">--%>
        <%--<div class="w3-container">--%>
        <%--<p>Upcoming Events:</p>--%>
        <%--<img src="/w3images/forest.jpg" alt="Forest" style="width:100%;">--%>
        <%--<p><strong>Holiday</strong></p>--%>
        <%--<p>Friday 15:00</p>--%>
        <%--<p><button class="w3-button w3-block w3-theme-l4">Info</button></p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<br>--%>

        <%--<div class="w3-card w3-round w3-white w3-center">--%>
        <%--<div class="w3-container">--%>
        <%--<p>Friend Request</p>--%>
        <%--<img src="/w3images/avatar6.png" alt="Avatar" style="width:50%"><br>--%>
        <%--<span>Jane Doe</span>--%>
        <%--<div class="w3-row w3-opacity">--%>
        <%--<div class="w3-half">--%>
        <%--<button class="w3-button w3-block w3-green w3-section" title="Accept"><i class="fa fa-check"></i></button>--%>
        <%--</div>--%>
        <%--<div class="w3-half">--%>
        <%--<button class="w3-button w3-block w3-red w3-section" title="Decline"><i class="fa fa-remove"></i></button>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<br>--%>

        <%--<div class="w3-card w3-round w3-white w3-padding-16 w3-center">--%>
        <%--<p>ADS</p>--%>
        <%--</div>--%>
        <%--<br>--%>

        <%--<div class="w3-card w3-round w3-white w3-padding-32 w3-center">--%>
        <%--<p><i class="fa fa-bug w3-xxlarge"></i></p>--%>
        <%--</div>--%>

        <%--<!-- End Right Column -->--%>
        <%--</div>--%>

        <!-- End Grid -->
    </div>

    <!-- End Page Container -->
</div>
<br>
<jsp:include page="footer.jsp"/>

</body>
</html>

