<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Admin User List</h1>
        <table border="1" text-align="left" class="table table-striped">
                <tbody>
                    <tr>
                        <td><b>Email</b></td>
                        <td><b>Type of user</b></td>
                        
                    </tr>
                    <%
                        for (int i = 0; i < tempAL.size(); i++) {
                    %><tr>
                         
                        <td><%out.println(tempAL.get(i).getEmail());%></td>  
                        <td><%out.println(tempAL.get(i).getType());%></td>  
                    </tr>
                    <%}
                    %>
                </tbody>


            </table>
        
    </body>
</html>
