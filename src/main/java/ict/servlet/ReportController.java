package ict.servlet;

import ict.bean.BookingCenterBean;
import ict.bean.BookingTrainerBean;
import ict.bean.IncomeCenterBean;
import ict.bean.IncomeTrainerBean;
import ict.bean.ReportCenterBean;
import ict.bean.ReportTrainerBean;
import ict.db.BookingDB;
import ict.db.ReportDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReportController", urlPatterns = {"/staffReport"})
public class ReportController extends HttpServlet {

    private ArrayList<BookingCenterBean> bclist;
    private ArrayList<BookingTrainerBean> btlist;
    private ArrayList<ReportTrainerBean> trainerRateList;
    private ArrayList<ReportCenterBean> centerRateList;
    private ArrayList<IncomeTrainerBean> trainerIncomeList;
    private ArrayList<IncomeCenterBean> centerIncomeList;
    private BookingDB bookingDB;
    private ReportDB reportDB;
    private Date date;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);
        reportDB = new ReportDB(dbUrl, dbUser, dbPassword);
        date = new Date();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if action equals to list
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            doListAll(request, response);
        } else if ("getTrainerRate".equalsIgnoreCase(action)) {
            doGetTrainerRate(request, response);
        } else if ("getCenterRate".equalsIgnoreCase(action)) {
            doGetCenterRate(request, response);
        } else if ("getTrainerIncome".equalsIgnoreCase(action)) {
            doGetTrainerIncome(request, response);
        } else if ("getCenterIncome".equalsIgnoreCase(action)) {
            doGetCenterIncome(request, response);
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
        bclist = bookingDB.queryCenter();
        btlist = bookingDB.queryTrainer();
        trainerRateList = reportDB.queryTrainerRate("Month", getMonth(date));
        centerRateList = reportDB.queryCenterRate("Month", getMonth(date));
        trainerIncomeList = reportDB.queryTrainerIncome("Month", getMonth(date));
        centerIncomeList = reportDB.queryCenterIncome("Month", getMonth(date));

        request.setAttribute("currYear", getYear(date));
        request.setAttribute("bookingCenterList", bclist);
        request.setAttribute("bookingTrainerList", btlist);
        request.setAttribute("reportTrainerList", trainerRateList);
        request.setAttribute("reportCenterList", centerRateList);
        request.setAttribute("reportTrainerIncomeList", trainerIncomeList);
        request.setAttribute("reportCenterIncomeList", centerIncomeList);

        targetUrl = "staffReport.jsp";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doGetTrainerRate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String type = request.getParameter("type");
        String month = request.getParameter("month");
        String year = request.getParameter("year");

        if (type.equalsIgnoreCase("month")) {
            trainerRateList = reportDB.queryTrainerRate(type, month);
        } else if (type.equalsIgnoreCase("year")) {
            trainerRateList = reportDB.queryTrainerRate(type, year);
        }
        bclist = bookingDB.queryCenter();
        btlist = bookingDB.queryTrainer();

        centerRateList = reportDB.queryCenterRate("Month", getMonth(date));
        trainerIncomeList = reportDB.queryTrainerIncome("Month", getMonth(date));
        centerIncomeList = reportDB.queryCenterIncome("Month", getMonth(date));

        request.setAttribute("currYear", getYear(date));
        request.setAttribute("bookingCenterList", bclist);
        request.setAttribute("bookingTrainerList", btlist);
        request.setAttribute("reportTrainerList", trainerRateList);
        request.setAttribute("reportCenterList", centerRateList);
        request.setAttribute("reportTrainerIncomeList", trainerIncomeList);
        request.setAttribute("reportCenterIncomeList", centerIncomeList);

        targetUrl = "staffReport.jsp?page=rate";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doGetCenterRate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String type = request.getParameter("type");
        String month = request.getParameter("month");
        String year = request.getParameter("year");

        if (type.equalsIgnoreCase("month")) {
            centerRateList = reportDB.queryCenterRate(type, month);
        } else if (type.equalsIgnoreCase("year")) {
            centerRateList = reportDB.queryCenterRate(type, year);
        }
        bclist = bookingDB.queryCenter();
        btlist = bookingDB.queryTrainer();

        trainerRateList = reportDB.queryTrainerRate("Month", getMonth(date));
        trainerIncomeList = reportDB.queryTrainerIncome("Month", getMonth(date));
        centerIncomeList = reportDB.queryCenterIncome("Month", getMonth(date));

        request.setAttribute("currYear", getYear(date));
        request.setAttribute("bookingCenterList", bclist);
        request.setAttribute("bookingTrainerList", btlist);
        request.setAttribute("reportCenterList", centerRateList);
        request.setAttribute("reportTrainerList", trainerRateList);
        request.setAttribute("reportTrainerIncomeList", trainerIncomeList);
        request.setAttribute("reportCenterIncomeList", centerIncomeList);

        targetUrl = "staffReport.jsp?page=rate";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doGetTrainerIncome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String type = request.getParameter("type");
        String month = request.getParameter("month");
        String year = request.getParameter("year");

        if (type.equalsIgnoreCase("month")) {
            trainerIncomeList = reportDB.queryTrainerIncome(type, month);
        } else if (type.equalsIgnoreCase("year")) {
            trainerIncomeList = reportDB.queryTrainerIncome(type, year);
        }
        bclist = bookingDB.queryCenter();
        btlist = bookingDB.queryTrainer();

        centerIncomeList = reportDB.queryCenterIncome("Month", getMonth(date));
        trainerRateList = reportDB.queryTrainerRate("Month", getMonth(date));
        centerRateList = reportDB.queryCenterRate("Month", getMonth(date));

        request.setAttribute("currYear", getYear(date));
        request.setAttribute("bookingCenterList", bclist);
        request.setAttribute("bookingTrainerList", btlist);
        request.setAttribute("reportTrainerList", trainerRateList);
        request.setAttribute("reportCenterList", centerRateList);
        request.setAttribute("reportTrainerIncomeList", trainerIncomeList);
        request.setAttribute("reportCenterIncomeList", centerIncomeList);

        targetUrl = "staffReport.jsp?page=income";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doGetCenterIncome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String type = request.getParameter("type");
        String month = request.getParameter("month");
        String year = request.getParameter("year");

        if (type.equalsIgnoreCase("month")) {
            centerIncomeList = reportDB.queryCenterIncome(type, month);
        } else if (type.equalsIgnoreCase("year")) {
            centerIncomeList = reportDB.queryCenterIncome(type, year);
        }
        bclist = bookingDB.queryCenter();
        btlist = bookingDB.queryTrainer();

        trainerIncomeList = reportDB.queryTrainerIncome("Month", getMonth(date));
        trainerRateList = reportDB.queryTrainerRate("Month", getMonth(date));
        centerRateList = reportDB.queryCenterRate("Month", getMonth(date));

        request.setAttribute("currYear", getYear(date));
        request.setAttribute("bookingCenterList", bclist);
        request.setAttribute("bookingTrainerList", btlist);
        request.setAttribute("reportCenterList", centerRateList);
        request.setAttribute("reportTrainerList", trainerRateList);
        request.setAttribute("reportTrainerIncomeList", trainerIncomeList);
        request.setAttribute("reportCenterIncomeList", centerIncomeList);

        targetUrl = "staffReport.jsp?page=income";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private String getMonth(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getMonthValue() + "";
    }

    private String getYear(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear() + "";
    }

}
