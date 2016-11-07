
<%@page import="serviceLayer.entities.User"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="favicon.ico">
        <script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
        <title>Sunde Bygninger - Profil</title>
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

                                <li class="active">
                                    <a href="viewBuilding.jsp" target="self">
                                        <i class="glyphicon glyphicon-user"></i>
                                        Bruger indstillinger
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <!-- END MENU -->
                    </div>
                </div>
                <!-- SITE CONTENT -->
                <div class="col-sm-10">
                    <h1>Rediger Profil</h1>


                    <form class="form-edit-profile" action="Front" method="POST"> 
                        <p>Email</p>                      
                        <input type="text" name="email" value="<%=request.getSession().getAttribute("uEmail")%>" />
                        <br><br>
                        <p>Password</p>                      
                        <input type="text" name="password" value="<%=request.getSession().getAttribute("uPassword")%>" />
                        <br><br>
                        <p>Navn</p>                      
                        <input type="text" name="name" value="<%=request.getSession().getAttribute("uName")%>" />
                        <br><br>
                        <p>Telefon Nummer</p>                      
                        <input type="number" name="phonenumber" value="<%=request.getSession().getAttribute("uPhone")%>" />
                        <br><br>                       
                        <p>Firma</p> 
                        <input type="text" name="company" value="<%=request.getSession().getAttribute("uCompany")%>" />
                        <br><br>
                        <p>Adresse</p> 
                        <input type="text" name="address" value="<%=request.getSession().getAttribute("uAddress")%>" />
                        <br><br>
                        <p>Postnr.</p> 
                        <input type="number" name="postcode" value="<%=request.getSession().getAttribute("uPostcode")%>" />
                        <br><br>
                        <p>By</p>  
                        <input type="text" name="city" value="<%=request.getSession().getAttribute("uCity")%>" />
                        <br><br>

                        <!--<input type="hidden" name="selectedUser" value="<%=request.getSession().getAttribute("uUser_id")%>"-->
                        <input type="hidden" name="origin" value="editProfile" />
                        <br><br>
                        <input class="btn btn-primary" type="submit" value="KNAP NAVN HER [Gem Ændringer]" name="editProfile" />
                    </form>
                  


                </div>
            </div>
        </div>
    </body>
</html>