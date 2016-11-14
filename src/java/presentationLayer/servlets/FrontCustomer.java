package presentationLayer.servlets;

import dataAccessLayer.PDFCreator;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import serviceLayer.controllers.BuildingController;
import serviceLayer.controllers.UserController;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;
import serviceLayer.exceptions.CustomException;

/**
 * Servlet used to check what type of user is logging in.
 */
@WebServlet(name = "FrontC", urlPatterns = {"/FrontC"})
public class FrontCustomer extends HttpServlet {

    private ArrayList<Building> userBuildings = new ArrayList();
    private ArrayList<User> userList = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<Area> buildingAreas = new ArrayList();
    private ArrayList<Room> buildingRooms = new ArrayList();

    private UserController usrCtrl = new UserController();
    private BuildingController bldgCtrl = new BuildingController();
    private User user = null;
    private int user_id;
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
        
        
        
        String errMsg = null;
        String origin = request.getParameter("origin");

        
        
        try {

            //If we are coming from the Front servlet
            if(request.getSession().getAttribute("sourcePage").toString().equals("Front")){

                //Save the logged in user's id
                user_id = (Integer) request.getSession().getAttribute("user_id");

                refreshBuilding(user_id);
                request.getSession().setAttribute("userBuildings", userBuildings);
                //response.sendRedirect("viewBuilding.jsp");
                response.sendRedirect("user.jsp");
            }
        

            
            switch (origin) {
            
                //default:
                    
                    //break;
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

                case "viewBuilding":

                    //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                    Building build = (Building) request.getSession().getAttribute("buildingBeingEdited");
                    
                    //If 'Request healthcheck' button was clicked
                    if(request.getParameter("originSection").equals("healthcheckButton")){
                        request.getSession().setAttribute("source", "healthcheckButton");
                        
                        int healthcheckValueToWrite;
                        //If the building's healthcheck pending status needs setting to false
                        if(request.getParameter("originValue").equals("cancel")){
                            healthcheckValueToWrite = 0;
                        }
                        //If the building's healthcheck pending status needs setting to true
                        else{
                            healthcheckValueToWrite = 1;
                        }
                        
                        //Save values to database
                        bldgCtrl.toggleHealthcheck(build.getBuilding_id(), healthcheckValueToWrite);
                        
                        //Refresh the logged in user's buildings overview
                        refreshBuilding(user_id);
                        
                        //Fetch areas and rooms for selected building
                        //refreshAreas(build.getBuilding_id());
                        //refreshRooms(build.getBuilding_id());
                        
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    }
                    
                    //If 'Create area' button was clicked
                    if(request.getParameter("originSection").equals("createAreaButton")){
                        request.getSession().setAttribute("source", "createAreaButton");
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    }
                    //If an area needs deleting
                    else if(request.getParameter("originSection").equals("deleteAreaButton")){
                        request.getSession().setAttribute("source", "deleteAreaButton");
                        
                        //Retrieve form input values from viewBuilding
                        int area_id = Integer.parseInt(request.getParameter("areaId"));
                        
                        //Save values to database
                        bldgCtrl.deleteArea(area_id);
                        
                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");
                    
                        //Fetch areas and rooms for selected building
                        refreshAreas(build.getBuilding_id());
                        refreshRooms(build.getBuilding_id());

                        //Save areas and rooms in Session
                        request.getSession().setAttribute("buildingAreas", buildingAreas);
                        request.getSession().setAttribute("buildingRooms", buildingRooms);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    }

                    //If a new area needs creating
                    else if(request.getParameter("originSection").equals("createArea")){
                        request.getSession().setAttribute("source", "createArea");
                        //Retrieve form input values from viewBuilding
                        String areaName = request.getParameter("areaName");
                        String areaDesc = request.getParameter("areaDesc");
                        int areaSqm = Integer.parseInt(request.getParameter("areaSqm"));
                        int building_id = build.getBuilding_id();
                        //Save values to database
                        bldgCtrl.createArea(areaName, areaDesc, areaSqm, building_id);
                        
                        //Fetch areas and rooms for selected building
                        refreshAreas(building_id);
                        refreshRooms(building_id);

                        //Save areas and rooms in Session
                        request.getSession().setAttribute("buildingAreas", buildingAreas);
                        request.getSession().setAttribute("buildingRooms", buildingRooms);
                        
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    }
                    
                    //If 'Create area' button was clicked
                    else if(request.getParameter("originSection").equals("createRoomButton")){
                        request.getSession().setAttribute("source", "createRoomButton");
                        request.getSession().setAttribute("areaId",request.getParameter("areaId"));

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    }
                    
                    //If a new room needs creating
                    else if(request.getParameter("originSection").equals("createRoom")){
                        request.getSession().setAttribute("source", "createRoom");
                        //Retrieve form input values from viewBuilding
                        String roomName = request.getParameter("roomName");
                        String roomDesc = request.getParameter("roomDesc");
                        int roomSqm = Integer.parseInt(request.getParameter("roomSqm"));
                        int area_id = Integer.parseInt(request.getSession().getAttribute("areaId").toString());
                        
                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");
                        int building_id = build.getBuilding_id();

                        //Save values to database
                        bldgCtrl.createRoom(roomName, roomDesc, roomSqm, area_id);
                        
                        //Fetch areas and rooms for selected building
                        refreshAreas(building_id);
                        refreshRooms(building_id);

                        //Save areas and rooms in Session
                        request.getSession().setAttribute("buildingAreas", buildingAreas);
                        request.getSession().setAttribute("buildingRooms", buildingRooms);
                        
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    }
                    
                    //If an area needs deleting
                    else if(request.getParameter("originSection").equals("deleteRoomButton")){
                        request.getSession().setAttribute("source", "deleteRoomButton");
                        
                        //Retrieve form input values from viewBuilding
                        int room_id = Integer.parseInt(request.getParameter("roomId"));
                        //int area_id = 6;
                        
                        //Save values to database
                        bldgCtrl.deleteRoom(room_id);
                        
                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");
                    
                        //Fetch areas and rooms for selected building
                        refreshAreas(build.getBuilding_id());
                        refreshRooms(build.getBuilding_id());
                        //refreshRooms(building_id);

                        //Save areas and rooms in Session
                        request.getSession().setAttribute("buildingAreas", buildingAreas);
                        request.getSession().setAttribute("buildingRooms", buildingRooms);

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    }
                    
                    //If 'Edit building details' button was clicked
                    else if(request.getParameter("originSection").equals("editBuildingButton")){
                        request.getSession().setAttribute("source", "editBuildingButton");
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    }
                    
                    //If the building needs editing
                    else if(request.getParameter("originSection").equals("editBuilding")){
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
                        bldgCtrl.viewBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpos, sq);
                        //Refresh the logged in user's buildings overview
                        refreshBuilding(user.getUser_id());
                        //request.getSession().setAttribute("userBuilding", userBuildings);
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getBuilding_id() + "");
                    } 
                    
