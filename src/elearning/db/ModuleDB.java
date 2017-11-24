package elearning.db;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.tools.internal.xjc.model.Model;
import elearning.bean.Module;
import elearning.bean.Quiz;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ModuleDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public ModuleDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName( "com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }


    public Module getModule(String moduleID)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Module module = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From Module WHERE ModuleID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, moduleID);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                module = new Module();
                module.setModuleID(rs.getInt("ModuleID"));
                module.setModuleName(rs.getString("ModuleName"));
            }

            pStmnt.close();
            cnnct.close();
        } catch(SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return module;
    }

}
