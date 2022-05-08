<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Login</title>
        <%@include file="bootstrap.jsp" %>
    </head>

    <body>
        <div class="container" id="login-container">
            <div class="row justify-content-center" id="login-row">
                <div class="col-auto-4" id="login">
                    <div class="mb-3" id="title">
                        <h1>Dream Gym Limited</h1>
                        <p>Welcome</p>
                        <p>Login</p>
                    </div>
                    <form action="main" method="post">
                        <input type="hidden" name="action" value="authenticate" />
                        <div class="mb-3 uname">
                            <label for="username" class="form-label">Username</label>
                            <input class="form-control" type="text" name="username" id="username" placeholder="username" />
                        </div>
                        <div class="mb-3 pw">
                            <label for="password" class="form-label">Password</label>
                            <input class="form-control" type="password" name="password" id="password" placeholder="password" />
                        </div>

                        <input class='btn form-control' id="login-btn" type="submit" value="Login" />
                    </form> 

                    <a href="register.jsp">Create Account</a>

                </div>
            </div>
        </div>


        <% // login failed
            if (request.getAttribute("login") != null) {
                String login = (String) request.getAttribute("login");
                if (login == "failed") {
                    out.println("<script>alert('Incorrect Username or Password!');</script>");
                }
            }
        %>
    </body>

</html>