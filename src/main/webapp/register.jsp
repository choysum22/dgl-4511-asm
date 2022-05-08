<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Create Account</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <div class="container" id="register-container">
            <div class="row justify-content-center" id="register-row">
                <div class="col-auto-4" id="register">
                    <div class="mb-3" id="title">
                        <p>Create New Account</p>
                    </div>
                    <form action="register" method="post">
                       
                        <div class="mb-3 uname">
                            <label for="username" class="form-label">Username</label>
                            <input class="form-control" type="text" name="username" id="username" placeholder="Username..." />
                        </div>
                        <div class="mb-3 pw">
                            <label for="password" class="form-label">Password</label>
                            <input class="form-control" type="password" name="password" id="password" placeholder="Password..." />
                        </div>
                        <div class="mb-3 pw">
                            <label for="cpassword" class="form-label">Confirm Password</label>
                            <input class="form-control" type="password" name="confirm_password" id="cpassword" placeholder="Password..." />
                        </div>
                        <div class="mb-3 pw">
                            <label for="password" class="form-label">First Name</label>
                            <input class="form-control" type="text" name="fname" id="fname" placeholder="First Name..." />
                        </div>
                        <div class="mb-3 pw">
                            <label for="lname" class="form-label">Last Name</label>
                            <input class="form-control" type="text" name="lname" placeholder="Last Name..." />
                        </div>
                        <div class="mb-3 pw">
                            <label for="tel" class="form-label">Phone</label>
                            <input class="form-control" type="text" name="tel" id="tel" placeholder="Phone..." />
                        </div>
                        <div class="row">
                            <div class="col">
                                <input class='btn form-control' id="register-btn" type="submit" value="Register" />
                            </div>
                            <div class="col"> 
                                <input class='btn form-control' id="back-btn" type="button" value="Cancel" onclick="window.location='index.jsp';" />
                            </div>
                        </div>
                        
                        
                        
                    </form> 

                </div>
            </div>
        </div>
        
        
    </body>
</html>
