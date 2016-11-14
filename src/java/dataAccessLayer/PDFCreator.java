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
 *
 * Clean up: split op i metoder! fjern overskyddende/dubblet billede!
 *
 *
 *
 */
public class PDFCreator {

    //sourceFolder sf = new sourceFolder();
    PDDocument doc = doc = new PDDocument();

    public void createPDF(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath) {

        page1Setup(pdfName, buildingName, buildingAddress, buildingPostcode, buildingCity, buildingContructionYear,
                buildingSQM, buildingPurpose, buildingOwner, picturePath, imgFolderPath, savePath, doc);

        page2Setup(pdfName, buildingName, buildingAddress, buildingPostcode, buildingCity, buildingContructionYear,
                buildingSQM, buildingPurpose, buildingOwner, picturePath, imgFolderPath, savePath, doc);

        page3Setup(pdfName, buildingName, buildingAddress, buildingPostcode, buildingCity, buildingContructionYear,
                buildingSQM, buildingPurpose, buildingOwner, picturePath, imgFolderPath, savePath, doc);

        savePDF(savePath, pdfName, doc);

    }


    public void testBlank(String pdfName) throws CustomException {

        String pdfname2 = "test2312";

        //initiates a new PDDocument
        PDDocument doc = new PDDocument();

        try {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream conten = new PDPageContentStream(doc, page);

            // Adobe Acrobat uses Helvetica as a default font and 
            // stores that under the name '/Helv' in the resources dictionary
            PDFont font = PDType1Font.HELVETICA;
            PDResources resources = new PDResources();
            resources.put(COSName.getPDFName("Helv"), font);

            // Add a new AcroForm and add that to the document
            PDAcroForm acroForm = new PDAcroForm(doc);
            doc.getDocumentCatalog().setAcroForm(acroForm);

            // Add and set the resources and default appearance at the form level
            acroForm.setDefaultResources(resources);

            // Acrobat sets the font size on the form level to be
            // auto sized as default. This is done by setting the font size to '0'
            String defaultAppearanceString = "/Helv 0 Tf 0 g";
            acroForm.setDefaultAppearance(defaultAppearanceString);

            // Add a form field to the form.
            PDTextField textBox = new PDTextField(acroForm);
            textBox.setPartialName("SampleField");
            // Acrobat sets the font size to 12 as default
            // This is done by setting the font size to '12' on the
            // field level. 
            // The text color is set to blue in this example.
            // To use black, replace "0 0 1 rg" with "0 0 0 rg" or "0 g".
            defaultAppearanceString = "/Helv 12 Tf 0 0 1 rg";
            textBox.setDefaultAppearance(defaultAppearanceString);

            // add the field to the acroform
            acroForm.getFields().add(textBox);

            // Specify the annotation associated with the field
            PDAnnotationWidget widget = textBox.getWidgets().get(0);
            PDRectangle rect = new PDRectangle(50, 300, 200, 50);
            widget.setRectangle(rect);
            widget.setPage(page);

            // set green border and yellow background
            // if you prefer defaults, just delete this code block
            PDAppearanceCharacteristicsDictionary fieldAppearance
                    = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
            fieldAppearance.setBorderColour(new PDColor(new float[]{255, 0, 0}, PDDeviceRGB.INSTANCE));
            fieldAppearance.setBackground(new PDColor(new float[]{1, 1, 100}, PDDeviceRGB.INSTANCE));
            widget.setAppearanceCharacteristics(fieldAppearance);

            // make sure the annotation is visible on screen and paper
            widget.setPrinted(true);

            // Add the annotation to the page
            page.getAnnotations().add(widget);

            // set the field value
            textBox.setValue("Sample field");

            conten.beginText();
            conten.setFont(font, 12);
            conten.moveTextPositionByAmount(100, 700);
            conten.drawString("test!");
            conten.endText();

            conten.close();

            //WINDOWS CEO: 
            //doc.save("E:\\Dokumenter\\NetBeansProjects\\Polygon\\" + pdfName + ".pdf");
            //MAC CEO 
            //"/Users/Ceo/NetBeansProjects/Polygon/" +
            doc.save("/Users/Ceo/NetBeansProjects/Polygon/pdf/" + pdfName + ".pdf");
//            //Closes the PDF creation
            doc.close();

        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void page1Setup(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        System.out.println("Entered TestMethod");

        //Registers the time and date for the PDF document
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        //Initiates new PDPAge
        PDPage page1 = new PDPage();

        //Adds the page to the .doc
        doc.addPage(page1);

        try {

            //Adds pages to the PDF document
            //doc.addPage(page1);
//            doc.addPage(page2);
//            doc.addPage(page3);
            //Sets text fonts 
            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
            PDFont fontHel = PDType1Font.TIMES_ROMAN;

            //Reminder of how to move.
            //content.moveTextPositionByAmount(tx, ty);
            //tx = Width; (Max 450-500!)
            //ty = Height (max 800!)
            //Opens ContentStream for writting to the PDF document
            PDPageContentStream content = new PDPageContentStream(doc, page1);

            //Writes and places the Rapport Nr.
            content.beginText();
            content.setFont(fontHel, 10);
            content.moveTextPositionByAmount(50, 680);
            content.drawString("Rapport nr.: ");
            content.endText();

            //Writes and places the PDF Name
            content.beginText();
            content.moveTextPositionByAmount(50, 25);
            content.setFont(fontHelB, 8);
            content.drawString("PDF Navn: " + pdfName);
            content.endText();

            //Writes and places the Bygningens gennemgang
            content.beginText();
            content.setFont(fontHelB, 12);
            content.moveTextPositionByAmount(235, 647);
            content.drawString("Bygnings Gennemgang");
            content.endText();

            //Writtes and places bygningens navn and the date of the PDF
            //The placement of the "date" is tied to the placement of 
            //Bygningens navn
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

            //Creates a image from a file and places it.
            //Underline for bygningens navn 
            PDImageXObject bsBName = null;
            bsBName = PDImageXObject.createFromFile(imgFolderPath + "bstring.jpg", doc);
            content.drawXObject(bsBName, 50, 606, 200, 2);

            //Creates a image from a file and places it.
            //Underline for the date 
            PDImageXObject bsDate = null;
            bsDate = PDImageXObject.createFromFile(imgFolderPath + "/bstring.jpg", doc);
            content.drawXObject(bsDate, 350, 606, 200, 2);

            //Writes and places the Bygnings Adresse
            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 545);
            content.drawString("Bygnings Adresse");
            content.newLineAtOffset(0, 12);
            content.drawString("" + buildingAddress);
            content.endText();

            //Creates a image from a file and places it.
            //Underline for the bygnings adresse
            PDImageXObject bsBAdr = null;
            bsBAdr = PDImageXObject.createFromFile(imgFolderPath + "bstring2.jpg", doc);
            content.drawXObject(bsBAdr, 50, 553, 200, 2);

            //Writes and places the Bygnings By and Post Kode.
            //The placement of Postkode is dependent of Bygningens By 
            //placement
            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 475);
            content.drawString("Postnr. / By ");
            content.newLineAtOffset(0, 12);
            content.drawString("" + buildingPostcode);
            content.newLineAtOffset(45, 0);
            content.drawString("" + buildingCity);
            content.endText();

            //Creates a image from a file and places it.
            //Underline for the bygningens by & post kode.
            PDImageXObject bsBCity = null;
            bsBCity = PDImageXObject.createFromFile(imgFolderPath + "bstring4.jpg", doc);
            content.drawXObject(bsBCity, 50, 483, 200, 2);

            //Writes and places the Polygon information.
            //The intial placement is done by the 'Polygon' text and all other 
            //lines are placed accordingly to the 'Polygon' text.
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

            //Creates a image from a file and places it.
            //Sundebygninger logo in the top right corner of the document
            PDImageXObject sundB = null;
            sundB = PDImageXObject.createFromFile(imgFolderPath + "logoJ.jpg", doc);
            content.drawXObject(sundB, 400, 690, 150, 65);

            //Creates a image from a file and places it.
            //Polygon logo in the top left corner of the document
            PDImageXObject polygonLogo = null;
            polygonLogo = PDImageXObject.createFromFile(imgFolderPath + "polygon.jpg", doc);
            content.drawXObject(polygonLogo, 50, 690, 150, 30);

            //Creates a image from a file and places it.
            //Default or user selected picture of the building
            PDImageXObject userBygning = null;
            if (picturePath.equals("")) {

                //Default picture
                userBygning = PDImageXObject.createFromFile(imgFolderPath + "whouse.jpg", doc);
            } else {
                //User picture
                userBygning = PDImageXObject.createFromFile("" + picturePath, doc);
            }
            content.drawXObject(userBygning, 125, 225, 375, 215);

            //Writes and places the Generel information om bygning
            content.beginText();
            content.setFont(fontHelB, 12);
            content.moveTextPositionByAmount(50, 200);
            content.drawString("Generel information om bygningen:");
            content.endText();

            //Writes and places the Bygge år
            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 175);
            content.drawString("Bygge år: " + buildingContructionYear);
            content.endText();

            //Creates a image from a file and places it.
            //Underline for the Bygge År
            PDImageXObject bsBYear = null;
            bsBYear = PDImageXObject.createFromFile(imgFolderPath + "bstring5.jpg", doc);
            content.drawXObject(bsBYear, 98, 172, 25, 2);

            //Writes and places the Bygningens Areal
            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 145);
            content.drawString("Bygningens Areal: " + buildingSQM + " Kvadrat Meter");
            content.endText();

