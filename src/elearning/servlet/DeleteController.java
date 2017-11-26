package elearning.servlet;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.PrintWriter;

        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteController", urlPatterns = {"/delete"})
public class DeleteController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String file = request.getParameter("file");
        String moduleID = request.getParameter("moduleID");

        String path = (new File(".")).getAbsolutePath();
        String savePath = path + File.separator + ".." + File.separator + "material" + File.separator  + moduleID+File.separator + file;

        File f = new File(savePath);
        f.delete();

        response.sendRedirect("./"+ "moduleController?action=list&moduleID=" + moduleID + "");
        out.close();
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
