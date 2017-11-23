package elearning.db;

import elearning.bean.Module;
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
        Class.forName( "com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }


    public ArrayList<Quiz> getUserQuiz(int id)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select UserUserID ,QuizQuizID  From UserQuiz WHERE UserUserID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);

            ResultSet rs = pStmnt.executeQuery();
            if( rs.next() ){
                String preQueryStatement2 = "Select *  From Quiz WHERE QuizID=?";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, rs.getInt("QuizQuizID"));
                ResultSet rs2 = pStmnt.executeQuery();
                quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizQuizID"));
                quiz.setModuleID(rs2.getInt("ModuleID"));
                quiz.setQuizName(rs2.getString("QuizName"));
                quiz.setTimeLimit(rs2.getInt("TimeLimit"));
                quizList.add(quiz);
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
        return quizList;
    }

    public ArrayList<User> getQuizStudentList(String id)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        User user = null;
        ArrayList<User> studentList = new ArrayList<User>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select UserUserID  , UserName From UserQuiz , User WHERE UserUserID = UserID AND QuizQuizID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);

            ResultSet rs = pStmnt.executeQuery();
            if( rs.next() ){
                user = new User();
                user.setUserID(rs.getInt("UserUserID"));
                user.setUsername(rs.getString("UserName"));
                studentList.add(user);
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
        return studentList;
    }

}