            //Creates a image from a file and places it.
            //Underline for the Bygningens Areal
            PDImageXObject bsBArea = null;
            bsBArea = PDImageXObject.createFromFile(imgFolderPath + "bstring6.jpg", doc);
            content.drawXObject(bsBArea, 140, 142, 120, 2);

            //Writes and places the Hvad bruges bygningen til / Hvad har bygningen været brugt til?  " + buildingPurpose);
            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 120);
            content.drawString("Hvad bruges bygningen til / Hvad har bygningen været brugt til?  " + buildingPurpose);
            content.endText();

            //Creates a image from a file and places it.
            //Underline for the hvad bruges bygningen til / Hvad har bygningen været brugt til?
            PDImageXObject bsBPurpose = null;
            bsBPurpose = PDImageXObject.createFromFile(imgFolderPath + "bstring6.jpg", doc);
            content.drawXObject(bsBPurpose, 358, 117, 120, 2);

//            content.beginText();
//            content.setFont(fontHelB, 10);
//            content.moveTextPositionByAmount(50, 120);
//            content.drawString("Bygning Ejer: " + buildingOwner);
//            content.endText();
            //Writes and places the Page number
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
            //Closes the content creation for Page 1
            content.close();

            System.out.println("End of testMethod");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void page2Setup(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page
        PDPage page2 = new PDPage();

        //Adds the page to the .doc
        doc.addPage(page2);

        try {
            //Open the content creation for Page 2
            PDPageContentStream content2 = new PDPageContentStream(doc, page2);

            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
            PDFont fontHel = PDType1Font.TIMES_ROMAN;
            //Creates a image from a file and places it.
            //Polygon logo in the top left corner of the document
            PDImageXObject polygonLogo = null;
            polygonLogo = PDImageXObject.createFromFile(imgFolderPath + "polygon.jpg", doc);
            content2.drawXObject(polygonLogo, 50, 690, 150, 30);

            //Writes and places the Rapport Nr.
            content2.beginText();
            content2.setFont(fontHel, 10);
            content2.moveTextPositionByAmount(50, 680);
            content2.drawString("Rapport nr.: ");
            content2.endText();

            //Writes and places the PDF Name
            content2.beginText();
            content2.moveTextPositionByAmount(50, 25);
            content2.setFont(fontHelB, 8);
            content2.drawString("PDF Navn: " + pdfName);
            content2.endText();

            content2.beginText();
            content2.setFont(fontHelB, 16);
            content2.moveTextPositionByAmount(50, 660);
            content2.drawString("Gennemgang af bygningen udvendig ");
            content2.endText();

            content2.beginText();
            content2.setFont(fontHelB, 12);
            content2.moveTextPositionByAmount(50, 625);
            content2.drawString("Tag");
            content2.endText();
            PDImageXObject bsBPurpose = null;
            bsBPurpose = PDImageXObject.createFromFile(imgFolderPath + "bstring6.jpg", doc);
            content2.drawXObject(bsBPurpose, 50, 620, 23, 2);

            content2.beginText();
            content2.setFont(fontHelB, 8);
            content2.moveTextPositionByAmount(325, 625);
            content2.drawString("Bemærkning");
            content2.endText();

            content2.beginText();
            content2.setFont(fontHelB, 8);
            content2.moveTextPositionByAmount(400, 625);
            content2.drawString("Ingen Bemærkning");
            content2.endText();

            content2.beginText();
            content2.setFont(fontHelB, 8);
            content2.moveTextPositionByAmount(500, 625);
            content2.drawString("Billede");
            content2.endText();

            PDImageXObject noNotes = null;
            noNotes = PDImageXObject.createFromFile(imgFolderPath + "nonotes.jpg", doc);
            content2.drawXObject(noNotes, 375, 624, 7, 7);

            PDImageXObject notes = null;
            notes = PDImageXObject.createFromFile(imgFolderPath + "notes.jpg", doc);
            content2.drawXObject(notes, 475, 624, 7, 7);

            notes = PDImageXObject.createFromFile(imgFolderPath + "notes.jpg", doc);
            content2.drawXObject(notes, 528, 624, 7, 7);

            content2.beginText();
            content2.setFont(fontHelB, 12);
            content2.moveTextPositionByAmount(50, 310);
            content2.drawString("Ydervægge");
            content2.endText();

            content2.beginText();
            content2.setFont(fontHelB, 8);
            content2.moveTextPositionByAmount(325, 310);
            content2.drawString("Bemærkning");
            content2.endText();

            content2.beginText();
            content2.setFont(fontHelB, 8);
            content2.moveTextPositionByAmount(400, 310);
            content2.drawString("Ingen Bemærkning");
            content2.endText();

            content2.beginText();
            content2.setFont(fontHelB, 8);
            content2.moveTextPositionByAmount(500, 310);
            content2.drawString("Billede");
            content2.endText();

            bsBPurpose = PDImageXObject.createFromFile(imgFolderPath + "bstring6.jpg", doc);
            content2.drawXObject(bsBPurpose, 50, 305, 70, 2);

            noNotes = PDImageXObject.createFromFile(imgFolderPath + "nonotes.jpg", doc);
            content2.drawXObject(noNotes, 375, 310, 7, 7);

            notes = PDImageXObject.createFromFile(imgFolderPath + "notes.jpg", doc);
            content2.drawXObject(notes, 475, 310, 7, 7);

            notes = PDImageXObject.createFromFile(imgFolderPath + "notes.jpg", doc);
            content2.drawXObject(notes, 528, 310, 7, 7);

            //Writes and places the Page number 2
            content2.beginText();
            content2.setFont(fontHelB, 8);
            content2.moveTextPositionByAmount(550, 25);
            content2.drawString("2");
            content2.endText();

            //Closes the content creation for Page 2           
            content2.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void page3Setup(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page.
        PDPage page3 = new PDPage();

        //Adds the new page to the .doc
        doc.addPage(page3);
        try {

            PDPageContentStream content3 = new PDPageContentStream(doc, page3);
            
            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
            PDFont fontHel = PDType1Font.TIMES_ROMAN;

//
            //Writes and places the Page number 3
            content3.beginText();
            content3.setFont(fontHelB, 8);
            content3.moveTextPositionByAmount(550, 25);
            content3.drawString("3");
            content3.endText();

            //Closes the content creation for Page 3
            content3.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void savePDF(String savePath, String pdfName, PDDocument doc) {
        try {

            //WINDOWS CEO: 
            //doc.save("E:\\Dokumenter\\NetBeansProjects\\Polygon\\" + pdfName + ".pdf");
            //MAC CEO 
            //doc.save("/Users/Ceo/NetBeansProjects/Polygon/" + pdfName + ".pdf");
            //Saves the document at the path "savePath" and with the pdfName
            doc.save(savePath + pdfName + ".pdf");

            //Closes the creation the entire document
            doc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

//Image to PDF
   // PDDocument doc = null;
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



//       Boxes/Windows with PDAcroform            
//             //Adobe Acrobat uses Helvetica as a default font and 
//        // stores that under the name '/Helv' in the resources dictionary
//       PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
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
//        widget.setPage(page3);
//
//        // set green border and yellow background
//        // if you prefer defaults, just delete this code block
//        PDAppearanceCharacteristicsDictionary fieldAppearance
//                = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
//        fieldAppearance.setBorderColour(new PDColor(new float[]{0,255,0}, PDDeviceRGB.INSTANCE));
//        fieldAppearance.setBackground(new PDColor(new float[]{255,0,0}, PDDeviceRGB.INSTANCE));
//        widget.setAppearanceCharacteristics(fieldAppearance);
//
//        // make sure the annotation is visible on screen and paper
//        widget.setPrinted(true);
//        
//        // Add the annotation to the page
//        page3.getAnnotations().add(widget);
//        
//        // set the field value
//        textBox.setValue("Sample field");
//        //textBox.drawLine(10, 10, 10, 10);
