package elearning.servlet;

import elearning.bean.User;
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

    private UserModuleDB db;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserModuleDB (dbUrl, dbUser, dbPassword);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {

            String action = request.getParameter("action");

            String targetURL;

            if ("list".equalsIgnoreCase(action)){
                HttpSession session = request.getSession();
                User userData = (User) session.getAttribute("userInfo") ;
                ArrayList moduleList = db.getUserModule(userData.getUserID());
//
//                PrintWriter out = response.getWriter();
//                out.println(userData);
//                out.println(userData.getUserID());
//                out.println(moduleList.size());

                session.setAttribute("moduleList", moduleList);
                targetURL = "ReportingMenu.jsp";


                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/" + targetURL);
                rd.forward(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
