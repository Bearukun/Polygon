package presentationLayer.servlets;

import dataAccessLayer.PDFCreator;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import serviceLayer.controllers.BuildingController;
import serviceLayer.controllers.UserController;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Healthcheck;
import serviceLayer.entities.Issue;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;

@WebServlet(name = "TechnicianServlet", urlPatterns = {"/TechnicianServlet"})
public class TechnicianServlet extends HttpServlet {

    private ArrayList<Building> userBuildings = new ArrayList();
    private ArrayList<User> userList = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<Healthcheck> allHealthchecks = new ArrayList();
    private ArrayList<Healthcheck> buildingHealthchecks = new ArrayList();
    private ArrayList<Issue> healthcheckIssues = new ArrayList();
    private ArrayList<Area> buildingAreas = new ArrayList();
    private ArrayList<Room> buildingRooms = new ArrayList();

    private UserController usrCtrl = new UserController();
    private BuildingController bldgCtrl = new BuildingController();
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
            if(request.getParameter("origin")!=null){
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
                    else if(request.getParameter("originSection").equals("addIssueButton")){
                        request.getSession().setAttribute("source", "addIssueButton");
                        request.getSession().setAttribute("ActiveSidebarMenu","RegistrerProblem");
                        request.getSession().setAttribute("areaId", request.getParameter("areaId"));
                        request.getSession().setAttribute("roomId", request.getParameter("roomId"));
                        
                        //Save to Session if coming from add issue for room or for area
                        request.getSession().setAttribute("originType",request.getParameter("originType"));
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    
                    else if (request.getParameter("originSection").equals("deleteAreaButton")) {
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
                    }
                    //If an issue needs creating
                    else if(request.getParameter("originSection").equals("addIssue")){
                        request.getSession().setAttribute("source", "");
                        buildingId = build.getbuildingId();
                        int healthcheck_id = getBuildingHealthcheck(request, buildingId);
                        String description = request.getParameter("description");
                        String recommendation = request.getParameter("recommendation");
                        int areaId = Integer.parseInt(request.getSession().getAttribute("areaId").toString());
                        int roomId = Integer.parseInt(request.getSession().getAttribute("roomId").toString());
                        //create new issue
                        bldgCtrl.createIssue(buildingId, areaId, roomId, description, recommendation, healthcheck_id);
                        
                        //Fetch the current healthcheck and its issues for the chosen building 
                        healthcheckId = getBuildingHealthcheck(request, buildingId);
                        getHealthcheckIssues(request, healthcheckId);
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    //If an issue needs deleting
                    else if(request.getParameter("originSection").equals("deleteIssueButton")){
                        int issueId = Integer.parseInt(request.getParameter("issueId"));
                        //Delete issue
                        bldgCtrl.deleteIssue(issueId);
                        
                        //Fetch the current healthcheck and its issues for the chosen building 
                        healthcheckId = getBuildingHealthcheck(request, buildingId);
                        getHealthcheckIssues(request, healthcheckId);
                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    //If a healthcheck PDF report needs deleting
                    else if(request.getParameter("originSection").equals("createPDFButton")){
                        
                        
                        
                        
                        String pdfName = "newpdfname";
                        //String bName = build.getName();
                        String bName = "aaa";
                        String bAddress = "";
                        String bPostCode = "2800";
                        String bCity = "";
                        String bConstructionYear = "1980";
                        String bSQM = "200";
                        String bPurpose = "";
                        String bOwner = "";
                        
                        
                        String folderPath = "C:\\Users\\Martin\\Documents\\NetBeansProjects\\Polygon\\web\\img";
                        String filePath ="C:\\Users\\Martin\\Documents\\NetBeansProjects\\Polygon\\";
                                
                        String imgFolderPath = folderPath;
                        String savePath = filePath;

                        
                        
                        
                        
                        String systemDir = System.getProperty("user.dir");
                        System.out.println(systemDir);

                        String picturePath = "";
                        /*
                        //Filechooser for selecting an image for the generated PDF
                        JFileChooser choose = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg files", "jpg");
                        choose.setFileFilter(filter);
                        String picturePath = "";
                        String folderPath = "";
                        int returnVal = choose.showOpenDialog(choose);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {

                            picturePath = choose.getSelectedFile().getAbsolutePath();
                            folderPath = "" + choose.getCurrentDirectory();
                            System.out.println(picturePath);
                            System.out.println(folderPath + " Folder sti");

                            System.out.println(picturePath);
                        }*/

                        pdfwt.createPDF(pdfName, bName, bAddress,
                                Integer.parseInt(bPostCode), bCity, Integer.parseInt(bConstructionYear),
                                Integer.parseInt(bSQM), bPurpose, bOwner, picturePath, imgFolderPath, savePath);

                        response.sendRedirect("technicianViewBuilding.jsp?value=" + build.getbuildingId() + "");
                    break;

                        
                        
                        
                        
                    }

                    break;
                    
                    
                    

                case "editProfile":

                    //Retrieve form input values from editProfile.jsp
                    String uEmail = request.getParameter("email");
                    String uPassword = request.getParameter("password");
                    String uName = request.getParameter("name");
                    int uPhone = Integer.parseInt(request.getParameter("phonenumber"));
                    String uCompany = request.getParameter("company");
                    String uAddress = request.getParameter("address");
                    int uPostcode = Integer.parseInt(request.getParameter("postcode"));
                    String uCity = request.getParameter("city");
                    int uSelectedUser = user_id;

                    //Save the user's edited values to the user database
                    usrCtrl.editUser(uSelectedUser, uEmail, uPassword, uName, uPhone, uCompany, uAddress, uPostcode, uCity);

                    //Resets/updates the userName, password and updates the displayed username
                    request.getSession().setAttribute("email", uEmail);
                    request.getSession().setAttribute("password", uPassword);

                    //Updates the editUserTable with the new/updated user information
                    request.getSession().setAttribute("uEmail", uEmail);
                    request.getSession().setAttribute("uPassword", uPassword);
                    request.getSession().setAttribute("uName", uName);
                    request.getSession().setAttribute("uPhonenumber", uPhone);
                    request.getSession().setAttribute("uCompany", uCompany);
                    request.getSession().setAttribute("uAddress", uAddress);
                    request.getSession().setAttribute("uPostcode", uPostcode);
                    request.getSession().setAttribute("uCity", uCity);

                    //redirect to user.jsp
                    response.sendRedirect("technician.jsp?success=UpdateSuccessful");

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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //Fetches the issues for the current healthcheck
    public void getHealthcheckIssues(HttpServletRequest request, int healthcheckId){
        try {
            healthcheckIssues.clear();
            healthcheckIssues = bldgCtrl.getHealthcheckIssues(healthcheckId);
            request.getSession().setAttribute("healthcheckIssues", healthcheckIssues);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //Get the current healthcheck for a building
    public int getBuildingHealthcheck(HttpServletRequest request, int buildingId){
        int healthcheck_id = 0;
        try {
            buildingHealthchecks.clear();
            buildingHealthchecks = bldgCtrl.getBuildingHealthchecks(buildingId);
            request.getSession().setAttribute("buildingHealthchecks", buildingHealthchecks);
            for (int i = 0; i < buildingHealthchecks.size(); i++) {
                if(buildingHealthchecks.get(i).getbuildingId()==buildingId){
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