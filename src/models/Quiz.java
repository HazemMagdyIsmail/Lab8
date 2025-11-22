package models;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int quizId;
    private String title;
    private List<Question> questions;
    private int passingScore;

    public Quiz(int quizId, String title, List<Question> questions, int passingScore) {
        this.quizId = quizId;
        this.title = title;
        this.questions = questions != null ? questions : new ArrayList<>();
        this.passingScore = passingScore;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(int passingScore) {
        this.passingScore = passingScore;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public boolean isPassed(int score) {
        return score >= passingScore;
    }
}