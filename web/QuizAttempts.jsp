<%@ page import="elearning.bean.Question" %>
<%@ page import="elearning.bean.QuestionOption" %>
<%@ page import="elearning.bean.Quiz" %>
<%@ page import="elearning.bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%
    User user = (User) request.getSession().getAttribute("userInfo");
    if (null == user) {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    Quiz currentQuiz = (Quiz) session.getAttribute("currentQuiz");
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
                <b>
                    <center>
                        <h4><%=currentQuiz.getModule().getModuleName()%>
                        </h4>
                        <h5><%=currentQuiz.getQuizName()%>
                        </h5>
                    </center>
                </b>
                <hr>
                You have answer the following questions:
                <span class="w3-right">Remaining Time: <span class="secondToDuration" id="timeLabel"></span></span>
                <form method="post" action="quizAttempt" id="form1">
                    <script type="text/javascript">
                        var c = 0;
                        var t;
                        var timer_is_on = 0;

                        function timedCount() {
                            if ($("#duration").val() !== "0") {
                                $("#timeLabel").text($("#duration").val() - c);
                                if ($("#timeLabel").text() < 0) {
                                    $("#timeLabel").css("background-color", "red");
                                }
                                $(".secondToDuration").each(function (index) {
                                    $(this).text(sec2str($(this).text()));
                                });
                            } else {
                                $("#timeLabel").text("Unlimited");
                                stopCount();
                            }

                            c = c + 1;
                            t = setTimeout("timedCount()", 1000);
                        }

                        function doTimer() {
                            if (!timer_is_on) {
                                timer_is_on = 1;
                                timedCount();
                            }
                        }

                        function stopCount() {
                            clearTimeout(t);
                            timer_is_on = 0;
                        }

                        $(function () {
                            doTimer();
                        });

                        function checkDuration() {
                            if ($("#duration").val() != "0") {
                                stopCount();
                                if (c > $("#duration").val()) {
                                    window.location.href = "./quiz?action=EnterQuiz&quizid=" + $("#QuizID").val() + "&msg=You did too long!";
                                    return false;
                                }
                                $("#duration").val(c);
                                $("#form1").submit();
                            } else {
                                $("#duration").val(c);
                                $("#form1").submit();
                            }
                        }
                    </script>
                    <table width="100%" class="w3-left-align" id="table">
                        <input type="hidden" name="action" value="submit">
                        <%
                            ArrayList<Question> quizArrayList = (ArrayList<Question>) session.getAttribute("currentQuestionArrayList");
                            //雖然可恥但是有用
                            // Storing the question id which user is answering
                            for (Question question : quizArrayList) {
                                out.print("<input type='hidden' name='IDs' value='" + question.getQuestionID() + "'>");
                            }
                        %>
                        <input type="hidden" name="QuizID" id="QuizID" value="<%=currentQuiz.getQuizID()%>">
                        <input type="hidden" name="duration" id="duration" value="<%=currentQuiz.getTimeLimit()%>">
                        <%

                            for (int i = 0; i < quizArrayList.size(); i++) {
                                Question currentQuestion = quizArrayList.get(i);
                                out.print("<tr>");
                                out.print("<th>" + (i + 1) + ". " + currentQuestion.getQuestion() + "</th>");
                                out.print("<td><select name='" + currentQuestion.getQuestionID() + "'>");
                                for (QuestionOption questionOption : currentQuestion.getQuestionOptionArrayList()) {
                                    out.print("<option value=" + questionOption.getOptionID() + ">" + questionOption.getOption() + "</option>");
                                }
                                out.print("</select></td>");
                                out.print("</tr>");
                            }

                        %>

                        <tr>
                            <td colspan="2">
                                <center>
                                    <button type="button" onclick="checkDuration();return false;">Submit</button>
                                    <button type="reset">Reset</button>
                                </center>
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


    <!-- End Page Container -->
</div>
<br>
<jsp:include page="footer.jsp"/>

</body>
</html>


