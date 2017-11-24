<%@ page import="elearning.bean.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<Quiz> quizList = (ArrayList<Quiz>) session.getAttribute("quizList");
    if (quizList == null) {
        RequestDispatcher rd = request.getRequestDispatcher("/quiz?action=list");
        rd.forward(request, response);
    } else {
        for (Quiz quiz : quizList) {
            out.print("<a href='QuizEnter.jsp?id=" + quiz.getQuizID() + "'><p><i class=\"fa  fa-fw w3-margin-right w3-text-theme\"></i>" + quiz.getQuizName() + "</p></a>");
        }
    }
%>