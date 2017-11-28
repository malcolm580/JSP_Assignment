<%@ page import="elearning.bean.User" %>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="CSS/bootstrap.min.css">
<link rel="stylesheet" href="CSS/basic.css">
<link rel="stylesheet" href="CSS/themeBlueGrey.css">
<link rel='stylesheet' href='CSS/gFont.css'>
<link rel="stylesheet" href="CSS/fontAwesome.css">
<link rel="stylesheet" href="CSS/table.css">

<script type="text/javascript" src="JS/Chart.js"></script>

<link type="text/css" rel="stylesheet" href="JS/jsgrid/jsgrid.min.css"/>
<link type="text/css" rel="stylesheet" href="JS/jsgrid/jsgrid-theme.min.css"/>

<script src="JS/jquery.js"></script>

<script type="text/javascript" src="JS/jsgrid/jsgrid.min.js"></script>
<script type="text/javascript" src="JS/jsgrid/i18n/jsgrid-zh-tw.js"></script>


<link rel="stylesheet" href="CSS/profileForm.css">



<!-- Latest compiled and minified JavaScript -->
<script src="JS/bootstrap.min.js">
</script>

<style>
    html, body, h1, h2, h3, h4, h5 {
        font-family: "Open Sans", sans-serif
    }

    a {
        text-decoration: none
    }

    body {
        /* Margin bottom by footer height */
        margin-bottom: 60px;
    }

    html {
        position: relative;
        min-height: 100%;
    }

</style>
<%
    if (request.getParameter("msg") != null && request.getParameter("msg").length() > 0) {
%>
<style>
    #snackbar {
        visibility: hidden;
        min-width: 250px;
        margin-left: -125px;
        background-color: #333;
        color: #fff;
        text-align: center;
        border-radius: 2px;
        padding: 16px;
        position: fixed;
        z-index: 1;
        left: 50%;
        bottom: 30px;
        font-size: 17px;
    }

    #snackbar.show {
        visibility: visible;
        -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
        animation: fadein 0.5s, fadeout 0.5s 2.5s;
    }

    @-webkit-keyframes fadein {
        from {
            bottom: 0;
            opacity: 0;
        }
        to {
            bottom: 30px;
            opacity: 1;
        }
    }

    @keyframes fadein {
        from {
            bottom: 0;
            opacity: 0;
        }
        to {
            bottom: 30px;
            opacity: 1;
        }
    }

    @-webkit-keyframes fadeout {
        from {
            bottom: 30px;
            opacity: 1;
        }
        to {
            bottom: 0;
            opacity: 0;
        }
    }

    @keyframes fadeout {
        from {
            bottom: 30px;
            opacity: 1;
        }
        to {
            bottom: 0;
            opacity: 0;
        }
    }
</style>
<script>
    function showSnackBar() {
        var x = document.getElementById("snackbar")
        x.className = "show";
        setTimeout(function () {
            x.className = x.className.replace("show", "");
        }, 3000);
    }

    $(function () {
        showSnackBar();
    })
</script>
<%
        out.print("<div id=\"snackbar\">" + request.getParameter("msg") + "</div>");
    }
%>


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
        <a href="quiz?action=QuizManagement" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white"
           title="Account Settings"><i class="fa fa-user"></i>Quiz Management</a>
        <a href="reportMenu?action=getModuleList"
           class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Module Quiz Reporting"><i
                class="fa fa-envelope"></i>Module Quiz Reporting</a>
        <a href="accountManagement?action=list"
           class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="User Management"><i
                class="fa fa-envelope"></i>User Management</a>
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