<%--
  Created by IntelliJ IDEA.
  User: youma
  Date: 23/11/2017
  Time: 0:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import=" elearning.bean.Module ,java.util.ArrayList" %>
<%@ page import="elearning.bean.Module" %>
<%@ page import="elearning.bean.Quiz" %>
<%@ page import="elearning.bean.User" %>
<html>
<head>
    <title>Title</title>

</head>

<body>

<jsp:include page="../header.jsp"/>

<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
    <!-- The Grid -->
    <div class="w3-row">

        <!-- Left Column -->
        <jsp:include page="../leftContent.jsp"/>

        <!-- Middle Column -->
        <div class="w3-col m7">

            <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <%--<img src="/w3images/avatar2.png" alt="Avatar" class="w3-left w3-circle w3-margin-right"--%>
                     <%--style="width:60px">--%>
                <%--<span class="w3-right w3-opacity">1 min</span>--%>
                <h4>Quiz Reporting -- <%= session.getAttribute("quizName")%></h4>
                <h5>Total question number -- <%= session.getAttribute("quizTotalQuestion") %> </h5>
                <hr class="w3-clear">
                <canvas id="myChart" width="300" height="200"></canvas>

                <script type="text/javascript">
                    <%--Get Json form the servlet--%>
                    var ctx = document.getElementById("myChart");
                    var myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: <%= request.getSession().getAttribute("nameArrayString") %>,
                            datasets: [{
                                label : "The Correct Count" ,
                                data: <%= request.getSession().getAttribute("scoreArrayString") %>,
                                backgroundColor: [
                                    'rgba(255, 99, 132, 0.2)',
                                    'rgba(54, 162, 235, 0.2)',
                                    'rgba(255, 206, 86, 0.2)',
                                    'rgba(75, 192, 192, 0.2)',
                                    'rgba(153, 102, 255, 0.2)',
                                    'rgba(255, 159, 64, 0.2)'
                                ],
                                borderColor: [
                                    'rgba(255,99,132,1)',
                                    'rgba(54, 162, 235, 1)',
                                    'rgba(255, 206, 86, 1)',
                                    'rgba(75, 192, 192, 1)',
                                    'rgba(153, 102, 255, 1)',
                                    'rgba(255, 159, 64, 1)'
                                ],
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                yAxes: [{
                                    ticks: {
                                        beginAtZero:true
                                    }
                                }]
                            },
                            title: {
                                display: true,
                                text: 'Chart for all selected student',
                                fontSize : 20
                            }
                        }
                    });
                </script>
                <h4>
                    <b>
                    Highest Result : <%
                    User high = (User) session.getAttribute("highestResultUser");
                    out.print(high.getUsername());
                %>
                </b>

                </h4>
                <h4>
                    <b> Lowest Result : <%
                    User low = (User) session.getAttribute("lowestResultUser");
                    out.print(low.getUsername());
                %>
                    </b>
                </h4>

            </div>


            <!-- End Middle Column -->
        </div>


        <!-- End Grid -->
    </div>

    <!-- End Page Container -->
</div>


<jsp:include page="../footer.jsp"/>

</body>



</html>
