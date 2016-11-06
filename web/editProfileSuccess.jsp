<%@page import="serviceLayer.entities.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="serviceLayer.entities.Building"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="favicon.ico">
        <script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
        <title>Sunde Bygninger - Brugerportal</title>
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
                                <li class="active">
                                    <a href="user.jsp?refresh">
                                        <i class="glyphicon glyphicon-home"></i>
                                        Overblik </a>
                                </li>
                                <li>
                                    <a href="addBuilding.jsp" target="_self">
                                        <i class="glyphicon glyphicon-plus"></i>
                                        Tilføj bygning </a>
                                </li>
                                <li>
                                    <a href="editProfile.jsp">
                                        <i class="glyphicon glyphicon-user"></i>
                                        Bruger indstillinger </a>
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

                        <% ArrayList<Building> tempAL = new ArrayList();

                            tempAL = (ArrayList<Building>) request.getSession().getAttribute("tempAL");
                        %>

                        <!-- Checks if the user has any buildings. -->
                        <!-- If not, no table will be created/shown.-->

                        <% if (tempAL.isEmpty()) {%>
                        <div id="container" class="container-fluid">

                            <p class="text-danger">Du har ingen bygninger! Tryk på 'Tilføj bygning'</p>
                        </div>
                        <!-- Button/Link to create buildings-->
                        <a href="addBuilding.jsp" class="btn btn-info" role="button">Tilføj Bygning</a>

                        <!-- If the user has any buildings, the table is shown with the building(s) -->
                        <% } else { %>
                        <table border="1" text-align="left" class="table table-striped">
                            <tbody>
                                <tr>
                                    <td><b>Bygnings Navn</b></td>
                                    <td><b>Oprettet den</b></td>
                                    <td><b>Adresse</b></td>
                                    <td><b>Postnr.</b></td>
                                    <td><b>By</b></td>
                                    <td><b>Tilstand</b></td>
                                    <td><b>Opførelses År</b></td>
                                    <td><b>Formål</b></td>
                                    <td><b>KvadratMeter</b></td>
                                    
                                </tr>
                                <%
                                    for (int i = 0; i < tempAL.size(); i++) {
                                %><tr>
                                    <td><a href="editBuilding.jsp?value=<%=tempAL.get(i).getBuilding_id()%>"><%out.println(tempAL.get(i).getName());%></a></td>  
                                    <td><%out.println(tempAL.get(i).getDate_created());%></td>  
                                    <td><%out.println(tempAL.get(i).getAddress());%></td>  
                                    <td><%out.println(tempAL.get(i).getPostcode());%></td>  
                                    <td><%out.println(tempAL.get(i).getCity());%></td>  
                                    <td><%out.println(tempAL.get(i).getCondition());%></td>  
                                    <td><%out.println(tempAL.get(i).getConstruction_year());%></td>  
                                    <td><%out.println(tempAL.get(i).getPurpose());%></td>  
                                    <td><%out.println(tempAL.get(i).getSqm());%></td>  
                                </tr>
                                <%}
                                %>
                            </tbody>
                        </table>
                        <% }%>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>