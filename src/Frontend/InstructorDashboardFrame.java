package frontEnd;

import database.JsonUserDatabase;
import models.Instructor;
import models.User;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

public class InstructorDashboardFrame extends javax.swing.JFrame {

    public InstructorDashboardFrame() {
        initComponents();
        courseModel = new DefaultListModel<>();
        jListCourses.setModel(courseModel);
        jListCourses.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jsonUserDb = new JsonUserDatabase();
        loadOrCreateInstructor();

        if (currentInstructor != null && currentInstructor.getCreatedCourses() != null) {
            for (String cid : currentInstructor.getCreatedCourses()) {
                courseIds.add(cid);
                courseModel.addElement("(course " + cid + ")");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jListCourses = new javax.swing.JList<>();
        btnCreate = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnManageLessons = new javax.swing.JButton();
        btnViewEnrolled = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jListCourses.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListCourses);

        btnCreate.setText("Create course");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit courses");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnManageLessons.setText("Manage");
        btnManageLessons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageLessonsActionPerformed(evt);
            }
        });

        btnViewEnrolled.setText("View enrolled");
        btnViewEnrolled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewEnrolledActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(btnCreate)
                                                .addComponent(btnEdit))
                                        .addComponent(btnDelete)
                                        .addComponent(btnManageLessons)
                                        .addComponent(btnViewEnrolled))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(240, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnCreate)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEdit)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDelete)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnManageLessons)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnViewEnrolled))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(148, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        int idx = jListCourses.getSelectedIndex();
        if (idx < 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Select a course first.", "No selection", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        javax.swing.DefaultListModel<String> model = (javax.swing.DefaultListModel<String>) jListCourses.getModel();
        String currentTitle = model.getElementAt(idx);

        NewJDialog dlg = new NewJDialog(this, true);
        dlg.setTitleField(currentTitle);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);

        if (!dlg.isSaved()) return;

        NewJDialog.CourseData data = dlg.getSavedCourse();
        model.set(idx, data.getTitle());
        jListCourses.setSelectedIndex(idx);
    }

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {
        NewJDialog dlg = new NewJDialog(this, true);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);

        if (dlg.isSaved()) {
            NewJDialog.CourseData data = dlg.getSavedCourse();

            javax.swing.DefaultListModel<String> model;
            if (jListCourses.getModel() instanceof javax.swing.DefaultListModel) {
                model = (javax.swing.DefaultListModel<String>) jListCourses.getModel();
            } else {
                model = new javax.swing.DefaultListModel<>();
                jListCourses.setModel(model);
            }

            String newCourseId = java.util.UUID.randomUUID().toString();

            if (currentInstructor.getCreatedCourses() == null) {
                currentInstructor.setCreatedCourses(new ArrayList<>());
            }
            currentInstructor.getCreatedCourses().add(newCourseId);

            boolean ok = jsonUserDb.updateUser(currentInstructor.getUserId(), currentInstructor);
            if (!ok) {
                jsonUserDb.addUserToFile(currentInstructor);
            }

            model.addElement(data.getTitle());
            courseIds.add(newCourseId);
            jListCourses.setSelectedIndex(model.size() - 1);

            javax.swing.JOptionPane.showMessageDialog(this,
                    "Course created locally and instructor updated (id: " + newCourseId + ")",
                    "Created", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void btnViewEnrolledActionPerformed(java.awt.event.ActionEvent evt) {
        String selected = jListCourses.getSelectedValue();

        if (selected == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Please select a course first.",
                    "No Course Selected",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(this,
                "Enrolled students for course:\n" + selected +
                        "\n\n(Backend not connected yet)",
                "Enrolled Students",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int idx = jListCourses.getSelectedIndex();
        if (idx < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Select a course first.", "No selection", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int ok = javax.swing.JOptionPane.showConfirmDialog(this,
                "Delete selected course?\nThis will remove it from the UI (backend not connected).",
                "Confirm Delete",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);
        if (ok != javax.swing.JOptionPane.YES_OPTION) return;

        javax.swing.DefaultListModel<String> model = (javax.swing.DefaultListModel<String>) jListCourses.getModel();
        model.remove(idx);
    }

    private void btnManageLessonsActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedTitle = jListCourses.getSelectedValue();

        if (selectedTitle == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Select a course first.", "No selection", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            java.util.List<LessonManagerDialog.LessonData> initial = new java.util.ArrayList<>();
            LessonManagerDialog dlg = new LessonManagerDialog(this, true, selectedTitle, initial);
            dlg.setLocationRelativeTo(this);
            dlg.setVisible(true);
            java.util.List<LessonManagerDialog.LessonData> lessons = dlg.getLessons();
            if (lessons != null && !lessons.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Lessons for \"" + selectedTitle + "\": " + lessons.size() +
                                "\n(Backend not connected yet)",
                        "Lessons", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NoClassDefFoundError | Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Lesson manager not available in this build.\nYou can still add lessons later via backend tools.",
                    "Not Implemented", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadOrCreateInstructor() {
        List<User> users = jsonUserDb.readUsersFromFile();
        Instructor found = null;
        for (User u : users) {
            if (u instanceof Instructor) {
                found = (Instructor) u;
                break;
            }
        }

        if (found != null) {
            currentInstructor = found;
            if (currentInstructor.getCreatedCourses() == null) {
                currentInstructor.setCreatedCourses(new ArrayList<>());
            }
            return;
        }

        String newId = UUID.randomUUID().toString();
        currentInstructor = new Instructor(newId, "instructor1", "instructor1@example.com", "", new ArrayList<>());
        jsonUserDb.addUserToFile(currentInstructor);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InstructorDashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InstructorDashboardFrame().setVisible(true);
            }
        });
    }

    private JsonUserDatabase jsonUserDb;
    private Instructor currentInstructor;
    private DefaultListModel<String> courseModel;
    private List<String> courseIds = new ArrayList<>();

    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnManageLessons;
    private javax.swing.JButton btnViewEnrolled;
    private javax.swing.JList<String> jListCourses;
    private javax.swing.JScrollPane jScrollPane1;
}