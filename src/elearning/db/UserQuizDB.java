package elearning.db;

import elearning.bean.Module;
import elearning.bean.Quiz;

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
            String preQueryStatement = "Select UserUserID ,QuizQuizID  From UserQuiz WHERE UserUserID=11";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            //pStmnt.setInt(1, id);
            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                quizList.add(getQuiz(rs.getInt("QuizQuizID")));

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
    public Quiz getQuiz(int id){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From Quiz WHERE QuizID=? LIMIT 1";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);

            ResultSet rs = pStmnt.executeQuery();
            while( rs.next() ){
                quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setModuleID(rs.getInt("ModuleID"));
                quiz.setTimeLimit(rs.getInt("TimeLimit"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setTotalQuestion(rs.getInt("TotalQuestion"));
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quiz;
    }

}
