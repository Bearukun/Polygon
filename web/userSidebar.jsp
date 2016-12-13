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
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("TilføjBygning")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
           <a>
                <form action="UserServlet" method="POST">
                    <input type="hidden" name="origin" value="addBuildingButton" />
                    <label for="addBuildingSubmit" span role="button" style="font-weight: normal">
                        <i class="glyphicon glyphicon-plus"></i>
                        Tilføj bygning
                    </label>
                    <input id="addBuildingSubmit" type="submit" class="hidden" />
                </form>
            </a>
        </li>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("sendEmailButton")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
           <a>
                <form action="UserServlet" method="POST">
                    <input type="hidden" name="origin" value="sendEmailButton" />
                    <label for="sendEmailSubmit" span role="button" style="font-weight: normal">
                        <i class="glyphicon glyphicon-envelope"></i>
                        Send email
                    </label>
                    <input id="sendEmailSubmit" type="submit" class="hidden" />
                </form>
            </a>
        </li>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("VisBygning")){%>
            <li class="active">
                <a href="" target="_self">
                    <i class="glyphicon glyphicon-wrench"></i>
                    Vis bygning 
                </a>
            </li>
        <%}%>
        <% if(request.getSession().getAttribute("ActiveSidebarMenu").toString().equals("Brugerindstillinger")){%>
            <li class="active">
        <%} else{%>
            <li>
        <%}%>
           <a>
                <form action="NavigatorServlet" method="POST">
                    <input type="hidden" name="origin" value="editProfileButton" />
                    <input type="hidden" name="source" value="user" />
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
