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
import serviceLayer.entities.Image;

@WebServlet(name = "GetImage", urlPatterns = {"/GetImage"})
public class GetImage extends HttpServlet {

    private DataControllerInterface dat = new DataController();
    private Image img;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream out = response.getOutputStream();
        try {

            String type = request.getParameter("type");

            switch (type) {
                case "image":
                    img = dat.getImage(Integer.parseInt(request.getParameter("id")));
                    break;
                case "building":
                    img = dat.getBuildingImage(Integer.parseInt(request.getParameter("id")));
                    break;
                case "issue":
                    img = dat.getIssueImage(Integer.parseInt(request.getParameter("id")));
                    break;

                default:

                    img = dat.getImage(1);

            }

            Blob photo = img.getImg_file();

            if (photo != null) {

                response.addHeader("Content-Disposition", "attachment; filename=" + img.getImg_name()+".jpg");
                InputStream in = photo.getBinaryStream();
                int length = (int) photo.length();

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
            e.printStackTrace();
            response.setContentType("text/html");
            out.println("Some error found!");
            return;
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
