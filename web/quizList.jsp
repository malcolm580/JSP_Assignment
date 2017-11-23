<%@ page import="elearning.bean.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<Quiz> quizList = (ArrayList<Quiz>) session.getAttribute("quizList");
    if (quizList == null) {
        response.sendRedirect("quiz?action=list");
    } else {
        for (Quiz quiz : quizList) {
            out.print(quiz.getQuizID()+","+quiz.getQuizName()+"<br />");
        }
    }
%>