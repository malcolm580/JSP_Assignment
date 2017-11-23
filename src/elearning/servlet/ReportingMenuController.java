package elearning.servlet;

import elearning.bean.User;
import elearning.db.UserDB;
import elearning.db.UserModuleDB;

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

@WebServlet (name = "ReportingMenuController" , urlPatterns = {"/reportMenu"} )
public class ReportingMenuController extends HttpServlet {

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

            String action = request.getParameter("action");

            String targetURL = "";

            if ("getModuleList".equalsIgnoreCase(action)){
                UserModuleDB db = new UserModuleDB (dbUrl, dbUser, dbPassword);

                HttpSession session = request.getSession();
                User userData = (User) session.getAttribute("userInfo") ;
                ArrayList moduleList = db.getUserModule(userData.getUserID());
                session.setAttribute("moduleList", moduleList);

                targetURL = "ReportingMenu.jsp";
            }else if("getModuleQuiz".equalsIgnoreCase(action)){

            }

            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/" + targetURL);
            rd.forward(request, response);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
