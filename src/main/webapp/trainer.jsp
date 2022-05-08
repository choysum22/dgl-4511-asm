<%@page import="ict.bean.*, java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Trainer List</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="trainerList" scope="request" class="java.util.ArrayList<ict.bean.TrainerBean>" />
        <div class="container-fluid mt-3">
            <jsp:useBean id="customerInfo" class="ict.bean.CustomerBean" scope="session" />
            <div class="row" id="navbar">
                <div class="col-12" id="navbar-icon-col">
                    <a href="home.jsp"><h5 class="mb-3 user-select-none" id="navbar-icon">Dream Gym Limited</h5></a>
                </div>
            </div>
            <div class="row">
                <div class="col-2" id="navbar-left">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active nav-left" href="trainer?action=list" aria-current="page">
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
                        Trainer List
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="d-flex flex-row" id="search-input-container">
                                <input class="form-control select-filter" type="text" id="searchInputTrainerID" placeholder="Search">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="d-flex flex-row-reverse" id="search-input-container">

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="container user-select-none overflow-auto list-container" id="content-container">
                            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-6 g-5">
                                <%
                                    String cardDiv = "";
                                    for (TrainerBean t : trainerList) {
                                        cardDiv += "<div class='col card-col'>"
                                                + "<div class='card h-100'>"
                                                + "<img srcset=\"" + t.getImg() + " 2x\" class='card-img-top'>"
                                                + "<div class='card-body'>"
                                                + "<h6 class='card-title mb-0'>" + t.getFname() + " " + t.getLname() + "</h6>"
                                                + "<p class='card-text'><small class='text-muted'>" + t.getCenterName() + "</small></p>"
                                                + "<p class='card-text'>" + t.getDesc() + "</p>"
                                                + "<p class='card-text mb-0'>(Tel) " + t.getTel() + "</p>"
                                                + "<div class='row '>"
                                                + "<div class='col'><small class='text-muted'>Fee: " + t.getFee() + "</small></div>"
                                                + "<div class='col d-flex flex-row-reverse'><small class='text-muted'>" + t.getStatus() + "</small></div>"
                                                + "</div>"
                                                + "</div></div></div>";
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
