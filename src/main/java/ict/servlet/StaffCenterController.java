package ict.servlet;

import ict.bean.CenterBean;
import ict.db.BookingDB;
import ict.db.CenterDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "StaffCenterController", urlPatterns = {"/staffCenter"})
public class StaffCenterController extends HttpServlet {

    private CenterDB centerDB;
    private BookingDB bookingDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        centerDB = new CenterDB(dbUrl, dbUser, dbPassword);
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if action equals to list
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            doListAllCenter(request, response);
        } else if ("enable".equalsIgnoreCase(action)) {
            doEnableCenter(request, response);
        } else if ("disable".equalsIgnoreCase(action)) {
            doDisableCenter(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            doEditCenter(request, response);
        } else if ("updateEdit".equalsIgnoreCase(action)) {
            doUpdateEditCenter(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            doDeleteCenter(request, response);
        } else if ("createCenter".equalsIgnoreCase(action)) {
            doCreateCenter(request, response);
        } else if ("add".equalsIgnoreCase(action)) {
            doAddCenter(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }

    }

    private void doListAllCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<CenterBean> list = null;

        list = centerDB.query();
        request.setAttribute("centerList", list);

        RequestDispatcher rd = this.getServletContext()
                .getRequestDispatcher("/staffCenter.jsp");
        rd.forward(request, response);
    }

    private void doDisableCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");

        centerDB.updateStatus(id, "Unavailable");

        targetUrl = "staffCenter?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doEnableCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        String id = request.getParameter("id");

        centerDB.updateStatus(id, "Available");

        targetUrl = "staffCenter?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doEditCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String id = request.getParameter("id");
        request.setAttribute("center", centerDB.queryByID(id));

        targetUrl = "staffEditCenter.jsp?";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doUpdateEditCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String img = "img/" + request.getParameter("temp_img");
        String desc = request.getParameter("desc");
        String fee = request.getParameter("fee");
        String tel = request.getParameter("tel");
        String status = request.getParameter("status");

        centerDB.updateRow(id, name, address, img, desc, fee, tel, status);

        targetUrl = "staffCenter?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doDeleteCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        String id = request.getParameter("id");

        centerDB.deleteRowWithId(id);

        targetUrl = "staffCenter?action=list";

        response.sendRedirect(targetUrl);
    }

    private void doCreateCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;

        targetUrl = "staffNewCenter.jsp";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

    private void doAddCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        CenterBean b = null;

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String img = "img/" + request.getParameter("temp_img");
        String desc = request.getParameter("desc");
        String fee = request.getParameter("fee");
        String tel = request.getParameter("tel");
        String status = request.getParameter("status");

        centerDB.createCenter(name, address, img, desc, fee, tel, status);

        targetUrl = "staffCenter?action=list";

        response.sendRedirect(targetUrl);
    }
}
