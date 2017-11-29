<%@ page import="elearning.bean.Question" %>
<%@ page import="elearning.bean.QuestionOption" %>
<%@ page import="elearning.bean.Quiz" %>
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
%>
<jsp:useBean id="Quiz" class="elearning.bean.Quiz" scope="session"/>
<jsp:setProperty property="*" name="Quiz"/>
<html>
<head>
<style>
    img {
        width: 20px ;
        height: 20px;
    }

</style>
    <title>Quiz Edit</title>
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
                <h4>Quiz Name:<%=currentQuiz.getQuizName()%>
                </h4>
                <h5>
                </h5>
                <br>
                <hr class="w3-clear">


                <h5><b>Summary of your previous attempts</b></h5>
                <form action="${pageContext.request.contextPath}/quiz/edit" method="post">
                    <table width="100%" class="w3-left-align">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="QuizID" value="<%=currentQuiz.getQuizID()%>">
                        <tr>
                            <td><label for="QuizID"> Quiz ID: </label></td>
                            <td>
                                <input style="display: inline" type="text" min="-1"
                                       value="<%=currentQuiz.getQuizID()%>" disabled id="QuizID"
                                       title="You cannot modify the id of quiz"></td>
                        </tr>
                        <tr>
                            <td><label for="ModuleID"> Module ID: </label></td>
                            <td>
                                <input style="display: inline" type="number" min="0"
                                       value="<%=currentQuiz.getModuleID()%>" name="ModuleID"
                                       id="ModuleID"></td>
                        </tr>
                        <tr>
                            <td><label for="QuizName"> Quiz Name: </label></td>
                            <td>
                                <input style="display: inline" type="text"
                                       value="<%=currentQuiz.getQuizName()%>" name="QuizName"
                                       id="QuizName"></td>
                        </tr>
                        <tr>
                            <td><label for="AttemptLimit">Attempts allowed: </label></td>
                            <td>
                                <input style="display: inline" type="number" min="-1"
                                       value="<%=currentQuiz.getAttemptLimit()%>"
                                       name="AttemptLimit" id="AttemptLimit" data-toggle="tooltip"
                                       data-placement="right"
                                       title=" Hint: 0 means infinite,-1 means not open this quiz"></td>
                        </tr>
                        <tr>
                            <td><label for="TimeLimit"> Time Limit (Second) (0 means no limitation): </label></td>
                            <td>
                                <input style="display: inline" type="number" min="0"
                                       value="<%=currentQuiz.getTimeLimit()%>" name="TimeLimit"
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
                                       value="<%=currentQuiz.getTotalQuestion()%>" name="TotalQuestion"
                                       id="TotalQuestion" data-toggle="tooltip"
                                       data-placement="right"
                                       title=" Hint: 0 means all question, otherwise system will pick up the numbers of question from question pool">
                            </td>
                        </tr>
                        <tr>
                            <td><br/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <th colspan="2">Question:</th>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table border="0" width="auto" id="table">
                                    <tr>
                                        <th>ID: </th>
                                        <th>Type: </th>
                                        <th>Question Content: </th>
                                        <th>Options in Question: </th>
                                        <th>Correct Answer: </th>
                                        <th>Edit</th>
                                        <th>Delete</th>
                                    </tr>
                                    <%
                                        for (Question question : (ArrayList<Question>) session.getAttribute("currentQuiz_Question")) {
                                            out.print("<tr>");
                                            out.print("<td>" + question.getQuestionID() + "</td>");
                                            out.print("<td>" + question.getQuestionType() + "</td>");
                                            out.print("<td>" + question.getQuestion() + "</td>");
                                            out.print("<td><select width='100%'>");
                                            for (QuestionOption questionOption : question.getQuestionOptionArrayList()) {
                                                out.print("<option value='" + questionOption.getOptionID() + "'>" + questionOption.getOption() + "</option>");
                                            }
                                            out.print("</select></td>");
                                            out.print("<td><select>");
                                            for (QuestionOption questionOption : question.getQuestionOptionArrayList()) {
                                                if (question.getCorrectOptionID() == questionOption.getOptionID()) {//Checked if it is correct answer
                                                    out.print("<option value='" + questionOption.getOptionID() + "' selected>" + questionOption.getOption() + "</option>");
                                                } else {
                                                    out.print("<option value='" + questionOption.getOptionID() + "'>" + questionOption.getOption() + "</option>");
                                                }
                                            }
                                            out.print("</select></td>");
                                            out.print("<td><a href='javascript:editQuestion(" + question.getQuestionID() + ");'><img src='image/edit.png'></a></td>");
                                            out.print("<td><a href='javascript:deleteQuestion(" + question.getQuestionID() + ");'><img src='image/delete.png'></a></td>");
                                            out.print("</tr>");
                                        }
                                    %>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td><br/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center;">
                                <button type="submit">Submit</button>
                                <button type="reset">Reset</button>
                            </td>
                        </tr>
                    </table>
                </form>
                <br/>


                <center><a href="javascript: window.history.back();">Back</a></center>
            </div>
        </div>
        <!-- End Middle Column -->
    </div>
</div>
<script>
    $.ajaxSetup({
        // Disable caching of AJAX responses
        cache: false
    });
    function editQuestion(id) {
        $('.modal-body').load('question/edit?action=view&id=' + id, function () {
            $('#myModal').modal({show: true});
        });
    }
</script>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Edit Question</h4>
            </div>
            <div class="modal-body">

            </div>
            <%--            <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>--%>
        </div>
    </div>
</div>
<!-- End Page Container -->

<br>
<jsp:include page="footer.jsp"/>

</body>
</html>


