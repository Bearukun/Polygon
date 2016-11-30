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

/**
 *
 * @author Ceo
 *
 * COMMENTS!!!!
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
 * //Reminder of how to move. //content.moveTextPositionByAmount(tx, ty); //tx
 * = Width; (Max 450-500!) //ty = Height (max 800!) //Opens ContentStream for
 * writting to the PDF document
 *
 */
public class PDFCreator {

    //sourceFolder sf = new sourceFolder();
    PDDocument doc = doc = new PDDocument();

    //Creates various text font objects
    PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
    PDFont fontHel = PDType1Font.TIMES_ROMAN;

    int pageNumber = 1;
    String pageNumberTitel = "page" + pageNumber;
    String pageContentStreamNumber = "content" + pageNumber;

    //Method that creates the PDF pages.
    //Way too much input
    public void createPDF(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath) {

        page1Setup(pdfName, buildingName, buildingAddress, buildingPostcode, buildingCity, buildingContructionYear,
                buildingSQM, buildingPurpose, buildingOwner, picturePath, imgFolderPath, savePath, doc);

        pageNumber++;

        page2Setup(pdfName, picturePath, imgFolderPath, savePath, doc);

        pageNumber++;

        page3Setup(pdfName, buildingName, buildingAddress, buildingPostcode, buildingCity, buildingContructionYear,
                buildingSQM, buildingPurpose, buildingOwner, picturePath, imgFolderPath, savePath, doc);

        pageNumber++;

        page4Setup(pdfName, buildingName, buildingAddress, buildingPostcode, buildingCity, buildingContructionYear,
                buildingSQM, buildingPurpose, buildingOwner, picturePath, imgFolderPath, savePath, doc);

        pageNumber++;

        page5Setup(pdfName, picturePath, imgFolderPath, savePath, doc);

        pageNumber++;

        page6Setup(pdfName, picturePath, imgFolderPath, savePath, doc);

        pageNumber++;

        for (int i = 0; i < 10; i++) {
            pageGeneration(doc, pdfName, imgFolderPath);
            pageNumber++;
            System.out.println(pageNumber);

        }
        savePDF(savePath, pdfName, doc);

    }

    //BASIC AUTOPAGE GENERATOR!!!
    public void pageGeneration(PDDocument doc, String pdfName, String imgFolderPath) {
        PDPage pageNumberTitel = new PDPage();
        //PDPage page8 = new PDPage();
        doc.addPage(pageNumberTitel);
        try {

            PDPageContentStream pageContentNumber = new PDPageContentStream(doc, pageNumberTitel);

            defaultNewPageSetup(pageContentNumber, imgFolderPath, pdfName);

            pageContentNumber.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Setup of Page 1
    public void page1Setup(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //!REMOVE UPON COMPLETION OF PDFGENERATOR!
        System.out.println("Entered TestMethod");

        //Registers the time and date for the PDF document
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        //Initiates new PDPAge Object
        PDPage pageNumberTitel = new PDPage();

        //Adds the page to the .doc
        doc.addPage(pageNumberTitel);

        try {

            //Creates a new PDPageContentStream object,
            //which consist of a PDDocument object and PDPAge object
            PDPageContentStream pageContentStreamNumber = new PDPageContentStream(doc, pageNumberTitel);

            //Method that writes and places the default information that is required for each page of the PDF document.
            defaultNewPageSetup(pageContentStreamNumber, imgFolderPath, pdfName);

            //Writes and places the "Bygningens gennemgang"
            singleTextLine(pageContentStreamNumber, "Bygnings Gennemgang", 12, 235, 647);

            //Writtes and places bygningens navn and the date of the PDF
            //The placement of the "date" is tied to the placement of 
            //Bygningens navn
            //Start writting text
            pageContentStreamNumber.beginText();
            //Sets the font and text size
            pageContentStreamNumber.setFont(fontHelB, 10);
            //Set Coordinates for the first line of text
            pageContentStreamNumber.moveTextPositionByAmount(50, 610);
            //First line of text
            pageContentStreamNumber.drawString("" + buildingName);
            //Coordinates for the next line of text,
            //which are offset from the previous line of text            
            pageContentStreamNumber.newLineAtOffset(0, -12);
            pageContentStreamNumber.drawString("Bygnings navn");
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(300, 0);
            //Second line of text
            pageContentStreamNumber.drawString("Dato");
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(0, 12);
            //Third line of text (which is the date + time, which is autogenerated)
            pageContentStreamNumber.drawString("" + cal.getTime());
            //End writting text
            pageContentStreamNumber.endText();

            //Creates a image from a file and places it.
            //Underline for "bygningens navn"             
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 606, 200, 2);

            //Creates a image from a file and places it.
            //Underline for "the date "
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 350, 606, 200, 2);

            //Writes and places the Bygnings Adresse
            //Start writting text
            pageContentStreamNumber.beginText();
            //Sets the font and text size
            pageContentStreamNumber.setFont(fontHelB, 10);
            //Set Coordinates for the first line of text
            pageContentStreamNumber.moveTextPositionByAmount(50, 545);
            //First line of text
            pageContentStreamNumber.drawString("Bygnings Adresse");
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(0, 12);
            //Second line of text
            pageContentStreamNumber.drawString("" + buildingAddress);
            //End writting text
            pageContentStreamNumber.endText();

            //Creates a image from a file and places it.
            //Underline for the "bygnings adresse"            
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 553, 200, 2);

            //Writes and places the Bygnings By and Post Kode.
            //The placement of Postkode is dependent of Bygningens By 
            //placement
            //Start writting text
            pageContentStreamNumber.beginText();
            //Sets the font and text size
            pageContentStreamNumber.setFont(fontHelB, 10);
            //Set Coordinates for the first line of text
            pageContentStreamNumber.moveTextPositionByAmount(50, 475);
            //First line of text
            pageContentStreamNumber.drawString("Postnr. / By ");
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(0, 12);
            //Second line of text
            pageContentStreamNumber.drawString("" + buildingPostcode);
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(45, 0);
            //Third line of text
            pageContentStreamNumber.drawString("" + buildingCity);
            //End writting text
            pageContentStreamNumber.endText();

            //Creates a image from a file and places it.
            //Underline for the "bygningens by & post kode."
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 483, 200, 2);

