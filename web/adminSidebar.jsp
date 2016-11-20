<div class="profile-usermenu">
    <ul class="nav">
        <% if (request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("Overblik")) {%>
        <li class="active">
            <%} else {%>
        <li>
            <%}%>
            <a href="admin.jsp?refresh">
                <i class="glyphicon glyphicon-home"></i>
                Overblik </a>
        </li>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("VisBygninger")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
            <a href="adminBuildings.jsp" target="_self">
                <i class="glyphicon glyphicon-object-align-bottom"></i>
                Vis bygninger </a>
        </li>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("HåndterBrugere")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
            <a href="adminUsers.jsp" target="_self">
                <i class="glyphicon glyphicon-th-list"></i>
                Håndter brugere </a>
        </li>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("Sundhedscheck")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
            <a href="#">
            <!--<a href="adminPendingBuildings.jsp" target="_self">-->
                <i class="glyphicon glyphicon-list"></i>
                Healthchecks </a>
        </li>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("Brugerindstillinger")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
           <a>
                <form action="AdminServlet" method="POST">
                    <input type="hidden" name="origin" value="editProfileButton" />
                    <label for="editProfileSubmit" span role="button" style="font-weight: normal">
                        <i class="glyphicon glyphicon-user"></i>
                        Brugerindstillinger
                    </label>
                    <input id="editProfileSubmit" type="submit" class="hidden" />
                </form>
            </a>
        </li>
    </ul>
</div>