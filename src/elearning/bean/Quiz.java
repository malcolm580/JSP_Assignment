package elearning.bean;

import java.io.Serializable;

public class Quiz implements Serializable {
    int quizID, moduleID, attemptLimit, timeLimit, totalQuestion;
    String quizName;

    public int getAttemptLimit() {
        return attemptLimit;
    }

    public void setAttemptLimit(int attemptLimit) {
        this.attemptLimit = attemptLimit;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizID=" + quizID +
                ", moduleID=" + moduleID +
                ", attemptLimit=" + attemptLimit +
                ", timeLimit=" + timeLimit +
                ", totalQuestion=" + totalQuestion +
                ", quizName='" + quizName + '\'' +
                '}';
    }
}
