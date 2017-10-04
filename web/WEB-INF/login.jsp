<%-- 
    Document   : login
    Created on : Oct 2, 2017, 12:27:05 PM
    Author     : 698437
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
       <h1>Remember Me Login Page</h1>
        <div>
            <form action="login" method="post">
                Username: <input type="text" name="username" value="${username}"><br>
                Password: <input type="text" name="password" value="${password}"><br>
                <input type="submit" value="Login">  
            </form>
                <input type="checkbox" name="remember">Remember me<br>
            ${loginMessage}
</div>
    </body>
</html>
