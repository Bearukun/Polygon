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
import serviceLayer.exceptions.CustomException;

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
    private int user_id;
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
                        } catch (CustomException e) {

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
                    } catch (CustomException e) {

                        errMsg = e.getMessage();
                        response.sendRedirect("newCustomer.jsp?error=" + URLEncoder.encode(errMsg, "UTF-8"));

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
                    
                    redirectUser(request, response);

                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //Redirects the user to the appropriate page
    public void redirectUser(HttpServletRequest request, HttpServletResponse response) throws CustomException {
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
