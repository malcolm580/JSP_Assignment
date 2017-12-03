package elearning.servlet;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elearning.db.MaterialDB;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UploadController", urlPatterns = {"/upload"})
public class UploadController extends HttpServlet {

    private MaterialDB MLdb;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        MLdb = new MaterialDB(dbUrl, dbUser, dbPassword);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String moduleID = "";
        String[] part = new String[2];
        String name = "";
        int index = 0;

        if(ServletFileUpload.isMultipartContent(request)){
            try {

                List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                for(FileItem item : multiparts){
                    if(item.isFormField()){
                        if ("module".equalsIgnoreCase(item.getFieldName()))
                            moduleID = item.getString();
                    }

                }

                String path = (new File(".")).getAbsolutePath();

                File theDirL = new File(path + File.separator + ".." + File.separator + "material");

                if (!theDirL.exists()) {

                    try {
                        theDirL.mkdir();
                    } catch (SecurityException se) {
                        se.printStackTrace();
                    }
                }

                File theDir = new File(path + File.separator + ".." + File.separator + "material" + File.separator  + moduleID);

                if (!theDir.exists()) {

                    try {
                        theDir.mkdir();
                    } catch (SecurityException se) {
                        se.printStackTrace();
                    }
                }




                for(FileItem item : multiparts){


                    if(!item.isFormField()){

                        String savePath = path + File.separator + ".." + File.separator + "material" + File.separator  + moduleID;
                        name = new File(item.getName()).getName();

                        item.write( new File(savePath + File.separator + name));

                    }

                }
                index = name.indexOf(".");
                part[0] = name.substring(0,index);
                part[1] = name.substring(index+1);
                MLdb.addMaterial(Integer.parseInt(moduleID), part[0], part[1]);

            } catch (Exception ex) {
                System.out.println(name);
                ex.printStackTrace();
            }
        }
        response.sendRedirect("./"+ "moduleController?action=list&moduleID=" + moduleID + "");
        //request.getRequestDispatcher("moduleController?action=list&moduleID=\"" + moduleID + "\"").forward(request, response);

    }


}