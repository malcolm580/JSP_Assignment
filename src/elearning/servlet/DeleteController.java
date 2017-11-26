package elearning.servlet;

        import elearning.db.MaterialDB;

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

    private MaterialDB MLdb;

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        MLdb = new MaterialDB(dbUrl, dbUser, dbPassword);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String file = request.getParameter("file");
        String moduleID = request.getParameter("moduleID");
        String materialID = request.getParameter("materialID");

        String path = (new File(".")).getAbsolutePath();
        String savePath = path + File.separator + ".." + File.separator + "material" + File.separator  + moduleID+File.separator + file;

        File f = new File(savePath);

        if (f.delete()){
            MLdb.delMaterial(Integer.parseInt(materialID));
        }


        response.sendRedirect("./"+ "moduleController?action=list&moduleID=" + moduleID + "");
        out.close();
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
