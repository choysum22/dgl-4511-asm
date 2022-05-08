package ict.servlet;

import ict.bean.BookingCenterBean;
import ict.bean.BookingTrainerBean;
import ict.db.BookingDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "StaffBookingController", urlPatterns = {"/staffBooking"})
public class StaffBookingController extends HttpServlet {

    private ArrayList<BookingCenterBean> bclist;
    private ArrayList<BookingTrainerBean> btlist;
    private BookingDB bookingDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if action equals to list
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            doListAllBooking(request, response);
        }
        if ("confirm".equalsIgnoreCase(action)) {
            doConfirmBooking(request, response);
        }
        if ("decline".equalsIgnoreCase(action)) {
            doDeclineBooking(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void doListAllBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        bclist = bookingDB.queryCenter();
        btlist = bookingDB.queryTrainer();
        request.setAttribute("bookingCenterList", bclist);
        request.setAttribute("bookingTrainerList", btlist);

        targetUrl = "staffBooking.jsp";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doDeclineBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");
        String type = request.getParameter("type");

        if (type.equals("center")) {
            bookingDB.updateCenterStatus(id, "Decline");
        } else if (type.equals("trainer")) {
            bookingDB.updateTrainerStatus(id, "Decline");
        }

        targetUrl = "staffBooking?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doConfirmBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");
        String type = request.getParameter("type");

        if (type.equals("center")) {
            bookingDB.updateCenterStatus(id, "Confirm");
        } else if (type.equals("trainer")) {
            bookingDB.updateTrainerStatus(id, "Confirm");
        }
        targetUrl = "staffBooking?action=list";

        response.sendRedirect(targetUrl);
    }

}
