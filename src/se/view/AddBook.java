/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.util.ArrayList;
import se.database.QueryMethods;
import se.model.Books;
import se.model.Category;
import se.model.ComboItem;
import se.model.E_Books;

/**
 *
 * @author enriq
 */
public class AddBook extends javax.swing.JFrame {

    private QueryMethods qm = new QueryMethods();

    /**
     * Creates new form AddBook
     */
    public AddBook() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);

        boxType.addItem(new ComboItem("Bok", "1"));
        boxType.addItem(new ComboItem("E-Bok", "2"));
        fillBoxCategory();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelbackground = new javax.swing.JPanel();
        jLabelBackgroundPhoto = new javax.swing.JLabel();
        jPanelAddBook = new javax.swing.JPanel();
        jFieldTitle = new javax.swing.JTextField();
        jLabelISBN = new javax.swing.JLabel();
        jLabelTitel = new javax.swing.JLabel();
        jLabelPublisher = new javax.swing.JLabel();
        jLabelPurchaseprice = new javax.swing.JLabel();
        jLabelDescription = new javax.swing.JLabel();
        jLabelLocation = new javax.swing.JLabel();
        jLabelAuthor = new javax.swing.JLabel();
        jLabelTitel1 = new javax.swing.JLabel();
        jLabelTitel2 = new javax.swing.JLabel();
        boxType = new javax.swing.JComboBox();
        boxCategory = new javax.swing.JComboBox();
        txtISBN = new javax.swing.JTextField();
        jFieldPublisher = new javax.swing.JTextField();
        jFieldPurchasePrice = new javax.swing.JTextField();
        jFieldPlacement = new javax.swing.JTextField();
        jFieldAuthor = new javax.swing.JTextField();
        jFieldDescription = new javax.swing.JTextField();
        jbtnAccept = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelLogoIcon = new javax.swing.JLabel();
        jLabelLogoText = new javax.swing.JLabel();
        jLabelHome = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelBackgroundPhoto.setBackground(new java.awt.Color(244, 244, 244));
        jLabelBackgroundPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/AddBook1.jpg"))); // NOI18N

        jPanelAddBook.setBackground(new java.awt.Color(244, 244, 244));
        jPanelAddBook.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lägg till bok", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18), new java.awt.Color(105, 131, 170))); // NOI18N

        jFieldTitle.setToolTipText("");

        jLabelISBN.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelISBN.setForeground(new java.awt.Color(105, 131, 170));
        jLabelISBN.setText("ISBN");

        jLabelTitel.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelTitel.setForeground(new java.awt.Color(105, 131, 170));
        jLabelTitel.setText("Titel");

        jLabelPublisher.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelPublisher.setForeground(new java.awt.Color(105, 131, 170));
        jLabelPublisher.setText("Förlag");

        jLabelPurchaseprice.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelPurchaseprice.setForeground(new java.awt.Color(105, 131, 170));
        jLabelPurchaseprice.setText("Inköpspris");

        jLabelDescription.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelDescription.setForeground(new java.awt.Color(105, 131, 170));
        jLabelDescription.setText("Beskrivning");

        jLabelLocation.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelLocation.setForeground(new java.awt.Color(105, 131, 170));
        jLabelLocation.setText("Placering");

        jLabelAuthor.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelAuthor.setForeground(new java.awt.Color(105, 131, 170));
        jLabelAuthor.setText("Författare");

        jLabelTitel1.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelTitel1.setForeground(new java.awt.Color(105, 131, 170));
        jLabelTitel1.setText("Bokstyp");

        jLabelTitel2.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelTitel2.setForeground(new java.awt.Color(105, 131, 170));
        jLabelTitel2.setText("Kategori");

        boxType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxTypeItemStateChanged(evt);
            }
        });

        txtISBN.setToolTipText("");

        jFieldPublisher.setToolTipText("");

        jFieldPurchasePrice.setToolTipText("");

        jFieldPlacement.setToolTipText("");

        jFieldAuthor.setToolTipText("");

        jFieldDescription.setToolTipText("");

        jbtnAccept.setText("Lägg till");
        jbtnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAcceptActionPerformed(evt);
            }
        });

        jbtnCancel.setText("Avbryt");
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAddBookLayout = new javax.swing.GroupLayout(jPanelAddBook);
        jPanelAddBook.setLayout(jPanelAddBookLayout);
        jPanelAddBookLayout.setHorizontalGroup(
            jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddBookLayout.createSequentialGroup()
                .addContainerGap(92, Short.MAX_VALUE)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabelISBN)
                        .addComponent(jLabelPublisher)
                        .addComponent(jLabelPurchaseprice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelLocation)
                        .addComponent(jLabelTitel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelAuthor)
                        .addComponent(jLabelTitel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTitel2))
                    .addComponent(jLabelDescription))
                .addGap(64, 64, 64)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddBookLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnAccept)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnCancel))
                    .addComponent(jFieldDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .addGroup(jPanelAddBookLayout.createSequentialGroup()
                        .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFieldTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                            .addComponent(boxType, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtISBN)
                            .addComponent(jFieldPublisher)
                            .addComponent(jFieldPurchasePrice)
                            .addComponent(jFieldPlacement, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFieldAuthor)
                            .addComponent(boxCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(35, 35, 35))
        );
        jPanelAddBookLayout.setVerticalGroup(
            jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddBookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitel1)
                    .addComponent(boxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitel2)
                    .addComponent(boxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTitel))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelISBN)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPublisher)
                    .addComponent(jFieldPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPurchaseprice)
                    .addComponent(jFieldPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLocation)
                    .addComponent(jFieldPlacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAuthor)
                    .addComponent(jFieldAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDescription)
                    .addComponent(jFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnAccept)
                    .addComponent(jbtnCancel))
                .addGap(79, 79, 79))
        );

        jPanelTitle.setBackground(new java.awt.Color(133, 102, 170));

        jLabelTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(64, 186, 213));
        jLabelTitle.setText("Lägg till bok  ");

        jLabelLogoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo libro.png"))); // NOI18N

        jLabelLogoText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo letras libro.png"))); // NOI18N

        jLabelHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/home_80px.png"))); // NOI18N
        jLabelHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelHomeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addComponent(jLabelLogoIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLogoText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelHome)
                .addContainerGap())
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelLogoIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelHome)
                    .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLogoText, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelbackgroundLayout = new javax.swing.GroupLayout(jPanelbackground);
        jPanelbackground.setLayout(jPanelbackgroundLayout);
        jPanelbackgroundLayout.setHorizontalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBackgroundPhoto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAddBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelbackgroundLayout.setVerticalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelbackgroundLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelBackgroundPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void fillBoxCategory() {

        ArrayList<Category> categories = qm.findCategories();
        int indexCount = 1;

        for (Category category : categories) {
            boxCategory.addItem(new ComboItem(category.getCategory(), Integer.toString(indexCount)));
        }
    }

 

    private void jLabelHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelHomeMouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabelHomeMouseClicked

    private void boxTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxTypeItemStateChanged
        
        if(boxType.getSelectedItem().toString().toLowerCase().equals("e-bok")) {
            jFieldPlacement.setEnabled(false);
        } else {
             jFieldPlacement.setEnabled(true);
        }
    }//GEN-LAST:event_boxTypeItemStateChanged

    private void jbtnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAcceptActionPerformed
     
        QueryMethods qMethods = new QueryMethods();
        String title = jFieldTitle.getText().replace("'", "´");
        String author = jFieldAuthor.getText();
        String isbn = txtISBN.getText();
        String desc = jFieldDescription.getText();//.replaceAll("'", "\\'");
        String publisher = jFieldPublisher.getText();//.replaceAll("'", "\\'");
        double purchasePrice = Double.parseDouble(jFieldPurchasePrice.getText());
        String bookType = boxType.getSelectedItem().toString().toLowerCase();
        String category = boxCategory.getSelectedItem().toString().trim();
        String placement = jFieldPlacement.getText();


        if (bookType.equals("bok")) {
            Books b = new Books(title, author, isbn, publisher, purchasePrice, category, placement,  desc);
            qMethods.addBook(b);
        } else {
            E_Books b = new E_Books(title, author, isbn, publisher, purchasePrice, category, desc);
            qMethods.addEBook(b);
        }

        clearMenu();
    }                                    
    
    public void clearMenu() {
        jFieldTitle.setText("");
        jFieldAuthor.setText("");
        txtISBN.setText("");
        jFieldPublisher.setText("");
        jFieldPurchasePrice.setText("");
        jFieldPlacement.setText("");
        jFieldDescription.setText("");
        boxType.setSelectedIndex(0);
        boxCategory.setSelectedIndex(0);
    }//GEN-LAST:event_jbtnAcceptActionPerformed

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed
        LibrarianView lv = new LibrarianView();
        lv.setVisible(true);
        this.setVisible(false);
        
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
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxCategory;
    private javax.swing.JComboBox boxType;
    private javax.swing.JTextField jFieldAuthor;
    private javax.swing.JTextField jFieldDescription;
    private javax.swing.JTextField jFieldPlacement;
    private javax.swing.JTextField jFieldPublisher;
    private javax.swing.JTextField jFieldPurchasePrice;
    private javax.swing.JTextField jFieldTitle;
    private javax.swing.JLabel jLabelAuthor;
    private javax.swing.JLabel jLabelBackgroundPhoto;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelHome;
    private javax.swing.JLabel jLabelISBN;
    private javax.swing.JLabel jLabelLocation;
    private javax.swing.JLabel jLabelLogoIcon;
    private javax.swing.JLabel jLabelLogoText;
    private javax.swing.JLabel jLabelPublisher;
    private javax.swing.JLabel jLabelPurchaseprice;
    private javax.swing.JLabel jLabelTitel;
    private javax.swing.JLabel jLabelTitel1;
    private javax.swing.JLabel jLabelTitel2;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelAddBook;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JButton jbtnAccept;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JTextField txtISBN;
    // End of variables declaration//GEN-END:variables
}
