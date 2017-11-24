<%@ page import="elearning.bean.Module" %>
<%@ page import="elearning.bean.Quiz" %>
<%@ page import="elearning.bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%
    User user = (User) request.getSession().getAttribute("userInfo");
    if (null == user) {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    Quiz currentQuiz=(Quiz)request.getSession().getAttribute("currentQuiz");
    if (currentQuiz == null) {
        out.print("<script> window.onload = function() { alert('Please Select the Quiz First'); window.history.back(); }</script >");
    }
    ArrayList<Quiz> quizList = (ArrayList<Quiz>) session.getAttribute("quizList");
    if (quizList == null) {
        RequestDispatcher rd = request.getRequestDispatcher("/quiz?action=list&returnto=QuizEnter.jsp");
        rd.forward(request, response);
    }
%>
<html>
<head>
    <title>Quiz</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">
        <!-- Left Column -->
        <jsp:include page="leftContent.jsp"/>
        <!-- End Left Column -->

        <!-- Middle Column -->
        <div class="w3-col m7">
            <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <span class="w3-right w3-opacity"></span>
                <h4><%=((Module) session.getAttribute("currentModule")).getModuleName()%></h4><br>
                <hr class="w3-clear">
                <center>Attempts allowed: <%=currentQuiz.getAttemptLimit()%></center>
                <h5>Summary of your previous attempts</h5>
                <table width="100%">
                    <tr><th>State</th><th>Grade / <%=currentQuiz.getTotalQuestion()%></th><th>Review</th></tr>
                </table>
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


