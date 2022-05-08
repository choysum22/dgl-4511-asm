<%@page import="ict.bean.*, java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Center List</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="centerList" scope="request" class="java.util.ArrayList<ict.bean.CenterBean>" />
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
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left ' href='staffTrainer?action=list'><i class='bi bi-person-bounding-box'></i> Personal Trainers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left active' href='staffCenter?action=list'><i class='bi bi-building'></i> Gym Centers</a></li>"
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
                        Center List
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="d-flex flex-row" id="search-input-container">
                                <%
                                    String addForm = "";
                                    if (staffInfo.getType().equalsIgnoreCase("Staff")) {

                                        addForm = "<form action='staffCenter' method='get'>"
                                                + "<input type='hidden' name='action' value='createCenter'>"
                                                + "<input class='form-control btn' id='function-btn' type='submit' value='+ Add New Center'>"
                                                + "</form>";
                                        out.println(addForm);
                                    }
                                %>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="d-flex flex-row-reverse" id="search-input-container">
                                <input class="form-control select-filter" type="text" id="searchInputCenterID" placeholder="Search">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="container overflow-auto" id="content-container">
                            <%
                                String table = "";
                                if (staffInfo.getType().equalsIgnoreCase("Staff")) {

                                    table = "<table class='table table-striped' id='trainer' ><thead class='thead' style='position: sticky; top: 0'><tr class='table-head'><th>Center ID</th><th>Name</th><th>Address</th><th>Description</th>"
                                            + "<th>Fee</th><th>Phone</th><th>Status</th><th>Listing</th><th>Edit</th><th>Delete</th></tr></thead><tbody class='tbody' id='table-center'>";

                                    for (CenterBean c : centerList) {
                                        table += "<tr>"
                                                + "<td>" + c.getId() + "</td>"
                                                + "<td>" + c.getName() + "</td>"
                                                + "<td>" + c.getLocation() + "</td>"
                                                + "<td>" + c.getDesc() + "</td>"
                                                + "<td>$" + c.getFee() + "</td>"
                                                + "<td>" + c.getTel() + "</td>"
                                                + "<td>" + c.getStatus() + "</td>";
                                        if (c.getStatus().equals("Available")) {
                                            table += "<td><a href=\"staffCenter?action=disable&id=" + c.getId()
                                                    + "\" onclick='return confirmAlert(" + c.getId() + ",false)'> "
                                                    + "Disable</a></td>";

                                        } else if (c.getStatus().equals("Unavailable")) {
                                            table += "<td><a href=\"staffCenter?action=enable&id=" + c.getId()
                                                    + "\" onclick='return confirmAlert(" + c.getId() + ",true)'> "
                                                    + "Enable</a></td>";
                                        }
                                        table += "<td><a class='edit-text' href=\"staffCenter?action=edit&id=" + c.getId() + "\"> Edit</a></td>"
                                                + "<td><a class='delete-text' href=\"staffCenter?action=delete&id=" + c.getId() + "\" "
                                                + "onclick='return confirmDeleteAlert(" + c.getId() + ")'> "
                                                + "Delete</a></td></tr>";

                                    }
                                } else if (staffInfo.getType().equalsIgnoreCase("Personal Trainer")) {
                                    table = "<table class='table table-striped' id='trainer' ><thead class='thead' style='position: sticky; top: 0'><tr class='table-head'><th>Center ID</th><th>Name</th><th>Address</th><th>Description</th>"
                                            + "<th>Fee</th><th>Phone</th><th>Status</th></tr></thead><tbody class='tbody' id='table-center'>";

                                    for (CenterBean c : centerList) {
                                        table += "<tr>"
                                                + "<td>" + c.getId() + "</td>"
                                                + "<td>" + c.getName() + "</td>"
                                                + "<td>" + c.getLocation() + "</td>"
                                                + "<td>" + c.getDesc() + "</td>"
                                                + "<td>$" + c.getFee() + "</td>"
                                                + "<td>" + c.getTel() + "</td>"
                                                + "<td>" + c.getStatus() + "</td>";
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
                if (confirm('About to ' + str + ' listing of center with id: ' + id + ', are you sure?')) {
                    return true;
                } else {
                    return false;
                }
            }

            function confirmDeleteAlert(id) {
                if (confirm('About to delete center with center id: ' + id + ', are you sure?')) {
                    return true;
                } else {
                    return false;
                }
            }

            var $rows = $('#table-center tr');
            $('#searchInputCenterID').keyup(function () {
                var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

                $rows.show().filter(function () {
                    var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                    return !~text.indexOf(val);
                }).hide();
            });
        </script>
    </body>
</html>
