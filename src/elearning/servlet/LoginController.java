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

@WebServlet(name = "LoginController", urlPatterns = {"/main"} )
public class LoginController extends HttpServlet {

    private UserDB db;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserDB (dbUrl, dbUser, dbPassword);

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
                                HttpServletResponse response) throws Exception {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String targetURL;

        //if ("abc".equals(username) && "123".equals(password)){
        if(db.isValidUser(username,password)){
            HttpSession session = request.getSession(true);
            User bean = new User();
            bean.setUsername(username);
            session.setAttribute("userInfo", bean);
            targetURL = "index.jsp";
        }else {
            targetURL = "loginError.jsp";
        }

        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private boolean isAuthenticated(HttpServletRequest request){

        boolean result = false;

        HttpSession session = request.getSession(false);

        if (session.getAttribute("userInfo") != null){
            result = true;
        }

        return result;
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String targetURL = "login.jsp";

        RequestDispatcher rd;

        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        HttpSession session = request.getSession(false);

        if (session != null){
            session.removeAttribute("userInfo");
            session.invalidate();
        }

        doLogin(request, response);
    }


}