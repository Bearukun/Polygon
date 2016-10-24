<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Opret konto</title>
    </head>
    <body>
        <h1>Opret konto hos Sunde Bygninger</h1>
        <form class="form-signin" action="Front" method="POST">
            <h1>Email</h1>
            <input class="form-control" type="text" name="email" value="" placeholder="Email" />
            <h1>Adgangskode</h1>
            <input class="form-control" type="password" name="password" value="" placeholder="Kodeord" />
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Tilbage" name="" />
        <input type="hidden" name="origin" value="logout" />
        </form>
    </body>
</html>
