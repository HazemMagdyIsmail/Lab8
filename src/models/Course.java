package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {
    private int courseId;
    private String title;
    private String description;
    private int instructorId;
    private List<Integer> students;
    private List<Lesson> lessons;
    private String status;
    private Map<String, Object> analyticsData;

    public Course(int courseId, String title, String description, int instructorId){
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.students = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.status = "PENDING";
        this.analyticsData = new HashMap<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public List<Integer> getStudents() {
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getAnalyticsData() {
        return analyticsData;
    }

    public void setAnalyticsData(Map<String, Object> analyticsData) {
        this.analyticsData = analyticsData;
    }
    
     public String toString(){
     return title + " (ID: " + courseId + ")";
 }
}
