<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <title>Home</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <div class="container-fluid mt-3">
            <jsp:useBean id="customerInfo" class="ict.bean.CustomerBean" scope="session" />
            <div class="row" id="navbar">
                <div class="col-12" id="navbar-icon-col">
                    <h5 class="mb-3 user-select-none" id="navbar-icon">Dream Gym Limited</h5>
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
                            <a class="nav-link nav-left" href="booking?action=list&id=<jsp:getProperty name="customerInfo" property="id"/>">
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
                        Hello, <jsp:getProperty name="customerInfo" property="fname" />  <jsp:getProperty name="customerInfo" property="lname" />
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
                            <h1 class="welcomeSlogan">Welcome to Dream Gym Limited</h1>
                            <h1 class="welcomeSlogan">Together, We Dream Better.</h1>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
