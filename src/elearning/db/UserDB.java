package elearning.db;

import elearning.bean.User;

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


    public void createCustTable() {
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
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public User isValidUser(String user, String pwd)  {
        User userBean=null;
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
                userBean=new User();
                userBean.setUserID(rs.getInt("UserID"));
                userBean.setUsername(rs.getString("Username"));
                userBean.setRole(rs.getString("Role"));
                userBean.setEmail(rs.getString("Email"));
                userBean.setPassword(rs.getString("Password"));
            }

        } catch(SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return userBean;
    }

    public boolean addUserInfo(String id, String user, String pwd) {
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
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }

    public User findUserByID(int id) {

        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        User user = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From User Where UserID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);

            ResultSet rs = null;

            rs = pStmnt.executeQuery();

            if(rs.next()) {
                user = new User();
                user.setUsername(rs.getString("UserName"));
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
        return user;
    }

    public boolean editUserInfo(String id, String user, String pwd) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE User " +
                    "SET column1 = value1, column2 = value2 " +
                    "WHERE UserID = ?;";
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
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }

}
