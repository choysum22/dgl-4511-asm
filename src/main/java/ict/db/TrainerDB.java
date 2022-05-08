package ict.db;

import ict.bean.TrainerBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TrainerDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public TrainerDB(String dbUrl, String dbUser, String dbPassword) {
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

    public TrainerBean queryByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        TrainerBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM trainer_view WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(id));
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                b = new TrainerBean();
                b.setId(rs.getString(1));
                b.setFname(rs.getString(2));
                b.setLname(rs.getString(3));
                b.setImg(rs.getString(4));
                b.setDesc(rs.getString(5));
                b.setTel(rs.getString(6));
                b.setStatus(rs.getString(7));
                b.setFee(rs.getString(8));
                b.setCenter(rs.getString(9));
                b.setCenterName(rs.getString(10));
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

    public ArrayList<TrainerBean> queryByName(String username, String password) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<TrainerBean> bList = new ArrayList();
        TrainerBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM trainer_view WHERE name=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, username);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new TrainerBean();
                b.setId(rs.getString(1));
                b.setFname(rs.getString(2));
                b.setLname(rs.getString(3));
                b.setImg(rs.getString(4));
                b.setDesc(rs.getString(5));
                b.setTel(rs.getString(6));
                b.setStatus(rs.getString(7));
                b.setFee(rs.getString(8));
                b.setCenter(rs.getString(9));
                b.setCenterName(rs.getString(10));
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

    public ArrayList<TrainerBean> query() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<TrainerBean> bList = new ArrayList();
        TrainerBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM trainer_view ORDER BY center_name, id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new TrainerBean();
                b.setId(rs.getString(1));
                b.setFname(rs.getString(2));
                b.setLname(rs.getString(3));
                b.setImg(rs.getString(4));
                b.setDesc(rs.getString(5));
                b.setTel(rs.getString(6));
                b.setStatus(rs.getString(7));
                b.setFee(rs.getString(8));
                b.setCenter(rs.getString(9));
                b.setCenterName(rs.getString(10));
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

    public boolean createTrainer(String id, String img, String desc, String status, String fee) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO personal_trainer "
                    + "(id,img,description,status,fee) VALUES (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(id));
            pStmnt.setString(2, img);
            pStmnt.setString(3, desc);
            pStmnt.setString(4, status);
            pStmnt.setString(5, fee);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                // DEBUG MESSAGE
                System.out.println("trainer created");
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
            String preQueryStatement = "UPDATE personal_trainer SET status=? WHERE id=?";
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

    public boolean updateTrainer(String id, String img, String desc, String status, String fee) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE personal_trainer SET img=?, description=?, "
                    + "status=?, fee=? WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, img);
            pStmnt.setString(2, desc);
            pStmnt.setString(3, status);
            pStmnt.setString(4, fee);
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

    public boolean deleteRowWithTrainerId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM personal_trainer WHERE id=?";
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
