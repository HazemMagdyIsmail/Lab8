package database;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import models.Course;
import models.Lesson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CourseJsonDatabase {

    private static final String FILE_NAME = "Courses.JSON";
    private static ArrayList<Course> courseList = new ArrayList<>();

    public static List<Course> getAllCourses() {
        return courseList;
    }

  public static void loadCourses() {
    courseList.clear();
    java.io.File f = new java.io.File(FILE_NAME);
    if (!f.exists()) {
        // create empty JSON array file
        try (java.io.FileWriter writer = new java.io.FileWriter(f)) {
            writer.write("[]");
        } catch (Exception e) {
            System.err.println("Cannot create courses file: " + e.getMessage());
        }
    }

    try (java.io.FileReader reader = new java.io.FileReader(f)) {
        JSONArray jsonCourses = new JSONArray(new JSONTokener(reader));
        // rest of your loading logic...
    } catch (Exception e) {
        System.err.println("Error loading courses: " + e.getMessage());
    }
}


    public static void saveCourses() {
        JSONArray jsonCourses = new JSONArray();
        for (Course course : courseList) {
            JSONObject obj = new JSONObject();
            obj.put("courseId", course.getCourseId());
            obj.put("title", course.getTitle());
            obj.put("description", course.getDescription());
            obj.put("instructorId", course.getInstructorId());
            obj.put("status", course.getStatus());

            JSONArray studentArray = new JSONArray();
            for (Integer sid : course.getStudents()) {
                studentArray.put(sid);
            }
            obj.put("students", studentArray);

            JSONArray lessonArray = new JSONArray();
            for (Lesson lesson : course.getLessons()) {
                JSONObject l = new JSONObject();
                l.put("lessonId", lesson.getLessonId());
                l.put("title", lesson.getTitle());
                l.put("content", lesson.getContent());
                l.put("resources", new JSONArray(lesson.getResources()));
                lessonArray.put(l);
            }
            obj.put("lessons", lessonArray);

            jsonCourses.put(obj);
        }

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(jsonCourses.toString(2));
        } catch (Exception e) {
            System.err.println("Error saving courses: " + e.getMessage());
        }
    }

    public static boolean addCourse(Course course) {
        // always reload before add
        loadCourses();
        for (Course c : courseList) {
            if (c.getCourseId() == course.getCourseId()) {
                return false;
            }
        }
        courseList.add(course);
        saveCourses();
        return true;
    }

    public static Course getCourseById(int courseId) {
        for (Course c : courseList) {
            if (c.getCourseId() == courseId) return c;
        }
        return null;
    }

    public static void removeCourse(int courseId) {
        loadCourses();
        courseList.removeIf(c -> c.getCourseId() == courseId);
        saveCourses();
    }
}
