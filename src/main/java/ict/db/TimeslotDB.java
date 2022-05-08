package ict.db;

import ict.bean.TimeslotBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TimeslotDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public TimeslotDB(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public ArrayList<TimeslotBean> query() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<TimeslotBean> bList = new ArrayList();
        TimeslotBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM timeslot Order by id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new TimeslotBean();
                b.setId(rs.getString(1));
                b.setTimeslot(rs.getString(2));
                bList.add(b);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bList;
    }

}
