<%@page import="serviceLayer.entities.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="serviceLayer.entities.Building"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="/img/favicon.ico">
        <script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
        <title>Sunde Bygninger - Teknikerportal</title>
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
                        <% request.getSession().setAttribute("ActiveSidebarMenu", "Overblik");%>
                        <%@ include file="technicianSidebar.jsp" %>
                        <!-- END MENU -->
                    </div>
                </div>
                <!-- SITE CONTENT -->
                 <div class="col-sm-10">
                    <div id="container" class="container-fluid">
                        <h1>Overblik:</h1>

                        <% ArrayList<User> userList = new ArrayList();
                            ArrayList<Building> allBuildings = new ArrayList();
                            int user_id = (Integer) request.getSession().getAttribute("user_id");
                            String email = request.getSession().getAttribute("email").toString();
                            userList = (ArrayList<User>) request.getSession().getAttribute("userList");
                            allBuildings = (ArrayList<Building>) request.getSession().getAttribute("allBuildings");

                        %>

                        <table border="1" text-align="left" class="table table-striped">
                            <tbody>
                                <tr>
                                    <td><b>Bygnings-ID</b></td>
                                    <td><b>Bygningsnavn</b></td>
                                    <td><b>Oprettet den</b></td>
                                    <td><b>Adresse</b></td>
                                    <td><b>Postnummer</b></td>
                                    <td><b>By</b></td>
                                    <td><b>Tilstand</b></td>
                                    <td><b>Opførelsesår</b></td>
                                    <td><b>Formål</b></td>
                                    <td><b>Kvadratmeter</b></td>
                                    <td><b>Bruger(ID)</b></td>
                                    <% int buildingsThatNeedActivationButton = 0;
                                    for(int i = 0; i < allBuildings.size(); i++){
                                        if(user_id ==  allBuildings.get(i).getAssigned_tech_id()){
                                            if(allBuildings.get(i).getHealthcheck_pending()==2){
                                                buildingsThatNeedActivationButton++;
                                                break;
                                            }
                                        }
                                    }
                                    if(buildingsThatNeedActivationButton>0){%>
                                        <td><b>Muligheder</b></td>
                                    <%}%>
                                </tr>
                                <% for(int x = 0; x < allBuildings.size(); x++){
                                    if(user_id ==  allBuildings.get(x).getAssigned_tech_id()){%>
                                        <tr>
                                            <td><%= allBuildings.get(x).getbuildingId()%></td>  
                                                <% if(allBuildings.get(x).getHealthcheck_pending()==2){%>
                                                    <td><%= allBuildings.get(x).getName()%></td>
                                                <%} else{%>
                                                    <td>
                                                        <form action="TechnicianServlet" method="POST">
                                                            <input type="submit" class="submitLink" value="<%=allBuildings.get(x).getName()%>" />
                                                            <input type="hidden" name="buildingId" value="<%=allBuildings.get(x).getbuildingId()%>" />
                                                            <input type="hidden" name="origin" value="technicianOverview" />
                                                        </form>
                                                    </td>
                                                <%}%>
                                            <td><%= allBuildings.get(x).getDate_created()%></td>  
                                            <td><%= allBuildings.get(x).getAddress()%></td>  
                                            <td><%= allBuildings.get(x).getPostcode()%></td>  
                                            <td><%= allBuildings.get(x).getCity()%></td>  
                                            <td><%= allBuildings.get(x).getCondition()%></td>  
                                            <td><%= allBuildings.get(x).getConstruction_year()%></td>  
                                            <td><%= allBuildings.get(x).getPurpose()%></td>  
                                            <td><%= allBuildings.get(x).getSqm()%></td>  
                                            <td><%= email%></td>
                                            <% if(allBuildings.get(x).getHealthcheck_pending()==2){%>
                                                <td>
                                                    <form action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="acceptHealthcheckButton" />
                                                        <input type="hidden" name="buildingId" value="<%=allBuildings.get(x).getbuildingId()%>" />
                                                        <input class="btn btn-primary" type="submit" value="Påbegynd" />
                                                    </form>
                                                </td>
                                            <%}%>
                                        </tr>
                                    <%}%>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
