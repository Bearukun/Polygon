package serviceLayer.entities;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

/**
 * Class used to hold a document.
 */
public class Document implements Serializable {

    private int document_id, building_id;
    private String document_name, document_type;
    private Blob document_file;
    private Timestamp date_created;

    /**
     * Empty constructor
     */
    public Document() {
    }

    /**
     * Constructor to instantiate a specific document
     * @param document_id int identifying the document from the database
     * @param date_created Timestamp containing the date the 
     * @param document_name String contining the documents name
     * @param document_file Blob containing the document file
     * @param document_type String containing the documents filetype
     * @param building_id int identifying the building that is linked to the document
     */
    public Document(int document_id, Timestamp date_created, String document_name, Blob document_file, String document_type, int building_id) {
        this.document_id = document_id;
        this.date_created = date_created;
        this.building_id = building_id;
        this.document_name = document_name;
        this.document_type = document_type;
        this.document_file = document_file;
    }
    
    /**
     * getters & setters 
     */
    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public Blob getDocument_file() {
        return document_file;
    }

    public void setDocument_file(Blob document_file) {
        this.document_file = document_file;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

}
