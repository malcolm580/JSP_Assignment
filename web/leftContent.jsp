<%@ page buffer="5kb" autoFlush="false" %>

<!-- Left Column -->
<div class="w3-col m3">
    <!-- Profile -->
    <div class="w3-card w3-round w3-white">
        <div class="w3-container">
            <jsp:useBean id="userInfo" class="elearning.bean.User" scope="session"/>
            <h4 class="w3-center">My Profile</h4>
                <p class="w3-center"><img src="https://www.w3schools.com/w3images/avatar3.png" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
            <hr>
            <p><i class="fa fa-pencil fa-fw w3-margin-right w3-text-theme"></i> <jsp:getProperty name="userInfo" property="role"/><b /></p>
            <p><i class="fa fa-home fa-fw w3-margin-right w3-text-theme"></i> <jsp:getProperty name="userInfo" property="email"/></p>
            <p><i class="fa fa-birthday-cake fa-fw w3-margin-right w3-text-theme"></i> April 1, 1988</p>
        </div>
    </div>
    <br>

    <!-- Accordion -->
    <div class="w3-card w3-round">
        <div class="w3-white">
            <button onclick="myFunction('Demo1')" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-circle-o-notch fa-fw w3-margin-right"></i> My Groups</button>
            <div id="Demo1" class="w3-hide w3-container">
                <p>Some text..</p>
            </div>
            <button onclick="myFunction('Demo2')" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i> My Events</button>
            <div id="Demo2" class="w3-hide w3-container">
                <p>Some other text..</p>
            </div>
            <button onclick="myFunction('Demo3')" class="w3-button w3-block w3-theme-l1 w3-left-align"><i class="fa fa-users fa-fw w3-margin-right"></i> My Photos</button>

        </div>
    </div>
    <br>

    <%--<!-- Interests -->--%>
    <%--<div class="w3-card w3-round w3-white w3-hide-small">--%>
        <%--<div class="w3-container">--%>
            <%--<p>Interests</p>--%>
            <%--<p>--%>
                <%--<span class="w3-tag w3-small w3-theme-d5">News</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-d4">W3Schools</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-d3">Labels</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-d2">Games</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-d1">Friends</span>--%>
                <%--<span class="w3-tag w3-small w3-theme">Games</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-l1">Friends</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-l2">Food</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-l3">Design</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-l4">Art</span>--%>
                <%--<span class="w3-tag w3-small w3-theme-l5">Photos</span>--%>
            <%--</p>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<br>--%>

    <%--<!-- Alert Box -->--%>
    <%--<div class="w3-container w3-display-container w3-round w3-theme-l4 w3-border w3-theme-border w3-margin-bottom w3-hide-small">--%>
        <%--<span onclick="this.parentElement.style.display='none'" class="w3-button w3-theme-l3 w3-display-topright">--%>
          <%--<i class="fa fa-remove"></i>--%>
        <%--</span>--%>
        <%--<p><strong>Hey!</strong></p>--%>
        <%--<p>People are looking at your profile. Find out who.</p>--%>
    <%--</div>--%>
    <div class="w3-card w3-round w3-white w3-hide-small">
        <div class="w3-container">
            <p>Available Quiz</p>
            <p id="quizList">
               <script>
                   $(function () {

                           $.get("./quiz?action=list",function(data,status){
                               $("#quizList").html(data);
                           });
                   });
               </script>
            </p>
        </div>
    </div>
    <%--<br>--%>
    <%----%>

</div>
