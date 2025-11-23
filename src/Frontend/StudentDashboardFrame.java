package frontEnd;

import java.awt.BorderLayout;
import models.Student;

public class StudentDashboardFrame extends javax.swing.JFrame {

    private Student student;

    public StudentDashboardFrame(Student student) {
        initComponents();
        this.student = student;
        mainPanel.setLayout(new BorderLayout());  // FIXED
    }

    public StudentDashboardFrame() {
        initComponents();
        mainPanel.setLayout(new BorderLayout());  // FIXED
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Available Courses");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        jButton2.setText("Enrolled Courses");
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));

        jButton3.setText("Enroll in Course");
        jButton3.addActionListener(evt -> jButton3ActionPerformed(evt));

        jButton4.setText("Logout");
        jButton4.addActionListener(evt -> jButton4ActionPerformed(evt));

        jButton5.setText("View Lessons");
        jButton5.addActionListener(evt -> jButton5ActionPerformed(evt));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(101, Short.MAX_VALUE))
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    // ----------------- BUTTON ACTIONS (FIXED) --------------------

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        mainPanel.removeAll();
        mainPanel.add(new AvailableCourses(), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        mainPanel.removeAll();
        mainPanel.add(new EnrolledCourses(student), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        mainPanel.removeAll();
        mainPanel.add(new EnrollinCourse(student), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        mainPanel.removeAll();
        mainPanel.add(new ViewLessons(), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        Login l = new Login();
        l.setVisible(true);
        this.setVisible(false);
    }

    // Variables declaration
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel mainPanel;
}
