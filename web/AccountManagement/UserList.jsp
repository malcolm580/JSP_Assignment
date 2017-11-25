<%--
  Created by IntelliJ IDEA.
  User: youma
  Date: 23/11/2017
  Time: 0:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import=" elearning.bean.Module ,java.util.ArrayList" %>
<%@ page import="elearning.bean.Module" %>
<%@ page import="elearning.bean.Quiz" %>
<%@ page import="elearning.bean.User" %>
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


            <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <h4>User List</h4>
                <hr class="w3-clear">
                <table border="1" id="table">
                    <tr>
                        <th>User ID</th>
                        <th>User Name</th>
                        <th>User Email</th>
                        <th>User Role</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    <%
                        ArrayList userList = (ArrayList) session.getAttribute("userList");

                        if(userList.size() == 0){
                            out.print("This quiz have no any student");
                        }else{
                            for (Object bean : userList) {
                                User user = (User) bean;
                                out.println("<tr>");
                                out.println("<td>"
                                        + user.getUserID()+"</td><td>"+user.getUsername()
                                        +"</td><td>"+ user.getEmail()+ "</td><td>" + user.getRole()+ "</td>");
                                out.print("<td><a href='accountManagement?action=view&userID="
                                        + user.getUserID() + "' ><img src='../image/edit.png'></a></td>");
                                out.print("<td><a href='accountManagement?action=delete&userID="+ user.getUserID() + "' ><img src='../image/delete.png'></a></td>");
                                out.println("</tr>");
                            }
                        }

                    %>
                </table>
                <button>Add User</button>
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
