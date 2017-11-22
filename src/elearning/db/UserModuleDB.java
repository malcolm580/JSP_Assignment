package elearning.db;

import elearning.bean.Module;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class UserModuleDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public UserModuleDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName( "com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }


    public ArrayList getUserModule(int id)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Module module = null;
        ArrayList moduleList = new ArrayList();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select MO.ModuleName  From UserModule UM , MODULE MO WHERE UM.ModuleID = MO.ModuleID AND UserID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);

            ResultSet rs = pStmnt.executeQuery();
            if( rs.next() ){
                module = new Module();
                module.setModuleName(rs.getString("ModuleName"));
                moduleList.add(module);
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
        return moduleList;
    }

}
