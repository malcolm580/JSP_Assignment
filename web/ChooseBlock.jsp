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


        .tth, .tdd{
            padding-left: 150px;
            text-align: center;
            padding-top: 20px;
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
                   Block List

                </h4><br>
            </div>

            <jsp:useBean id="userList" scope="request" class="java.util.ArrayList"/>
            <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <form action="blockUser" method="get">

                    <input type="hidden" name="materialID" value="<%=request.getParameter("materialID")%>">
                    <input type="hidden" name="moduleID" value="<%=request.getParameter("moduleID")%>">

                    <table>
                        <th class="tth">Select</th>
                        <th class="tth">Student ID</th>
                        <th class="tth">Student Name</th>
                    <%


                        if (null != userList) {
                            for (Object bean : userList) {
                                User moduleUser = (User) bean;
                                boolean block = false;
                                String check = "";
                                if (moduleUser.isBlocked())
                                    check = "checked";
                                out.println("<tr>");
                                out.println(" <td class='tdd'> <input type='checkbox' name='blockList' value='"+ moduleUser.getUserID()+ "' "+ check +"></td>");
                                out.println("<td class='tdd'>"+ moduleUser.getUserID()+"</td>");
                                out.println("<td class='tdd'>"+ moduleUser.getUsername()+"</td>");
                                out.println(" </tr>");
                            }
                        }

                    %>

                        </table>
                    <hr class="w3-clear">
                    <input type="submit" class="w3-button w3-theme-l4 w3-block" value="Submit">
                </form>
                <hr class="w3-clear">

                <hr class="w3-clear">

            </div>


        </div>



    </div>

    <!-- End Page Container -->
</div>
<br>
<jsp:include page="footer.jsp"/>



</body>
</html>

