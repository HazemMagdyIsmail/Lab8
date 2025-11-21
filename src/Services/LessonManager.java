package Services;

import models.Course;
import models.Lesson;
import java.util.*;

public class LessonManager {

    private int generateLessonId(Course course) {
        if (course == null || course.getLessons() == null) {
            return 1;
        }
        
        List<Lesson> lessons = course.getLessons();
        int maxId = 0;
        for (Lesson l : lessons) {
            if (l != null) {
                maxId = Math.max(maxId, l.getLessonId());
            }
        }
        return maxId + 1;
    }

    public void addLesson(int courseId, String title, String content) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return;

        for (Course c : courses) {
            if (c != null && c.getCourseId() == courseId) {
                if (c.getLessons() == null) {
                    c.setLessons(new ArrayList<>());
                }
                
                int newId = generateLessonId(c);
                Lesson lesson = new Lesson(newId, title, content, new ArrayList<>());
                c.getLessons().add(lesson);
                break;
            }
        }
        JsonDatabaseManager.writeCourses(courses);
    }

    public void updateLesson(int courseId, int lessonId, String newTitle, String newContent) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return;

        for (Course c : courses) {
            if (c != null && c.getCourseId() == courseId && c.getLessons() != null) {
                for (Lesson l : c.getLessons()) {
                    if (l != null && l.getLessonId() == lessonId) {
                        l.setTitle(newTitle);
                        l.setContent(newContent);
                        break;
                    }
                }
            }
        }
        JsonDatabaseManager.writeCourses(courses);
    }

    public void deleteLesson(int courseId, int lessonId) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return;

        for (Course c : courses) {
            if (c != null && c.getCourseId() == courseId && c.getLessons() != null) {
                c.getLessons().removeIf(l -> l != null && l.getLessonId() == lessonId);
            }
        }
        JsonDatabaseManager.writeCourses(courses);
    }

    public List<Lesson> getLessons(int courseId) {
        CourseManager cm = new CourseManager();
        Course c = cm.getCourseById(courseId);

        if (c != null && c.getLessons() != null) {
            return c.getLessons();
        }
        return new ArrayList<>();
    }
}