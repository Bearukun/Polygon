<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%--TO DO
We need to check if a user is logged in,
if yes - then redirect - else nothing. --%>
<html>
    <head>
       
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="favicon.ico">
        <script type="text/javascript" src="scripts/jquery-3.1.1.js"></script>
        <title>Sunde Bygninger - Login</title>
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
        <!-- Register user div, as modal window -->
        <div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="registerModal" aria-hidden="true" style="display: none;">
            <div class="modal-dialog">
                <div class="loginmodal-container">
                    <h1>Opret ny bruger</h1><br>
                    <form class="form-signin" name="ValidationForm" action="Front" method="POST">
                        <input class="form-control" type="text" name="email" placeholder="Email">
                        <input class="form-control" type="password" name="password" placeholder="Adgangskode">
                        <input class="form-control" type="password" name="passwordConfirm" placeholder="Bekræft Adgangskode">
                        <h5>Ydeligere information</h5><br><!-- Skal centreres -->
                        <input class="form-control" type="text" name="name" value="" placeholder="Navn" />
                        <input class="form-control" type="text" name="phone" value="" placeholder="Telefon" />
                        <input class="form-control" type="text" name="company" value="" placeholder="Firma" />
                        <input class="form-control" type="text" name="address" value="" placeholder="Adresse" />
                        <input class="form-control" type="text" name="postcode" value="" placeholder="Postnr." />
                        <input class="form-control" type="text" name="city" value="" placeholder="By" />
                        <input class="btn btn-lg btn-success btn-block" type="submit" name="login"  value="Registrer">
                        <input type="hidden" name="origin" value="newCustomer" />
                        
                    </form>
                    

                </div>
            </div>
        </div>
        <!-- Login div -->
        <div class="login_container">
            <form class="form-signin" action="Front" method="POST">
                <img src="./img/logo.png" class="img-responsive center-block" alt="Responsive image">
                <input class="form-control" type="text" name="email" value="" placeholder="Email" />
                <input class="form-control" type="password" name="password" value="" placeholder="Adgangskode" />

                <input type="hidden" name="origin" value="login" />
                <input class="btn btn-lg btn-primary btn-block" type="submit" value="Log ind" name="" />
                <br>
                <div class="span12" style="text-align:center">
                    <a href="#" data-toggle="modal" data-target="#login-modal">Opret ny bruger</a>
                </div>
                <br>
                <br>
                <a href="createPDF.jsp"> Opret test PDF </a>
            </form>
            
        </div>
    </body>
</html>
