package elearning.servlet;

import elearning.bean.User;
import elearning.db.UserDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/main"})
public class LoginController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (!isAuthenticated(request) && !("authenticate".equals(action))) {
            doLogin(request, response);
            return;
        }
        if ("authenticate".equals(action)) {
            doAuthenticate(request, response);
        } else if ("logout".equals(action)) {
            doLogout(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    private UserDB userDb;
    public void init(){
        String dburl=this.getServletContext().getInitParameter("dbUser");
        String dbUser=this.getServletContext().getInitParameter("dbPass");
        String dbPassword=this.getServletContext().getInitParameter("dbUrl");
        userDb=new UserDB(dburl,dbUser,dbPassword);
    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String targetURL;
        if (this.userDb.isValidUser(username, password)) {
            HttpSession session = request.getSession(true);
            User bean = new User();
            bean.setUsername(username);
            session.setAttribute("userInfo", bean);
            targetURL = "welcome.jsp";
        } else {
            targetURL = "loginError.jsp";
        }
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        boolean result = false;
        HttpSession session = request.getSession();
        if (session.getAttribute("userInfo") != null) {
            result = true;
        }
        return result;
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetURL = "login.jsp";
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("userInfo");
            session.invalidate();
        }
        doLogin(request, response);
    }
}
