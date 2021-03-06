<%--
  Created by IntelliJ IDEA.
  User: youma
  Date: 23/11/2017
  Time: 0:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import=" elearning.bean.User ,java.util.ArrayList" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<jsp:include page="header.jsp"/>


<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">

        <!-- Left Column -->
        <jsp:include page="leftContent.jsp"/>

        <!-- Middle Column -->
        <div class="w3-col m7">

            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">
                            <h3> Quiz --Add Student Selection</h3>
                            <h5>Please select any or a set of student</h5>
                            <%--<button type="button" class="w3-button w3-theme" style="margin-left: 88.5%">Search</button>--%>
                        </div>
                    </div>
                </div>
            </div>

            <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <hr class="w3-clear">
                <form action="quiz" method="get">
                    <input type="hidden" name="action" value="addStudentToQuiz">
                    <table border="1" id="table">
                        <tr>
                            <th></th>
                            <th>Student ID</th>
                            <th>Student Name</th>
                        </tr>
                        <%
                            ArrayList studentList = (ArrayList) session.getAttribute("studentList");

                            for (Object bean : studentList) {
                                User student = (User) bean;
                                out.println("<tr>");
                                out.println("<td><input type='checkbox' value='" +
                                        student.getUserID() + "' name='target' /></td><td>"
                                        + student.getUserID() + "</td><td>" + student.getUsername() + "</td>");
                                out.println("</tr>");
                            }

                            if (studentList.size() == 0) {
                                out.print("This quiz have no any student");
                            }

                        %>
                    </table>
                    <hr class="w3-clear">
                    <input type="submit" value="Submit your student list">
                </form>
            </div>


            <!-- End Middle Column -->
        </div>


        <!-- End Grid -->
    </div>

    <!-- End Page Container -->
</div>


<jsp:include page="footer.jsp"/>
</body>
</html>
