package dataAccessLayer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));

        int pageNumber = 0;
        PDDocument doc = null;
        PDPage page1 = new PDPage();
        PDPage page2 = new PDPage();
        PDPage page3 = new PDPage();

        //String test = "Would you please work? I'm asking nicely....";
        try {
            doc = new PDDocument();

            doc.addPage(page1);
            doc.addPage(page2);
            doc.addPage(page3);

            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
            PDFont fontHel = PDType1Font.TIMES_ROMAN;

            //content.moveTextPositionByAmount(tx, ty);
            //tx = Width; (Max 450-500!)
            //ty = Height (max 800!)
            PDPageContentStream content = new PDPageContentStream(doc, page1);

            content.beginText();
            content.setFont(fontHel, 10);
            content.moveTextPositionByAmount(50, 680);
            content.drawString("Rapport nr.: ");
            content.endText();

            content.beginText();
            content.moveTextPositionByAmount(50, 667);
            content.setFont(fontHelB, 8);
            content.drawString("PDF Navn: " + pdfName);
            content.endText();

            content.beginText();
            content.setFont(fontHelB, 12);
            content.moveTextPositionByAmount(245, 647);
            content.drawString("Bygnings Gennemgang");
            content.endText();

            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 610);
            content.drawString("" + buildingName);
            content.newLineAtOffset(0, -12);
            content.drawString("Bygnings navn");
            content.newLineAtOffset(300, 0);
            content.drawString("Dato");
            content.newLineAtOffset(0, 12);
            content.drawString("" + cal.getTime());
            content.endText();
            PDImageXObject bsBName = null;
            bsBName = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\bstring.jpg", doc);
            content.drawXObject(bsBName, 50, 606, 200, 2);
            PDImageXObject bsDate = null;
            bsDate = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\bstring.jpg", doc);
            content.drawXObject(bsDate, 350, 606, 200, 2);

            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 545);
            content.drawString("Bygnings Adresse");
            content.newLineAtOffset(0, 12);
            content.drawString("" + buildingAddress);
            content.endText();

            PDImageXObject bsBAdr = null;
            bsBAdr = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\bstring2.jpg", doc);
            content.drawXObject(bsBAdr, 50, 553, 200, 2);

            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 475);
            content.drawString("Postnr. / By ");
            content.newLineAtOffset(0, 12);
            content.drawString("" + buildingPostcode);
            content.newLineAtOffset(45, 0);
            content.drawString("" + buildingCity);
            content.endText();

            PDImageXObject bsBCity = null;
            bsBCity = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\bstring4.jpg", doc);
            content.drawXObject(bsBCity, 50, 483, 200, 2);

            content.beginText();
            content.setFont(fontHelB, 12);
            content.moveTextPositionByAmount(500, 560);
            content.drawString("Polygon");
            content.newLineAtOffset(-19, -13);
            content.drawString("Rypevang 5");
            content.newLineAtOffset(-6, -13);
            content.drawString("3450 Allerød");
            content.newLineAtOffset(-5, -18);
            content.drawString("Tlf. 4814 0055");
            content.newLineAtOffset(-90, -13);
            content.drawString("sundebygninger@polygon.dk");
            content.endText();

            PDImageXObject sundB = null;
            sundB = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\logoJ.jpg", doc);
            content.drawXObject(sundB, 400, 690, 150, 65);

            PDImageXObject polygonLogo = null;
            polygonLogo = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\polygon.jpg", doc);
            content.drawXObject(polygonLogo, 50, 690, 150, 30);

            PDImageXObject userBygning = null;
            if (picturePath.contains("")) {
                userBygning = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\whouse.jpg", doc);
            } else {
                        userBygning = PDImageXObject.createFromFile("" + picturePath, doc);
            }
            content.drawXObject(userBygning, 125, 225, 375, 215); 

            content.beginText();
            content.setFont(fontHelB, 12);
            content.moveTextPositionByAmount(50, 200);
            content.drawString("Generel information om bygningen:");
            content.endText();

            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 175);
            content.drawString("Bygge år: " + buildingContructionYear);
            content.endText();
            PDImageXObject bsBYear = null;
            bsBYear = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\bstring5.jpg", doc);
            content.drawXObject(bsBYear, 98, 172, 25, 2);

            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 145);
            content.drawString("Bygningens Areal: " + buildingSQM + " Kvadrat Meter");
            content.endText();
            PDImageXObject bsBArea = null;
            bsBArea = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\bstring6.jpg", doc);
            content.drawXObject(bsBArea, 140, 142, 120, 2);

            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 120);
            content.drawString("Hvad bruges bygningen til / Hvad har bygningen været brugt til?" + buildingPurpose);
            content.endText();
            PDImageXObject bsBPurpose = null;
            bsBPurpose = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\bstring6.jpg", doc);
            content.drawXObject(bsBPurpose, 355, 117, 120, 2);

