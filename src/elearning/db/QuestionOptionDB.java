package elearning.db;

import elearning.bean.QuestionOption;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class QuestionOptionDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public QuestionOptionDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public ArrayList<QuestionOption> getOptionByQuestionID(int questionID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        QuestionOption questionOption = null;
        ArrayList<QuestionOption> questionOptionArrayList = new ArrayList<QuestionOption>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM `QuestionOption` WHERE `QuestionID`=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, questionID);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                questionOption = new QuestionOption();
                questionOption.setOptionID(rs.getInt("OptionID"));
                questionOption.setQuestionID(rs.getInt("QuestionID"));
                questionOption.setOption(rs.getString("Option"));
                questionOptionArrayList.add(questionOption);
            }

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return questionOptionArrayList;
    }


    public QuestionOption getOptionByOptionID(int optionID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        QuestionOption questionOption = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM QuestionOption WHERE `OptionID`=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, optionID);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                questionOption = new QuestionOption();
                questionOption.setOptionID(rs.getInt("OptionID"));
                questionOption.setQuestionID(rs.getInt("QuestionID"));
                questionOption.setOption(rs.getString("Option"));
            }

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return questionOption;
    }

    public boolean editOption(QuestionOption questionOption) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE `QuestionOption` SET `QuestionID`=?,`Option`=? WHERE `OptionID`=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, questionOption.getQuestionID());
            pStmnt.setString(2, questionOption.getOption());
            pStmnt.setInt(3, questionOption.getOptionID());
            row = pStmnt.executeUpdate();

            pStmnt.close();
            cnnct.close();


        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return row > 0;
    }

}
