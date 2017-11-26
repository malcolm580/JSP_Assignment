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
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet(name = "AccountManagementController", urlPatterns = {"/accountManagement"})
public class AccountManagementController extends HttpServlet {

    UserDB db;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String targetURL = "";
        User viewUser = null;

        if("list".equalsIgnoreCase(action)){

            ArrayList userList = db.getAllUser();
            HttpSession session = request.getSession();
            session.setAttribute("userList" , userList);

            targetURL = "AccountManagement/UserList.jsp";



        }else if("view".equalsIgnoreCase(action)){

            String userID = request.getParameter("userID");

            ArrayList allRoles = db.getAllRoles();
            viewUser = db.findUserByID(Integer.parseInt(userID));

            HttpSession session = request.getSession();
            session.setAttribute("viewUser" , viewUser);
            session.setAttribute("allRoles" , allRoles);

            targetURL = "AccountManagement/ViewSpecificUser.jsp";



        }else if("edit".equalsIgnoreCase(action)){

            String userID = request.getParameter("userID");
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            String email = request.getParameter("email");
            Boolean edited = false;


            try{
                edited = db.editUserInfoAndRole(Integer.parseInt(userID), userName , password , role , email);
            }catch (Exception ex){
                ex.printStackTrace();
            }

            HttpSession session = request.getSession();
            session.setAttribute("edited" , edited);

            targetURL = "AccountManagement/ViewSpecificUser.jsp";

        }else if ("viewAdd".equalsIgnoreCase(action)){
            ArrayList allRoles = db.getAllRoles();
            HttpSession session = request.getSession();
            session.setAttribute("allRoles" , allRoles);
            targetURL = "AccountManagement/addUser.jsp";

        }else if ("add".equalsIgnoreCase(action)){

        }else if ("delete".equalsIgnoreCase(action)){

        }


        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/" + targetURL);
        rd.forward(request, response);

    }


}