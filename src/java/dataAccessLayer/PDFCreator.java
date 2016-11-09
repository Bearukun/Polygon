package dataAccessLayer;

import java.io.IOException;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import serviceLayer.exceptions.CustomException;

/**
 *
 * @author Ceo
 */
public class PDFCreator {

    public void pdfWithText(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingOwner) {

        PDDocument doc = null;
        PDPage page = null;

        //String test = "Would you please work? I'm asking nicely....";
        try {
            doc = new PDDocument();
            page = new PDPage();

            doc.addPage(page);
            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
            PDFont fontHel = PDType1Font.TIMES_ROMAN; 
            

            //content.moveTextPositionByAmount(tx, ty);
            //tx = Width; (Max 450-500!)
            //ty = Height (max 800!)
            PDPageContentStream content = new PDPageContentStream(doc, page);
            
            content.beginText();
            content.setFont(fontHel, 10);
            content.moveTextPositionByAmount(50, 700);
            content.drawString("Rapport nr.: ");
            content.endText();
            
            
            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(50, 675);
            content.drawString("PDF Navn: " + pdfName);
            content.newLineAtOffset(200,-27 );
            content.drawString("Bygnings Gennemgang");
            content.endText();

            
            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(50, 615);
            content.drawString(""+ buildingName);
            content.newLineAtOffset(0,-10);
            content.drawString("Bygnings navn");
            content.newLineAtOffset(300,0);
            content.drawString("Dato");
            content.endText();
            
                      

            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(50, 550);
            content.drawString("Bygnings Adresse");
            content.newLineAtOffset(0, 10);
            content.drawString("" + buildingAddress);
            content.endText();

            
            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(50, 480);
            content.drawString("Postnr. / By ");
            content.newLineAtOffset(0, 10);
            content.drawString(""+buildingPostcode);
            content.newLineAtOffset(35, 0);
            content.drawString(""+buildingCity);
            content.endText();
            
            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(50, 500);
           
            content.endText();

            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(75, 50);
            
            content.endText();

            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(500, 500);
            content.drawString("500, 500 Bygnings opførelses år: " + buildingContructionYear);
            content.endText();

            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(300, 300);
            content.drawString("250, 250 Bygningens Areal: " + buildingSQM);
            content.endText();

            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(100, 100);
            content.drawString("100, 100 Bygning Ejer: " + buildingOwner);
            content.endText();
            
            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(500, 580);
            content.drawString("Polygon");
            content.newLineAtOffset(-13, -13);
            content.drawString("Rypevang 5");
            content.newLineAtOffset(-3, -13);
            content.drawString("3450 Allerød");
            content.newLineAtOffset(-2, -18);
            content.drawString("Tlf. 4814 0055");
            content.newLineAtOffset(-62, -13);
            content.drawString("sundebygninger@polygon.dk");
            content.endText();
            
           content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(500, 500);
            content.drawString("Billede indsættes her");
            content.endText();

            PDImageXObject image = null;
         image = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\logoJ.jpg", doc);
         content.drawXObject(image,400,690, 150, 65);
             
       
       
      
            
            content.close();
            
            

            //Save directory + file name
            doc.save("E:\\Dokumenter\\NetBeansProjects\\Polygon\\" + pdfName + ".pdf");
            doc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//     public static void main(String[] args) {
//        PDDocument doc = null;
//        try{
//          /* Step 1: Prepare the document.
//           */
//          //test
//         doc = new PDDocument();
//         PDPage page = new PDPage();
//         doc.addPage(page);
//         
//         //Text test
//         
//         PDFont font = PDType1Font.HELVETICA_BOLD;
//         
//         /* Step 2: Prepare the image
//          * PDJpeg is the class you use when dealing with jpg images.
//          * You will need to mention the jpg file and the document to which it is to be added
//          * Note that if you complete these steps after the creating the content stream the PDF
//          * file created will show "Out of memory" error.
//          */
//         
//         PDImageXObject image = null;
//         image = PDImageXObject.createFromFile("tesla.jpg", doc);
//         
//         /* Create a content stream mentioning the document, the page in the dcoument where the content stream is to be added.
//          * Note that this step has to be completed after the above two steps are complete.
//          */
//         PDPageContentStream content = new PDPageContentStream(doc, page);
// 
//       /* Step 3:
//        * Add (draw) the image to the content stream mentioning the position where it should be drawn
//        * and leaving the size of the image as it is
//        */
//       
//       content.beginText();
//       content.setFont( font, 12 );
//       content.moveTextPositionByAmount( 100, 700 );
//           content.drawString("Please work, please work, please work..." + "/t" );
//           content.endText();
//         content.drawImage(image,20,20);
//         content.close();
//       
//         /* Step 4:
//          * Save the document as a pdf file mentioning the name of the file
//          */
//        
//        doc.save("ImageNowPdf.pdf");
//       
//        } catch (Exception e){
//             System.out.println("Exception");
//        }
    public void testBlank(String pdfName) throws CustomException {

        try{
        //initiates a new PDDocument
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();        
              
        doc.save("E:\\Dokumenter\\NetBeansProjects\\Polygon\\" + pdfName + ".pdf");
//            //Closes the PDF creation
            doc.close();
        
        
        
//Creates a new page
 
        doc.addPage(page);

        }catch (Exception e) {
            System.out.println(e);
        }
       
        
//        try {
//            
//                // Adobe Acrobat uses Helvetica as a default font and 
//        // stores that under the name '/Helv' in the resources dictionary
//        PDFont font = PDType1Font.HELVETICA;
//        PDResources resources = new PDResources();
//        resources.put(COSName.getPDFName("Helv"), font);
//        
//        // Add a new AcroForm and add that to the document
//        PDAcroForm acroForm = new PDAcroForm(doc);
//        doc.getDocumentCatalog().setAcroForm(acroForm);
//        
//        // Add and set the resources and default appearance at the form level
//        acroForm.setDefaultResources(resources);
//        
//        // Acrobat sets the font size on the form level to be
//        // auto sized as default. This is done by setting the font size to '0'
//        String defaultAppearanceString = "/Helv 0 Tf 0 g";
//        acroForm.setDefaultAppearance(defaultAppearanceString);
//        
//        // Add a form field to the form.
//        PDTextField textBox = new PDTextField(acroForm);
//        textBox.setPartialName("SampleField");
//        // Acrobat sets the font size to 12 as default
//        // This is done by setting the font size to '12' on the
//        // field level. 
//        // The text color is set to blue in this example.
//        // To use black, replace "0 0 1 rg" with "0 0 0 rg" or "0 g".
//        defaultAppearanceString = "/Helv 12 Tf 255 0 0 rg";
//        textBox.setDefaultAppearance(defaultAppearanceString);
//        
//        // add the field to the acroform
//        acroForm.getFields().add(textBox);
//        
//        // Specify the annotation associated with the field
//        PDAnnotationWidget widget = textBox.getWidgets().get(0);
//        PDRectangle rect = new PDRectangle(50, 350, 200, 50);
//        widget.setRectangle(rect);
//        widget.setPage(page);
//
//        // set green border and yellow background
//        // if you prefer defaults, just delete this code block
//        PDAppearanceCharacteristicsDictionary fieldAppearance
//                = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
//        fieldAppearance.setBorderColour(new PDColor(new float[]{0,0,0}, PDDeviceRGB.INSTANCE));
//        fieldAppearance.setBackground(new PDColor(new float[]{255,255,255}, PDDeviceRGB.INSTANCE));
//        widget.setAppearanceCharacteristics(fieldAppearance);
//
//        // make sure the annotation is visible on screen and paper
//        widget.setPrinted(true);
//        
//        // Add the annotation to the page
//        page.getAnnotations().add(widget);
//        
//        // set the field value
//        textBox.setValue("Sample field");
//        //textBox.drawLine(10, 10, 10, 10);
//
//                    
//            //Saves The .pdf at The designated path, with the custom file name.
//            doc.save("E:\\Dokumenter\\NetBeansProjects\\Polygon\\" + pdfName + ".pdf");
//            //Closes the PDF creation
//            doc.close();
//        } catch (Exception io) {
//            System.out.println(io);
//        }
//    }

}
}
