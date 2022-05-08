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
                            <a class="nav-link active nav-left" href="center?action=list">
                                <i class="bi bi-building"></i> Gym Centers
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-left" href="booking?action=book">
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
                        Center List
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="d-flex flex-row" id="search-input-container">
                                <input class="form-control select-filter" type="text" id="searchInputCenterID" placeholder="Search">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="d-flex flex-row-reverse" id="search-input-container">

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="container overflow-auto list-container" id="content-container">
                            <div class="row row-cols-sm-1 row-cols-md-2 g-4 ">
                                <%
                                    String cardDiv = "";
                                    for (CenterBean c : centerList) {
                                        cardDiv += "<div class='col card-col'>"
                                                + "<div class='card h-100' >"
                                                + "<div class='row g-0'>"
                                                + "<div class='col-md-4'>"
                                                + "<img srcset=\"" + c.getImg() + "\" class='img-fluid rounded-start'>"
                                                + "</div>"
                                                + "<div class='col-md-8 '>"
                                                + "<div class='card-body center-card-body'>"
                                                + "<h5 class='card-title mb-0'>" + c.getName() + "</h5>"
                                                + "<p class='card-text'><small class='text-muted'>" + c.getLocation() + "</small></p>"
                                                + "<p class='card-text'>" + c.getDesc() + "</p>"
                                                + "</div>"
                                                + "<div class='row p-3'>"
                                                + "<div class='col'><small class='text-muted'>Fee: " + c.getFee() + "</small></div>"
                                                + "<div class='col'><small class='text-muted'>(Tel) " + c.getTel() + "</small></div>"
                                                + "<div class='col d-flex flex-row-reverse'><small class='text-muted'>" + c.getStatus() + "</small></div>"
                                                + "</div>"
                                                + "</div></div></div></div>";
                                    }
                                    out.println(cardDiv);

                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            var $rows = $('.card-col');
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
