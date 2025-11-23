package models;

import java.util.Date;
import java.util.Map;

public class QuizAttempt {
    private String attemptId;
    private String studentId;
    private int courseId;
    private int lessonId;
    private int quizId;
    private int score;
    private int totalQuestions;
    private Map<Integer, Integer> answers;
    private boolean passed;
    private Date attemptDate;

    public QuizAttempt(String attemptId, String studentId, int courseId, int lessonId, 
                      int quizId, int score, int totalQuestions, Map<Integer, Integer> answers, 
                      boolean passed, Date attemptDate) {
        this.attemptId = attemptId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.lessonId = lessonId;
        this.quizId = quizId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.answers = answers;
        this.passed = passed;
        this.attemptDate = attemptDate;
    }

    public String getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(String attemptId) {
        this.attemptId = attemptId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Map<Integer, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, Integer> answers) {
        this.answers = answers;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Date getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }

    public double getPercentage() {
        return totalQuestions > 0 ? (score * 100.0 / totalQuestions) : 0;
    }
}