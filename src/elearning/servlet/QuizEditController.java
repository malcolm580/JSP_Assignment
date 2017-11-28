package elearning.servlet;

import elearning.bean.Module;
import elearning.bean.Quiz;
import elearning.bean.User;
import elearning.db.QuizDB;
import elearning.db.QuizResultDB;
import elearning.db.UserModuleDB;
import elearning.db.UserQuizDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "QuizEditController", urlPatterns = {"/quiz/edit"})
public class QuizEditController extends HttpServlet {

    private UserQuizDB userQuizDB;
    private QuizDB quizDB;
    private QuizResultDB quizResultDB;
    private UserModuleDB userModuleDB;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userQuizDB = new UserQuizDB(dbUrl, dbUser, dbPassword);
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        quizResultDB = new QuizResultDB(dbUrl, dbUser, dbPassword);
        userModuleDB = new UserModuleDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            if (request.getSession().isNew() ||
                    null == ((User) request.getSession(false).getAttribute("userInfo"))) {//Check Is authorized
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            String action = request.getParameter("action");

            String targetURL;

            if ("edit".equalsIgnoreCase(action)) {
                if (!checkPermission(request, response)) {//Abort when no permission
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();

                Quiz editingQuiz = new Quiz();
                String quizIDString = request.getParameter("QuizID");
                String moduleIDString = request.getParameter("ModuleID");
                String QuizName = request.getParameter("QuizName");
                String attemptLimitString = request.getParameter("AttemptLimit");
                String timeLimitString = request.getParameter("TimeLimit");
                String totalQuestionString = request.getParameter("TotalQuestion");

                if (!isInteger(quizIDString) ||//Checking is the value correct, if not, send bad request
                        !isInteger(moduleIDString) ||
                        !isInteger(attemptLimitString) ||
                        !isInteger(timeLimitString) ||
                        !isInteger(totalQuestionString)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                int QuizID = Integer.parseInt(request.getParameter("QuizID"));
                int ModuleID = Integer.parseInt(request.getParameter("ModuleID"));
                int AttemptLimit = Integer.parseInt(request.getParameter("AttemptLimit"));
                int TimeLimit = Integer.parseInt(request.getParameter("TimeLimit"));
                int TotalQuestion = Integer.parseInt(request.getParameter("TotalQuestion"));

                editingQuiz.setQuizID(QuizID);
                editingQuiz.setModuleID(ModuleID);
                editingQuiz.setQuizName(QuizName);
                editingQuiz.setAttemptLimit(AttemptLimit);
                editingQuiz.setTimeLimit(TimeLimit);
                editingQuiz.setTotalQuestion(TotalQuestion);


                //Return
                targetURL = "quiz?action=QuizManagement&msg=Success%20edit%20the%20quiz";

                //Execute Return
                response.sendRedirect("../" + targetURL);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission(HttpServletRequest request, HttpServletResponse
            response) {
        HttpSession session = request.getSession();

        if (request.getSession().isNew() ||
                null == (request.getSession(false).getAttribute("userInfo"))) {//Check Is authorized
            return false;
        }
        //Get Current User Data
        User userData = (User) session.getAttribute("userInfo");
        if (("Admin".equalsIgnoreCase(userData.getRole())) || ("teacher".equalsIgnoreCase(userData.getRole()))) {//It means no permission
            return true;
        }
        return false;
    }

    private boolean isAuthenticated(HttpServletRequest request, HttpServletResponse
            response, User userinfo, int quizID) {
        if (userinfo.getRole().equalsIgnoreCase("admin")) {
            return true;
        }
        if (!userinfo.getRole().equalsIgnoreCase("teacher")) {
            return false;
        }
        ArrayList<Module> moduleArrayList = userModuleDB.getUserModule(userinfo.getUserID());
        Module module = quizDB.getParentModule(quizDB.getQuizByID(quizID));
        for (Module checkingModule : moduleArrayList) {
            if (checkingModule.getModuleID() == module.getModuleID()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInteger(String s) {
        if (s == null || s.length() <= 0) {
            return false;
        }
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
