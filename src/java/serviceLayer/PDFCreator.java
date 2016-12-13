package serviceLayer;

import dataAccessLayer.mappers.BuildingMapper;
import dataAccessLayer.mappers.interfaces.BuildingMapperInterface;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import serviceLayer.controllers.DataController;
import serviceLayer.controllers.UserController;
import serviceLayer.controllers.interfaces.DataControllerInterface;
import serviceLayer.controllers.interfaces.UserControllerInterface;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.DamageRepair;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Issue;
import serviceLayer.entities.MoistureInfo;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;
import serviceLayer.exceptions.PolygonException;

/**
 * Class that generates a PDF Document.
 *
 */
public class PDFCreator {

    private DataControllerInterface datCtrl = new DataController();
    private BuildingMapperInterface buildCtrl = new BuildingMapper();
    private UserControllerInterface usrCtrl = new UserController();

    private User technician = null;
    private User customer = null;

    private Building building = new Building();
    private Healthcheck healthcheck = new Healthcheck();
    private ArrayList<Room> roomList = new ArrayList();
    private ArrayList<DamageRepair> dmgRepair = new ArrayList();
    private ArrayList<Area> areaList = new ArrayList();
    private ArrayList<Issue> issueList = new ArrayList();
    private ArrayList<MoistureInfo> moistList = new ArrayList();

    private String imgFolderPath;
    private String pdfName;
    private String buildingResponsible;

    //sourceFolder sf = new sourceFolder();
    private PDDocument doc = new PDDocument();

    //Creates various text font objects
    private PDFont fontHelB = PDType1Font.HELVETICA_BOLD;
    private PDFont fontHel = PDType1Font.TIMES_ROMAN;

    //Sets the first page number
    private int pageNumber = 1;
    //Sets the first page number to be 
    private String pageNumberTitel = "page" + pageNumber;
    private String pageContentStreamNumber = "content" + pageNumber;

    /**
     * The actual method that generates and creates the PDF.
     *
     * @param technician User type, this case a technician.
     * @param customer User type, this case a customer.
     * @param healthcheckId int that identifies the healthcheck to be retrived
     * @param buildingId int that identifies the building to be retrived
     * @param buildingResponsible String that contains the name of the
     * technician who signed off on the PDF
     * @param condition String that contains the type of condition the
     * technician has determined the building to be in
     * @param imgFolderPath String that contains the root path to the images on
     * the website
     */
    public void createPDF(User technician, User customer, int healthcheckId, int buildingId, String buildingResponsible, String condition, String imgFolderPath) throws PolygonException {

        try {

            areaList = buildCtrl.getAreas(buildingId);
            roomList = buildCtrl.getRooms(buildingId);
            building = buildCtrl.getBuilding(buildingId);
            issueList = buildCtrl.getHealthcheckIssues(healthcheckId);
            dmgRepair = buildCtrl.getAllDamageRepairs();
            moistList = buildCtrl.getAllMoistureMeasurements();
            this.customer = customer;
            this.technician = technician;
            this.imgFolderPath = imgFolderPath;
            this.buildingResponsible = buildingResponsible;

            //MOISTLIST
            pdfName = building.getName() + "ID#" + building.getbuildingId();

            //Create front page.
            frontPage();
            pageNumber++;

            if (areaList.size() != 0) {
                //Outer loop is area, cos it has rooms. 
                for (int a = 0; a < areaList.size(); a++) {

                    if (hasIssue(0, areaList.get(a).getArea_id())) {

                        areaWalkthrough(areaList.get(a));
                        pageNumber++;

                    }

                }
            }

            if (roomList.size() != 0) {

                for (int i = 0; i < roomList.size(); i++) {

                    if (hasIssue(1, roomList.get(i).getRoom_id())) {

                        roomWalkthrough(areaList.get(1), roomList.get(i));
                        pageNumber++;
                        
                    }

                    //Dmg report
                    for (int k = 0; k < dmgRepair.size(); k++) {

                        if (dmgRepair.get(k).getRoomId() == roomList.get(i).getRoom_id()) {

                            damageReport(roomList.get(i), areaList.get(1), dmgRepair.get(k));
                            pageNumber++;

                        }

                    }

                    for (int k = 0; k < moistList.size(); k++) {

                        if (moistList.get(k).getRoomId() == roomList.get(i).getRoom_id()) {

                            roomMoistReport(areaList.get(1), roomList.get(i), moistList.get(k));
                            pageNumber++;

                        }

                    }

                }

            }

            lastPage();
            pageNumber++;

            savePDF(pdfName, doc);
            
        } catch (Exception e) {
            
            throw new PolygonException("Exception:@PDFCreator.createPDF:" + e.getMessage());
            
        }

    }

