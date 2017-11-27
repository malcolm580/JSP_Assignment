<%--
  Created by IntelliJ IDEA.
  User: youma
  Date: 23/11/2017
  Time: 0:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import=" elearning.bean.User" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<jsp:include page="../header.jsp"/>
<jsp:useBean id="allRoles" class="java.util.ArrayList" scope="session"/>

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
                            <h4 class="w3-opacity">Edit Profile</h4>
                            <p contenteditable="true" class="w3-border w3-padding"></p>
                            <%--<button type="button" class="w3-button w3-theme" style="margin-left: 88.5%">Search</button>--%>
                        </div>
                    </div>
                </div>
            </div>

            <br />

            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">

                                <div id="form">

                                    <form action="accountManagement" method="post">

                                        <input type="hidden" name="userID" >
                                        <input type="hidden" name="action" value="edit" >

                                        <div class="formgroup" id="name-form">
                                            <label for="username"> Username:*</label>
                                            <input type="text" id="username" name="username"/>
                                        </div>

                                        <div class="formgroup" id="message-form">
                                            <label for="password"> Password:*</label>
                                            <input type="password" id="password" name="password" >
                                        </div>

                                        <div class="formgroup" id="email-form">
                                            <label for="email"> e-mail*</label>
                                            <input type="email" id="email" name="email"  />
                                        </div>

                                        <div class="formgroup" >
                                            <label for="role">Role*</label>
                                            <select name="role" id="role">
                                                <%
                                                    for(Object role : allRoles){
                                                        User userRole = (User)role;
                                                        String selected = "";
                                                        out.print("<option value='" + userRole.getRole() + "' "+ selected +">"+ userRole.getRole() +"</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>

                                        <input type="submit" value="Submit Your Edit" />
                                        <label>
                                        <%
                                           if(null != session.getAttribute("added")){
                                               if((Boolean) session.getAttribute("added")){
                                                   out.print("This user has been added");
                                               }else {
                                                   out.print("This user has not been added");
                                               }
                                           }
                                        %>
                                        </label>
                                        <br>
                                        <label>
                                            <a href="accountManagement?action=list">Back</a></label>

                                    </form>
                                </div>
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
