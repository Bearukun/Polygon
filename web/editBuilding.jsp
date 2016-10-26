<%-- 
    Document   : editBuilding
    Created on : 26-Oct-2016, 11:26:45
    Author     : Martin
--%>

<%@page import="serviceLayer.enties.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="serviceLayer.enties.Building"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sunde Bygninger - Rediger bygning</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!--Adding our own css-->
        <!--<link href="css/index.css" rel="stylesheet" type="text/css"/>-->
    </head>
    <body>
        
        <!--Top navigation bar:-->
        <nav class="navbar navbar-default container-fluid">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a><span class="glyphicon glyphicon-user"></span> <%= (User)session.getAttribute("user") %></a></li>
                <li><a href="${pageContext.request.contextPath}/Logout"><span class="glyphicon glyphicon-log-out"></span>Log ud</a></li>
            </ul>
        </nav>
        
        <div id="container" class="container-fluid">
        
            <h1>Rediger Bygning</h1>
    
            <% Building build = new Building(); %>

            <% 
                ArrayList<Building> tempAL = new ArrayList();
                tempAL = (ArrayList<Building>) request.getSession().getAttribute("tempAL");

                for (int i = 0; i < tempAL.size(); i++) {
                    if(tempAL.get(i).getBuilding_id()==Integer.parseInt(request.getParameter("value"))){
                        build = tempAL.get(i);    
                    }
                }
            %>
        </div>
        <div class="container">
            <form class="form-edit-building" action="Front" method="POST">
                <p>Adresse</p>
                <input type="text" name="address" value="<%=build.getAddress()%>" />
                <br><br>
                <p>Postnr.</p>
                <input type="text" name="postcode" value="<%=build.getPostcode()%>" />
                <br><br>
                <p>By</p>
                <input type="text" name="city" value="<%=build.getCity()%>" />
                <input type="hidden" name="origin" value="editBuilding" />
                <br><br>
                <input class="btn btn-primary" type="submit" value="Gem ændringer" name="" />
            </form>
        </div>
        
        
    
    </body>
</html>