//            content.beginText();
//            content.setFont(fontHelB, 10);
//            content.moveTextPositionByAmount(50, 120);
//            content.drawString("Bygning Ejer: " + buildingOwner);
//            content.endText();
            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(550, 25);
            content.drawString("1");
            content.endText();

            //!! TURNED OFF ATM! -->
            
            
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
//        PDRectangle rect = new PDRectangle(50, 350, 385, 225);
//        widget.setRectangle(rect);
//        widget.setPage(page1);
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
//        page1.getAnnotations().add(widget);
//        
//        // set the field value
//        textBox.setValue("Sample field");
//        
//        //textBox.drawLine(10, 10, 10, 10);
//            
            content.close();

            PDPageContentStream content2 = new PDPageContentStream(doc, page2);

            content2.beginText();
            content2.setFont(fontHelB, 12);
            content2.moveTextPositionByAmount(50, 200);
            content2.drawString("Gennemgang af bygningen udvendig ");
            content2.endText();

            content2.beginText();
            content2.setFont(fontHelB, 8);
            content2.moveTextPositionByAmount(550, 25);
            content2.drawString("2");
            content2.endText();

                        
            content2.close();

            PDPageContentStream content3 = new PDPageContentStream(doc, page3);
            
            
            PDImageXObject tj = null;
            tj = PDImageXObject.createFromFile("E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\tj.jpg", doc);
            content.drawXObject(tj, 355, 117, 25, 25);
            
            // Adobe Acrobat uses Helvetica as a default font and 
        // stores that under the name '/Helv' in the resources dictionary
        PDFont fontP3 = PDType1Font.HELVETICA;
        PDResources resourcesP3 = new PDResources();
        resourcesP3.put(COSName.getPDFName("Helv"), fontP3);
        
        
        // Add a new AcroForm and add that to the document
        PDAcroForm acroFormP3 = new PDAcroForm(doc);
        PDAcroForm acro = new PDAcroForm(doc);
        doc.getDocumentCatalog().setAcroForm(acroFormP3);
        
        // Add and set the resources and default appearance at the form level
        acroFormP3.setDefaultResources(resourcesP3);
        
        // Acrobat sets the font size on the form level to be
        // auto sized as default. This is done by setting the font size to '0'
        String defaultAppearanceStringP3 = "/Helv 0 Tf 0 g";
        acroFormP3.setDefaultAppearance(defaultAppearanceStringP3);
        
        // Add a form field to the form.
        PDTextField textBoxP3 = new PDTextField(acroFormP3);
        
        textBoxP3.setPartialName("SampleField");
        // Acrobat sets the font size to 12 as default
        // This is done by setting the font size to '12' on the
        // field level. 
        // The text color is set to blue in this example.
        // To use black, replace "0 0 1 rg" with "0 0 0 rg" or "0 g".
        defaultAppearanceStringP3 = "/Helv 12 Tf 255 0 0 rg";
        textBoxP3.setDefaultAppearance(defaultAppearanceStringP3);
        
        // add the field to the acroform
        acroFormP3.getFields().add(textBoxP3);
        
        // Specify the annotation associated with the field
        PDAnnotationWidget widgetP3 = textBoxP3.getWidgets().get(0);
        PDRectangle rectP3 = new PDRectangle(50, 350, 50, 50);
        widgetP3.setRectangle(rectP3);
        widgetP3.setPage(page3);

        // set green border and yellow background
        // if you prefer defaults, just delete this code block
        PDAppearanceCharacteristicsDictionary fieldAppearanceP3 = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        fieldAppearanceP3.setBorderColour(new PDColor(new float[]{0,0,0}, PDDeviceRGB.INSTANCE));
        fieldAppearanceP3.setBackground(new PDColor(new float[]{255,255,255}, PDDeviceRGB.INSTANCE));
        widgetP3.setAppearanceCharacteristics(fieldAppearanceP3);

        // make sure the annotation is visible on screen and paper
        widgetP3.setPrinted(true);
        
        // Add the annotation to the page
        page3.getAnnotations().add(widgetP3);
        
        // set the field value
        textBoxP3.setValue("" + tj);
        //textBox.drawLine(10, 10, 10, 10);
            
        
          content3.beginText();
            content3.setFont(fontHelB, 8);
            content3.moveTextPositionByAmount(550, 25);
            content3.drawString("3");
            content3.endText();
            
            
            content3.close();

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

        try {
            //initiates a new PDDocument
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();

            doc.save("E:\\Dokumenter\\NetBeansProjects\\Polygon\\" + pdfName + ".pdf");
//            //Closes the PDF creation
            doc.close();

//Creates a new page
            doc.addPage(page);

        } catch (Exception e) {
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
