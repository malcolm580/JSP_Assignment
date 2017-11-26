package elearning.db;

import elearning.bean.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

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
            String preQueryStatement = "INSERT INTO User VALUES (?,?,?)";
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
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("UserName"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
                user.setEmail(rs.getString("Email"));
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

    public boolean editUserInfo(int id, String username, String password , String email) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE User " +
                    "SET username = ?, password = ? , email = ? " +
                    "WHERE UserID = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, username);
            pStmnt.setString(2, password);
            pStmnt.setString(3, email);
            pStmnt.setInt(4, id);

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

    public boolean editUserInfoAndRole(int id, String username, String password , String email , String role) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE User " +
                    "SET username = ?, password = ? , email = ? , role = ? " +
                    "WHERE UserID = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, username);
            pStmnt.setString(2, password);
            pStmnt.setString(3, "'" + email + "'");
            pStmnt.setString(4, role);
            pStmnt.setInt(5, id);

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

    public ArrayList<User> getAllUser() {
        User user = null;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<User> userList = new ArrayList<User>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From User";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setRole(rs.getString("Role"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
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

    public ArrayList<User> getAllRoles() {
        User user = null;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<User> userList = new ArrayList<User>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select DISTINCT Role From User";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setRole(rs.getString("Role"));
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
