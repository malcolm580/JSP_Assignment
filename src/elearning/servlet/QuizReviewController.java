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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet(name = "QuizReviewController", urlPatterns = {"/quizReview"})
public class QuizReviewController extends HttpServlet {
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
            if("Review".equalsIgnoreCase(action)){

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

                // Get Current Result
                String resultid = request.getParameter("resultid");

                QuizResult quizResult = quizResultDB.getResult(Integer.parseInt(resultid));

                String quizResultAnswer = quizResult.getAnsweringQuestionState_JSON();

                JSONObject quizResultAnswerJson = new JSONObject(quizResultAnswer);
                JSONArray quizResultAnswerArray =  quizResultAnswerJson.getJSONArray("Question");

                ArrayList<String> optionList = new ArrayList();
                ArrayList<String> questionIDList = new ArrayList();

                PrintWriter out = response.getWriter();
                for (int i = 0; i < quizResultAnswerArray.length(); i++) {
                    JSONArray questionArray = quizResultAnswerArray.getJSONArray(i);

                    JSONObject OptionIDObject = (JSONObject) questionArray.get(0);
                    String OptionID = (String) OptionIDObject.get("OptionID");
                    optionList.add(OptionID);

                    JSONObject QuestionIDObject = (JSONObject) questionArray.get(1);
                    String QuestionID = (String) QuestionIDObject.get("QuestionID");
                    questionIDList.add(QuestionID);
                }

                session.setAttribute("optionList" , optionList);
                session.setAttribute("questionIDList" , questionIDList);

                //Execute Return
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/QuizReview.jsp");
                rd.forward(request, response);
            }

            else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
