
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Generate PDF - Technician</h1>

         <h3>Generate blank PDF</h3>
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
         <h3>Generate PDF with information</h3>
        <form class="form-edit-profile" action="Front" method="POST"> 
            <p>PDFName</p>                      
            <input type="text" name="pdfname" value="" />
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
            <input type="number" name="buildingsqm" value="" />
            <br><br>                  
            
            <p>Bygnings Formål</p>                      
            <input type="text" name="buildingpurpose" value="" />
            <br><br>
            
            <p>Bygnings ansvarlig</p>                      
            <input type="text" name="buildingsowner" value="" />
            <br><br>
            
             <p>Billeder folder</p>                      
             <p>Mac: /Users/Ceo/NetBeansProjects/Polygon/web/img/ </p>    
             <p>Windows: E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\ </p>
             <input type="text" name="folderPath" value="/Users/Ceo/NetBeansProjects/Polygon/web/img/" />
            <br><br>
            
            <p>Save Folder</p>                      
             <p>Mac: /Users/Ceo/NetBeansProjects/Polygon/pdf/ </p>    
             <p>Windows: E:\\Dokumenter\\NetBeansProjects\\Polygon\\pdf\\ </p>
             <input type="text" name="savePath" value="/Users/Ceo/NetBeansProjects/Polygon/pdf/" />
            <br><br>

            <input type="hidden" name="origin" value="pdfwithtext" />
            <br><br>
            <input class="btn btn-primary" type="submit" value="Generate PDF with Text" name="pdfwithtext" />
        </form>
    </body>
</html>
