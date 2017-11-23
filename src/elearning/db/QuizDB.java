package elearning.db;

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


    public ArrayList<Quiz> getModuleQuiz(String moduleID)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Quiz quiz = null;
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select QuizID , QuizName From Quiz WHERE ModuleID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, moduleID);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setQuizName(rs.getString("QuizName"));
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

}
