package ict.tag;
// import required lib

import ict.bean.CustomerBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CustomerAllTag extends SimpleTagSupport {

    private ArrayList<CustomerBean> customers = new ArrayList<CustomerBean>();

    @Override
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            String table = "<table class='table table-striped table-sm' id='customer' >"
                    + "<thead class='thead' style='position: sticky; top: 0'><tr><th>ID</th><th>Username</th><th>Password</th>"
                    + "<th>Name</th><th>Phone</th><th>Edit</th><th>Delete</th>"
                    + "<thead></tr><tbody class='tbody' id='customerTable'>";
            for (CustomerBean s : customers) {
                table += "<tr>"
                        + "<td>" + s.getId() + "</td>"
                        + "<td>" + s.getUsername() + "</td>"
                        + "<td>" + s.getPassword() + "</td>"
                        + "<td>" + s.getFname() + " " + s.getLname() + "</td>"
                        + "<td>" + s.getTel() + "</td>"
                        + "<td><a href='staffAccount?action=edit&type=customer&id=" + s.getId() + "'>Edit</a></td>"
                        + "<td><a class='delete-text' href='staffAccount?action=delete&type=customer&id=" + s.getId() + "' "
                        + "onclick='return confirmDeleteCustAlert(" + s.getId() + ")'>Delete</a></td>"
                        + "</tr>";
            }
            out.print(table + "</tbody></table>");
        } catch (IOException e) {
            System.out.println("Error getting trainer: " + e);
        }
    }

    public ArrayList getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList customers) {
        this.customers = customers;
    }
}
