
<%@ page contentType="text/html;charset=UTF-8" language="java" import=" elearning.bean.User ,java.util.Objects" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<jsp:include page="../header.jsp"/>
<jsp:useBean id="viewUser" class="elearning.bean.User" scope="session"/>
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

                                        <input type="hidden" name="userID" value="<%= viewUser.getUserID() %>">
                                        <input type="hidden" name="action" value="edit" >

                                        <div class="formgroup" id="name-form">
                                            <label for="username"> Username:*</label>
                                            <input type="text" id="username" name="username"  value="<%= viewUser.getUsername() %>"/>
                                        </div>

                                        <div class="formgroup" id="message-form">
                                            <label for="password"> Password:*</label>
                                            <input type="password" id="password" name="password" value="<%= viewUser.getPassword() %>">
                                        </div>

                                        <div class="formgroup" id="email-form">
                                            <label for="email"> e-mail*</label>
                                            <input type="email" id="email" name="email" value="<%= viewUser.getEmail() %>" />
                                        </div>

                                        <div class="formgroup" >
                                            <label for="role">Role*</label>
                                            <select name="role" id="role">
                                                <%
                                                    for(Object role : allRoles){
                                                        User userRole = (User)role;
                                                        String selected = "";
                                                        if(Objects.equals(userRole.getRole(), viewUser.getRole())){
                                                            selected = "selected";
                                                        }
                                                        out.print("<option value='" + userRole.getRole() + "' "+ selected +">"+ userRole.getRole() +"</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>

                                        <input type="submit" value="Submit Your Edit" />
                                        <label>
                                        <%
                                           if(null != session.getAttribute("edited")){
                                               if((Boolean) session.getAttribute("edited")){
                                                   out.print("This user info has been edited");
                                               }else {
                                                   out.print("This user info has not been edited");
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
