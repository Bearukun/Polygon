<%@page import="serviceLayer.enties.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sunde Bygninger - Tilføj bygning</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!--Adding our own css-->
    </head>
    <body>

        <!--Top navigation bar:-->
        <nav class="navbar navbar-default container-fluid">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Forside</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a><span class="glyphicon glyphicon-user"></span> <%= (User) session.getAttribute("user")%></a></li>
                <li><a href="${pageContext.request.contextPath}/Logout"><span class="glyphicon glyphicon-log-out"></span>Log ud</a></li>
            </ul>
        </nav>
        <h1>Add building~!</h1>
        <div class="container">
            <form class="form-add-building" action="Front" method="POST">
                <input class="form-control" type="text" name="address" value="" placeholder="Adresse" />
                <input class="form-control" type="text" name="postcode" value="" placeholder="Postnummer" />
                <input class="form-control" type="text" name="city" value="" placeholder="By" />
                <input type="hidden" name="origin" value="createBuilding" />
                <input class="btn btn-lg btn-primary btn-block" type="submit" value="Opret bygning" name="" />
            </form>
        </div>
    </body>
</html>
