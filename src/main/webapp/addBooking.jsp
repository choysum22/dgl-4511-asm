<%@page import="ict.bean.TempBookingBean"%>
<%@page import="ict.bean.TimeslotBean"%>
<%@page import="ict.bean.TrainerBean"%>
<%@page import="ict.bean.CenterBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="trainerBeanList" scope="request" class="java.util.ArrayList<ict.bean.TrainerBean>" />
        <jsp:useBean id="centerBeanList" scope="request" class="java.util.ArrayList<ict.bean.CenterBean>" />
        <jsp:useBean id="timeslotBeanList" scope="request" class="java.util.ArrayList<ict.bean.TimeslotBean>" />
        <jsp:useBean id="customerInfo" class="ict.bean.CustomerBean" scope="session" />
        <jsp:useBean id="tempList" class="java.util.ArrayList<ict.bean.TempBookingBean>" scope="session" />
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
                            <a class="nav-link active nav-left" href="booking?action=book">
                                <i class="bi bi-calendar-plus"></i> Book Now
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-left" href="booking?action=list">
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
                        Create Booking
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
                            <div class="row">
                                <div class="col">
                                    <form action="booking" method="post">
                                        <input class='form-control' type="hidden" name="action" value="add"/>
                                        <input class='form-control' type="hidden" name="uid" value="<%= customerInfo.getId()%>"/>
                                        Type:
                                        <select class='form-select' name="type" id="type" aria-label=".form-select">
                                            <option value='center' selected>Center</option>
                                            <option value='trainer' >Trainer</option>
                                        </select>
                                        </br>
                                        <%
                                            String centerOptions = "";
                                            String trainerOptions = "";
                                            String timeslotOptions = "";
                                            centerOptions = "<div id='centerDiv' style='display:block'>Center: <select class='form-select' name='center' id='center'>"
                                                    + "<option style='display:none' selected disabled value> "
                                                    + "-- Select a Gym Center -- </option>";
                                            for (CenterBean b : centerBeanList) {
                                                if (b.getStatus().equals("Available")) {
                                                    centerOptions += "<option value=\"" + b.getId() + "\" >" + b.getName() + "</option>";
                                                }
                                            }
                                            out.println(centerOptions + "</select></div>");

                                            trainerOptions = "<div id='trainerDiv' style='display:none'>Trainer: <select class='form-select' name='trainer' id='trainer' disabled>"
                                                    + "<option style='display:none' selected disabled value> "
                                                    + "-- Select a Trainer -- </option>";
                                            for (TrainerBean b : trainerBeanList) {
                                                if (b.getStatus().equals("Available")) {
                                                    trainerOptions += "<option value=\"" + b.getId() + "\" >"
                                                            + b.getFname() + " " + b.getLname() + " | " + b.getCenterName() + "</option>";
                                                }
                                            }
                                            out.println(trainerOptions + "</select></div></br>");

                                            out.println("Date: <input class='form-control' type='date' name='date' id='date' required='required'> </br>");

                                            timeslotOptions = "Timeslot: <select class='form-select' name='timeslot'id='timeslot'>"
                                                    + "<option style='display:none' disabled selected value> -- Select a Timeslot -- </option>";
                                            for (TimeslotBean b : timeslotBeanList) {
                                                timeslotOptions += "<option value=\"" + b.getId() + "\" >" + b.getTimeslot() + "</option>";
                                            }
                                            out.println(timeslotOptions + "</select></br>");
                                        %>
                                        <div class="row">
                                            <div class="col-md-3">
                                                <input class='form-control btn' type="submit" id="submit-btn" value="Add">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="col">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="card">
                                                <div class="card-header">
                                                    <h5 class="card-title">Booking List</h5>
                                                </div>
                                                <div class="card-body">
                                                    <div class="table-responsive bordered">
                                                        <table class="table table-striped table-hover">
                                                            <thead>
                                                                <tr>
                                                                    <th>Type</th>
                                                                    <th>Center/Trainer Name</th>
                                                                    <th>Date</th>
                                                                    <th>Timeslot</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    String tempTable = "";
                                                                    if (!tempList.isEmpty()) {
                                                                        for (TempBookingBean b : tempList) {
                                                                            tempTable += "<tr>"
                                                                                    + "<td>" + b.getType() + "</td>";
                                                                            if (b.getType().equalsIgnoreCase("center")) {
                                                                                for (CenterBean bean : centerBeanList) {
                                                                                    if (bean.getId().equalsIgnoreCase(b.getSelectedId())) {
                                                                                        tempTable += "<td>" + bean.getName() + "</td>";
                                                                                    }
                                                                                }
                                                                            } else if (b.getType().equalsIgnoreCase("trainer")) {
                                                                                for (TrainerBean bean : trainerBeanList) {
                                                                                    if (bean.getId().equalsIgnoreCase(b.getSelectedId())) {
                                                                                        tempTable += "<td>" + bean.getFname() + " " + bean.getLname() + "</td>";
                                                                                    }
                                                                                }
                                                                            }
                                                                            tempTable += "<td>" + b.getDate() + "</td>";
                                                                            for (TimeslotBean bean : timeslotBeanList) {
                                                                                if (bean.getId().equalsIgnoreCase(b.getSelectedId())) {
                                                                                    tempTable += "<td>" + bean.getTimeslot() + "</td>";
                                                                                }
                                                                            }
                                                                        }
                                                                        out.println(tempTable + "</tr>");
                                                                    }
                                                                %>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <form action="booking" method="get">
                                                        <input type="hidden" name="action" value="create">
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <input class='form-control btn' type="submit" id="submit-btn" value="Submit">
                                                            </div>
                                                            <div class="col-md-3">
                                                                <input class='form-control btn' type="button" id="cancel-btn" onclick="location.href = 'home.jsp'" value ="Cancel" />
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            document.getElementById('type').onchange = function () {
                document.getElementById("trainer").disabled = (this.value === 'center');
                if (this.value === 'trainer') {
                    document.getElementById("centerDiv").style = "display:none";
                    document.getElementById("trainerDiv").style = "display:block";
                } else if (this.value === 'center') {
                    document.getElementById("centerDiv").style = "display:block";
                    document.getElementById("trainerDiv").style = "display:none";
                }
            }
        </script>
    </body>
</html>
