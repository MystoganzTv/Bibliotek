/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

/**
 *
 * @author enrique
 */
public class AdminUpdate extends javax.swing.JFrame {

    /**
     * Creates new form AdminUpdate
     */
    public AdminUpdate() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBackground = new javax.swing.JPanel();
        jPanelWorkingArea = new javax.swing.JPanel();
        emailField = new javax.swing.JTextField();
        UsernameField = new javax.swing.JTextField();
        firstnameField = new javax.swing.JTextField();
        lastnameField = new javax.swing.JTextField();
        jLabelPw = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelLastname = new javax.swing.JLabel();
        jLabelFirstname = new javax.swing.JLabel();
        pwField = new javax.swing.JTextField();
        jLabelUsername = new javax.swing.JLabel();
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jbtnUpdate = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelWorkingArea.setBackground(new java.awt.Color(255, 255, 255));

        emailField.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N

        UsernameField.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N

        firstnameField.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N

        lastnameField.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N

        jLabelPw.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelPw.setForeground(new java.awt.Color(105, 131, 170));
        jLabelPw.setText("Lösenord");

        jLabelEmail.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEmail.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEmail.setText("Email");

        jLabelLastname.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelLastname.setForeground(new java.awt.Color(105, 131, 170));
        jLabelLastname.setText("Efternamn");

        jLabelFirstname.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelFirstname.setForeground(new java.awt.Color(105, 131, 170));
        jLabelFirstname.setText("Förnamn");

        pwField.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        pwField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwFieldActionPerformed(evt);
            }
        });

        jLabelUsername.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUsername.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUsername.setText("Användarnamn");

        jPanelTitle.setBackground(new java.awt.Color(133, 102, 170));

        jLabelTitle5.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabelTitle5.setForeground(new java.awt.Color(64, 186, 213));
        jLabelTitle5.setText("Uppdatera");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo libro.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(250, 134));

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabelTitle5)
                .addGap(33, 33, 33))
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabelTitle5)))
                .addContainerGap())
        );

        jbtnUpdate.setText("Uppdatera");
        jbtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateActionPerformed(evt);
            }
        });

        jbtnCancel.setText("Avbryt");
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelWorkingAreaLayout = new javax.swing.GroupLayout(jPanelWorkingArea);
        jPanelWorkingArea.setLayout(jPanelWorkingAreaLayout);
        jPanelWorkingAreaLayout.setHorizontalGroup(
            jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelWorkingAreaLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFirstname)
                    .addComponent(jLabelEmail)
                    .addComponent(jLabelUsername)
                    .addComponent(jLabelLastname)
                    .addComponent(jLabelPw))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelWorkingAreaLayout.createSequentialGroup()
                        .addComponent(jbtnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jbtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(emailField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UsernameField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(firstnameField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lastnameField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pwField, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(45, 45, 45))
            .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelWorkingAreaLayout.setVerticalGroup(
            jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWorkingAreaLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFirstname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLastname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPw))
                .addGap(7, 7, 7)
                .addGroup(jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEmail))
                .addGap(46, 46, 46)
                .addGroup(jPanelWorkingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnUpdate)
                    .addComponent(jbtnCancel))
                .addGap(134, 134, 134))
        );

        javax.swing.GroupLayout jPanelBackgroundLayout = new javax.swing.GroupLayout(jPanelBackground);
        jPanelBackground.setLayout(jPanelBackgroundLayout);
        jPanelBackgroundLayout.setHorizontalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelWorkingArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelBackgroundLayout.setVerticalGroup(
            jPanelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelWorkingArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pwFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwFieldActionPerformed

    private void jbtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnUpdateActionPerformed

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminUpdate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUpdate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField UsernameField;
    public javax.swing.JTextField emailField;
    public javax.swing.JTextField firstnameField;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelFirstname;
    private javax.swing.JLabel jLabelLastname;
    private javax.swing.JLabel jLabelPw;
    private javax.swing.JLabel jLabelTitle5;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPanel jPanelBackground;
    private javax.swing.JPanel jPanelTitle;
    public javax.swing.JPanel jPanelWorkingArea;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnUpdate;
    public javax.swing.JTextField lastnameField;
    public javax.swing.JTextField pwField;
    // End of variables declaration//GEN-END:variables
}
