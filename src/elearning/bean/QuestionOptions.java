package elearning.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionOptions implements Serializable {
    ArrayList<String> optionList;

    public ArrayList<String> getOptionList() {
        return optionList;
    }

    public void setOptionList(ArrayList<String> optionList) {
        this.optionList = optionList;
    }
}