    /**
     * Setup of the front page
     */
    public void frontPage() {

        //Registers the time and date for the PDF document
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
            pageContentStreamNumber.drawString("" + building.getName());
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
            pageContentStreamNumber.drawString("" + building.getAddress());
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
            pageContentStreamNumber.drawString("" + building.getPostcode());
            //Coordinates for the next line of text,
            //which are offset from the previous line of text
            pageContentStreamNumber.newLineAtOffset(45, 0);
            //Third line of text
            pageContentStreamNumber.drawString("" + building.getCity());
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
            //userPicture(picturePath, imgFolderPath, pageContentStreamNumber, 125, 225, 375, 215);
            //Writes and places the text line "Generel information om bygning"           
            singleTextLine(pageContentStreamNumber, "Generel information om bygningen:", 12, 50, 200);

            //Writes and places the text line "Bygge år"
            singleTextLineWithUserInput(pageContentStreamNumber, "Bygge år", "" + building.getConstruction_year(), 10, 50, 175);

            //Creates a image from a file and places it.
            //Underline for the "Bygge År "           
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 98, 172, 25, 2);

            //Writes and places the text line "Bygningens Areal"
            singleTextLineWithUserInput(pageContentStreamNumber, "Bygningens Areal", building.getSqm() + " Kvadrat Meter", 10, 50, 145);

            //Creates a image from a file and places it.
            //Underline for the "Bygningens Areal"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 140, 142, 120, 2);

            //Writes and places the text line "Hvad bruges bygningen til / Hvad har bygningen været brugt til?" + the users input
            singleTextLineWithUserInput(pageContentStreamNumber, "Hvad bruges bygningen til / Hvad har bygningen været brugt til?", building.getPurpose(), 10, 50, 120);

            //Creates a image from a file and places it.
            //Underline for the "Hvad bruges bygningen til / Hvad har bygningen været brugt til?"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 358, 117, 120, 2);

            //closes the page for anymore content to be written.
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Setup of the page that genereates the damage report part of the PDF
     *
     * @param room Room type that contains the information regarding the specific room
     * @param area Area type that contains the information regarding the specific area
     * @param dmgRepair DamageRepair type that contains the information regarding 
     * the specific damage done to the room
     */

    public void damageReport(Room room, Area area, DamageRepair dmgRepair) {

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
            singleTextLineWithUserInput(pageContentStreamNumber, "Lokale", room.getName(), 10, 50, 665);

            //NEEDS DYNAMIC USER INPUT!!! "Ceo's Kontor = Room Name
            singleTextLineWithUserInput(pageContentStreamNumber, "Område", area.getName(), 10, 50, 645);

            //NEEDS A F*CKING NEW NAME!.... AND DYNAMIC USER INPUT!!!
            //checkIfPage3NeedsPopulation(true, false, true, pageContentStreamNumber, imgFolderPath);
            //if localNotes is true, set noLocalNotes to false
            singleTextLine(pageContentStreamNumber, "Skade og Reparation", 12, 50, 622);

            singleTextLine(pageContentStreamNumber, "Har der været skade i lokalet?", 10, 50, 605);

            //Input from user 
            singleTextLineWithUserInput(pageContentStreamNumber, "Hvornår? ", dmgRepair.getDate_occurred().toString(), 10, 50, 590);

            //Input from user
            singleTextLineWithUserInput(pageContentStreamNumber, "Hvor? ", dmgRepair.getLocation(), 10, 50, 575);

            //Input from user
            singleTextLineWithUserInput(pageContentStreamNumber, "Hvad er der sket?", dmgRepair.getDetails(), 10, 50, 560);

            //Input from user
            singleTextLineWithUserInput(pageContentStreamNumber, "Hvad er der repareret", dmgRepair.getWork_done(), 10, 50, 540);

            //Input from user
            singleTextLine(pageContentStreamNumber, "Skade TYPE: ", 10, 50, 520);
            singleTextLine(pageContentStreamNumber, dmgRepair.getType().toString(), 10, 125, 520);

            // moistPageData(true, pageContentStreamNumber, imgFolderPath, false, false, false, false, false);
            //Closes the content creation for Page 3
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Setup of the page that genereates the moist scan part of the PDF
     *
     * @param area Area type that contains the information regarding the specific area
     * @param room Room type that contains the information regarding the specific room
     * @param moistInfo MoistInfo type that contains the information regarding the specific moist Scan
     */
    public void roomMoistReport(Area area, Room room, MoistureInfo moistInfo) {

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
            singleTextLineWithUserInput(pageContentStreamNumber, "Lokale", room.getName(), 10, 50, 665);

            //NEEDS DYNAMIC USER INPUT!!! "Ceo's Kontor = Room Name
            singleTextLineWithUserInput(pageContentStreamNumber, "Område", area.getName(), 10, 50, 645);

            singleTextLine(pageContentStreamNumber, "FugtScanning", 12, 50, 600);

            singleTextLineWithUserInput(pageContentStreamNumber, "Fugtscanning", "" + moistInfo.getMoistureValue(), 10, 50, 380);
            singleTextLineWithUserInput(pageContentStreamNumber, "Målepunkt", moistInfo.getMeasurePoint(), 10, 250, 380);

            //Closes the content creation
            pageContentStreamNumber.close();

        } catch (Exception e) {

            System.out.println("roomMoist" + e.getMessage());

        }

    }

