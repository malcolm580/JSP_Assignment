<%@ page import="elearning.bean.User" %>
<%@ page import="elearning.bean.QuizResult" %>
<%@ page import="java.util.ArrayList" %>
<%
    User user = (User) request.getSession().getAttribute("userInfo");
    if (null == user) {
        request.getRequestDispatcher("/login.jsp").forward(request,response);
    }

%>

<html>
<head>
    <title>Quiz</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">
        <!-- Left Column -->
        <jsp:include page="/leftContent.jsp"/>
        <!-- End Left Column -->

        <!-- Middle Column -->
        <div class="w3-col m7">
            <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <span class="w3-right w3-opacity"></span>
                <h4>
                </h4><br>
                <hr class="w3-clear">
                <center>Attempts allowed:
                </center>
                <h5><b>Summary of your previous attempts</b></h5>
                <table width="100%" class="w3-left-align">
                    <tr>
                        <th>State</th>
                        <th>Grade /
                        </th>
                        <th>Review</th>
                    </tr>

                </table>
                <br />


                <center><a href="javascript: window.history.back();">Back</a></center>
            </div>
        </div>
        <!-- End Middle Column -->
    </div>

</div>

<!-- End Page Container -->
</div>
<br>
<jsp:include page="footer.jsp"/>

</body>
</html>

