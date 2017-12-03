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
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public ArrayList<Quiz> getQuiz() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Quiz";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
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

    public ArrayList<Quiz> getModuleQuiz(String moduleID) throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Quiz WHERE ModuleID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, moduleID);

            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
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
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
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
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return module;
    }

    // Use in reportingController --> QuizStudentReport.jsp (FKY)
    public Quiz getQuizByID(int quizID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Module module = null;
        Quiz quiz = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Quiz WHERE QuizID=? ";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, quizID);

            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setModuleID(rs.getInt("ModuleID"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setAttemptLimit(rs.getInt("AttemptLimit"));
                quiz.setTimeLimit(rs.getInt("TimeLimit"));
                quiz.setTotalQuestion(rs.getInt("TotalQuestion"));
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
        return quiz;
    }

    public boolean editQuiz(Quiz quiz) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE `Quiz` SET `ModuleID`=?,`QuizName`=?,`AttemptLimit`=?,`TimeLimit`=?,`TotalQuestion`=? WHERE `QuizID`=? ";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, quiz.getModuleID());
            pStmnt.setString(2, quiz.getQuizName());
            pStmnt.setInt(3, quiz.getAttemptLimit());
            pStmnt.setInt(4, quiz.getTimeLimit());
            pStmnt.setInt(5, quiz.getTotalQuestion());
            pStmnt.setInt(6, quiz.getQuizID());
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
    public boolean deleteQuiz(int quizID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM `Quiz` WHERE `QuizID`=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1,quizID);
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

    public boolean addQuiz(Quiz quiz) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO `Quiz`( `ModuleID`, `QuizName`, `AttemptLimit`, `TimeLimit`, `TotalQuestion`) VALUES (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, quiz.getModuleID());
            pStmnt.setString(2, quiz.getQuizName());
            pStmnt.setInt(3, quiz.getAttemptLimit());
            pStmnt.setInt(4, quiz.getTimeLimit());
            pStmnt.setInt(5, quiz.getTotalQuestion());
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

    public int getLastRecordID() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int id = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT QuizID FROM Quiz ORDER BY QuizID DESC LIMIT 1";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                id = rs.getInt("QuizID");
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
        return id;
    }

}
