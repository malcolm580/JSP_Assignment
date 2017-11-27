<%--
  Created by IntelliJ IDEA.
  User: youma
  Date: 23/11/2017
  Time: 0:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<jsp:include page="../header.jsp"/>
<jsp:useBean id="userInfo" class="elearning.bean.User" scope="session"/>

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

                                    <form action="profile" method="post">

                                        <input type="hidden" name="action" value="edit" >

                                        <div class="formgroup" id="name-form">
                                            <label for="username">Your Username:*</label>
                                            <input type="text" id="username" name="username"  value="<%= userInfo.getUsername() %>"/>
                                        </div>

                                        <div class="formgroup" id="message-form">
                                            <label for="password">Your Password</label>
                                            <input type="password" id="password" name="password" value="<%= userInfo.getPassword() %>">
                                        </div>

                                        <div class="formgroup" id="email-form">
                                            <label for="email">Your e-mail*</label>
                                            <input type="email" id="email" name="email" value="<%= userInfo.getEmail() %>" />
                                        </div>

                                        <input type="submit" value="Submit Your Edit" />

                                        <% if(null != session.getAttribute("edited") ){
                                            out.print("<label>Your profile has been updated successfully!</label>");
                                        }%>

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
