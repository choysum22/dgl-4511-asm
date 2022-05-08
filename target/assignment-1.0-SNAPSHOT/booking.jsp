<%@page import="ict.bean.*, java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Booking List</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="bookingCenterList" scope="request" class="java.util.ArrayList<ict.bean.BookingCenterBean>" />
        <jsp:useBean id="bookingTrainerList" scope="request" class="java.util.ArrayList<ict.bean.BookingTrainerBean>" />
        <jsp:useBean id="customerInfo" scope="session" class="ict.bean.CustomerBean" />

        <div class="container-fluid mt-3">
            <div class="row" id="navbar">
                <div class="col-12" id="navbar-icon-col">
                    <a href="home.jsp"><h5 class="mb-3 user-select-none" id="navbar-icon">Dream Gym Limited</h5></a>
                </div>
            </div>
            <div class="row">
                <div class="col-2" id="navbar-left">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link nav-left" href="trainer?action=list" aria-current="page">
                                <i class="bi bi-person-bounding-box"></i> Personal Trainers
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-left" href="center?action=list">
                                <i class="bi bi-building"></i> Gym Centers
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-left" href="booking?action=book">
                                <i class="bi bi-calendar-plus"></i> Book Now
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active nav-left" href="booking?action=list">
                                <i class="bi bi-search"></i> Check Booking
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-left" href="main?action=logout" id="logout-btn">
                                <i class="bi bi-box-arrow-left"></i> Logout
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="col-10">
                    <div class="row user-select-none" id="content-tab-title">
                        Booking List
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
                        <div class="container" id="content-container">
                            <div class="col">
                                <h5>Center Booking Record</h5>
                                <input class="form-control select-filter" type="text" id="searchInputCenterID" placeholder="Search">
                                <div class="col half-col overflow-auto">
                                    <%
                                        String ctable = "<table class='table table-striped table-hover' id='bookingCenter'><thead class='thead' style='position: sticky; top: 0'><tr class='table-head'><th>ID</th><th>Center Name</th><th>Fee</th>"
                                                + "<th>Timeslot</th><th>Date</th><th>Status</th><th>Booking Time</th>"
                                                + "<th>Update</th><th>Cancel</th></tr></thead><tbody class='tbody' id='table-bookingCenter'>";

                                        for (BookingCenterBean b : bookingCenterList) {
                                            ctable += "<tr>"
                                                    + "<td>" + b.getId() + "</td>"
                                                    + "<td>" + b.getCenterName() + "</td>"
                                                    + "<td>" + b.getFee() + "</td>"
                                                    + "<td>" + b.getTimeslot() + "</td>"
                                                    + "<td>" + b.getDate() + "</td>"
                                                    + "<td>" + b.getStatus() + "</td>"
                                                    + "<td>" + b.getTimestamp() + "</td>";
                                            if (b.getStatus().equalsIgnoreCase("Pending")) {
                                                ctable += "<td><a href=\"booking?action=update&type=center&id=" + b.getId() + "\"> Update</a></td>"
                                                        + "<td><a href=\"booking?action=cancel&type=center&uid=" + customerInfo.getId() + "&id=" + b.getId()
                                                        + "\" onclick='return confirmDeleteAlert(" + b.getId() + ")'> Cancel</a></td>";
                                            } else {
                                                ctable += "<td></td><td></td>";
                                            }

                                            ctable += "</tr>";

                                        }
                                        out.println(ctable + "</tbody></table>");

                                    %>
                                </div>
                            </div>

                            </br>

                            <div class="col">
                                <h5>Trainer Booking Record</h5>
                                <input class="form-control select-filter" type="text" id="searchInputTrainerID" placeholder="Search">
                                <div class="col half-col overflow-auto">
                                    <%  String ttable = "<table class='table table-striped table-hover' id='bookingTrainer'><thead class='thead' style='position: sticky; top: 0'><tr class='table-head'><th>ID</th><th>Trainer Name</th><th>Center Name</th>"
                                                + "<th>Fee</th><th>Timeslot</th><th>Date</th><th>Status</th><th>Booking Time</th>"
                                                + "<th>Update</th><th>Cancel</th></tr></thead><tbody class='tbody' id='table-bookingTrainer'>";

                                        for (BookingTrainerBean b : bookingTrainerList) {
                                            ttable += "<tr>"
                                                    + "<td>" + b.getId() + "</td>"
                                                    + "<td>" + b.getTrainerName() + "</td>"
                                                    + "<td>" + b.getCenterName() + "</td>"
                                                    + "<td>" + b.getFee() + "</td>"
                                                    + "<td>" + b.getTimeslot() + "</td>"
                                                    + "<td>" + b.getDate() + "</td>"
                                                    + "<td>" + b.getStatus() + "</td>"
                                                    + "<td>" + b.getTimestamp() + "</td>";
                                            if (b.getStatus().equalsIgnoreCase("Pending")) {
                                                ttable += "<td><a href=\"booking?action=update&type=trainer&id=" + b.getId() + "\"> Update</a></td>"
                                                        + "<td><a href=\"booking?action=cancel&type=trainer&uid=" + customerInfo.getId() + "&id=" + b.getId()
                                                        + "\" onclick='return confirmDeleteAlert(" + b.getId() + ")'> Cancel</a></td>";
                                            } else {
                                                ttable += "<td></td><td></td>";
                                            }
                                            ttable += "</tr>";

                                        }
                                        out.println(ttable + "</tbody></table>");

                                    %>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function confirmDeleteAlert(id) {
                if (confirm('About to cancel booking with id: ' + id + ', are you sure?')) {
                    return true;
                } else {
                    return false;
                }
            }

            var $centerRows = $('#table-bookingCenter tr');
            $('#searchInputCenterID').keyup(function () {
                var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

                $centerRows.show().filter(function () {
                    var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                    return !~text.indexOf(val);
                }).hide();
            });

            var $trainerRows = $('#table-bookingTrainer tr');
            $('#searchInputTrainerID').keyup(function () {
                var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

                $trainerRows.show().filter(function () {
                    var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                    return !~text.indexOf(val);
                }).hide();
            });
        </script>
    </body>
</html>
