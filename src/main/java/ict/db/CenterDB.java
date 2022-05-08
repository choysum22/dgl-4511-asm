package ict.db;

import ict.bean.CenterBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CenterDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public CenterDB(String dbUrl, String dbUser, String dbPassword) {
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

    public CenterBean queryByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        CenterBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM center WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                b = new CenterBean();
                b.setId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setLocation(rs.getString(3));
                b.setImg(rs.getString(4));
                b.setDesc(rs.getString(5));
                b.setFee(rs.getString(6));
                b.setTel(rs.getString(7));
                b.setStatus(rs.getString(8));
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

    public ArrayList<CenterBean> queryByName(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<CenterBean> bList = new ArrayList();
        CenterBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM center WHERE name=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, name);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new CenterBean();
                b.setId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setLocation(rs.getString(3));
                b.setImg(rs.getString(4));
                b.setDesc(rs.getString(5));
                b.setFee(rs.getString(6));
                b.setTel(rs.getString(7));
                b.setStatus(rs.getString(8));
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

    public ArrayList<CenterBean> query() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<CenterBean> bList = new ArrayList();
        CenterBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM center Order by id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new CenterBean();
                b.setId(rs.getString(1));
                b.setName(rs.getString(2));
                b.setLocation(rs.getString(3));
                b.setImg(rs.getString(4));
                b.setDesc(rs.getString(5));
                b.setFee(rs.getString(6));
                b.setTel(rs.getString(7));
                b.setStatus(rs.getString(8));
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

    public boolean createCenter(String name, String address, String img, String desc, String fee, String tel, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO center "
                    + "(name,address,img,description,fee,tel,status) VALUES (?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, name);
            pStmnt.setString(2, address);
            pStmnt.setString(3, img);
            pStmnt.setString(4, desc);
            pStmnt.setString(5, fee);
            pStmnt.setString(6, tel);
            pStmnt.setString(7, status);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                // DEBUG MESSAGE
                System.out.println("center created");
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

    public boolean updateRow(String id, String name, String address, String img, String desc, String fee, String tel, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE center SET "
                    + "name=?, address=?, img=?, description=?, fee=?, tel=?, status=? WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, name);
            pStmnt.setString(2, address);
            pStmnt.setString(3, img);
            pStmnt.setString(4, desc);
            pStmnt.setString(5, fee);
            pStmnt.setString(6, tel);
            pStmnt.setString(7, status);
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

    public boolean updateStatus(String id, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE center SET status=? WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, status);
            pStmnt.setInt(2, Integer.parseInt(id));
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
            String preQueryStatement = "DELETE FROM center WHERE id=?";
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

}
