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
        <title>Sunde Bygninger - Admin Brugeroversigt</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!--Adding our own css-->
        <link href="css/stylesheet.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#slectboxid option').click(function () {
                    $('#textboxid').val($(this).val());
                });
            });
        </script>
    </head>
    <body>
    </div>
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
                            <li>
                                <a href="adminBuildings.jsp" target="_self">
                                    <i class="glyphicon glyphicon-object-align-bottom"></i>
                                    Vis bygninger </a>
                            </li>
                            <li class="active">
                                <a href="adminUsers.jsp" target="_self">
                                    <i class="glyphicon glyphicon-th-list"></i>
                                    Håndter brugere </a>
                            </li>
                            <li>
                                <a href="adminPendingBuildings.jsp" target="_self">
                                    <i class="glyphicon glyphicon-list"></i>
                                    Healthchecks </a>
                            </li>
                        </ul>
                    </div>
                    <!-- END MENU -->
                </div>
            </div>

            <!-- SITE CONTENT -->

            <div class="col-sm-10">
                <div id="container" class="container-fluid">

                    <h1>Brugere:</h1>
                    <br>

                    <a href="adminCreateUser.jsp" target="_self">
                        <i class="glyphicon glyphicon-plus"></i>
                        Ny bruger </a>

                    <% ArrayList<User> tempUL = new ArrayList();

                        tempUL = (ArrayList<User>) request.getSession().getAttribute("userList");


                    %>

                    <table border="1" text-align="left" class="table table-striped">
                        <tbody>
                            <tr>
                                <td><b>ID</b></td>
                                <td><b>Email</b></td>
                                <td><b>Adgandskode</b></td>
                                <td><b>Navn</b></td>
                                <td><b>Type</b></td>
                                <td><b>Firma</b></td>
                                <td><b>Adresse</b></td>
                                <td><b>Postnr.</b></td>
                                <td><b>By</b></td>
                            </tr>
                            <%                                    for (int i = 0; i < tempUL.size(); i++) {
                            %><tr>
                                <td><%out.println(tempUL.get(i).getUser_id());%></td>  
                                <td><%out.println(tempUL.get(i).getEmail());%></td>
                                <td><%out.println(tempUL.get(i).getPassword());%></td> 
                                <td><%out.println(tempUL.get(i).getName());%></td> 
                                <td><%out.println(tempUL.get(i).getType());%></td>                                    
                                <td><%out.println(tempUL.get(i).getCompany());%></td> 
                                <td><%out.println(tempUL.get(i).getAddress());%></td> 
                                <td><%out.println(tempUL.get(i).getPostcode());%></td> 
                                <td><%out.println(tempUL.get(i).getCity());%></td> 

                            </tr>
                            <%}
                            %>
                        </tbody>
                    </table>






                </div>
            </div>
        </div>
    </div>
</body>
</html>
<input class="form-control" type="text" name="email" placeholder="Email">
<input class="form-control" type="password" name="password" placeholder="Adgangskode">
<input class="form-control" type="password" name="passwordConfirm" placeholder="Bekræft Adgangskode">
<select name="type" id="slectboxid">

    <option value="ADMIN">Admin</option>
    <option value="TECHNICIAN">Teknikker</option>
    <option value="CUSTOMER">Kunde</option>

</select>
<input type="text" name="text" placeholder="Vælg brugertype" id="textboxid" />
<h5>Ydeligere information</h5><br><!-- Skal centreres -->
<input class="form-control" type="text" name="name" value="" placeholder="Navn" />
<input class="form-control" type="text" name="phone" value="" placeholder="Telefon" />
<input class="form-control" type="text" name="company" value="" placeholder="Firma" />
<input class="form-control" type="text" name="address" value="" placeholder="Adresse" />
<input class="form-control" type="text" name="postcode" value="" placeholder="Postnr." />
<input class="form-control" type="text" name="city" value="" placeholder="By" />
<input class="btn btn-lg btn-success btn-block" type="submit" name="login"  value="Registrer">
<input type="hidden" name="origin" value="newCustomer" />