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
        <link rel="shortcut icon" href="favicon.ico">
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
        %>
        <% ArrayList<Room> buildingRooms = new ArrayList();
           //buildingRooms.add(new Room(8, "name", "description", 100, 1, 1));
            buildingRooms = (ArrayList<Room>) request.getSession().getAttribute("buildingRooms");
        %>
        
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
                                    <a href="user.jsp" target="_self">
                                        <i class="glyphicon glyphicon-home"></i>
                                        Overblik </a>
                                </li>
                                <li>
                                    <a href="addBuilding.jsp" target="_self">
                                        <i class="glyphicon glyphicon-plus"></i>
                                        Tilføj bygning </a>
                                </li>
                                <li class="active">
                                    <a href="viewBuilding?noID" target="_self">
                                        <i class="glyphicon glyphicon-wrench"></i>
                                        Vis bygning </a>
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
            <!-- If else statements show different page contents depending on whether a building is 'just' being viewed or whether it is being edited -->    
            <% 
                ArrayList<Building> userBuildings = new ArrayList();
                userBuildings = (ArrayList<Building>) request.getSession().getAttribute("userBuildings");
                Building build = new Building();
            
            
            String source = "";
            if(request.getSession().getAttribute("source")!=null){
                source = request.getSession().getAttribute("source").toString();
            }
            switch (source) {
                
                case "createAreaButton":
                    %>
                    <div class="col-sm-10">
                    <div id="container" class="container-fluid">
                        <h1>Nyt område:</h1>

                        <form class="form-view-building" id="editArea" action="Front" method="POST">
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
                
                //If the building's details are being edited
                case "editBuildingButton":
                %>
                    <div class="col-sm-10">
                    <%
                        //Loop through entire buildings list
                        for (int i = 0; i < userBuildings.size(); i++) {
                            //If the currently selected building has the same building id as the one saved in the Session
                            if (userBuildings.get(i).getBuilding_id() == Integer.parseInt(request.getParameter("value"))) {
                                //Save the building in the reference object build so its details can be shown on page
                                build = userBuildings.get(i);
                                request.getSession().setAttribute("buildingBeingEdited", build);
                            }
                        }
                    %>
                        <h1>Rediger bygning</h1>
                        <form class="form-view-building" id="editBuilding" action="Front" method="POST">
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
                                    if(build.getPurpose().equals(bldgPurpose.get(i))){
                                        %><option selected="<%=bldgPurpose.get(i)%>" value="<%=bldgPurpose.get(i)%>"><%=bldgPurpose.get(i)%></option><%
                                    }
                                    else{
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
                    </div>
                
                
                <%
                    break;
                
                //If none of the building's details are being edited
                default:
                
                %>    
                    <div class="col-sm-10">
                    <%
                        //Loop through entire buildings list
                        for (int i = 0; i < userBuildings.size(); i++) {
                            //If the currently selected building has the same building id as the one saved in the Session
                            if (userBuildings.get(i).getBuilding_id() == Integer.parseInt(request.getParameter("value"))) {
                                //Save the building in the reference object build so its details can be shown on page
                                build = userBuildings.get(i);
                                request.getSession().setAttribute("buildingBeingEdited", build);
                            }
                        }
                    %>
                    <h1>Vis bygning</h1>

                    <table text-align="left" class="table">
                        <tbody>
                            <tr bgcolor='cyan'>
                                <th colspan="3"><b>Staminformationer</b></th>
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
                                    <form class="form-view-building" id="viewBuilding" action="Front" method="POST">
                                        <input type="hidden" name="origin" value="viewBuilding" />
                                        <input type="hidden" name="originSection" value="editBuildingButton" />
                                        <input class="btn btn-primary" type="submit" value="Rediger" />
                                    </form>
                                </td>
                            </tr>
                        </tbody>
                    </table>  
                    <br><br>
                    
                    <form class="form-view-building" id="viewBuilding" action="Front" method="POST">
                        <input type="hidden" name="origin" value="viewBuilding" />
                        <input type="hidden" name="originSection" value="createAreaButton" />
                        <input class="btn btn-primary" type="submit" value="Nyt område" />
                    </form>
                    
                    <% 
                        for (int i = 0; i < buildingAreas.size(); i++) {
                    %>
                            <table text-align="left" class="table">
                                <tbody>
                                    <tr bgcolor='cyan' height="100">
                                        <th colspan="1"><b><%=buildingAreas.get(i).getName()%></b></th>
                                        <th colspan="1"><b><%=buildingAreas.get(i).getDescription()%></b></th>
                                        <th colspan="1"><b><%=buildingAreas.get(i).getSqm()%></b></th>
                                    </tr>
                                    <% 
                                    for (int j = 0; j < buildingRooms.size(); j++) {
                                       if(buildingAreas.get(i).getArea_id()==buildingRooms.get(j).getArea_id()){
                                        %> 
                                            <tr>
                                                <td><%=buildingRooms.get(j).getName()%></td>
                                                <td><%=buildingRooms.get(j).getDescription()%></td>
                                                <td><%=buildingRooms.get(j).getSqm()%></td>
                                            </tr>
                                        <%}
                                    }
                                    %>
                                </tbody>
                            </table>        
                    <br><br>    
                       <%}
                    %>
                    
                    
                    <br><br><br><br><br><br><br><br><br>  
                    
                    <table text-align="left" class="table">
                        <tbody>
                            <tr bgcolor='cyan'>
                                <th colspan="1"><b>Dokumentnavn</b></th>
                                <th colspan="1"><b>Filtype</b></th>
                                <th colspan="1"><b>Oprettet den</b></th>
                                <th colspan="3"><b>Filstørrelse</b></th>
                            </tr>
                            <tr>
                                <td>Dokument 1</td>
                                <td>PDF</td>
                                <td>31-10-2016</td>
                                <td>2MB</td>
                                <td>
                                    <form class="form-view-building" id="viewBuilding" action="#" method="POST">
                                        <input class="btn btn-primary" type="submit" value="Vis" />
                                    </form>
                                </td>
                                <td>
                                    <form class="form-view-building" id="viewBuilding" action="#" method="POST">
                                        <input class="btn btn-primary" type="submit" value="Download" />
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td>Dokument 2</td>
                                <td>DOC</td>
                                <td>03-11-2016</td>
                                <td>0.5MB</td>
                                <td>
                                    <form class="form-view-building" id="viewBuilding" action="#" method="POST">
                                        <input class="btn btn-primary" type="submit" value="Vis" />
                                    </form>
                                </td>
                                <td>
                                    <form class="form-view-building" id="viewBuilding" action="#" method="POST">
                                        <input class="btn btn-primary" type="submit" value="Download" />
                                    </form>
                                </td>
                            </tr>
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
