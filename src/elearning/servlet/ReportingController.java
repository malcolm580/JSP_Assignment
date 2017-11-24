package elearning.servlet;

import elearning.bean.Quiz;
import elearning.bean.QuizResult;
import elearning.bean.User;
import elearning.db.*;

import javax.jms.Session;
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

import org.json.*;


@WebServlet(name = "ReportingController", urlPatterns = {"/reportMenu"})
public class ReportingController extends HttpServlet {

    String dbUser;
    String dbPassword;
    String dbUrl;

    @Override
    public void init() throws ServletException {
        dbUser = this.getServletContext().getInitParameter("dbUser");
        dbPassword = this.getServletContext().getInitParameter("dbPassword");
        dbUrl = this.getServletContext().getInitParameter("dbUrl");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            if (request.getSession().isNew() ||
                    null == ((User) request.getSession(false).getAttribute("userInfo"))) {//Check Is authorized
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            PrintWriter out = response.getWriter();

            String action = request.getParameter("action");
            HttpSession session;
            String targetURL = "";

            if ("getModuleList".equalsIgnoreCase(action)) {
                UserModuleDB db = new UserModuleDB(dbUrl, dbUser, dbPassword);

                session = request.getSession();
                User userData = (User) session.getAttribute("userInfo");
                ArrayList moduleList = db.getUserModule(userData.getUserID());
                session.setAttribute("moduleList", moduleList);

                targetURL = "Reporting/ReportingMenu.jsp";
            } else if ("getModuleQuiz".equalsIgnoreCase(action)) {
                QuizDB db = new QuizDB(dbUrl, dbUser, dbPassword);

                session = request.getSession();
                String moduleID = request.getParameter("moduleID");
                ArrayList quizList = db.getModuleQuiz(moduleID);
                session.setAttribute("quizList", quizList);

                targetURL = "Reporting/ModuleQuiz.jsp";
            } else if ("getQuizStudent".equalsIgnoreCase(action)) {
                UserQuizDB db = new UserQuizDB(dbUrl, dbUser, dbPassword);

                session = request.getSession();
                String quizID = request.getParameter("quizID");
                String quizName = request.getParameter("quizName");

                session.setAttribute("quizID", quizID);
                session.setAttribute("quizName", quizName);

                ArrayList quizStudentList = db.getQuizStudentList(quizID);
                session.setAttribute("quizStudentList", quizStudentList);

                targetURL = "Reporting/QuizStudentList.jsp";
            } else if ("getStudentQuizReport".equalsIgnoreCase(action)) {

                QuizResultDB qrdb = new QuizResultDB(dbUrl, dbUser, dbPassword);
                UserDB userDB = new UserDB(dbUrl, dbUser, dbPassword);

                JSONArray nameArray = new JSONArray();
                JSONArray scoreArray = new JSONArray();

                String[] selectStudentID = request.getParameterValues("target");

                if (selectStudentID == null) {

                    targetURL = "Reporting/ReportingError.jsp";

                } else {

                    session = request.getSession();

                    User highestResultUser = new User();
                    QuizResult highestResult = new QuizResult();
                    highestResult.setCorrectCount(0);

                    User lowestResultUser = new User();
                    QuizResult lowestResult = new QuizResult();
                    lowestResult.setCorrectCount(100);

                    for (String studentID : selectStudentID) {

                        User user = userDB.findUserByID(Integer.parseInt(studentID));
                        nameArray.put(user.getUsername());

                        ArrayList<QuizResult> quizResultList = qrdb.getMQuizResult(Integer.parseInt(studentID), Integer.parseInt((String) session.getAttribute("quizID")));
                        QuizResult userHighestResult = quizResultList.get(0);

                        for (QuizResult quizResult : quizResultList) {
                            //Select the highest quiz result for this user
                            if (quizResult.getCorrectCount() > userHighestResult.getCorrectCount()) {
                                userHighestResult = quizResult;
                            }
                            //Set the highest result in all student
                            if(quizResult.getCorrectCount() > highestResult.getCorrectCount()){
                                highestResult = quizResult;
                            }
                            //Set the lowest result in all student
                            if(quizResult.getCorrectCount() < lowestResult.getCorrectCount()){
                                lowestResult = quizResult;
                            }
                        }

                        scoreArray.put(userHighestResult.getCorrectCount());


                    }

                    highestResultUser = userDB.findUserByID(highestResult.getUserID());
                    lowestResultUser = userDB.findUserByID(lowestResult.getUserID());

                    String nameArrayString = nameArray.toString();
                    String scoreArrayString = scoreArray.toString();

                    session.setAttribute("nameArrayString", nameArrayString);
                    session.setAttribute("scoreArrayString", scoreArrayString);
                    session.setAttribute("highestResultUser", highestResultUser);
                    session.setAttribute("lowestResultUser", lowestResultUser);
                    targetURL = "Reporting/QuizStudentReport.jsp";

                }

            }

            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/" + targetURL);
            rd.forward(request, response);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
