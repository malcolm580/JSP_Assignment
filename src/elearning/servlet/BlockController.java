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


@WebServlet(name="BlockController", urlPatterns={"/blockUser"})
public class BlockController extends HttpServlet {
    private MetrialUserDB metrialUserDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        metrialUserDB = new  MetrialUserDB(dbUrl, dbUser, dbPassword);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String metrialID = request.getParameter("materialID");
        String moduleID = request.getParameter("moduleID");
        String[] userID = request.getParameterValues("blockList");

        // forward the request to brands

        try {

            metrialUserDB.delRecord(Integer.parseInt(metrialID));

            if (userID != null) {
                for (int i = 0; i < userID.length; i++) {
                    metrialUserDB.addBlock(Integer.parseInt(metrialID), Integer.parseInt(userID[i]));
                }
            }


            response.sendRedirect("./"+ "moduleController?action=list&moduleID=" + moduleID + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


