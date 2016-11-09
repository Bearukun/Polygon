package dataAccessLayer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Ceo
 */
public class PDFWithText {

    public void pdfWithText(String pdfName, String buildingName, String buildingAddress, Integer buildingPostcode, String buildingCity, Integer buildingContructionYear, 
            Integer buildingSQM, String buildingOwner  ) {

        PDDocument doc = null;
        PDPage page = null;

        //String test = "Would you please work? I'm asking nicely....";
        try {
            doc = new PDDocument();
            page = new PDPage();

            doc.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;

            
            //content.moveTextPositionByAmount(tx, ty);
            //tx = Height
            //ty = Width;
            
            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(100, 700);
            content.drawString("100, 700 PDF Navn: " + pdfName);
            //content.newLineAtOffset(100, 100);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(150, 750);
            content.drawString("150, 750 Bygnings navn: " + buildingName);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(50, 600);
            content.drawString("50, 600 Bygnings Adresse: " + buildingAddress);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(25, 25);
            content.drawString("25, 25 Bygnings Postnr.: " + buildingPostcode);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(50, 50);
            content.drawString("25, 50 Bygnings by: " + buildingCity);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(500, 500);
            content.drawString("500, 500 Bygnings opførelses år: " + buildingContructionYear);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(250, 250);
            content.drawString("250, 250 Bygningens Areal: " + buildingSQM);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(100, 100);
            content.drawString("100, 100 Bygning Ejer: " + buildingOwner);
            content.endText();
            
            content.close();

            //Save directory + file name
            doc.save("E:\\Dokumenter\\NetBeansProjects\\Polygon\\" + pdfName + ".pdf");
            doc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
