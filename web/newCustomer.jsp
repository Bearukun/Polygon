<%-- 
    Document   : newuser
    Created on : 24-10-2016, 15:06:29
    Author     : Ceo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Create new Customer User</h1>
        <form class="form-signin" action="Front" method="POST">
            <h1>Nyt brugernavn/email</h1>
            <input class="form-control" type="text" name="email" value="" placeholder="Email" />
            <h1>Nyt adgangskode</h1>
            <input class="form-control" type="password" name="password" value="" placeholder="Kodeord" />
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Tilbage" name="" />
        <input type="hidden" name="origin" value="logout" />
        </form>
    </body>
</html>
