package elearning.servlet;

import elearning.bean.Module;
import elearning.bean.Quiz;
import elearning.bean.User;
import elearning.db.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "QuizDeleteController", urlPatterns = {"/quiz/delete"})
public class QuizDeleteController extends HttpServlet {

    private UserQuizDB userQuizDB;
    private QuizDB quizDB;
    private QuizResultDB quizResultDB;
    private UserModuleDB userModuleDB;
    private QuestionDB questionDB;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userQuizDB = new UserQuizDB(dbUrl, dbUser, dbPassword);
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        quizResultDB = new QuizResultDB(dbUrl, dbUser, dbPassword);
        userModuleDB = new UserModuleDB(dbUrl, dbUser, dbPassword);
        questionDB=new QuestionDB(dbUrl, dbUser, dbPassword);
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


            if (!checkPermission(request, response)) {//Abort when no permission
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            HttpSession session = request.getSession();

            //Get Current User Data
            User userData = (User) session.getAttribute("userInfo");
            int userID = userData.getUserID();

            String quizID_String=request.getParameter("quizid");
            if(quizID_String==null||quizID_String.length()<=0||!isInteger(quizID_String)){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int quizID=Integer.parseInt(quizID_String);
            if(!isAuthenticated(request,response,userData,quizID)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            if(quizDB.deleteQuiz(quizID)){
                questionDB.resetQuestionOfQuiz(quizID);
                targetURL = "quiz?action=QuizManagement&msg=Success%20<span style='color:red'>DELETE</span>%20the%20quiz%20And%20Reset%20Question%20To%20Question%20Pool";
            }else{
                targetURL = "quiz?action=QuizManagement&msg=Delete%20Failure";
            }

            //Execute Return
            response.sendRedirect("../" + targetURL);

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
