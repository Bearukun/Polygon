<%-- 
    Document   : user
    Created on : Oct 24, 2016, 12:37:29 AM
    Author     : Bear
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>User page~!</h1>
        Welcome!
        <form class="form-signin" action="Front" method="POST">
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Log ud" name="" />
        <input type="hidden" name="origin" value="logout" />
        </form>
    </body>
</html>
