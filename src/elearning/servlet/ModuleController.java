package elearning.servlet;


import elearning.bean.Module;
import elearning.db.ModuleDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="BrandController", urlPatterns={"/moduleController"})
public class ModuleController extends HttpServlet {
    private ModuleDB moduleDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        moduleDB = new ModuleDB(dbUrl, dbUser, dbPassword);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String moduleID = request.getParameter("moduleID");

        // forward the request to brands
        if("list".equalsIgnoreCase(action)) {
            try {
                Module module = moduleDB.getModule(moduleID);
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

