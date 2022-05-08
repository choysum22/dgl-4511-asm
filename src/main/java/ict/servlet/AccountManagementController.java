package ict.servlet;

import ict.bean.CenterBean;
import ict.bean.CustomerBean;
import ict.bean.StaffBean;
import ict.bean.StaffTypeBean;
import ict.bean.TrainerBean;
import ict.db.BookingDB;
import ict.db.CenterDB;
import ict.db.CustomerDB;
import ict.db.StaffDB;
import ict.db.StaffTypeDB;
import ict.db.TrainerDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AccountManagementController", urlPatterns = {"/staffAccount"})
public class AccountManagementController extends HttpServlet {

    private ArrayList<CustomerBean> customerList;
    private ArrayList<StaffBean> staffList;
    private ArrayList<CenterBean> centerList;
    private ArrayList<StaffTypeBean> staffTypeList;

    private CustomerDB customerDB;
    private StaffDB staffDB;
    private TrainerDB trainerDB;
    private CenterDB centerDB;
    private StaffTypeDB staffTypeDB;
    private BookingDB bookingDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        customerDB = new CustomerDB(dbUrl, dbUser, dbPassword);
        staffDB = new StaffDB(dbUrl, dbUser, dbPassword);
        trainerDB = new TrainerDB(dbUrl, dbUser, dbPassword);
        centerDB = new CenterDB(dbUrl, dbUser, dbPassword);
        staffTypeDB = new StaffTypeDB(dbUrl, dbUser, dbPassword);
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if action equals to list
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            doListAll(request, response);
        } else if ("create".equalsIgnoreCase(action)) {
            doCreateAccount(request, response);
        } else if ("addStaff".equalsIgnoreCase(action)) {
            doAddStaffAccount(request, response);
        } else if ("addCust".equalsIgnoreCase(action)) {
            doAddCustAccount(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            doEditAccount(request, response);
        } else if ("editStaff".equalsIgnoreCase(action)) {
            doEditStaffAccount(request, response);
        } else if ("editCust".equalsIgnoreCase(action)) {
            doEditCustAccount(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            doDeleteAccount(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void doListAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        customerList = customerDB.query();
        staffList = staffDB.query();

        request.setAttribute("customerList", customerList);
        request.setAttribute("staffList", staffList);

        targetUrl = "staffAccount.jsp";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doCreateAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl = "";
        String type = request.getParameter("type");

        if (type.equals("staff")) {
            targetUrl = "staffNewStaffAccount.jsp";

            staffList = staffDB.query();
            staffTypeList = staffTypeDB.query();

            request.setAttribute("staffList", staffList);
            request.setAttribute("staffTypeList", staffTypeList);

        } else if (type.equals("customer")) {

            targetUrl = "staffNewCustAccount.jsp";
        }

        centerList = centerDB.query();
        request.setAttribute("centerList", centerList);

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doAddStaffAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        StaffBean staff = null;

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String type = request.getParameter("type");
        String tel = request.getParameter("tel");
        String center = request.getParameter("center");

        if (type.equals("3")) {
            String img = "img/" + request.getParameter("temp_img");
            String desc = request.getParameter("desc");
            String status = request.getParameter("status");
            String fee = request.getParameter("fee");

            staff = staffDB.createTrainerAccount(username, password, fname, lname, tel, type, center);
            trainerDB.createTrainer(staff.getId(), img, desc, status, fee);
        } else {
            staffDB.createAccount(username, password, fname, lname, tel, type, center);
        }

        targetUrl = "staffAccount?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doAddCustAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String tel = request.getParameter("tel");

        customerDB.register(username, password, fname, lname, tel);

        targetUrl = "staffAccount?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doEditAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl = "";
        StaffBean staff = null;
        TrainerBean trainer = null;
        String type = request.getParameter("type");
        String id = request.getParameter("id");

        if (type.equals("staff")) {
            centerList = centerDB.query();
            staffTypeList = staffTypeDB.query();
            staff = staffDB.queryByID(id);
            request.setAttribute("staff", staff);
            if (staff.getType().equals("Personal Trainer")) {
                trainer = trainerDB.queryByID(id);
                request.setAttribute("trainer", trainer);
            }
            request.setAttribute("staffTypeList", staffTypeList);
            request.setAttribute("centerList", centerList);

            targetUrl = "staffEditStaffAccount.jsp";

        } else if (type.equals("customer")) {
            request.setAttribute("customer", customerDB.queryByID(id));

            targetUrl = "staffEditCustAccount.jsp";

        }

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doEditStaffAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String type = request.getParameter("type");
        String tel = request.getParameter("tel");
        String center = request.getParameter("center");
        if (type.equals("3")) {
            String img = "img/" + request.getParameter("temp_img");
            String desc = request.getParameter("desc");
            String status = request.getParameter("status");
            String fee = request.getParameter("fee");
            trainerDB.updateTrainer(id, img, desc, status, fee);
        }

        staffDB.updateRow(id, username, password, fname, lname, tel, type, center);

        targetUrl = "staffAccount?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doEditCustAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String tel = request.getParameter("tel");

        customerDB.updateRow(id, username, password, fname, lname, tel);

        targetUrl = "staffAccount?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doDeleteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");
        String type = request.getParameter("type");

        if (type.equalsIgnoreCase("staff")) {
            bookingDB.deleteRowWithTrainerId(id);
            trainerDB.deleteRowWithTrainerId(id);
            staffDB.deleteRowWithId(id);
        } else if (type.equalsIgnoreCase("customer")) {
            bookingDB.deleteCenterRowWithCustId(id);
            bookingDB.deleteTrainerRowWithCustId(id);
            customerDB.deleteRowWithCustId(id);
        }

        targetUrl = "staffAccount?action=list";

        response.sendRedirect(targetUrl);
    }

}
