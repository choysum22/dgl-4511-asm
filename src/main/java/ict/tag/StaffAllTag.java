package ict.tag;
// import required lib

import ict.bean.StaffBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class StaffAllTag extends SimpleTagSupport {

    private ArrayList<StaffBean> staff = new ArrayList<StaffBean>();

    @Override
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            String table = "<table class='table table-striped table-sm' id='staff'>"
                    + "<thead class='thead' style='position: sticky; top: 0'><tr><th>ID</th><th>Username</th><th>Password</th>"
                    + "<th>Name</th><th>Phone</th><th>Type</th><th>Center ID</th><th>Center Name</th><th>Edit</th><th>Delete</th>"
                    + "<thead></tr><tbody class='tbody' id='staffTable'>";
            for (StaffBean s : staff) {
                table += "<tr>"
                        + "<td>" + s.getId() + "</td>"
                        + "<td>" + s.getUsername() + "</td>"
                        + "<td>" + s.getPassword() + "</td>"
                        + "<td>" + s.getFname() + " " + s.getLname() + "</td>"
                        + "<td>" + s.getTel() + "</td>"
                        + "<td>" + s.getType() + "</td>"
                        + "<td>" + s.getCenter() + "</td>"
                        + "<td>" + s.getCenterName() + "</td>"
                        + "<td><a href='staffAccount?action=edit&type=staff&id=" + s.getId() + "'>Edit</a></td>"
                        + "<td><a class='delete-text' href='staffAccount?action=delete&type=staff&id=" + s.getId() + "' "
                        + "onclick='return confirmDeleteStaffAlert(" + s.getId() + ")'>Delete</a></td>"
                        + "</tr>";
            }
            out.print(table + "</tbody></table>");
        } catch (IOException e) {
            System.out.println("Error getting staff: " + e);
        }
    }

    public ArrayList getStaff() {
        return staff;
    }

    public void setStaff(ArrayList staff) {
        this.staff = staff;
    }
}
