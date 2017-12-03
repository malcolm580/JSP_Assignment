package elearning.tag;

import elearning.bean.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class permissionCheck extends SimpleTagSupport {
    User user = null;
    String requiredPermission = null;
    String outputHTML = "";

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(String requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

    public String getOutputHTML() {
        return outputHTML;
    }

    public void setOutputHTML(String outputHTML) {
        this.outputHTML = outputHTML;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        user = (User) pageContext.getSession().getAttribute("userInfo");

        try {
            if (user.getRole().equalsIgnoreCase(requiredPermission)) {
                out.println(outputHTML);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
