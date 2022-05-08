package ict.db;

import ict.bean.CenterBean;
import ict.bean.IncomeCenterBean;
import ict.bean.IncomeTrainerBean;
import ict.bean.ReportCenterBean;
import ict.bean.ReportTrainerBean;
import ict.bean.TrainerBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReportDB {

    private String dbUrl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public ReportDB(String dbUrl, String dbUser, String dbPassword) {
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

    public ArrayList<ReportTrainerBean> queryTrainerRate(String type, String input) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement subPStmnt = null;

        ArrayList<ReportTrainerBean> bList = new ArrayList();
        TrainerBean tb = null;
        ReportTrainerBean b = null;

        try {
            cnnct = getConnection();
            String subPreQueryStatement = "";
            String preQueryStatement = "";
            if (type.equalsIgnoreCase("month")) {
                preQueryStatement = "SELECT trainer_id, count(*), count(*) * 100.0 / sum(count(*)) over() "
                        + "FROM booking_trainer_view WHERE " + type + "(date) = ? AND year(date) = 2022 GROUP BY trainer_id";
            } else if (type.equalsIgnoreCase("year")) {
                preQueryStatement = "SELECT trainer_id, count(*), count(*) * 100.0 / sum(count(*)) over() "
                        + "FROM booking_trainer_view WHERE " + type + "(date) = ? GROUP BY trainer_id";
            }

            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(input));
            ResultSet rs = null;
            ResultSet subRs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new ReportTrainerBean();
                b.setCount(rs.getString(2));
                b.setRate(rs.getString(3));
                b.setType(type);

                subPreQueryStatement = "SELECT * FROM trainer_view WHERE id=" + rs.getString(1);
                subPStmnt = cnnct.prepareStatement(subPreQueryStatement);
                subRs = subPStmnt.executeQuery();

                if (subRs.next()) {
                    tb = new TrainerBean();
                    tb.setId(subRs.getString(1));
                    tb.setFname(subRs.getString(2));
                    tb.setLname(subRs.getString(3));
                    tb.setImg(subRs.getString(4));
                    tb.setDesc(subRs.getString(5));
                    tb.setTel(subRs.getString(6));
                    tb.setStatus(subRs.getString(7));
                    tb.setFee(subRs.getString(8));
                    tb.setCenter(subRs.getString(9));
                    tb.setCenterName(subRs.getString(10));
                    b.setTrainer(tb);

                    // DEBUG MESSAGE
                    System.out.println("query success");
                }
                subPStmnt.close();

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

    public ArrayList<ReportCenterBean> queryCenterRate(String type, String input) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement subPStmnt = null;

        ArrayList<ReportCenterBean> bList = new ArrayList();
        CenterBean tb = null;
        ReportCenterBean b = null;

        try {
            cnnct = getConnection();
            String subPreQueryStatement = "";
            String preQueryStatement = "";
            if (type.equalsIgnoreCase("month")) {
                preQueryStatement = "SELECT center_id, count(*), count(*) * 100.0 / sum(count(*)) over() "
                        + "FROM booking_center_view WHERE " + type + "(date) = ? AND year(date) = 2022 GROUP BY center_id";
            } else if (type.equalsIgnoreCase("year")) {
                preQueryStatement = "SELECT center_id, count(*), count(*) * 100.0 / sum(count(*)) over() "
                        + "FROM booking_center_view WHERE " + type + "(date) = ? GROUP BY center_id";
            }

            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(input));
            ResultSet rs = null;
            ResultSet subRs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new ReportCenterBean();
                b.setCount(rs.getString(2));
                b.setRate(rs.getString(3));
                b.setType(type);

                subPreQueryStatement = "SELECT * FROM center WHERE id=" + rs.getString(1);
                subPStmnt = cnnct.prepareStatement(subPreQueryStatement);
                subRs = subPStmnt.executeQuery();

                if (subRs.next()) {
                    tb = new CenterBean();
                    tb.setId(subRs.getString(1));
                    tb.setName(subRs.getString(2));
                    tb.setLocation(subRs.getString(3));
                    tb.setImg(subRs.getString(4));
                    tb.setDesc(subRs.getString(5));
                    tb.setFee(subRs.getString(6));
                    tb.setTel(subRs.getString(7));
                    tb.setStatus(subRs.getString(8));
                    b.setCenter(tb);

                    // DEBUG MESSAGE
                    System.out.println("query success");
                }
                subPStmnt.close();

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

    public ArrayList<IncomeTrainerBean> queryTrainerIncome(String type, String input) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement subPStmnt = null;

        ArrayList<IncomeTrainerBean> bList = new ArrayList();
        TrainerBean tb = null;
        IncomeTrainerBean b = null;

        try {
            cnnct = getConnection();
            String subPreQueryStatement = "";
            String preQueryStatement = "";
            if (type.equalsIgnoreCase("month")) {
                preQueryStatement = "SELECT personal_trainer.id,count(trainer_id) "
                        + ",count(trainer_id) * personal_trainer.fee FROM personal_trainer "
                        + "LEFT JOIN booking_trainer_view as b "
                        + "ON personal_trainer.id = b.trainer_id "
                        + "AND " + type + "(date) = ? AND year(date) = 2022 GROUP BY personal_trainer.id";
            } else if (type.equalsIgnoreCase("year")) {
                preQueryStatement = "SELECT personal_trainer.id,count(trainer_id) "
                        + ",count(trainer_id) * personal_trainer.fee FROM personal_trainer "
                        + "LEFT JOIN booking_trainer_view as b "
                        + "ON personal_trainer.id = b.trainer_id "
                        + "AND " + type + "(date) = ? GROUP BY personal_trainer.id";
            }

            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(input));
            ResultSet rs = null;
            ResultSet subRs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new IncomeTrainerBean();
                b.setCount(rs.getString(2));
                b.setIncome(rs.getString(3));
                b.setType(type);

                subPreQueryStatement = "SELECT * FROM trainer_view WHERE id=" + rs.getString(1);
                subPStmnt = cnnct.prepareStatement(subPreQueryStatement);
                subRs = subPStmnt.executeQuery();

                if (subRs.next()) {
                    tb = new TrainerBean();
                    tb.setId(subRs.getString(1));
                    tb.setFname(subRs.getString(2));
                    tb.setLname(subRs.getString(3));
                    tb.setImg(subRs.getString(4));
                    tb.setDesc(subRs.getString(5));
                    tb.setTel(subRs.getString(6));
                    tb.setStatus(subRs.getString(7));
                    tb.setFee(subRs.getString(8));
                    tb.setCenter(subRs.getString(9));
                    tb.setCenterName(subRs.getString(10));
                    b.setTrainer(tb);

                    // DEBUG MESSAGE
                    System.out.println("query success");
                }
                subPStmnt.close();

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

    public ArrayList<IncomeCenterBean> queryCenterIncome(String type, String input) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement subPStmnt = null;

        ArrayList<IncomeCenterBean> bList = new ArrayList();
        CenterBean tb = null;
        IncomeCenterBean b = null;

        try {
            cnnct = getConnection();
            String subPreQueryStatement = "";
            String preQueryStatement = "";
            if (type.equalsIgnoreCase("month")) {
                preQueryStatement = "SELECT center.id,count(b.center_id) "
                        + "as 'booking_count',count(b.center_id) * center.fee as 'income' "
                        + "FROM center LEFT JOIN booking_center_view as b "
                        + "ON center.id = b.center_id AND " + type + "(date) = ? "
                        + "AND year(date) = 2022 GROUP BY  center.id";
            } else if (type.equalsIgnoreCase("year")) {
                preQueryStatement = "SELECT center.id,count(b.center_id) "
                        + "as 'booking_count',count(b.center_id) * center.fee as 'income' "
                        + "FROM center LEFT JOIN booking_center_view as b "
                        + "ON center.id = b.center_id AND " + type + "(date) = ? GROUP BY  center.id";
            }

            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, Integer.parseInt(input));
            ResultSet rs = null;
            ResultSet subRs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                b = new IncomeCenterBean();
                b.setCount(rs.getString(2));
                b.setIncome(rs.getString(3));
                b.setType(type);

                subPreQueryStatement = "SELECT * FROM center WHERE id=" + rs.getString(1);
                subPStmnt = cnnct.prepareStatement(subPreQueryStatement);
                subRs = subPStmnt.executeQuery();

                if (subRs.next()) {
                    tb = new CenterBean();
                    tb.setId(subRs.getString(1));
                    tb.setName(subRs.getString(2));
                    tb.setLocation(subRs.getString(3));
                    tb.setImg(subRs.getString(4));
                    tb.setDesc(subRs.getString(5));
                    tb.setFee(subRs.getString(6));
                    tb.setTel(subRs.getString(7));
                    tb.setStatus(subRs.getString(8));
                    b.setCenter(tb);

                    // DEBUG MESSAGE
                    System.out.println("query success");
                }
                subPStmnt.close();

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
