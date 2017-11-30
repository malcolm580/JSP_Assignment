<%@ page import="elearning.bean.Quiz" %>
<%@ page import="elearning.bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="elearning.bean.Module" %>
<%
    User user = (User) request.getSession().getAttribute("userInfo");
    if (request.getSession().isNew() || null == user) {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    ArrayList<Quiz> quizList = (ArrayList<Quiz>) session.getAttribute("quizList");
    if (quizList == null) {
        //  RequestDispatcher rd = request.getRequestDispatcher("/quiz?action=list&returnto=QuizEnter.jsp");
        //rd.forward(request, response);
    }
%>
<html>
<head>
    <style>
        img {
            width: 20px;
            height: 20px;
        }

    </style>
    <title>Quiz Management</title>
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
                <h4>Quiz Management
                </h4><br>
                <hr class="w3-clear">
                <table width="100%" class="w3-left-align" id="table">
                    <tr>
                        <th>Quiz ID:</th>
                        <th>Module:</th>
                        <th>Quiz Name:</th>
                        <th>Attempt Limit:</th>
                        <th>Time Limit:</th>
                        <th>Total Question:</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    <%
                        for (Quiz quiz : (ArrayList<Quiz>) session.getAttribute("currentQuiz")) {
                            out.print("<tr>");
                            out.print("<td>" + quiz.getQuizID() + "</td>");
                            out.print("<td>" + quiz.getModuleID() + "</td>");
                            out.print("<td>" + quiz.getQuizName() + "</td>");
                            out.print(quiz.getAttemptLimit() == 0 ? "<td>Unlimited</td>" : "<td>" + quiz.getAttemptLimit() + "</td>");
                            out.print(quiz.getTimeLimit() == 0 ? "<td>Unlimited</td>" : "<td class='secondToDuration''>" + quiz.getTimeLimit() + "</td>");
                            out.print("<td>" + quiz.getTotalQuestion() + "</td>");
                            out.print("<td><a href='quiz?action=edit&quizid=" + quiz.getQuizID() + "'><img src='image/edit.png'></a></td>");
                            out.print("<td><a href='quiz/delete?quizid=" + quiz.getQuizID() + "'><img src='image/delete.png'></a></td>");
                            out.print("</tr>");
                        }
                    %>
                </table>
                <br/>
                <hr />
                <table width="100%" class="w3-left-align" id="table">
                    <form method="post" action="${pageContext.request.contextPath}/question/create">
                        <input type="hidden" name="action" value="create">
                        <tr>
                            <th colspan="2">Add New Quiz:</th>
                        </tr>
                        <tr>
                            <td><label for="ModuleID"> Module: </label></td>
                            <td>
                                <select name="ModuleID" id="ModuleID">
                                    <%
                                        for (Module module : (ArrayList<Module>) session.getAttribute("allModule")) {
                                            out.print("<option value='" + module.getModuleID() + "'>" + module.getModuleName() + "</option>");
                                        }
                                    %>
                                </select>
                                </td>
                        </tr>
                        <tr>
                            <td><label for="QuizName"> Quiz Name: </label></td>
                            <td>
                                <input style="display: inline" type="text"
                                       name="QuizName"
                                       id="QuizName"></td>
                        </tr>
                        <tr>
                            <td><label for="AttemptLimit">Attempts allowed: </label></td>
                            <td>
                                <input style="display: inline" type="number" min="-1"

                                       name="AttemptLimit" id="AttemptLimit" data-toggle="tooltip"
                                       data-placement="right"
                                       title=" Hint: 0 means infinite,-1 means not open this quiz"></td>
                        </tr>
                        <tr>
                            <td><label for="TimeLimit"> Time Limit (Second) (0 means no limitation): </label></td>
                            <td>
                                <input style="display: inline" type="number" min="0"
                                       name="TimeLimit"
                                       id="TimeLimit" data-toggle="tooltip"
                                       data-placement="right"
                                       title=" Hint: 0 means infinite, otherwise is the time limit of this quiz">
                            </td>
                        </tr>
                        <tr>
                            <td><label for="TimeLimit"> Total Number Of The Question Extract From Pool (0 means
                                all): </label></td>
                            <td>
                                <input style="display: inline" type="number" min="0"
                                       name="TotalQuestion"
                                       id="TotalQuestion" data-toggle="tooltip"
                                       data-placement="right"
                                       title=" Hint: 0 means all question, otherwise system will pick up the numbers of question from question pool">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <center>
                                    <button type="submit">Submit</button>
                                    <button type="reset"> Cancel</button>
                                </center>
                            </td>
                        </tr>
                    </form>
                </table>


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


