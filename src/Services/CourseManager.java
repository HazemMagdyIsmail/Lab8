package Services;

import models.Course;
import java.util.*;

public class CourseManager {
    private int generateCourseId(List<Course> courses) {
        return (int) (Math.random() * 9000) + 1000;
    }

    public Course createCourse(String title, String description, int instructorId) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) {
            courses = new ArrayList<>();
        }

        int newId = generateCourseId(courses);
        Course course = new Course(newId, title, description, instructorId);
        courses.add(course);
        JsonDatabaseManager.writeCourses(courses);
        return course;
    }

    public void updateCourse(int courseId, String newTitle, String newDescription) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return;

        for (Course c : courses) {
            if (c != null && c.getCourseId() == courseId) {
                c.setTitle(newTitle);
                c.setDescription(newDescription);
                break;
            }
        }
        JsonDatabaseManager.writeCourses(courses);
    }

    public void deleteCourse(int courseId) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return;

        courses.removeIf(c -> c != null && c.getCourseId() == courseId);
        JsonDatabaseManager.writeCourses(courses);
    }

    public Course getCourseById(int courseId) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return null;

        for (Course c : courses) {
            if (c != null && c.getCourseId() == courseId) {
                return c;
            }
        }
        return null;
    }

    public List<Course> getCoursesByInstructor(int instructorId) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        List<Course> result = new ArrayList<>();

        if (courses == null) return result;

        for (Course c : courses) {
            if (c != null && c.getInstructorId() == instructorId) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Course> getAllAvailableCourses() {
        List<Course> courses = JsonDatabaseManager.readCourses();
        List<Course> approvedcourses = new ArrayList<>();
        if (courses != null) {
            for (int i = 0; i < courses.size(); i++) {
                Course c = courses.get(i);
                if (c != null && "APPROVED".equalsIgnoreCase(c.getStatus())) {
                    approvedcourses.add(c);
                }
            }
        }
        return approvedcourses;
    }
    
    public List<Course> getPendingCourses() {
        List<Course> courses = JsonDatabaseManager.readCourses();
        List<Course> pendingCourses = new ArrayList<>();
        if (courses != null) {
            for (int i = 0; i < courses.size(); i++) {
                Course c = courses.get(i);
                if (c != null && "PENDING".equalsIgnoreCase(c.getStatus())) {
                    pendingCourses.add(c);
                }
            }
        }
        return pendingCourses;
    }

    public boolean updateCourseStatus(int courseId, String newStatus) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return false;

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c != null && c.getCourseId() == courseId) {
                c.setStatus(newStatus);
                JsonDatabaseManager.writeCourses(courses);
                return true;
            }
        }
        return false;
    }

    public boolean enrollStudentInCourse(int studentId, int courseId) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        if (courses == null) return false;

        for (Course c : courses) {
            if (c != null && c.getCourseId() == courseId) {
                if (c.getStudents() == null) {
                    c.setStudents(new ArrayList<>());
                }

                if (!c.getStudents().contains(studentId)) {
                    c.getStudents().add(studentId);
                    JsonDatabaseManager.writeCourses(courses);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public List<Course> getEnrolledCourses(int studentId) {
        List<Course> courses = JsonDatabaseManager.readCourses();
        List<Course> enrolledCourses = new ArrayList<>();

        if (courses == null) return enrolledCourses;

        for (Course c : courses) {
            if (c != null && c.getStudents() != null && c.getStudents().contains(studentId)) {
                enrolledCourses.add(c);
            }
        }
        return enrolledCourses;
    }
}