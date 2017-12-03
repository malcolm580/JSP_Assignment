package elearning.db;

import elearning.bean.Quiz;
import elearning.bean.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class UserQuizDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public UserQuizDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }


    public ArrayList<Quiz> getUserQuiz(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT UserUserID ,QuizQuizID  FROM UserQuiz WHERE UserUserID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                quizList.add(getQuiz(rs.getInt("QuizQuizID")));

            }

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quizList;
    }

    public void addRecord(String quizID, String userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO UserQuiz (UserUserID, QuizQuizID) VALUES (?  , ? );";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userID);
            pStmnt.setString(2, quizID);

            pStmnt.executeUpdate();

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getQuizStudentList(String id) throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        User user = null;
        ArrayList<User> studentList = new ArrayList<User>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT UserUserID ,UserName FROM UserQuiz ,User WHERE UserUserID = UserID AND QuizQuizID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);

            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserUserID"));
                user.setUsername(rs.getString("UserName"));
                studentList.add(user);
            }

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return studentList;
    }

    public Quiz getQuiz(int quizID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Quiz WHERE QuizID=? LIMIT 1";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, quizID);

            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setModuleID(rs.getInt("ModuleID"));
                quiz.setTimeLimit(rs.getInt("TimeLimit"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setTotalQuestion(rs.getInt("TotalQuestion"));
                quiz.setAttemptLimit(rs.getInt("AttemptLimit"));
            }

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quiz;
    }

}
