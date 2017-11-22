
<%@ page import="elearning.bean.Quiz" %>
<%
    Quiz user = (Quiz) request.getSession().getAttribute("quizList");
    if(user==null){
        response.sendRedirect("quiz");
    }else{
        //response.sendRedirect("index.jsp?msg=You%20have%20been%20logined");
    }
%>