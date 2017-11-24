package elearning.bean;

import java.io.Serializable;

public class QuizResult implements Serializable {
    int userID, quizID, quizResultID, duration, correctCount;
    String answeringQuestionState_JSON;

    public String getAnsweringQuestionState_JSON() {
        return answeringQuestionState_JSON;
    }

    public void setAnsweringQuestionState_JSON(String answeringQuestionState_JSON) {
        this.answeringQuestionState_JSON = answeringQuestionState_JSON;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getQuizResultID() {
        return quizResultID;
    }

    public void setQuizResultID(int quizResultID) {
        this.quizResultID = quizResultID;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
