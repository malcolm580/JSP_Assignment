package elearning.servlet;

import elearning.bean.Quiz;
import elearning.bean.User;
import elearning.db.UserModuleDB;
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

@WebServlet (name = "QuizController" , urlPatterns = {"/quiz"} )
public class QuizController extends HttpServlet {

    private UserQuizDB db;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserQuizDB (dbUrl, dbUser, dbPassword);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {

            String action = request.getParameter("action");

            String targetURL;

            if ("list".equalsIgnoreCase(action)){
                HttpSession session = request.getSession();
                User userData = (User) session.getAttribute("userInfo") ;
                int userID = userData.getUserID();
                ArrayList<Quiz> quizList = db.getUserQuiz(userID);

                session.setAttribute("quizList", quizList);
                targetURL = "quizList.jsp";

                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/" + targetURL);
                rd.forward(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
