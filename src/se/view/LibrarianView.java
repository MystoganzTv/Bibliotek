/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import se.database.QueryMethods;
import se.model.Admin;
import se.model.Guest;
import se.model.Books;
import se.model.DeletedBook;

/**
 *
 * @author enriq
 */
public class LibrarianView extends javax.swing.JFrame {

    private QueryMethods queryMethods;
    private String[] colNames = {"Titel", "Författare", "ISBN", "Förlag", "Inköp Pris", "Kategori", "Placering"};
    private DefaultTableModel model = new DefaultTableModel(colNames, 0);
    private QueryMethods qMethods = new QueryMethods();
    private ArrayList<Books> books;

    /**
     * Creates new form StartPage1
     */
    public LibrarianView() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        queryMethods = new QueryMethods();
        books = qMethods.findBooks();

        fillBooksTable();
        fillUsersTable();

        jbtnManageCards.setToolTipText("Tryck här för att redigera lånekort");
        UsersTable.setToolTipText("Tryck på funktionknappen för att redigera");

    }

    public void fillBooksTable() {
        //ArrayList<Books> books = qMethods.findBooks();
        books = queryMethods.findBooks();
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        //model = (DefaultTableModel) BooksTable.getModel();
        model.setRowCount(0);
        for (int i = 0; i < books.size(); i++) {
            model.addRow(new Object[]{books.get(i).getTitle(), books.get(i).getAuthor(),
                books.get(i).getIsbn(), books.get(i).getPublisher(), books.get(i).getPurchase_price(), books.get(i).getCategory(),books.get(i).getPlacement()});
        }
        BooksTable.setModel(model);
        BooksTable.setRowSelectionAllowed(true);

    }

    public void fillUsersTable() {
        model = (DefaultTableModel) UsersTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(4);

        for (int i = 0; i < qMethods.getAllCards().size(); i++) {
            model.addRow(new Object[]{qMethods.getAllCards().get(i).getGuestId(), qMethods.getAllCards().get(i).getFullname(),
                qMethods.getAllCards().get(i).getEntry(), qMethods.getAllCards().get(i).getCategory()});

            if (qMethods.getAllCards().get(i).getEntry() == 1) {
                UsersTable.setValueAt("Ja", i, 2);
            } else {
                UsersTable.setValueAt("Nej", i, 2);
            }
        }

        UsersTable.getColumnModel().getColumn(0).setHeaderValue("Id");
        UsersTable.getColumnModel().getColumn(1).setHeaderValue("Namn");
        UsersTable.getColumnModel().getColumn(2).setHeaderValue("Spärrad");
        UsersTable.getColumnModel().getColumn(3).setHeaderValue("Kategori");

        UsersTable.getColumn("Id").setPreferredWidth(25);
        UsersTable.getColumn("Namn").setPreferredWidth(100);
        UsersTable.getColumn("Spärrad").setPreferredWidth(60);
        UsersTable.getColumn("Kategori").setPreferredWidth(150);

    }
    
    public void searchBooksResult(String searchWord){
        String wordToMatch = searchWord.toLowerCase();
        ArrayList<Books> foundBooks = new ArrayList<>();
        
        books.stream().filter((b) -> b.getTitle().toLowerCase().contains(wordToMatch) || b.getAuthor().toLowerCase().equals(wordToMatch)
                                || b.getCategory().toLowerCase().equals(wordToMatch)).forEach(foundBooks::add);
        
        
        if(!foundBooks.isEmpty()){
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        
        for(Books b : foundBooks){
            model.addRow(new Object[]{b.getTitle(),b.getAuthor(), b.getIsbn(),b.getPublisher(),b.getPurchase_price(),b.getCategory(),b.getPlacement()});
        }
        BooksTable.setModel(model);
        
        }else{
            JOptionPane.showMessageDialog(this,"Kunde inte hitta bok");
            
        }
        
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TitleUser = new javax.swing.JLabel();
        Usertxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        UsersTable = new javax.swing.JTable();
        NewUserbtn = new javax.swing.JButton();
        jbtnManageCards = new javax.swing.JButton();
        jbtnBlockedCards = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jlblTableTitle = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        TitleBooks = new javax.swing.JLabel();
        Bookstxt = new javax.swing.JTextField();
        NewBookbtn = new javax.swing.JButton();
        DeleteBookbtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        BooksTable = new javax.swing.JTable();
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelbackground.setBackground(new java.awt.Color(244, 244, 244));

        jLabelBackgroundPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/LibrarianPageBooks.jpg"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));

        jPanel2.setBackground(new java.awt.Color(244, 244, 244));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Användaren", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18), new java.awt.Color(105, 131, 170))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/key_24px.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/lock_24px.png"))); // NOI18N

        TitleUser.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        TitleUser.setText("Sök användare:");

        Usertxt.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        Usertxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsertxtActionPerformed(evt);
            }
        });

        UsersTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(UsersTable);

        NewUserbtn.setBackground(new java.awt.Color(244, 244, 244));
        NewUserbtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        NewUserbtn.setText("Lägga ny Användare");
        NewUserbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        NewUserbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewUserbtnActionPerformed(evt);
            }
        });

        jbtnManageCards.setBackground(new java.awt.Color(244, 244, 244));
        jbtnManageCards.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jbtnManageCards.setText("Handera Lånekort");
        jbtnManageCards.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        jbtnManageCards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnManageCardsActionPerformed(evt);
            }
        });

        jbtnBlockedCards.setBackground(new java.awt.Color(244, 244, 244));
        jbtnBlockedCards.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jbtnBlockedCards.setText("Spärrade Lånekort");
        jbtnBlockedCards.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        jbtnBlockedCards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBlockedCardsActionPerformed(evt);
            }
        });

        jbtnSave.setBackground(new java.awt.Color(244, 244, 244));
        jbtnSave.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jbtnSave.setText("Spara Ändring");
        jbtnSave.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jlblTableTitle.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jbtnBlockedCards, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89)
                                .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(NewUserbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89)
                                .addComponent(jbtnManageCards, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(TitleUser)
                        .addGap(26, 26, 26)
                        .addComponent(Usertxt, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TitleUser)
                            .addComponent(Usertxt, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NewUserbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnManageCards, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnBlockedCards, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        jPanel4.setBackground(new java.awt.Color(244, 244, 244));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Böcker", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18), new java.awt.Color(105, 131, 170))); // NOI18N

        TitleBooks.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        TitleBooks.setText("Sök bok:");

        Bookstxt.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        Bookstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookstxtActionPerformed(evt);
            }
        });

        NewBookbtn.setBackground(new java.awt.Color(244, 244, 244));
        NewBookbtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        NewBookbtn.setText("Lägga till");
        NewBookbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        NewBookbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewBookbtnActionPerformed(evt);
            }
        });

        DeleteBookbtn.setBackground(new java.awt.Color(244, 244, 244));
        DeleteBookbtn.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        DeleteBookbtn.setText("Radera");
        DeleteBookbtn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        DeleteBookbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBookbtnActionPerformed(evt);
            }
        });

        BooksTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(BooksTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(NewBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(DeleteBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(TitleBooks)
                        .addGap(53, 53, 53)
                        .addComponent(Bookstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Bookstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TitleBooks))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(157, Short.MAX_VALUE))
        );

        jPanel4.getAccessibleContext().setAccessibleName("Böker");

        jPanelTitle.setBackground(new java.awt.Color(105, 131, 170));

        jLabelTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabelTitle.setText("Inloggad som Bibliotekarie");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo libro.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo letras libro.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/home_80px.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTitle)
                .addGap(38, 38, 38)
                .addComponent(jLabel5)
                .addGap(18, 18, 18))
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelbackgroundLayout = new javax.swing.GroupLayout(jPanelbackground);
        jPanelbackground.setLayout(jPanelbackgroundLayout);
        jPanelbackgroundLayout.setHorizontalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBackgroundPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelbackgroundLayout.setVerticalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelbackgroundLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBackgroundPhoto))
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
            .addComponent(jPanelbackground, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UsertxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsertxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsertxtActionPerformed

    private void NewUserbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewUserbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewUserbtnActionPerformed

    private void BookstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookstxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BookstxtActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void DeleteBookbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBookbtnActionPerformed

        int selection = BooksTable.getSelectedRow();
        String stringId = BooksTable.getModel().getValueAt(selection, 0).toString();
        System.out.println(stringId);
        int id = Integer.parseInt(stringId);

        for (Books b : books) {
            if (b.getId() == id) {
                System.out.println(b.getTitle());
                queryMethods.deleteBook(b);

            }
            fillBooksTable();
        }
    }//GEN-LAST:event_DeleteBookbtnActionPerformed

    private void jbtnBlockedCardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBlockedCardsActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel) UsersTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(3);
        for (int i = 0; i < qMethods.blockedCards().size(); i++) {
            model.addRow(new Object[]{qMethods.blockedCards().get(i).getGuestId(), qMethods.blockedCards().get(i).getFullname(),
                qMethods.blockedCards().get(i).getCategory()});
        }

        UsersTable.setRowSelectionAllowed(true);

        UsersTable.getColumnModel().getColumn(0).setHeaderValue("Id");
        UsersTable.getColumnModel().getColumn(1).setHeaderValue("Namn");
        UsersTable.getColumnModel().getColumn(2).setHeaderValue("Kategori");
        jlblTableTitle.setText("Spärrade Lånekort");
        UsersTable.setToolTipText(null);
    }//GEN-LAST:event_jbtnBlockedCardsActionPerformed


    private void jbtnManageCardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnManageCardsActionPerformed
        // TODO add your handling code here:
        fillUsersTable();
        UsersTable.setToolTipText("Du kan nu redigera kolumnera Spärrad och Kategori");
        String[] blocked = {"Ja", "Nej"};
        String[] category = {"", "Många sena böcker", "Många försvunna böcker", "Stöld"};
        JComboBox blockedBox = new JComboBox(blocked);
        JComboBox categoryBox = new JComboBox(category);
        UsersTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(blockedBox));
        UsersTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(categoryBox));
        jlblTableTitle.setText("Hantera kort");


    }//GEN-LAST:event_jbtnManageCardsActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        // TODO add your handling code here:
        boolean saved = false;

        for (int i = 0; i < UsersTable.getRowCount(); i++) {
            if (UsersTable.getValueAt(i, 2).equals("Ja")) {
                if (UsersTable.getValueAt(i, 3).equals("")) {
                    JOptionPane.showMessageDialog(this, "Välj kategori för användare Id: " + UsersTable.getValueAt(i, 0)
                            + " för att kunna spara");
                    return;

                } else {
                    qMethods.updateLibraryCards(1, (int) UsersTable.getValueAt(i, 0), UsersTable.getValueAt(i, 3).toString());
                    saved = true;
                }
            } else if (UsersTable.getValueAt(i, 2).equals("Nej")) {
                qMethods.updateLibraryCards(0, (int) UsersTable.getValueAt(i, 0), UsersTable.getValueAt(i, 3).toString());
                UsersTable.setValueAt("", i, 3);
                saved = true;

            }
        }

        if (saved == true) {
            JOptionPane.showMessageDialog(this, "Ändringarna har sparats");
        }


    }//GEN-LAST:event_jbtnSaveActionPerformed


    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void NewBookbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewBookbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewBookbtnActionPerformed



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
            java.util.logging.Logger.getLogger(LibrarianView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LibrarianView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LibrarianView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LibrarianView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LibrarianView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BooksTable;
    private javax.swing.JTextField Bookstxt;
    private javax.swing.JButton DeleteBookbtn;
    private javax.swing.JButton NewBookbtn;
    private javax.swing.JButton NewUserbtn;
    private javax.swing.JLabel TitleBooks;
    private javax.swing.JLabel TitleUser;
    private javax.swing.JTable UsersTable;
    private javax.swing.JTextField Usertxt;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelBackgroundPhoto;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnBlockedCards;
    private javax.swing.JButton jbtnManageCards;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JLabel jlblTableTitle;
    // End of variables declaration//GEN-END:variables
}
