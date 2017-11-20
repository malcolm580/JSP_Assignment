package elearning.bean;

import java.io.Serializable;

public class Metrial implements Serializable {
    int moduleID;
    String contentType,content,blackListId_JSON;

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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
