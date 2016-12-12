<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="/img/favicon.ico">
        <script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
        <title>Sunde Bygninger - Fejlside</title>
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
        <div class="login_container">
            <form class="form-signin" action="LoginServlet" method="POST">
                <img src="./img/logo.png" class="img-responsive center-block" alt="Responsive image">
                <center>
                    <h1>Ups...<br>Det er sket en fejl!</h1>
                </center>
                <% String exception_msg = (String) request.getSession().getAttribute("ExceptionError");
                    if (exception_msg != null) {
                        out.println("<div class=\"alert alert-danger\"><center>" + exception_msg + "</center></div>");
                    }%>
                <input type="hidden" name="origin" value="logout" />
                <input class="btn btn-lg btn-primary btn-block" type="submit" value="Gå til forside" name="" />
            </form>
        </div>
    </body>
</html>
