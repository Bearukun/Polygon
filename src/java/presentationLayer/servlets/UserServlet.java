package presentationLayer.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import serviceLayer.controllers.BuildingController;
import serviceLayer.controllers.DataController;
import serviceLayer.controllers.EmailController;
import serviceLayer.controllers.UserController;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.controllers.interfaces.DataControllerInterface;
import serviceLayer.controllers.interfaces.EmailControllerInterface;
import serviceLayer.controllers.interfaces.UserControllerInterface;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Document;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;
import serviceLayer.exceptions.PolygonException;

/**
 * Servlet that handles the customer.
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    private ArrayList<Building> userBuildings = new ArrayList();
    private ArrayList<User> userList = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<Area> buildingAreas = new ArrayList();
    private ArrayList<Room> buildingRooms = new ArrayList();
    private ArrayList<Document> buildingDocuments = new ArrayList();

    private Date date = new Date();
    private UserControllerInterface usrCtrl = new UserController();
    private BuildingControllerInterface bldgCtrl = new BuildingController();
    private DataControllerInterface dat = new DataController();
    private EmailControllerInterface emailCtrl = new EmailController();
    private User user = null;
    private int user_id;
    private String origin = "";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {

            //If we are coming from the LoginServlet servlet, i.e. we have just logged in
            if (request.getSession().getAttribute("sourcePage").toString().equals("LoginServlet")) {
                request.getSession().setAttribute("sourcePage", "Invalid");
                request.getSession().setAttribute("source", "user");
                //Save the logged in user's id
                user_id = (Integer) request.getSession().getAttribute("user_id");
                user = usrCtrl.getUser(user_id);
                refreshBuilding(request, user_id);
                response.sendRedirect("user.jsp");
            }

            if (request.getParameter("origin") != null) {
                origin = request.getParameter("origin");
            }

            switch (origin) {

                //If we are coming from the user's buildings overview
                case "userOverview":

                    //Reset the source, i.e. which page we are coming from
                    request.getSession().setAttribute("source", "");

                    //Retrieve the building being edited
                    int buildingId = Integer.parseInt(request.getParameter("buildingId"));
                    request.getSession().setAttribute("buildingBeingEdited", buildingId);

                    //Fetch areas and rooms for selected building
                    refreshAreas(buildingId);
                    refreshRooms(buildingId);
                    refreshDocuments(buildingId);

                    //Save areas and rooms in Session
                    request.getSession().setAttribute("buildingAreas", buildingAreas);
                    request.getSession().setAttribute("buildingRooms", buildingRooms);
                    request.getSession().setAttribute("buildingDocuments", buildingDocuments);

                    //redirect to viewBuilding into the specific building being edited
                    response.sendRedirect("viewBuilding.jsp?value=" + buildingId + "");

                    break;

                case "viewBuilding":

                    //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                    Building build = (Building) request.getSession().getAttribute("buildingBeingEdited");

                    //If 'Request healthcheck' button was clicked
                    if (request.getParameter("originSection").equals("healthcheckButton")) {

                        request.getSession().setAttribute("source", "healthcheckButton");

                        int healthcheckValueToWrite;
                        //If the building's healthcheck pending status needs setting to false
                        if (request.getParameter("originValue").equals("cancel")) {
                            healthcheckValueToWrite = 0;
                            //Sends a confirmation email regarding cancellation of the healthcheck
                            emailCustomerCancelHealthcheck(build, user_id);
                        } //If the building's healthcheck pending status needs setting to true
                        else {
                            healthcheckValueToWrite = 1;
                            emailHealthcheckRequest(build, user_id);
                        }

                        //Save values to database
                        bldgCtrl.toggleHealthcheck(build.getbuildingId(), healthcheckValueToWrite);

                        //Refresh the logged in user's buildings overview
                        refreshBuilding(request, user_id);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    //If 'Create area' button was clicked
                    if (request.getParameter("originSection").equals("createAreaButton")) {
                        request.getSession().setAttribute("source", "createAreaButton");
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If an area needs deleting
                    else if (request.getParameter("originSection").equals("deleteAreaButton")) {
                        request.getSession().setAttribute("source", "deleteAreaButton");

                        //Retrieve form input values from viewBuilding
                        int areaId = Integer.parseInt(request.getParameter("areaId"));

                        //Save values to database
                        bldgCtrl.deleteArea(areaId);

                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");

                        //Fetch areas and rooms for selected building
                        refreshAreas(build.getbuildingId());
                        refreshRooms(build.getbuildingId());

                        //Save areas and rooms in Session
                        request.getSession().setAttribute("buildingAreas", buildingAreas);
                        request.getSession().setAttribute("buildingRooms", buildingRooms);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If a new area needs creating
                    else if (request.getParameter("originSection").equals("createArea")) {
                        request.getSession().setAttribute("source", "createArea");
                        //Retrieve form input values from viewBuilding
                        String areaName = request.getParameter("areaName");
                        String areaDesc = request.getParameter("areaDesc");
                        int areaSqm = Integer.parseInt(request.getParameter("areaSqm"));
                        buildingId = build.getbuildingId();
                        //Save values to database
                        bldgCtrl.createArea(areaName, areaDesc, areaSqm, buildingId);

                        //Fetch areas and rooms for selected building
                        refreshAreas(buildingId);
                        refreshRooms(buildingId);

                        //Save areas and rooms in Session
                        request.getSession().setAttribute("buildingAreas", buildingAreas);
                        request.getSession().setAttribute("buildingRooms", buildingRooms);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If 'Create area' button was clicked
                    else if (request.getParameter("originSection").equals("createRoomButton")) {
                        request.getSession().setAttribute("source", "createRoomButton");
                        request.getSession().setAttribute("areaId", request.getParameter("areaId"));

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If a new room needs creating
                    else if (request.getParameter("originSection").equals("createRoom")) {
                        request.getSession().setAttribute("source", "createRoom");
                        //Retrieve form input values from viewBuilding
                        String roomName = request.getParameter("roomName");
                        String roomDesc = request.getParameter("roomDesc");
                        int roomSqm = Integer.parseInt(request.getParameter("roomSqm"));
                        int areaId = Integer.parseInt(request.getSession().getAttribute("areaId").toString());

                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");
                        buildingId = build.getbuildingId();

                        //Save values to database
                        bldgCtrl.createRoom(roomName, roomDesc, roomSqm, areaId);

                        //Fetch areas and rooms for selected building
                        refreshAreas(buildingId);
                        refreshRooms(buildingId);

                        //Save areas and rooms in Session
                        request.getSession().setAttribute("buildingAreas", buildingAreas);
                        request.getSession().setAttribute("buildingRooms", buildingRooms);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } else if (request.getParameter("originSection").equals("addDocumentButton")) {
                        request.getSession().setAttribute("source", "addDocumentButton");
                        request.getSession().setAttribute("buildingId", request.getParameter("buildingId"));

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If a new document needs uploaded
                    else if (request.getParameter("originSection").equals("addDocument")) {
                        request.getSession().setAttribute("source", "addDocument");

                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");
                        buildingId = build.getbuildingId();

                        Part filePart = request.getPart("document");
                        String[] header = (filePart.getHeader("content-disposition").split(" "));
                        String[] headerExt = header[2].split("\"");
                        String[] fileNameExt = headerExt[1].split("\\.");
                        InputStream inputStream = filePart.getInputStream();

                        //Save values to database
                        dat.uploadDocument(buildingId, fileNameExt[0].toString(), fileNameExt[1].toString(), inputStream);

                        //Refresh documents
                        refreshDocuments(buildingId);

                        //Save documents in Session
                        request.getSession().setAttribute("buildingDocuments", buildingDocuments);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If an area needs deleting
                    else if (request.getParameter("originSection").equals("deleteDocumentButton")) {
                        request.getSession().setAttribute("source", "deleteDocumentButton");

                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");
                        buildingId = build.getbuildingId();
                        int documentId = Integer.parseInt(request.getParameter("documentId"));

                        //Remove document from database
                        dat.deleteDocument(documentId);

                        //Refresh documents
                        refreshDocuments(buildingId);

                        //Save documents in Session
                        request.getSession().setAttribute("buildingDocuments", buildingDocuments);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If an area needs deleting
                    else if (request.getParameter("originSection").equals("deleteRoomButton")) {
                        request.getSession().setAttribute("source", "deleteRoomButton");

                        //Retrieve form input values from viewBuilding
                        int roomId = Integer.parseInt(request.getParameter("roomId"));
                        //int areaId = 6;

                        //Save values to database
                        bldgCtrl.deleteRoom(roomId);

                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");

                        //Fetch areas and rooms for selected building
                        refreshAreas(build.getbuildingId());
                        refreshRooms(build.getbuildingId());
                        //refreshRooms(buildingId);

                        //Save areas and rooms in Session
                        request.getSession().setAttribute("buildingAreas", buildingAreas);
                        request.getSession().setAttribute("buildingRooms", buildingRooms);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If 'Edit building details' button was clicked
                    else if (request.getParameter("originSection").equals("editBuildingButton")) {
                        request.getSession().setAttribute("source", "editBuildingButton");
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If the building needs editing
                    else if (request.getParameter("originSection").equals("editBuilding")) {
                        request.getSession().setAttribute("source", "editBuilding");
                        //Retrieve form input values from viewBuilding
                        String buildingName = request.getParameter("buildingName");
                        String address = request.getParameter("address");
                        int postcod = Integer.parseInt(request.getParameter("postcode"));
                        String cit = request.getParameter("city");
                        int constructionYear = Integer.parseInt(request.getParameter("constructionYear"));
                        String purpos = request.getParameter("purpose");
                        int sq = Integer.parseInt(request.getParameter("sqm"));
                        int selectedBuilding = Integer.parseInt(request.getParameter("selectedBuilding"));

                        emailEditBuilding(buildingName, address, postcod, cit, constructionYear, purpos, sq, selectedBuilding);

                        //Save values to database
                        bldgCtrl.editBuilding(selectedBuilding, buildingName, address, postcod, cit, constructionYear, purpos, sq);
                        //Refresh the logged in user's buildings overview
                        refreshBuilding(request, user_id);
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } else if (request.getParameter("originSection").equals("editBuildingImage")) {

                        request.getSession().setAttribute("source", "editBuilding");
                        Part filePart = request.getPart("img");
                        String[] header = (filePart.getHeader("content-disposition").split(" "));
                        String[] fileName = header[2].split("\"");
                        InputStream inputStream = filePart.getInputStream();
                        //Save values to database
                        dat.uploadBuildingImage(Integer.parseInt(request.getParameter("selectedBuilding")), fileName[1], inputStream);
                        //Refresh the logged in user's buildings overview
                        refreshBuilding(request, user_id);
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");

                    }

                    break;

                case "addBuildingButton":
                    //Tell the page redirected to where it was accessed from, in order to display the corresponding sidebar menu
                    request.getSession().setAttribute("source", "user");
                    response.sendRedirect("addBuilding.jsp");
                    break;

                case "sendEmailToPolygon":

                    String polygonEmail = "polygonmailtest4@gmail.com";
                    String emailHeader = "Bruger#" + user.getUser_id() + " (" + user.getCompany() + "): " + request.getParameter("emailHead");
                    String userInfo
                            = "\n\n---------------------------------------------------------------------\n"
                            + "Kunde information\n"
                            + "---------------------------------------------------------------------\n"
                            + "Kunde Email: " + user.getEmail() + "\n"
                            + "Kunde id: " + user.getUser_id() + "\n"
                            + "Kunde Navn: " + user.getName() + "\n"
                            + "Kunde Firma: " + user.getCompany() + "\n"
                            + "Kunde Tlf: " + user.getPhone() + "\n"
                            + "---------------------------------------------------------------------\n\n";

                    String emailMessage = request.getParameter("emailMessage") + userInfo;
                    request.getSession().setAttribute("source", "user");
                    emailCtrl.send(polygonEmail, emailHeader, emailMessage);
                    response.sendRedirect("user.jsp?mailSuccess");
                    break;
            }

        } catch (PolygonException e) {
            
            request.getSession().setAttribute("ExceptionError", e.getMessage());
            response.sendRedirect("error.jsp");
            
        }

    }

    //Refreshes the list of buildings
    public void refreshBuilding(HttpServletRequest request, int user_id) throws PolygonException {

        userBuildings.clear();
        userBuildings = bldgCtrl.getBuildings(user_id);
        request.getSession().setAttribute("userBuildings", userBuildings);
    }

    //Refreshes the list of buildings
    public void refreshAllBuildings(HttpServletRequest request) throws PolygonException {
        allBuildings.clear();
        allBuildings = bldgCtrl.getAllBuildings();
        request.getSession().setAttribute("allBuildings", allBuildings);
    }

    //Refreshes the list of building areas
    public void refreshAreas(int buildingId) throws PolygonException {
        buildingAreas.clear();
        buildingAreas = bldgCtrl.getAreas(buildingId);
    }

    //Refreshes the list of building rooms
    public void refreshRooms(int buildingId) throws PolygonException {
        buildingRooms.clear();
        buildingRooms = bldgCtrl.getRooms(buildingId);
    }

    //Refreshes documents of a select building.
    public void refreshDocuments(int buildingId) throws PolygonException {

        buildingDocuments.clear();
        buildingDocuments = dat.getDocuments(buildingId);

    }

    public void emailEditBuilding(String buildingName, String address, int postcod, String cit, int constructionYear, String purpos, int sqm, int selectedBuilding) {
        
        //Email the customer about the changes to the building           
        String emailEditBuildingHeader = "Polygon: Ændringer i deres bygning\"" + buildingName + "\". ";
        String emailEditBuildingMessage = "Hej " + user.getName() + " (" + user.getCompany() + " )"
                + "\n\nVi har den " + date + " registeret, at der er sket ændringer i oplysningerne om deres bygning \"" + buildingName + "\". "
                + "Vi har nu derfor følgende generelle information om bygningen:"
                + "Deres profil ser således ud nu: "
                + "\n\n"
                + "\n\n"
                + "Bygningens Navn: " + buildingName + "\n"
                + "Adresse: " + address + "\n"
                + "Postnummer: " + postcod + "\n "
                + "By: " + cit + "\n"
                + "Opførelses år: " + constructionYear + "\n"
                + "Bygningens formål: " + purpos + "\n"
                + "Samlede kvadratmeter: " + sqm + "\n"
                + "Bygningen ID#: " + selectedBuilding + "\n"
                + "\n\n\n"
                + "Har de nogen spørgsmål, "
                + "så tøv ikke med at kontakte os!"
                + "\n\n\n"
                + " Med Venlig Hilsen"
                + "\n\n"
                + "Polygon"
                + "\n\n"
                + "Rypevang 5\n"
                + "3450 Allerød\n"
                + "Tlf. 4814 0055\n"
                + "sundebygninger@polygon.dk";
        
        try {
            
            emailCtrl.send(user.getEmail(), emailEditBuildingHeader, emailEditBuildingMessage);
            
        } catch (PolygonException e) {
            
            e.getMessage();
            
        }
    }

    public void emailHealthcheckRequest(Building build, int id) throws PolygonException {
        //Email the customer about the requested healthcheck 

        String polygonMail = "polygonmailtest4@gmail.com";
        String emailHealthcheckRequestHeader = "Polygon: Anmodning om Sunhedscheck indsendt af \"" + build.getName() + "\". ";
        String emailHealthcheckRequestMessage = "Hej " + user.getName() + " (" + user.getCompany() + " )"
                + "\n\nVi har den " + date + " registeret, at de har anmodet om et sundhedscheck af deres bygning: \"" + build.getName() + "\". "
                + ""
                + "\n"
                + "Har de nogen spørgsmål, "
                + "så tøv ikke med at kontakte os!"
                + "\n\n\n"
                + " Med Venlig Hilsen"
                + "\n\n"
                + "Polygon"
                + "\n\n"
                + "Rypevang 5\n"
                + "3450 Allerød\n"
                + "Tlf. 4814 0055\n"
                + "sundebygninger@polygon.dk";

        try {
            
            //Sends email to both he customer and Polygon
            //Customer
            emailCtrl.send(usrCtrl.getUser(id).getEmail(), emailHealthcheckRequestHeader, emailHealthcheckRequestMessage);
            //Polygon
            emailCtrl.send(polygonMail, emailHealthcheckRequestHeader, emailHealthcheckRequestMessage);
            
        } catch (PolygonException e) {
            
            e.getMessage();
            
        }
        
    }

    public void emailCustomerCancelHealthcheck(Building build, int id) throws PolygonException {
        //Email the customer about the requested healthcheck 

        String polygonMail = "polygonmailtest4@gmail.com";
        String emailHealthcheckRequestHeader = "Polygon: Anmodning om Aflysning af Sunhedscheck  af \"" + build.getName() + "\". ";
        String emailHealthcheckRequestMessage = "Hej " + user.getName() + " (" + user.getCompany() + " )"
                + "\n\nVi har den " + date + " registeret, at de har anmodet om at aflyse sundhedschecket af deres bygning: \"" + build.getName() + "\". "
                + ""
                + "\n"
                + "Har de nogen spørgsmål, "
                + "så tøv ikke med at kontakte os!"
                + "\n\n\n"
                + " Med Venlig Hilsen"
                + "\n\n"
                + "Polygon"
                + "\n\n"
                + "Rypevang 5\n"
                + "3450 Allerød\n"
                + "Tlf. 4814 0055\n"
                + "sundebygninger@polygon.dk";

        try {
            
            //Sends email to both he customer and Polygon
            //Customer
            emailCtrl.send(usrCtrl.getUser(id).getEmail(), emailHealthcheckRequestHeader, emailHealthcheckRequestMessage);
            //Polygon
            emailCtrl.send(polygonMail, emailHealthcheckRequestHeader, emailHealthcheckRequestMessage);
            
        } catch (PolygonException e) {
            
            e.getMessage();
            
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
