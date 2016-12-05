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
        <title>Sunde Bygninger - Admin Portal</title>
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
                        <%@ include file="adminSidebar.jsp" %>
                        <!-- END MENU -->
                    </div>
                </div>

                <!-- SITE CONTENT -->

                <div class="col-sm-10">
                    <div id="container" class="container-fluid">
                        <h1>Overblik:</h1>
                         <a href="adminEmail.jsp"> Opret test Mail </a>
                       
                        <% ArrayList<User> userList = new ArrayList();
                            ArrayList<Building> allBuildings = new ArrayList();

                            userList = (ArrayList<User>) request.getSession().getAttribute("userList");
                            allBuildings = (ArrayList<Building>) request.getSession().getAttribute("allBuildings");
                            
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
                            %>
                        
                        <br><br>
                        <table text-align="left" class="table">
                            <tbody>
                                <tr bgcolor='cyan'>
                                    <th colspan="2"><b>Brugere</b></th>
                                </tr>
                                <tr>
                                    <td><b>Brugertype</b></td>
                                    <td><b>Antal brugere</b></td>
                                </tr>
                                <tr>
                                    <td>Kunde</td>
                                    <td><%=request.getSession().getAttribute("countOfCustomers")%></td>
                                </tr>
                                <tr>
                                    <td>Tekniker</td>
                                    <td><%=request.getSession().getAttribute("countOfTechnicians")%></td>
                                </tr>
                                <tr>
                                    <td>Administrator</td>
                                    <td><%=request.getSession().getAttribute("countOfAdministrators")%></td>
                                </tr>
                                <tr>
                                    <td><b>Total</b></td>
                                    <td><b><%=(Integer) request.getSession().getAttribute("countOfCustomers")+(Integer) request.getSession().getAttribute("countOfAdministrators")+(Integer) request.getSession().getAttribute("countOfAdministrators")%></b></td>
                                </tr>
                            </tbody>
                        </table>
                        
                        <br><br>
                        <table text-align="left" class="table">
                            <tbody>
                                <tr bgcolor='cyan'>
                                    <th colspan="4"><b>Bygninger</b></th>
                                </tr>
                                <tr>
                                    <td><b>Type</b></td>
                                    <td><b>Antal bygninger</b></td>
                                </tr>
                                
                                <% for (int i = 0; i < bldgPurpose.size(); i++) {%>
                                <tr>
                                    <td><%=bldgPurpose.get(i)%></td>
                                    <td><%=request.getSession().getAttribute("countOf"+bldgPurpose.get(i)+"")%></td>
                                </tr>
                                <%}%>
                                <tr>
                                    <td><b>Total</b></td>
                                    <td><b><%=allBuildings.size()%></b></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>