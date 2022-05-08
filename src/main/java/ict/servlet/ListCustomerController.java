package ict.servlet;

import ict.bean.CustomerBean;
import ict.db.CustomerDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListCustomerController", urlPatterns = {"/customer"})
public class ListCustomerController extends HttpServlet {

    private ArrayList<CustomerBean> list;
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
        doListAllTrainer(request, response);
    }

    private void doListAllTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl;
        list = db.query();
        request.setAttribute("customerList", list);

        targetUrl = "customer.jsp";

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetUrl);
        rd.forward(request, response);
    }

}
