package elearning.db;

import java.io.IOException;
import java.sql.*;

public class UserDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public UserDB( String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName( "com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }


    public void createCustTable()throws Exception {
        Connection cnnct = null;
        Statement stmnt = null;
        try{
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "CREATE TABLE  IF NOT EXISTS USERINFO ("
                    + "id varchar(5) NOT NULL,"
                    + "username varchar(25) NOT NULL,"
                    + "password varchar(25) NOT NULL,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
        } catch(SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isValidUser(String user, String pwd) throws Exception {
        boolean isValid = false;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try{
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM USER WHERE username=? and password=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, user);
            pStmnt.setString(2, pwd);

            ResultSet rs = null;

            rs = pStmnt.executeQuery();

            if (rs.next()) {
                isValid = true;
            }

        } catch(SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return isValid;
    }

    public boolean addUserInfo(String id, String user, String pwd)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO USERINFO VALUES (?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            pStmnt.setString(2, user);
            pStmnt.setString(3, pwd);
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >= 1) {
                isSuccess = true;
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
        return isSuccess;
    }

}
