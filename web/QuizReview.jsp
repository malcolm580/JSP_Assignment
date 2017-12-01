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
                <form method="post" action="quizAttempt">
                    <table width="100%" class="w3-left-align" id="table">
                        <input type="hidden" name="action" value="submit">
                        <%
                            ArrayList<Question> quizArrayList = (ArrayList<Question>) session.getAttribute("currentQuestionArrayList");
                            ArrayList<String> optionList = (ArrayList<String>) session.getAttribute("optionList");
                            ArrayList<String> questionIDList = (ArrayList<String>) session.getAttribute("questionIDList");
                        %>
                        <input type="hidden" name="QuizID" value="<%=currentQuiz.getQuizID()%>">
                        <%

                            for (int i = 0; i < quizArrayList.size(); i++) {
                                Question currentQuestion = quizArrayList.get(i);
                                out.print("<tr>");
                                out.print("<th>" + (i + 1) + ". " + currentQuestion.getQuestion() + "</th>");
                                out.print("<td><select name='" + currentQuestion.getQuestionID() + "' disabled ");
                                for(int j =0 ;  j<questionIDList.size() ; j++){
                                    if(Integer.parseInt(questionIDList.get(j)) == currentQuestion.getQuestionID()){
                                            if(Integer.parseInt(optionList.get(j)) ==  currentQuestion.getCorrectOptionID()){
                                                out.print(" style= 'background-color: #00e5ff'");
                                            }else{
                                                out.print(" style= 'background-color: red'");
                                            }
                                    }
                                }
                                out.print(">");

                                for (QuestionOption questionOption : currentQuestion.getQuestionOptionArrayList()) {
                                    out.print("<option value=" + questionOption.getOptionID());
                                    for(int j =0 ;  j<questionIDList.size() ; j++){
                                        if(Integer.parseInt(questionIDList.get(j)) == currentQuestion.getQuestionID()){
                                            if(Integer.parseInt(optionList.get(j)) == questionOption.getOptionID()){
                                                out.print(" selected");
                                            }
                                        }
                                    }
                                    out.print(">" + questionOption.getOption() + "</option>");
                                }
                                out.print("</select></td>");
                                out.print("</tr>");
                            }

                        %>
                        </tr >
                    </table>
                </form>
                <br/>


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


