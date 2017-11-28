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
import javax.servlet.http.HttpSession;


@WebServlet(name="ModuleController", urlPatterns={"/moduleController"})
public class ModuleController extends HttpServlet {
    private ModuleDB moduleDB;
    private MaterialDB materialDB;
    private UserModuleDB userModule;
    private MetrialUserDB metrialUserDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        moduleDB = new ModuleDB(dbUrl, dbUser, dbPassword);
        materialDB = new MaterialDB(dbUrl, dbUser, dbPassword);
        userModule = new UserModuleDB(dbUrl, dbUser, dbPassword);
        metrialUserDB = new  MetrialUserDB(dbUrl, dbUser, dbPassword);


    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String moduleID = request.getParameter("moduleID");

        // forward the request to brands
        if("list".equalsIgnoreCase(action)) {
            try {

                HttpSession session = request.getSession();

                //Get Current User Data
                User userData = (User) session.getAttribute("userInfo");

                Module module = moduleDB.getModule(moduleID);
                ArrayList<Metrial> metrialArrayList = materialDB.getMaterial(moduleID);

                if (null != metrialArrayList) {

                    for (int i = 0; i < metrialArrayList.size(); i++) {
                        Metrial metrial = (Metrial) metrialArrayList.get(i);

                        if (metrialUserDB.isBlockUser(metrial.getMaterialID(), userData.getUserID()))
                            metrialArrayList.remove(metrial);
                    }

                }

                request.setAttribute("materialList",metrialArrayList);
                request.setAttribute("moduleContent",module);


                RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/ModuleContent.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            PrintWriter out = response.getWriter();
            out.println("NO such action :" + action);
        }
    }
}

