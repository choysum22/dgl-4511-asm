<%--
    Document   : updateBooking
    Created on : Apr 24, 2022, 1:02:30 AM
    Author     : User
--%>

<%@page import="ict.bean.TimeslotBean"%>
<%@page import="ict.bean.CustomerBean"%>
<%@page import="ict.bean.CenterBean"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Booking</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="trainer" scope="request" class="ict.bean.BookingTrainerBean" />
        <jsp:useBean id="center" scope="request" class="ict.bean.BookingCenterBean" />
        <jsp:useBean id="centerBeanList" scope="request" class="java.util.ArrayList<ict.bean.CenterBean>" />
        <jsp:useBean id="timeslotBeanList" scope="request" class="java.util.ArrayList<ict.bean.TimeslotBean>" />
        <jsp:useBean id="customerInfo" class="ict.bean.CustomerBean" scope="session" />
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
                        Update Booking
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
                            <form action="booking" method="get">

                                <%
                                    String id = request.getParameter("id");
                                    Date date = new Date();
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateNow = formatter.format(date);
                                    String type = (String) request.getAttribute("type");
                                %>
                                <h6>Booking ID: <%= id%></h6>

                                <input type="hidden" name="action" value="edit"/>
                                <input type="hidden" name="type" value="<%= type%>"/>
                                <input type="hidden" name="id" value="<%= id%>"/>
                                <input type="hidden" name="uid" value="<%= customerInfo.getId()%>"/>

                                <%
                                    String timeslotId = "";
                                    String bookedDate = "";
                                    String centerOptions = "";
                                    if (type.equals("trainer")) {
                                        centerOptions = "Center: <select class='form-select' disabled name='center'><option disabled value> -- select a gym center -- </option>";
                                        for (CenterBean b : centerBeanList) {
                                            if (trainer.getCenterId().equals(b.getId())) {
                                                centerOptions += "<option value=\"" + b.getId() + "\" selected>" + b.getName() + "</option>";
                                            } else {
                                                centerOptions += "<option value=\"" + b.getId() + "\">" + b.getName() + "</option>";
                                            }
                                        }
                                        timeslotId = trainer.getTimeslotId();
                                        bookedDate = trainer.getDate();
                                    } else if (type.equals("center")) {
                                        centerOptions = "Center: <select class='form-select' name='center'><option disabled value> -- select a gym center -- </option>";
                                        for (CenterBean b : centerBeanList) {
                                            if (center.getCenterId().equals(b.getId())) {
                                                centerOptions += "<option value=\"" + b.getId() + "\" selected>" + b.getName() + "</option>";
                                            } else {
                                                centerOptions += "<option value=\"" + b.getId() + "\">" + b.getName() + "</option>";
                                            }
                                        }
                                        timeslotId = center.getTimeslotId();
                                        bookedDate = center.getDate();
                                    }

                                    out.println(centerOptions + "</select>");

                                    out.println("</br>Date: <input class='form-control' type='date' name='date' id='date' min='"
                                            + dateNow + "' value='" + bookedDate + "'> </br>");

                                    String timeslotOptions = "";
                                    timeslotOptions = "Timeslot: <select class='form-select' name='timeslot'>"
                                            + "<option disabled value> -- select a timeslot -- </option>";
                                    for (TimeslotBean b : timeslotBeanList) {
                                        if (timeslotId.equals(b.getId())) {
                                            timeslotOptions += "<option value=\"" + b.getId() + "\" selected>" + b.getTimeslot() + "</option>";
                                        } else {
                                            timeslotOptions += "<option value=\"" + b.getId() + "\">" + b.getTimeslot() + "</option>";
                                        }
                                    }

                                    out.println(timeslotOptions + "</select>");
                                %>
                                </br>
                                <div class="row">
                                    <div class="col-md-3">
                                        <input class='form-control btn' id="submit-btn" type="submit" value="Submit">
                                    </div>
                                    <div class="col-md-3">
                                        <input class='form-control btn' id="cancel-btn" type="button"
                                               onclick="location.href = 'booking?action=list&id=<%= customerInfo.getId()%>';"
                                               value="Cancel" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>date.min = new Date().toLocaleDateString('en')</script>
    </body>
</html>
