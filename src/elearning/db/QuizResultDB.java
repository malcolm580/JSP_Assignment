package elearning.db;

import elearning.bean.Module;
import elearning.bean.Quiz;
import elearning.bean.QuizResult;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class QuizResultDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public QuizResultDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName( "com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }


    public ArrayList<QuizResult> getMQuizResult(int userID, int quizID)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        QuizResult quizResult = null;
        ArrayList<QuizResult> quizResultArrayList = new ArrayList<QuizResult>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From QuizResult WHERE UserID=? AND QuizID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userID);
            pStmnt.setInt(2, quizID);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                quizResult = new QuizResult();
                quizResult.setUserID(rs.getInt("UserID"));
                quizResult.setQuizID(rs.getInt("QuizID"));
                quizResult.setQuizResultID(rs.getInt("QuizResultID"));
                quizResult.setAnsweringQuestionState_JSON(rs.getString("AnsweringQuestionState_JSON"));
                quizResult.setCorrectCount(rs.getInt("CorrectCount"));
                quizResultArrayList.add(quizResult);
            }

            pStmnt.close();
            cnnct.close();
        } catch(SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return quizResultArrayList;
    }

    public ArrayList<QuizResult> getMQuizResult(int userID)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        QuizResult quizResult = null;
        ArrayList<QuizResult> quizResultArrayList = new ArrayList<QuizResult>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From QuizResult WHERE UserID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userID);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                quizResult = new QuizResult();
                quizResult.setUserID(rs.getInt("UserID"));
                quizResult.setQuizID(rs.getInt("QuizID"));
                quizResult.setQuizResultID(rs.getInt("QuizResultID"));
                quizResult.setAnsweringQuestionState_JSON(rs.getString("AnsweringQuestionState_JSON"));
                quizResult.setCorrectCount(rs.getInt("CorrectCount"));
                quizResultArrayList.add(quizResult);
            }

            pStmnt.close();
            cnnct.close();
        } catch(SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return quizResultArrayList;
    }



}
