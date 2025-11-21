package Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Course;
import java.io.*;
import java.util.*;
import java.lang.reflect.Type;

public class JsonDatabaseManager {
    private static final String COURSES_FILE = "courses.json";
    private static final Gson gson = new Gson();

    public static List<Course> readCourses() {
        File file = new File(COURSES_FILE);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(COURSES_FILE)) {
            Type listType = new TypeToken<List<Course>>() {}.getType();
            List<Course> courses = gson.fromJson(reader, listType);
            return courses == null ? new ArrayList<>() : courses;
        } catch (IOException e) {
            System.err.println("Error reading courses: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error parsing courses JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void writeCourses(List<Course> courses) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        
        try (FileWriter writer = new FileWriter(COURSES_FILE)) {
            gson.toJson(courses, writer);
        } catch (IOException e) {
            System.err.println("Error writing courses: " + e.getMessage());
            e.printStackTrace();
        }
    }
}