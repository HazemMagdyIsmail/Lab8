package models;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int quizId;
    private String title;
    private List<Question> questions;
    private int passingScore;
    private int timeLimit;

    public Quiz(int quizId, String title, List<Question> questions, int passingScore) {
        this.quizId = quizId;
        this.title = title;
        this.questions = questions != null ? questions : new ArrayList<>();
        this.passingScore = passingScore;
        this.timeLimit = 30;
    }

    public Quiz(int quizId, String title, List<Question> questions, int passingScore, int timeLimit) {
        this.quizId = quizId;
        this.title = title;
        this.questions = questions != null ? questions : new ArrayList<>();
        this.passingScore = passingScore;
        this.timeLimit = timeLimit;
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

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public int getTotalPoints() {
        int total = 0;
        for (Question question : questions) {
            total += question.getPoints();
        }
        return total;
    }

    public boolean isPassed(int score) {
        return score >= passingScore;
    }

    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        if (questions != null) {
            questions.remove(question);
        }
    }

    public Question getQuestion(int index) {
        if (questions != null && index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }

    public int calculateScore(List<Integer> userAnswers) {
        if (userAnswers == null || userAnswers.size() != questions.size()) {
            return 0;
        }

        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            if (question.isCorrect(userAnswers.get(i))) {
                score += question.getPoints();
            }
        }
        return score;
    }

    public boolean isTimeLimitEnabled() {
        return timeLimit > 0;
    }

    public String getFormattedTimeLimit() {
        if (timeLimit < 60) {
            return timeLimit + " minutes";
        } else {
            int hours = timeLimit / 60;
            int minutes = timeLimit % 60;
            if (minutes == 0) {
                return hours + " hour" + (hours > 1 ? "s" : "");
            } else {
                return hours + " hour" + (hours > 1 ? "s" : "") + " " + minutes + " minutes";
            }
        }
    }
}