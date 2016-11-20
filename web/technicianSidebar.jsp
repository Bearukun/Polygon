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
    </ul>
</div>