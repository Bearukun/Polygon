<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="/img/favicon.ico">
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
        <style>
            body{
                font-family: Verdana;
            }
            .bluebox{
                border: 2px solid blue;
                padding: 5px;
                margin-left: 10px;
                width: 300px;
                height: 525px;
                float: left;
            }
            textarea {
                width: 290px;
                height: 400px;
            }
            input{
                width: 290px;
                margin-bottom: 10px;
            }
        </style>
    </head>
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
            <body>
                <h1>Admin Send Email</h1>
                <h1>        
                    Send an email (If you need to check the mails sent to Polygon, go to gmail.com and login polygonmailtest4.
                </h1> 

                <div class="bluebox"><form action="AdminServlet" method="POST">
                        <!--<input type="text" name="from" value="" placeholder="from"/>-->
                        <!--<input type="text" name="to" value="" placeholder="send to"/>-->
                        <input type="text" name="emailHead" value="" placeholder="Emne"/>
                        <textarea type="text" name="emailMessage" value="" placeholder="Besked"></textarea>
                        <input type="submit" value="Send email til alle brugere" name="submit" />
                        <input type="hidden" name="origin" value="sendEmailToAllUsers" />
                    </form></div>
            </body>
            </html>
