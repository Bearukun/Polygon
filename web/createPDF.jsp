
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
        <form class="form-edit-profile" action="UserServlet" method="POST"> 
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
        <form class="form-edit-profile" action="LoginServlet" method="POST"> 
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

            <!--Manually set the img folder-->
            <p>Billeder folder</p>                      
            <p>Mac: /Users/Ceo/NetBeansProjects/Polygon/web/img/ </p>    
            <p>Windows: E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\ </p>
            <input type="text" name="folderPath" value="/Users/Ceo/NetBeansProjects/Polygon/web/img/" />
            <br><br>

            <!--Manually set the save folder-->
            <p>Save Folder</p>                      
            <p>Mac: /Users/Ceo/NetBeansProjects/Polygon/pdf/ </p>    
            <p>Windows: E:\\Dokumenter\\NetBeansProjects\\Polygon\\pdf\\ </p>
            <input type="text" name="savePath" value="/Users/Ceo/NetBeansProjects/Polygon/pdf/" />
            <br><br>

            <input type="hidden" name="origin" value="pdfwithtext" />
            <br><br>
            <input class="btn btn-primary" type="submit" value="Generate PDF with Text" name="pdfwithtext" />

            <h1>NYE FELTER </h1>


            <p>Tag-bemærkninger ?</p>
            <div><div class="radio">
                    <label><input type="radio" value="yes" name="optradio">Ja</label>
                </div>
                <div class="radio">
                    <label><input type="radio" value="no" name="optradio">Nej</label>
                </div></div>
            <!-- If "Yes", the display the following text area!
            and mark the "notes" check box with X and the "noNotes" empty-->
            <!-- If "No", then DON'T display the text area, 
            and mark the "notes" check box empty and the "noNotes" with X!-->
            <div class="form-group">
                <label for="comment">[HVIS JA!] Tag-bemærkninger: </label><br>
                <textarea class="form-control" rows="5" maxlength="10" id="comment"></textarea>
            </div>

            <p>Tag-billede ?</p>
            <div><div class="radio">
                    <label><input type="radio" value="yes" name="optradio2">Ja</label>
                </div>
                <div class="radio">
                    <label><input type="radio" value="no" name="optradio2">Nej</label>
                </div></div>
            <!-- If "Yes", the display the following text box for link to the picture/file!
                and mark the "picture" check box with X-->
            <!-- If "No", then DON'T display the text box, 
                and mark the "picture" check box empty !-->
            <p>[HVIS JA!]</p>
            <input type="text" name="roofPicturePath" value="" />
            <br><br>



            <p>Ydervægge-bemærkninger? </p>
            <div class="radio">
                <label><input type="radio" name="optradio3">Ja</label>
            </div>
            <div class="radio">
                <label><input type="radio" name="optradio3">Nej</label>
            </div>
            <div class="container">
                <!-- If "Yes", the display the following text area!
                and mark the "notes" check box with X and the "noNotes" empty-->
                <!-- If "No", then DON'T display the text area, 
                and mark the "notes" check box empty and the "noNotes" with X!-->
                <div class="form-group">
                    <label for="comment">[HVIS JA!] Ydervægge-bemærkninger: </label><br>
                    <textarea class="form-control" rows="5" maxlength="10" id="comment"></textarea>
                </div>


                <div class="row">

                    <p>Ydervægge-billede? </p>
                    <div class="radio">
                        <label><input type="radio" name="optradio4">Ja</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="optradio4">Nej</label>
                    </div>
                    <div class="container">
                        <div class="row">
                            <!-- If "Yes", the display the following text box for link to the picture/file!
               and mark the "picture" check box with X-->
                            <!-- If "No", then DON'T display the text box, 
                                and mark the "picture" check box empty !-->
                            <p>[HVIS JA!]</p>
                            <input type="text" name="roofPicturePath" value="" />
                            <br><br>








                            </form>
                            </body>
                            </html>
