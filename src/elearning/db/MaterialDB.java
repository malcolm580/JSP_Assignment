package elearning.db;

import elearning.bean.Metrial;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class MaterialDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public MaterialDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }


    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        Class.forName( "com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }


    public ArrayList<Metrial> getMaterial(String moduleID)throws Exception {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Metrial metrial = null;
        ArrayList<Metrial> metrialArrayList = new ArrayList<Metrial>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * From Metrial WHERE ModuleID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, moduleID);

            ResultSet rs = pStmnt.executeQuery();
            while ( rs.next() ){
                metrial = new Metrial();
                metrial.setModuleID(rs.getInt("ModuleID"));
                metrial.setMaterialID(rs.getInt("MaterialID"));
                metrial.setContent(rs.getString("Content"));
                metrialArrayList.add(metrial);
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
        return metrialArrayList;
    }

    public boolean addMaterial(int moduleID, String content, String contentType) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO Metrial(ModuleID, ContentType, Content) VALUES (?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, moduleID);
            pStmnt.setString(2, contentType);
            pStmnt.setString(3, content);
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
