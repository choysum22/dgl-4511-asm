<%@page import="ict.bean.TrainerBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trainer</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="trainerList" scope="request" class="java.util.ArrayList<ict.bean.TrainerBean>" />
        <jsp:useBean id="staffInfo" class="ict.bean.StaffBean" scope="session" />
        <div class="container-fluid mt-3">
            <div class="row" id="navbar">
                <div class="col-6" id="navbar-icon-col">
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
                        Trainer List
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="d-flex flex-row" id="search-input-container">
                                <%
                                    String addForm = "";
                                    if (staffInfo.getType().equalsIgnoreCase("Staff")) {

                                        addForm = "<form action='staffTrainer' method='get'>"
                                                + "<input type='hidden' name='action' value='createTrainer'>"
                                                + "<input class='form-control btn' type='submit' id='function-btn' value='+ Add New Trainer'>"
                                                + "</form>";
                                        out.println(addForm);
                                    }
                                %>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="d-flex flex-row-reverse" id="search-input-container">
                                <input class="form-control select-filter" type="text" id="searchInputTrainerID" placeholder="Search">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="container overflow-auto" id="content-container">
                            <%
                                String table = "";
                                if (staffInfo.getType().equalsIgnoreCase("Staff")) {

                                    table = "<table class='table table-striped' id='trainer' ><thead class='thead' style='position: sticky; top: 0'><tr class='table-head'><th>Staff ID</th><th>Image</th><th>First Name</th>"
                                            + "<th>Last Name</th><th>Description</th>"
                                            + "<th>Phone</th><th>Status</th><th>Fee</th><th>Center</th>"
                                            + "<th>Listing</th><th>Edit</th><th>Delete</th></tr></thead><tbody class='tbody' id='table-trainer'>";

                                    for (TrainerBean t : trainerList) {
                                        table += "<tr>"
                                                + "<td>" + t.getId() + "</td>"
                                                + "<td><img srcset=\"" + t.getImg() + " 3x\"></td>"
                                                + "<td>" + t.getFname() + "</td>"
                                                + "<td>" + t.getLname() + "</td>"
                                                + "<td>" + t.getDesc() + "</td>"
                                                + "<td>" + t.getTel() + "</td>"
                                                + "<td>" + t.getStatus() + "</td>"
                                                + "<td>$" + t.getFee() + "</td>"
                                                + "<td>" + t.getCenterName() + "</td>";
                                        if (t.getStatus().equals("Available")) {
                                            table += "<td><a href=\"staffTrainer?action=disable&id=" + t.getId()
                                                    + "\" onclick='return confirmAlert(" + t.getId() + ",false)'> "
                                                    + "Disable</a></td>";

                                        } else if (t.getStatus().equals("Unavailable")) {
                                            table += "<td><a href=\"staffTrainer?action=enable&id=" + t.getId()
                                                    + "\" onclick='return confirmAlert(" + t.getId() + ",true)'> "
                                                    + "Enable</a></td>";
                                        }
                                        table += "<td><a class='edit-text' href=\"staffTrainer?action=edit&id=" + t.getId() + "\"> Edit</a></td>"
                                                + "<td><a class='delete-text' href=\"staffTrainer?action=delete&id=" + t.getId() + "\" "
                                                + "onclick='return confirmDeleteAlert(" + t.getId() + ")'> "
                                                + "Delete</a></td></tr>";

                                    }
                                } else if (staffInfo.getType().equalsIgnoreCase("Personal Trainer")) {
                                    table = "<table class='table table-striped' id='trainer' ><thead class='thead' style='position: sticky; top: 0'><tr class='table-head'><th>Staff ID</th><th>Image</th><th>First Name</th>"
                                            + "<th>Last Name</th><th>Description</th>"
                                            + "<th>Phone</th><th>Status</th><th>Fee</th><th>Center</th>"
                                            + "</tr></thead><tbody class='tbody' id='table-trainer'>";

                                    for (TrainerBean t : trainerList) {
                                        table += "<tr>"
                                                + "<td>" + t.getId() + "</td>"
                                                + "<td><img srcset=\"" + t.getImg() + " 3x\"></td>"
                                                + "<td>" + t.getFname() + "</td>"
                                                + "<td>" + t.getLname() + "</td>"
                                                + "<td>" + t.getDesc() + "</td>"
                                                + "<td>" + t.getTel() + "</td>"
                                                + "<td>" + t.getStatus() + "</td>"
                                                + "<td>$" + t.getFee() + "</td>"
                                                + "<td>" + t.getCenterName() + "</td>";
                                        table += "</tr>";

                                    }
                                }

                                out.println(table + "</tbody></table>");

                            %>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            function confirmAlert(id, action) {
                str = "";
                if (action) {
                    str = "enable";
                } else {
                    str = "disable";
                }
                if (confirm('About to ' + str + ' listing of trainer with staff id: ' + id + ', are you sure?')) {
                    return true;
                } else {
                    return false;
                }
            }
            function confirmDeleteAlert(id) {
                if (confirm('About to delete trainer with staff id: ' + id + ', are you sure?')) {
                    return true;
                } else {
                    return false;
                }
            }

            var $rows = $('#table-trainer tr');
            $('#searchInputTrainerID').keyup(function () {
                var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

                $rows.show().filter(function () {
                    var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                    return !~text.indexOf(val);
                }).hide();
            });
        </script>
    </body>
</html>
