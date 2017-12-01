<%@ page import="elearning.bean.Module" %>
<%@ page import="elearning.bean.Quiz" %>
<%@ page import="elearning.bean.QuizResult" %>
<%@ page import="elearning.bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%
    User user = (User) request.getSession().getAttribute("userInfo");
    if (request.getSession().isNew() || null == user) {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    Quiz currentQuiz = (Quiz) request.getSession().getAttribute("currentQuiz");
    if (currentQuiz == null) {
        out.print("<script> window.onload = function() { alert('Please Select the Quiz First'); window.history.back(); }</script >");
    }
    ArrayList<Quiz> quizList = (ArrayList<Quiz>) session.getAttribute("quizList");
    if (quizList == null) {
        RequestDispatcher rd = request.getRequestDispatcher("/quiz?action=list&returnto=QuizEnter.jsp");
        rd.forward(request, response);
    }
%>
<%@ taglib uri="/WEB-INF/tlds/quizEnterTag.tld" prefix="quiz" %>
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
                <h4><%=((Module) session.getAttribute("currentModule")).getModuleName()%>
                </h4>
                <h5><%=currentQuiz.getQuizName()%>
                </h5>
                <br>
                <hr class="w3-clear">
                <center>Total attempts allowed: <% if (currentQuiz.getAttemptLimit() == 0) {
                    out.print("Unlimited");
                } else out.print(currentQuiz.getAttemptLimit());%>
                </center>
                <h5><b>Summary of your previous attempts</b></h5>
                <table width="100%" class="w3-left-align" id="table">
                    <tr>
                        <th>State</th>
                        <th>Grade / <%=currentQuiz.getTotalQuestion()%>
                        </th>
                        <th>Review</th>
                    </tr>
                    <%
                        ArrayList<QuizResult> quizResultList = (ArrayList<QuizResult>) session.getAttribute("currentQuizResultList");
                        if (quizResultList != null && quizResultList.size() > 0) {
                            for (QuizResult quizResult : quizResultList) {

                    %>
                    <tr>
                        <td>
                            Finished
                        </td>
                        <td>
                            <%=quizResult.getCorrectCount()%>
                        </td>
                        <td>
                            <a href='./QuizReview.jsp?quizid=<%=quizResult.getQuizResultID()%>'><u>Review</u></a>
                            <%

                            %>
                        </td>
                    </tr>
                    <%
                        }
                        if (currentQuiz.getAttemptLimit() > quizResultList.size() || currentQuiz.getAttemptLimit() == 0) {
                    %>
                    <tr>
                        <td colspan="2">
                            <b>Waiting for attempt</b>
                        </td>
                        <td>

                            <a href='${pageContext.request.contextPath}/quizAttempt?action=attempt&quizid=<%=currentQuiz.getQuizID()%>'><u>Attempt</u></a>

                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="2">
                            <b>Waiting for attempt</b>
                        </td>
                        <td>

                            <a href='QuizAttempts.jsp?quizid=<%=currentQuiz.getQuizID()%>'><u>Attempt</u></a>

                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
                <br/>

                <%
                    if (quizResultList == null) {
                        out.print("<center>You Have Not Finished Any Quiz Yet</center>");
                    }
                %>
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


