<%-- 
    Document   : editBuilding
    Created on : 26-Oct-2016, 11:26:45
    Author     : Martin
--%>

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
        <h1>Hello Worlddddddd!</h1>
    
        <% ArrayList<Building> tempAL = new ArrayList();
            tempAL = (ArrayList<Building>) request.getSession().getAttribute("tempAL");
        
        
        
        for (int i = 0; i < tempAL.size(); i++) {
            if(tempAL.get(i).getBuilding_id()==Integer.parseInt(request.getParameter("value"))){
            
        out.println(tempAL.get(i).getAddress());
            }
        }
    %>
    
    
    
    
    
    </body>
</html>
