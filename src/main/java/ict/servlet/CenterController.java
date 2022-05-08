package ict.servlet;

import ict.bean.CenterBean;
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

@WebServlet(name = "CenterController", urlPatterns = {"/center"})
public class CenterController extends HttpServlet {

    private CenterDB db;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new CenterDB(dbUrl, dbUser, dbPassword);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if action equals to list
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            ArrayList<CenterBean> list = null;

            list = db.query();
            request.setAttribute("centerList", list);

            RequestDispatcher rd = this.getServletContext()
                    .getRequestDispatcher("/center.jsp");
            rd.forward(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }

    }
}
