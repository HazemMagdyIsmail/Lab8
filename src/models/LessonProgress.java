/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class LessonProgress {
    private boolean completed;
    private int attemptCount;
    private String lastAccessed;

    public LessonProgress() {
        this.completed = false;
        this.attemptCount = 0;
        this.lastAccessed = java.time.LocalDate.now().toString();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            this.lastAccessed = java.time.LocalDate.now().toString();
        }
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public String getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(String lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public void incrementAttemptCount() {
        this.attemptCount++;
    }
}
