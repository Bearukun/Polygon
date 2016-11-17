<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload test page</title>
    </head>
    <body>
        <form method="post" action="uploadServlet" enctype="multipart/form-data">
            Name on img: <input type="text" name="name" value="test" /> Choose a file: <input
                type="file" name="photo" size="50" /> <input type="submit"
                value="Upload">
        </form>
    </body>
</html>