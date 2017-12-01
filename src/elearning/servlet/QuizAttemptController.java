package elearning.servlet;

import elearning.bean.*;
import elearning.db.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet(name = "QuizAttemptController", urlPatterns = {"/quizAttempt"})
public class QuizAttemptController extends HttpServlet {
    private QuizDB quizDB;
    private UserModuleDB userModuleDB;
    private QuestionDB questionDB;
    private QuestionOptionDB questionOptionDB;
    private UserQuizDB userQuizDB;
    private ModuleDB moduleDB;
    private QuizResultDB quizResultDB;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        userModuleDB = new UserModuleDB(dbUrl, dbUser, dbPassword);
        questionDB = new QuestionDB(dbUrl, dbUser, dbPassword);
        questionOptionDB = new QuestionOptionDB(dbUrl, dbUser, dbPassword);
        userQuizDB = new UserQuizDB(dbUrl, dbUser, dbPassword);
        moduleDB = new ModuleDB(dbUrl, dbUser, dbPassword);
        quizResultDB = new QuizResultDB(dbUrl, dbUser, dbPassword);
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
            if ("attempt".equalsIgnoreCase(action)) {
                String quizID_String = request.getParameter("quizid");
                if (quizID_String == null || quizID_String.length() <= 0 || !isInteger(quizID_String)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();
                int quizID = Integer.parseInt(quizID_String);
                boolean isValid = false;
                for (Quiz checkingQuiz : userQuizDB.getUserQuiz(userID)) {
                    if (checkingQuiz.getQuizID() == quizID) {
                        isValid = true;
                        break;
                    }
                }
                if (!isValid) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Quiz quiz = quizDB.getQuizByID(quizID);
                quiz.setModule(quizDB.getParentModule(quiz));
                ArrayList<Question> questionArrayList = questionDB.getQuestionByQuizID(quizID);

                for (Question question : questionArrayList) {
                    ArrayList<QuestionOption> questionOptionArrayList = questionOptionDB.getOptionByQuestionID(question.getQuestionID());
                    Collections.shuffle(questionOptionArrayList);
                    question.setQuestionOptionArrayList(questionOptionArrayList);
                }
                Collections.shuffle(questionArrayList);

                int totalQuestionsNeeds = quiz.getTotalQuestion();
                if (totalQuestionsNeeds > 0) {
                    for (int i = totalQuestionsNeeds; i < questionArrayList.size(); i = totalQuestionsNeeds) {
                        questionArrayList.remove(0);
                    }
                }
                session.setAttribute("currentQuiz", quiz);
                session.setAttribute("currentQuestionArrayList", questionArrayList);
                //Execute Return
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/QuizAttempts.jsp");
                rd.forward(request, response);
            } else if ("submit".equalsIgnoreCase(action)) {
                String quizID_String = request.getParameter("QuizID");
                if (quizID_String == null || quizID_String.length() <= 0 || !isInteger(quizID_String)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();
                int quizID = Integer.parseInt(quizID_String);
                Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");

                String[] ids = request.getParameterValues("IDs");
                ArrayList<Question> questionArrayList = null;
                ArrayList<QuestionOption> questionOptionArrayList = new ArrayList<>();
                QuizResult quizResult = new QuizResult();
                JSONObject answeringQuestionState_JSON = new JSONObject();
                JSONArray questionJSON = new JSONArray();
                int correctCount = 0;

                for (String id : ids) {
                    JSONArray questionArray = new JSONArray();
                    JSONObject idJSON = new JSONObject();
                    JSONObject optionJSON = new JSONObject();
                    idJSON.put("QuestionID", id);
                    optionJSON.put("OptionID", request.getParameter(id));
                    questionArray.put(optionJSON);
                    questionArray.put(idJSON);
                    questionJSON.put(questionArray);


                    //Check is correct
                    int correctAnswer = questionDB.getQuestionByQuestionID(Integer.parseInt(id)).getCorrectOptionID();
                    int userAnswer = Integer.parseInt(request.getParameter(id));
                    if (correctAnswer == userAnswer) {
                        correctCount++;
                    }
                }
                answeringQuestionState_JSON.put("Question", questionJSON);

                quizResult.setUserID(userID);
                quizResult.setQuizID(quizID);
                quizResult.setDuration(0);//TODO Duration
                quizResult.setAnsweringQuestionState_JSON(answeringQuestionState_JSON.toString());
                quizResult.setCorrectCount(correctCount);
                quizResultDB.addQuizResult(quizResult);

                //Execute Return
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/quiz?action=EnterQuiz&quizid=" + quizID);
                rd.forward(request, response);
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
