<%@page import="serviceLayer.entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="favicon.ico">
        <script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
        <title>Sunde Bygninger - Tilføj bygning</title>
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
                        <% request.getSession().setAttribute("ActiveSidebarMenu", "TilføjBygning"); %>
                        <% if (request.getSession().getAttribute("source").toString().equals("user")) {%>
                        <%@ include file="userSidebar.jsp"%>
                        <%} else if (request.getSession().getAttribute("source").toString().equals("technician")) {%>
                        <%@ include file="technicianSidebar.jsp"%>
                        <%} else if (request.getSession().getAttribute("source").toString().equals("admin")) {%>
                        <%@ include file="adminSidebar.jsp"%>
                        <%}%>
                        <!-- END MENU -->
                    </div>
                </div>
                <!-- SITE CONTENT -->
                <div class="col-sm-10">
                        <h2>Tilføj bygning</h2>
                        <div>
                                <div class="col-xs-6">

                                    <form class="form-add-building" action="NavigatorServlet" method="POST">
                                        <h4>Bygningens navn</h4>
                                        <p>Her skal navnet på bygningen skrives</p>
                                        <input class="form-control" type="text" name="name" value="" placeholder="Navn" maxlength="20"/>
                                        <h4>Bygningens adresse</h4>
                                        <p>Bygnigens vejnavn og nummer</p>
                                        <input class="form-control" type="text" name="address" value="" placeholder="Adresse" />
                                        <h4>Bygningens postnummer</h4>
                                        <p>Her skal kun postnummeret skrives</p>
                                        <input class="form-control" type="number" min="1000" max="9999" name="postcode" value="" placeholder="Postnummer" />
                                        <h4>Bygnignens tilsvarende by</h4>
                                        <p>Her skal der stå den by postnummeret svarer til</p>
                                        <input class="form-control" type="text" name="city" value="" placeholder="By" />
                                        <br>
                                    </form>
                                </div>
                                <div class="col-xs-6">
                                    <form class="form-add-building" action="NavigatorServlet" method="POST">
                                        <h4>Bygningens opførelsesår</h4>
                                        <p>Det år bygningen blev bygget</p>
                                        <input class="form-control" type="number" min="0" max="2500" name="construction_year" value="" placeholder="Byggeår:" />
                                        <h4>Bygningens formål</h4>
                                        <p>Hvad bliver bygningen brugt til</p>
                                        <input class="form-control" type="text" name="purpose" value="" placeholder="Formål" />
                                        <h4>Bygningens areal</h4>
                                        <p>Hvad er bygningens totale areal</p>
                                        <input class="form-control" type="number" name="sqm" value="" placeholder="Areal" />
                                        <input type="hidden" name="origin" value="createBuilding" />
                                        <input type="hidden" name="originSection" value="<%= session.getAttribute("type")%>" />
                                    </form>
                                </div>
                            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Opret bygning" name=""/>
                        </div>
                </div>
            </div>
    </body>
</html>
