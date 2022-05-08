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
        <jsp:useBean id="centerBeanList" scope="request" class="java.util.ArrayList<ict.bean.CenterBean>" />
        <jsp:useBean id="staffInfo" class="ict.bean.StaffBean" scope="session" />
        <div class="container-fluid mt-3">
            <jsp:useBean id="customerInfo" class="ict.bean.CustomerBean" scope="session" />
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
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left active' href='staffTrainer?action=list'><i class='bi bi-person-bounding-box'></i> Personal Trainers</a></li>"
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
                        Create New Trainer
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
                            <form action="staffTrainer" method="get" runat="server" >

                                <input type="hidden" name="action" value="add"/>
                                <input type="hidden" name="type" value="3"/>

                                <div class="row">
                                    <div class="col">
                                        Username: <input class='form-control' type="text" name="username" value="" />
                                    </div>
                                    <div class="col">
                                        Password: <input class='form-control' type="password" name="password" value=""/>
                                    </div>
                                </div>
                                </br>
                                <div class="row">
                                    <div class="col">
                                        First Name: <input class='form-control' type="text" name="fname" value=""/>
                                    </div>
                                    <div class="col">
                                        Last Name: <input class='form-control' type="text" name="lname" value=""/>
                                    </div>
                                </div>
                                </br>

                                Profile Image: </br>

                                <input class='form-control' type="file" name="temp_img" id="temp_img" onchange="getFileData(this);"/>
                                <input type="hidden" id="img" name="img" value=""/>
                                </br>
                                <img src="" name="currImg" id="currImg"/>
                                </br>
                                </br>
                                Description: </br>
                                <textarea class='form-control' name="desc" cols="40" rows="5"></textarea>
                                </br>
                                <div class="row">
                                    <div class="col">
                                        Status:
                                        <select class='form-select' name="status">
                                            <option value="Available" selected>Available</option>
                                            <option value="Unavailable">Unavailable</option>
                                        </select>
                                    </div>
                                    <div class="col">
                                        <%
                                            String centerOptions = "";

                                            centerOptions = "Center: <select class='form-select' name='center'><option selected disabled value> -- Select a Gym Center -- </option>";
                                            for (CenterBean b : centerBeanList) {
                                                centerOptions += "<option value=\"" + b.getId() + "\">" + b.getName() + "</option>";
                                            }

                                            out.println(centerOptions + "</select>");

                                        %>
                                    </div>
                                </div>
                                </br>
                                <div class="row">
                                    <div class="col">
                                        Phone: <input class='form-control' type="text" id="tel" name="tel" />
                                    </div>
                                    <div class="col">
                                        Fee: <input class='form-control' type="text" id="fee" name="fee" />
                                    </div>
                                </div>
                                </br>
                                <div class="row">
                                    <div class="col-3"><input class='form-control btn' type="submit" id="submit-btn" value="Submit"></div>
                                    <div class="col-3">
                                        <input class='form-control btn'
                                               type="button" id="cancel-btn"
                                               onclick="location.href = 'staffTrainer?action=list';" value="Cancel" /></div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.getElementById("temp_img").onchange = evt => {
                const [file] = document.getElementById("temp_img").files
                if (file) {
                    document.getElementById("currImg").src = URL.createObjectURL(file)
                    getFileData(file);
                }
            }

            function getFileData(myFile) {
                var file = myFile.files[0];
                var filename = file.name;
                document.getElementById("img").value = "img/" + filename;
            }
        </script>
    </body>
</html>