                    break;
                
                case "editProfile":

                    System.out.println("Entered edit profile");

                    //Retrieve form input values from editProfile.jsp
                    String uEmail = request.getParameter("email");
                    String uPassword = request.getParameter("password");
                    String uName = request.getParameter("name");
                    int uPhone = Integer.parseInt(request.getParameter("phonenumber"));
                    String uCompany = request.getParameter("company");
                    String uAddress = request.getParameter("address");
                    int uPostcode = Integer.parseInt(request.getParameter("postcode"));
                    String uCity = request.getParameter("city");
                    int uSelectedUser = user.getUser_id();

                    //Displayes what data is being pulled down into the usrCtrl.editUser
//                    System.out.println("DATA confirmed funneled down into editProfile case");
//                    System.out.println(uEmail);
//                    System.out.println(uPassword);
//                    System.out.println(uName);
//                    System.out.println(uPhone);
//                    System.out.println(uCompany);
//                    System.out.println(uAddress);
//                    System.out.println(uPostcode);
//                    System.out.println(uCity);
//                    System.out.println(uSelectedUser);
                    //Basic idea for checking user email ! Not working atm!
//                    System.out.println(userList.size());                                       
//                    System.out.println("user email: " + user.getEmail());
//                    System.out.println("new user email:" + uEmail);
//                    
//                    
//                    
//                    for (int j = 0; j < userList.size(); j++) {
//                        System.out.println("users: " + userList.get(j).getEmail());
//                    }
//                    for (int i = 0; i < userList.size(); i++) {
//                        System.out.println("Second for-loop");
//                        if (uEmail == userList.get(i).getEmail()) {
//                            
//                            System.out.println("EMAIL ALREADY EXIST!");
//                            response.sendRedirect("user.jsp?success=EmailAlreadyExist");
//                            
//                        }
//                    }
                    //Save the users edited values to the user database
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

