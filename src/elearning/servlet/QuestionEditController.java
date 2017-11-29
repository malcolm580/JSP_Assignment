package elearning.servlet;

import elearning.bean.Module;
import elearning.bean.Question;
import elearning.bean.User;
import elearning.db.QuestionDB;
import elearning.db.QuestionOptionDB;
import elearning.db.QuizDB;
import elearning.db.UserModuleDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "QuestionEditController", urlPatterns = {"/question/edit"})
public class QuestionEditController extends HttpServlet {

    private QuizDB quizDB;
    private UserModuleDB userModuleDB;
    private QuestionDB questionDB;
    private QuestionOptionDB questionOptionDB;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        userModuleDB = new UserModuleDB(dbUrl, dbUser, dbPassword);
        questionDB = new QuestionDB(dbUrl, dbUser, dbPassword);
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
            if ("view".equalsIgnoreCase(action)) {
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
                Question question = questionDB.getQuestionByQuestionID(questionID);
                question.setQuestionOptionArrayList(questionOptionDB.getOptionByQuestionID(question.getQuestionID()));
                session.setAttribute("currentQuestion", question);

                //Execute Return
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/QuestionEdit.jsp");
                rd.forward(request, response);
            } else if ("edit".equalsIgnoreCase(action)) {
                if (!checkPermission(request, response)) {//Abort when no permission
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();

                Question question = new Question();
                String QuestionID_String = request.getParameter("QuestionID");
                String QuizID_String = request.getParameter("QuizID");
                String QuestionType = request.getParameter("QuestionType");
                String Question = request.getParameter("Question");
                String CorrectOptionID_String = request.getParameter("CorrectOptionID");

                if (!isInteger(QuestionID_String) ||//Checking is the value correct, if not, send bad request
                        !isInteger(QuizID_String) ||
                        !isInteger(CorrectOptionID_String)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                int QuestionID = Integer.parseInt(QuizID_String);
                int QuizID = Integer.parseInt(QuizID_String);
                int CorrectOptionID = Integer.parseInt(CorrectOptionID_String);

                question.setQuestionID(QuestionID);
                question.setQuizID(QuizID);
                question.setQuestionType(QuestionType);
                question.setQuestion(Question);
                question.setCorrectOptionID(CorrectOptionID);
                questionDB.editQuestion(question);

                //Return
                targetURL = "quiz?action=edit&quizid=" + QuizID_String + "&msg=Success%20edit%20the%20quiz";

                //Execute Return
                response.sendRedirect("../" + targetURL);
            } else if ("add".equalsIgnoreCase(action)) {
                if (!checkPermission(request, response)) {//Abort when no permission
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();

                Question question = new Question();
                String QuizID_String = request.getParameter("id");
                String QuestionType = "multiple";
                String Question = request.getParameter("question");

                if (//Checking is the value correct, if not, send bad request
                        !isInteger(QuizID_String)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                int QuizID = Integer.parseInt(QuizID_String);

                question.setQuizID(QuizID);
                question.setQuestionType(QuestionType);
                question.setQuestion(Question);
                questionDB.addQuestion(question);

                //Return
                targetURL = "quiz?action=edit&quizid=" + QuizID_String + "&msg=Success%20add%20the%20quiz";

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
