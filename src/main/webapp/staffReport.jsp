<%--
    Document   : staffReport
    Created on : Apr 25, 2022, 7:07:50 PM
    Author     : User
--%>

<%@page import="ict.bean.IncomeCenterBean"%>
<%@page import="ict.bean.IncomeTrainerBean"%>
<%@page import="ict.bean.ReportCenterBean"%>
<%@page import="ict.bean.ReportTrainerBean"%>
<%@page import="ict.bean.BookingTrainerBean"%>
<%@page import="ict.bean.BookingCenterBean"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Analytic / Report</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="bookingCenterList" scope="request" class="java.util.ArrayList<ict.bean.BookingCenterBean>" />
        <jsp:useBean id="bookingTrainerList" scope="request" class="java.util.ArrayList<ict.bean.BookingTrainerBean>" />
        <jsp:useBean id="reportTrainerList" scope="request" class="java.util.ArrayList<ict.bean.ReportTrainerBean>" />
        <jsp:useBean id="reportCenterList" scope="request" class="java.util.ArrayList<ict.bean.ReportCenterBean>" />
        <jsp:useBean id="reportTrainerIncomeList" scope="request" class="java.util.ArrayList<ict.bean.IncomeTrainerBean>" />
        <jsp:useBean id="reportCenterIncomeList" scope="request" class="java.util.ArrayList<ict.bean.IncomeCenterBean>" />
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
                                        + "<li class='nav-item'> <a class='nav-link nav-left' href='staffBooking?action=list'><i class='bi bi-clipboard-check'></i> Confirm/Decline Booking</a></li>";
                            } else if (staffInfo.getType().equalsIgnoreCase("Senior Management")) {
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left active' href='staffReport?action=list'><i class='bi bi-pie-chart-fill'></i> Analytic / Report</a></li>"
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
                        Analytic / Report
                    </div>
                    <div class="row">
                        <div class="col-md">
                            <div id="search-input-container">
                                <ul class="nav nav-tabs" id="tab" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link active" id="record-tab" data-bs-toggle="tab" data-bs-target="#record" type="button" role="tab" aria-controls="record" aria-selected="true">Booking</button>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link" id="rate-tab" data-bs-toggle="tab" data-bs-target="#rate" type="button" role="tab" aria-controls="rate" aria-selected="false">Booking Rate</button>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link" id="income-tab" data-bs-toggle="tab" data-bs-target="#income" type="button" role="tab" aria-controls="income" aria-selected="false">Income</button>
                                    </li>
                                </ul>
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="container overflow-auto" id="content-container">
                            <div class="tab-content" id="tabContent">
                                <div class="tab-pane show active" id="record" role="tabpanel" aria-labelledby="record-tab">
                                    <h3>Booking Report</h3>

                                    <form>
                                        <div class="row">
                                            <div class="col">
                                                <input class='form-control select-filter' type="text" id="searchInputBookingID" placeholder="Booking ID...">
                                            </div>
                                            <div class="col">
                                                <input class='form-control select-filter' type="text" id="searchInputCustomerID" placeholder="Customer ID...">
                                            </div>
                                            <div class="col">
                                                <input class='form-control select-filter' type="text" id="searchInputCustomer" placeholder="Customer Name...">
                                            </div>
                                            <div class="col">
                                                <input class='form-control select-filter' type="text" id="searchInputCenterTrainerID" placeholder="Center/Trainer ID...">
                                            </div>
                                            <div class="col">
                                                <input class='form-control select-filter' type="text" id="searchInputCenterTrainer" placeholder="Center/Trainer Name...">
                                            </div>
                                            <div class="col">
                                                <input class='form-control select-filter' type="text" id="searchInputType" placeholder="Type...">
                                            </div>

                                            <div class="col">
                                                <div class="row">
                                                    <div class="col"><input class='form-control btn' type="button" id="function-btn" onclick="exportTableToExcel('table', 'report')" value="Export"></div>
                                                    <div class="col"><input class='form-control btn' type="reset" id="reset-btn" value="Reset"></div>
                                                </div>

                                            </div>
                                        </div>
                                    </form>
                                    <div class="col overflow-auto half-col-admin-full">

                                        <%
                                            String table = "<table class='table table-striped table-sm' id='table'>"
                                                    + "<thead class='thead'><tr><th>ID</th><th>Customer ID</th><th>Customer Name</th><th>Center/Trainer ID</th>"
                                                    + "<th>Center/Trainer Name</th><th>Fee</th><th>Timeslot</th>"
                                                    + "<th>Date</th><th>Status</th><th>Booking Time</th><th>Type</th>"
                                                    + "<thead></tr><tbody class='tbody' id='bookingTable'>";

                                            for (BookingCenterBean b : bookingCenterList) {
                                                table += "<tr>"
                                                        + "<td>" + b.getId() + "</td>"
                                                        + "<td>" + b.getCustId() + "</td>"
                                                        + "<td>" + b.getCustName() + "</td>"
                                                        + "<td>" + b.getCenterId() + "</td>"
                                                        + "<td>" + b.getCenterName() + "</td>"
                                                        + "<td>" + b.getFee() + "</td>"
                                                        + "<td>" + b.getTimeslot() + "</td>"
                                                        + "<td>" + b.getDate() + "</td>"
                                                        + "<td>" + b.getStatus() + "</td>"
                                                        + "<td>" + b.getTimestamp() + "</td>"
                                                        + "<td>Center</td>";

                                            }

                                            for (BookingTrainerBean b : bookingTrainerList) {
                                                table += "<tr>"
                                                        + "<td>" + b.getId() + "</td>"
                                                        + "<td>" + b.getCustId() + "</td>"
                                                        + "<td>" + b.getCustName() + "</td>"
                                                        + "<td>" + b.getTrainerId() + "</td>"
                                                        + "<td>" + b.getTrainerName() + "</td>"
                                                        + "<td>" + b.getFee() + "</td>"
                                                        + "<td>" + b.getTimeslot() + "</td>"
                                                        + "<td>" + b.getDate() + "</td>"
                                                        + "<td>" + b.getStatus() + "</td>"
                                                        + "<td>" + b.getTimestamp() + "</td>"
                                                        + "<td>Trainer</td>";

                                                table += "</tr>";
                                            }
                                            out.println(table + "</tbody></table>");
                                        %>
                                    </div>
                                </div>

                                <%
                                    // pie chart for booking rate
                                    // trainer rate
                                    Gson gsonObjTrainer = new Gson();
                                    Map<Object, Object> mapTrainer = null;
                                    List<Map<Object, Object>> listTrainer = new ArrayList<Map<Object, Object>>();
                                    String title = "";

                                    for (ReportTrainerBean b : reportTrainerList) {
                                        mapTrainer = new HashMap<Object, Object>();
                                        mapTrainer.put("label", b.getFname() + " " + b.getLname());
                                        mapTrainer.put("y", Double.parseDouble(b.getRate()));
                                        listTrainer.add(mapTrainer);
                                        title = b.getType();
                                    }

                                    String dataPointsTrainer = gsonObjTrainer.toJson(listTrainer);

                                    // center rate
                                    Gson gsonObjCenter = new Gson();
                                    Map<Object, Object> mapCenter = null;
                                    List<Map<Object, Object>> listCenter = new ArrayList<Map<Object, Object>>();

                                    for (ReportCenterBean b : reportCenterList) {
                                        mapCenter = new HashMap<Object, Object>();
                                        mapCenter.put("label", b.getName());
                                        mapCenter.put("y", Double.parseDouble(b.getRate()));
                                        listCenter.add(mapCenter);
                                        title = b.getType();
                                    }

                                    String dataPointsCenter = gsonObjCenter.toJson(listCenter);

                                    if (request.getParameter("type") != null) {
                                        if (request.getParameter("type").equalsIgnoreCase("year")) {
                                            title = request.getParameter("type") + " " + request.getParameter("year");
                                        } else if (request.getParameter("type").equalsIgnoreCase("month")) {
                                            title = request.getParameter("type") + " " + request.getParameter("month");
                                        }
                                    } else {
                                        title = "Month 4";
                                    }

                                    // bar chart for booking rate
                                    // trainer income
                                    Gson gsonObjTrainerInc = new Gson();
                                    Map<Object, Object> mapTrainerInc = null;
                                    List<Map<Object, Object>> listTrainerInc = new ArrayList<Map<Object, Object>>();
                                    String titleInc = "";

                                    for (IncomeTrainerBean b : reportTrainerIncomeList) {
                                        mapTrainerInc = new HashMap<Object, Object>();
                                        mapTrainerInc.put("label", b.getFname() + " " + b.getLname());
                                        mapTrainerInc.put("y", Double.parseDouble(b.getIncome()));
                                        listTrainerInc.add(mapTrainerInc);
                                        titleInc = b.getType();
                                    }

                                    String dataPointsTrainerInc = gsonObjTrainerInc.toJson(listTrainerInc);

                                    // center income
                                    Gson gsonObjCenterInc = new Gson();
                                    Map<Object, Object> mapCenterInc = null;
                                    List<Map<Object, Object>> listCenterInc = new ArrayList<Map<Object, Object>>();

                                    for (IncomeCenterBean b : reportCenterIncomeList) {
                                        mapCenterInc = new HashMap<Object, Object>();
                                        mapCenterInc.put("label", b.getName());
                                        mapCenterInc.put("y", Double.parseDouble(b.getIncome()));
                                        listCenterInc.add(mapCenterInc);
                                        titleInc = b.getType();
                                    }

                                    String dataPointsCenterInc = gsonObjCenterInc.toJson(listCenterInc);

                                    if (request.getParameter("type") != null) {
                                        if (request.getParameter("type").equalsIgnoreCase("year")) {
                                            titleInc = request.getParameter("type") + " " + request.getParameter("year");
                                        } else if (request.getParameter("type").equalsIgnoreCase("month")) {
                                            titleInc = request.getParameter("type") + " " + request.getParameter("month");
                                        }
                                    } else {
                                        titleInc = "Month 4";
                                    }


                                %>

                                <!-- Chart Modals -->
                                <div class="modal fade" id="trainerRateModal" tabindex="-1" aria-labelledby="trainerRateModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-lg modal-chart">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="trainerRateModalLabel">Trainer Rate</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div id="trainerRateChartContainer" style="height: 500px; width: 100%;"></div>
                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <div class="modal fade" id="centerRateModal" tabindex="-1" aria-labelledby="centerRateModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-lg modal-chart">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="centerRateModalLabel">Center Rate</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div id="centerRateChartContainer" style="height: 500px; width: 100%;"></div>
                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <div class="modal fade" id="trainerIncomeModal" tabindex="-1" aria-labelledby="trainerIncomeModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-lg modal-chart">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="trainerIncomeModalLabel">Trainer Income</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div id="trainerIncomeChartContainer" style="height: 500px; width: 100%;"></div>
                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <div class="modal fade" id="centerIncomeModal" tabindex="-1" aria-labelledby="centerIncomeModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-lg modal-chart">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="centerIncomeModalLabel">Center Income</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div id="centerIncomeChartContainer" style="height: 500px; width: 100%;"></div>
                                            </div>

                                        </div>
                                    </div>
                                </div>

                                <div class="tab-pane" id="rate" role="tabpanel" aria-labelledby="rate-tab">
                                    <div class="col">
                                        <h3>Trainer Booking Rate</h3>
                                        <div class="row">
                                            <div class="col-6">
                                                <form action="staffReport" method="get">
                                                    <input type="hidden" name="action" value="getTrainerRate">
                                                    <div class="row">
                                                        <div class="col">
                                                            <select class='form-select select-filter' name="type">
                                                                <option value="Month">Month</option>
                                                                <option value="Year">Year</option>
                                                            </select></div>
                                                        <div class="col">
                                                            <select class='form-select select-filter' name="month">
                                                                <option value="1">January</option>
                                                                <option value="2">February</option>
                                                                <option value="3">March</option>
                                                                <option value="4" selected>April</option>
                                                                <option value="5">May</option>
                                                                <option value="6">June</option>
                                                                <option value="7">July</option>
                                                                <option value="8">August</option>
                                                                <option value="9">September</option>
                                                                <option value="10">October</option>
                                                                <option value="11">November</option>
                                                                <option value="12">December</option>
                                                            </select></div>
                                                        <div class="col">
                                                            <input class='form-control select-filter' type="text" name="year" placeholder="Year..." value="<%= request.getAttribute("currYear")%>" >
                                                        </div>
                                                        <div class="col">
                                                            <input class='form-control btn' type="submit" id="function-btn" value="Submit">
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="col-6">
                                                <div class="d-flex flex-row-reverse">
                                                    <input class='form-control select-filter' type="text" id="searchRateInputTrainerID" placeholder="Trainer ID...">
                                                    <!-- Button trigger modal -->
                                                    <button type="button" class="btn modal-trigger-btn" onclick="render()" data-bs-toggle="modal" data-bs-target="#trainerRateModal">
                                                        <i class="bi bi-pie-chart-fill"></i>
                                                        Chart
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col overflow-auto half-col-admin">
                                            <%            String rateTable = "<table class='table table-striped table-sm' id='rateTrainer'  >"
                                                        + "<thead class='thead'><tr><th>ID</th><th>Trainer Name</th>"
                                                        + "<th>Center Name</th><th>Count</th><th>Rate</th><th>Type</th>"
                                                        + "<thead></tr><tbody class='tbody' id='rateTrainerTable'>";

                                                for (ReportTrainerBean b : reportTrainerList) {

                                                    rateTable += "<tr>"
                                                            + "<td>" + b.getId() + "</td>"
                                                            + "<td>" + b.getFname() + ' ' + b.getLname() + "</td>"
                                                            + "<td>" + b.getCenterName() + "</td>"
                                                            + "<td>" + b.getCount() + "</td>"
                                                            + "<td>" + b.getRate() + "%</td>"
                                                            + "<td>" + b.getType() + "</td>";

                                                    rateTable += "</tr>";
                                                }
                                                out.println(rateTable + "</tbody></table>");
                                            %>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <h3>Center Booking Rate</h3>
                                        <div class="row">
                                            <div class="col-6">
                                                <form action="staffReport" method="get">
                                                    <input type="hidden" name="action" value="getCenterRate">
                                                    <div class="row">
                                                        <div class="col">
                                                            <select class='form-select select-filter' name="type">
                                                                <option value="Month">Month</option>
                                                                <option value="Year">Year</option>
                                                            </select></div>
                                                        <div class="col">
                                                            <select class='form-select select-filter' name="month">
                                                                <option value="1">January</option>
                                                                <option value="2">February</option>
                                                                <option value="3">March</option>
                                                                <option value="4" selected>April</option>
                                                                <option value="5">May</option>
                                                                <option value="6">June</option>
                                                                <option value="7">July</option>
                                                                <option value="8">August</option>
                                                                <option value="9">September</option>
                                                                <option value="10">October</option>
                                                                <option value="11">November</option>
                                                                <option value="12">December</option>
                                                            </select></div>
                                                        <div class="col">
                                                            <input class='form-control select-filter' type="text" name="year" placeholder="Year..." value="<%= request.getAttribute("currYear")%>" >
                                                        </div>
                                                        <div class="col">
                                                            <input class='form-control btn' type="submit" id="function-btn" value="Submit">
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="col-6">
                                                <div class="d-flex flex-row-reverse">
                                                    <input class='form-control select-filter' type="text" id="searchRateInputCenterID" placeholder="Center ID...">
                                                    <!-- Button trigger modal -->
                                                    <button type="button" class="btn modal-trigger-btn" onclick="render()" data-bs-toggle="modal" data-bs-target="#centerRateModal">
                                                        <i class="bi bi-pie-chart-fill"></i>
                                                        Chart
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col overflow-auto half-col-admin">
                                            <%
                                                String rateCenterTable = "<table class='table table-striped table-sm' id='rateCenter'  >"
                                                        + "<thead class='thead'><tr><th>ID</th><th>Center Name</th>"
                                                        + "<th>Count</th><th>Rate</th><th>Type</th>"
                                                        + "<thead></tr><tbody class='tbody' id='rateCenterTable'>";

                                                for (ReportCenterBean b : reportCenterList) {

                                                    rateCenterTable += "<tr>"
                                                            + "<td>" + b.getId() + "</td>"
                                                            + "<td>" + b.getName() + "</td>"
                                                            + "<td>" + b.getCount() + "</td>"
                                                            + "<td>" + b.getRate() + "</td>"
                                                            + "<td>" + b.getType() + "</td>";

                                                    rateCenterTable += "</tr>";

                                                }
                                                out.println(rateCenterTable + "</tbody></table>");
                                            %>
                                        </div>
                                    </div>


                                </div>

                                <div class="tab-pane" id="income" role="tabpanel" aria-labelledby="income-tab">
                                    <div class="col">
                                        <h3>Trainer Income</h3>
                                        <div class="row">
                                            <div class="col-6">
                                                <form action="staffReport" method="get">
                                                    <input type="hidden" name="action" value="getTrainerIncome">
                                                    <div class="row">
                                                        <div class="col">
                                                            <select class='form-select select-filter' name="type">
                                                                <option value="Month">Month</option>
                                                                <option value="Year">Year</option>
                                                            </select></div>
                                                        <div class="col">
                                                            <select class='form-select select-filter' name="month">
                                                                <option value="1">January</option>
                                                                <option value="2">February</option>
                                                                <option value="3">March</option>
                                                                <option value="4" selected>April</option>
                                                                <option value="5">May</option>
                                                                <option value="6">June</option>
                                                                <option value="7">July</option>
                                                                <option value="8">August</option>
                                                                <option value="9">September</option>
                                                                <option value="10">October</option>
                                                                <option value="11">November</option>
                                                                <option value="12">December</option>
                                                            </select></div>
                                                        <div class="col">
                                                            <input class='form-control select-filter' type="text" name="year" placeholder="Year..." value="<%= request.getAttribute("currYear")%>" >
                                                        </div>
                                                        <div class="col">
                                                            <input class='form-control btn' type="submit" id="function-btn" value="Submit">
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="col-6">
                                                <div class="d-flex flex-row-reverse">
                                                    <input class='form-control select-filter' type="text" id="searchIncomeInputTrainerID" placeholder="Trainer ID...">
                                                    <!-- Button trigger modal -->
                                                    <button type="button" class="btn modal-trigger-btn" onclick="render()" data-bs-toggle="modal" data-bs-target="#trainerIncomeModal">
                                                        <i class="bi bi-bar-chart-fill"></i>
                                                        Chart
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col overflow-auto half-col-admin">
                                            <%            String incomeTrainerTable = "<table class='table table-striped table-sm' id='incomeTrainer'  >"
                                                        + "<thead class='thead'><tr><th>ID</th><th>Type</th><th>Trainer Name</th>"
                                                        + "<th>Center Name</th><th>Count</th><th>Income</th>"
                                                        + "<thead></tr><tbody class='tbody' id='incomeTrainerTable'>";

                                                for (IncomeTrainerBean b : reportTrainerIncomeList) {

                                                    incomeTrainerTable += "<tr>"
                                                            + "<td>" + b.getId() + "</td>"
                                                            + "<td>" + b.getType() + "ly</td>"
                                                            + "<td>" + b.getFname() + ' ' + b.getLname() + "</td>"
                                                            + "<td>" + b.getCenterName() + "</td>"
                                                            + "<td>" + b.getCount() + "</td>"
                                                            + "<td>$" + b.getIncome() + "</td>";

                                                    incomeTrainerTable += "</tr>";

                                                }
                                                out.println(incomeTrainerTable + "</tbody></table>");
                                            %>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <h3>Center Income</h3>
                                        <div class="row">
                                            <div class="col-6">
                                                <form action="staffReport" method="get">
                                                    <input class='form-control select-filter' type="hidden" name="action" value="getCenterIncome">
                                                    <div class="row">
                                                        <div class="col">
                                                            <select class='form-select select-filter' name="type">
                                                                <option value="Month">Month</option>
                                                                <option value="Year">Year</option>
                                                            </select></div>
                                                        <div class="col">
                                                            <select class='form-select select-filter' name="month">
                                                                <option value="1">January</option>
                                                                <option value="2">February</option>
                                                                <option value="3">March</option>
                                                                <option value="4" selected>April</option>
                                                                <option value="5">May</option>
                                                                <option value="6">June</option>
                                                                <option value="7">July</option>
                                                                <option value="8">August</option>
                                                                <option value="9">September</option>
                                                                <option value="10">October</option>
                                                                <option value="11">November</option>
                                                                <option value="12">December</option>
                                                            </select></div>
                                                        <div class="col">
                                                            <input class='form-control select-filter' type="text" name="year" placeholder="Year..." value="<%= request.getAttribute("currYear")%>" >
                                                        </div>
                                                        <div class="col">
                                                            <input class='form-control btn' type="submit" id="function-btn" value="Submit">
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="col-6">
                                                <div class="d-flex flex-row-reverse">
                                                    <input class='form-control select-filter' type="text" id="searchIncomeInputCenterID" placeholder="Center ID...">
                                                    <!-- Button trigger modal -->
                                                    <button type="button" class="btn modal-trigger-btn" onclick="render()" data-bs-toggle="modal" data-bs-target="#centerIncomeModal">
                                                        <i class="bi bi-bar-chart-fill"></i>
                                                        Chart
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col overflow-auto half-col-admin">
                                            <%            String incomeCenterTable = "<table class='table table-striped table-sm' id='incomeCenter'  >"
                                                        + "<thead class='thead'><tr><th>ID</th><th>Type</th><th>Center Name</th>"
                                                        + "<th>Count</th><th>Income</th>"
                                                        + "<thead></tr><tbody class='tbody' id='incomeCenterTable'>";

                                                for (IncomeCenterBean b : reportCenterIncomeList) {

                                                    incomeCenterTable += "<tr>"
                                                            + "<td>" + b.getId() + "</td>"
                                                            + "<td>" + b.getType() + "ly</td>"
                                                            + "<td>" + b.getName() + "</td>"
                                                            + "<td>" + b.getCount() + "</td>"
                                                            + "<td>$" + b.getIncome() + "</td>";

                                                    incomeCenterTable += "</tr>";
                                                }
                                                out.println(incomeCenterTable + "</tbody></table>");
                                            %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        </br>

        <script>

            var $rows = $('#bookingTable tr');
            $('#resetButton').on('click', function () {
                $rows.show();
            });

            $('#searchInputBookingID, #searchInputCustomerID, \n\
        #searchInputCustomer,#searchInputCenterTrainerID, #searchInputCenterTrainer, #searchInputType').on('input', function () {
                var bookingIdInput = $.trim($('#searchInputBookingID').val()).replace(/ +/g, ' ').toLowerCase();
                var customerIdInput = $.trim($('#searchInputCustomerID').val()).replace(/ +/g, ' ').toLowerCase();
                var customerNameInput = $.trim($('#searchInputCustomer').val()).replace(/ +/g, ' ').toLowerCase();
                var trainerIdInput = $.trim($('#searchInputCenterTrainerID').val()).replace(/ +/g, ' ').toLowerCase();
                var centerTrainerNameInput = $.trim($('#searchInputCenterTrainer').val()).replace(/ +/g, ' ').toLowerCase();
                var typeInput = $.trim($('#searchInputType').val()).replace(/ +/g, ' ').toLowerCase();


                $rows.show().filter(function () {
                    var bookingId = $(this).find('td:nth-child(1)').text().replace(/\s+/g, ' ').toLowerCase();
                    var customerId = $(this).find('td:nth-child(2)').text().replace(/\s+/g, ' ').toLowerCase();
                    var customerName = $(this).find('td:nth-child(3)').text().replace(/\s+/g, ' ').toLowerCase();
                    var trainerId = $(this).find('td:nth-child(4)').text().replace(/\s+/g, ' ').toLowerCase();
                    var trainerName = $(this).find('td:nth-child(5)').text().replace(/\s+/g, ' ').toLowerCase();
                    var type = $(this).find('td:nth-child(11)').text().replace(/\s+/g, ' ').toLowerCase();

                    return !~bookingId.indexOf(bookingIdInput) || !~customerId.indexOf(customerIdInput) ||
                            !~customerName.indexOf(customerNameInput) || !~trainerId.indexOf(trainerIdInput) ||
                            !~trainerName.indexOf(centerTrainerNameInput) || !~type.indexOf(typeInput);
                }).hide();
            });

            var $rateTrainerRows = $('#rateTrainerTable tr');
            $('#searchRateInputTrainerID').on('input', function () {
                var rateTrainerIdInput = $.trim($('#searchRateInputTrainerID').val()).replace(/ +/g, ' ').toLowerCase();


                $rateTrainerRows.show().filter(function () {
                    var rateTrainerId = $(this).find('td:nth-child(1)').text().replace(/\s+/g, ' ').toLowerCase();

                    return !~rateTrainerId.indexOf(rateTrainerIdInput);
                }).hide();
            });

            var $rateCenterRows = $('#rateCenterTable tr');
            $('#searchRateInputCenterID').on('input', function () {
                var rateCenterIdInput = $.trim($('#searchRateInputCenterID').val()).replace(/ +/g, ' ').toLowerCase();


                $rateCenterRows.show().filter(function () {
                    var rateCenterId = $(this).find('td:nth-child(1)').text().replace(/\s+/g, ' ').toLowerCase();

                    return !~rateCenterId.indexOf(rateCenterIdInput);
                }).hide();
            });


            var $incomeTrainerRows = $('#incomeTrainerTable tr');
            $('#searchIncomeInputTrainerID').on('input', function () {
                var incomeTrainerIdInput = $.trim($('#searchIncomeInputTrainerID').val()).replace(/ +/g, ' ').toLowerCase();


                $incomeTrainerRows.show().filter(function () {
                    var incomeTrainerId = $(this).find('td:nth-child(1)').text().replace(/\s+/g, ' ').toLowerCase();

                    return !~incomeTrainerId.indexOf(incomeTrainerIdInput);
                }).hide();
            });

            var $incomeCenterRows = $('#incomeCenterTable tr');
            $('#searchIncomeInputCenterID').on('input', function () {
                var incomeCenterIdInput = $.trim($('#searchIncomeInputCenterID').val()).replace(/ +/g, ' ').toLowerCase();


                $incomeCenterRows.show().filter(function () {
                    var incomeCenterId = $(this).find('td:nth-child(1)').text().replace(/\s+/g, ' ').toLowerCase();

                    return !~incomeCenterId.indexOf(incomeCenterIdInput);
                }).hide();
            });

            if ("<%= request.getParameter("page")%>" === "record") {
                document.getElementById("record").classList.add('active');
                document.getElementById("record-tab").classList.add('active');
                document.getElementById("rate").classList.remove('active');
                document.getElementById("rate-tab").classList.remove('active');
                document.getElementById("income").classList.remove('active');
                document.getElementById("income-tab").classList.remove('active');
            } else if ("<%= request.getParameter("page")%>" === "rate") {
                document.getElementById("record").classList.remove('active');
                document.getElementById("record-tab").classList.remove('active');
                document.getElementById("rate").classList.add('active');
                document.getElementById("rate-tab").classList.add('active');
                document.getElementById("income").classList.remove('active');
                document.getElementById("income-tab").classList.remove('active');
            } else if ("<%= request.getParameter("page")%>" === "income") {
                document.getElementById("record").classList.remove('active');
                document.getElementById("record-tab").classList.remove('active');
                document.getElementById("rate").classList.remove('active');
                document.getElementById("rate-tab").classList.remove('active');
                document.getElementById("income").classList.add('active');
                document.getElementById("income-tab").classList.add('active');
            }
        </script>

        <script type="text/javascript">

            function render() {

                var chartTrainer = new CanvasJS.Chart("trainerRateChartContainer", {
                    theme: "light2", // "light1", "dark1", "dark2"
                    exportEnabled: true,
                    animationEnabled: true,
                    title: {
                        text: "Trainer Rate",
                        fontSize: 16
                    },
                    subtitles: [{
                            text: "<%out.print(title);%>"
                        }],
                    data: [{
                            type: "pie",
                            toolTipContent: "<b>{label}</b>: {y}%",
                            indexLabelFontSize: 12,
                            indexLabel: "{label} - {y}%",
                            dataPoints: <%out.print(dataPointsTrainer);%>
                        }]
                });
                chartTrainer.render();

                var chartCenter = new CanvasJS.Chart("centerRateChartContainer", {
                    theme: "light2", // "light1", "dark1", "dark2"
                    exportEnabled: true,
                    animationEnabled: true,
                    title: {
                        text: "Center Rate",
                        fontSize: 16
                    },
                    subtitles: [{
                            text: "<%out.print(title);%>"
                        }],
                    data: [{
                            type: "pie",
                            toolTipContent: "<b>{label}</b>: {y}%",
                            indexLabelFontSize: 12,
                            indexLabel: "{label} - {y}%",
                            dataPoints: <%out.print(dataPointsCenter);%>
                        }]
                });
                chartCenter.render();

                var chartTrainerInc = new CanvasJS.Chart("trainerIncomeChartContainer", {
                    theme: "light2", // "light1", "dark1", "dark2"
                    exportEnabled: true,
                    animationEnabled: true,
                    title: {
                        text: "Trainer Income",
                        fontSize: 16
                    },
                    subtitles: [{
                            text: "<%out.print(titleInc);%>"
                        }],
                    axisY: {
                        title: "Income",
                        includeZero: true,
                        titleFontSize: 14

                    },
                    data: [{
                            type: "bar",
                            toolTipContent: "<b>{label}</b>: \${y}",
                            indexLabelFontSize: 12,
                            indexLabel: "{y}",
                            indexLabelFontColor: "#444",
                            indexLabelPlacement: "inside",
                            dataPoints: <%out.print(dataPointsTrainerInc);%>
                        }]
                });
                chartTrainerInc.render();

                var chartCenterInc = new CanvasJS.Chart("centerIncomeChartContainer", {
                    theme: "light2", // "light1", "dark1", "dark2"
                    exportEnabled: true,
                    animationEnabled: true,
                    title: {
                        text: "Center Income",
                        fontSize: 16
                    },
                    subtitles: [{
                            text: "<%out.print(titleInc);%>"
                        }],
                    axisY: {
                        title: "Income",
                        includeZero: true,
                        titleFontSize: 14
                    },
                    data: [{
                            type: "bar",
                            toolTipContent: "<b>{label}</b>: \${y}",
                            indexLabelFontSize: 12,
                            indexLabel: "{y}",
                            indexLabelFontColor: "#444",
                            indexLabelPlacement: "inside",
                            dataPoints: <%out.print(dataPointsCenterInc);%>
                        }]
                });
                chartCenterInc.render();

            }
        </script>

        <script type="text/javascript" src="js/export-excel.js"></script>
        <script type="text/javascript" src="js/canvasjs.min.js"></script>
    </body>
</html>
