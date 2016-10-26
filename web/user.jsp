<%@page import="serviceLayer.enties.User"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.util.ArrayList"%>
<%@page import="serviceLayer.enties.Building"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sunde Bygninger - Brugerportal</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!--Adding our own css-->
        <link href="css/index.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
    
  
        <!--Top navigation bar:-->
        <nav class="navbar navbar-default container-fluid">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a><span class="glyphicon glyphicon-user"></span> <%= (User)session.getAttribute("user") %></a></li>
                <li><a href="logout.jsp"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
            </ul>
        </nav>
        
        <div id="container" class="container-fluid">
        
        
        <h1>User page~!</h1>
        
        
        
        
        <% ArrayList<Building> tempAL = new ArrayList();
        tempAL = (ArrayList<Building>) request.getSession().getAttribute("tempAL"); 
        out.println(tempAL.toString());
        


        //JOptionPane.showMessageDialog(null, tempAL.get(0).getPostcode());
        //JOptionPane.showMessageDialog(null, "hej");
        //System.out.println(tempAL.get(0).getPostcode());
        //System.out.println("hej");
        %>
        
        <table border="1" text-align="left">
            <tr>
                <td><b>Adresse</b></td>
                <td><b>Postnr.</b></td>
                <td><b>By</b></td>
            </tr>
            <%
                for (int i = 0; i < tempAL.size(); i++) {
                    %><tr>
                        <td><%out.println(tempAL.get(i).getAddress());%></td>  
                        <td><%out.println(tempAL.get(i).getPostcode());%></td>  
                        <td><%out.println(tempAL.get(i).getCity());%></td>  
</tr>
                <%}
            %>
                    
            
        </table>
        
            
            
        <form class="form-signin" action="Front" method="POST">
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Log ud" name="" />
        <input type="hidden" name="origin" value="logout" />
        </form>
            
            </div>
    </body>
</html>
