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
                    var clients = [
                        { "Name": "Otto Clay", "Age": 25, "Country": 1, "Address": "Ap #897-1459 Quam Avenue", "Married": false },
                        { "Name": "Connor Johnston", "Age": 45, "Country": 2, "Address": "Ap #370-4647 Dis Av.", "Married": true },
                        { "Name": "Lacey Hess", "Age": 29, "Country": 3, "Address": "Ap #365-8835 Integer St.", "Married": false },
                        { "Name": "Timothy Henson", "Age": 56, "Country": 1, "Address": "911-5143 Luctus Ave", "Married": true },
                        { "Name": "Ramona Benton", "Age": 32, "Country": 3, "Address": "Ap #614-689 Vehicula Street", "Married": false }
                    ];

                    var countries = [
                        { Name: "", Id: 0 },
                        { Name: "United States", Id: 1 },
                        { Name: "Canada", Id: 2 },
                        { Name: "United Kingdom", Id: 3 }
                    ];

                    $("#jsGrid").jsGrid({
                        width: "100%",
                        height: "400px",

                        inserting: true,
                        editing: true,
                        sorting: true,
                        paging: true,

                        data: clients,

                        fields: [
                            { name: "Name", type: "text", width: 150, validate: "required" },
                            { name: "Age", type: "number", width: 50 },
                            { name: "Address", type: "text", width: 200 },
                            { name: "Country", type: "select", items: countries, valueField: "Id", textField: "Name" },
                            { name: "Married", type: "checkbox", title: "Is Married", sorting: false },
                           // { type: "control" },
                            { type: "control", width: 100,
                                itemTemplate: function(value, item) {
                                    var $result = jsGrid.fields.control.prototype.itemTemplate.apply(this, arguments);

                                    var $customButton = $("<button>")
                                        .text("Edit Question")
                                        .click(function (e) {
                                            alert("Age: " + item.Age);
                                        });
                                    return $result.add($customButton);
                                }
                            }
                        ]
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


