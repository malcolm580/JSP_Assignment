package elearning.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private int quizID, questionID, correctOptionID;
    private String questionType, question;
    private ArrayList<QuestionOption> questionOptionArrayList;

    public ArrayList<QuestionOption> getQuestionOptionArrayList() {
        return questionOptionArrayList;
    }

    public void setQuestionOptionArrayList(ArrayList<QuestionOption> questionOptionArrayList) {
        this.questionOptionArrayList = questionOptionArrayList;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCorrectOptionID() {
        return correctOptionID;
    }

    public void setCorrectOptionID(int correctOptionID) {
        this.correctOptionID = correctOptionID;
    }

}
