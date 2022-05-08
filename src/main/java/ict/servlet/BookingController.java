package ict.servlet;

import ict.bean.BookingCenterBean;
import ict.bean.BookingTrainerBean;
import ict.bean.CenterBean;
import ict.bean.CustomerBean;
import ict.bean.TempBookingBean;
import ict.bean.TrainerBean;
import ict.db.BookingDB;
import ict.db.CenterDB;
import ict.db.TimeslotDB;
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
import javax.servlet.http.HttpSession;

@WebServlet(name = "BookingController", urlPatterns = {"/booking"})
public class BookingController extends HttpServlet {

    private ArrayList<BookingCenterBean> bclist;
    private ArrayList<BookingTrainerBean> btlist;
    private ArrayList<TempBookingBean> tempList;
    private ArrayList<TrainerBean> trainerlist;
    private ArrayList<CenterBean> centerlist;
    private BookingCenterBean cBean;
    private BookingTrainerBean tBean;
    private BookingDB bookingDB;
    private CenterDB centerDB;
    private TimeslotDB timeslotDB;
    private TrainerDB trainerDB;
    private TempBookingBean tempBean;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);
        centerDB = new CenterDB(dbUrl, dbUser, dbPassword);
        timeslotDB = new TimeslotDB(dbUrl, dbUser, dbPassword);
        trainerDB = new TrainerDB(dbUrl, dbUser, dbPassword);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if action equals to list
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            doListAllBooking(request, response);
        } else if ("book".equalsIgnoreCase(action)) {
            doBooking(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
            doUpdateBooking(request, response);
        } else if ("add".equalsIgnoreCase(action)) {
            doAddBooking(request, response);
        } else if ("create".equalsIgnoreCase(action)) {
            doCreateBooking(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            doEditBooking(request, response);
        } else if ("cancel".equalsIgnoreCase(action)) {
            doCancelBooking(request, response);
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

        HttpSession session = request.getSession(false);
        String custId = ((CustomerBean) session.getAttribute("customerInfo")).getId();

        bclist = bookingDB.queryCenterByCustId(custId);
        btlist = bookingDB.queryTrainerByCustId(custId);
        request.setAttribute("bookingCenterList", bclist);
        request.setAttribute("bookingTrainerList", btlist);

        targetUrl = "booking.jsp";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        trainerlist = trainerDB.query();
        centerlist = centerDB.query();

        HttpSession session = request.getSession(true);
        tempList = new ArrayList();
        session.setAttribute("tempList", tempList);

        request.setAttribute("trainerBeanList", trainerlist);
        request.setAttribute("centerBeanList", centerlist);
        request.setAttribute("timeslotBeanList", timeslotDB.query());

        targetUrl = "addBooking.jsp?";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doAddBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String selected = "";

        HttpSession session = request.getSession(false);
        tempList = (ArrayList<TempBookingBean>) session.getAttribute("tempList");

        String id = request.getParameter("uid");
        String date = request.getParameter("date");
        String timeslot = request.getParameter("timeslot");
        String type = request.getParameter("type");

        if (type.equals("trainer")) {
            selected = request.getParameter("trainer");

        } else if (type.equals("center")) {
            selected = request.getParameter("center");
        }

        TempBookingBean bean = new TempBookingBean(id, date, timeslot, type, selected);
        tempList.add(bean);
        session.setAttribute("tempList", tempList);

        request.setAttribute("trainerBeanList", trainerDB.query());
        request.setAttribute("centerBeanList", centerDB.query());
        request.setAttribute("timeslotBeanList", timeslotDB.query());

        targetUrl = "addBooking.jsp?";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doCreateBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        HttpSession session = request.getSession(false);
        tempList = (ArrayList<TempBookingBean>) session.getAttribute("tempList");

        for (TempBookingBean bean : tempList) {
            if (bean.getType().equals("trainer")) {
                bookingDB.insertTrainerRow(bean.getId(), bean.getSelectedId(), bean.getTimeslot(), bean.getDate());

            } else if (bean.getType().equals("center")) {
                bookingDB.insertCenterRow(bean.getId(), bean.getSelectedId(), bean.getTimeslot(), bean.getDate());
            }
        }

        trainerlist = trainerDB.query();
        centerlist = centerDB.query();

        session.removeAttribute("tempList");

        targetUrl = "booking?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doUpdateBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        request.setAttribute("centerBeanList", centerDB.query());
        request.setAttribute("timeslotBeanList", timeslotDB.query());

        if ("trainer".equalsIgnoreCase(type)) {
            tBean = bookingDB.queryTrainerByID(id);
            request.setAttribute("type", "trainer");
            request.setAttribute("trainer", tBean);
        } else if ("center".equalsIgnoreCase(type)) {
            cBean = bookingDB.queryCenterByID(id);
            request.setAttribute("type", "center");
            request.setAttribute("center", cBean);
        }

        targetUrl = "updateBooking.jsp?id=" + id + "&type=" + type;

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doEditBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl = "";
        String id = request.getParameter("id");

        String date = request.getParameter("date");
        String timeslot = request.getParameter("timeslot");
        String type = request.getParameter("type");

        if (type.equals("trainer")) {
            bookingDB.updateTrainerRow(id, timeslot, date);

        } else if (type.equals("center")) {
            String center = request.getParameter("center");
            bookingDB.updateCenterRow(id, center, timeslot, date);
        }
        targetUrl = "booking?action=list&id=" + request.getParameter("uid");

        response.sendRedirect(targetUrl);
    }

    private void doCancelBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String uid = request.getParameter("uid");

        String targetUrl;
        bookingDB.deleteRow(type, id);

        targetUrl = "booking?action=list&id=" + uid;

        response.sendRedirect(targetUrl);
    }

}
