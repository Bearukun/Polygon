
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
        <br><br>
        <br><br>
        <br><br>
        <form class="form-edit-profile" action="Front" method="POST"> 
                        <p>PDFName</p>                      
                        <input type="text" name="pdfname2" value="" />
                        <br><br>
                        <p>Bygningens Navn</p>                      
                        <input type="text" name="buildingname" value="" />
                        <br><br>
                         <p>Adresse</p>                      
                        <input type="text" name="buildingadddress" value="" />
                        <br><br>
<p>Postnummer</p>                      
                        <input type="number" name="buildingpostcode" value="" />
                        <br><br>
<p>By</p>                      
                        <input type="text" name="buildingcity" value="" />
                        <br><br>                     
<p>Opførelses år</p>                      
                        <input type="number" name="constructionyear" value="" />
                        <br><br>
 <p>Bygningens Samlede areal [kvadrat meter]</p>                      
                        <input type="text" name="buildingsqm" value="" />
                        <br><br>                  
<p>Bygnings ansvarlig</p>                      
                        <input type="text" name="buildingsowner" value="" />
                        <br><br>

 <input type="hidden" name="origin" value="pdfwithtext" />
                        <br><br>
                        <input class="btn btn-primary" type="submit" value="Generate PDF with Text" name="pdfwithtext" />
        </form>
    </body>
</html>
