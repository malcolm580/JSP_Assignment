package elearning.db;

import elearning.bean.QuizResult;
import elearning.bean.User;

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


    public boolean addQuizResult(QuizResult quizResult) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO `QuizResult`(`UserID`, `QuizID`, `Duration`, `AnsweringQuestionState_JSON`, `CorrectCount`) VALUES (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, quizResult.getUserID());
            pStmnt.setInt(2, quizResult.getQuizID());
            pStmnt.setInt(3, quizResult.getDuration());
            pStmnt.setString(4, quizResult.getAnsweringQuestionState_JSON());
            pStmnt.setInt(5, quizResult.getCorrectCount());
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

    public ArrayList<User> getAttemptedStudentList(String quizID)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        User AttemptedStudent = null;
        ArrayList<User> AttemptedStudentList = new ArrayList<User>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select UserName ,  QR.UserID , MAX(CorrectCount) From QuizResult QR, User UR WHERE QR.UserID = UR.UserID and quizID = ?  GROUP BY UserID ";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, quizID);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                AttemptedStudent = new User();
                AttemptedStudent.setUserID(rs.getInt("UserID"));
                AttemptedStudent.setUsername(rs.getString("UserName"));
                AttemptedStudentList.add(AttemptedStudent);
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
        return AttemptedStudentList;
    }


}
