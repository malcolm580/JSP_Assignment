package elearning.servlet;

import elearning.bean.*;
import elearning.db.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "QuizController", urlPatterns = {"/quiz"})
public class QuizController extends HttpServlet {

    private UserQuizDB userQuizDB;
    private QuizDB quizDB;
    private QuizResultDB quizResultDB;
    private UserModuleDB userModuleDB;
    private QuestionDB questionDB;
    private QuestionOptionDB questionOptionDB;
    private ModuleDB moduleDB;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userQuizDB = new UserQuizDB(dbUrl, dbUser, dbPassword);
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        quizResultDB = new QuizResultDB(dbUrl, dbUser, dbPassword);
        userModuleDB = new UserModuleDB(dbUrl, dbUser, dbPassword);
        questionOptionDB = new QuestionOptionDB(dbUrl, dbUser, dbPassword);
        questionDB = new QuestionDB(dbUrl, dbUser, dbPassword);
        moduleDB = new ModuleDB(dbUrl, dbUser, dbPassword);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
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

            if ("list".equalsIgnoreCase(action)) {
                //Init
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();
                ArrayList<Quiz> quizList = userQuizDB.getUserQuiz(userID);
                session.setAttribute("quizList", quizList);

                //Return
                targetURL = request.getParameter("returnto");
                if (targetURL == null || targetURL.length() <= 0) {
                    targetURL = "QuizList.jsp";
                }

                //Execute Return
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/" + targetURL);
                rd.forward(request, response);
            } else if ("EnterQuiz".equalsIgnoreCase(action)) {
                HttpSession session = request.getSession();
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();
                ArrayList<Quiz> quizList = userQuizDB.getUserQuiz(userID);

                session.setAttribute("quizList", quizList);
                String quizID_String = request.getParameter("quizid");
                if (null == quizID_String || quizID_String.length() <= 0) {
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("./index.jsp?msg=Please%20Select%20A%20Quiz!");
                    rd.forward(request, response);
                    return;
                }
                if (!isInteger(quizID_String)) {
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("./index.jsp?msg=Please%20Input%20Valid%20Quiz%20ID!");
                    rd.forward(request, response);
                    return;
                }
                int quizID = Integer.parseInt(quizID_String);
                Quiz currentQuiz = userQuizDB.getQuiz(quizID);
                if (currentQuiz == null) {
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("./index.jsp?msg=Please%20Select%20A%20Quiz!");
                    rd.forward(request, response);
                    return;
                }
                Module currentModule = quizDB.getParentModule(currentQuiz);
                ArrayList<QuizResult> currentQuizResultList = quizResultDB.getMQuizResult(userID, quizID);

                session.setAttribute("currentModule", currentModule);
                session.setAttribute("currentQuiz", currentQuiz);
                session.setAttribute("currentQuizResultList", currentQuizResultList);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/QuizEnter.jsp");
                rd.forward(request, response);
            } else if ("QuizManagement".equalsIgnoreCase(action)) {
                if (!checkPermission(request, response)) {//Abort when no permission
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
                for (Quiz quiz : quizList) {
                    quiz.setModule(moduleDB.getModule(quiz.getModuleID() + ""));//When get the quiz from database, get moduleID and take the module in to the quiz for association.
                }
                session.setAttribute("currentQuiz", quizList);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/QuizManagement.jsp");
                rd.forward(request, response);
            }
            else if ("Edit".equalsIgnoreCase(action)) {
                if (!checkPermission(request, response)) {//Abort when no permission
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();
                String quizID_String = request.getParameter("quizid");

                if (quizID_String == null || quizID_String.length() <= 0 || (!isInteger(quizID_String))) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                int quizID = Integer.parseInt(quizID_String);
                if (!isAuthenticated(request, response, userData, quizID)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                ArrayList<Question> currentQuiz_Question = questionDB.getQuestionByQuizID(quizID);
                for (Question question : currentQuiz_Question) {
                    question.setQuestionOptionArrayList(questionOptionDB.getOptionByQuestionID(question.getQuestionID()));
                }

                session.setAttribute("currentQuiz", quizDB.getQuizByID(quizID));
                session.setAttribute("currentQuiz_Question", currentQuiz_Question);

                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/QuizEdit.jsp");
                rd.forward(request, response);
            }else {
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
