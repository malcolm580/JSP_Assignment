package elearning.servlet;

import elearning.bean.Module;
import elearning.bean.QuestionOption;
import elearning.bean.User;
import elearning.db.QuestionOptionDB;
import elearning.db.QuizDB;
import elearning.db.UserModuleDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "QuestionOptionController", urlPatterns = {"/question/option/edit"})
public class QuestionOptionController extends HttpServlet {

    private QuizDB quizDB;

    private UserModuleDB userModuleDB;

    private QuestionOptionDB questionOptionDB;


    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        questionOptionDB = new QuestionOptionDB(dbUrl, dbUser, dbPassword);
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        userModuleDB = new UserModuleDB(dbUrl, dbUser, dbPassword);
        questionOptionDB = new QuestionOptionDB(dbUrl, dbUser, dbPassword);
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
            if ("add".equalsIgnoreCase(action)) {
                if (!checkPermission(request, response)) {//Abort when no permission
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                String questionID_String = request.getParameter("id");
                if (questionID_String == null || questionID_String.length() <= 0 || !isInteger(questionID_String)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();

                int questionID = Integer.parseInt(questionID_String);
                String option = request.getParameter("option");
                if (option == null || option.length() <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                QuestionOption questionOption = new QuestionOption();
                questionOption.setOption(option);
                questionOption.setQuestionID(questionID);
                if (questionOptionDB.addQuestion(questionOption)) {
                    response.sendError(HttpServletResponse.SC_OK);
                    return;
                }
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);


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
