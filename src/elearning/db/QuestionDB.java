package elearning.db;

import elearning.bean.Question;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class QuestionDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public QuestionDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public ArrayList<Question> getQuestionByQuizID(int quizID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Question question = null;
        ArrayList<Question> questionArrayList = new ArrayList<Question>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Question WHERE `QuizID`=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, quizID);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                question = new Question();
                question.setQuizID(rs.getInt("QuizID"));
                question.setQuestionID(rs.getInt("QuestionID"));
                question.setQuestionType(rs.getString("QuestionType"));
                question.setQuestion(rs.getString("Question"));
                question.setCorrectOptionID(rs.getInt("CorrectOptionID"));
                questionArrayList.add(question);
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
        return questionArrayList;
    }


    public Question getQuestionByQuestionID(int questionID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Question question = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Question WHERE `QuestionID`=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, questionID);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                question = new Question();
                question.setQuizID(rs.getInt("QuizID"));
                question.setQuestionID(rs.getInt("QuestionID"));
                question.setQuestionType(rs.getString("QuestionType"));
                question.setQuestion(rs.getString("Question"));
                question.setCorrectOptionID(rs.getInt("CorrectOptionID"));
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
        return question;
    }

    public boolean editQuestion(Question question) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE `Question` SET `QuizID`=?,`QuestionType`=?,`Question`=?,`CorrectOptionID`=? WHERE `QuestionID`=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, question.getQuizID());
            pStmnt.setString(2, question.getQuestionType());
            pStmnt.setString(3, question.getQuestion());
            pStmnt.setInt(4, question.getCorrectOptionID());
            pStmnt.setInt(5, question.getQuizID());
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
    public boolean deleteQuestion(int questionID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM `Question` WHERE `QuestionID`=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1,questionID);
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

    public boolean resetQuestionOfQuiz(int quizID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE `Question` SET `QuizID`=NULL WHERE `QuizID`=?";
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

    public boolean addQuestion(Question question) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int row = 0; //It is the affected row count of the query
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO `Question`( `QuizID`, `QuestionType`, `Question`, `CorrectOptionID`) VALUES (?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, question.getQuizID() + "");
            pStmnt.setString(2, question.getQuestionType());
            pStmnt.setString(3, question.getQuestion());
            pStmnt.setString(4, question.getCorrectOptionID() + "");
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
