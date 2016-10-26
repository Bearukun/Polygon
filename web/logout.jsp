<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <!-- Logud styring - Sender brugeren tilbage til login skærmen -->
    
        <h1>You've been logged out!</h1>
        <% session.setAttribute("user_id", null);
            session.invalidate();
            response.sendRedirect("index.jsp");
        %>
    
    </body>
</html>
