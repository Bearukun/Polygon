
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Generate PDF</h1>
        
        <form class="form-edit-profile" action="Front" method="POST"> 
                        <p>PDFName</p>                      
                        <input type="text" name="pdfname" value="" />
                        <br><br>
                        <input type="hidden" name="origin" value="blankTestPDF" />
                        <br><br>
                        <input class="btn btn-primary" type="submit" value="Try" name="blankTestPDF" />
        </form>
    </body>
</html>
