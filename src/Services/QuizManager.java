package Services;

import models.*;
import java.util.*;

public class QuizManager {
    private CourseManager courseManager;
    private UserManager userManager;

    public QuizManager() {
        this.courseManager = new CourseManager();
        this.userManager = new UserManager();
    }

    public void addQuizToLesson(int courseId, int lessonId, Quiz quiz) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return;

        for (Course course : courses) {
            if (course.getCourseId() == courseId && course.getLessons() != null) {
                for (Lesson lesson : course.getLessons()) {
                    if (lesson.getLessonId() == lessonId) {
                        lesson.setQuiz(quiz);
                        JsonDatabaseManager.writeCourses(courses);
                        return;
                    }
                }
            }
        }
    }

    public Quiz getQuizForLesson(int courseId, int lessonId) {
        Course course = courseManager.getCourseById(courseId);
        if (course == null || course.getLessons() == null) {
            return null;
        }

        for (Lesson lesson : course.getLessons()) {
            if (lesson.getLessonId() == lessonId) {
                return lesson.getQuiz();
            }
        }
        return null;
    }

    public QuizAttempt submitQuizAttempt(String studentId, int courseId, int lessonId, 
                                        int quizId, Map<Integer, Integer> answers) {
        Quiz quiz = getQuizForLesson(courseId, lessonId);
        if (quiz == null) {
            return null;
        }

        int score = 0;
        List<Question> questions = quiz.getQuestions();
        
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            Integer studentAnswer = answers.get(question.getQuestionId());
            if (studentAnswer != null && question.isCorrectAnswer(studentAnswer)) {
                score++;
            }
        }

        boolean passed = quiz.isPassed(score);
        
        QuizAttempt attempt = new QuizAttempt(
            UUID.randomUUID().toString(),
            studentId,
            courseId,
            lessonId,
            quizId,
            score,
            questions.size(),
            answers,
            passed,
            new Date()
        );

        User user = userManager.getUserById(studentId);
        if (user instanceof Student) {
            Student student = (Student) user;
            student.addQuizAttempt(attempt);
            
            if (passed) {
                student.getProgress().put(lessonId + "", true);
            }
            
            userManager.updateUser(student);
        }

        return attempt;
    }

    public List<QuizAttempt> getStudentQuizAttempts(String studentId, int courseId) {
        User user = userManager.getUserById(studentId);
        if (!(user instanceof Student)) {
            return new ArrayList<>();
        }

        Student student = (Student) user;
        List<QuizAttempt> courseAttempts = new ArrayList<>();
        
        if (student.getQuizAttempts() != null) {
            for (QuizAttempt attempt : student.getQuizAttempts()) {
                if (attempt.getCourseId() == courseId) {
                    courseAttempts.add(attempt);
                }
            }
        }
        
        return courseAttempts;
    }

    public boolean hasPassedQuiz(String studentId, int courseId, int lessonId) {
        List<QuizAttempt> attempts = getStudentQuizAttempts(studentId, courseId);
        
        for (QuizAttempt attempt : attempts) {
            if (attempt.getLessonId() == lessonId && attempt.isPassed()) {
                return true;
            }
        }
        
        return false;
    }
}