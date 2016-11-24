package presentationLayer.servlets;

import dataAccessLayer.PDFCreator;
import java.io.IOException;
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
import serviceLayer.entities.Room;
import serviceLayer.entities.User;

/**
 * Servlet that handles the customer. 
 */
@WebServlet(name = "NavigatorServlet", urlPatterns = {"/NavigatorServlet"})
public class NavigatorServlet extends HttpServlet {

    private ArrayList<Building> userBuildings = new ArrayList();
    private ArrayList<User> userList = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private ArrayList<Area> buildingAreas = new ArrayList();
    private ArrayList<Room> buildingRooms = new ArrayList();

    private UserController usrCtrl = new UserController();
    private BuildingController bldgCtrl = new BuildingController();
    private User user = null;
    private String uEmail, uPassword, uName, uCompany, uAddress, uCity;
    private int user_id, uPhone, uPostcode, uUser_id;
    private boolean editingOtherUserProfile = false;
    private Building build;
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

            //Save the logged in user's id
            user_id = (Integer) request.getSession().getAttribute("user_id");
            String email = request.getSession().getAttribute("email").toString();
            //Get user object with the above email
            user = usrCtrl.getUser(email);
            build  = (Building) request.getSession().getAttribute("buildingBeingEdited");
        
            String errMsg = null;
            String origin = request.getParameter("origin");
            
            switch (origin) {
        
                case "createBuilding":

                    //If no user is logged in. (user == 0)
                    if (user_id>0) {

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
                            //Refresh the user's buildings
                            refreshBuilding(user_id);
                            request.getSession().setAttribute("userBuildings", userBuildings);
                            redirectUser(request, response);
                        } catch (Exception e) {

                            errMsg = e.getMessage();
                            response.sendRedirect("newCustomer.jsp?error=" + URLEncoder.encode(errMsg, "UTF-8"));

                        }

                    } else {

                        //Redirect to index if no user is logged in.
                        response.sendRedirect("index.jsp?=notLoggedIn");

                    }
                break;
                
                case "deleteBuildingButton":
                
                    try {
                        int building_id = build.getBuilding_id();
                        bldgCtrl.deleteBuilding(building_id);
                        //Refresh the user's buildings
                        refreshBuilding(user_id);
                        request.getSession().setAttribute("userBuildings", userBuildings);
                        redirectUser(request, response);
                    } catch (Exception e) {

                        errMsg = e.getMessage();
                        response.sendRedirect("newCustomer.jsp?error=" + URLEncoder.encode(errMsg, "UTF-8"));

                    }
                break;
                
                case "editOtherProfileButton":
                    User userToEdit = usrCtrl.getUser(request.getParameter("userEmail"));
                    populateEditUserPage(request, userToEdit);
                    //Toggle boolean
                    editingOtherUserProfile = true;
                    request.getSession().setAttribute("userToEdit", userToEdit.getUser_id());
                    //Tell the page redirected to where it was accessed from, in order to display the corresponding sidebar menu
                    request.getSession().setAttribute("source", request.getParameter("source"));
                    response.sendRedirect("editProfile.jsp");
                break;
                
                case "editProfileButton":
                    populateEditUserPage(request, user);
                    //Tell the page redirected to where it was accessed from, in order to display the corresponding sidebar menu
                    request.getSession().setAttribute("source", request.getParameter("source"));
                    response.sendRedirect("editProfile.jsp");
                break;
                    
                case "editProfile":
                    
                    //Check if editing another user's profile or one's own profile
                    if(editingOtherUserProfile){
                        editUser(request, (Integer) request.getSession().getAttribute("userToEdit"));
                        editingOtherUserProfile=false;
                    }
                    else if(!editingOtherUserProfile){
                        editUser(request, user_id);
                    }
                    
                    //Fetch user from the user's id
                    user = usrCtrl.getUser(user_id); 
                    
                    //Resets/updates the userName, password and updates the displayed username
                    request.getSession().setAttribute("email", user.getEmail());
                    request.getSession().setAttribute("password", user.getPassword());
                    
                    refreshUsers(request);
                    
            
                    /*
                    //Updates the editUserTable with the new/updated user information
                    request.getSession().setAttribute("uEmail", uEmail);
                    request.getSession().setAttribute("uPassword", uPassword);
                    request.getSession().setAttribute("uName", uName);
                    request.getSession().setAttribute("uPhonenumber", uPhone);
                    request.getSession().setAttribute("uCompany", uCompany);
                    request.getSession().setAttribute("uAddress", uAddress);
                    request.getSession().setAttribute("uPostcode", uPostcode);
                    request.getSession().setAttribute("uCity", uCity);
                    */
                    redirectUser(request, response);

                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //Redirects the user to the appropriate page
    public void redirectUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            if(request.getParameter("originSection").equals("Kunde")){
                response.sendRedirect("user.jsp?success");
            }
            else if(request.getParameter("originSection").equals("Tekniker")){
                response.sendRedirect("technician.jsp?success");
            }
            else if(request.getParameter("originSection").equals("Administration")){
                response.sendRedirect("admin.jsp?success");
            }
        } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    
    
    //Populates the editUser.jsp fields
    public void populateEditUserPage(HttpServletRequest request, User user) throws Exception {
        //Retrieve the user's data
        uEmail = user.getEmail();
        uPassword = user.getPassword();
        uName = user.getName();
        uPhone = user.getPhone();
        uCompany = user.getCompany();
        uAddress = user.getAddress();
        uPostcode = user.getPostcode();
        uCity = user.getCity();
        uUser_id = user.getUser_id();

        //Save data to Session
        request.getSession().setAttribute("uEmail", uEmail);
        request.getSession().setAttribute("uPassword", uPassword);
        request.getSession().setAttribute("uName", uName);
        request.getSession().setAttribute("uPhone", uPhone);
        request.getSession().setAttribute("uCompany", uCompany);
        request.getSession().setAttribute("uAddress", uAddress);
        request.getSession().setAttribute("uPostcode", uPostcode);
        request.getSession().setAttribute("uCity", uCity);
        request.getSession().setAttribute("uUser_id", uUser_id);
    }
        
    //Edits the user's details
    public void editUser(HttpServletRequest request, int user_id) throws Exception {
        //Retrieve form input values from editProfile.jsp
        uEmail = request.getParameter("email");
        uPassword = request.getParameter("password");
        uName = request.getParameter("name");
        uPhone = Integer.parseInt(request.getParameter("phonenumber"));
        uCompany = request.getParameter("company");
        uAddress = request.getParameter("address");
        uPostcode = Integer.parseInt(request.getParameter("postcode"));
        uCity = request.getParameter("city");
        int uSelectedUser = user_id;
        //Save the user's edited values to the user database
        usrCtrl.editUser(uSelectedUser, uEmail, uPassword, uName, uPhone, uCompany, uAddress, uPostcode, uCity);
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
    public void refreshAreas(int building_id) throws Exception {
        buildingAreas.clear();
        buildingAreas = bldgCtrl.getAreas(building_id);
    }

    //Refreshes the list of building rooms
    public void refreshRooms(int building_id) throws Exception {
        buildingRooms.clear();
        buildingRooms = bldgCtrl.getRooms(building_id);
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