            //Writes and places the Polygon information.
            //The intial placement is done by the 'Polygon' text and all other 
            //lines are placed accordingly to the 'Polygon' text.
            //Start writting text
            pageContentStreamNumber.beginText();
            //Sets the font and text size
            pageContentStreamNumber.setFont(fontHelB, 12);
            //Set Coordinates for the first line of text
            pageContentStreamNumber.moveTextPositionByAmount(500, 560);
            //First line of text
            pageContentStreamNumber.drawString("Polygon");
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(-19, -13);
            //Scond line of text
            pageContentStreamNumber.drawString("Rypevang 5");
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(-6, -13);
            //Third line of text
            pageContentStreamNumber.drawString("3450 Allerød");
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(-5, -18);
            //Fourth line of text
            pageContentStreamNumber.drawString("Tlf. 4814 0055");
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(-90, -13);
            //Fifth line of text
            pageContentStreamNumber.drawString("sundebygninger@polygon.dk");
            //End writting text
            pageContentStreamNumber.endText();

            //Creates a image from a file and places it.
            //Places the "Sundebygninger" logo in the top right corner of the document
            PDImageXObject sundBygLogo = null;
            sundBygLogo = PDImageXObject.createFromFile(imgFolderPath + "logoJ.jpg", doc);
            pageContentStreamNumber.drawXObject(sundBygLogo, 400, 690, 150, 65);

            //Creates a image from a file and places it.
            //Places the Default or user selected picture of the building in the PDF document.                  
            userPicture(picturePath, imgFolderPath, pageContentStreamNumber, 125, 225, 375, 215);

            //Writes and places the text line "Generel information om bygning"           
            singleTextLine(pageContentStreamNumber, "Generel information om bygningen:", 12, 50, 200);

            //Writes and places the text line "Bygge år"
            singleTextLineWithUserInput(pageContentStreamNumber, "Bygge år", buildingContructionYear.toString(), 10, 50, 175);

