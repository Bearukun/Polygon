package presentationLayer.servlets;

import dataAccessLayer.PDFCreator;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import serviceLayer.controllers.BuildingController;
import serviceLayer.controllers.DataController;
import serviceLayer.controllers.UserController;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.controllers.interfaces.DataControllerInterface;
import serviceLayer.controllers.interfaces.UserControllerInterface;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.DamageRepair;
import serviceLayer.entities.Document;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Issue;
import serviceLayer.entities.MoistureInfo;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;

@WebServlet(name = "TechnicianServlet", urlPatterns = {"/TechnicianServlet"})
public class TechnicianServlet extends HttpServlet {

    private ArrayList<Building> userBuildings = new ArrayList();
    private ArrayList<User> userList = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<Healthcheck> allHealthchecks = new ArrayList();
    private ArrayList<Healthcheck> buildingHealthchecks = new ArrayList();
    private ArrayList<MoistureInfo> allMoistureMeasurements = new ArrayList();
    private ArrayList<DamageRepair> allDamageRepairs = new ArrayList();
    private ArrayList<Issue> healthcheckIssues = new ArrayList();
    private ArrayList<Area> buildingAreas = new ArrayList();
    private ArrayList<Room> buildingRooms = new ArrayList();
    private ArrayList<Document> buildingDocuments = new ArrayList();

    
    private UserControllerInterface usrCtrl = new UserController();
    private BuildingControllerInterface bldgCtrl = new BuildingController();
    private DataControllerInterface datCtrl = new DataController();
    private User user = null;
    private int user_id, buildingId = 0;
    private String origin = "";
    PDFCreator pdfwt = new PDFCreator();

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
                //Save the logged in user's id
                user_id = (Integer) request.getSession().getAttribute("user_id");
                refreshUsers(request);
                refreshAllBuildings(request);
                refreshAllHealthchecks(request);
                response.sendRedirect("technician.jsp");
            }

            String errMsg = null;
            if (request.getParameter("origin") != null) {
                origin = request.getParameter("origin");
            }

            switch (origin) {

                case "technicianOverview":

                    //Reset the source, i.e. which page we are coming from
                    request.getSession().setAttribute("source", "");

                    //Retrieve the building being edited
                    buildingId = Integer.parseInt(request.getParameter("buildingId"));
                    request.getSession().setAttribute("buildingBeingEdited", buildingId);

                    //Fetch areas and rooms for selected building
                    refreshAreas(request, buildingId);
                    refreshRooms(request, buildingId);

                    //Fetch the current healthcheck and its issues for the chosen building 
                    int healthcheckId = getBuildingHealthcheck(request, buildingId);
                    getHealthcheckIssues(request, healthcheckId);

                    //Refresh all moisture measurements 
                    refreshAllMoistureMeasurements(request);

                    //Refresh all damage repairs 
                    refreshAllDamageRepairs(request);
                    
                    //Refresh all documents to given building
                    refreshDocuments(buildingId);
                    request.getSession().setAttribute("buildingDocuments", buildingDocuments);

                    //redirect to viewBuilding into the specific building being edited
                    response.sendRedirect("technicianViewBuilding.jsp?value=" + buildingId + "");

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
                        } //If the building's healthcheck pending status needs setting to true
                        else {
                            healthcheckValueToWrite = 1;
                        }

                        //Save values to database
                        bldgCtrl.toggleHealthcheck(build.getbuildingId(), healthcheckValueToWrite);

                        //Refresh the logged in user's buildings overview
                        refreshBuilding(request, user_id);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }

                    //If 'Create area' button was clicked
                    if (request.getParameter("originSection").equals("createAreaButton")) {
                        request.getSession().setAttribute("source", "createAreaButton");
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If an area needs deleting
                    //If 'Create issue' button was clicked
                    else if (request.getParameter("originSection").equals("addIssueButton")) {
                        request.getSession().setAttribute("source", "addIssueButton");
                        request.getSession().setAttribute("ActiveSidebarMenu", "RegistrerProblem");
                        request.getSession().setAttribute("areaId", request.getParameter("areaId"));
                        request.getSession().setAttribute("roomId", request.getParameter("roomId"));

                        //Save to Session if coming from add issue for room or for area
                        request.getSession().setAttribute("originType", request.getParameter("originType"));
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } else if (request.getParameter("originSection").equals("deleteAreaButton")) {
                        request.getSession().setAttribute("source", "deleteAreaButton");

                        //Retrieve form input values from viewBuilding
                        int areaId = Integer.parseInt(request.getParameter("areaId"));

                        //Save values to database
                        bldgCtrl.deleteArea(areaId);

                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");

                        //Fetch areas and rooms for selected building
                        refreshAreas(request, build.getbuildingId());
                        refreshRooms(request, build.getbuildingId());

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
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
                        refreshAreas(request, buildingId);
                        refreshRooms(request, buildingId);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If 'Create room' button was clicked
                    else if (request.getParameter("originSection").equals("createRoomButton")) {
                        request.getSession().setAttribute("source", "createRoomButton");
                        request.getSession().setAttribute("areaId", request.getParameter("areaId"));

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
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
                        refreshAreas(request, buildingId);
                        refreshRooms(request, buildingId);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
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
                        refreshAreas(request, build.getbuildingId());
                        refreshRooms(request, build.getbuildingId());

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If 'Edit building details' button was clicked
                    else if (request.getParameter("originSection").equals("editBuildingButton")) {
                        request.getSession().setAttribute("source", "editBuildingButton");
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If the building needs editing
                    else if (request.getParameter("originSection").equals("editBuilding")) {
                        request.getSession().setAttribute("source", "editBuilding");
                        //Retrieve form input values from viewBuilding
                        String buildingName = request.getParameter("buildingName");
                        String addres = request.getParameter("address");
                        int postcod = Integer.parseInt(request.getParameter("postcode"));
                        String cit = request.getParameter("city");
                        int constructionYear = Integer.parseInt(request.getParameter("constructionYear"));
                        String purpos = request.getParameter("purpose");
                        int sq = Integer.parseInt(request.getParameter("sqm"));
                        int selectedBuilding = Integer.parseInt(request.getParameter("selectedBuilding"));
                        //Save values to database
                        bldgCtrl.editBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpos, sq);
                        //Refresh the logged in user's buildings overview
                        refreshBuilding(request, user_id);
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If an issue needs creating
                    else if (request.getParameter("originSection").equals("addIssue")) {

                        request.getSession().setAttribute("source", "");
                        buildingId = build.getbuildingId();
                        int healthcheck_id = getBuildingHealthcheck(request, buildingId);
                        String description = request.getParameter("description");
                        String recommendation = request.getParameter("recommendation");
                        int areaId = Integer.parseInt(request.getSession().getAttribute("areaId").toString());
                        int roomId = Integer.parseInt(request.getSession().getAttribute("roomId").toString());
                        //create new issue
                        int issue_index = bldgCtrl.createIssue(buildingId, areaId, roomId, description, recommendation, healthcheck_id);

                        Part filePart = request.getPart("img");
                        String[] header = (filePart.getHeader("content-disposition").split(" "));
                        String[] fileName = header[2].split("\"");

                        InputStream inputStream = filePart.getInputStream();
                        //Save values to database
                        datCtrl.uploadIssueImage(issue_index, fileName[1], inputStream);

                        //Fetch the current healthcheck and its issues for the chosen building 
                        healthcheckId = getBuildingHealthcheck(request, buildingId);
                        getHealthcheckIssues(request, healthcheckId);
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");

                    } //If an issue needs deleting
                    else if (request.getParameter("originSection").equals("deleteIssueButton")) {
                        int issueId = Integer.parseInt(request.getParameter("issueId"));
                        //Delete issue
                        bldgCtrl.deleteIssue(issueId);

                        //Fetch the current healthcheck and its issues for the chosen building 
                        healthcheckId = getBuildingHealthcheck(request, buildingId);
                        getHealthcheckIssues(request, healthcheckId);
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If 'Register moisture scan' button was clicked
                    else if (request.getParameter("originSection").equals("registerMoistButton")) {
                        request.getSession().setAttribute("source", "registerMoistButton");
                        request.getSession().setAttribute("roomId", request.getParameter("roomId"));
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If a moisture level needs registering
                    else if (request.getParameter("originSection").equals("registerMoist")) {
                        request.getSession().setAttribute("source", "");

                        int roomId = Integer.parseInt(request.getSession().getAttribute("roomId").toString());
                        String measurePoint = request.getParameter("measurePoint");
                        int measureValue = Integer.parseInt(request.getParameter("measureValue"));
                        bldgCtrl.registerMoistureMeasurement(roomId, measurePoint, measureValue);
                        //Refresh all moisture measurements 
                        refreshAllMoistureMeasurements(request);
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If a moisture level needs deleting
                    else if (request.getParameter("originSection").equals("deleteMoist")) {
                        request.getSession().setAttribute("source", "");
                        int moistId = Integer.parseInt(request.getParameter("moistId"));
                        bldgCtrl.deleteMoistureMeasurement(moistId);
                        //Refresh all moisture measurements 
                        refreshAllMoistureMeasurements(request);
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If 'Register damage repair' button was clicked
                    else if (request.getParameter("originSection").equals("registerDamageRepairButton")) {
                        request.getSession().setAttribute("source", "registerDamageRepairButton");
                        request.getSession().setAttribute("roomId", request.getParameter("roomId"));
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } //If a damage repair needs registering
                    else if (request.getParameter("originSection").equals("registerDamageRepair")) {
                        request.getSession().setAttribute("source", "");
                        int roomId = Integer.parseInt(request.getSession().getAttribute("roomId").toString());
                        String damageTime = request.getParameter("damageTime");
                        String damageLocation = request.getParameter("damageLocation");
                        String damageDetails = request.getParameter("damageDetails");
                        String workDone = request.getParameter("workDone");
                        String type = request.getParameter("type");
                        bldgCtrl.registerDamageRepair(roomId, damageTime, damageLocation, damageDetails, workDone, type);
                        //Refresh all damage repairs 
                        refreshAllDamageRepairs(request);
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    //If a damage repair needs deleting
                    else if(request.getParameter("originSection").equals("deleteDamageRepairButton")){
                        request.getSession().setAttribute("source", "");
                        int roomId = Integer.parseInt(request.getParameter("roomId"));
                        bldgCtrl.deleteDamageRepair(roomId);
                        //Refresh all damage repairs 
                        refreshAllDamageRepairs(request);
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } 
                    //If 'complete healthcheck' button was clicked
                    else if (request.getParameter("originSection").equals("completeHealthcheckButton")) {
                        request.getSession().setAttribute("source", "completeHealthcheckButton");
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    //If a healthcheck needs completing
                    else if (request.getParameter("originSection").equals("completeHealthcheck")) {
                        request.getSession().setAttribute("source", "");
                        String condition = request.getParameter("condition");
                        String buildingResponsible = request.getParameter("buildingResponsible");
                        healthcheckId = (Integer) request.getSession().getAttribute("healthcheckId");
                        bldgCtrl.completeHealthcheck(condition, buildingResponsible, healthcheckId, build.getbuildingId());
                        refreshAllBuildings(request);
                        response.sendRedirect("technician.jsp");
                    }

                    break;

                case "acceptHealthcheckButton":
                    //Save parameters from technician.jsp: buildingId and technicianID
                    int technicianId = (Integer) request.getSession().getAttribute("user_id");
                    //int technicianId = 12;
                    buildingId = Integer.parseInt(request.getParameter("buildingId"));
                    //Call method to modify database
                    bldgCtrl.acceptHealthcheck(buildingId, technicianId);
                    refreshAllBuildings(request);
                    //redirect to user.jsp
                    response.sendRedirect("technician.jsp?success=UpdateSuccessful");
                    break;
            }

            /*
                        String pdfName = build.getName() + "-" + build.getbuildingId();
                        String bName = build.getName();
                        String bAddress = build.getAddress();
                        String bPostCode = "" + build.getPostcode();
                        String bCity = build.getCity();
                        String bConstructionYear = "" + build.getConstruction_year();
                        String bSQM = "" + build.getSqm();
                        String bPurpose = build.getPurpose();
                        String bOwner = "23";
                        String imgFolderPath = "/Users/Ceo/NetBeansProjects/Polygon/web/img/";
                        String savePath = "/Users/Ceo/NetBeansProjects/Polygon/pdf/";
                        */
                        ///Users/Ceo/NetBeansProjects/Polygon/web/img/
                        //"E:\\Dokumenter\\NetBeansProjects\\Polygon\\web\\img\\"

                        ///Users/Ceo/NetBeansProjects/Polygon/pdf/
                        //"E:\\Dokumenter\\NetBeansProjects\\Polygon\\pdf\\"
                        //String picturePath = "";

                        //String systemDir = System.getProperty("user.dir");
                        //System.out.println(systemDir);

//                        //Filechooser for selecting an image for the generated PDF
//                        JFileChooser choose = new JFileChooser();
//                        FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg files", "jpg");
//                        choose.setFileFilter(filter);
//                        String picturePath = "";
//                        
//                        int returnVal = choose.showOpenDialog(choose);
//
//                        if (returnVal == JFileChooser.APPROVE_OPTION) {
//
//                            picturePath = choose.getSelectedFile().getAbsolutePath();
//                            folderPath = "" + choose.getCurrentDirectory();
//                            System.out.println(picturePath);
//                            System.out.println(folderPath + " Folder sti");
//
//                            System.out.println(picturePath);
//                        }
                        /**pdfwt.createPDF(pdfName, bName, bAddress,
                                Integer.parseInt(bPostCode), bCity, Integer.parseInt(bConstructionYear),
                                Integer.parseInt(bSQM), bPurpose, bOwner, picturePath, imgFolderPath, savePath);
                        */
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Refreshes the damage repairs list
    public void refreshAllDamageRepairs(HttpServletRequest request) {
        allDamageRepairs.clear();
        try {
            allDamageRepairs = bldgCtrl.getAllDamageRepairs();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getSession().setAttribute("allDamageRepairs", allDamageRepairs);
    }

    //Refreshes the moisture measurements list
    public void refreshAllMoistureMeasurements(HttpServletRequest request) {
        allMoistureMeasurements.clear();
        try {
            allMoistureMeasurements = bldgCtrl.getAllMoistureMeasurements();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getSession().setAttribute("allMoistureMeasurements", allMoistureMeasurements);
    }

    //Fetches the issues for the current healthcheck
    public void getHealthcheckIssues(HttpServletRequest request, int healthcheckId) {
        try {
            healthcheckIssues.clear();
            healthcheckIssues = bldgCtrl.getHealthcheckIssues(healthcheckId);
            request.getSession().setAttribute("healthcheckIssues", healthcheckIssues);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Get the current healthcheck for a building
    public int getBuildingHealthcheck(HttpServletRequest request, int buildingId) {
        int healthcheck_id = 0;
        try {
            buildingHealthchecks.clear();
            buildingHealthchecks = bldgCtrl.getBuildingHealthchecks(buildingId);
            request.getSession().setAttribute("buildingHealthchecks", buildingHealthchecks);
            for (int i = 0; i < buildingHealthchecks.size(); i++) {
                if (buildingHealthchecks.get(i).getbuildingId() == buildingId) {
                    healthcheck_id = buildingHealthchecks.get(i).getHealthcheck_id();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getSession().setAttribute("healthcheckId", healthcheck_id);
        return healthcheck_id;
    }

    //Refreshes the list of all healthchecks
    public void refreshAllHealthchecks(HttpServletRequest request) throws Exception {
        allHealthchecks.clear();
        allHealthchecks = bldgCtrl.getAllHealthchecks();
        request.getSession().setAttribute("allHealthchecks", allHealthchecks);
    }

    //Refreshes the list of buildings
    public void refreshBuilding(HttpServletRequest request, int user_id) throws Exception {
        userBuildings.clear();
        userBuildings = bldgCtrl.getBuildings(user_id);
        request.getSession().setAttribute("userBuildings", userBuildings);
    }

    //Refreshes the list of buildings
    public void refreshAllBuildings(HttpServletRequest request) throws Exception {
        allBuildings.clear();
        allBuildings = bldgCtrl.getAllBuildings();
        request.getSession().setAttribute("allBuildings", allBuildings);
    }

    //Refreshes the list of building areas
    public void refreshAreas(HttpServletRequest request, int buildingId) throws Exception {
        buildingAreas.clear();
        buildingAreas = bldgCtrl.getAreas(buildingId);
        request.getSession().setAttribute("buildingAreas", buildingAreas);
    }

    //Refreshes the list of building rooms
    public void refreshRooms(HttpServletRequest request, int buildingId) throws Exception {
        buildingRooms.clear();
        buildingRooms = bldgCtrl.getRooms(buildingId);
        request.getSession().setAttribute("buildingRooms", buildingRooms);
    }

    public void refreshUsers(HttpServletRequest request) throws Exception {

        userList.clear();
        userList = usrCtrl.getUsers();
        request.getSession().setAttribute("userList", userList);
    }
        //Refreshes documents of a select building.
    public void refreshDocuments(int buildingId) throws Exception {

        buildingDocuments.clear();
        buildingDocuments = datCtrl.getDocuments(buildingId);

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
