package elearning.bean;

import java.io.Serializable;

public class Question implements Serializable {
    private int quizID,questionID;
    private String questionType,question,correctAnswer,options_JSON;

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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getOptions_JSON() {
        return options_JSON;
    }

    public void setOptions_JSON(String options_JSON) {
        this.options_JSON = options_JSON;
    }
}
