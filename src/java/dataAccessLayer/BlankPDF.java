/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccessLayer;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 *
 * @author Ceo
 */
public class BlankPDF {
    
    public void testBlank(String pdfName){
    
        PDDocument doc = null;
        doc = new PDDocument();
        doc.addPage(new PDPage());
        
        
        try{
            doc.save(pdfName + ".pdf");
            doc.close();
        } catch (Exception io){
            System.out.println(io);
        }
    }

   
    

   

   
    
    
}
