<%@page import="serviceLayer.entities.Document"%>
<%@page import="serviceLayer.entities.DamageRepair"%>
<%@page import="serviceLayer.entities.MoistureInfo"%>
<%@page import="serviceLayer.entities.Issue"%>
<%@page import="serviceLayer.entities.Healthcheck"%>
<%@page import="serviceLayer.entities.Room"%>
<%@page import="serviceLayer.entities.Area"%>
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
        <title>Sunde Bygninger - Vis bygning</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <!--Adding our own css-->
        <link href="css/stylesheet.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>



        <% ArrayList<Area> buildingAreas = new ArrayList();
            buildingAreas = (ArrayList<Area>) request.getSession().getAttribute("buildingAreas");

            ArrayList<Room> buildingRooms = new ArrayList();
            buildingRooms = (ArrayList<Room>) request.getSession().getAttribute("buildingRooms");

            ArrayList<Document> buildingDocuments = new ArrayList();
            buildingDocuments = (ArrayList<Document>) request.getSession().getAttribute("buildingDocuments");

            ArrayList<Building> allBuildings = new ArrayList();
            ArrayList<Healthcheck> buildingHealthchecks = new ArrayList();
            buildingHealthchecks = (ArrayList<Healthcheck>) request.getSession().getAttribute("buildingHealthchecks");

            ArrayList<MoistureInfo> allMoistureMeasurements = new ArrayList();
            allMoistureMeasurements = (ArrayList<MoistureInfo>) request.getSession().getAttribute("allMoistureMeasurements");

            ArrayList<DamageRepair> allDamageRepairs = new ArrayList();
            allDamageRepairs = (ArrayList<DamageRepair>) request.getSession().getAttribute("allDamageRepairs");

            ArrayList<Issue> healthcheckIssues = new ArrayList();
            healthcheckIssues = (ArrayList<Issue>) request.getSession().getAttribute("healthcheckIssues");
            int user_id = (Integer) request.getSession().getAttribute("user_id");
            allBuildings = (ArrayList<Building>) request.getSession().getAttribute("allBuildings");
            Building build = new Building();
        %>
        <!--request.getSession().getAttribute("buildingBeingEdited");-->
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
                                <%= session.getAttribute("loginName")%>
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
                        <% request.getSession().setAttribute("ActiveSidebarMenu", "VisBygning");
                        %>
                        <%@ include file="technicianSidebar.jsp" %>
                        <!-- END MENU -->
                    </div>
                </div>
                <!-- SITE CONTENT -->
                <!-- If else statements show different page contents depending on whether a building is 'just' being viewed or whether it is being edited -->    
                <%
                    String source = "";
                    if (request.getSession().getAttribute("source") != null) {
                        source = request.getSession().getAttribute("source").toString();
                    }
                    switch (source) {

                        case "completeHealthcheckButton":
                %>
                <div class="col-sm-10">
                    <div id="container" class="container-fluid">    
                        <h1>Færdiggør sundhedscheck</h1>

                        <div class="container-fluid">
                            <form class="form-edit-profile" action="TechnicianServlet" enctype="multipart/form-data" method="POST"> 
                                <div class="col-sm-4">
                                    <p>Bygningsansvarlig</p>                      
                                    <input type="text" name="buildingResponsible" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                    <br><br>
                                    <p>Tilstandsgrad</p>
                                    <select name="condition">
                                        <option value="GOOD" selected="GOOD">God</option>
                                        <option value="MEDIUM" selected="MEDIUM">Middel</option>
                                        <option value="POOR" selected="POOR">Dårlig</option>
                                    </select>
                                    <input type="hidden" name="origin" value="viewBuilding" />
                                    <input type="hidden" name="originSection" value="completeHealthcheck" />
                                    <input class="btn btn-primary" type="submit" value="Færdiggør" />
                                </div>
                            </form>
                        </div>
                    </div>
                    <%
                            break;

                        case "addIssueButton":
                    %>
                    <div class="col-sm-10">
                        <div id="container" class="container-fluid">    
                            <h1>Registrer problem</h1>

                            <div class="container-fluid">
                                <form class="form-edit-profile" action="TechnicianServlet" enctype="multipart/form-data" method="POST"> 
                                    <div class="col-sm-4">
                                        <p>Beskrivelse</p>                      
                                        <input type="text" name="description" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                        <br><br>
                                        <p>Anbefalet behandling</p>                      
                                        <input type="text" name="recommendation" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                        <br><br>
                                        <div class="panel panel-default">
                                            <div class="panel-heading">Upload et billede</div>
                                            <div class="panel-body">Upload et billede af problemet for nemmere identifikation.</div>
                                            <input class="panel-body" type="file" name="img" size="50" accept=".jpg, .jpeg" />
                                        </div>
                                        <input type="hidden" name="origin" value="viewBuilding" />
                                        <input type="hidden" name="originSection" value="addIssue" />
                                        <input class="btn btn-primary" type="submit" value="Registrer" />
                                    </div>
                                </form>
                            </div>
                        </div>
                        <%
                                break;

                            case "registerDamageRepairButton":
                        %>
                        <div class="col-sm-10">
                            <div id="container" class="container-fluid">    
                                <h1>Registrer tidligere skade</h1>

                                <div class="container-fluid">
                                    <form action="TechnicianServlet" method="POST"> 
                                        <div class="col-sm-4">
                                            <p>Hvornår skete skaden?</p>                      
                                            <input type="text" name="damageTime" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                            <br><br>
                                            <p>Sted skade skete</p>                      
                                            <input type="text" name="damageLocation" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                            <br><br>
                                            <p>Detaljer</p>                      
                                            <input type="text" name="damageDetails" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                            <br><br>
                                            <p>Arbejde udført</p>                      
                                            <input type="text" name="workDone" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                            <br><br>
                                            <p>Skadestype</p>
                                            <select name="type">
                                                <option value="DAMP" selected="DAMP">Damp</option>
                                                <option value="ROTFUNGUS" selected="ROTFUNGUS">Råd/Svamp</option>
                                                <option value="MOULD" selected="MOULD">Mug</option>
                                                <option value="FIRE" selected="FIRE">Brand</option>
                                                <option value="OTHER" selected="OTHER">Andet</option>
                                            </select>
                                            <input type="hidden" name="origin" value="viewBuilding" />
                                            <input type="hidden" name="originSection" value="registerDamageRepair" />
                                            <input class="btn btn-primary" type="submit" value="Registrer" />
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <%
                                    break;

                                case "registerMoistButton":
                            %>
                            <div class="col-sm-10">
                                <div id="container" class="container-fluid">    
                                    <h1>Registrer fugtmåling</h1>

                                    <div class="container-fluid">
                                        <form action="TechnicianServlet" enctype="multipart/form-data" method="POST"> 
                                            <div class="col-sm-4">
                                                <p>Målepunkt</p>                      
                                                <input type="text" name="measurePoint" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                                <br><br>
                                                <p>Målt værdi</p>                      
                                                <input type="text" name="measureValue" value="" style="height: auto; width: auto; margin-bottom: 1px;" />
                                                <br><br>
                                                <input type="hidden" name="origin" value="viewBuilding" />
                                                <input type="hidden" name="originSection" value="registerMoist" />
                                                <input class="btn btn-primary" type="submit" value="Registrer" />
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <%
                                        break;

                                    case "createAreaButton":
                                %>
                                <div class="col-sm-10">
                                    <div id="container" class="container-fluid">
                                        <h1>Nyt område:</h1>

                                        <form class="form-view-building" id="newArea" action="TechnicianServlet" method="POST">
                                            <p>Områdenavn</p>
                                            <input type="text" name="areaName" />
                                            <br><br>
                                            <p>Beskrivelse</p>
                                            <input type="text" name="areaDesc" />
                                            <br><br>
                                            <p>Kvadratmeter</p>
                                            <input type="number" name="areaSqm" />
                                            <br><br>
                                            <p>Bygning dropdown</p>
                                            <input type="hidden" name="selectedBuilding" value="<%=request.getParameter("value")%>" />
                                            <input type="hidden" name="origin" value="viewBuilding" />
                                            <input type="hidden" name="originSection" value="createArea" />
                                            <input class="btn btn-primary" type="submit" value="Opret område" />
                                        </form>
                                    </div>
                                </div>
                                <%
                                        break;

                                    case "createRoomButton":
                                %>
                                <div class="col-sm-10">
                                    <div id="container" class="container-fluid">
                                        <h1>Nyt lokale:</h1>

                                        <form class="form-view-building" id="newRoom" action="TechnicianServlet" method="POST">
                                            <p>Lokalenavn</p>
                                            <input type="text" name="roomName" />
                                            <br><br>
                                            <p>Beskrivelse</p>
                                            <input type="text" name="roomDesc" />
                                            <br><br>
                                            <p>Kvadratmeter</p>
                                            <input type="number" name="roomSqm" />
                                            <br><br>
                                            <input type="hidden" name="selectedBuilding" value="<%=request.getParameter("value")%>" />
                                            <input type="hidden" name="origin" value="viewBuilding" />
                                            <input type="hidden" name="originSection" value="createRoom" />
                                            <input class="btn btn-primary" type="submit" value="Opret lokale" />
                                        </form>
                                    </div>
                                </div>
                                <%
                                        break;

                                    //If the building's details are being edited
                                    case "editBuildingButton":
                                %>
                                <div class="col-sm-10">
                                    <%
                                        //Loop through entire buildings list
                                        for (int i = 0; i < allBuildings.size(); i++) {
                                            //If the currently selected building has the same building id as the one saved in the Session
                                            if (allBuildings.get(i).getbuildingId() == Integer.parseInt(request.getParameter("value"))) {
                                                //Save the building in the reference object build so its details can be shown on page
                                                build = allBuildings.get(i);
                                                request.getSession().setAttribute("buildingBeingEdited", build);
                                            }
                                        }
                                    %>
                                    <h1>Rediger bygning</h1>
                                    <form class="form-view-building" id="editBuilding" action="TechnicianServlet" method="POST">
                                        <p>Bygningsnavn</p>
                                        <input type="text" name="buildingName" value="<%=build.getName()%>" />
                                        <br><br>
                                        <p>Adresse</p>
                                        <input type="text" name="address" value="<%=build.getAddress()%>" />
                                        <br><br>
                                        <p>Postnr.</p>
                                        <input type="number" name="postcode" min="1000" max="9999" value="<%=build.getPostcode()%>" />
                                        <br><br>
                                        <p>By</p>
                                        <input type="text" name="city" value="<%=build.getCity()%>" />
                                        <br><br>
                                        <p>Opførelsesår</p>
                                        <input type="text" name="constructionYear" value="<%=build.getConstruction_year()%>" />
                                        <br><br>
                                        <p>Formål</p>
                                        <select name="purpose">
                                            <!-- Scriptlet section to ensure the correct option is selected as default  -->
                                            <%
                                                ArrayList<String> bldgPurpose = new ArrayList();
                                                bldgPurpose.add("Landbrug");
                                                bldgPurpose.add("Erhverv");
                                                bldgPurpose.add("Bolig");
                                                bldgPurpose.add("Uddannelse");
                                                bldgPurpose.add("Offentlig");
                                                bldgPurpose.add("Industriel");
                                                bldgPurpose.add("Militær");
                                                bldgPurpose.add("Religiøs");
                                                bldgPurpose.add("Transport");
                                                bldgPurpose.add("Andet");

                                                for (int i = 0; i < bldgPurpose.size(); i++) {
                                                    if (build.getPurpose().equals(bldgPurpose.get(i))) {
                                            %><option selected="<%=bldgPurpose.get(i)%>" value="<%=bldgPurpose.get(i)%>"><%=bldgPurpose.get(i)%></option><%
                                            } else {
                                            %><option value="<%=bldgPurpose.get(i)%>"><%=bldgPurpose.get(i)%></option><%
                                                    }
                                                }
                                                %>
                                        </select>
                                        <br><br>
                                        <p>Kvadratmeter</p>
                                        <input type="number" name="sqm" max="51660" value="<%=build.getSqm()%>" />
                                        <br><br>
                                        <input type="hidden" name="selectedBuilding" value="<%=request.getParameter("value")%>" />
                                        <input type="hidden" name="origin" value="viewBuilding" />
                                        <input type="hidden" name="originSection" value="editBuilding" />
                                        <input class="btn btn-primary" type="submit" value="Gem ændringer" />
                                    </form>
                                    <br><br>
                                    <form method="post" action="TechnicianServlet" enctype="multipart/form-data">
                                        <input type="hidden" name="selectedBuilding" value="<%=request.getParameter("value")%>" />
                                        <div class="panel panel-default">
                                            <div class="panel-heading">Upload et billede</div>
                                            <div class="panel-body">Upload et billede af din bygning så vi har et overblik over hvordan den ser ud.</div>
                                        </div>
                                        <input class="panel-body" type="file" name="img" size="50" accept=".jpg, .jpeg" />
                                        <input type="hidden" name="originSection" value="editBuildingImage" />
                                        <input class="btn btn-primary" type="submit" value="Upload billed" />
                                    </form>
                                </div>


                                <%
                                        break;

                                    //If none of the building's details are being edited
                                    default:

                                %>    
                                <div class="col-sm-10">
                                    <% for (int i = 0; i < allBuildings.size(); i++) {//Loop through entire buildings list                       
                                            //If the currently selected building has the same building id as the one saved in the Session
                                            if (allBuildings.get(i).getbuildingId() == Integer.parseInt(request.getParameter("value"))) {
                                                //Save the building in the reference object build so its details can be shown on page
                                                build = allBuildings.get(i);
                                                request.getSession().setAttribute("buildingBeingEdited", build);
                                            }
                                        }%>
                                    <h1>Vis bygning</h1>

                                    <table text-align="left" class="table">
                                        <tbody>
                                            <tr bgcolor='#c0dee8'>
                                                <th colspan="4"><b>Staminformationer</b></th>
                                            </tr>
                                            <tr>
                                                <td><%=build.getName()%><br>
                                                    <%=build.getAddress()%><br>
                                                    <%=build.getPostcode()%> <%=build.getCity()%>
                                                </td>
                                                <td>Opførelsesår: <%=build.getConstruction_year()%><br>
                                                    Formål: <%=build.getPurpose()%><br>
                                                    Kvadratmeter: <%=build.getSqm()%>
                                                </td>
                                                <td>
                                                    <img src="./GetImage?type=building&id=<%=build.getbuildingId()%>" class="img-fluid " height="250" alt="Responsive image">
                                                </td>    
                                                <td>
                                                    <form class="form-view-building" id="editBuilding" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="editBuildingButton" />
                                                        <input class="btn btn-primary" type="submit" value="Rediger" />
                                                    </form>
                                                    <br>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>  
                                    <br><br>

                                    <!--Table of building areas and rooms -->
                                    <table text-align="left" class="table">
                                        <tbody>
                                            <tr bgcolor='#c0dee8'>
                                                <th colspan="2">Navn</th>
                                                <th>Beskrivelse</th>
                                                <th>Kvadratmeter</th>
                                                <th colspan="2">Muligheder</th>
                                                <th>
                                                    <form class="form-view-building" id="viewBuilding" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="createAreaButton" />
                                                        <input class="btn btn-primary" type="submit" value="Nyt område" />
                                                    </form>
                                                </th>
                                            </tr>
                                            <% if (buildingAreas.size() == 0) {%>
                                            <tr>
                                                <td colspan="5">Ingen områder oprettet</td>
                                            </tr>
                                            <%}%> 
                                            <% for (int i = 0; i < buildingAreas.size(); i++) {%>
                                            <tr>
                                                <td colspan="2" style="border-top: 1px solid #000000"><b><%=buildingAreas.get(i).getName()%></b></td>
                                                <td colspan="1" style="border-top: 1px solid #000000"><b><%=buildingAreas.get(i).getDescription()%></b></td>
                                                <td colspan="1" style="border-top: 1px solid #000000"><b><%=buildingAreas.get(i).getSqm()%></b></td>
                                                <td colspan="1" style="border-top: 1px solid #000000">
                                                    <form class="form-view-building" id="createIssue" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="addIssueButton" />
                                                        <input type="hidden" name="originType" value="area" />
                                                        <input type="hidden" name="areaId" value="<%=buildingAreas.get(i).getArea_id()%>" />
                                                        <input type="hidden" name="roomId" value="0" />
                                                        <input class="btn btn-primary" type="submit" value="Registrer problem" />
                                                    </form>
                                                </td>
                                                <td colspan="1" style="border-top: 1px solid #000000">
                                                    <form class="form-view-building" id="viewBuilding" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="deleteAreaButton" />
                                                        <input type="hidden" name="areaId" value="<%=buildingAreas.get(i).getArea_id()%>" />
                                                        <input class="btn btn-danger" type="submit" value="Slet område" />
                                                    </form>
                                                </td>
                                                <td colspan="1" style="border-top: 1px solid #000000">
                                                    <form class="form-view-building" id="viewBuilding" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="createRoomButton" />
                                                        <input type="hidden" name="areaId" value="<%=buildingAreas.get(i).getArea_id()%>" />
                                                        <input class="btn btn-primary" type="submit" value="Nyt lokale" />
                                                    </form>
                                                </td>
                                            </tr>
                                            <% for (int j = 0; j < buildingHealthchecks.size(); j++) {//Loop through list of building healthchecks
                                                    //If healthcheck matches that saved in the Session (= the newest/currently open one)
                                                    if (buildingHealthchecks.get(j).getHealthcheck_id() == (Integer) request.getSession().getAttribute("healthcheckId")) {
                                                        //For each issue pertaining this healthcheck
                                                        for (int k = 0; k < healthcheckIssues.size(); k++) {
                                                            //If the issue is for this area, print table row with issue details
                                                            if ((healthcheckIssues.get(k).getArea_id() == buildingAreas.get(i).getArea_id()) && (healthcheckIssues.get(k).getRoom_id() == 0)) {%>
                                            <tr>
                                                <td colspan="5"><b>Problem</b></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2">Beskrivelse</td>
                                                <td colspan="3">Anbefalet behandling</td>
                                            </tr>
                                            <tr>
                                                <td colspan="2">
                                                    <%= healthcheckIssues.get(k).getDescription()%>
                                                </td>
                                                <td>
                                                    <%= healthcheckIssues.get(k).getRecommendation()%>
                                                </td>
                                                <td>
                                                    <img src="./GetImage?type=issue&id=<%=healthcheckIssues.get(k).getIssue_id()%>" class="img-fluid " width="300" alt="Responsive image">
                                                </td>
                                                <td></td>
                                                <td colspan="2">
                                                    <form class="form-view-building" id="viewBuilding" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="deleteIssueButton" />
                                                        <input type="hidden" name="issueId" value="<%=healthcheckIssues.get(k).getIssue_id()%>" />
                                                        <input class="btn btn-danger" type="submit" value="Slet problem" />
                                                    </form>
                                                </td>
                                            </tr>
                                            <%}
                                                        }

                                                    }
                                                }%>

                                            <% for (int l = 0; l < buildingRooms.size(); l++) {
                                                    if (buildingAreas.get(i).getArea_id() == buildingRooms.get(l).getArea_id()) {%>
                                            <tr>
                                                <td colspan="2">&nbsp;<%=buildingRooms.get(l).getName()%></td>
                                                <td><%=buildingRooms.get(l).getDescription()%></td>
                                                <td><%=buildingRooms.get(l).getSqm()%></td>
                                                <td>
                                                    <form class="form-view-building" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="addIssueButton" />
                                                        <input type="hidden" name="originType" value="room" />
                                                        <input type="hidden" name="areaId" value="<%=buildingAreas.get(i).getArea_id()%>" />
                                                        <input type="hidden" name="roomId" value="<%=buildingRooms.get(l).getRoom_id()%>" />
                                                        <input class="btn btn-primary" type="submit" value="Registrer problem" />
                                                    </form>
                                                    <form action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="registerMoistButton" />
                                                        <input type="hidden" name="roomId" value="<%=buildingRooms.get(l).getRoom_id()%>" />
                                                        <input class="btn btn-primary" type="submit" value="Registrer fugtmåling" />
                                                    </form>
                                                    <form action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="registerDamageRepairButton" />
                                                        <input type="hidden" name="roomId" value="<%=buildingRooms.get(l).getRoom_id()%>" />
                                                        <input class="btn btn-primary" type="submit" value="Registrer tidl. skade" />
                                                    </form>
                                                </td>
                                                <td colspan="2">
                                                    <form class="form-view-building" id="viewBuilding" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="deleteRoomButton" />
                                                        <input type="hidden" name="roomId" value="<%=buildingRooms.get(l).getRoom_id()%>" />
                                                        <input class="btn btn-danger" type="submit" value="Slet lokale" />
                                                    </form>
                                                </td>
                                            </tr>

                                            <% for (int m = 0; m < buildingHealthchecks.size(); m++) {//Loop through list of building healthchecks
                                                    //If healthcheck matches that saved in the Session (= the newest/currently open one)
                                                    if (buildingHealthchecks.get(m).getHealthcheck_id() == (Integer) request.getSession().getAttribute("healthcheckId")) {
                                                        //For each issue pertaining this healthcheck
                                                        for (int n = 0; n < healthcheckIssues.size(); n++) {
                                                            //If the issue is for this room, print table row with issue details
                                                            if (healthcheckIssues.get(n).getRoom_id() == buildingRooms.get(l).getRoom_id()) {%>
                                            <tr>
                                                <td colspan="7"><b>Problem</b></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2">Beskrivelse</td>
                                                <td colspan="5">Anbefalet behandling</td>
                                            </tr>
                                            <tr>
                                                <td colspan="2">
                                                    <%= healthcheckIssues.get(n).getDescription()%>
                                                </td>
                                                <td>
                                                    <%= healthcheckIssues.get(n).getRecommendation()%>
                                                </td>
                                                <td colspan="2">
                                                    <img src="./GetImage?type=issue&id=<%=healthcheckIssues.get(n).getIssue_id()%>" class="img-fluid " width="300" alt="Responsive image">
                                                </td>
                                                <td colspan="2">
                                                    <form class="form-view-building" id="viewBuilding" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="deleteIssueButton" />
                                                        <input type="hidden" name="issueId" value="<%=healthcheckIssues.get(n).getIssue_id()%>" />
                                                        <input class="btn btn-danger" type="submit" value="Slet problem" />
                                                    </form>
                                                </td>
                                            </tr>
                                            <%}
                                                        }
                                                    }
                                                }

                                                for (int o = 0; o < allMoistureMeasurements.size(); o++) {
                                                    if (allMoistureMeasurements.get(o).getRoomId() == buildingRooms.get(l).getRoom_id()) {%>
                                            <tr>
                                                <td colspan="7"><b>Fugtmåling</b></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2">Målepunkt</td>
                                                <td colspan="5">Måleværdi</td>
                                            </tr>
                                            <tr>
                                                <td colspan="2">
                                                    <%= allMoistureMeasurements.get(o).getMeasurePoint()%>
                                                </td>
                                                <td colspan="3">
                                                    <%= allMoistureMeasurements.get(o).getMoistureValue()%>
                                                </td>
                                                <td colspan="2">
                                                    <form id="viewBuilding" action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="deleteMoist" />
                                                        <input type="hidden" name="moistId" value="<%=allMoistureMeasurements.get(o).getMoisture_info_id()%>" />
                                                        <input class="btn btn-danger" type="submit" value="Slet fugtmåling" />
                                                    </form>
                                                </td>
                                            </tr>
                                            <%}
                                                }

                                                for (int p = 0; p < allDamageRepairs.size(); p++) {
                                                    if (allDamageRepairs.get(p).getRoomId() == buildingRooms.get(l).getRoom_id()) {%>
                                            <tr>
                                                <td colspan="7"><b>Tidl. skade</b></td>
                                            </tr>
                                            <tr>
                                                <td>Skadesdato</td>
                                                <td>Sted</td>
                                                <td>Beskrivelse</td>
                                                <td>Arbejde udført</td>
                                                <td>Type</td>
                                                <td colspan="2">Muligheder</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <%= allDamageRepairs.get(p).getDate_occurred()%>
                                                </td>
                                                <td>
                                                    <%= allDamageRepairs.get(p).getLocation()%>
                                                </td>
                                                <td>
                                                    <%= allDamageRepairs.get(p).getDetails()%>
                                                </td>
                                                <td>
                                                    <%= allDamageRepairs.get(p).getWork_done()%>
                                                </td>
                                                <td>
                                                    <%= allDamageRepairs.get(p).getType()%>
                                                </td>
                                                <td colspan="2">
                                                    <form action="TechnicianServlet" method="POST">
                                                        <input type="hidden" name="origin" value="viewBuilding" />
                                                        <input type="hidden" name="originSection" value="deleteDamageRepairButton" />
                                                        <input type="hidden" name="roomId" value="<%=buildingRooms.get(l).getRoom_id()%>" />
                                                        <input class="btn btn-danger" type="submit" value="Slet tidl. skade" />
                                                    </form>
                                                </td>
                                            </tr>
                                            <%}
                                                            }
                                                        }
                                                    }
                                                }%>
                                        </tbody>
                                    </table>
                                    <br><br>    
                                    <center>
                                        <form class="form-view-building" id="viewBuilding" action="TechnicianServlet" method="POST">
                                            <input type="hidden" name="origin" value="viewBuilding" />
                                            <input type="hidden" name="originSection" value="completeHealthcheckButton" />
                                            <input style="height: 100px" class="btn btn-success" type="submit" value="Afslut sundhedsrapport" />
                                        </form>
                                    </center>
                                    <br><br>
                                    <h4>Dokumenter</h4>
                                    <p>Tilføj relevante dokumenter til din bygning så Polygon har et bedre overblik, dette kunne være en planoversigt.</p>
                                    <table text-align="left" class="table">
                                        <tbody>
                                            <tr bgcolor='#c0dee8'>
                                                <th colspan="1"><b>Dokumentnavn</b></th>
                                                <th colspan="1"><b>Filtype</b></th>
                                                <th colspan="1"><b>Oprettet den</b></th>
                                                <th colspan="3"><b>Muligheder</b></th>
                                            </tr>
                                            <tr>
                                                <% if (buildingDocuments.size() == 0) {%>
                                            <tr>
                                                <td colspan="5">Ingen dokumenter uploaded.</td>
                                            </tr>
                                            <%}%> 
                                            <% for (int i = 0; i < buildingDocuments.size(); i++) {

                                            %>
                                            <tr>
                                                <td colspan="1" style="border-top: 1px solid #000000"><%=buildingDocuments.get(i).getDocument_name()%></td>
                                                <td colspan="1" style="border-top: 1px solid #000000"><%=buildingDocuments.get(i).getDocument_type()%></td>
                                                <td colspan="1" style="border-top: 1px solid #000000"><%=buildingDocuments.get(i).getDate_created()%></td>
                                                <td colspan="1" style="border-top: 1px solid #000000">
                                                    <a href="./GetDocument?&id=<%=buildingDocuments.get(i).getDocument_id()%>" class="btn btn-default">Download</a>
                                                </td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                    </table>  
                                </div>
                                <%break;

                                    }
                                %>


                            </div>
                        </div>
                        </body>
                        </html>
