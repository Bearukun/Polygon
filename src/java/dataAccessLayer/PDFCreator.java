package dataAccessLayer;

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
 * Comments!!!!!!! Need to figure out to make a method for "PDFont"!
 *
 *
 * Slå polygonLogo sammen med insertJPGImage
 *
 * noNotesCheckBoxImg , gotNotesCheckBoxImg perhaps shouldn't be merged with
 * insertJPGImage, due to them having a specialized function in the code, the
 * fact that they are used alot and multiple times throughout the code, and that
 * having their own method and method name, makes it way easier and quick to
 * understand what is happening.
 *
 * SPLIT THEM INTO 1 Method with a Boolean!
 *
 * Also, use a boolean for: // Ingen Bemærkning if(gotNoTagNotes == 0 ){
 * noNotesCheckBoxImg(imgFolderPath, content, 475, 624, 7, 7); } else if
 * (gotNoTagNotes == 1 ) { gotNotesCheckBoxImg(imgFolderPath, content, 475, 624,
 * 7, 7); }
 *
 * Rough idea for creating a new method that can quicly setup noNotesCheckBoxImg
 * & gotNotesCheckBoxImg, along with their switchCaseName and
 * switchCaseParameters
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

        page2Setup(pdfName, picturePath, imgFolderPath, savePath, doc);

        page3Setup(pdfName, buildingName, buildingAddress, buildingPostcode, buildingCity, buildingContructionYear,
                buildingSQM, buildingPurpose, buildingOwner, picturePath, imgFolderPath, savePath, doc);

        page4Setup(pdfName, buildingName, buildingAddress, buildingPostcode, buildingCity, buildingContructionYear,
                buildingSQM, buildingPurpose, buildingOwner, picturePath, imgFolderPath, savePath, doc);

        page5Setup(pdfName, picturePath, imgFolderPath, savePath, doc);

        page6Setup(pdfName, picturePath, imgFolderPath, savePath, doc);

        savePDF(savePath, pdfName, doc);

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
            //Writes and places the PDF Name 
            defaultNewPageSetup(content, imgFolderPath, pdfName, 1);

            //Writes and places the "Bygningens gennemgang"
            singleTextLine(content, "Bygnings Gennemgang", 12, 235, 647);

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
            insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 606, 200, 2);

            //Creates a image from a file and places it.
            //Underline for the date 
            insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 350, 606, 200, 2);

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
            insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 553, 200, 2);

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
            insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 483, 200, 2);

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
            PDImageXObject sundBygLogo = null;
            sundBygLogo = PDImageXObject.createFromFile(imgFolderPath + "logoJ.jpg", doc);
            content.drawXObject(sundBygLogo, 400, 690, 150, 65);

            //Creates a image from a file and places it.
            //Default or user selected picture of the building                  
            userPicture(picturePath, imgFolderPath, content, 125, 225, 375, 215);

            //Writes and places the Generel information om bygning            
            singleTextLine(content, "Generel information om bygningen:", 12, 50, 200);

            singleTextLineWithUserInput(content, "Bygge år", buildingContructionYear.toString(), 10, 50, 175);

            //Creates a image from a file and places it.
            //Underline for the Bygge År            
            insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 98, 172, 25, 2);

            //Writes and places the Bygningens Areal
            content.beginText();
            content.setFont(fontHelB, 10);
            content.moveTextPositionByAmount(50, 145);
            content.drawString("Bygningens Areal: " + buildingSQM + " Kvadrat Meter");
            content.endText();

            //Creates a image from a file and places it.
            //Underline for the Bygningens Areal
            insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 140, 142, 120, 2);

            singleTextLineWithUserInput(content, "Hvad bruges bygningen til / Hvad har bygningen været brugt til?", buildingPurpose, 10, 50, 120);

            //Creates a image from a file and places it.
            //Underline for the hvad bruges bygningen til / Hvad har bygningen været brugt til?
            insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 358, 117, 120, 2);

            content.close();

            System.out.println("End of testMethod");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void page2Setup(String pdfName, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

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

            defaultNewPageSetup(content2, imgFolderPath, pdfName, 2);

            //Writes and places the PDF Name
            singleTextLine(content2, "Gennemgang af bygningen udvendig ", 16, 50, 660);

            singleTextLine(content2, "Tag", 12, 50, 625);
            insertJPGImage(content2, imgFolderPath, "underLineJPG.jpg", 50, 620, 23, 2);
            singleTextLine(content2, "There are some topics which will be addressed in the project period. Basically they are best understood when you have a larger system to keep track of.", 6, 50, 600);

            singleTextLine(content2, "Bemærkning", 8, 325, 625);

            singleTextLine(content2, "Ingen Bemærkning", 8, 400, 625);

            singleTextLine(content2, "Billede", 8, 500, 625);

            singleTextLine(content2, "Ydervægge", 12, 50, 310);

            singleTextLine(content2, "Bemærkning", 8, 325, 310);

            singleTextLine(content2, "Ingen Bemærkning", 8, 400, 310);

            singleTextLine(content2, "Billede", 8, 500, 310);

            insertJPGImage(content2, imgFolderPath, "underLineJPG.jpg", 50, 305, 70, 2);

            //NEEDS DYNAMIC USER INPUT!!!
            //checkBoxesPage2(content2, imgFolderPath, true, false, true, false, true, true);
            checkBoxesPage2(content2, imgFolderPath, false, true, false, true, false, false);

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

            defaultNewPageSetup(content3, imgFolderPath, pdfName, 3);

            //NEEDS DYNAMIC USER INPUT!!! "Ceo's Kontor = Room Name
            singleTextLineWithUserInput(content3, "Lokale", "Ceo's Kontor", 10, 50, 665);

            //NEEDS A F*CKING NEW NAME!.... AND DYNAMIC USER INPUT!!! 
            // checkIfPage3NeedsPopulation(false, true, false, content3, imgFolderPath);
            checkIfPage3NeedsPopulation(true, false, true, content3, imgFolderPath);
            //if localNotes is true, set noLocalNotes to false

            singleTextLine(content3, "Skade og Reparation", 12, 50, 642);

            singleTextLine(content3, "Har der været skade i lokalet?", 10, 50, 625);

            page3DamageAndRepair(true, content3, imgFolderPath, false, false, false, false, false);

            //Closes the content creation for Page 3
            content3.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //If "Ingen bemærkning" on Page 4, DOES THIS PAGE NEED TO BE GENERATED!?
    public void page4Setup(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page.
        PDPage page4 = new PDPage();

        //Adds the new page to the .doc
        doc.addPage(page4);
        try {

            PDPageContentStream content4 = new PDPageContentStream(doc, page4);

            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
            PDFont fontHel = PDType1Font.TIMES_ROMAN;

            defaultNewPageSetup(content4, imgFolderPath, pdfName, 4);

            singleTextLineWithUserInput(content4, "Lokale", "Ceo's Kontor", 10, 50, 665);

            checkBoxesPage4Walkthrough(content4, imgFolderPath,
                    true, false, true,
                    false, true, false,
                    false, true, true,
                    true, false, false,
                    true, false, true,
                    false, false, false,
                    true, true, true);

            //Closes the content creation for Page 4
            content4.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void page5Setup(String pdfName, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page.
        PDPage page5 = new PDPage();

        //Adds the new page to the .doc
        doc.addPage(page5);
        try {

            PDPageContentStream content5 = new PDPageContentStream(doc, page5);

            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
            PDFont fontHel = PDType1Font.TIMES_ROMAN;

            defaultNewPageSetup(content5, imgFolderPath, pdfName, 5);

            singleTextLine(content5, "Konklusion", 14, 50, 650);

            singleTextLine(content5, "Lokale", 10, 50, 600);
            singleTextLine(content5, "Anbefalinger", 10, 200, 600);

            //Closes the content creation for Page 4
            content5.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void page6Setup(String pdfName, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page.
        PDPage page6 = new PDPage();

        //Adds the new page to the .doc
        doc.addPage(page6);
        try {

            PDPageContentStream content6 = new PDPageContentStream(doc, page6);

            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
            PDFont fontHel = PDType1Font.TIMES_ROMAN;

            defaultNewPageSetup(content6, imgFolderPath, pdfName, 6);

            //Needs real user input
            singleTextLineWithUserInput(content6, "Bygningsgennemgang er fortaget af", "The Ceo" + " , Polygon", 10, 50, 650);

            //Needs real user input
            singleTextLineWithUserInput(content6, "i samarbejde med ", "The other Ceo" + " (bygningsansvarlig).", 10, 50, 630);

            singleTextLine(content6, "Bygningen er katagoriseret som", 14, 50, 600);

            singleTextLine(content6, "Tilstand", 12, 50, 580);

            singleTextLine(content6, "Tilstandsgrad 1", 10, 50, 560);
            insertJPGImage(content6, imgFolderPath, "underLineJPG.jpg", 50, 555, 70, 2);
            singleTextLine(content6, "God Tilstand", 10, 50, 545);

            singleTextLine(content6, "Tilstandsgrad 2", 10, 50, 520);
            insertJPGImage(content6, imgFolderPath, "underLineJPG.jpg", 50, 515, 70, 2);
            singleTextLine(content6, "Middel Tilstand", 10, 50, 505);

            singleTextLine(content6, "Tilstandsgrad 3", 10, 50, 480);
            insertJPGImage(content6, imgFolderPath, "underLineJPG.jpg", 50, 475, 70, 2);
            singleTextLine(content6, "Dårlig Tilstand", 10, 50, 465);

            singleTextLine(content6, "Beskrivelse af bygningen", 10, 200, 580);

            //God Tilstand
            singleTextLine(content6, "Der er ingen problemer med bygningen; ", 8, 140, 560);
            singleTextLine(content6, "Bygningens funktion er uden problemer", 8, 140, 550);

            //Middel Tilstand
            singleTextLine(content6, "Der er slid og skader på bygningen eller risiko for potentielle problemer med bygningen.", 8, 140, 520);
            singleTextLine(content6, "Bygningens funktion er nedsat, eller der er risiko for, at funktionen bliver nedsat.", 8, 140, 510);

            //Dårlig tilstand
            singleTextLine(content6, "Der er problemer med bygningen.", 8, 140, 480);
            singleTextLine(content6, "Bygningen er begyndt at forfalde, har defekte komponenter, er nedbrudt eller bør udskiftes", 8, 140, 470);
            singleTextLine(content6, "Bygningens funktion er nedsat, eller bygningen er næsten eller helt ubrulig.", 8, 140, 460);

            singleTextLine(content6, "Tilstandsgrad", 12, 515, 580);
            
            //Skal bruges en boolean at styre sig efter
            
            //God Tilstand
            checkBoxImg(true, imgFolderPath, content6, 550, 560, 7, 7);
            //Middel Tilstand
            checkBoxImg(true, imgFolderPath, content6, 550, 520, 7, 7);
            //Dårlig Tilstand
            checkBoxImg(true, imgFolderPath, content6, 550, 480, 7, 7);
            
            //Terms of Use (?)
            singleTextLine(content6, "Denne rapport og bygningsgennemgang er lavet for at klarlægge umiddelbare visuelle problemstillinger.", 8, 50, 400);
            singleTextLine(content6, "Vores formål er at sikre, at bygningens anvendelse kan opretholdes", 8, 50, 390);
            singleTextLine(content6, "Vi udbedrer ikke skader som en del af bygningsgennemgangen/rapporten.", 8, 50, 380);
            singleTextLine(content6, "Gennemgangen af bygningen indeholder ikke fugtmålinger af hele bygningen,", 8, 50, 370);
            singleTextLine(content6, "men vi kan foretage fugtscanninger enkelte steder i bygningen, hvis vi finder det nødvendigt.", 8, 50, 360);
            singleTextLine(content6, "Hvis vi finder kritiske områder i bygningen vil vi fremlægge anbefalinger angående yderligere tiltag så som yderligere undersøgelser,", 8, 50, 350);
            singleTextLine(content6, "reparationer eller bygningsopdateringer.", 8, 50, 340);
            singleTextLine(content6, "Bemærk at vi skal have adgang til hele bygningen for at kunne udføre en fuld gennemgang", 8, 50, 330);
            singleTextLine(content6, "(dette inkluderer adgang til tag, tagrum, kælder, krybekælder eller andre aflukkede områder). ", 8, 50, 320);
            singleTextLine(content6, "Denne bygningsgennemgang er ikke-destruktiv. Hvis der skal laves destruktive indgreb, ", 8, 50, 310);
            singleTextLine(content6, "skal dette først godkendes af de bygningsansvarlige.", 8, 50, 300);
            singleTextLine(content6, "Destruktive indgreb er ikke en del af denne rapport eller bygningsgennemgang. ", 8, 50, 290);
            singleTextLine(content6, "Den bygningsansvarlige skal udlevere plantegning over bygningen inden bygningsgennemgangen kan foretages. ", 8, 50, 260);
            
            
            
            //Closes the content creation for Page 6
            content6.close();

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

    public void insertJPGImage(PDPageContentStream content, String imgFolderPath, String imgName, int xCoordinate, int yCoordinate, int imgWidth, int imgHeight) {

        try {
            PDImageXObject underLineString = null;
            underLineString = PDImageXObject.createFromFile(imgFolderPath + imgName, doc);
            content.drawXObject(underLineString, xCoordinate, yCoordinate, imgWidth, imgHeight);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Writes and places the Page number
    public void pageNumber(PDPageContentStream content, int pageNumber) {
        try {
            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;

            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(550, 25);
            content.drawString("" + pageNumber);
            content.endText();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void repportNumber(PDPageContentStream content) {

        PDFont fontHel = PDType1Font.TIMES_ROMAN;

        try {

            //Writes and places the Rapport Nr.
            content.beginText();
            content.setFont(fontHel, 10);
            content.moveTextPositionByAmount(50, 680);
            content.drawString("Rapport nr.: ");
            content.endText();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void defaultNewPageSetup(PDPageContentStream content, String imgFolderPath, String pdfName, int pageNumber) {

        repportNumber(content);
        pdfDocName(content, pdfName);
        pageNumber(content, pageNumber);
        insertJPGImage(content, imgFolderPath, "polygon.jpg", 50, 690, 150, 30);

    }

    public void pdfDocName(PDPageContentStream content, String pdfName) {
        try {
            PDFont fontHelB = PDType1Font.HELVETICA_BOLD;

            content.beginText();
            content.moveTextPositionByAmount(50, 25);
            content.setFont(fontHelB, 8);
            content.drawString("PDF Navn: " + pdfName);
            content.endText();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void userPicture(String picturePath, String imgFolderPath, PDPageContentStream content, int xCoordinate, int yCoordinate, int imgWidth, int imgHeight) {

        try {

            PDImageXObject userBygning = null;
            if (picturePath.equals("")) {

                //Default picture
                userBygning = PDImageXObject.createFromFile(imgFolderPath + "whouse.jpg", doc);
            } else {
                //User picture
                userBygning = PDImageXObject.createFromFile("" + picturePath, doc);
            }
            content.drawXObject(userBygning, xCoordinate, yCoordinate, imgWidth, imgHeight);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public void noNotesCheckBoxImg(String imgFolderPath, PDPageContentStream content, int xCoordinate, int yCoordinate, int imgWidth, int imgHeight) {
//        try {
//            PDImageXObject noNotes = null;
//            noNotes = PDImageXObject.createFromFile(imgFolderPath + "nonotes.jpg", doc);
//            content.drawXObject(noNotes, xCoordinate, yCoordinate, imgWidth, imgHeight);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//    public void gotNotesCheckBoxImg(String imgFolderPath, PDPageContentStream content, int xCoordinate, int yCoordinate, int imgWidth, int imgHeight) {
//        try {
//            PDImageXObject notes = null;
//            notes = PDImageXObject.createFromFile(imgFolderPath + "notes.jpg", doc);
//            content.drawXObject(notes, xCoordinate, yCoordinate, imgWidth, imgHeight);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
    public void checkBoxImg(boolean box, String imgFolderPath, PDPageContentStream content, int xCoordinate, int yCoordinate, int imgWidth, int imgHeight) {

        //Boolean box
        //If false = no X in checkbox
        //If true = X in Checkbox
        try {
            if (box == true) {

                insertJPGImage(content, imgFolderPath, "notes.jpg", xCoordinate, yCoordinate, imgWidth, imgHeight);

            } else {

                insertJPGImage(content, imgFolderPath, "nonotes.jpg", xCoordinate, yCoordinate, imgWidth, imgHeight);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void page3DamageAndRepair(boolean roomDamage, PDPageContentStream content, String imgFolderPath,
            boolean moist, boolean rotAndMushroom, boolean mold, boolean fire, boolean otherDamage) {

        singleTextLine(content, "Ja", 10, 200, 625);
        singleTextLine(content, "Nej", 10, 228, 625);

        if (roomDamage == false) {
            checkBoxImg(roomDamage, imgFolderPath, content, 215, 625, 7, 7);

            checkBoxImg(true, imgFolderPath, content, 250, 625, 7, 7);
        } else {

            checkBoxImg(roomDamage, imgFolderPath, content, 215, 625, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 250, 625, 7, 7);

            //Input fra bruger 
            singleTextLineWithUserInput(content, "Hvornår? ", "Test31", 10, 50, 590);

            //Input fra bruger
            singleTextLineWithUserInput(content, "Hvor? ", "Test det er sket her", 10, 200, 590);

            //Input fra bruger
            singleTextLineWithUserInput(content, "Hvad er der sket?", "Katten legede med sin kugle under border og den trillede væk...", 10, 50, 560);

            //Input fra bruger
            singleTextLineWithUserInput(content, "Hvad er der repareret", "Katten fik en ny bold og en lillebror", 10, 50, 540);

            //Input fra bruger
            singleTextLine(content, "Skade", 10, 50, 520);

            singleTextLine(content, "Fugt", 10, 60, 505);
            singleTextLine(content, "Råd og svamp", 10, 140, 505);
            singleTextLine(content, "Skimmel", 10, 260, 505);
            singleTextLine(content, "Brand", 10, 60, 490);
            singleTextLine(content, "Andet", 10, 60, 475);

            if (moist == true) {
                checkBoxImg(moist, imgFolderPath, content, 50, 505, 7, 7);
            } else {
                checkBoxImg(moist, imgFolderPath, content, 50, 505, 7, 7);
            }
            if (rotAndMushroom == true) {
                checkBoxImg(rotAndMushroom, imgFolderPath, content, 130, 505, 7, 7);
            } else {
                checkBoxImg(rotAndMushroom, imgFolderPath, content, 130, 505, 7, 7);
            }
            if (mold == true) {
                checkBoxImg(mold, imgFolderPath, content, 250, 505, 7, 7);
            } else {
                checkBoxImg(mold, imgFolderPath, content, 250, 505, 7, 7);
            }
            if (fire == true) {
                checkBoxImg(fire, imgFolderPath, content, 50, 490, 7, 7);
            } else {
                checkBoxImg(fire, imgFolderPath, content, 50, 490, 7, 7);
            }
            if (otherDamage == true) {
                checkBoxImg(otherDamage, imgFolderPath, content, 50, 475, 7, 7);
                //needs user input
                singleTextLineWithUserInput(content, "", "The kat is on freaking fire", 10, 130, 475);
                insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 128, 472, 120, 2);
            } else {
                checkBoxImg(otherDamage, imgFolderPath, content, 50, 475, 7, 7);
            }

        }
    }

    //Add a 'PDFont' parameter at a later point!
    public void singleTextLine(PDPageContentStream content, String textLine, int textSize, int xCoordinate, int yCoordinate) {

        PDFont fontHelB = PDType1Font.HELVETICA_BOLD;

        try {
            //Writes and places the Generel information om bygning
            content.beginText();
            content.setFont(fontHelB, textSize);
            content.moveTextPositionByAmount(xCoordinate, yCoordinate);
            content.drawString(textLine);
            content.endText();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //Add a 'PDFont' parameter at a later point!
    public void singleTextLineWithUserInput(PDPageContentStream content, String textLine, String userInput, int textSize, int xCoordinate, int yCoordinate) {

        PDFont fontHelB = PDType1Font.HELVETICA_BOLD;

        try {

            //Writes and places the Bygge år
            content.beginText();
            content.setFont(fontHelB, textSize);
            content.moveTextPositionByAmount(xCoordinate, yCoordinate);
            content.drawString(textLine + ": " + userInput);
            content.endText();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void checkBoxesPage2(PDPageContentStream content, String imgFolderPath,
            boolean roofNotes, boolean noRoofNotes, boolean roofPicture,
            boolean wallNotes, boolean noWallNotes, boolean wallPicture) {

        //Ydervægge
        //Bemærkning
        checkBoxImg(roofNotes, imgFolderPath, content, 375, 624, 7, 7);

        //  Ingen Bemærkning
        checkBoxImg(noRoofNotes, imgFolderPath, content, 475, 624, 7, 7);
        // Billede

        checkBoxImg(roofPicture, imgFolderPath, content, 528, 624, 7, 7);

        //Ydervægge
        //Bemærkning
        checkBoxImg(wallNotes, imgFolderPath, content, 375, 310, 7, 7);
        //  Ingen Bemærkning
        checkBoxImg(noWallNotes, imgFolderPath, content, 475, 310, 7, 7);

        // Billede
        checkBoxImg(wallPicture, imgFolderPath, content, 528, 310, 7, 7);

    }

    //NEEDS A F*CKING NEW NAME!.... AND DYNAMIC USER INPUT!!!
    public void checkIfPage3NeedsPopulation(boolean localNotes, boolean noLocalNotes, boolean moistScan,
            PDPageContentStream content, String imgFolderPath) {

        if (localNotes == true && noLocalNotes == false) {

            //NEEDS DYNAMIC USER INPUT!!! 
            //Bemærkning
            singleTextLine(content, "Bemærkninger", 10, 340, 670);
            checkBoxImg(localNotes, imgFolderPath, content, 413, 670, 7, 7);

            //Ingen Bemærkning
            singleTextLine(content, "Ingen bemærkninger", 10, 430, 670);
            checkBoxImg(noLocalNotes, imgFolderPath, content, 531, 670, 7, 7);

            checkBoxesPage3MoistScan(moistScan, content, "31/12-2016", "Ceo Office", imgFolderPath);
        } else {

            singleTextLine(content, "Bemærkninger", 10, 340, 670);
            checkBoxImg(localNotes, imgFolderPath, content, 413, 670, 7, 7);

            //Ingen Bemærkning
            singleTextLine(content, "Ingen bemærkninger", 10, 430, 670);
            checkBoxImg(noLocalNotes, imgFolderPath, content, 531, 670, 7, 7);

            singleTextLine(content, "No further information", 20, 250, 250);

        }

        //NEEDS DYNAMIC USER INPUT!!! String moistScanned, String moistMeasurePoint
        //NEEDS DYNAMIC USER INPUT!!!
        //NEEDS DYNAMIC USER INPUT!!!
    }

    public void checkBoxesPage3DamageAndRepair() {

    }

    public void checkBoxesPage3MoistScan(boolean moistScan, PDPageContentStream content, String moistScanned, String moistMeasurePoint, String imgFolderPath) {

        //Has a Moistscan been performed?
        //Hvad er målepunkt?
        //Hvad er fugtscanning?
        //Bemærkning?
        singleTextLine(content, "Er der fortaget fugtscanning?", 10, 50, 400);

        if (moistScan == false) {
            singleTextLine(content, "Ja", 10, 250, 400);
            checkBoxImg(moistScan, imgFolderPath, content, 240, 400, 7, 7);
            singleTextLine(content, "Nej", 10, 280, 400);
            checkBoxImg(true, imgFolderPath, content, 270, 400, 7, 7);

        } else if (moistScan == true) {
            singleTextLine(content, "Ja", 10, 250, 400);
            checkBoxImg(moistScan, imgFolderPath, content, 240, 400, 7, 7);
            singleTextLine(content, "Nej", 10, 280, 400);
            checkBoxImg(false, imgFolderPath, content, 270, 400, 7, 7);
            singleTextLineWithUserInput(content, "Fugtscanning", moistScanned, 10, 50, 380);
            singleTextLineWithUserInput(content, "Målepunkt", moistMeasurePoint, 10, 250, 380);
            singleTextLineWithUserInput(content, "Note:", "Vi har konstateret fugtskade under kontorbordet!", 10, 50, 360);
        }

    }

    //Can't possibly fufill all your needs? What if the case also needs some text?
//    public void gotNotesAndNoNotesCheckBoxsImages(int switchMechanismName, int switchMechanismInput,  PDPageContentStream content, 
//            int xGotNotesCoordinate, int yGotNotesCoordinate, int xNoNotesCoordinate, int yNoNotesCoordinate, int imgWidth, int imgHeight){
//        
//        
//        
//    }
    public void checkBoxesPage4Walkthrough(PDPageContentStream content, String imgFolderPath,
            boolean ceilingNotes, boolean noCeilingNotes, boolean ceilingPicture,
            boolean wallNotes, boolean noWallNotes, boolean wallPicture,
            boolean floorNotes, boolean noFloorNotes, boolean floorPicture,
            boolean windowNotes, boolean noWindowNotes, boolean windowPicture,
            boolean doorNotes, boolean noDoorNotes, boolean doorPicture,
            boolean other1Notes, boolean noOther1Notes, boolean other1Picture,
            boolean other2Notes, boolean noOther2Notes, boolean other2Picture) {

        singleTextLine(content, "Gennemgang af lokalet", 12, 50, 640);

        singleTextLine(content, "Bemærkning", 10, 300, 620);
        singleTextLine(content, "Ingen Bemærkning", 10, 390, 620);
        singleTextLine(content, "Billede", 10, 510, 620);

        singleTextLine(content, "Vægge", 10, 50, 600);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 595, 37, 2);
        singleTextLineWithUserInput(content, "Væg tekst", "væææææææææææææææææææg", 8, 50, 584);

        singleTextLine(content, "Loft", 10, 50, 525);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 522, 22, 2);
        singleTextLineWithUserInput(content, "Loft tekst", "loooooooooooft", 8, 50, 511);

        singleTextLine(content, "Gulv", 10, 50, 450);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 447, 23, 2);
        singleTextLineWithUserInput(content, "Gulv tekst", "guuuuuuuuuuuuuuuuuvl", 8, 50, 436);

        singleTextLine(content, "Vinduer", 10, 50, 375);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 372, 40, 2);
        singleTextLineWithUserInput(content, "Vindue tekst", "vinduuuuuuuuuuuue", 8, 50, 361);

        singleTextLine(content, "Døre", 10, 50, 300);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 297, 25, 2);
        singleTextLineWithUserInput(content, "Dør tekst", "døøøøøøøøøøøøøøøøøøør", 8, 50, 286);

        singleTextLine(content, "Andet", 10, 50, 225);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 222, 30, 2);
        singleTextLineWithUserInput(content, "Andet 1 tekst", "Andddddddeeeeeeeeeeetttt 1", 8, 50, 211);

        singleTextLine(content, "Andet", 10, 50, 150);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 147, 30, 2);
        singleTextLineWithUserInput(content, "Andet 2 tekst", "Andddddddeeeeeeeeeeetttt 2", 8, 50, 136);

        //Wall
        //Bemærkning
        checkBoxImg(wallNotes, imgFolderPath, content, 324, 600, 7, 7);
        //  Ingen Bemærkning
        checkBoxImg(noWallNotes, imgFolderPath, content, 428, 600, 7, 7);

        // Billede
        checkBoxImg(wallPicture, imgFolderPath, content, 524, 600, 7, 7);

        //Ceiling
        //Bemærkning
        checkBoxImg(ceilingNotes, imgFolderPath, content, 324, 525, 7, 7);

        //  Ingen Bemærkning
        checkBoxImg(noCeilingNotes, imgFolderPath, content, 428, 525, 7, 7);
        // Billede

        checkBoxImg(ceilingPicture, imgFolderPath, content, 524, 525, 7, 7);

        //Floor
        //Bemærkning
        checkBoxImg(floorNotes, imgFolderPath, content, 324, 450, 7, 7);

        //  Ingen Bemærkning
        checkBoxImg(noFloorNotes, imgFolderPath, content, 428, 450, 7, 7);
        // Billede

        checkBoxImg(floorPicture, imgFolderPath, content, 524, 450, 7, 7);

        //Windows
        //Bemærkning
        checkBoxImg(windowNotes, imgFolderPath, content, 324, 375, 7, 7);

        //  Ingen Bemærkning
        checkBoxImg(noWindowNotes, imgFolderPath, content, 428, 375, 7, 7);
        // Billede

        checkBoxImg(windowPicture, imgFolderPath, content, 524, 375, 7, 7);

        //Doors
        //Bemærkning
        checkBoxImg(doorNotes, imgFolderPath, content, 324, 300, 7, 7);

        //  Ingen Bemærkning
        checkBoxImg(doorNotes, imgFolderPath, content, 428, 300, 7, 7);
        // Billede

        checkBoxImg(doorPicture, imgFolderPath, content, 524, 300, 7, 7);

        //other1
        //Bemærkning
        checkBoxImg(other1Notes, imgFolderPath, content, 324, 225, 7, 7);

        //  Ingen Bemærkning
        checkBoxImg(other1Notes, imgFolderPath, content, 428, 225, 7, 7);
        // Billede

        checkBoxImg(other1Picture, imgFolderPath, content, 524, 225, 7, 7);

        //other2
        //Bemærkning
        checkBoxImg(other1Notes, imgFolderPath, content, 324, 150, 7, 7);

        //  Ingen Bemærkning
        checkBoxImg(other1Notes, imgFolderPath, content, 428, 150, 7, 7);
        // Billede

        checkBoxImg(other1Picture, imgFolderPath, content, 524, 150, 7, 7);

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

