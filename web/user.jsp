<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.util.ArrayList"%>
<%@page import="serviceLayer.enties.Building"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>User page~!</h1>
        
        
        <% ArrayList<Building> tempAL = new ArrayList();
        tempAL = (ArrayList<Building>) request.getSession().getAttribute("tempAL"); 
        out.println(tempAL.toString());
        //JOptionPane.showMessageDialog(null, tempAL.get(0).getPostcode());
        //JOptionPane.showMessageDialog(null, "hej");
        //System.out.println(tempAL.get(0).getPostcode());
        //System.out.println("hej");
        %>
        
        <form class="form-signin" action="Front" method="POST">
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Log ud" name="" />
        <input type="hidden" name="origin" value="logout" />
        </form>
    </body>
</html>
