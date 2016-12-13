package presentationLayer.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import serviceLayer.controllers.BuildingController;
import serviceLayer.controllers.EmailController;
import serviceLayer.controllers.UserController;
import serviceLayer.controllers.interfaces.BuildingControllerInterface;
import serviceLayer.controllers.interfaces.EmailControllerInterface;
import serviceLayer.controllers.interfaces.UserControllerInterface;
import serviceLayer.entities.User;
import serviceLayer.exceptions.PolygonException;

/**
 * Servlet used to check what type of user is logging in.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private UserControllerInterface usrCtrl = new UserController();
    private BuildingControllerInterface bldgCtrl = new BuildingController();
    private User user = null;
    private EmailControllerInterface emailCtrl = new EmailController();


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
        request.getSession().setAttribute("error", null);
        String errMsg = null;
        String origin = request.getParameter("origin");
        //System.getProperties().list(System.out);

        try {

            switch (origin) {

                case "loginAsUser":
                    
                    //Retrieve user email from adminUsers.jsp
                    int user_id = Integer.parseInt(request.getParameter("user_id"));
                    //Get user object with the above email
                    user = usrCtrl.getUser(user_id);
                    //Save where (which page) we are coming from
                    request.getSession().setAttribute("sourcePage", "LoginServlet");
                    //Save user values to Session

                    request.getSession().setAttribute("user_id", user.getUser_id());
                    request.getSession().setAttribute("email", user.getEmail());
                    request.getSession().setAttribute("loginName", user.getName());
                    //Set user type and redirect
                    userTypeRedirect(user, request, response);
                    break;

                case "login":

                    String loginEmail = request.getParameter("email");
                    String loginPassword = request.getParameter("password");

                    try {

                        user = usrCtrl.login(loginEmail, loginPassword);
                        request.getSession().setAttribute("user_id", user.getUser_id());
                        request.getSession().setAttribute("email", user.getEmail());
                        request.getSession().setAttribute("loginName", user.getName());

                        if (user != null) {

                            //Save where (which page) we are coming from
                            request.getSession().setAttribute("sourcePage", "LoginServlet");

                            //Set user type and redirect
                            userTypeRedirect(user, request, response);

                        }

                        //If something goes wrong, we need a way to show it.
                    } catch (PolygonException e) {

                        request.getSession().setAttribute("error", e.getMessage());
                        response.sendRedirect("index.jsp");

                    }

                    break;

                //Logout of the website
                case "logout":

                    request.getSession().invalidate();
                    response.sendRedirect("index.jsp");

                    break;

                case "update":

                case "newCustomer":

                    //If no user is logged in. (user == null)
                    if (request.getSession().getAttribute("user") == null) {

                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        String confirmPassword = request.getParameter("passwordConfirm");
                        String name = request.getParameter("name");
                        String phone = request.getParameter("phone"); //HUSK INTEGER.PARSE!*/
                        String company = request.getParameter("company");
                        String address = request.getParameter("address");
                        String postcode = request.getParameter("postcode"); //HUSK INTEGER.PARSE!*/
                        String city = request.getParameter("city");

                        try {

                            //Create user
                            usrCtrl.createUser(email, password, name, Integer.parseInt(phone), company, address, Integer.parseInt(postcode), city, User.type.CUSTOMER);
                            //If successful, redirect
                            emailNewCustomer(name, email, Integer.parseInt(phone), company, address, Integer.parseInt(postcode), city);
                            response.sendRedirect("index.jsp?success");

                        } catch (PolygonException e) {

                            request.getSession().setAttribute("error", e.getMessage());
                            response.sendRedirect("index.jsp");

                        }

                    } else {

                        //if user is logged in, as in not null.
                        response.sendRedirect("user.jsp");

                    }

                    break;

            }

        } catch (PolygonException e) {

            request.getSession().setAttribute("ExceptionError", e.getMessage());
            response.sendRedirect("error.jsp");

        }

    }

    public void userTypeRedirect(User user, HttpServletRequest request, HttpServletResponse response) throws PolygonException {
        try {
            if (user.getType().toString().equals("CUSTOMER")) {
                request.getSession().setAttribute("type", "Kunde");
                response.sendRedirect("UserServlet");
            } else if (user.getType().toString().equals("TECHNICIAN")) {
                request.getSession().setAttribute("type", "Tekniker");
                response.sendRedirect("TechnicianServlet");
            } else {
                request.getSession().setAttribute("type", "Administration");
                response.sendRedirect("AdminServlet");
            }
        } catch (Exception e) {

        }
    }

    public void emailNewCustomer(String name, String email, Integer phone, String company, String address, Integer postcode, String city) {
        //Send confirmation email to new Customer:
        String emailNewCustomerHeader = "Hej " + name + " (" + company + " )" + " og velkommen til Polygons's Sundebygninger!";
        String emailNewCustomerMessage = "Hej " + name + "!"
                + "\n\nVi er glade for at de har registeret "
                + "deres virksomhed hos os"
                + "og vi ser frem til at arbejde sammen med "
                + "dem i den nærmeste fremtid!"
                + "\n\n\n"
                + "Her er hvad vi har registeret: "
                + "\n\n"
                + "Navn: " + name + "\n"
                + "Email: " + email + "\n"
                + "Telefon: " + phone + "\n"
                + "Firma: " + company + "\n"
                + "Adresse: " + address + "\n"
                + "Postnummer: " + postcode + "\n "
                + "By: " + city
                + "\n\n\n"
                + "Skulle de glemme deres kodeord til deres "
                + "bruger eller har andre spørgsmål, "
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
            emailCtrl.send(email, emailNewCustomerHeader, emailNewCustomerMessage);
        } catch (Exception e) {
            e.printStackTrace();
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
