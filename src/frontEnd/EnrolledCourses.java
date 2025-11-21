package frontEnd;

import java.util.List;
import Services.CourseManager;
import models.Course;
import models.Student;

public class EnrolledCourses extends javax.swing.JPanel {
    private Student student;
    CourseManager manager = new CourseManager();

    public EnrolledCourses(Student student) {
        this.student = student;
        initComponents();
        LoadEnrolledCourses();
    }

    public void LoadEnrolledCourses() {
        // FIX: Handle String userId properly
        List<String> enrolledIds = student.getEnrolledCourses();
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        if (enrolledIds != null) {
            for (String courseIdStr : enrolledIds) {
                try {
                    int courseId = Integer.parseInt(courseIdStr);
                    Course c = manager.getCourseById(courseId);
                    if (c != null) {
                        model.addRow(new Object[]{c.getCourseId(), c.getTitle(), c.getDescription(), c.getInstructorId()});
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid course ID format: " + courseIdStr);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "CourseID", "Title", "Description", "Instructor Name"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel1.setText("Enrolled Courses");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
}