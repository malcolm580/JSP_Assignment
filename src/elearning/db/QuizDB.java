package elearning.db;

import elearning.bean.Module;
import elearning.bean.Quiz;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class QuizDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public QuizDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName( "com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public ArrayList<Quiz> getQuiz() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From Quiz";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setModuleID(rs.getInt("ModuleID"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setAttemptLimit(rs.getInt("AttemptLimit"));
                quiz.setTimeLimit(rs.getInt("TimeLimit"));
                quiz.setTotalQuestion(rs.getInt("TotalQuestion"));
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quizList;
    }
    public ArrayList<Quiz> getModuleQuiz(String moduleID)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From Quiz WHERE ModuleID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, moduleID);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setModuleID(rs.getInt("ModuleID"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setAttemptLimit(rs.getInt("AttemptLimit"));
                quiz.setTimeLimit(rs.getInt("TimeLimit"));
                quiz.setTotalQuestion(rs.getInt("TotalQuestion"));
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
    public Module getParentModule(Quiz quiz) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Module module = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Module WHERE ModuleID=? LIMIT 1";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, quiz.getModuleID());

            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                module = new Module();
                module.setModuleID(rs.getInt("ModuleID"));
                module.setModuleName(rs.getString("ModuleName"));
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
        return module;
    }

}
