package presentationLayer.servlets;

import dataAccessLayer.PDFCreator;
import java.awt.AWTEventMulticaster;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import serviceLayer.controllers.BuildingController;
import serviceLayer.controllers.EmailController;
import serviceLayer.controllers.UserController;
import serviceLayer.entities.Area;
import serviceLayer.entities.Building;
import serviceLayer.entities.Room;
import serviceLayer.entities.User;

@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

    private ArrayList<Building> userBuildings = new ArrayList();
    private ArrayList<User> userList = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<Area> buildingAreas = new ArrayList();
    private ArrayList<Room> buildingRooms = new ArrayList();
    private ArrayList<String> buildingPurposes = new ArrayList(Arrays.asList("Landbrug","Erhverv","Bolig","Uddannelse","Offentlig","Industriel","Militær","Religiøs","Transport","Andet"));
    
    private EmailController emailCtrl = new EmailController();
    private UserController usrCtrl = new UserController();
    private BuildingController bldgCtrl = new BuildingController();
    private User user = null;
    private int user_id;
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
            if(request.getSession().getAttribute("sourcePage").toString().equals("LoginServlet")){
                request.getSession().setAttribute("sourcePage","Invalid");
                //Save the logged in user's id
                user_id = (Integer) request.getSession().getAttribute("user_id");
                refreshUsers(request);
                refreshAllBuildings(request);
                compileAdminOverviewStats(request);
                compileAdminOverviewBuildingStats(request);
                response.sendRedirect("admin.jsp");
            }
        
            String errMsg = null;
            if(request.getParameter("origin")!=null){
                origin = request.getParameter("origin");
            }
            
            switch (origin) {
        
                

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
                        bldgCtrl.toggleHealthcheck(build.getbuildingId(), healthcheckValueToWrite);
                        
                        //Refresh the logged in user's buildings overview
                        refreshBuilding(user_id);
                        
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    
                    //If 'Create area' button was clicked
                    if(request.getParameter("originSection").equals("createAreaButton")){
                        request.getSession().setAttribute("source", "createAreaButton");
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    //If an area needs deleting
                    else if(request.getParameter("originSection").equals("deleteAreaButton")){
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
                    }

                    //If a new area needs creating
                    else if(request.getParameter("originSection").equals("createArea")){
                        request.getSession().setAttribute("source", "createArea");
                        //Retrieve form input values from viewBuilding
                        String areaName = request.getParameter("areaName");
                        String areaDesc = request.getParameter("areaDesc");
                        int areaSqm = Integer.parseInt(request.getParameter("areaSqm"));
                        int buildingId = build.getbuildingId();
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
                    }
                    
                    //If 'Create area' button was clicked
                    else if(request.getParameter("originSection").equals("createRoomButton")){
                        request.getSession().setAttribute("source", "createRoomButton");
                        request.getSession().setAttribute("areaId",request.getParameter("areaId"));

                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    }
                    
                    //If a new room needs creating
                    else if(request.getParameter("originSection").equals("createRoom")){
                        request.getSession().setAttribute("source", "createRoom");
                        //Retrieve form input values from viewBuilding
                        String roomName = request.getParameter("roomName");
                        String roomDesc = request.getParameter("roomDesc");
                        int roomSqm = Integer.parseInt(request.getParameter("roomSqm"));
                        int areaId = Integer.parseInt(request.getSession().getAttribute("areaId").toString());
                        
                        //Retrieve the building being edited (saved in the Session) and save it in the reference object build
                        build = (Building) request.getSession().getAttribute("buildingBeingEdited");
                        int buildingId = build.getbuildingId();

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
                    }
                    
                    //If an area needs deleting
                    else if(request.getParameter("originSection").equals("deleteRoomButton")){
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
                    }
                    
                    //If 'Edit building details' button was clicked
                    else if(request.getParameter("originSection").equals("editBuildingButton")){
                        request.getSession().setAttribute("source", "editBuildingButton");
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
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
                        bldgCtrl.editBuilding(selectedBuilding, buildingName, addres, postcod, cit, constructionYear, purpos, sq);
                        //Refresh the logged in user's buildings overview
                        refreshBuilding(user_id);
                        //redirect to viewBuilding into the specific building being edited
                        response.sendRedirect("viewBuilding.jsp?value=" + build.getbuildingId() + "");
                    } 
                    
                break;
                
                case "editOtherProfileButton":
                    
                    
                    
                    
                break;
                

                case "healthcheckButton":
                    
                    //Save list of technicians in the Session
                    request.getSession().setAttribute("techniciansList", getTechnicians());
                    response.sendRedirect("adminPendingBuildings.jsp");
                break;
                
                case "assignHealthcheckButton":
                    int buildingId = Integer.parseInt(request.getParameter("buildingId"));
                    int technicianId = Integer.parseInt(request.getParameter("selectedTechnician").split("\\|")[0]);
                    bldgCtrl.assignHealthcheck(buildingId, technicianId);
                    refreshAllBuildings(request);
                    response.sendRedirect("adminPendingBuildings.jsp");
                break;   
                
                case "newCustomer":
                    
                    String newUserEmail = request.getParameter("email");
                    String newUserPassword = request.getParameter("password");
                    String newUserConfirmedPassword = request.getParameter("passwordConfirm");               
                    String newUserName = request.getParameter("name");                  
                    int newUserPhone = Integer.parseInt(request.getParameter("phone"));              
                    String newUserCompany = request.getParameter("company");                
                    String newUserAddress = request.getParameter("address");                   
                    int newUserPostcode = Integer.parseInt(request.getParameter("postcode"));          
                    String newUserCity = request.getParameter("city");
                    String newUserType = request.getParameter("type");
                     
                    usrCtrl.createUser(newUserEmail, newUserPassword, newUserName, newUserPhone, newUserCompany, newUserAddress, newUserPostcode, newUserCity, User.type.valueOf(newUserType));
                   
                    
                    if(newUserType.equalsIgnoreCase("ADMIN")){
                        
                        emailNewAdmin(newUserName, newUserEmail, newUserPhone, newUserAddress, newUserPostcode, newUserCity);
                        
                    } else if (newUserType.equalsIgnoreCase("TECHNICIAN")){
                        emailNewTechnician(newUserName, newUserEmail, newUserPhone, newUserAddress, newUserPostcode, newUserCity);
                        
                    } else {
                        emailNewCustomer(newUserName, newUserEmail, newUserPhone, newUserCompany, newUserAddress, newUserPostcode, newUserCity);
                    }
                    refreshUsers(request);
                   
                    response.sendRedirect("adminUsers.jsp");
                    
                    
                    
                break; 
                    
                case "createUserButton":
                    request.getSession().setAttribute("source", "admin");
                    response.sendRedirect("adminCreateUser.jsp");
                break;
                
                case "sendEmailToAllUsers":
                    
                    
                    String emailHeader = request.getParameter("emailHead");
                    String emailMessage = request.getParameter("emailMessage");
                    
                    //Loops through all registered users
                    for (int i = 0; i < userList.size(); i++) {
                         emailCtrl.send(usrCtrl.getUser(i).getEmail(), emailHeader, emailMessage);
                    }
                   
                    
                    
                    
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Compiles user statistics to display on admin overview page
    public void compileAdminOverviewStats(HttpServletRequest request){
        int countOfCustomers = 0;
        int countOfTechnicians = 0;
        int countOfAdministrators = 0;

        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getType().toString().equals("CUSTOMER")){
                countOfCustomers++;
            }
            else if(userList.get(i).getType().toString().equals("TECHNICIAN")){
                countOfTechnicians++;
            }
            else if(userList.get(i).getType().toString().equals("ADMIN")){
                countOfAdministrators++;
            }
        }
        
        request.getSession().setAttribute("countOfCustomers", countOfCustomers);
        request.getSession().setAttribute("countOfTechnicians", countOfTechnicians);
        request.getSession().setAttribute("countOfAdministrators", countOfAdministrators);
    }
     
    //Compiles building statistics to display on admin overview page
    public void compileAdminOverviewBuildingStats(HttpServletRequest request){
        
        int countOfLandbrug=0;
        int countOfErhverv=0;
        int countOfBolig=0;
        int countOfUddannelse=0;
        int countOfOffentlig=0;
        int countOfIndustriel=0;
        int countOfMilitær=0;
        int countOfReligiøs=0;
        int countOfTransport=0;
        int countOfAndet=0;
        for (int i = 0; i < allBuildings.size(); i++) {
            String buildingPurpose = allBuildings.get(i).getPurpose();
            switch (buildingPurpose){  
                case "Landbrug":
                    countOfLandbrug++;
                    break;

                case "Erhverv":
                    countOfErhverv++;
                    break;

                case "Bolig":
                    countOfBolig++;
                    break;

                case "Uddannelse":
                    countOfUddannelse++;
                    break;

                case "Offentlig":
                    countOfOffentlig++;
                    break;

                case "Industriel":
                    countOfIndustriel++;
                    break;

                case "Militær":
                    countOfMilitær++;
                    break;

                case "Religiøs":
                    countOfReligiøs++;
                    break;

                case "Transport":
                    countOfTransport++;
                    break;

                case "Andet":
                    countOfAndet++;
                    break;
            }
            
        }
        
        request.getSession().setAttribute("countOfLandbrug", countOfLandbrug);
        request.getSession().setAttribute("countOfErhverv", countOfErhverv);
        request.getSession().setAttribute("countOfBolig", countOfBolig);
        request.getSession().setAttribute("countOfUddannelse", countOfUddannelse);
        request.getSession().setAttribute("countOfOffentlig", countOfOffentlig);
        request.getSession().setAttribute("countOfIndustriel", countOfIndustriel);
        request.getSession().setAttribute("countOfMilitær", countOfMilitær);
        request.getSession().setAttribute("countOfReligiøs", countOfReligiøs);
        request.getSession().setAttribute("countOfTransport", countOfTransport);
        request.getSession().setAttribute("countOfAndet", countOfAndet);
    }
    
    //Deducts a list of technicians from the user list
    public ArrayList<User> getTechnicians() throws Exception {
        ArrayList<User> techniciansList = new ArrayList();
        for (User thisUser : userList) {
            if(thisUser.getType().toString().equals("TECHNICIAN")){
                techniciansList.add(new User(thisUser.getUser_id(), thisUser.getEmail(), thisUser.getName()));
            }
        }
        return techniciansList; 
    }
    
    //Refreshes the list of buildings
    public void refreshBuilding(int user_id) throws Exception {

        userBuildings.clear();
        userBuildings = bldgCtrl.getBuildings(user_id);

    }
    
    //Refreshes the list of buildings
    public void refreshAllBuildings(HttpServletRequest request) throws Exception {
        allBuildings.clear();
        allBuildings = bldgCtrl.getAllBuildings();
        request.getSession().setAttribute("allBuildings", allBuildings);
    }

    //Refreshes the list of building areas
    public void refreshAreas(int buildingId) throws Exception {
        buildingAreas.clear();
        buildingAreas = bldgCtrl.getAreas(buildingId);
    }

    //Refreshes the list of building rooms
    public void refreshRooms(int buildingId) throws Exception {
        buildingRooms.clear();
        buildingRooms = bldgCtrl.getRooms(buildingId);
    }

    public void refreshUsers(HttpServletRequest request) throws Exception {

        userList.clear();
        userList = usrCtrl.getUsers();
        request.getSession().setAttribute("userList", userList);
    }
    
    public void emailNewTechnician(String name, String email, Integer phone, String address, Integer postcode, String city){
           //Send confirmation email to new Admin
                            String emailNewCustomerHeader = "Hej " + name +" og velkommen til Polygons som Admin!";
                            String emailNewCustomerMessage = "Hej " + name + "!"
                                                                    
                                    +"\n\nAdmin\n\n"
                                  
                                    + "Navn: " + name +"\n"
                                    + "Email: " + email +"\n"
                                    
                                    + "Telefon: " + phone +"\n"
                                    
                                    + "Adresse: " + address +"\n"
                                    + "Postnummer: " + postcode + "\n " 
                                    + "By: "+ city 
                                    
                                    +"\n\n\n"
                                    + "Skulle de glemme deres kodeord til deres "
                                    + "bruger eller har andre spørgsmål, "
                                    + "så tøv ikke med at kontakte os!"
                                   
                                    + "\n\n\n"
                                    +" Med Venlig Hilsen"
                                    + "\n\n"
                                    +"Polygon"
                                    +"\n\n"
                                    +"Rypevang 5\n"
                                    +"3450 Allerød\n"
                                    +"Tlf. 4814 0055\n"
                                    + "sundebygninger@polygon.dk" ;
                   
                            emailCtrl.send(email, emailNewCustomerHeader, emailNewCustomerMessage);
    }
    
    public void emailNewAdmin(String name, String email, Integer phone, String address, Integer postcode, String city){
          //Send confirmation email to new Admin
                            String emailNewCustomerHeader = "Hej " + name +" og velkommen til Polygons som Tekniker!";
                            String emailNewCustomerMessage = "Hej " + name + "!"
                                                                    
                                    +"\n\nTekniker\n\n"
                                  
                                    + "Navn: " + name +"\n"
                                    + "Email: " + email +"\n"
                                    
                                    + "Telefon: " + phone +"\n"
                                    
                                    + "Adresse: " + address +"\n"
                                    + "Postnummer: " + postcode + "\n " 
                                    + "By: "+ city 
                                    
                                    +"\n\n\n"
                                    + "Skulle de glemme deres kodeord til deres "
                                    + "bruger eller har andre spørgsmål, "
                                    + "så tøv ikke med at kontakte os!"
                                   
                                    + "\n\n\n"
                                    +" Med Venlig Hilsen"
                                    + "\n\n"
                                    +"Polygon"
                                    +"\n\n"
                                    +"Rypevang 5\n"
                                    +"3450 Allerød\n"
                                    +"Tlf. 4814 0055\n"
                                    + "sundebygninger@polygon.dk" ;
                   
                            emailCtrl.send(email, emailNewCustomerHeader, emailNewCustomerMessage);
    }
    
    public void emailNewCustomer(String name, String email, Integer phone, String company, String address, Integer postcode, String city){
         //Send confirmation email to new Customer:
                            String emailNewCustomerHeader = "Hej " + name + " (" + company+ " )"+" og velkommen til Polygons's Sundebygninger!";
                            String emailNewCustomerMessage = "Hej " + name + "!"+
                                    "\n\nVi er glade for at de har registeret "
                                    + "deres virksomhed hos os"
                                    + "og vi ser frem til at arbejde sammen med "
                                    + "dem i den nærmeste fremtid!"
                                    + "\n\n\n"
                                    
                                    
                                    +"Her er hvad vi har registeret: "
                                    + "\n\n"
                                    + "Navn: " + name +"\n"
                                    + "Email: " + email +"\n"
                                    
                                    + "Telefon: " + phone +"\n"
                                    + "Firma: " + company + "\n"
                                    + "Adresse: " + address +"\n"
                                    + "Postnummer: " + postcode + "\n " 
                                    + "By: "+ city 
                                    +"\n\n\n"
                                    + "Skulle de glemme deres kodeord til deres "
                                    + "bruger eller har andre spørgsmål, "
                                    + "så tøv ikke med at kontakte os!"
                                   
                                    + "\n\n\n"
                                    +" Med Venlig Hilsen"
                                    + "\n\n"
                                    +"Polygon"
                                    +"\n\n"
                                    +"Rypevang 5\n"
                                    +"3450 Allerød\n"
                                    +"Tlf. 4814 0055\n"
                                    + "sundebygninger@polygon.dk" ;
                   
                            emailCtrl.send(email, emailNewCustomerHeader, emailNewCustomerMessage);
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
