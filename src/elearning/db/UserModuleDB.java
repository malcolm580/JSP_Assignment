package elearning.db;

import elearning.bean.Module;
import elearning.bean.User;

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


    public ArrayList<Module> getUserModule(int userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Module module = null;
        ArrayList<Module> moduleList = new ArrayList<Module>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select MO.ModuleName , MO.ModuleID  " +
                    "From UserModule UM , MODULE MO " +
                    "WHERE UM.ModuleID = MO.ModuleID AND UserID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userID);

            ResultSet rs = pStmnt.executeQuery();
            while( rs.next() ){
                module = new Module();
                module.setModuleID(rs.getInt("ModuleID"));
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
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return moduleList;
    }

    public ArrayList<User> getUser(int moduleID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        User user = null;
        ArrayList<User> userList = new ArrayList<User>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select UM.UserID, Username From UserModule UM, User U  WHERE UM.UserID = U.UserID AND ModuleID = ? AND Role = 'Student' ORDER BY UserID ASC";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, moduleID);

            ResultSet rs = pStmnt.executeQuery();
            while( rs.next() ){
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                userList.add(user);
            }

            pStmnt.close();
            cnnct.close();
        } catch(SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return userList;
    }




}