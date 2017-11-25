package elearning.tag;

import elearning.bean.Quiz;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.ArrayList;

public class quizEnterTag extends SimpleTagSupport {
    ArrayList<Quiz> quizList;

    public ArrayList<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(ArrayList<Quiz> quizList) {
        this.quizList = quizList;
    }

    @Override
    public void doTag() throws JspException, IOException {
        super.doTag();
    }

}
