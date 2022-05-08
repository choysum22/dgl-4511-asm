package ict.db;

import ict.bean.CustomerBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public CustomerDB(String dbUrl, String dbUser, String dbPassword) {
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

    public boolean isValidUser(String username, String pwd) {
        PreparedStatement pStmnt = null;
        Connection cnnct = null;
        boolean isValid = false;

        try {
            //1. get Connection
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM customer WHERE username =  ? and  password =  ?";
            //2. get the prepare Statement
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            //3. update the placeholders with username and pwd
            pStmnt.setString(1, username);
            pStmnt.setString(2, pwd);
            //4. execute the query and assign to the result
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                isValid = true;

                // DEBUG MESSAGE
                System.out.println("login success");
            }
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isValid;

    }

    public boolean register(String username, String pwd, String fname, String lname, String tel) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO customer (username,password,fname,lname,tel) VALUES (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, username);
            pStmnt.setString(2, pwd);
            pStmnt.setString(3, fname);
            pStmnt.setString(4, lname);
            pStmnt.setString(5, tel);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                // DEBUG MESSAGE
                System.out.println("register success");
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

        return isSuccess;
    }

    public boolean updateRow(String id, String username, String password, String fname, String lname, String tel) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE customer SET username=?, password=?, fname=?, lname=?, tel=? WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, username);
            pStmnt.setString(2, password);
            pStmnt.setString(3, fname);
            pStmnt.setString(4, lname);
            pStmnt.setString(5, tel);
            pStmnt.setInt(6, Integer.parseInt(id));
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                // DEBUG MESSAGE
                System.out.println("update successful");
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

        return isSuccess;
    }

    public boolean deleteRowWithCustId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM customer WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(id));
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                // DEBUG MESSAGE
                System.out.println("delete successful");
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
        return isSuccess;
    }

    public CustomerBean queryByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        CustomerBean cb = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM customer WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                cb = new CustomerBean();
                cb.setId(rs.getString(1));
                cb.setUsername(rs.getString(2));
                cb.setPassword(rs.getString(3));
                cb.setFname(rs.getString(4));
                cb.setLname(rs.getString(5));
                cb.setTel(rs.getString(6));
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
        return cb;
    }

    public CustomerBean queryByUsernameAndPassword(String username, String password) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        CustomerBean cb = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM customer WHERE username=? AND password=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, username);
            pStmnt.setString(2, password);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                cb = new CustomerBean();
                cb.setId(rs.getString(1));
                cb.setUsername(rs.getString(2));
                cb.setPassword(rs.getString(3));
                cb.setFname(rs.getString(4));
                cb.setLname(rs.getString(5));
                cb.setTel(rs.getString(6));
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
        return cb;
    }

    public ArrayList<CustomerBean> query() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<CustomerBean> bList = new ArrayList();
        CustomerBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM customer Order by id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new CustomerBean();
                b.setId(rs.getString(1));
                b.setUsername(rs.getString(2));
                b.setPassword(rs.getString(3));
                b.setFname(rs.getString(4));
                b.setLname(rs.getString(5));
                b.setTel(rs.getString(6));
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

    public ArrayList<CustomerBean> queryByName(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<CustomerBean> bList = new ArrayList();
        CustomerBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM customer WHERE fname LIKE ('%' + ? + '%') OR lname LIKE ('%' + ? + '%') ORDER BY id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, name);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new CustomerBean();
                b.setId(rs.getString(1));
                b.setUsername(rs.getString(2));
                b.setPassword(rs.getString(3));
                b.setFname(rs.getString(4));
                b.setLname(rs.getString(5));
                b.setTel(rs.getString(6));
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
