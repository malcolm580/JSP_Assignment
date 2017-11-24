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

            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">
                            <h6 class="w3-opacity">Social Media template by w3.css</h6>
                            <p contenteditable="true" class="w3-border w3-padding">Status: Feeling Blue</p>
                            <button type="button" class="w3-button w3-theme"><i class="fa fa-pencil"></i> Post</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <img src="/w3images/avatar2.png" alt="Avatar" class="w3-left w3-circle w3-margin-right"
                     style="width:60px">
                <span class="w3-right w3-opacity">1 min</span>
                <h4>Module Reporting</h4><br>
                <hr class="w3-clear">
                 <h3>
                     <form action="quizReport" method="get">
                         <table border="1" id="table">
                             <tr>
                                 <th></th>
                                 <th>UserID</th>
                                 <th>UserName</th>
                             </tr>
                             <%
                                 ArrayList quizStudentList = (ArrayList) session.getAttribute("quizStudentList");

                                 for (Object bean : quizStudentList) {
                                     User student = (User) bean;
                                     out.println("<tr>");
                                     out.println("<td><input type='checkbox' value='"+
                                             student.getUserID()+"' name='target' /></td><td>"
                                             + student.getUserID()+"</td><td>"+student.getUsername()+"</td>");
                                     out.println("</tr>");
                                 }

                                 if(quizStudentList.size() == 0){
                                     out.print("This quiz have no any student");
                                 }

                             %>
                         </table>
                     </form>
                 </h3>
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
