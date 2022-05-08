<%@page import="ict.bean.TrainerBean"%>
<%@page import="ict.bean.StaffTypeBean"%>
<%@page import="ict.bean.CenterBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Trainer</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="customer" scope="request" class="ict.bean.CustomerBean" />
        <jsp:useBean id="staffInfo" class="ict.bean.StaffBean" scope="session" />
        <div class="container-fluid mt-3">
            <div class="row" id="navbar">
                <div class="col-12" id="navbar-icon-col">
                    <a href="staffHome.jsp"><h5 class="mb-3 user-select-none" id="navbar-icon">Dream Gym Limited - <jsp:getProperty name="staffInfo" property="type" /></h5></a>
                </div>
            </div>
            <div class="row">
                <div class="col-2" id="navbar-left">
                    <ul class="nav flex-column">
                        <%
                            String navigation = "";
                            if (staffInfo.getType().equalsIgnoreCase("Staff") || staffInfo.getType().equalsIgnoreCase("Personal Trainer")) {
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left  ' href='staffTrainer?action=list'><i class='bi bi-person-bounding-box'></i> Personal Trainers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left ' href='staffCenter?action=list'><i class='bi bi-building'></i> Gym Centers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left' href='staffBooking?action=list'><i class='bi bi-clipboard-check'></i> Confirm/Decline Booking</a></li>";
                            } else if (staffInfo.getType().equalsIgnoreCase("Senior Management")) {
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left ' href='staffReport?action=list'><i class='bi bi-pie-chart-fill'></i> Analytic / Report</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left active' href='staffAccount?action=list'><i class='bi bi-people-fill'></i> Account Management</a></li>";
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
                        Edit Customer
                    </div>

                    <div class="row">
                        <div class="container overflow-auto" id="content-container">
                            <form action="staffAccount" method="post" runat="server" >
                                <div class="row">
                                    <div class="col-6">
                                        Customer ID: <%= customer.getId()%>
                                        </br>
                                        </br>
                                        <input class="form-control" type="hidden" name="action" value="editCust"/>
                                        <input class="form-control" type="hidden" name="id" value="<%= customer.getId()%>"/>

                                        Username: <input class="form-control" type="text" name="username" value="<%= customer.getUsername()%>" />
                                        </br>
                                        Password: <input class="form-control" type="password" name="password" value="<%= customer.getPassword()%>"/>
                                        </br>
                                        First Name: <input class="form-control" type="text" name="fname" value="<%= customer.getFname()%>"/>
                                        </br>
                                        Last Name: <input class="form-control" type="text" name="lname" value="<%= customer.getLname()%>"/>
                                        </br>
                                        Phone: <input class="form-control" type="text" id="tel" name="tel" value="<%= customer.getTel()%>"/>
                                        </br>
                                    </div>

                                </div>

                                <div class="row">
                                    <div class="col-3">
                                        <input class='form-control btn' id="submit-btn" type="submit" value="Submit">
                                    </div>
                                    <div class="col-3">
                                        <input type="button" class="form-control btn" id="cancel-btn" onclick="location.href = 'staffAccount?action=list';" value="Cancel" />
                                    </div>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
