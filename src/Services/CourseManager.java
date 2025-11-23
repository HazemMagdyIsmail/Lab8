package Services;

import database.CourseJsonDatabase;
import models.Course;

import java.util.List;

public class CourseManager {

    // Generate a new integer course ID based on existing courses
    private int generateCourseId(List<Course> courses) {
        // Find max existing courseId, then +1
        int max = 0;
        for (Course c : courses) {
            if (c.getCourseId() > max) {
                max = c.getCourseId();
            }
        }
        return max + 1;
    }

    /**
     * Create a new course.
     * @param title     Title of the course.
     * @param description Description of the course.
     * @param instructorId  The instructor's userId (String).
     * @return The created Course object, or null if failed.
     */
    public Course createCourse(String title, String description, String instructorId) {
        // Load existing courses
        CourseJsonDatabase.loadCourses();
        List<Course> courses = CourseJsonDatabase.getAllCourses();

        int newId = generateCourseId(courses);
        Course course = new Course(newId, title, description, instructorId);
        course.setStudents(new java.util.ArrayList<>());
        course.setLessons(new java.util.ArrayList<>());
        course.setStatus("PENDING");

        boolean added = CourseJsonDatabase.addCourse(course);
        if (!added) {
            return null;
        }
        return course;
    }

    /**
     * Update title and description of a course.
     */
    public boolean updateCourse(int courseId, String newTitle, String newDescription) {
        CourseJsonDatabase.loadCourses();
        Course c = CourseJsonDatabase.getCourseById(courseId);
        if (c == null) {
            return false;
        }
        c.setTitle(newTitle);
        c.setDescription(newDescription);
        CourseJsonDatabase.saveCourses();
        return true;
    }

    /**
     * Delete a course by its ID.
     */
    public boolean deleteCourse(int courseId) {
        CourseJsonDatabase.loadCourses();
        Course c = CourseJsonDatabase.getCourseById(courseId);
        if (c == null) {
            return false;
        }
        CourseJsonDatabase.removeCourse(courseId);
        return true;
    }

    /**
     * Get a course by ID.
     */
    public Course getCourseById(int courseId) {
        CourseJsonDatabase.loadCourses();
        return CourseJsonDatabase.getCourseById(courseId);
    }

    /**
     * Get all courses created by a specific instructor.
     * @param instructorId The instructor's userId (String).
     */
    public List<Course> getCoursesByInstructor(String instructorId) {
        CourseJsonDatabase.loadCourses();
        List<Course> all = CourseJsonDatabase.getAllCourses();
        java.util.List<Course> result = new java.util.ArrayList<>();
        for (Course c : all) {
            if (instructorId.equals(c.getInstructorId())) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Get all approved / available courses.
     */
    public List<Course> getAllAvailableCourses() {
        CourseJsonDatabase.loadCourses();
        List<Course> all = CourseJsonDatabase.getAllCourses();
        java.util.List<Course> approved = new java.util.ArrayList<>();
        for (Course c : all) {
            if ("APPROVED".equalsIgnoreCase(c.getStatus())) {
                approved.add(c);
            }
        }
        return approved;
    }

    /**
     * Get all courses in pending status.
     */
    public List<Course> getPendingCourses() {
        CourseJsonDatabase.loadCourses();
        List<Course> all = CourseJsonDatabase.getAllCourses();
        java.util.List<Course> pending = new java.util.ArrayList<>();
        for (Course c : all) {
            if ("PENDING".equalsIgnoreCase(c.getStatus())) {
                pending.add(c);
            }
        }
        return pending;
    }

    /**
     * Update the status of a course.
     */
    public boolean updateCourseStatus(int courseId, String newStatus) {
        CourseJsonDatabase.loadCourses();
        Course c = CourseJsonDatabase.getCourseById(courseId);
        if (c == null) {
            return false;
        }
        c.setStatus(newStatus);
        CourseJsonDatabase.saveCourses();
        return true;
    }

    /**
     * Enroll a student in a course.
     * @param studentId String id of student user.
     * @param courseId course integer id.
     */
    public boolean enrollStudentInCourse(String studentId, int courseId) {
        CourseJsonDatabase.loadCourses();
        Course c = CourseJsonDatabase.getCourseById(courseId);
        if (c == null) return false;

        List<String> students = c.getStudentsAsString(); // assume you change Course.getStudents to List<String>
        if (students == null) {
            students = new java.util.ArrayList<>();
            c.setStudentsAsString(students);
        }

        if (!students.contains(studentId)) {
            students.add(studentId);
            CourseJsonDatabase.saveCourses();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get courses in which a student is enrolled.
     * @param studentId string user ID.
     */
    public List<Course> getEnrolledCourses(String studentId) {
        CourseJsonDatabase.loadCourses();
        List<Course> all = CourseJsonDatabase.getAllCourses();
        java.util.List<Course> enrolled = new java.util.ArrayList<>();
        for (Course c : all) {
            List<String> students = c.getStudentsAsString();
            if (students != null && students.contains(studentId)) {
                enrolled.add(c);
            }
        }
        return enrolled;
    }
}
 
