<div class="profile-usermenu">
    <ul class="nav">
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("Overblik")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
            <a href="user.jsp?refresh">
                <i class="glyphicon glyphicon-home"></i>
                Overblik </a>
        </li>
        <li>
            <a href="addBuilding.jsp" target="_self">
                <i class="glyphicon glyphicon-plus"></i>
                Tilføj bygning </a>
        </li>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("VisBygning")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
            <a href="" target="_self">
                <i class="glyphicon glyphicon-wrench"></i>
                Vis bygning </a>
        </li>
        <li>
           <a>
                <form action="CustomerServlet" method="POST">
                    <input type="hidden" name="origin" value="editProfileButton" />
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
