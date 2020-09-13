/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import se.database.QueryMethods;
import se.model.Seminar;

/**
 *
 * @author enrique & simon
 */
public class AddSeminar extends javax.swing.JFrame {

    /**
     * Creates new form SeminariumView
     */
    private QueryMethods qMethods = new QueryMethods();

    private String email;
    private ArrayList<Seminar> seminar;

    public AddSeminar(String email) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        fillSeminarTable();
        this.email = email;
    }

    public AddSeminar() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        fillSeminarTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void fillSeminarTable() {
        this.seminar = qMethods.findSeminar();
        String[] colname = {"ID", "Namn", "Talare", "Plats", "Start Datum", "Max Besökare", "Beskrivning", "Program"};
        DefaultTableModel defaultModel = new DefaultTableModel(colname, 0);
        defaultModel.setRowCount(0);
        for (int i = 0; i < this.seminar.size(); i++) {
            defaultModel.addRow(new Object[]{this.seminar.get(i).getId(),
                this.seminar.get(i).getTitle(),
                this.seminar.get(i).getSpeaker(),
                this.seminar.get(i).getLocation(),
                this.seminar.get(i).getStartDate(),
                this.seminar.get(i).getCountVisitor(),
                this.seminar.get(i).getSeminariumDescription(),
                this.seminar.get(i).getProgramDescription()});
        }

        jTableSeminarium.setModel(defaultModel);
        jTableSeminarium.setRowSelectionAllowed(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelbackground = new javax.swing.JPanel();
        jLabelBackgroundPhoto = new javax.swing.JLabel();
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanelAddSeminarium = new javax.swing.JPanel();
        jtfName = new javax.swing.JTextField();
        jlbPlace = new javax.swing.JLabel();
        jlbName = new javax.swing.JLabel();
        jbtnCancel = new javax.swing.JButton();
        jlbDate = new javax.swing.JLabel();
        jlbTotalVisitor = new javax.swing.JLabel();
        jbtnAdd = new javax.swing.JButton();
        jlbSpeaker = new javax.swing.JLabel();
        jlbDescription = new javax.swing.JLabel();
        jlbProgram = new javax.swing.JLabel();
        txtSpeaker = new javax.swing.JTextField();
        txtLocation = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        txtMaxGuests = new javax.swing.JTextField();
        txtDesc = new javax.swing.JTextField();
        txtProgram = new javax.swing.JTextField();
        jbtnTillbaka = new javax.swing.JButton();
        jPanelCurrentSeminarium = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSeminarium = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelbackground.setBackground(new java.awt.Color(244, 244, 244));

        jLabelBackgroundPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/seminarium.jpg"))); // NOI18N

        jPanelTitle.setBackground(new java.awt.Color(133, 102, 170));

        jLabelTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(64, 186, 213));
        jLabelTitle.setText("Seminarium");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo libro.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo letras libro.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/home_50px.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(64, 186, 213));
        jLabel4.setText("Logga ut");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                        .addComponent(jLabelTitle)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabelTitle))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelAddSeminarium.setBackground(new java.awt.Color(244, 244, 244));
        jPanelAddSeminarium.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lägg till seminarium", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18), new java.awt.Color(105, 131, 170))); // NOI18N

        jtfName.setToolTipText("");
        jtfName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNameActionPerformed(evt);
            }
        });

        jlbPlace.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jlbPlace.setForeground(new java.awt.Color(105, 131, 170));
        jlbPlace.setText("Plats");

        jlbName.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jlbName.setForeground(new java.awt.Color(105, 131, 170));
        jlbName.setText("Namn");

        jbtnCancel.setText("Avbryt");

        jlbDate.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jlbDate.setForeground(new java.awt.Color(105, 131, 170));
        jlbDate.setText("Datum");

        jlbTotalVisitor.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jlbTotalVisitor.setForeground(new java.awt.Color(105, 131, 170));
        jlbTotalVisitor.setText("Max besökare");

        jbtnAdd.setText("Lägg till");
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });

        jlbSpeaker.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jlbSpeaker.setForeground(new java.awt.Color(105, 131, 170));
        jlbSpeaker.setText("Talare");

        jlbDescription.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jlbDescription.setForeground(new java.awt.Color(105, 131, 170));
        jlbDescription.setText("Beskrivning");

        jlbProgram.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jlbProgram.setForeground(new java.awt.Color(105, 131, 170));
        jlbProgram.setText("Program");

        txtSpeaker.setToolTipText("");
        txtSpeaker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSpeakerActionPerformed(evt);
            }
        });

        txtLocation.setToolTipText("");
        txtLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocationActionPerformed(evt);
            }
        });

        txtDate.setToolTipText("MM-DD");

        txtMaxGuests.setToolTipText("");
        txtMaxGuests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaxGuestsActionPerformed(evt);
            }
        });

        txtDesc.setToolTipText("");
        txtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescActionPerformed(evt);
            }
        });

        txtProgram.setToolTipText("");
        txtProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProgramActionPerformed(evt);
            }
        });

        jbtnTillbaka.setText("Tillbaka");
        jbtnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnTillbakaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAddSeminariumLayout = new javax.swing.GroupLayout(jPanelAddSeminarium);
        jPanelAddSeminarium.setLayout(jPanelAddSeminariumLayout);
        jPanelAddSeminariumLayout.setHorizontalGroup(
            jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jlbName)
                        .addGap(18, 18, 18)
                        .addComponent(jtfName))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddSeminariumLayout.createSequentialGroup()
                        .addContainerGap(167, Short.MAX_VALUE)
                        .addComponent(jbtnTillbaka)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnAdd)
                        .addGap(6, 6, 6)
                        .addComponent(jbtnCancel))
                    .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlbDescription)
                            .addComponent(jlbProgram))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDesc)
                            .addComponent(txtProgram)))
                    .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jlbPlace, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtLocation))
                            .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                                .addComponent(jlbSpeaker)
                                .addGap(18, 18, 18)
                                .addComponent(txtSpeaker))))
                    .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                        .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddSeminariumLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jlbTotalVisitor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaxGuests))
                            .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jlbDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDate)))
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        jPanelAddSeminariumLayout.setVerticalGroup(
            jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbName))
                .addGap(17, 17, 17)
                .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbSpeaker)
                    .addComponent(txtSpeaker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbPlace)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbDate)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbTotalVisitor)
                    .addComponent(txtMaxGuests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                        .addComponent(jlbDescription)
                        .addGap(0, 100, Short.MAX_VALUE))
                    .addComponent(txtDesc))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                        .addComponent(jlbProgram)
                        .addGap(182, 182, 182))
                    .addGroup(jPanelAddSeminariumLayout.createSequentialGroup()
                        .addComponent(txtProgram)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAddSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jbtnAdd)
                                .addComponent(jbtnTillbaka))
                            .addComponent(jbtnCancel))
                        .addContainerGap())))
        );

        jPanelCurrentSeminarium.setBackground(new java.awt.Color(244, 244, 244));
        jPanelCurrentSeminarium.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aktuella Seminarium", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18), new java.awt.Color(105, 131, 170))); // NOI18N

        jTableSeminarium.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableSeminarium);

        javax.swing.GroupLayout jPanelCurrentSeminariumLayout = new javax.swing.GroupLayout(jPanelCurrentSeminarium);
        jPanelCurrentSeminarium.setLayout(jPanelCurrentSeminariumLayout);
        jPanelCurrentSeminariumLayout.setHorizontalGroup(
            jPanelCurrentSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );
        jPanelCurrentSeminariumLayout.setVerticalGroup(
            jPanelCurrentSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCurrentSeminariumLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelbackgroundLayout = new javax.swing.GroupLayout(jPanelbackground);
        jPanelbackground.setLayout(jPanelbackgroundLayout);
        jPanelbackgroundLayout.setHorizontalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBackgroundPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCurrentSeminarium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAddSeminarium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
            .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelbackgroundLayout.setVerticalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanelCurrentSeminarium, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelAddSeminarium, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelBackgroundPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNameActionPerformed

    private void txtSpeakerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSpeakerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSpeakerActionPerformed

    private void txtLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocationActionPerformed

    private void txtMaxGuestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaxGuestsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaxGuestsActionPerformed

    private void txtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescActionPerformed

    private void txtProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProgramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProgramActionPerformed

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
        String name = jtfName.getText();
        String speaker = txtSpeaker.getText();
        String location = txtLocation.getText();
        String sDate = txtDate.getText();
        String maxGuestsInput = txtMaxGuests.getText();
        int maxGuests = 0;
        String desc = txtDesc.getText();
        String program = txtProgram.getText();

        if (jtfName.getText().equals("") || txtSpeaker.getText().equals("")
                || txtLocation.getText().equals("") || txtDate.getText().equals("")
                || txtMaxGuests.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Första 5 raderna får inte vara tomma.");
            return;
        }

        try {
            maxGuests = Integer.parseInt(maxGuestsInput);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Antal gäster måste vara ett tal.");
            txtMaxGuests.setText("");
            return;
        }

        qMethods = new QueryMethods();
        Seminar seminar = new Seminar(name, speaker, location, sDate, maxGuests, desc, program);
        qMethods.addSeminar(seminar);
        clearInputFields();
        fillSeminarTable();
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jbtnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnTillbakaActionPerformed
        LibrarianView lv = new LibrarianView(email);
        lv.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_jbtnTillbakaActionPerformed

    private void clearInputFields() {
        jtfName.setText("");
        txtSpeaker.setText("");
        txtLocation.setText("");
        txtDate.setText("");
        txtMaxGuests.setText("");
        txtDesc.setText("");
        txtProgram.setText("");
    }

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
            java.util.logging.Logger.getLogger(AddSeminar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddSeminar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddSeminar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddSeminar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddSeminar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelBackgroundPhoto;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelAddSeminarium;
    private javax.swing.JPanel jPanelCurrentSeminarium;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSeminarium;
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnTillbaka;
    private javax.swing.JLabel jlbDate;
    private javax.swing.JLabel jlbDescription;
    private javax.swing.JLabel jlbName;
    private javax.swing.JLabel jlbPlace;
    private javax.swing.JLabel jlbProgram;
    private javax.swing.JLabel jlbSpeaker;
    private javax.swing.JLabel jlbTotalVisitor;
    private javax.swing.JTextField jtfName;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtMaxGuests;
    private javax.swing.JTextField txtProgram;
    private javax.swing.JTextField txtSpeaker;
    // End of variables declaration//GEN-END:variables
}