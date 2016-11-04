package presentationLayer.servlets;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import dataAccessLayer.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import serviceLayer.controllers.BuildingController;
import serviceLayer.controllers.UserController;
import serviceLayer.enties.Building;
import serviceLayer.enties.User;
import serviceLayer.exceptions.CustomException;

/**
 * Servlet used to check what type of user is logging in.
 */
@WebServlet(name = "Front", urlPatterns = {"/Front"})
public class Front extends HttpServlet {

    private ArrayList<Building> tempAL = new ArrayList();
    private ArrayList<User> tempUL = new ArrayList();
    private ArrayList<Building> allBuildings = new ArrayList();
    private UserController usrCtrl = new UserController();
    private BuildingController bldgCtrl = new BuildingController();
    private User user = null;

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

        response.setContentType("text/html;charset=UTF-8");
        String errMsg = null;
        String origin = request.getParameter("origin");

        try {

            switch (origin) {

                case "login":

                    if (request.getSession().getAttribute("user") == null) {

                        String email = request.getParameter("email");
                        String password = request.getParameter("password");

                        try {

                            user = usrCtrl.login(email, password);

                            if (user != null) {

                                request.getSession().setAttribute("email", user.getEmail().toString());

                                //Translate user type:
                                if (user.getType().toString().equals("CUSTOMER")) {

                                    request.getSession().setAttribute("type", "Kunde");

                                } else if (user.getType().toString().equals("TECHNICIAN")) {

                                    request.getSession().setAttribute("type", "Tekniker");

                                } else {

                                    request.getSession().setAttribute("type", "Administration");
                                }

                                if (user.getType().equals(User.type.ADMIN)) {
                                    refreshUsers(); 
                                    refreshAllBuildings();
                                    request.getSession().setAttribute("tempUL", tempUL);
                                    request.getSession().setAttribute("allBuildings", allBuildings);
                                    response.sendRedirect("admin.jsp");
                                    break;

                                } else if (user.getType().equals(User.type.TECHNICIAN)) {

                                    response.sendRedirect("technician.jsp");
                                    break;

                                } else {

                                    //Refreshes and populates the arrayList with buildings for the user.
                                    refreshBuilding(user.getUser_id());
                                    request.getSession().setAttribute("tempAL", tempAL);
                                    response.sendRedirect("user.jsp");

                                    break;

                                }

                            }

                            //If something goes wrong, we need a way to show it.
                        } catch (CustomException e) {

                            response.sendRedirect("#" + e.getMessage());

                        }

                    } else {

                        response.sendRedirect("index.jsp?wrongLogin");

                    }

                    break;

                //Logout of the website
                case "logout":

                    request.getSession().invalidate();
                    response.sendRedirect("index.jsp#");

                    break;
                    
                case "editBuilding":
                    //Retrieve form input values from editBuilding.jsp
                    String addres = request.getParameter("address");
                    int postcod = Integer.parseInt(request.getParameter("postcode"));
                    String cit = request.getParameter("city");
                    int selectedBuilding = Integer.parseInt(request.getParameter("selectedBuilding"));
                    
                    //Save values to database
                    bldgCtrl.editBuilding(selectedBuilding, addres, postcod, cit);

                    //Refresh the logged in user's buildings overview
                    //refreshAllBuildings();
                    refreshBuilding(user.getUser_id());
                    request.getSession().setAttribute("tempAL", tempAL);
                    
                    //redirect to user.jsp
                    response.sendRedirect("user.jsp?success=UpdateSuccessful");
    //fix æøå bug here!
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
//                        System.out.println(password + " pass");
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
                        String name = request.getParameter("name");
                        String address = request.getParameter("address");
                        String postcode = request.getParameter("postcode");
                        String city = request.getParameter("city");
                        
                        String construction_year = request.getParameter("construction_year");
                        String purpose = request.getParameter("purpose");
                        String sqm = request.getParameter("sqm");
                        try {

                            //createBuilding
                            bldgCtrl.createBuilding( name, address, Integer.parseInt(postcode), city, Integer.parseInt(construction_year), purpose, Integer.parseInt(sqm), user_id);
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
                    
                //case "editBuilding":
                    
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
                    
                    
                    
            }

        } catch (Exception e) {
        }

    }

    //Refreshes the list of buildings
    public void refreshBuilding(int user_id) throws CustomException {

        tempAL.clear();
        tempAL = bldgCtrl.getBuildings(user_id);

    }
    //Refreshes the list of buildings
    public void refreshAllBuildings() throws CustomException {

        allBuildings.clear();
        allBuildings = bldgCtrl.getAllBuildings();

    }

    public void refreshUsers() throws CustomException{
        
        tempUL.clear();
        tempUL = usrCtrl.getUsers();
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
