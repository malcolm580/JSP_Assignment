package elearning.servlet;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UploadController", urlPatterns = {"/upload"})
public class UploadController extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String moduleID = request.getParameter("module");

        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item : multiparts){

                    String path = (new File(".")).getAbsolutePath();

                    if(!item.isFormField()){

                        File theDir = new File(path + File.separator + ".." + File.separator + "material");

                        if (!theDir.exists()) {

                            try{
                                theDir.mkdir();
                            }
                            catch(SecurityException se){
                                //handle it
                            }

                        }
                        String savePath = path + File.separator + ".." + File.separator + "material";
                        String name = new File(item.getName()).getName();

                        item.write( new File(savePath + File.separator + name));

                    }
                }

            } catch (Exception ex) {

            }
        }
        response.sendRedirect("./"+ "moduleController?action=list&moduleID=\"" + moduleID + "\"");
        //request.getRequestDispatcher("moduleController?action=list&moduleID=\"" + moduleID + "\"").forward(request, response);

    }


}