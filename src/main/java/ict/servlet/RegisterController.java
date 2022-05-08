package ict.servlet;

import ict.db.CustomerDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private CustomerDB db;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new CustomerDB(dbUrl, dbUser, dbPassword);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRegisterAccount(request, response);
    }

    private void doRegisterAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String tel = request.getParameter("tel");
        boolean isValid = db.isValidUser(username, password);
        String targetUrl;

        // craete new account if account does not exist
        if (!isValid) {
            db.register(username, password, fname, lname, tel);
            targetUrl = "login.jsp";
            request.setAttribute("registerResult", true);

        } else {
            targetUrl = "login.jsp";
        }
        response.sendRedirect(targetUrl);
    }

}
