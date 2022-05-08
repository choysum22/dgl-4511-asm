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
        <jsp:useBean id="centerList" scope="request" class="java.util.ArrayList<ict.bean.CenterBean>" />
        <jsp:useBean id="staffTypeList" scope="request" class="java.util.ArrayList<ict.bean.StaffTypeBean>" />
        <jsp:useBean id="staff" scope="request" class="ict.bean.StaffBean" />
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
                        Edit Staff
                    </div>

                    <div class="row">
                        <div class="container overflow-auto" id="content-container">
                            <form action="staffAccount" method="post" runat="server" >
                                <div class="row">
                                    <div class="col-6">
                                        <input class='form-control' type="hidden" name="action" value="editStaff"/>
                                        <input class='form-control' type="hidden" name="id" value="<%= staff.getId()%>"/>

                                        Username: <input class='form-control' type="text" name="username" value="<%= staff.getUsername()%>" />
                                        </br>
                                        Password: <input class='form-control' type="password" name="password" value="<%= staff.getPassword()%>"/>
                                        </br>
                                        First Name: <input class='form-control' type="text" name="fname" value="<%= staff.getFname()%>"/>
                                        </br>
                                        Last Name: <input class='form-control' type="text" name="lname" value="<%= staff.getLname()%>"/>
                                        </br>

                                        <%
                                            String typeOptions = "";

                                            typeOptions = "Staff Type: <select class='form-select' name='type' id='type' onchange='toggle()'>"
                                                    + "<option selected disabled value> -- Select a Account Type -- </option>";
                                            for (StaffTypeBean b : staffTypeList) {
                                                if (b.getType().equalsIgnoreCase(staff.getType())) {
                                                    typeOptions += "<option value=\"" + b.getId() + "\" selected>" + b.getType() + "</option>";
                                                } else {
                                                    typeOptions += "<option value=\"" + b.getId() + "\">" + b.getType() + "</option>";
                                                }

                                            }

                                            out.println(typeOptions + "</select></br>");

                                            String trainerForm = "";
                                            if (request.getAttribute("trainer") != null) {
                                                TrainerBean trainer = (TrainerBean) request.getAttribute("trainer");
                                                trainerForm = "<div id='trainerInput'>"
                                                        + "Profile Image: </br>"
                                                        + "<input class='form-control' type='file' name='temp_img' id='temp_img' />"
                                                        + "<input class='form-control' type='hidden' id='img' name='img' value='" + trainer.getImg() + "'/></br>"
                                                        + "<img src='" + trainer.getImg() + "' name='currImg' id='currImg'/></br>Description: </br>"
                                                        + "<textarea class='form-control' name='desc' cols='40' rows='5'>" + trainer.getDesc() + "</textarea></br>"
                                                        + "Status: <select class='form-select' name='status'>";
                                                if (trainer.getStatus().equalsIgnoreCase("Available")) {
                                                    trainerForm += "<option value='Available' selected>Available</option>"
                                                            + "<option value='Unavailable'>Unavailable</option>";
                                                } else {
                                                    trainerForm += "<option value='Available' >Available</option>"
                                                            + "<option value='Unavailable' selected>Unavailable</option>";
                                                }
                                                trainerForm += "</select></br>"
                                                        + "Fee: <input class='form-control' type='text' id='fee' name='fee' value='" + trainer.getFee() + "'/></br></br>"
                                                        + "</div>";
                                            } else {
                                                trainerForm = "<div id='trainerInput' style='display:none'></br>"
                                                        + "Profile Image: </br>"
                                                        + "<input class='form-control' type='file' name='temp_img' id='temp_img' onchange='getFileData(this);'/>"
                                                        + "<input class='form-control' type='hidden' id='img' name='img' value=''/></br>"
                                                        + "<img src='' name='currImg' id='currImg'/></br>Description: </br>"
                                                        + "<textarea class='form-control' name='desc' cols='40' rows='5'></textarea></br>"
                                                        + "Status: <select class='form-select' name='status'>"
                                                        + "<option value='Available' selected>Available</option>"
                                                        + "<option value='Unavailable'>Unavailable</option>"
                                                        + "</select></br>"
                                                        + "Fee: <input class='form-control' type='text' id='fee' name='fee' value=''/></br></br>"
                                                        + "</div>";
                                            }

                                            out.println(trainerForm);

                                        %>



                                        Phone: <input class='form-control' type="text" id="tel" name="tel" value="<%= staff.getTel()%>"/>
                                        </br>
                                        <%                String centerOptions = "";

                                            centerOptions = "Center: <select class='form-select' name='center'><option selected disabled value> -- Select a Gym Center -- </option>";
                                            for (CenterBean b : centerList) {
                                                if (b.getId().equalsIgnoreCase(staff.getCenter())) {
                                                    centerOptions += "<option value=\"" + b.getId() + "\" selected>" + b.getName() + "</option>";
                                                } else {
                                                    centerOptions += "<option value=\"" + b.getId() + "\">" + b.getName() + "</option>";
                                                }
                                            }

                                            out.println(centerOptions + "</select></br>");

                                        %>
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

            function toggle() {
                var div = document.getElementById('trainerInput');
                if (document.getElementById('type').value == "3") {
                    if (div.style.display == 'block') {
                        div.style.display = 'none';
                    } else {
                        div.style.display = 'block';
                    }
                } else {
                    div.style.display = 'none';
                }
            }

        </script>
    </body>
</html>
