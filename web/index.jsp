<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%--TO DO
We need to check if a user is logged in,
if yes - then redirect - else nothing. --%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sunde Bygninger - Login</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!--Adding our own css-->
        <link href="css/index.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <!-- Login-button og styring -->
        <div class="container">
            <form class="form-signin" action="Front" method="POST">
                <img src="./img/logo.png" class="img-responsive center-block" alt="Responsive image">
                <input class="form-control" type="text" name="email" value="" placeholder="Email" />
                <input class="form-control" type="password" name="password" value="" placeholder="Kodeord" />
                <input type="hidden" name="origin" value="login" />
                <input class="btn btn-lg btn-primary btn-block" type="submit" value="Log ind" name="" />
                <a href="newCustomer.jsp">Opret bruger.</a>
                
            </form>
        </div>
    </body>
</html>
