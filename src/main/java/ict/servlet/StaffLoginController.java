package ict.servlet;

import ict.bean.StaffBean;
import ict.db.BookingDB;
import ict.db.StaffDB;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "StaffLoginController", urlPatterns = {"/staff"})
public class StaffLoginController extends HttpServlet {

    private StaffDB staffDB;
    private BookingDB bookingDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        staffDB = new StaffDB(dbUrl, dbUser, dbPassword);
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (!isAuthenticated(request)
                && !("authenticate".equals(action))) {
            doLogin(request, response);
            return;
        }
        if ("authenticate".equals(action)) {
            doAuthenticate(request, response);
        } else if ("logout".equals(action)) {
            doLogout(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isValid = staffDB.isValidUser(username, password);
        String targetUrl;
        if (isValid) {
            //obtain session from request
            HttpSession session = request.getSession(true);
            StaffBean bean = staffDB.queryByUsernameAndPassword(username, password);
            // store the userInfo to the session
            session.setAttribute("staffInfo", bean);

            int rows = bookingDB.queryTrainerByStatus(bean.getId());
            request.setAttribute("rows", rows);
            targetUrl = "staffHome.jsp";
        } else {
            targetUrl = "staffLogin.jsp";
            request.setAttribute("login", "failed");
        }
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        boolean result = false;
        HttpSession session = request.getSession();
        // get the UserInfo from session
        if (session.getAttribute("staffInfo") != null) {
            result = true;
        }
        return result;
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String targetUrl = "staffLogin.jsp";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // remove the attribute from session
            session.removeAttribute("staffInfo");
            // invalidate the session
            session.invalidate();
        }
        doLogin(request, response);
    }

}
