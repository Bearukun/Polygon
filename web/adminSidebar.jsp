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
        <li>
            <a href="adminBuildings.jsp" target="_self">
                <i class="glyphicon glyphicon-object-align-bottom"></i>
                Vis bygninger </a>
        </li>
        <li>
            <a href="adminUsers.jsp" target="_self">
                <i class="glyphicon glyphicon-th-list"></i>
                Håndter brugere </a>
        </li>
        <li>
            <a href="adminPendingBuildings.jsp" target="_self">
                <i class="glyphicon glyphicon-list"></i>
                Healthchecks </a>
        </li>
    </ul>
</div>