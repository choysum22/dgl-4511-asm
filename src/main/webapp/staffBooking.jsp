<%@page import="ict.bean.BookingTrainerBean"%>
<%@page import="ict.bean.BookingCenterBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="bookingCenterList" scope="request" class="java.util.ArrayList<ict.bean.BookingCenterBean>" />
        <jsp:useBean id="bookingTrainerList" scope="request" class="java.util.ArrayList<ict.bean.BookingTrainerBean>" />
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
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left ' href='staffTrainer?action=list'><i class='bi bi-person-bounding-box'></i> Personal Trainers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left' href='staffCenter?action=list'><i class='bi bi-building'></i> Gym Centers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left active' href='staffBooking?action=list'><i class='bi bi-clipboard-check'></i> Confirm/Decline Booking</a></li>";
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
                        <%
                            String title = "";
                            if (staffInfo.getType().equalsIgnoreCase("Staff")) {
                                title = "Center Booking Request";
                            } else if (staffInfo.getType().equalsIgnoreCase("Personal Trainer")) {
                                title = "Trainer Booking Request";
                            }
                            out.println(title);

                        %>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="d-flex flex-row" id="search-input-container">

                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="d-flex flex-row-reverse" id="search-input-container">
                                <input class="form-control select-filter" type="text" id="searchInputBookingID" placeholder="Search">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="container user-select-none overflow-auto" id="content-container">
                            <%                                if (staffInfo.getType().equalsIgnoreCase("Staff")) {
                                    String ctable = "<table class='table table-striped' id='trainer' ><thead class='thead' style='position: sticky; top: 0'><tr class='table-head'><th>ID</th>"
                                            + "<th>Customer ID</th><th>Customer</th><th>Center ID</th><th>Center Name</th><th>Fee</th>"
                                            + "<th>Timeslot</th><th>Date</th><th>Status</th><th>Booking Time</th>"
                                            + "<th>Confirm</th><th>Decline</th></tr></thead><tbody class='tbody' id='table-booking'>";

                                    for (BookingCenterBean b : bookingCenterList) {
                                        ctable += "<tr>"
                                                + "<td>" + b.getId() + "</td>"
                                                + "<td>" + b.getCustId() + "</td>"
                                                + "<td>" + b.getCustName() + "</td>"
                                                + "<td>" + b.getCenterId() + "</td>"
                                                + "<td>" + b.getCenterName() + "</td>"
                                                + "<td>" + b.getFee() + "</td>"
                                                + "<td>" + b.getTimeslot() + "</td>"
                                                + "<td>" + b.getDate() + "</td>"
                                                + "<td>" + b.getStatus() + "</td>"
                                                + "<td>" + b.getTimestamp() + "</td>";
                                        if (b.getStatus().equals("Pending")) {
                                            ctable += "<td><a href=\"staffBooking?action=confirm&type=center&id=" + b.getId()
                                                    + "\" onclick='return confirmAlert(" + b.getId() + ",true)'> Confirm</a></td>"
                                                    + "<td><a class='delete-text' href=\"staffBooking?action=decline&type=center&id=" + b.getId()
                                                    + "\" onclick='return confirmAlert(" + b.getId() + ",false)'> Decline</a></td>";

                                        } else {
                                            ctable += "<td></td><td></td>";
                                        }
                                        ctable += "</tr>";

                                    }
                                    out.println(ctable + "</tbody></table>");
                                } else if (staffInfo.getType().equalsIgnoreCase("Personal Trainer")) {
                                    String ttable = "<table class='table table-striped' id='trainer' ><thead class='thead' style='position: sticky; top: 0'><tr class='table-head'><th>ID</th>"
                                            + "<th>Customer ID</th><th>Customer</th><th>Trainer ID</th><th>Trainer Name</th><th>Center ID</th>"
                                            + "<th>Center Name</th><th>Fee</th><th>Timeslot</th><th>Date</th>"
                                            + "<th>Status</th><th>Booking Time</th><th>Confirm</th><th>Decline</th></tr></thead><tbody class='tbody' id='table-booking'>";

                                    for (BookingTrainerBean b : bookingTrainerList) {
                                        if (b.getTrainerId().equals(staffInfo.getId())) {
                                            ttable += "<tr>"
                                                    + "<td>" + b.getId() + "</td>"
                                                    + "<td>" + b.getCustId() + "</td>"
                                                    + "<td>" + b.getCustName() + "</td>"
                                                    + "<td>" + b.getTrainerId() + "</td>"
                                                    + "<td>" + b.getTrainerName() + "</td>"
                                                    + "<td>" + b.getCenterId() + "</td>"
                                                    + "<td>" + b.getCenterName() + "</td>"
                                                    + "<td>" + b.getFee() + "</td>"
                                                    + "<td>" + b.getTimeslot() + "</td>"
                                                    + "<td>" + b.getDate() + "</td>"
                                                    + "<td>" + b.getStatus() + "</td>"
                                                    + "<td>" + b.getTimestamp() + "</td>";
                                            if (b.getStatus().equals("Pending")) {
                                                ttable += "<td><a href=\"staffBooking?action=confirm&type=trainer&id=" + b.getId()
                                                        + "\" onclick='return confirmAlert(" + b.getId() + ",true)'> Confirm</a></td>"
                                                        + "<td><a class='delete-text' href=\"staffBooking?action=decline&type=trainer&id=" + b.getId()
                                                        + "\" onclick='return confirmAlert(" + b.getId() + ",false)'> Decline</a></td>";
                                            } else {
                                                ttable += "<td></td><td></td>";
                                            }
                                            ttable += "</tr>";
                                        }

                                    }
                                    out.println(ttable + "</tbody></table>");
                                }


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
                    str = "confirm";
                } else {
                    str = "decline";
                }
                if (confirm('About to ' + str + ' booking with id: ' + id + ', are you sure?')) {
                    return true;
                } else {
                    return false;
                }
            }

            var $rows = $('#table-booking tr');
            $('#searchInputBookingID').keyup(function () {
                var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

                $rows.show().filter(function () {
                    var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                    return !~text.indexOf(val);
                }).hide();
            });
        </script>
    </body>
</html>
