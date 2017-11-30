package elearning.servlet;

import elearning.bean.User;
import elearning.db.UserDB;
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

@WebServlet(name = "LoginController", urlPatterns = {"/main"})
public class LoginController extends HttpServlet {

    private UserDB db;
    private UserModuleDB UMdb;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserDB(dbUrl, dbUser, dbPassword);
        UMdb = new UserModuleDB (dbUrl, dbUser, dbPassword);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (!isAuthenticated(request) &&
                !("authenticate".equals(action))) {
            doLogin(request, response);
            return;
        }
        if ("authenticate".equals(action)) {
            try {
                doAuthenticate(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("logout".equals(action)) {
            doLogout(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    private void doAuthenticate(HttpServletRequest request,
                                HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String targetURL;

        //if ("abc".equals(username) && "123".equals(password)){
        User user = db.isValidUser(username, password);
        if (null != user) {
            HttpSession session = request.getSession();
            session.setAttribute("userInfo", user);

            session = request.getSession();
            User userData = (User) session.getAttribute("userInfo") ;
            ArrayList moduleList = UMdb.getUserModule(userData.getUserID());
            session.setAttribute("moduleList", moduleList);

            targetURL = "index.jsp";
        } else {
            targetURL = "loginError.jsp";
        }

            //RequestDispatcher rd;
            //rd = getServletContext().getRequestDispatcher("/" + targetURL);
           // rd.forward(request, response);
        try {
            response.sendRedirect("./"+targetURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isAuthenticated(HttpServletRequest request) {

        boolean result = false;

        HttpSession session = request.getSession();

        if (null != session.getAttribute("userInfo")) {
            result = true;
        }

        return result;
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String targetURL = "login.jsp";

        RequestDispatcher rd;

        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute("userInfo");
            session.invalidate();
        }

        doLogin(request, response);
    }


}
