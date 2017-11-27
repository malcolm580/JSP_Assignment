<%@ page import="elearning.bean.Quiz" %>
<%@ page import="elearning.bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%
    User user = (User) request.getSession().getAttribute("userInfo");
    if (request.getSession().isNew()||null == user) {
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
                <h5>You can edit the following quiz.</h5>
                <table width="100%" class="w3-left-align">
                    <tr>
                        <th>Quiz ID</th>
                        <th>Module ID</th>
                        <th>Review</th>
                    </tr>




                </table>
                <div id="jsGrid"></div>
                <script>
                    $(function () {
                        $.ajax({
                            type: "GET",
                            url: "./QuizJSONController",
                            dataType: "json"
                        }).done(function (data) {
                            console.log(data);
                            $("#jsGrid").jsGrid({
                                width: "100%",
                               // height: "400px",

                                filtering: true,
                                inserting: false,
                                editing: true,
                                sorting: true,
                                paging: true,
                                autoload: true,
                                pageSize: 10,
                                pageButtonCount: 5,
                                deleteConfirm: "Do you really want to delete this quiz?",

                                data: data,

                                fields: [
                                    { name: "QuizID", type: "number", width: 70 ,title:"Quiz ID",readOnly: true },
                                    { name: "ModuleID", type: "number", width: 70 ,title:"Module"},
                                    { name: "QuizName", type: "text", width: 200 ,title:"Quiz Name"},
                                    { name: "AttemptLimit", type: "number",title:"Attempt Limit" },
                                    { name: "TimeLimit", type: "number", title: "Time Limit", sorting: false },
                                    { name: "TotalQuestion", type: "number", title: "Total Question", sorting: false },
                                    // { type: "control" },
                                    { type: "control", width: 100,
                                        itemTemplate: function(value, item) {
                                            var $result = jsGrid.fields.control.prototype.itemTemplate.apply(this, arguments);

                                            var $customButton = $("<button>")
                                                .text("Edit Question")
                                                .click(function (e) {
                                                    window.location.href = "quiz?action=Edit&quizid="+item.QuizID;
                                                });
                                            return $result.add($customButton);
                                        }
                                    }
                                ]
                            });
                        })
                    });


                </script>
                <br />


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


