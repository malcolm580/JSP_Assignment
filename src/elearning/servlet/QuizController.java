package elearning.servlet;

import elearning.bean.Module;
import elearning.bean.Quiz;
import elearning.bean.QuizResult;
import elearning.bean.User;
import elearning.db.QuizDB;
import elearning.db.QuizResultDB;
import elearning.db.UserQuizDB;

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

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userQuizDB = new UserQuizDB(dbUrl, dbUser, dbPassword);
        quizDB = new QuizDB(dbUrl, dbUser, dbPassword);
        quizResultDB=new QuizResultDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            if (null==((User)request.getSession(false).getAttribute("userInfo"))) {//Check Is authorized
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
                int quizID=Integer.parseInt(quizID_String);
                Quiz currentQuiz = userQuizDB.getQuiz(quizID);
                if(currentQuiz==null){
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("./index.jsp?msg=Please%20Select%20A%20Quiz!");
                    rd.forward(request, response);
                    return;
                }
                Module currentModule = quizDB.getParentModule(currentQuiz);
                ArrayList<QuizResult> currentQuizResultList=quizResultDB.getMQuizResult(userID,quizID);

                session.setAttribute("currentModule", currentModule);
                session.setAttribute("currentQuiz", currentQuiz);
                session.setAttribute("currentQuizResultList", currentQuizResultList);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/QuizEnter.jsp");
                rd.forward(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
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
