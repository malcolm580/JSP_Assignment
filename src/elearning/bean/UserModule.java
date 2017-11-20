package elearning.bean;

import java.io.Serializable;

public class UserModule implements Serializable {
    int userID,moduleID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        moduleID = moduleID;
    }
}
