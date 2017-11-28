package elearning.db;

import elearning.bean.Module;
import elearning.bean.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class MetrialUserDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public MetrialUserDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName( "com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }


    public boolean isBlockUser(int metrialID ,int userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean block = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select isBlackListed From MetrialUser WHERE  MetrialModuleID = ? AND UserID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, metrialID);
            pStmnt.setInt(2, userID);

            ResultSet rs = pStmnt.executeQuery();
            while( rs.next() ){
                if (rs.getString("isBlackListed").equals("1"))
                    block = true;
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
        return block;
    }

    public boolean delRecord(int metrialID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            //1. get Connection
            cnnct = getConnection();

            String preQueryStatement = "DELETE FROM MetrialUser WHERE MetrialModuleID = ?";
            //2. get the prepare Statement
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            //3. update the placeholder with id
            pStmnt.setInt(1, metrialID);

            //4. execute the query and assign to the result
            int row = pStmnt.executeUpdate();

            pStmnt.close();
            cnnct.close();

            if(row > 0) {
                return true;
            } else {
                return false;
            }

        } catch(SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addBlock(int metrialID ,int userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO MetrialUser VALUES (?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, metrialID);
            pStmnt.setInt(2, userID);
            pStmnt.setString(3,"1");
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }


}