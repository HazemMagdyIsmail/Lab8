/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private List<String> enrolledCourses;
    private Map<String, Object> progress;
    private List<QuizAttempt> quizAttempts;
    private Map<String, Map<String, Boolean>> lessonCompletion;
    private Map<String, Map<String, QuizAttempt>> courseQuizAttempts;

    public Student(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "Student");
        this.enrolledCourses = new ArrayList<>();
        this.progress = new HashMap<>();
        this.quizAttempts = new ArrayList<>();
        this.lessonCompletion = new HashMap<>();
        this.courseQuizAttempts = new HashMap<>();
    }

    public Student(String userId, String username, String email, String passwordHash, 
                   List<String> enrolledCourses, Map<String, Object> progress) {
        super(userId, username, email, passwordHash, "Student");
        this.enrolledCourses = enrolledCourses != null ? enrolledCourses : new ArrayList<>();
        this.progress = progress != null ? progress : new HashMap<>();
        this.quizAttempts = new ArrayList<>();
        this.lessonCompletion = new HashMap<>();
        this.courseQuizAttempts = new HashMap<>();
    }

    @Override
    public String getDetails() {
        return "Student: " + username + " (" + email + ") - Enrolled in " + 
               enrolledCourses.size() + " courses";
    }

    public void enrollInCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
            lessonCompletion.put(courseId, new HashMap<>());
            courseQuizAttempts.put(courseId, new HashMap<>());
        }
    }

    public void markLessonCompleted(int courseId, String lessonId) {
        Map<String, Boolean> courseLessons = lessonCompletion.get(courseId);
        if (courseLessons != null) {
            courseLessons.put(lessonId, true);
        }
    }

    public boolean isLessonCompleted(String courseId, String lessonId) {
        Map<String, Boolean> courseLessons = lessonCompletion.get(courseId);
        return courseLessons != null && Boolean.TRUE.equals(courseLessons.get(lessonId));
    }

    public void addQuizAttempt(int courseId, QuizAttempt attempt) {
        if (this.quizAttempts == null) {
            this.quizAttempts = new ArrayList<>();
        }
        this.quizAttempts.add(attempt);
        
        Map<String, QuizAttempt> courseAttempts = courseQuizAttempts.get(courseId);
        if (courseAttempts != null) {
            courseAttempts.put(String.valueOf(attempt.getQuizId()), attempt);
        }
        
        if (attempt.isPassed()) {
            markLessonCompleted(courseId, String.valueOf(attempt.getLessonId()));
        }
    }

    public List<QuizAttempt> getQuizAttemptsForCourse(String courseId) {
        List<QuizAttempt> courseAttempts = new ArrayList<>();
        for (int i = 0; i < quizAttempts.size(); i++) {
            QuizAttempt attempt = quizAttempts.get(i);
            if (String.valueOf(attempt.getCourseId()).equals(courseId)) {
                courseAttempts.add(attempt);
            }
        }
        return courseAttempts;
    }

    public boolean canAccessNextLesson(String courseId, String currentLessonId) {
        return isLessonCompleted(courseId, currentLessonId);
    }

    public boolean isCourseCompleted(String courseId, int totalLessonsInCourse) {
        Map<String, Boolean> courseLessons = lessonCompletion.get(courseId);
        if (courseLessons == null) return false;
        
        int completedLessons = 0;
        Object[] lessonIds = courseLessons.keySet().toArray();
        for (int i = 0; i < lessonIds.length; i++) {
            String lessonId = (String) lessonIds[i];
            Boolean completed = courseLessons.get(lessonId);
            if (completed != null && completed == true) {
                completedLessons++;
            }
        }
        return completedLessons >= totalLessonsInCourse;
    }

    public double getCourseCompletionPercentage(String courseId, int totalLessonsInCourse) {
        Map<String, Boolean> courseLessons = lessonCompletion.get(courseId);
        if (courseLessons == null || totalLessonsInCourse == 0) return 0.0;
        
        int completedLessons = 0;
        Object[] lessonIds = courseLessons.keySet().toArray();
        for (int i = 0; i < lessonIds.length; i++) {
            String lessonId = (String) lessonIds[i];
            Boolean completed = courseLessons.get(lessonId);
            if (completed != null && completed == true) {
                completedLessons++;
            }
        }
        return (completedLessons * 100.0) / totalLessonsInCourse;
    }

    public void updateProgress(String key, Object value) {
        progress.put(key, value);
    }

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public Map<String, Object> getProgress() {
        return progress;
    }

    public void setProgress(Map<String, Object> progress) {
        this.progress = progress;
    }

    public List<QuizAttempt> getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    public Map<String, Map<String, Boolean>> getLessonCompletion() {
        return lessonCompletion;
    }

    public void setLessonCompletion(Map<String, Map<String, Boolean>> lessonCompletion) {
        this.lessonCompletion = lessonCompletion;
    }

    public Map<String, Map<String, QuizAttempt>> getCourseQuizAttempts() {
        return courseQuizAttempts;
    }

    public void setCourseQuizAttempts(Map<String, Map<String, QuizAttempt>> courseQuizAttempts) {
        this.courseQuizAttempts = courseQuizAttempts;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", enrolledCourses=" + enrolledCourses.size() +
                '}';
    }
}