                    System.out.println("Response inc.");
                    //redirect to user.jsp
                    response.sendRedirect("user.jsp?success=UpdateSuccessful");

//    //fix æøå bug here!
                    break;

                case "update":

                case "newCustomer":

                    //If no user is logged in. (user == null)
                    if (request.getSession().getAttribute("user") == null) {

                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        String name = request.getParameter("name");
                        String phone = request.getParameter("phone"); //HUSK INTEGER.PARSE!
                        String company = request.getParameter("company");
                        String address = request.getParameter("address");
                        String postcode = request.getParameter("postcode"); //HUSK INTEGER.PARSE!
                        String city = request.getParameter("city");

//                        Check that the parameters are being read
//                        System.out.println(email + " em");
//              
                        System.out.println(password + " pass");
//                        System.out.println(name + " nam");
//                        System.out.println(phone+ " ph");
//                        System.out.println(company+ " com");
//                        System.out.println(address+ " add");
//                        System.out.println(postcode+ " post");
//                        System.out.println(city + " city");

                        //test password match
//                        if(!request.getSession().getAttribute("password").equals(request.getSession().getAttribute("passwordConfirm")) ){
//                            System.out.println("PASSWORD NOT MATCHING!");
//                            JOptionPane.showMessageDialog(null, "PASSWORD NOT MATCHING!");
//                             response.sendRedirect("newCustomer.jsp?error=" + URLEncoder.encode(errMsg, "UTF-8") + " PASSWORD NOT MATCHING");
//                        }
                        try {
                            System.out.println("creating user");
                            //Create user
                            usrCtrl.createUser(email, password, name, Integer.parseInt(phone), company, address, Integer.parseInt(postcode), city);
                            //If successful, redirect
                            System.out.println("Index redirect");
                            response.sendRedirect("index.jsp?success");

                        } catch (CustomException e) {

                            errMsg = e.getMessage();
                            response.sendRedirect("newCustomer.jsp?error=" + URLEncoder.encode(errMsg, "UTF-8"));

                        }

                    } else {

                        //if user is logged in, as in not null.
                        response.sendRedirect("user.jsp");

                    }

                    break;

                case "createBuilding":

                    //If no user is logged in. (user == null)
                    if (user != null) {

                        int user_id = user.getUser_id();
                        String assigned_tech_id = request.getParameter("assigned_tech_id");
                        String healthcheck_pending = request.getParameter("healthcheck_pending");
                        String name = request.getParameter("name");
                        String address = request.getParameter("address");
                        String postcode = request.getParameter("postcode");
                        String city = request.getParameter("city");
                        String construction_year = request.getParameter("construction_year");
                        String purpose = request.getParameter("purpose");
                        String sqm = request.getParameter("sqm");
                        try {

                            //createBuilding
                            bldgCtrl.createBuilding(name, address, Integer.parseInt(postcode), city, Integer.parseInt(construction_year), purpose, Integer.parseInt(sqm), user_id);
                            refreshBuilding(user_id);
                            //If successful, redirect
                            response.sendRedirect("user.jsp?sucess=buildingAdded");

                        } catch (CustomException e) {

                            errMsg = e.getMessage();
                            response.sendRedirect("newCustomer.jsp?error=" + URLEncoder.encode(errMsg, "UTF-8"));

                        }

                    } else {

                        //Redirect to index if no user is logged in.
                        response.sendRedirect("index.jsp?=notLoggedIn");

                    }

