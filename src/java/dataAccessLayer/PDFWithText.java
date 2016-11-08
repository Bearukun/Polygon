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

    public void pdfWithText(String pdfName, String firstname, String lastname) {
        PDDocument doc = null;
        PDPage page = null;

        //String test = "Would you please work? I'm asking nicely....";
        try {
            doc = new PDDocument();
            page = new PDPage();

            doc.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(100, 700);
            content.drawString("Frist name: " + firstname);
            //content.newLineAtOffset(100, 100);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(150, 750);
            content.drawString("Last name: " + lastname);
            content.endText();
            
            content.beginText();
            content.setFont(font, 12);
            content.moveTextPositionByAmount(50, 600);
            content.drawString("Last name: " + lastname);
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
