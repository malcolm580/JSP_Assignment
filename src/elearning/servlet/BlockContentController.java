package elearning.servlet;


import elearning.bean.Metrial;
import elearning.bean.Module;
import elearning.bean.User;
import elearning.bean.UserModule;
import elearning.db.MaterialDB;
import elearning.db.MetrialUserDB;
import elearning.db.ModuleDB;
import elearning.db.UserModuleDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="BlockContentController", urlPatterns={"/block"})
public class BlockContentController extends HttpServlet {
    private UserModuleDB userModule;
    private MetrialUserDB metrialUserDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userModule = new UserModuleDB(dbUrl, dbUser, dbPassword);
        metrialUserDB = new  MetrialUserDB(dbUrl, dbUser, dbPassword);


    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String metrialID = request.getParameter("materialID");
        String moduleID = request.getParameter("moduleID");

        // forward the request to brands

            try {
                ArrayList<User> userArrayList = userModule.getUser(Integer.parseInt(moduleID));

                if (null != userArrayList) {
                    for (Object bean : userArrayList) {
                        User moduleUser = (User) bean;

                        moduleUser.setBlocked(metrialUserDB.isBlockUser(Integer.parseInt(metrialID), moduleUser.getUserID()));

                    }
                }

                request.setAttribute("userList", userArrayList);


                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/ChooseBlock.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}


