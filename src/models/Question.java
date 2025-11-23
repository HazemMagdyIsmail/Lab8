package models;

import java.util.List;

public class Question {
    private int questionId;
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;
    private int points;

    public Question(int questionId, String questionText, List<String> options, 
               int correctAnswerIndex, int points, String explanation) {
    this.questionId = questionId;
    this.questionText = questionText;
    this.options = options;
    this.correctAnswerIndex = correctAnswerIndex;
    this.points = points;
}


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
    
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isCorrectAnswer(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }
    public String getCorrectAnswerText() {
        if (options == null) return "";
        if (correctAnswerIndex < 0) return "";
        if (correctAnswerIndex >= options.size()) return "";
        return options.get(correctAnswerIndex);
    }

    public int getOptionCount() {
        if (options == null) return 0;
        return options.size();
    }

    public boolean isValidOptionIndex(int index) {
        if (options == null) return false;
        if (index < 0) return false;
        if (index >= options.size()) return false;
        return true;
    }
}
