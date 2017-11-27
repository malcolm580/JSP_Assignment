package elearning.tag;

import elearning.bean.Question;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class questionTag extends SimpleTagSupport {
    private Question question;

    public Question getQuestion() {
        String output="";
        if(question.getQuestionType().equalsIgnoreCase("Multiple")){

        }
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public void doTag() throws JspException, IOException {
        super.doTag();
    }

}