            //Creates a image from a file and places it.
            //Underline for the "Bygge År "           
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 98, 172, 25, 2);

            //Writes and places the text line "Bygningens Areal"
            singleTextLineWithUserInput(pageContentStreamNumber, "Bygningens Areal", buildingSQM + " Kvadrat Meter", 10, 50, 145);

            //Creates a image from a file and places it.
            //Underline for the "Bygningens Areal"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 140, 142, 120, 2);

            //Writes and places the text line "Hvad bruges bygningen til / Hvad har bygningen været brugt til?" + the users input
            singleTextLineWithUserInput(pageContentStreamNumber, "Hvad bruges bygningen til / Hvad har bygningen været brugt til?", buildingPurpose, 10, 50, 120);

            //Creates a image from a file and places it.
            //Underline for the "Hvad bruges bygningen til / Hvad har bygningen været brugt til?"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 358, 117, 120, 2);

            //closes the page for anymore content to be written.
            pageContentStreamNumber.close();

            System.out.println("End of testMethod");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //Setup of Page 2
    public void page2Setup(String pdfName, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page Object
        PDPage pageNumberTitel = new PDPage();

        //Adds the page to the .doc
        doc.addPage(pageNumberTitel);

        try {
            //Creates a new PDPageContentStream object,
            //which consist of a PDDocument object and PDPAge object
            PDPageContentStream pageContentStreamNumber = new PDPageContentStream(doc, pageNumberTitel);

            //Method that writes and places the default information that is required for each page of the PDF document.
            defaultNewPageSetup(pageContentStreamNumber, imgFolderPath, pdfName);

            //Writes and places the text line "Gennemgang af bygningen udvendig "
            singleTextLine(pageContentStreamNumber, "Gennemgang af bygningen udvendig ", 16, 50, 660);

            //Writes and places the text line "Tag"
            singleTextLine(pageContentStreamNumber, "Tag", 12, 50, 625);
            //Creates a image from a file and places it.
            //Underline for "Tag"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 620, 23, 2);

            //Some test Text !TO BE REMOVED!
            singleTextLine(pageContentStreamNumber, "There are some topics which will be addressed in the project period. Basically they are best understood when you have a larger system to keep track of.", 6, 50, 600);

            //Writes and places the text line "Bemærkning" for "Tag"
            singleTextLine(pageContentStreamNumber, "Bemærkning", 8, 325, 625);

            //Writes and places the text line "Ingen Bemærkning" for "Tag"
            singleTextLine(pageContentStreamNumber, "Ingen Bemærkning", 8, 400, 625);

            //Writes and places the text line "Billede" for "Tag"
            singleTextLine(pageContentStreamNumber, "Billede", 8, 500, 625);

            //Writes and places the text line "Ydervægge" 
            singleTextLine(pageContentStreamNumber, "Ydervægge", 12, 50, 310);

            //Writes and places the text line "Bemærkning" for "Ydervægge"
            singleTextLine(pageContentStreamNumber, "Bemærkning", 8, 325, 310);

            //Writes and places the text line "Ingen Bemærkning" for "Ydervægge"
            singleTextLine(pageContentStreamNumber, "Ingen Bemærkning", 8, 400, 310);

            //Writes and places the text line "Billede" for "Ydervægge"
            singleTextLine(pageContentStreamNumber, "Billede", 8, 500, 310);

            //Creates a image from a file and places it.
            //Underline for "Ydervægge"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 305, 70, 2);

            //NEEDS DYNAMIC USER INPUT!!!
            //Sets the checkboxes for:
            //"Tag" (Bemærkning, Ingen bemærkning, Billede) and 
            //Ydervægge (Bemærkning, Ingen bemærkning, Billede)
            //checkBoxesPage2(pageContentStreamNumber, imgFolderPath, true, false, true, false, true, true);
            checkBoxesPage2(pageContentStreamNumber, imgFolderPath, false, true, false, true, false, false);

            //Closes the content creation for Page 2           
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Setup of Page 3
    public void page3Setup(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page Object
        PDPage pageNumberTitel = new PDPage();

        //Adds the new page to the .doc
        doc.addPage(pageNumberTitel);
        try {

            //Creates a new PDPageContentStream object,
            //which consist of a PDDocument object and PDPAge object
            PDPageContentStream pageContentStreamNumber = new PDPageContentStream(doc, pageNumberTitel);

            //Method that writes and places the default information that is required for each page of the PDF document.
            defaultNewPageSetup(pageContentStreamNumber, imgFolderPath, pdfName);

            //NEEDS DYNAMIC USER INPUT!!! "Ceo's Kontor = Room Name
            singleTextLineWithUserInput(pageContentStreamNumber, "Lokale", "Ceo's Kontor", 10, 50, 665);

            //NEEDS A F*CKING NEW NAME!.... AND DYNAMIC USER INPUT!!! 
            // checkIfPage3NeedsPopulation(false, true, false, content3, imgFolderPath);
            checkIfPage3NeedsPopulation(true, false, true, pageContentStreamNumber, imgFolderPath);
            //if localNotes is true, set noLocalNotes to false

            singleTextLine(pageContentStreamNumber, "Skade og Reparation", 12, 50, 642);

            singleTextLine(pageContentStreamNumber, "Har der været skade i lokalet?", 10, 50, 625);

            page3DamageAndRepair(true, pageContentStreamNumber, imgFolderPath, false, false, false, false, false);

            //Closes the content creation for Page 3
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Setup of Page 4
    //If "Ingen bemærkning" on Page 4, DOES THIS PAGE NEED TO BE GENERATED!?
    public void page4Setup(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear,
            Integer buildingSQM, String buildingPurpose, String buildingOwner, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page Object
        PDPage pageNumberTitel = new PDPage();

        //Adds the new page to the .doc
        doc.addPage(pageNumberTitel);
        try {

            //Creates a new PDPageContentStream object,
            //which consist of a PDDocument object and PDPAge object
            PDPageContentStream pageContentStreamNumber = new PDPageContentStream(doc, pageNumberTitel);

            //Method that writes and places the default information that is required for each page of the PDF document.
            defaultNewPageSetup(pageContentStreamNumber, imgFolderPath, pdfName);

            //NEEDS USER INPUT!
            singleTextLineWithUserInput(pageContentStreamNumber, "Lokale", "Ceo's Kontor", 10, 50, 665);

            checkBoxesPage4Walkthrough(pageContentStreamNumber, imgFolderPath);

            //Closes the content creation for Page 4
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //Setup of Page 5

    public void page5Setup(String pdfName, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page Object
        PDPage pageNumberTitel = new PDPage();

        //Adds the new page to the .doc
        doc.addPage(pageNumberTitel);
        try {

            //Creates a new PDPageContentStream object,
            //which consist of a PDDocument object and PDPAge object
            PDPageContentStream pageContentStreamNumber = new PDPageContentStream(doc, pageNumberTitel);

            //Method that writes and places the default information that is required for each page of the PDF document.
            defaultNewPageSetup(pageContentStreamNumber, imgFolderPath, pdfName);

            singleTextLine(pageContentStreamNumber, "Konklusion", 14, 50, 650);

            singleTextLine(pageContentStreamNumber, "Lokale", 10, 50, 600);
            singleTextLine(pageContentStreamNumber, "Anbefalinger", 10, 200, 600);

            //Closes the content creation for Page 5
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Setup of Page 6
    public void page6Setup(String pdfName, String picturePath, String imgFolderPath, String savePath, PDDocument doc) {

        //Creates a new page Object
        PDPage pageNumberTitel = new PDPage();

        //Adds the new page to the .doc
        doc.addPage(pageNumberTitel);
        try {

            //Creates a new PDPageContentStream object,
            //which consist of a PDDocument object and PDPAge object
            PDPageContentStream pageContentStreamNumber = new PDPageContentStream(doc, pageNumberTitel);

            //Method that writes and places the default information that is required for each page of the PDF document.
            defaultNewPageSetup(pageContentStreamNumber, imgFolderPath, pdfName);

            //Needs real user input
            singleTextLineWithUserInput(pageContentStreamNumber, "Bygningsgennemgang er fortaget af", /*INPUT HER*/ "The Ceo" + " , Polygon", 10, 50, 650);

            //Needs real user input
            singleTextLineWithUserInput(pageContentStreamNumber, "i samarbejde med ", /*INPUT HER*/ "The other Ceo" + " (bygningsansvarlig).", 10, 50, 630);

            //Writes and places the text-line "Bygningen er katagoriseret som"
            singleTextLine(pageContentStreamNumber, "Bygningen er katagoriseret som", 14, 50, 600);

            //Writes and places the text-line ""Tilstand"
            singleTextLine(pageContentStreamNumber, "Tilstand", 12, 50, 580);

            //Writes and places the text-line ""Tilstandsgrad 1"
            singleTextLine(pageContentStreamNumber, "Tilstandsgrad 1", 10, 50, 560);
            //Places the "underline.jpg"-image, as an underline for the "Tilstandsgrad 1"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 555, 70, 2);
            //Writes and places the text-line ""God Tilstand"
            singleTextLine(pageContentStreamNumber, "God Tilstand", 10, 50, 545);

            //Writes and places the text-line ""Tilstandsgrad2"
            singleTextLine(pageContentStreamNumber, "Tilstandsgrad 2", 10, 50, 520);
            //Places the "underline.jpg"-image, as an underline for the "Tilstandsgrad 2"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 515, 70, 2);
            //Writes and places the text-line ""Middel Tilstand"
            singleTextLine(pageContentStreamNumber, "Middel Tilstand", 10, 50, 505);

            //Writes and places the text-line ""Tilstandsgrad3"
            singleTextLine(pageContentStreamNumber, "Tilstandsgrad 3", 10, 50, 480);
            //Places the "underline.jpg"-image, as an underline for the "Tilstandsgrad 3"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 475, 70, 2);
            //Writes and places the text-line ""Dårlig Tilstand"
            singleTextLine(pageContentStreamNumber, "Dårlig Tilstand", 10, 50, 465);

            //Writes and places the text-line "Beskrivelse af bygningen"
            singleTextLine(pageContentStreamNumber, "Beskrivelse af bygningen", 10, 200, 580);

            //God Tilstand
            singleTextLine(pageContentStreamNumber, "Der er ingen problemer med bygningen; ", 8, 140, 560);
            singleTextLine(pageContentStreamNumber, "Bygningens funktion er uden problemer", 8, 140, 550);

            //Middel Tilstand
            singleTextLine(pageContentStreamNumber, "Der er slid og skader på bygningen eller risiko for potentielle problemer med bygningen.", 8, 140, 520);
            singleTextLine(pageContentStreamNumber, "Bygningens funktion er nedsat, eller der er risiko for, at funktionen bliver nedsat.", 8, 140, 510);

            //Dårlig tilstand
            singleTextLine(pageContentStreamNumber, "Der er problemer med bygningen.", 8, 140, 480);
            singleTextLine(pageContentStreamNumber, "Bygningen er begyndt at forfalde, har defekte komponenter, er nedbrudt eller bør udskiftes", 8, 140, 470);
            singleTextLine(pageContentStreamNumber, "Bygningens funktion er nedsat, eller bygningen er næsten eller helt ubrulig.", 8, 140, 460);

            singleTextLine(pageContentStreamNumber, "Tilstandsgrad", 12, 515, 580);

            //Skal bruges en boolean at styre sig efter
            //Sets the checkbox for "God Tilstand"
            checkBoxImg(true, imgFolderPath, pageContentStreamNumber, 550, 560, 7, 7);
            //Sets the Checkbox for "Middel Tilstand"
            checkBoxImg(true, imgFolderPath, pageContentStreamNumber, 550, 520, 7, 7);
            //Sets the checkbox for "Dårlig Tilstand"
            checkBoxImg(true, imgFolderPath, pageContentStreamNumber, 550, 480, 7, 7);

            //Writes the Terms of Use (?)
            singleTextLine(pageContentStreamNumber, "Denne rapport og bygningsgennemgang er lavet for at klarlægge umiddelbare visuelle problemstillinger.", 8, 50, 400);
            singleTextLine(pageContentStreamNumber, "Vores formål er at sikre, at bygningens anvendelse kan opretholdes", 8, 50, 390);
            singleTextLine(pageContentStreamNumber, "Vi udbedrer ikke skader som en del af bygningsgennemgangen/rapporten.", 8, 50, 380);
            singleTextLine(pageContentStreamNumber, "Gennemgangen af bygningen indeholder ikke fugtmålinger af hele bygningen,", 8, 50, 370);
            singleTextLine(pageContentStreamNumber, "men vi kan foretage fugtscanninger enkelte steder i bygningen, hvis vi finder det nødvendigt.", 8, 50, 360);
            singleTextLine(pageContentStreamNumber, "Hvis vi finder kritiske områder i bygningen vil vi fremlægge anbefalinger angående yderligere tiltag så som yderligere undersøgelser,", 8, 50, 350);
            singleTextLine(pageContentStreamNumber, "reparationer eller bygningsopdateringer.", 8, 50, 340);
            singleTextLine(pageContentStreamNumber, "Bemærk at vi skal have adgang til hele bygningen for at kunne udføre en fuld gennemgang", 8, 50, 330);
            singleTextLine(pageContentStreamNumber, "(dette inkluderer adgang til tag, tagrum, kælder, krybekælder eller andre aflukkede områder). ", 8, 50, 320);
            singleTextLine(pageContentStreamNumber, "Denne bygningsgennemgang er ikke-destruktiv. Hvis der skal laves destruktive indgreb, ", 8, 50, 310);
            singleTextLine(pageContentStreamNumber, "skal dette først godkendes af de bygningsansvarlige.", 8, 50, 300);
            singleTextLine(pageContentStreamNumber, "Destruktive indgreb er ikke en del af denne rapport eller bygningsgennemgang. ", 8, 50, 290);
            singleTextLine(pageContentStreamNumber, "Den bygningsansvarlige skal udlevere plantegning over bygningen inden bygningsgennemgangen kan foretages. ", 8, 50, 260);

            //Closes the content creation for Page 6
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Method to save the PDF document 
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

    //Method to insert .jpg image
    public void insertJPGImage(PDPageContentStream content, String imgFolderPath, String imgName, int xCoordinate, int yCoordinate, int imgWidth, int imgHeight) {

        try {
            PDImageXObject underLineString = null;
            underLineString = PDImageXObject.createFromFile(imgFolderPath + imgName, doc);
            content.drawXObject(underLineString, xCoordinate, yCoordinate, imgWidth, imgHeight);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Method that writes and places the Page number
    public void pageNumber(PDPageContentStream content) {
        try {

            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(550, 25);
            content.drawString("" + pageNumber);
            content.endText();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Method that writes and places the reportnumber
    public void reportNumber(PDPageContentStream content) {

        //
        try {

            //Writes and places the Rapport Nr.
            //Start writting text
            content.beginText();
            //Sets the font and text size
            content.setFont(fontHel, 10);
            //Set Coordinates for the Rapport Number Text
            content.moveTextPositionByAmount(50, 680);
            //Write the "Rapport nr:." text
            content.drawString("Rapport nr.: ");
            //End writting text
            content.endText();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //Method that writes and places the default information that is required for each page of the PDF document.
    public void defaultNewPageSetup(PDPageContentStream content, String imgFolderPath, String pdfName) {

        reportNumber(content);
        pdfDocName(content, pdfName);
        pageNumber(content);
        insertJPGImage(content, imgFolderPath, "polygon.jpg", 50, 690, 150, 30);

    }

    //Method for the user to write the name of the PDF Document.
    //The name will be displayed in the PDF document and will also be the pdfdocuments filename.
    public void pdfDocName(PDPageContentStream content, String pdfName) {
        try {

            //Begin writting text
            content.beginText();
            //Where to place the text (X , Y)
            content.moveTextPositionByAmount(50, 25);
            //Selects the font and textsize to be written with.
            content.setFont(fontHelB, 8);
            //Write the text
            content.drawString("PDF Navn: " + pdfName);

            //Stop writting
            content.endText();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Method for handling a user selected/uploaded picture.
    public void userPicture(String picturePath, String imgFolderPath, PDPageContentStream content, int xCoordinate, int yCoordinate, int imgWidth, int imgHeight) {

        try {

            //Creates the picture object for later use.
            PDImageXObject userBygning = null;

            //If no picture has been uploaded by the user, the PDF will take and use a specified default picture
            if (picturePath.equals("")) {

                //Default picture
                userBygning = PDImageXObject.createFromFile(imgFolderPath + "whouse.jpg", doc);

//If the user has decided to uploade a picure
            } else {
                //User picture
                userBygning = PDImageXObject.createFromFile("" + picturePath, doc);
            }
            //Takes 
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
    //Method to handle the "checked"- and "unchecked"-checkbox image
    //Also req. coordinates as paramters, for where the boxes needs to be placed.
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

    //Method specifically for the Page3
    //Should only (!?) be displayed, if there are any "bemærkninger" or damage happened to the room
    public void page3DamageAndRepair(boolean roomDamage, PDPageContentStream content, String imgFolderPath,
            boolean moist, boolean rotAndMushroom, boolean mold, boolean fire, boolean otherDamage) {

        //Text to always be displayed
        singleTextLine(content, "Ja", 10, 200, 625);
        singleTextLine(content, "Nej", 10, 228, 625);

        //If "Nej"
        if (roomDamage == false) {
            checkBoxImg(roomDamage, imgFolderPath, content, 215, 625, 7, 7);

            checkBoxImg(true, imgFolderPath, content, 250, 625, 7, 7);
            //If "Ja"
        } else {

            checkBoxImg(roomDamage, imgFolderPath, content, 215, 625, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 250, 625, 7, 7);

            //Input from user 
            singleTextLineWithUserInput(content, "Hvornår? ", /*INPUT HER*/ "Test31", 10, 50, 590);

            //Input from user
            singleTextLineWithUserInput(content, "Hvor? ", /*INPUT HER*/ "Test det er sket her", 10, 200, 590);

            //Input from user
            singleTextLineWithUserInput(content, "Hvad er der sket?", /*INPUT HER*/ "Katten legede med sin kugle under border og den trillede væk...", 10, 50, 560);

            //Input from user
            singleTextLineWithUserInput(content, "Hvad er der repareret", /*INPUT HER*/ "Katten fik en ny bold og en lillebror", 10, 50, 540);

            //Input from user
            singleTextLine(content, "Skade", 10, 50, 520);

            singleTextLine(content, "Fugt", 10, 60, 505);
            singleTextLine(content, "Råd og svamp", 10, 140, 505);
            singleTextLine(content, "Skimmel", 10, 260, 505);
            singleTextLine(content, "Brand", 10, 60, 490);
            singleTextLine(content, "Andet", 10, 60, 475);

            //Sets the checkBoxImage
            if (moist == true) {
                checkBoxImg(moist, imgFolderPath, content, 50, 505, 7, 7);
            } else {
                checkBoxImg(moist, imgFolderPath, content, 50, 505, 7, 7);
            }
            //Sets the checkBoxImage
            if (rotAndMushroom == true) {
                checkBoxImg(rotAndMushroom, imgFolderPath, content, 130, 505, 7, 7);
            } else {
                checkBoxImg(rotAndMushroom, imgFolderPath, content, 130, 505, 7, 7);
            }
            //Sets the checkBoxImage
            if (mold == true) {
                checkBoxImg(mold, imgFolderPath, content, 250, 505, 7, 7);
            } else {
                checkBoxImg(mold, imgFolderPath, content, 250, 505, 7, 7);
            }
            //Sets the checkBoxImage
            if (fire == true) {
                checkBoxImg(fire, imgFolderPath, content, 50, 490, 7, 7);
            } else {
                checkBoxImg(fire, imgFolderPath, content, 50, 490, 7, 7);
            }
            //Sets the checkBoxImage
            if (otherDamage == true) {
                checkBoxImg(otherDamage, imgFolderPath, content, 50, 475, 7, 7);
                //needs user input
                singleTextLineWithUserInput(content, "", /*NEEDS USERINPUT*/ "The kat is on freaking fire", 10, 130, 475);
                insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 128, 472, 120, 2);
            } else {
                checkBoxImg(otherDamage, imgFolderPath, content, 50, 475, 7, 7);
            }

        }
    }

    //Add a 'PDFont' parameter at a later point!
    //Method to simplify the creation of text-lines       
    public void singleTextLine(PDPageContentStream content, String textLine, int textSize, int xCoordinate, int yCoordinate) {

        try {

            //Start writting text
            content.beginText();
            //Sets the font and text size
            content.setFont(fontHelB, textSize);
            //Set Coordinates for the line of text
            content.moveTextPositionByAmount(xCoordinate, yCoordinate);
            //The text to be written
            content.drawString(textLine);
            //End writting text
            content.endText();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //Add a 'PDFont' parameter at a later point!
    //Method to simplify the creation of text-lines, 
    //which also contains user input.
    public void singleTextLineWithUserInput(PDPageContentStream content, String textLine, String userInput, int textSize, int xCoordinate, int yCoordinate) {

        try {

            //Start writting text
            content.beginText();
            //Sets the font and text size
            content.setFont(fontHelB, textSize);
            //Set Coordinates for the first line of text
            content.moveTextPositionByAmount(xCoordinate, yCoordinate);
            //Text + the userinput
            content.drawString(textLine + ": " + userInput);
            //End writting text
            content.endText();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Sets the checkboxes for Page 2:
    //"Tag" (Bemærkning, Ingen bemærkning, Billede) and 
    //Ydervægge (Bemærkning, Ingen bemærkning, Billede)
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

    //Method specifically for Page 3
    //Setups various checkboxes in Page 3, based on wether or not there has been performed a MoistScan
    public void checkBoxesPage3MoistScan(boolean moistScan, PDPageContentStream content, String moistScanned, String moistMeasurePoint, String imgFolderPath) {

        //Has a Moistscan been performed?
        //Hvad er målepunkt?
        //Hvad er fugtscanning?
        //Bemærkning?
        singleTextLine(content, "Er der fortaget fugtscanning?", 10, 50, 400);

        //To be displayed if NO moistScan has been performed
        if (moistScan == false) {
            singleTextLine(content, "Ja", 10, 250, 400);
            checkBoxImg(moistScan, imgFolderPath, content, 240, 400, 7, 7);
            singleTextLine(content, "Nej", 10, 280, 400);
            checkBoxImg(true, imgFolderPath, content, 270, 400, 7, 7);

            //To be displayed if moistScan has been performed
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
    //NEEDS USER INPUT
    //METHOD NEEDS BOOLEAN PARAMTERS!
    public void checkBoxesPage4Walkthrough(PDPageContentStream content, String imgFolderPath) {

        singleTextLine(content, "Gennemgang af lokalet", 12, 50, 640);
        singleTextLine(content, "Bemærkning", 10, 335, 610);
        singleTextLine(content, "Ingen Bemærkning", 10, 420, 610);
        singleTextLine(content, "Billede", 10, 540, 610);


        //Test of method
        wallWalkthrough(content, imgFolderPath, false, false, "it works!",
                600, 596, 590, 600, 600, 0);
        ceilingWalkthrough(content, imgFolderPath, true, true, "it really works!",
                520, 516, 510, 520, 520, 0);
        floorWalkthrough(content, imgFolderPath, false, false, "Holy hell!",
                440, 436, 430, 440, 440, 0);
        windowsWalkthrough(content, imgFolderPath, true, false, "When the night...",
                360, 356, 350, 360, 360, 0);
        doorWalkthrough(content, imgFolderPath, true, true, "... has come", 
                280, 276, 270, 280, 280, 0);
        
        otherWalkthrough(content, imgFolderPath, true, false, "Jambo jambo!",
                200, 196, 190, 200, 200, 0);
    }

    //int wallTitelXCoordinate, int wallTitelYCoordinate
    //int underlineJPGXCoordinate, int underlineJPGYCoordinate,
    //int wallNotesCheckBoxImgXCoordinate, int wallNotesCheckBoxImgYCoordinate
    //int pictureCheckBoxImgXCoordinate, int pictureCheckBoxBoxImgYCoordinate
    //int pictureIMGXCoordinate, int pictureIMGYCoordinate
    public void wallWalkthrough(PDPageContentStream content, String imgFolderPath,
            boolean wallNotes, boolean wallPicture, String wallNoteText,
            int wallTitelYCoordinate, int underlineJPGYCoordinate,
            int wallNoteTextYCoordinate, int wallNotesCheckBoxImgYCoordinate,
            int pictureCheckBoxImgYCoordinate, int pictureIMGYCoordinate
    ) {
        //Vægge
        // singleTextLine(content, "Vægge", 10, 50, 600);
        singleTextLine(content, "Vægge", 10, 50, wallTitelYCoordinate);

        //Underlinde-jpg
        //insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 596, 37, 2);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, underlineJPGYCoordinate, 37, 2);
        //Bemærkning / Ingen Bemærkning
        if (wallNotes == true) {
            //set "Bermærkninger" true
            //singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ "teeeeeeeeest", 8, 50, 590);
            singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ "" + wallNoteText, 8, 50, wallNoteTextYCoordinate);

            //checkBoxImg(true, imgFolderPath, content, 360, 600, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 360, wallNotesCheckBoxImgYCoordinate, 7, 7);

            //Set "ingen bemærkninger" false + 100!
            //checkBoxImg(false, imgFolderPath, content, 460, 600, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 460, wallNotesCheckBoxImgYCoordinate, 7, 7);

        } else if (wallNotes != true || wallNotes == false) {
            //set "Bermærkninger" false
            //checkBoxImg(false, imgFolderPath, content, 360, 600, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 360, wallNotesCheckBoxImgYCoordinate, 7, 7);
            //Set "ingen bemærkninger" true + 100!
            //checkBoxImg(true, imgFolderPath, content, 460, 600, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 460, wallNotesCheckBoxImgYCoordinate, 7, 7);
        }

        //Billede
        if (wallPicture == true) {
            //set "Picture" true

            //checkBoxImg(true, imgFolderPath, content, 553, 600, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
            //insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, 0, 50, 25);
            insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, pictureIMGYCoordinate, 50, 25);
        } else if (wallPicture != true || wallPicture == false) {
            // set "Picture" false
            //checkBoxImg(false, imgFolderPath, content, 553, 600, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
        }
    }
    
    public void ceilingWalkthrough(PDPageContentStream content, String imgFolderPath,
            boolean ceilingNotes, boolean ceilingPicture, String ceilingNoteText,
            int ceilingTitelYCoordinate, int underlineJPGYCoordinate,
            int celingNoteTextYCoordinate, int ceilingNotesCheckBoxImgYCoordinate,
            int pictureCheckBoxImgYCoordinate, int pictureIMGYCoordinate){
        //Loft
        //singleTextLine(content, "Loft", 10, 50, 520);
        singleTextLine(content, "Loft", 10, 50, ceilingTitelYCoordinate);
        //Underlinde-jpg
        //insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 516, 20, 2);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, underlineJPGYCoordinate, 20, 2);
        //Bemærkning / Ingen Bemærkning
        if (ceilingNotes == true) {
            //set "Bermærkninger" true
           // singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/"" + ceilingNoteText, 8, 50, 510);
            singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/"" + ceilingNoteText, 8, 50, celingNoteTextYCoordinate);

            //checkBoxImg(true, imgFolderPath, content, 360, 520, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 360,ceilingNotesCheckBoxImgYCoordinate, 7, 7);

            //Set "ingen bemærkninger" false
           // checkBoxImg(false, imgFolderPath, content, 460, 520, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 460, ceilingNotesCheckBoxImgYCoordinate, 7, 7);

        } else if (ceilingNotes != true || ceilingNotes == false) {
            //set "Bermærkninger" false
            //checkBoxImg(false, imgFolderPath, content, 360, 520, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 360, ceilingNotesCheckBoxImgYCoordinate, 7, 7);
            //Set "ingen bemærkninger" true
            //checkBoxImg(true, imgFolderPath, content, 460, 520, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 460, ceilingNotesCheckBoxImgYCoordinate, 7, 7);
        }

        //Billede
        if (ceilingPicture == true) {
            //set "Picture" true
            //checkBoxImg(true, imgFolderPath, content, 553, 520, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
            //insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, 0, 0, 0);
            insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, pictureIMGYCoordinate, 0, 0);

        } else if (ceilingPicture != true || ceilingPicture == false) {
            // set "Picture" false
            //checkBoxImg(false, imgFolderPath, content, 553, 520, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
        }
    }
    
    public void floorWalkthrough(PDPageContentStream content, String imgFolderPath,
            boolean floorNotes, boolean floorPicture, String floorNoteText,
            int floorTitelYCoordinate, int underlineJPGYCoordinate,
            int floorNoteTextYCoordinate, int floorNotesCheckBoxImgYCoordinate,
            int pictureCheckBoxImgYCoordinate, int pictureIMGYCoordinate){
        //Gulv
        //singleTextLine(content, "Gulv", 10, 50, 440);
        singleTextLine(content, "Gulv", 10, 50, floorTitelYCoordinate);
        //Underlinde-jpg
        //insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 436, 23, 2);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, underlineJPGYCoordinate, 23, 2);
        //Bemærkning / Ingen Bemærkning
        if (floorNotes == true) {
            //set "Bermærkninger" true
            //singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ "teeeeeeeeest", 8, 50, 430);
            singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ ""+ floorNoteText, 8, 50, floorNoteTextYCoordinate);

            //checkBoxImg(true, imgFolderPath, content, 360, 440, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 360, floorNotesCheckBoxImgYCoordinate, 7, 7);

            //Set "ingen bemærkninger" false
            //checkBoxImg(false, imgFolderPath, content, 460, 440, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 460, floorNotesCheckBoxImgYCoordinate, 7, 7);

        } else if (floorNotes != true || floorNotes == false) {
            //set "Bermærkninger" false
            //checkBoxImg(false, imgFolderPath, content, 360, 440, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 360, floorNotesCheckBoxImgYCoordinate, 7, 7);
            //Set "ingen bemærkninger" true
            //checkBoxImg(true, imgFolderPath, content, 460, 440, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 460, floorNotesCheckBoxImgYCoordinate, 7, 7);
        }
        //Billede
        if (floorPicture == true) {
            //set "Picture" true
            //checkBoxImg(true, imgFolderPath, content, 553, 440, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
            //insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, 0, 0, 0);
            insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0,  pictureIMGYCoordinate, 0, 0);
        } else if (floorPicture != true || floorPicture == false) {
            // set "Picture" false
            //checkBoxImg(false, imgFolderPath, content, 553, 440, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
        }
        
        
    }
    
    public void windowsWalkthrough(PDPageContentStream content, String imgFolderPath,
            boolean windowNotes, boolean windowPicture, String windowNoteText,
            int windowTitelYCoordinate, int underlineJPGYCoordinate,
            int windowNoteTextYCoordinate, int windowNotesCheckBoxImgYCoordinate,
            int pictureCheckBoxImgYCoordinate, int pictureIMGYCoordinate){
        //Vinduer
        //singleTextLine(content, "Vinduer", 10, 50, 360);
        singleTextLine(content, "Vinduer", 10, 50, windowTitelYCoordinate);
        //Underlinde-jpg
        //insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 356, 45, 2);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, underlineJPGYCoordinate, 45, 2);
        if (windowNotes == true) {
            //set "Bermærkninger" true
           // singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ "teeeeeeeeest", 8, 50, 350);
            singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ ""+windowNoteText, 8, 50, windowNoteTextYCoordinate);

            //checkBoxImg(true, imgFolderPath, content, 360, 360, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 360, windowNotesCheckBoxImgYCoordinate, 7, 7);

            //Set "ingen bemærkninger" false
           // checkBoxImg(false, imgFolderPath, content, 460, 360, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 460, windowNotesCheckBoxImgYCoordinate, 7, 7);

        } else if (windowNotes != true || windowNotes == false) {
            //set "Bermærkninger" false
           // checkBoxImg(false, imgFolderPath, content, 360, 360, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 360, windowNotesCheckBoxImgYCoordinate, 7, 7);
            //Set "ingen bemærkninger" true
           // checkBoxImg(true, imgFolderPath, content, 460, 360, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 460, windowNotesCheckBoxImgYCoordinate, 7, 7);
        }
        //Billede
        if (windowPicture == true) {
            //set "Picture" true
            //checkBoxImg(true, imgFolderPath, content, 553, 360, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
            //insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, 0, 0, 0);
            insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, pictureIMGYCoordinate, 0, 0);
        } else if (windowPicture != true || windowPicture == false) {
            // set "Picture" false
            //checkBoxImg(false, imgFolderPath, content, 553, 360, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
        }
    }
    
    public void doorWalkthrough(PDPageContentStream content, String imgFolderPath,
            boolean doorNotes, boolean doorPicture, String doorNoteText,
            int doorTitelYCoordinate, int underlineJPGYCoordinate,
            int doorNoteTextYCoordinate, int doorNotesCheckBoxImgYCoordinate,
            int pictureCheckBoxImgYCoordinate, int pictureIMGYCoordinate){
        
         //Døre
        //singleTextLine(content, "Døre", 10, 50, 280);
        singleTextLine(content, "Døre", 10, 50, doorTitelYCoordinate);
        //Underlinde-jpg
        //insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 276, 23, 2);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, underlineJPGYCoordinate, 23, 2);
        if (doorNotes == true) {
            //set "Bermærkninger" true
           // singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ "teeeeeeeeest", 8, 50, 270);
            singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ "" + doorNoteText, 8, 50, doorNoteTextYCoordinate);

           // checkBoxImg(true, imgFolderPath, content, 360, 280, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 360, doorNotesCheckBoxImgYCoordinate, 7, 7);

            //Set "ingen bemærkninger" false
            //checkBoxImg(false, imgFolderPath, content, 460, 280, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 460, doorNotesCheckBoxImgYCoordinate, 7, 7);

        } else if (doorNotes != true || doorNotes == false) {
            //set "Bermærkninger" false
           // checkBoxImg(false, imgFolderPath, content, 360, 280, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 360, doorNotesCheckBoxImgYCoordinate, 7, 7);
            //Set "ingen bemærkninger" true
            //checkBoxImg(true, imgFolderPath, content, 460, 280, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 460, doorNotesCheckBoxImgYCoordinate, 7, 7);
        }
        //Billede
        if (doorPicture == true) {
            //set "Picture" true
           // checkBoxImg(true, imgFolderPath, content, 553, 280, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
            //insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, 0, 0, 0);
            insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, pictureIMGYCoordinate, 0, 0);
        } else if (doorPicture != true || doorPicture == false) {
            // set "Picture" false
            //checkBoxImg(false, imgFolderPath, content, 553, 280, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
        }
    }
    
    public void otherWalkthrough(PDPageContentStream content, String imgFolderPath,
            boolean otherNotes, boolean otherPicture, String otherNoteText,
            int otherTitelYCoordinate, int underlineJPGYCoordinate,
            int otherNoteTextYCoordinate, int otherNotesCheckBoxImgYCoordinate,
            int pictureCheckBoxImgYCoordinate, int pictureIMGYCoordinate){
        
         //Andet
        //singleTextLine(content, "Andet", 10, 50, 280);
        singleTextLine(content, "Andet", 10, 50, otherTitelYCoordinate);
        //Underlinde-jpg
        //insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, 276, 23, 2);
        insertJPGImage(content, imgFolderPath, "underLineJPG.jpg", 50, underlineJPGYCoordinate, 23, 2);
        if (otherNotes == true) {
            //set "Bermærkninger" true
           // singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ "teeeeeeeeest", 8, 50, 270);
            singleTextLineWithUserInput(content, imgFolderPath, /*NEEDS USER INPUT*/ "" + otherNoteText, 8, 50, otherNoteTextYCoordinate);

           // checkBoxImg(true, imgFolderPath, content, 360, 280, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 360, otherNotesCheckBoxImgYCoordinate, 7, 7);

            //Set "ingen bemærkninger" false
            //checkBoxImg(false, imgFolderPath, content, 460, 280, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 460, otherNotesCheckBoxImgYCoordinate, 7, 7);

        } else if (otherNotes != true || otherNotes == false) {
            //set "Bermærkninger" false
           // checkBoxImg(false, imgFolderPath, content, 360, 280, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 360, otherNotesCheckBoxImgYCoordinate, 7, 7);
            //Set "ingen bemærkninger" true
            //checkBoxImg(true, imgFolderPath, content, 460, 280, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 460, otherNotesCheckBoxImgYCoordinate, 7, 7);
        }
        //Billede
        if (otherPicture == true) {
            //set "Picture" true
           // checkBoxImg(true, imgFolderPath, content, 553, 280, 7, 7);
            checkBoxImg(true, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
            //insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, 0, 0, 0);
            insertJPGImage(content, imgFolderPath, /*IMG NAME HERE*/ imgFolderPath, 0, pictureIMGYCoordinate, 0, 0);
        } else if (otherPicture != true || otherPicture == false) {
            // set "Picture" false
            //checkBoxImg(false, imgFolderPath, content, 553, 280, 7, 7);
            checkBoxImg(false, imgFolderPath, content, 553, pictureCheckBoxImgYCoordinate, 7, 7);
        }
    }

    //ONLY FOR TESTING THE SAVE MECHANIC AND TEXT BOX!!! 
    //TO BE REMOVED UPON PROGRAM COMPLETION!
    public void testBlank(String pdfName) throws Exception {

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

