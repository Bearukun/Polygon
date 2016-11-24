<div class="profile-usermenu">
    <ul class="nav">
        <% if (request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("Overblik")) {%>
        <li class="active">
            <%} else {%>
        <li>
            <%}%>
            <a href="technician.jsp?refresh">
                <i class="glyphicon glyphicon-home"></i>
                Overblik </a>
        </li>
        <% if (request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("Sundhedscheck")) {%>
        <li class="active">
            <%} else {%>
        <li>
            <%}%>
            <a href="#">
                <i class="glyphicon glyphicon-home"></i>
                Sundhedscheck </a>
        </li>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("Brugerindstillinger")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
           <a>
                <form action="NavigatorServlet" method="POST">
                    <input type="hidden" name="origin" value="editProfileButton" />
                    <input type="hidden" name="source" value="technician" />
                    <label for="mySubmit" span role="button" style="font-weight: normal">
                        <i class="glyphicon glyphicon-user"></i>
                        Brugerindstillinger
                    </label>
                    <input id="mySubmit" type="submit" class="hidden" />
                </form>
            </a>
        </li>
    </ul>
</div>