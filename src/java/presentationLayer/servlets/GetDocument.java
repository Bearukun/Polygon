package presentationLayer.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import serviceLayer.controllers.DataController;
import serviceLayer.controllers.interfaces.DataControllerInterface;
import serviceLayer.entities.Document;

@WebServlet(name = "GetDocument", urlPatterns = {"/GetDocument"})
public class GetDocument extends HttpServlet {

    private DataControllerInterface dat = new DataController();
    private Document document;
    
//    //This is new, used to handle Exceptions
//    StringWriter sw = new StringWriter();
//    PrintWriter pw = new PrintWriter(sw);

    /**
     * Handles the HTTP <code>GET</code> method
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream out = response.getOutputStream();
        try {

            document = dat.getDocument(Integer.parseInt(request.getParameter("id")));

            Blob doc = document.getDocument_file();

            if (document != null) {

                
                InputStream in = doc.getBinaryStream();
                int length = (int) doc.length();
                
		response.addHeader("Content-Disposition", "attachment; filename=" + document.getDocument_name()+"."+document.getDocument_type());

                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                while ((length = in.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                }

                in.close();
                out.flush();

            } else {

                response.setContentType("text/html");
                out.println("No data to display");
                return;

            }

        } catch (Exception e) {
            
//            //Get the stacktrace, and save it to pw. 
//            e.printStackTrace(pw);
//            e.getLocalizedMessage();
//            
//            //This could be sent to it-department.
//            sw.toString();
            
            request.getSession().setAttribute("ExceptionError", "Fejl: " + e.toString());

            response.sendRedirect("error.jsp");
            return;
            
        }

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
