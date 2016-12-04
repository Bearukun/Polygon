package presentationLayer.servlets;

import dataAccessLayer.PDFCreator;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import serviceLayer.controllers.BuildingController;
import serviceLayer.controllers.EmailController;
import serviceLayer.controllers.UserController;
import serviceLayer.entities.User;

/**
 * Servlet used to check what type of user is logging in.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private UserController usrCtrl = new UserController();
    private BuildingController bldgCtrl = new BuildingController();
    private User user = null;
    private EmailController emailC = new EmailController();

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
                    //Set user type and redirect
                    userTypeRedirect(user, request, response);
                break;
                
                case "login":

                    if (request.getSession().getAttribute("user") == null) {

                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        
                        try {
                            
                            
                            user = usrCtrl.login(email, password);
                            request.getSession().setAttribute("user_id", user.getUser_id());
                            request.getSession().setAttribute("email", user.getEmail());
                            
                            if (user != null) {
                                
                                //Save where (which page) we are coming from
                                request.getSession().setAttribute("sourcePage", "LoginServlet");

                                //Set user type and redirect
                                userTypeRedirect(user, request, response);
                                EmailController EC = new EmailController();
                             

                            }

                            //If something goes wrong, we need a way to show it.
                        } catch (Exception e) {

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

//                        Check that the parameters are being read
//                        System.out.println(email + " em");
//              
                        System.out.println(password + " pass");
                        System.out.println(confirmPassword + " con pass");
//                        System.out.println(name + " nam");
//                        System.out.println(phone+ " ph");
//                        System.out.println(company+ " com");
//                        System.out.println(address+ " add");
//                        System.out.println(postcode+ " post");
//                        System.out.println(city + " city");

                        //Redundant method: Being handled by JS in the index.jsp
                        //test password match
//                        if(!password.equalsIgnoreCase(confirmPassword)){
//                            System.out.println("PASSWORD NOT MATCHING!");
//                            
//                            response.sendRedirect("index.jsp?error=" + "PasswordNotMatching");
//                            
//                        }
                        
                        
                    
                         try {
                            System.out.println("creating user");
                            //Create user
                          usrCtrl.createUser(email, password, name, Integer.parseInt(phone), company, address, Integer.parseInt(postcode), city, User.type.CUSTOMER);
                            //If successful, redirect
                            System.out.println("Index redirect");
                            
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
                   
                            emailC.send(email, emailNewCustomerHeader, emailNewCustomerMessage);
                            response.sendRedirect("index.jsp?success");

                        } catch (Exception e) {

                            errMsg = e.getMessage();
                            response.sendRedirect("newCustomer.jsp?error=" + URLEncoder.encode(errMsg, "UTF-8"));

                        }

                    } else {

                        //if user is logged in, as in not null.
                        response.sendRedirect("user.jsp");

                    }

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
                    String picturePath = "";

                    String systemDir = System.getProperty("user.dir");
                    System.out.println(systemDir);

//                    //Filechooser for selecting an image for the generated PDF
//                    JFileChooser choose = new JFileChooser();
//                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg files", "jpg");
//                    choose.setFileFilter(filter);
//                    String picturePath = "";
//                    String folderPath = "";
//                    int returnVal = choose.showOpenDialog(choose);
//
//                    if (returnVal == JFileChooser.APPROVE_OPTION) {
//
//                        picturePath = choose.getSelectedFile().getAbsolutePath();
//                        folderPath = "" + choose.getCurrentDirectory();
//                        System.out.println(picturePath);
//                        System.out.println(folderPath + " Folder sti");
//                       
//
//                    }

                    System.out.println(picturePath);

                    pdfwt.createPDF(pdfName, bName, bAddress,
                            Integer.parseInt(bPostCode), bCity, Integer.parseInt(bConstructionYear),
                            Integer.parseInt(bSQM), bPurpose, bOwner, picturePath, imgFolderPath, savePath);

                    response.sendRedirect("index.jsp?success=PDFCreated");
                    break;

            }

        } catch (Exception e) {

        }

        
        
    }
    
    public void userTypeRedirect(User user, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
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
