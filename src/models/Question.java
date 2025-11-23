package models;

import java.util.List;

public class Question {
    private int questionId;
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;
    private int points;
    private String explanation; // feedback for correct/incorrect answers

    public Question(int questionId, String questionText, List<String> options,
                    int correctAnswerIndex, int points, String explanation) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.points = points;
        this.explanation = explanation;
    }

    // Getters and Setters
    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
    public void setCorrectAnswerIndex(int correctAnswerIndex) { this.correctAnswerIndex = correctAnswerIndex; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    // Core Methods
    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }

    public String getCorrectAnswerText() {
        if (options == null || correctAnswerIndex < 0 || correctAnswerIndex >= options.size()) {
            return "";
        }
        return options.get(correctAnswerIndex);
    }

    public int getOptionCount() {
        return options == null ? 0 : options.size();
    }

    public boolean isValidOptionIndex(int index) {
        return options != null && index >= 0 && index < options.size();
    }
}