                    break;

                //case "viewBuilding":
                //needs to recieve the unique id for the user assigned to the building also.
                //JOptionPane.showMessageDialog(null, "Test!");
//                    if (user != null) {
//
//                        int user_id = user.getUser_id();
//
//                        String address = request.getParameter("address");
//                        String postcode = request.getParameter("postcode");
//                        String city = request.getParameter("city");
//
//                        try {
//
//                            //createBuilding
//                            bldgCtrl.createBuilding(user_id, address, Integer.parseInt(postcode), city);
//                            refreshBuilding(user_id);
//                            //If successful, redirect
//                            response.sendRedirect("user.jsp?sucess=buildingEdited");
//
//                        } catch (CustomException e) {
//
//                            errMsg = e.getMessage();
//                            response.sendRedirect("newCustomer.jsp?error=" + URLEncoder.encode(errMsg, "UTF-8"));
//
//                        }
//
//                    } else {
//
//                        //Redirect to index if no user is logged in.
//                        response.sendRedirect("index.jsp?=notLoggedIn");
//
//                    }
//
//                    break;
                case "adminUsers":
                    break;

                case "blankTestPDF":

                    String testPDF = request.getParameter("pdfname");
                    pdfwt.testBlank(testPDF);

                    break;

                case "pdfwithtext":

                    String pdfName = request.getParameter("pdfname");
                    String bName = request.getParameter("buildingname");
                    String bAddress = request.getParameter("buildingadddress");
                    String bPostCode = request.getParameter("buildingpostcode"); //String that needs to parse into int!
                    String bCity = request.getParameter("buildingcity");
                    String bConstructionYear = request.getParameter("constructionyear");  //String that needs to parse into int!
                    String bSQM = request.getParameter("buildingsqm");  //String that needs to parse into int!
                    String bPurpose = request.getParameter("buildingpurpose");
                    String bOwner = request.getParameter("buildingsowner");
                    String imgFolderPath = request.getParameter("folderPath");
                    String savePath = request.getParameter("savePath");

                    String systemDir = System.getProperty("user.dir");
                    System.out.println(systemDir);

//                    //Folderchooser
//                    JFileChooser chooser = new JFileChooser();
//                    chooser.setCurrentDirectory(new java.io.File("."));
//                    chooser.setDialogTitle("choosertitle");
//                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//                    chooser.setAcceptAllFileFilterUsed(false);
//
//                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//                        System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
//                        System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
//                    } else {
//                        System.out.println("No Selection ");
//
//                    }
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
                       

                    }

                    System.out.println(picturePath);

                    pdfwt.pdfWithText(pdfName, bName, bAddress,
                            Integer.parseInt(bPostCode), bCity, Integer.parseInt(bConstructionYear),
                            Integer.parseInt(bSQM), bPurpose, bOwner, picturePath, imgFolderPath, savePath);

                    response.sendRedirect("index.jsp?sucess=PDFCreated");
                    break;

            }

        } catch (Exception e) {

        }

    }

    //Refreshes the list of buildings
    public void refreshBuilding(int user_id) throws CustomException {

        userBuildings.clear();
        userBuildings = bldgCtrl.getBuildings(user_id);

    }
    //Refreshes the list of buildings

    public void refreshAllBuildings() throws CustomException {

        allBuildings.clear();
        allBuildings = bldgCtrl.getAllBuildings();

    }

    //Refreshes the list of building areas
    public void refreshAreas(int building_id) throws CustomException {
        buildingAreas.clear();
        buildingAreas = bldgCtrl.getAreas(building_id);
    }

    //Refreshes the list of building rooms
    public void refreshRooms(int building_id) throws CustomException {
        buildingRooms.clear();
        buildingRooms = bldgCtrl.getRooms(building_id);
    }

    public void refreshUsers() throws CustomException {

        userList.clear();
        userList = usrCtrl.getUsers();
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
