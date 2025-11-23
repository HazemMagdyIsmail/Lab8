package frontEnd;

import database.CourseJsonDatabase;
import models.Course;
import models.Instructor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InstructorDashboardFrame extends JFrame {

    private Instructor instructor;
    private DefaultListModel<Course> courseModel;
    private JList<Course> courseList;
    private JButton btnAdd, btnEdit, btnDelete, btnManage;

    public InstructorDashboardFrame(Instructor instructor) {
        this.instructor = instructor;

        setTitle("Instructor Dashboard - " + instructor.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        initComponents();
        loadCourses();
    }

    private void initComponents() {
        courseModel = new DefaultListModel<>();
        courseList = new JList<>(courseModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(courseList);

        btnAdd = new JButton("Add Course");
        btnEdit = new JButton("Edit Course");
        btnDelete = new JButton("Delete Course");
        btnManage = new JButton("Manage Lessons");

        btnAdd.addActionListener(e -> onAddCourse());
        btnEdit.addActionListener(e -> onEditCourse());
        btnDelete.addActionListener(e -> onDeleteCourse());
        btnManage.addActionListener(e -> onManageLessons());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnManage);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadCourses() {
        courseModel.clear();
        CourseJsonDatabase.loadCourses();
        List<Course> all = CourseJsonDatabase.getAllCourses();
        for (Course c : all) {
           if (c.getInstructorId().equals(instructor.getUserId())) {
    courseModel.addElement(c);
}
        }
    }

    private void onAddCourse() {
        NewJDialog dlg = new NewJDialog(this, true);
        dlg.setVisible(true);
        if (!dlg.isSaved()) return;

        NewJDialog.CourseData data = dlg.getSavedCourse();

        // generate new int id
        int newId = CourseJsonDatabase.getAllCourses().stream()
                .mapToInt(Course::getCourseId).max().orElse(0) + 1;
String instrId = instructor.getUserId(); 
        Course newCourse = new Course(newId, data.getTitle(), data.getDescription(), instrId);
        boolean ok = CourseJsonDatabase.addCourse(newCourse);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Course ID conflict", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        instructor.getCreatedCourses().add(String.valueOf(newId)); // if you maintain string ids in instructor
        courseModel.addElement(newCourse);
    }

    private void onEditCourse() {
        int idx = courseList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }
        Course c = courseModel.getElementAt(idx);
        NewJDialog dlg = new NewJDialog(this, true);
        dlg.setEditingCourse(new NewJDialog.CourseData(String.valueOf(c.getCourseId()), c.getTitle(), c.getDescription()));
        dlg.setVisible(true);

        if (!dlg.isSaved()) return;
        NewJDialog.CourseData d = dlg.getSavedCourse();
        c.setTitle(d.getTitle());
        c.setDescription(d.getDescription());
        CourseJsonDatabase.saveCourses();
        courseModel.set(idx, c);
    }

    private void onDeleteCourse() {
        int idx = courseList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }
        Course c = courseModel.getElementAt(idx);
        int res = JOptionPane.showConfirmDialog(this, "Delete course " + c.getTitle() + "?", "Delete", JOptionPane.YES_NO_OPTION);
        if (res != JOptionPane.YES_OPTION) return;

        CourseJsonDatabase.removeCourse(c.getCourseId());
        courseModel.remove(idx);
    }

    private void onManageLessons() {
        int idx = courseList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }
        Course c = courseModel.getElementAt(idx);
        // load existing lesson data
        List<LessonManagerDialog.LessonData> ld = new java.util.ArrayList<>();
        // convert existing Course lessons to LessonData
        for (models.Lesson lesson : c.getLessons()) {
            LessonManagerDialog.LessonData d = new LessonManagerDialog.LessonData(
                    String.valueOf(lesson.getLessonId()), lesson.getTitle(), lesson.getContent());
            ld.add(d);
        }
        LessonManagerDialog dlg = new LessonManagerDialog(this, true, c.getTitle(), ld);
        dlg.setVisible(true);
        java.util.List<LessonManagerDialog.LessonData> lessonsNew = dlg.getLessons();
        // convert back to model.Lesson
        List<models.Lesson> lessonModels = new java.util.ArrayList<>();
        int nextLessonId = c.getLessons().stream()
                .mapToInt(models.Lesson::getLessonId).max().orElse(0) + 1;

        for (LessonManagerDialog.LessonData d : lessonsNew) {
            int lid;
            try {
                lid = Integer.parseInt(d.getId());
            } catch (NumberFormatException e) {
                lid = nextLessonId++;
            }
            models.Lesson m = new models.Lesson(lid, d.getTitle(), d.getContent(), new java.util.ArrayList<>());
            lessonModels.add(m);
        }

        c.setLessons(lessonModels);
        CourseJsonDatabase.saveCourses();
        JOptionPane.showMessageDialog(this, "Saved " + lessonModels.size() + " lessons.");
    }

    public static void main(String[] args) {
        // for testing; you will pass actual instructor object after login
        Instructor dummy = new Instructor("instructor1", "instr@ex.com", "pwdhash");
        SwingUtilities.invokeLater(() -> new InstructorDashboardFrame(dummy).setVisible(true));
    }
}
