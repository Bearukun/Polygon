<%@page import="serviceLayer.enties.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="serviceLayer.enties.Building"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
        <title>Sunde Bygninger - Admin Bygningsoversigt</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!--Adding our own css-->
        <link href="css/stylesheet.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container-fluid">
            <div class="siteContent">
                <div class="col-sm-2">
                    <div class="profile-sidebar">
                        <!-- SIDEBAR USERPIC -->
                        <div class="profile-userpic">
                            <img src="img/logo2.png" class="img-responsive" alt="">
                        </div>
                        <!-- END SIDEBAR USERPIC -->
                        <!-- SIDEBAR USER TITLE -->
                        <div class="profile-usertitle">
                            <div class="profile-usertitle-name">
                                <%= session.getAttribute("email")%>
                            </div>
                            <div class="profile-usertitle-job">
                                <%= session.getAttribute("type")%>
                            </div>
                        </div>
                        <!-- END SIDEBAR USER TITLE -->
                        <!-- SIDEBAR BUTTONS -->
                        <div class="profile-userbuttons">
                            <a href="${pageContext.request.contextPath}/Logout" class="btn btn-danger" role="button">Log ud</a>
                        </div>
                        <!-- END SIDEBAR BUTTONS -->
                        <!-- SIDEBAR MENU - For icons find class names here http://getbootstrap.com/components/ -->
                        <div class="profile-usermenu">
                            <ul class="nav">
                                <li>
                                    <a href="admin.jsp?refresh">
                                        <i class="glyphicon glyphicon-home"></i>
                                        Overblik </a>
                                </li>
                                <li class="active">
                                    <a href="adminBuildings.jsp" target="_self">
                                        <i class="glyphicon glyphicon-object-align-bottom"></i>
                                        Vis bygninger </a>
                                </li>
                                <li>
                                    <a href="adminUsers.jsp" target="_self">
                                        <i class="glyphicon glyphicon-th-list"></i>
                                        Vis brugere </a>
                                </li>
                            </ul>
                        </div>
                        <!-- END MENU -->
                    </div>
                </div>
                <!-- SITE CONTENT -->
                <div class="col-sm-10">
                    <div id="container" class="container-fluid">
                        <h1>Bygninger:</h1>

                        <% ArrayList<User> tempUL = new ArrayList();
                            ArrayList<Building> allBuildings = new ArrayList();

                            tempUL = (ArrayList<User>) request.getSession().getAttribute("tempUL");
                            allBuildings = (ArrayList<Building>) request.getSession().getAttribute("allBuildings");

                        %>

                        <table border="1" text-align="left" class="table table-striped">
                            <tbody>
                                <tr>
                                    <td><b>Building(ID)</b></td>
                                    <td><b>Adresse</b></td>
                                    <td><b>Postnummer</b></td>
                                    <td><b>By</b></td>
                                    <td><b>Bruger(ID)</b></td>
                                </tr>
                                <%
                                    for (int i = 0; i < tempUL.size(); i++) {
                                        for(int x = 0; x < allBuildings.size(); x++){
                                            if(tempUL.get(i).getUser_id() ==  allBuildings.get(x).getUser_id()){
                                %><tr>
                                    <td><%out.println(allBuildings.get(x).getBuilding_id());%></td>  
                                    <td><%out.println(allBuildings.get(x).getAddress());%></td>  
                                    <td><%out.println(allBuildings.get(x).getPostcode());%></td>  
                                    <td><%out.println(allBuildings.get(x).getCity());%></td>  
                                    <td><%out.println(tempUL.get(i).getEmail());%></td> 
                                </tr>
                                <%}}}
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
