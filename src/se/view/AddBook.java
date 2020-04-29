/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import se.database.QueryMethods;
import se.model.Books;
import se.model.ComboItem;
import se.model.E_Books;

/**
 *
 * @author enriq
 */
public class AddBook extends javax.swing.JFrame {

    /**
     * Creates new form AddBook
     */
    public AddBook() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        
        boxType.addItem(new ComboItem("Bok", "1"));
        boxType.addItem(new ComboItem("E-Bok", "2"));
        boxCategory.addItem(new ComboItem("Matte (Test)", "1"));
        boxCategory.addItem(new ComboItem("Historia (Test)", "2"));
        boxCategory.addItem(new ComboItem("Biologi (Test)", "3"));
        boxCategory.addItem(new ComboItem("Programmering (Test)", "4"));
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
        txtISBN = new javax.swing.JPasswordField();
        jFieldTitle = new javax.swing.JTextField();
        jLabelISBN = new javax.swing.JLabel();
        jLabelTitel = new javax.swing.JLabel();
        jLabelPublisher = new javax.swing.JLabel();
        jFieldPublisher = new javax.swing.JPasswordField();
        jLabelPurchaseprice = new javax.swing.JLabel();
        jFieldPurchasePrice = new javax.swing.JPasswordField();
        jLabelDescription = new javax.swing.JLabel();
        jFieldDescription = new javax.swing.JPasswordField();
        jLabelLocation = new javax.swing.JLabel();
        jFieldLocation = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelAuthor = new javax.swing.JLabel();
        jFieldAuthor = new javax.swing.JPasswordField();
        jLabelTitel1 = new javax.swing.JLabel();
        jLabelTitel2 = new javax.swing.JLabel();
        boxType = new javax.swing.JComboBox();
        boxCategory = new javax.swing.JComboBox();
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/ok_64px.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/cancel_64px.png"))); // NOI18N

        jLabelAuthor.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelAuthor.setForeground(new java.awt.Color(105, 131, 170));
        jLabelAuthor.setText("Författare");

        jLabelTitel1.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelTitel1.setForeground(new java.awt.Color(105, 131, 170));
        jLabelTitel1.setText("Bokstyp");

        jLabelTitel2.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelTitel2.setForeground(new java.awt.Color(105, 131, 170));
        jLabelTitel2.setText("Kategori");

        javax.swing.GroupLayout jPanelAddBookLayout = new javax.swing.GroupLayout(jPanelAddBook);
        jPanelAddBook.setLayout(jPanelAddBookLayout);
        jPanelAddBookLayout.setHorizontalGroup(
            jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddBookLayout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddBookLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddBookLayout.createSequentialGroup()
                        .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAddBookLayout.createSequentialGroup()
                                .addComponent(jLabelDescription)
                                .addGap(64, 64, 64)
                                .addComponent(jFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelAddBookLayout.createSequentialGroup()
                                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelISBN)
                                    .addComponent(jLabelPublisher)
                                    .addComponent(jLabelPurchaseprice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelLocation)
                                    .addComponent(jLabelTitel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelAuthor)
                                    .addComponent(jLabelTitel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelTitel2))
                                .addGap(73, 73, 73)
                                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jFieldTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(jFieldLocation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(jFieldPurchasePrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(jFieldPublisher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(txtISBN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(jFieldAuthor, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(boxType, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(35, 35, 35))))
        );

        jPanelAddBookLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jFieldDescription, jFieldLocation, jFieldPublisher, jFieldPurchasePrice, jFieldTitle, txtISBN});

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
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelISBN))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFieldPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPublisher))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPurchaseprice)
                    .addComponent(jFieldPurchasePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFieldLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLocation))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFieldAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAuthor))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDescription)
                    .addComponent(jFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        jPanelAddBookLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jFieldTitle, txtISBN});

        jPanelTitle.setBackground(new java.awt.Color(133, 102, 170));

        jLabelTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(64, 186, 213));
        jLabelTitle.setText("Lägg till bok  ");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo libro.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo letras libro.png"))); // NOI18N

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTitle)
                .addContainerGap())
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                        .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
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

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        
        QueryMethods qMethods = new QueryMethods();
        String title = jFieldTitle.getText();
        String author = jFieldAuthor.getText();
        String isbn = txtISBN.getText();
        String publisher = jFieldPublisher.getText();
        double purchasePrice = Double.parseDouble(jFieldPurchasePrice.getText());
        String bookType = boxType.getSelectedItem().toString().toLowerCase();
        String category = boxCategory.getSelectedItem().toString();
        
        if(bookType.equals("bok"))
        {
            Books b = new Books(title, author, isbn, publisher, purchasePrice, category);
            qMethods.addBook(b);
        }
        else
        {
            E_Books b = new E_Books(title, author, isbn, publisher, purchasePrice, category);
            qMethods.addEBook(b);
        }
    }//GEN-LAST:event_jLabel1MouseClicked
    
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
    private javax.swing.JPasswordField jFieldAuthor;
    private javax.swing.JPasswordField jFieldDescription;
    private javax.swing.JPasswordField jFieldLocation;
    private javax.swing.JPasswordField jFieldPublisher;
    private javax.swing.JPasswordField jFieldPurchasePrice;
    private javax.swing.JTextField jFieldTitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelAuthor;
    private javax.swing.JLabel jLabelBackgroundPhoto;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelISBN;
    private javax.swing.JLabel jLabelLocation;
    private javax.swing.JLabel jLabelPublisher;
    private javax.swing.JLabel jLabelPurchaseprice;
    private javax.swing.JLabel jLabelTitel;
    private javax.swing.JLabel jLabelTitel1;
    private javax.swing.JLabel jLabelTitel2;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelAddBook;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JPasswordField txtISBN;
    // End of variables declaration//GEN-END:variables
}
