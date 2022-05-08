<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="staffInfo" class="ict.bean.StaffBean" scope="session" />
        <div class="container-fluid mt-3">
            <jsp:useBean id="customerInfo" class="ict.bean.CustomerBean" scope="session" />
            <div class="row" id="navbar">
                <div class="col-12" id="navbar-icon-col">
                    <h5 class="mb-3 user-select-none" id="navbar-icon">Dream Gym Limited - <jsp:getProperty name="staffInfo" property="type" /></h5>
                </div>
            </div>
            <div class="row">
                <div class="col-2" id="navbar-left">

                    <ul class="nav flex-column">
                        <%
                            String navigation = "";
                            if (staffInfo.getType().equalsIgnoreCase("Staff") || staffInfo.getType().equalsIgnoreCase("Personal Trainer")) {
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left' href='staffTrainer?action=list'><i class='bi bi-person-bounding-box'></i> Personal Trainers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left' href='staffCenter?action=list'><i class='bi bi-building'></i> Gym Centers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left' href='staffBooking?action=list'><i class='bi bi-clipboard-check'></i> Confirm/Decline Booking</a></li>";
                            } else if (staffInfo.getType().equalsIgnoreCase("Senior Management")) {
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left' href='staffReport?action=list'><i class='bi bi-pie-chart-fill'></i> Analytic / Report</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left' href='staffAccount?action=list'><i class='bi bi-people-fill'></i> Account Management</a></li>";
                            }
                            out.println(navigation);
                        %>
                        <li class="nav-item">
                            <a class="nav-link nav-left" href="staff?action=logout" id="logout-btn">
                                <i class="bi bi-box-arrow-left"></i> Logout
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="col-10">
                    <div class="row user-select-none" id="content-tab-title">
                        Hello, <jsp:getProperty name="staffInfo" property="fname" />  <jsp:getProperty name="staffInfo" property="lname" />
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="d-flex flex-row" id="search-input-container">

                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="d-flex flex-row-reverse" id="search-input-container">

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="container user-select-none overflow-auto" id="content-container">
                            <h1 class="welcomeSlogan">Welcome to Dream Gym Limited</h1>
                            <h1 class="welcomeSlogan">Together, We Dream Better.</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <h1></h1>
        <b>  </b>
        <%// trainer booking request alert
            if (request.getAttribute("rows") != null) {
                int rows = (Integer) request.getAttribute("rows");
                if (rows != 0) {
                    out.println("<script>alert('You have pending booking request');</script>");
                }
            }
        %>

    </body>
</html>
