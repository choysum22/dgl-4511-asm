package ict.servlet;

import ict.bean.StaffBean;
import ict.bean.TrainerBean;
import ict.db.BookingDB;
import ict.db.CenterDB;
import ict.db.StaffDB;
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

@WebServlet(name = "StaffTrainerController", urlPatterns = {"/staffTrainer"})
public class StaffTrainerController extends HttpServlet {

    private StaffDB staffDB;
    private TrainerDB trainerDB;
    private CenterDB centerDB;
    private TimeslotDB timeslotDB;
    private BookingDB bookingDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        trainerDB = new TrainerDB(dbUrl, dbUser, dbPassword);
        centerDB = new CenterDB(dbUrl, dbUser, dbPassword);
        timeslotDB = new TimeslotDB(dbUrl, dbUser, dbPassword);
        staffDB = new StaffDB(dbUrl, dbUser, dbPassword);
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if action equals to list
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            doListAllTrainer(request, response);
        } else if ("enable".equalsIgnoreCase(action)) {
            doEnableTrainer(request, response);
        } else if ("disable".equalsIgnoreCase(action)) {
            doDisableTrainer(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            doEditTrainer(request, response);
        } else if ("updateEdit".equalsIgnoreCase(action)) {
            doUpdateEditTrainer(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            doDeleteTrainer(request, response);
        } else if ("createTrainer".equalsIgnoreCase(action)) {
            doCreateTrainer(request, response);
        } else if ("add".equalsIgnoreCase(action)) {
            doAddTrainer(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }

    }

    private void doListAllTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<TrainerBean> list = null;

        list = trainerDB.query();
        request.setAttribute("trainerList", list);

        RequestDispatcher rd = this.getServletContext()
                .getRequestDispatcher("/staffTrainer.jsp");
        rd.forward(request, response);
    }

    private void doDisableTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");

        trainerDB.updateStatus(id, "Unavailable");

        targetUrl = "staffTrainer?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doEnableTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");

        trainerDB.updateStatus(id, "Available");

        targetUrl = "staffTrainer?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doEditTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String id = request.getParameter("id");
        request.setAttribute("trainer", trainerDB.queryByID(id));
        request.setAttribute("centerBeanList", centerDB.query());
        request.setAttribute("timeslotBeanList", timeslotDB.query());

        targetUrl = "staffEditTrainer.jsp?";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doUpdateEditTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String id = request.getParameter("id");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String img = "img/" + request.getParameter("temp_img");
        String desc = request.getParameter("desc");
        String tel = request.getParameter("tel");
        String center = request.getParameter("center");
        String status = request.getParameter("status");
        String fee = request.getParameter("fee");

        staffDB.updateTrainer(id, fname, lname, tel, center);
        trainerDB.updateTrainer(id, img, desc, status, fee);

        targetUrl = "staffTrainer?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doDeleteTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String id = request.getParameter("id");

        bookingDB.deleteRowWithTrainerId(id);
        trainerDB.deleteRowWithTrainerId(id);
        staffDB.deleteRowWithId(id);

        targetUrl = "staffTrainer?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doCreateTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        request.setAttribute("centerBeanList", centerDB.query());

        targetUrl = "staffNewTrainer.jsp";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doAddTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        StaffBean b = null;

        String type = request.getParameter("type");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String img = "img/" + request.getParameter("temp_img");
        String desc = request.getParameter("desc");
        String tel = request.getParameter("tel");
        String center = request.getParameter("center");
        String status = request.getParameter("status");
        String fee = request.getParameter("fee");

        b = staffDB.createTrainerAccount(username, password, fname, lname, tel, type, center);
        trainerDB.createTrainer(b.getId(), img, desc, status, fee);

        targetUrl = "staffTrainer?action=list";

        response.sendRedirect(targetUrl);
    }
}
