/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccessLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import serviceLayer.exceptions.CustomException;

/**
 *
 * @author Ceo
 */
public class BlankPDF {
    
//Creates an empty .pdf-document with a custom filename
    public void testBlank(String pdfName) throws CustomException{
    
//initiates a new PDDocument
        PDDocument doc = null;
        doc = new PDDocument();

//Creates a new page
        doc.addPage(new PDPage());
        
        
        try{
//Saves The .pdf at The designated path, with the custom file name.
            doc.save("E:\\Dokumenter\\NetBeansProjects\\Polygon\\"+ pdfName + ".pdf");
//Closes the PDF creation
            doc.close();
        } catch (Exception io){
            System.out.println(io);
        }
    }

   
    

   

   
    
    
}
