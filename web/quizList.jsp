<%@ page import="elearning.bean.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%
    out.print(session.getAttribute("userInfo"));
    ArrayList<Quiz> user = (ArrayList<Quiz>) session.getAttribute("quizList");
    if (user == null) {
        response.sendRedirect("quiz?action=list");
    } else {
        //response.sendRedirect("index.jsp?msg=You%20have%20been%20logined");
        out.print(user);
    }
%>