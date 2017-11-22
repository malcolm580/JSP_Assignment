<%--
  Created by IntelliJ IDEA.
  User: LOC
  Date: 20/11/2017
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Learning</title>


    <link rel="stylesheet" href="CSS/login.css">
    <link rel="stylesheet" href="CSS/login_2.css">

    <style>
        .signup{
            margin-top: -30px;
        }

        .input-cart{
            margin-top: 150px;
        }
    </style>


</head>

<body class="login-body">
<div class="row">
    <div class="input-cart col s12 m10 push-m1 z-depth-2 grey lighten-5">
        <div class="col s12 m5 login">
            <h4 class="center">Login</h4>
            <br>
            <form action="main" method="post">
                <input type="hidden" name="action" value="authenticate">
                <div class="row">
                    <div class="input-field">
                        <input type="text" id="user" name="username" class="validate" required="required" placeholder="UserName">

                    </div>
                </div>
                <div class="row">
                    <div class="input-field">
                        <input type="password" id="pass" name="password" class="validate" required="required" placeholder="Password">

                    </div>
                </div>
                <div class="row">
                    <div class="switch col s6">
                        <label>
                            <input type="checkbox">
                            <span class="lever"></span>
                            Show Password
                        </label>
                    </div>
                    <div class="col s6">
                        <button type="submit" name="login" class="btn waves-effect waves-light blue right">Log in
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <!-- Signup form -->
        <div class="col s12 m7 signup">

            <div class="signup-toggle center">

                <img src="image/moodle.gif" alt="" style="width: 200px;">

                <h4 class="center">Moodle e-Learning <p>Management Platform</p></h4>
            </div>
        </div>

        <div class="col s12">
            <br>
            <div class="legal center">
            </div>
            <div class="legal center">
                <div class="col s12 m5">
                    <p class="center grey-text" style="font-size: 14px;">Information Technology (IT) - E Learning</p>
                </div>
            </div>

        </div>

    </div>
</div>

</body>
</html>

