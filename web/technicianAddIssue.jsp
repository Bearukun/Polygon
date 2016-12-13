<%@page import="serviceLayer.entities.User"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="/img/favicon.ico">
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
                        <% request.getSession().setAttribute("ActiveSidebarMenu", "RegistrerProblem"); %>
                        <%@ include file="technicianSidebar.jsp"%>
                        <!-- END MENU -->
                    </div>
                </div>
                <!-- SITE CONTENT -->
                <div class="col-sm-10">
                    <h1>Registrer problem</h1>


                    <form class="form-edit-profile" action="NavigatorServlet" method="POST"> 
                        <div class="container-fluid">
                            <div class="row" style="height: auto; width: auto; margin-bottom: auto" >
                                <div class="col-sm-4">

                                    <p>Email</p>                      
                                    <input type="text" name="email" value="<%=request.getSession().getAttribute("uEmail")%>" style="height: auto; width: auto; margin-bottom: 1px;" />
                                    <br><br>
                                    <p>Password</p>                      
                                    <input type="password" name="password" value="<%=request.getSession().getAttribute("uPassword")%>" id="password"style="height: auto; width: auto; margin-bottom: 1px; margin-top: 1px;"   />
                                    <br><br>
                                    <p>Bekræft Password</p>                      
                                    <input type="password" name="passwordConfirm" value="" id="passwordConfirm" style="height: auto;width: auto;margin-bottom: 1px; margin-top: 1px;"/>
                                    <br><br>
                                    
                                </div>
                                <div class="col-sm-4">
                                  
                                    <p>Navn</p>                      
                                    <input type="text" name="name" value="<%=request.getSession().getAttribute("uName")%>" style="height: auto; width: auto;"/>

                                    <br><br>
                                    <p>Firma</p> 
                                    <input type="text" name="company" value="<%=request.getSession().getAttribute("uCompany")%>" style="height: auto; width: auto;"/>
                                    <br><br>
                                    <p>Telefon Nummer</p>                      
                                    <input type="number" name="phonenumber" value="<%=request.getSession().getAttribute("uPhone")%>" style="height: auto; width: auto;"/>
                                    <br><br>  
                                    <br><br>
                                    
                                    <input class="btn btn-primary" type="submit" value="Gem Ændringer" />
                                </div>
                                <div class="col-sm-4">
                                    
                                    <p>Adresse</p> 
                                    <input type="text" name="address" value="<%=request.getSession().getAttribute("uAddress")%>"style="height: auto; width: auto;" />
                                    <br><br>
                                    <p>Postnr.</p> 
                                    <input type="number" name="postcode" value="<%=request.getSession().getAttribute("uPostcode")%>"style="height: auto; width: auto;" />
                                    <br><br>
                                    <p>By</p>  
                                    <input type="text" name="city" value="<%=request.getSession().getAttribute("uCity")%>"style="height: auto; width: auto;" />
                                    <br><br>
                                </div>
                                <div class="col-sm-4">
                                    
                                    
                                </div>
                                <input type="hidden" name="origin" value="editProfile" />
                                <input type="hidden" name="originSection" value="<%= session.getAttribute("type")%>" />
                                <br><br>
                                
                                <br><br>
                                </form>
                            </div>
                        </div>
                                <script src="scripts/passwordChecker.js" type="text/javascript"></script>
                </div>
            </div>
        </div>
    </body>
</html>
