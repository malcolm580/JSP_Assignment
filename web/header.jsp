<%@ page import="elearning.bean.User" %>


<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="CSS/basic.css">
<link rel="stylesheet" href="CSS/themeBlueGrey.css">
<link rel='stylesheet' href='CSS/gFont.css'>
<link rel="stylesheet" href="CSS/fontAwesome.css">
<link rel="stylesheet" href="CSS/table.css">

<script type="text/javascript" src="JS/Chart.js"></script>
<script src="JS/jquery.js"></script>
<style>
    html, body, h1, h2, h3, h4, h5 {
        font-family: "Open Sans", sans-serif
    }
</style>


<!-- Navbar -->
<div class="w3-top">
    <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
        <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2"
           href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
        <a href="login.jsp" class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i
                class="fa fa-home w3-margin-right"></i>Logo</a>
        <a href="http://ivefyp.ml/phpmyadmin"
           class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i
                class="fa fa-globe"></i>PhpMyAdmin</a>
        <a href="QuizList.jsp" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white"
           title="Account Settings"><i class="fa fa-user"></i>Quiz List</a>
        <a href="reportMenu?action=getModuleList"
           class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Course Reporting"><i
                class="fa fa-envelope"></i>Course Reporting</a>
        <div class="w3-dropdown-hover w3-hide-small">
            <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-bell"></i><span
                    class="w3-badge w3-right w3-small w3-green">3</span></button>
            <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
                <a href="#" class="w3-bar-item w3-button">John Doe posted on your wall</a>
                <a href="#" class="w3-bar-item w3-button">Jane likes your post</a>
            </div>
        </div>
        <div class="w3-dropdown-hover w3-hide-small w3-right">
            <a href="#" class="w3-button w3-padding-large  "
               title="My Account"><% if (request.getSession().getAttribute("userInfo") != null)
                out.print(((User) request.getSession().getAttribute("userInfo")).getUsername() + " "); %><img
                    src="${pageContext.request.contextPath}/image/moodle.gif" class="w3-circle"
                    style="height:25px;width:25px" alt="Avatar"></a>
            <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px;right: 0;left: auto;">
                <a href="./main?action=logout" class="w3-bar-item w3-button ">Logout</a>
                <a href="#" class="w3-bar-item w3-button ">John Doe posted on your wall</a>
                <a href="#" class="w3-bar-item w3-button">Jane likes your post</a>
            </div>
        </div>
    </div>
</div>

<!-- Navbar on small screens -->
<div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
    <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 1</a>
    <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 2</a>
    <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 3</a>
    <a href="#" class="w3-bar-item w3-button w3-padding-large">My Profile</a>
</div>