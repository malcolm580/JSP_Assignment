<%@ page import="elearning.bean.Question" %>
<%@ page import="elearning.bean.QuestionOption" %>
<%@ page import="elearning.bean.User" %>

<%
    User user = (User) request.getSession().getAttribute("userInfo");
    if (request.getSession().isNew() || null == user) {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    Question currentQuestion = (Question) session.getAttribute("currentQuestion");
    if (currentQuestion == null) {
        out.print("<script> window.onload = function() { alert('Please Select the Quiz First'); window.history.back(); }</script >");
    }
%>
<jsp:useBean id="Quiz" class="elearning.bean.Quiz" scope="session"/>
<jsp:setProperty property="*" name="Quiz"/>
<html>
<head>
    <style>
        img {
            width: 20px;
            height: 20px;
        }

    </style>

</head>
<body>
<jsp:include page="header.jsp"/>
<script>
    $(function () {
        $(".w3-top").hide();
        $("#navDemo").hide();
    });
</script>
<form action="${pageContext.request.contextPath}/quiz/edit" method="post">
    <table width="100%" class="w3-left-align" id="table">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="QuestionID" value="<%=currentQuestion.getQuestionID()%>">

        <tr>
            <th>Question ID:</th>
            <td>
                <input type="number" value="<%=currentQuestion.getQuestionID()%>" readonly disabled>
            </td>
        <tr>
        <tr>
            <th>
                Type:
            </th>
            <td>
                <select name="QuestionType">
                    <option value="Multiple">Multiple</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>
                Question Content:
            </th>
            <td>
                <input type="text" value="<%=currentQuestion.getQuestion()%>" name="Question">
            </td>
        </tr>
        <tr>
            <th>
                Correct Answer:
            </th>
            <td>
                <select name="CorrectOptionID">
                    <%
                        for (QuestionOption questionOption : currentQuestion.getQuestionOptionArrayList()) {
                            if (currentQuestion.getCorrectOptionID() == questionOption.getOptionID()) {//Checked if it is correct answer
                                out.print("<option value='" + questionOption.getOptionID() + "' selected>" + questionOption.getOption() + "</option>");
                            } else {
                                out.print("<option value='" + questionOption.getOptionID() + "'>" + questionOption.getOption() + "</option>");
                            }
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2"></td>
        </tr>

        <tr>
            <td colspan="2">
                <table id="table">
                    <tr>
                        <th>Options of this question</th>
                        <th>Delete</th>
                    </tr>
                    <%
                        for (QuestionOption questionOption : currentQuestion.getQuestionOptionArrayList()) {
                            out.print("<tr><td>" + questionOption.getOption() + "</td><td><a href='javascript:deleteOption(" + questionOption.getOptionID() + ");'><img src='image/delete.png'></a></td></tr>");
                        }
                    %>
                </table>
            </td>
        </tr>
        <tr>
            <th>Add option:</th>
            <td><input type="text" onblur="addOption($(this),<%=currentQuestion.getQuestionID()%>)"></td>
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

<script>
    function addOption(element, questionID) {
        $.post("question/option/edit?action=add&id=" + questionID,
            {
                action: "add",
                id: questionID,
                option: element.val()
            },
            function (data, status) {
                console.log("Data: " + data + "\nStatus: " + status);
                if (status = "success") {
                    location.reload();
                }
            });
        editQuestion(<%=currentQuestion.getQuestionID()%>);
    }
</script>

<!-- End Page Container -->

<br>

</body>
</html>


