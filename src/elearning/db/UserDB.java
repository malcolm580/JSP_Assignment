package elearning.db;

import elearning.bean.User;

import java.sql.*;

public class UserDB {
    String dburl;
    String dbUser;
    String dbPassword;

    public UserDB(String dburl, String dbUser, String dbPassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

   public boolean isValidUser(String user, String pwd) {
        boolean isValid = false;
        Connection connection = null;
        User userInfo = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = null;
            String preQueryStatement = "SELECT * FROM User WHERE Username=? AND Password=?";

            preparedStatement = connection.prepareStatement(preQueryStatement);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pwd);
            ResultSet resultSet = null;
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public void CreateUserInfoTable() {
    }

    public boolean addUserInfo(String id, String usr, String pwd) {
        return false;
    }

}
