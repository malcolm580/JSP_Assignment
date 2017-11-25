package elearning.servlet;

import elearning.bean.Quiz;
import elearning.bean.User;
import elearning.db.QuizDB;
import elearning.db.QuizResultDB;
import elearning.db.UserQuizDB;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "QuizJSONController", urlPatterns = {"/QuizJSONController"})
public class QuizJSONController extends HttpServlet {

    private UserQuizDB userQuizDB;
    private QuizDB quizDB;
    private QuizResultDB quizResultDB;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userQuizDB = new UserQuizDB(dbUrl, dbUser, dbPassword);
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        quizResultDB = new QuizResultDB(dbUrl, dbUser, dbPassword);
    }

    private boolean checkPermission(HttpServletRequest request, HttpServletResponse
            response) {
        HttpSession session = request.getSession();

        if (request.getSession().isNew() ||
                null == ( request.getSession(false).getAttribute("userInfo"))) {//Check Is authorized
            return false;
        }
        //Get Current User Data
        User userData = (User) session.getAttribute("userInfo");
        if ((!"Admin".equalsIgnoreCase(userData.getRole())) && (!"teacher".equalsIgnoreCase(userData.getRole()))) {//It means no permission
            return false;
        }
        return true;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
       if(!checkPermission(request, response)){//Abort when no permission
           response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
           return;
       }
        HttpSession session = request.getSession();

        //Get Current User Data
        User userData = (User) session.getAttribute("userInfo");
        int userID = userData.getUserID();
        ArrayList<Quiz> quizList = null;
        if ("Admin".equalsIgnoreCase(userData.getRole())) {
            quizList = quizDB.getQuiz();
        } else if ("teacher".equalsIgnoreCase(userData.getRole())) {
            quizList = userQuizDB.getUserQuiz(userID);
        }
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        for (Quiz quiz : quizList) {
            JSONObject quizJSON = new JSONObject();
            quizJSON.put("QuizID", quiz.getQuizID());
            quizJSON.put("ModuleID", quiz.getModuleID());
            quizJSON.put("QuizName", quiz.getQuizName());
            quizJSON.put("AttemptLimit", quiz.getAttemptLimit());
            quizJSON.put("TimeLimit", quiz.getTimeLimit());
            quizJSON.put("TotalQuestion", quiz.getTotalQuestion());
            list.put(quizJSON);
            obj.put("Quiz", list);
        }

        response.getWriter().println(list.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
