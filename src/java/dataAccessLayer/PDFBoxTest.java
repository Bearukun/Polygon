/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccessLayer;

import java.io.FileInputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


/**
 *
 * @author Ceo
 */
public class PDFBoxTest {
    
    // Note that this code works ONLY with jpg files
    public static void main(String[] args) {
        PDDocument doc = null;
        try{
          /* Step 1: Prepare the document.
           */
          //test
         doc = new PDDocument();
         PDPage page = new PDPage();
         doc.addPage(page);
         
         //Text test
         
         PDFont font = PDType1Font.HELVETICA_BOLD;
         
         /* Step 2: Prepare the image
          * PDJpeg is the class you use when dealing with jpg images.
          * You will need to mention the jpg file and the document to which it is to be added
          * Note that if you complete these steps after the creating the content stream the PDF
          * file created will show "Out of memory" error.
          */
         
         PDImageXObject image = null;
         image = PDImageXObject.createFromFile("tesla.jpg", doc);
         
         /* Create a content stream mentioning the document, the page in the dcoument where the content stream is to be added.
          * Note that this step has to be completed after the above two steps are complete.
          */
         PDPageContentStream content = new PDPageContentStream(doc, page);
 
       /* Step 3:
        * Add (draw) the image to the content stream mentioning the position where it should be drawn
        * and leaving the size of the image as it is
        */
       
       content.beginText();
       content.setFont( font, 12 );
       content.moveTextPositionByAmount( 100, 700 );
           content.drawString("Please work, please work, please work..." + "/t" );
           content.endText();
         content.drawImage(image,20,20);
         content.close();
       
         /* Step 4:
          * Save the document as a pdf file mentioning the name of the file
          */
        
        doc.save("ImageNowPdf.pdf");
       
        } catch (Exception e){
             System.out.println("Exception");
        }
    }
    
}
