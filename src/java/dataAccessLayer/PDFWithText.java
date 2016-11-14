package dataAccessLayer;
 
 import org.apache.pdfbox.pdmodel.PDDocument;
 import org.apache.pdfbox.pdmodel.PDPage;
 import org.apache.pdfbox.pdmodel.PDPageContentStream;
 import org.apache.pdfbox.pdmodel.font.PDFont;
 import org.apache.pdfbox.pdmodel.font.PDType1Font;
 
 /**
 + *
 + * @author Ceo
 + */
 public class PDFWithText {
    String test;
    
     
     
    
      
      
     
     public static void main(String[] args) {
         PDDocument doc = null;
         PDPage page = null;
 
         String test = "Would you please work? I'm asking nicely....";
         
         String systemDir = System.getProperty("user.dir");
            System.out.println(systemDir);
         
        try{
           doc = new PDDocument();
            page = new PDPage();
 
           doc.addPage(page);
           PDFont font = PDType1Font.HELVETICA_BOLD;

           PDPageContentStream content = new PDPageContentStream(doc, page);
           content.beginText();
           content.setFont( font, 12 );
           content.moveTextPositionByAmount( 100, 700 );
           content.drawString("Please work, please work, please work..." + "/t" );
           //content.newLineAtOffset(100, 100);
           content.drawString(test);
           

           content.endText();
           content.close();
           
           
           //Save directory + file name
           ///Users/Ceo/NetBeansProjects/Polygon
           doc.save(systemDir + "/src/PolygonTesmedDir.pdf");
           doc.close();
     } catch (Exception e){
         System.out.println(e);
     }
 }
     
    
 }