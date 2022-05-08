package ict.db;

import ict.bean.BookingCenterBean;
import ict.bean.BookingTrainerBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookingDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public BookingDB(String dbUrl, String dbUser, String dbPassword) {
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

    public BookingCenterBean queryCenterByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        BookingCenterBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM booking_center_view WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(id));
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                b = new BookingCenterBean();
                b.setId(rs.getString(1));
                b.setCustId(rs.getString(2));
                b.setCustName(rs.getString(3));
                b.setCenterId(rs.getString(4));
                b.setCenterName(rs.getString(5));
                b.setFee(rs.getString(6));
                b.setTimeslotId(rs.getString(7));
                b.setTimeslot(rs.getString(8));
                b.setDate(rs.getString(9));
                b.setStatus(rs.getString(10));
                b.setTimestamp(rs.getString(11));
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

    public ArrayList<BookingCenterBean> queryCenterByCustId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<BookingCenterBean> bList = new ArrayList();
        BookingCenterBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM booking_center_view WHERE customer_id=? ORDER BY status DESC, timestamp";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(id));
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new BookingCenterBean();
                b.setId(rs.getString(1));
                b.setCustId(rs.getString(2));
                b.setCustName(rs.getString(3));
                b.setCenterId(rs.getString(4));
                b.setCenterName(rs.getString(5));
                b.setFee(rs.getString(6));
                b.setTimeslotId(rs.getString(7));
                b.setTimeslot(rs.getString(8));
                b.setDate(rs.getString(9));
                b.setStatus(rs.getString(10));
                b.setTimestamp(rs.getString(11));
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

    public ArrayList<BookingCenterBean> queryCenter() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<BookingCenterBean> bList = new ArrayList();
        BookingCenterBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM booking_center_view Order by status DESC, timestamp ";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new BookingCenterBean();
                b.setId(rs.getString(1));
                b.setCustId(rs.getString(2));
                b.setCustName(rs.getString(3));
                b.setCenterId(rs.getString(4));
                b.setCenterName(rs.getString(5));
                b.setFee(rs.getString(6));
                b.setTimeslotId(rs.getString(7));
                b.setTimeslot(rs.getString(8));
                b.setDate(rs.getString(9));
                b.setStatus(rs.getString(10));
                b.setTimestamp(rs.getString(11));
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

    public BookingTrainerBean queryTrainerByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        BookingTrainerBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM booking_trainer_view WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                b = new BookingTrainerBean();
                b.setId(rs.getString(1));
                b.setCustId(rs.getString(2));
                b.setCustName(rs.getString(3));
                b.setTrainerId(rs.getString(4));
                b.setTrainerName(rs.getString(5));
                b.setCenterId(rs.getString(6));
                b.setCenterName(rs.getString(7));
                b.setFee(rs.getString(8));
                b.setTimeslotId(rs.getString(9));
                b.setTimeslot(rs.getString(10));
                b.setDate(rs.getString(11));
                b.setStatus(rs.getString(12));
                b.setTimestamp(rs.getString(13));
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

    public int queryTrainerByStatus(String trainerId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        int rows = 0;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM booking_trainer_view WHERE trainer_id=? AND status='Pending'";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, trainerId);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                rows += 1;
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
        return rows;
    }

    public ArrayList<BookingTrainerBean> queryTrainerByCustId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<BookingTrainerBean> bList = new ArrayList();
        BookingTrainerBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM booking_trainer_view WHERE customer_id=? ORDER BY status DESC, timestamp";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(id));
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new BookingTrainerBean();
                b.setId(rs.getString(1));
                b.setCustId(rs.getString(2));
                b.setCustName(rs.getString(3));
                b.setTrainerId(rs.getString(4));
                b.setTrainerName(rs.getString(5));
                b.setCenterId(rs.getString(6));
                b.setCenterName(rs.getString(7));
                b.setFee(rs.getString(8));
                b.setTimeslotId(rs.getString(9));
                b.setTimeslot(rs.getString(10));
                b.setDate(rs.getString(11));
                b.setStatus(rs.getString(12));
                b.setTimestamp(rs.getString(13));
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

    public ArrayList<BookingTrainerBean> queryTrainer() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<BookingTrainerBean> bList = new ArrayList();
        BookingTrainerBean b = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM booking_trainer_view Order by status DESC, timestamp ";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new BookingTrainerBean();
                b.setId(rs.getString(1));
                b.setCustId(rs.getString(2));
                b.setCustName(rs.getString(3));
                b.setTrainerId(rs.getString(4));
                b.setTrainerName(rs.getString(5));
                b.setCenterId(rs.getString(6));
                b.setCenterName(rs.getString(7));
                b.setFee(rs.getString(8));
                b.setTimeslotId(rs.getString(9));
                b.setTimeslot(rs.getString(10));
                b.setDate(rs.getString(11));
                b.setStatus(rs.getString(12));
                b.setTimestamp(rs.getString(13));
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

    public boolean updateCenterRow(String id, String centerId, String timeslotId, String date) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE booking_center SET center_id=?, timeslot=?, date=? WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, centerId);
            pStmnt.setString(2, timeslotId);
            pStmnt.setString(3, date);
            pStmnt.setInt(4, Integer.parseInt(id));
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

    public boolean updateTrainerRow(String id, String timeslotId, String date) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE booking_trainer SET timeslot=?, date=? WHERE id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, timeslotId);
            pStmnt.setString(2, date);
            pStmnt.setInt(3, Integer.parseInt(id));
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

    public boolean updateCenterStatus(String id, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE booking_center SET status=? WHERE id=?";
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

    public boolean updateTrainerStatus(String id, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE booking_trainer SET status=? WHERE id=?";
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

    public boolean insertCenterRow(String customerId, String centerId, String timeslotId, String date) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO booking_center "
                    + "(customer_id, center_id, timeslot, date) "
                    + "VALUES (?, ?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(customerId));
            pStmnt.setInt(2, Integer.parseInt(centerId));
            pStmnt.setInt(3, Integer.parseInt(timeslotId));
            pStmnt.setString(4, date);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                // DEBUG MESSAGE
                System.out.println("insert center booking successful");
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

    public boolean insertTrainerRow(String customerId, String trainerId, String timeslotId, String date) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO booking_trainer "
                    + "(customer_id, trainer_id, timeslot, date) "
                    + "VALUES (?, ?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(customerId));
            pStmnt.setInt(2, Integer.parseInt(trainerId));
            pStmnt.setInt(3, Integer.parseInt(timeslotId));
            pStmnt.setString(4, date);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                // DEBUG MESSAGE
                System.out.println("insert trainer booking successful");
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

    public boolean deleteRow(String type, String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "";
            if ("center".equalsIgnoreCase(type)) {
                preQueryStatement = "DELETE FROM booking_center WHERE id=?";
            } else if ("trainer".equalsIgnoreCase(type)) {
                preQueryStatement = "DELETE FROM booking_trainer WHERE id=?";
            }
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

    public boolean deleteRowWithTrainerId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM booking_trainer WHERE trainer_id=?";
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

    public boolean deleteRowWithCenterId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM booking_center WHERE center_id=?";
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

    public boolean deleteTrainerRowWithCustId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM booking_trainer WHERE customer_id=?";
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

    public boolean deleteCenterRowWithCustId(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM booking_center WHERE customer_id=?";
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
