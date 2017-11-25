package elearning.bean;

import java.io.Serializable;

public class Metrial implements Serializable {
    int moduleID, MaterialID;
    String content,blackListId_JSON, ContentType;

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getMaterialID() {
        return MaterialID;
    }

    public void setMaterialID(int materialID) {
        MaterialID = materialID;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBlackListId_JSON() {
        return blackListId_JSON;
    }

    public void setBlackListId_JSON(String blackListId_JSON) {
        this.blackListId_JSON = blackListId_JSON;
    }
}