    /**
     * Setup of the page that genereates the pdf's report about the individual
     * rooms, the room issues and suggested solutions.
     *
     * @param area Area type that contains the information regarding the specific area
     * @param room Room type that contains the information regarding the specific Room
     */
    public void roomWalkthrough(Area area, Room room) {

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
            singleTextLineWithUserInput(pageContentStreamNumber, "Lokale", room.getName() + " " + room.getDescription(), 10, 50, 665);

            //
            singleTextLineWithUserInput(pageContentStreamNumber, "Område", area.getName(), 10, 50, 645);

            singleTextLine(pageContentStreamNumber, "Problem Beskrivelse", 10, 50, 600);
            //Creates a image from a file and places it.
            //Underline for "Lokale"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 596, 100, 2);
            singleTextLine(pageContentStreamNumber, "Problem: " + issueList.get(getIssueId(1, room.getRoom_id())).getDescription(), 10, 50, 580);

            singleTextLine(pageContentStreamNumber, "Anbefalet behandling", 10, 50, 550);
            //Creates a image from a file and places it.
            //Underline for "Anbefalinger"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 546, 100, 2);
            singleTextLine(pageContentStreamNumber, "Anbefaldet: " + issueList.get(getIssueId(1, room.getRoom_id())).getRecommendation(), 10, 50, 530);

            //checkBoxesPage4Walkthrough(pageContentStreamNumber, imgFolderPath);
            //Closes the content creation for Page 4
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println("NONO" + e.getMessage());
        }
    }

    /**
     * Setup of the page that genereates the pdf's report about the individual
     * area, the room issues and suggested solutions.
     *
     * @param area Area type that contains the information regarding the specific area
     */
    public void areaWalkthrough(Area area) {

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

            //
            singleTextLineWithUserInput(pageContentStreamNumber, "Område", area.getName(), 10, 50, 645);

            singleTextLine(pageContentStreamNumber, "Problem Beskrivelse", 10, 50, 600);
            //Creates a image from a file and places it.
            //Underline for "Lokale"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 596, 100, 2);
            singleTextLine(pageContentStreamNumber, "" + issueList.get(getIssueId(0, area.getArea_id())).getDescription(), 10, 50, 580);

            singleTextLine(pageContentStreamNumber, "Anbefalet behandling", 10, 50, 550);
            //Creates a image from a file and places it.
            //Underline for "Anbefalinger"
            insertJPGImage(pageContentStreamNumber, imgFolderPath, "underLineJPG.jpg", 50, 546, 100, 2);
            singleTextLine(pageContentStreamNumber, "" + issueList.get(getIssueId(0, area.getArea_id())).getRecommendation(), 10, 50, 530);

            //checkBoxesPage4Walkthrough(pageContentStreamNumber, imgFolderPath);
            //Closes the content creation for Page 4
            pageContentStreamNumber.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Generates the last and final page of the PDF report!
     */
    //Setup of the last and final Page
    public void lastPage() {

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
            singleTextLineWithUserInput(pageContentStreamNumber, "Bygningsgennemgang er fortaget af", technician.getName() + " , Polygon (Teknikker)", 10, 50, 650);

            //Needs real user input
            singleTextLineWithUserInput(pageContentStreamNumber, "i samarbejde med ", buildingResponsible + " (bygningsansvarlig).", 10, 50, 630);

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

            if (building.getCondition() == Building.condition.GOOD) {

                //Sets the checkbox for "God Tilstand"
                checkBoxImg(true, imgFolderPath, pageContentStreamNumber, 550, 560, 7, 7);

                //Sets the empty checkbox for "Medium Tilstand"
                checkBoxImg(false, imgFolderPath, pageContentStreamNumber, 550, 520, 7, 7);

                //Sets the empty checkbox for "Dårlig Tilstand"
                checkBoxImg(false, imgFolderPath, pageContentStreamNumber, 550, 480, 7, 7);
            } else if (building.getCondition() == Building.condition.MEDIUM) {

                //Sets the Checkbox for "Middel Tilstand"
                checkBoxImg(true, imgFolderPath, pageContentStreamNumber, 550, 520, 7, 7);

                //Sets the empty checkbox for "God Tilstand"
                checkBoxImg(false, imgFolderPath, pageContentStreamNumber, 550, 560, 7, 7);

                //Sets the empty checkbox for "Dårlig Tilstand"
                checkBoxImg(false, imgFolderPath, pageContentStreamNumber, 550, 480, 7, 7);

            } else if (building.getCondition() == Building.condition.POOR) {

                //Sets the checkbox for "Dårlig Tilstand"
                checkBoxImg(true, imgFolderPath, pageContentStreamNumber, 550, 480, 7, 7);

                //Sets the empty checkbox for "God Tilstand"
                checkBoxImg(false, imgFolderPath, pageContentStreamNumber, 550, 560, 7, 7);

                //Sets the empty checkbox for "Medium Tilstand"
                checkBoxImg(false, imgFolderPath, pageContentStreamNumber, 550, 520, 7, 7);
            }

            //Skal bruges en boolean at styre sig efter
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
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that saves the PDF
     *
     * @param pdfName String name of the PDF
     * @param doc PDDOcument object that is generated
     */
    public void savePDF(String pdfName, PDDocument doc) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayInputStream inStream = null;

        try {

            doc.save(output);

            //byte[] -> InputStream
            inStream = new ByteArrayInputStream(output.toByteArray());

            datCtrl.uploadDocument(building.getbuildingId(), pdfName, "pdf", inStream);

        } catch (Exception e) {
            System.out.println("savepdf" + e.getMessage());
        } finally {

            try {
                output.close();
                doc.close();
                inStream.close();

            } catch (IOException ex) {
                System.out.println("save" + ex.getMessage());
            }

        }

    }

    /**
     * Method that handles and inserts images (.jpg) into the PDF
     *
     * @param content PDPageContentStream object that determines which 'page'
     * that needs to be written to
     * @param imgFolderPath String that contains the root path to the images on
     * the website
     * @param imgName String that contains the image.jpg file name
     * @param xCoordinate int that sets the x-coordinate for where to place the image
     * @param yCoordinate int that sets the y-coordinate for where to place the image
     * @param imgWidth int that sets the width of the image
     * @param imgHeight int that sets the height of the image
     */
    //Method to insert .jpg image
    public void insertJPGImage(PDPageContentStream content, String imgFolderPath, String imgName, int xCoordinate, int yCoordinate, int imgWidth, int imgHeight) {

        try {
            PDImageXObject underLineString = null;
            underLineString = PDImageXObject.createFromFile(imgFolderPath + imgName, doc);
            content.drawXObject(underLineString, xCoordinate, yCoordinate, imgWidth, imgHeight);

        } catch (Exception e) {
            System.out.println("inserJpg" + e.getMessage());
        }
    }

    /**
     * Method that writes and places the Page number
     *
     * @param content PDPageContentStream object that determines which 'page'
     * that needs to be written to
     */

    public void pageNumber(PDPageContentStream content) {
        try {

            content.beginText();
            content.setFont(fontHelB, 8);
            content.moveTextPositionByAmount(550, 25);
            content.drawString("" + pageNumber);
            content.endText();

        } catch (Exception e) {
            System.out.println("pagenumber" + e.getMessage());
        }
    }

    /**
     * Method that writes and places the reportnumber
     *
     * @param content PDPageContentStream object that determines which 'page'
     * that needs to be written to
     */

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
            System.out.println("reportnr" + e.getMessage());
        }

    }

    /**
     * Method that writes and places the default information that is required
     * for each page of the PDF document.
     *
     * @param content PDPageContentStream object that determines which 'page'
     * that needs to be written to
     * @param imgFolderPath String that contains the root path to the images on
     * the website
     * @param pdfName String that contains the PDFname
     */

    public void defaultNewPageSetup(PDPageContentStream content, String imgFolderPath, String pdfName) {

        reportNumber(content);
        pdfDocName(content, pdfName);
        pageNumber(content);
        insertJPGImage(content, imgFolderPath, "polygon.jpg", 50, 690, 150, 30);

    }

    /**
     * Method for the user to write the name of the PDF Document in the bottom
     * left corner of each generated page.
     *
     * @param content PDPageContentStream object that determines which 'page'
     * that needs to be written to
     * @param pdfName String that contains the PDFname
     */
    //

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
            System.out.println("pdfDocName" + e.getMessage());
        }
    }

    /**
     * Method that handles the "checked"- and "unchecked"-checkbox images and
     * their placement
     *
     * @param box boolean to decide which box is to be displayed
     * @param imgFolderPath String that contains the root path to the images on
     * the website
     * @param content PDPageContentStream object that determines which 'page'
     * that needs to be written to
     * @param xCoordinate int that sets the x-coordinate for where to place the image
     * @param yCoordinate int that sets the y-coordinate for where to place the image
     * @param imgWidth int that sets the width of the image
     * @param imgHeight int that sets the height of the image
     */
    //
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
            System.out.println("checkBoxImg" + e.getMessage());
        }
    }

    /**
     * Method to creat single-textlines quick and simple!
     *
     * @param content PDPageContentStream object that determines which 'page'
     * that needs to be written to
     * @param textLine String that contains the wanted text to be displayed
     * @param textSize int that sets the text size
     * @param xCoordinate int that sets the x-coordinate for where to place the textline
     * @param yCoordinateint int that sets the y-coordinate for where to place the textline
     */

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
            System.out.println("singleTextline" + e.getMessage());
        }

    }

    /**
     * Method to creat single-textlines with user input (parameters) quick and
     * simple!
     *
     * @param content PDPageContentStream object that determines which 'page'
     * that needs to be written to
     * @param textLine String that contains the wanted text to be displayed
     * @param userInput String that contains the users input
     * @param textSize int that sets the text size
     * @param xCoordinate int that sets the x-coordinate for the textline
     * @param yCoordinate int that sets the y-coordinate for the textline
     */
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
            System.out.println("singleTextLIneWithUserInput" + e.getMessage());
        }
    }

    /**
     * Calculates the buildings total squaremeter
     *
     * @return int bSqm
     */
    public int buildingSQM() {

        //Get buildings final squarementer
        int bSqm = 0;
        for (int i = 0; i < roomList.size(); i++) {
            bSqm += roomList.get(i).getSqm();
        }

        return bSqm;
    }

    /**
     * Returns the issueId
     *
     * @param type 0 for area 1 for room
     * @param id id of given area/room
     * @return a int
     */
    private int getIssueId(int type, int id) {

        if (type == 0) {

            for (int i = 0; i < issueList.size(); i++) {

                if (issueList.get(i).getArea_id() == id) {

                    return i;

                }

            }

        } else {

            for (int i = 0; i < issueList.size(); i++) {

                if (issueList.get(i).getRoom_id() == id) {

                    return i;

                }

            }

        }

        return 0;

    }

    /**
     * Returns boolean if there are issues
     *
     * @param type 0 for area 1 for room
     * @param id id of given area/room
     * @return a boolean
     */
    private boolean hasIssue(int type, int id) {

        if (type == 0) {

            for (int i = 0; i < issueList.size(); i++) {

                if (issueList.get(i).getArea_id() == id) {

                    return true;

                }

            }

        } else {

            for (int i = 0; i < issueList.size(); i++) {

                if (issueList.get(i).getRoom_id() == id) {

                    return true;

                }

            }

        }

        return false;

    }

}
