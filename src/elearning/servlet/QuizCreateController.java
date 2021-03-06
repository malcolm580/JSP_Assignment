package elearning.servlet;

import elearning.bean.Quiz;
import elearning.bean.User;
import elearning.db.QuizDB;
import elearning.db.UserQuizDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "QuizCreateController", urlPatterns = {"/quiz/create"})
public class QuizCreateController extends HttpServlet {

    private QuizDB quizDB;
    private UserQuizDB userQuizDB;

    private static boolean isInteger(String s) {
        if (s == null || s.length() <= 0) {
            return false;
        }
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        userQuizDB = new UserQuizDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            if (request.getSession().isNew() ||
                    null == request.getSession(false).getAttribute("userInfo")) {//Check Is authorized
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            String action = request.getParameter("action");

            String targetURL;
            if ("create".equalsIgnoreCase(action)) {
                if (!checkPermission(request, response)) {//Abort when no permission
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");
                int userID = userData.getUserID();

                Quiz quiz = new Quiz();
                String ModuleID_String = request.getParameter("ModuleID");
                String QuizName = request.getParameter("QuizName");
                String AttemptLimit_String = request.getParameter("AttemptLimit");
                String TimeLimit_String = request.getParameter("TimeLimit");
                String TotalQuestion_String = request.getParameter("TotalQuestion");
                int ModuleID;
                int AttemptLimit;
                int TimeLimit;
                int TotalQuestion;
                try {
                    ModuleID = Integer.parseInt(ModuleID_String);
                    AttemptLimit = Integer.parseInt(AttemptLimit_String);
                    TimeLimit = Integer.parseInt(TimeLimit_String);
                    TotalQuestion = Integer.parseInt(TotalQuestion_String);
                } catch (NullPointerException | NumberFormatException ex) {
                    targetURL = "quiz?action=QuizManagement&msg=Input%20Invalidate%20,Please%20Input again";
                    //Execute Return
                    response.sendRedirect("../" + targetURL);
                    return;
                }
                quiz.setModuleID(ModuleID);
                quiz.setQuizName(QuizName);
                quiz.setAttemptLimit(AttemptLimit);
                quiz.setTimeLimit(TimeLimit);
                quiz.setTotalQuestion(TotalQuestion);

                quizDB.addQuiz(quiz);

                //Add current user to new Quiz (FKY)
                int newQuizid = quizDB.getLastRecordID();

//                PrintWriter out = response.getWriter();
//                out.print(newQuizid + " "  +userID);

                userQuizDB.addRecord(Integer.toString(newQuizid), Integer.toString(userID));

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
        return ("Admin".equalsIgnoreCase(userData.getRole())) || ("teacher".equalsIgnoreCase(userData.getRole()));
    }


}
