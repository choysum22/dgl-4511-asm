<%@page import="ict.bean.CustomerBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/tlds/customer.tld" prefix="cust" %>
<%@ taglib uri="/WEB-INF/tlds/staff.tld" prefix="staff" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Management</title>
        <%@include file="bootstrap.jsp" %>
    </head>
    <body>
        <jsp:useBean id="customerList" scope="request" class="java.util.ArrayList<ict.bean.CustomerBean>" />
        <jsp:useBean id="staffList" scope="request" class="java.util.ArrayList<ict.bean.StaffBean>" />
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
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left' href='staffTrainer?action=list'><i class='bi bi-person-bounding-box'></i> Personal Trainers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left ' href='staffCenter?action=list'><i class='bi bi-building'></i> Gym Centers</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left' href='staffBooking?action=list'><i class='bi bi-clipboard-check'></i> Confirm/Decline Booking</a></li>";
                            } else if (staffInfo.getType().equalsIgnoreCase("Senior Management")) {
                                navigation += "<li class='nav-item'> <a class='nav-link nav-left ' href='staffReport?action=list'><i class='bi bi-pie-chart-fill'></i> Analytic / Report</a></li>"
                                        + "<li class='nav-item'> <a class='nav-link nav-left active' href='staffAccount?action=list'><i class='bi bi-people-fill'></i> Account Management</a></li>";
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
                        Account Management
                    </div>

                    <div class="row">
                        <div class="container overflow-auto" id="content-container">
                            <h3>Customer</h3>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="d-flex flex-row" >
                                        <form action="staffAccount" method="get">
                                            <input type="hidden" name="action" value="create">
                                            <input type="hidden" name="type" value="customer">
                                            <input class="form-control btn" id="function-btn" type="submit" value="+ New Customer">
                                        </form>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="d-flex flex-row-reverse">
                                        <input class="form-control select-filter" type="text" id="searchCustInputId" placeholder="Customer ID...">
                                    </div>
                                </div>

                            </div>


                            <div class="col overflow-auto half-col-admin-account">
                                <cust:showCustomer customers="<%=customerList%>"/>
                            </div>
                            </br>
                            <h3>Staff</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="d-flex flex-row" >
                                        <form action="staffAccount" method="get">
                                            <input type="hidden" name="action" value="create">
                                            <input type="hidden" name="type" value="staff">
                                            <input class="form-control btn" id="function-btn" type="submit" value="+ New Staff">
                                        </form>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="d-flex flex-row-reverse">
                                        <input class="form-control select-filter" type="text" id="searchStaffInputId" placeholder="Staff ID...">
                                    </div>
                                </div>
                            </div>
                            <div class="col overflow-auto half-col-admin-account">
                                <staff:showStaff staff="<%=staffList%>"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            function confirmDeleteStaffAlert(id) {
                if (confirm('About to delete a staff with staff id: ' + id + ', are you sure?')) {
                    return true;
                } else {
                    return false;
                }
            }
            function confirmDeleteCustAlert(id) {
                if (confirm('About to delete a customer with customer id: ' + id + ', are you sure?')) {
                    return true;
                } else {
                    return false;
                }
            }

            var $custRows = $('#customerTable tr');
            $('#searchCustInputId').on('input', function () {
                var customerIdInput = $.trim($('#searchCustInputId').val()).replace(/ +/g, ' ').toLowerCase();


                $custRows.show().filter(function () {
                    var customerId = $(this).find('td:nth-child(1)').text().replace(/\s+/g, ' ').toLowerCase();

                    return !~customerId.indexOf(customerIdInput);
                }).hide();
            });

            var $staffRows = $('#staffTable tr');
            $('#searchStaffInputId').on('input', function () {
                var staffIdInput = $.trim($('#searchStaffInputId').val()).replace(/ +/g, ' ').toLowerCase();


                $staffRows.show().filter(function () {
                    var staffId = $(this).find('td:nth-child(1)').text().replace(/\s+/g, ' ').toLowerCase();

                    return !~staffId.indexOf(staffIdInput);
                }).hide();
            });
        </script>
    </body>
</html>
