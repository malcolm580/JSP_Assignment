<%--
  Created by IntelliJ IDEA.
  User: youma
  Date: 23/11/2017
  Time: 0:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import=" elearning.bean.Module ,java.util.ArrayList" %>
<%@ page import="elearning.bean.Module" %>
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

            <div class="w3-row-padding">
                <div class="w3-col m12">
                    <div class="w3-card w3-round w3-white">
                        <div class="w3-container w3-padding">
                            <h3>Module Quiz Report -- Module Selection</h3>
                            <h6 class="w3-opacity">Search Module</h6>
                            <input style="width: 100%;" id="search" class="w3-border w3-padding" />
                            <h5>Please select one module</h5>
                            <%--<button type="button" class="w3-button w3-theme" style="margin-left: 88.5%">Search</button>--%>
                        </div>
                    </div>
                </div>
            </div>

            <%
                ArrayList moduleList = (ArrayList) session.getAttribute("moduleList");

                for (Object bean : moduleList) {
                    Module module = (Module) bean;
                    out.println("<div class=\"moduleColumn w3-container w3-card w3-white w3-round w3-margin\"><br>");
                    out.println("<h4><a href='reportMenu?action=getModuleQuiz&moduleID="+ module.getModuleID()+"'>" +  module.getModuleName()+"</a></h4><br />");
                    out.println("</div>");
                }

                if(moduleList.size() == 0){
                    out.print("You have no any module");
                }

            %>

            <%--<div class="w3-container w3-card w3-white w3-round w3-margin"><br>--%>
                <%--<img src="/w3images/avatar2.png" alt="Avatar" class="w3-left w3-circle w3-margin-right"--%>
                     <%--style="width:60px">--%>
                <%--<span class="w3-right w3-opacity">1 min</span>--%>
                <%--<h4>Module Reporting</h4><br>--%>
                <%--<hr class="w3-clear">--%>
                 <%--<h3>--%>
               <%----%>
                 <%--</h3>--%>

            <%--</div>--%>


            <!-- End Middle Column -->
        </div>


        <!-- End Grid -->
    </div>

    <!-- End Page Container -->
</div>

<script>
    $(document).ready(function () {
        $("#search").keyup(function () {
            $(".moduleColumn").hide();
            var input = $('#search').val();
            $(".moduleColumn:contains(" + input + ")").show();

        });
    });
</script>


<jsp:include page="../footer.jsp"/>
</body>
</html>
