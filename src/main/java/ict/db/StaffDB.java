package ict.db;

import ict.bean.StaffBean;
import ict.bean.TrainerBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public StaffDB(String dbUrl, String dbUser, String dbPassword) {
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
            String preQueryStatement = "SELECT * FROM staff WHERE username =  ? and  password =  ?";
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

    public boolean createAccount(String username, String pwd, String fname, String lname, String tel, String type, String center) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO staff "
                    + "(username,password,fname,lname,tel,type,center) VALUES (?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, username);
            pStmnt.setString(2, pwd);
            pStmnt.setString(3, fname);
            pStmnt.setString(4, lname);
            pStmnt.setString(5, tel);
            pStmnt.setInt(6, Integer.parseInt(type));
            pStmnt.setInt(7, Integer.parseInt(center));
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                // DEBUG MESSAGE
                System.out.println("account created");
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

    public StaffBean createTrainerAccount(String username, String pwd, String fname, String lname, String tel, String type, String center) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        StaffBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO staff "
                    + "(username,password,fname,lname,tel,type,center) VALUES (?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, username);
            pStmnt.setString(2, pwd);
            pStmnt.setString(3, fname);
            pStmnt.setString(4, lname);
            pStmnt.setString(5, tel);
            pStmnt.setInt(6, Integer.parseInt(type));
            pStmnt.setInt(7, Integer.parseInt(center));
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                b = queryByUsernameAndPassword(username, pwd);
                // DEBUG MESSAGE
                System.out.println("account created");
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

        return b;
    }

    public StaffBean queryByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        StaffBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM staff_view WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                b = new StaffBean();
                b.setId(rs.getString(1));
                b.setUsername(rs.getString(2));
                b.setPassword(rs.getString(3));
                b.setFname(rs.getString(4));
                b.setLname(rs.getString(5));
                b.setTel(rs.getString(6));
                b.setType(rs.getString(7));
                b.setCenter(rs.getString(8));
                b.setCenterName(rs.getString(9));
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
        return b;
    }

    public StaffBean queryByUsernameAndPassword(String username, String password) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        StaffBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM staff_view WHERE username=? AND password=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, username);
            pStmnt.setString(2, password);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                b = new StaffBean();
                b.setId(rs.getString(1));
                b.setUsername(rs.getString(2));
                b.setPassword(rs.getString(3));
                b.setFname(rs.getString(4));
                b.setLname(rs.getString(5));
                b.setTel(rs.getString(6));
                b.setType(rs.getString(7));
                b.setCenter(rs.getString(8));
                b.setCenterName(rs.getString(9));
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
        return b;
    }

    public ArrayList<StaffBean> query() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<StaffBean> bList = new ArrayList();
        StaffBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM staff_view Order by id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new StaffBean();
                b.setId(rs.getString(1));
                b.setUsername(rs.getString(2));
                b.setPassword(rs.getString(3));
                b.setFname(rs.getString(4));
                b.setLname(rs.getString(5));
                b.setTel(rs.getString(6));
                b.setType(rs.getString(7));
                b.setCenter(rs.getString(8));
                b.setCenterName(rs.getString(9));
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

    public ArrayList<StaffBean> queryByName(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<StaffBean> bList = new ArrayList();
        StaffBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM staff_view WHERE fname LIKE ('%' + ? + '%') OR lname LIKE ('%' + ? + '%') ORDER BY id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, name);
            pStmnt.setString(2, name);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new StaffBean();
                b.setId(rs.getString(1));
                b.setUsername(rs.getString(2));
                b.setPassword(rs.getString(3));
                b.setFname(rs.getString(4));
                b.setLname(rs.getString(5));
                b.setTel(rs.getString(6));
                b.setType(rs.getString(7));
                b.setCenter(rs.getString(8));
                b.setCenterName(rs.getString(9));
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

    public boolean updateRow(String id, String username, String password, String fname, String lname, String tel, String type, String center) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE staff SET username=?, password=?, fname=?, lname=?, tel=?, type=?, center=? WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, username);
            pStmnt.setString(2, password);
            pStmnt.setString(3, fname);
            pStmnt.setString(4, lname);
            pStmnt.setString(5, tel);
            pStmnt.setString(6, type);
            pStmnt.setString(7, center);
            pStmnt.setInt(8, Integer.parseInt(id));
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

    public boolean updateTrainer(String id, String fname, String lname, String tel, String center) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE staff SET fname=?, lname=?, tel=?, center=? WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, fname);
            pStmnt.setString(2, lname);
            pStmnt.setString(3, tel);
            pStmnt.setString(4, center);
            pStmnt.setInt(5, Integer.parseInt(id));
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

    public boolean deleteRowWithId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM staff WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(id));
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
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

}
