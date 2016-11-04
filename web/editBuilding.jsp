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
        <title>Sunde Bygninger - Rediger bygning</title>
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
                                    <a href="editBuilding?noID" target="_self">
                                        <i class="glyphicon glyphicon-wrench"></i>
                                        Rediger bygning </a>
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
                    <% Building build = new Building(); %>
                    <%
                        ArrayList<Building> tempAL = new ArrayList();
                        tempAL = (ArrayList<Building>) request.getSession().getAttribute("tempAL");

                        for (int i = 0; i < tempAL.size(); i++) {
                            if (tempAL.get(i).getBuilding_id() == Integer.parseInt(request.getParameter("value"))) {
                                build = tempAL.get(i);
                            }
                        }
                    %>
                    <h1>Rediger bygning:</h1>
                    <form class="form-edit-building" id="editBuilding" action="Front" method="POST">
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
                        <input type="hidden" name="origin" value="editBuilding" />
                        <input class="btn btn-primary" type="submit" value="Gem ændringer" name="editBuilding"/>
                    </form>
                    <% //request.getSession().setAttribute("LoggingError", message);%>    
<!--                        <form class="form-signin" action="Front" method="POST">
                            <input type="hidden" name="origin" value="javascript:amendDetails();" />
                        <br><br>
                        <input class="btn btn-primary" type="submit" value="Gem !!! TEST" name="" />

                        </form>-->
                        
                        
                </div>
            </div>
        </div>
    </body>
</html>
