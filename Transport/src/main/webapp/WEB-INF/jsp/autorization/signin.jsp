<%-- 
    Document   : signin
    Created on : 08.07.2015, 23:46:45
    Author     : Ponomarev
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Signin Template for Bootstrap</title>

        <!-- Bootstrap core CSS -->
        <link href="js/libs/twitter-bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="js/libs/itapteka/css/login.css" rel="stylesheet">

    </head>

    <body>

        <div class="container well">
            <form class="form-signin navbar-form" action="<c:url value="/j_spring_security_check.htm" />" method='POST'>
                <h2 class="form-signin-heading">Вход</h2>
                <!--<label for="inputEmail" class="sr-only">Login</label>-->
                <label class="delimiter-label"></label>
                <input type="text" class="form-control" placeholder="Login" name="j_username" required autofocus>
                <!--<label for="inputPassword" class="sr-only">Password</label>-->
                <label class="delimiter-label"></label>
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="j_password" required>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}" />
                <label class="delimiter-label"></label>
                <!--                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="remember-me"> Remember me
                                    </label>
                                </div>-->
                <label class="checkbox ">
                    <input type="checkbox" name="remember" value="1"> Запомнить
                </label>
                <button class="btn btn-info btn-block " type="submit" name="submit">Sign in</button>
            </form>
        </div>
    </body>
</html>

