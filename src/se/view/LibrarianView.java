/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
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
    private String[] colNames = {"Id", "Titel", "Författare", "ISBN", "Förlag", "Inköp Pris", "Kategori", "Placering"};
    private DefaultTableModel model = new DefaultTableModel(colNames, 0);
    private QueryMethods qMethods = new QueryMethods();
    private ArrayList<Books> books;
    private ArrayList<DeletedBook> deletedBook;
    private String librarianEmail;
    /**
     * Creates new form StartPage1
     */
    public LibrarianView() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        jPanelInvisible.setVisible(false);

        queryMethods = new QueryMethods();
        books = qMethods.findBooks();

        fillBooksTable();
        fillUsersTable();

        jbtnManageCards.setToolTipText("Tryck här för att redigera lånekort");
        UsersTable.setToolTipText("Tryck på funktionknappen för att redigera");

    }
    public LibrarianView(String librarianEmail) {
        initComponents();
        setLocationRelativeTo(null);
        jPanelInvisible.setVisible(false);
        setResizable(false);
        this.librarianEmail = librarianEmail;

        queryMethods = new QueryMethods();
        books = qMethods.findBooks();

        fillBooksTable();
        fillUsersTable();

        jbtnManageCards.setToolTipText("Tryck här för att redigera lånekort");
        UsersTable.setToolTipText("Tryck på funktionknappen för att redigera");
        jLabelTitle.setText("Inloggad Bibliotekarie: "+ librarianFullName());
        

    }
    
    public String librarianFullName(){
        String librarianFirstName = "";
        String librarianLastName = "";
       
        for (int i = 0 ; i < qMethods.findLibrarians().size() ; i ++){
        if ( qMethods.findLibrarians().get(i).getEmail().equals(this.librarianEmail) ){
            librarianFirstName = qMethods.findLibrarians().get(i).getFirstName();
            librarianLastName = qMethods.findLibrarians().get(i).getLastName();
        }
        }
        String firstName = librarianFirstName.substring(0, 1).toUpperCase() 
                            + librarianFirstName.substring(1);
        
        String lastName = librarianLastName.substring(0, 1).toUpperCase() 
                            + librarianLastName.substring(1);
        return  firstName + " " + lastName ;
    }

    public void fillBooksTable() {
        //ArrayList<Books> books = qMethods.findBooks();
        books = queryMethods.findBooks();
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        //model = (DefaultTableModel) BooksTable.getModel();
        model.setRowCount(0);
        for (int i = 0; i < books.size(); i++) {
            model.addRow(new Object[]{books.get(i).getId(), books.get(i).getTitle(), books.get(i).getAuthor(),
                books.get(i).getIsbn(), books.get(i).getPublisher(), books.get(i).getPurchase_price(), books.get(i).getCategory(),books.get(i).getPlacement()});
        }
        BooksTable.setModel(model);
        BooksTable.setRowSelectionAllowed(true);

    }
    public void fillBookLogTable() {
        deletedBook = qMethods.findDeletedBooks();

 

        DefaultTableModel defaultModel = new DefaultTableModel(colNames, 0);

 

        defaultModel.setRowCount(0);
        for (int i = 0; i < deletedBook.size(); i++) {
            defaultModel.addRow(new Object[]{deletedBook.get(i).getId(),
                deletedBook.get(i).getTitle(),
                deletedBook.get(i).getAuthor(),
                deletedBook.get(i).getBookType(),
                deletedBook.get(i).getIsbn(),
                deletedBook.get(i).getPurchasePrice(),
                deletedBook.get(i).getCategory(),
                deletedBook.get(i).getPublisher(),
                deletedBook.get(i).getPlacement(),
                deletedBook.get(i).getNotes()});
        }

 

        BooksTable.setModel(defaultModel);
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
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTabbedPaneReport3 = new javax.swing.JTabbedPane();
        jPanelTabBookings3 = new javax.swing.JPanel();
        jLabelSearchBookingsText3 = new javax.swing.JLabel();
        Usertxt = new javax.swing.JTextField();
        jLabelSearchUsersIcon3 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        UsersTable = new javax.swing.JTable();
        jbtnShowBorrowedBooks = new javax.swing.JButton();
        jbtnBlockedCards = new javax.swing.JButton();
        jbtnManageCards = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jPanelTabLendings = new javax.swing.JPanel();
        jLabelSearchLendingText = new javax.swing.JLabel();
        Bookstxt = new javax.swing.JTextField();
        jLabelSearchBooksIcon = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        BooksTable = new javax.swing.JTable();
        NewBookbtn = new javax.swing.JButton();
        DeleteBookbtn = new javax.swing.JButton();
        jPanelInvisible = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListBorrowedBooks = new javax.swing.JList<>();
        jbtnReturn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1719, 795));

        jPanelbackground.setBackground(new java.awt.Color(244, 244, 244));

        jLabelBackgroundPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/LibrarianPageBooks.jpg"))); // NOI18N

        jPanelTitle.setBackground(new java.awt.Color(105, 131, 170));
        jPanelTitle.setPreferredSize(new java.awt.Dimension(1719, 159));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 829, Short.MAX_VALUE)
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
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPaneReport3.setForeground(new java.awt.Color(105, 131, 170));
        jTabbedPaneReport3.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N

        jLabelSearchBookingsText3.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchBookingsText3.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchBookingsText3.setText("Sök");

        jLabelSearchUsersIcon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchUsersIcon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchUsersIcon3MouseClicked(evt);
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
        jScrollPane7.setViewportView(UsersTable);

        jbtnShowBorrowedBooks.setBackground(new java.awt.Color(244, 244, 244));
        jbtnShowBorrowedBooks.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jbtnShowBorrowedBooks.setText("Visa Lånade Böcker");
        jbtnShowBorrowedBooks.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        jbtnShowBorrowedBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnShowBorrowedBooksActionPerformed(evt);
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

        jbtnManageCards.setBackground(new java.awt.Color(244, 244, 244));
        jbtnManageCards.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jbtnManageCards.setText("Hantera Lånekort");
        jbtnManageCards.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        jbtnManageCards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnManageCardsActionPerformed(evt);
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

        javax.swing.GroupLayout jPanelTabBookings3Layout = new javax.swing.GroupLayout(jPanelTabBookings3);
        jPanelTabBookings3.setLayout(jPanelTabBookings3Layout);
        jPanelTabBookings3Layout.setHorizontalGroup(
            jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabBookings3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabBookings3Layout.createSequentialGroup()
                        .addComponent(jbtnShowBorrowedBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnBlockedCards, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnManageCards, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookings3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelSearchBookingsText3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Usertxt, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSearchUsersIcon3)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookings3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanelTabBookings3Layout.setVerticalGroup(
            jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookings3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchUsersIcon3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Usertxt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchBookingsText3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTabBookings3Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnShowBorrowedBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnBlockedCards, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnManageCards, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68))
        );

        jTabbedPaneReport3.addTab("Användare", jPanelTabBookings3);

        jLabelSearchLendingText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchLendingText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchLendingText.setText("Sök");

        jLabelSearchBooksIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N

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
        jScrollPane8.setViewportView(BooksTable);

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

        javax.swing.GroupLayout jPanelTabLendingsLayout = new javax.swing.GroupLayout(jPanelTabLendings);
        jPanelTabLendings.setLayout(jPanelTabLendingsLayout);
        jPanelTabLendingsLayout.setHorizontalGroup(
            jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                        .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                                .addComponent(NewBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DeleteBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelSearchLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Bookstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSearchBooksIcon)))
                .addContainerGap())
        );
        jPanelTabLendingsLayout.setVerticalGroup(
            jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchBooksIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Bookstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(NewBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(DeleteBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69))
        );

        jTabbedPaneReport3.addTab("Böcker", jPanelTabLendings);

        btnClose.setText("Stäng");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jListBorrowedBooks.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListBorrowedBooks);

        jbtnReturn.setText("Återlämna");
        jbtnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelInvisibleLayout = new javax.swing.GroupLayout(jPanelInvisible);
        jPanelInvisible.setLayout(jPanelInvisibleLayout);
        jPanelInvisibleLayout.setHorizontalGroup(
            jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInvisibleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInvisibleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnReturn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClose))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanelInvisibleLayout.setVerticalGroup(
            jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInvisibleLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(jbtnReturn))
                .addGap(68, 68, 68))
        );

        javax.swing.GroupLayout jPanelbackgroundLayout = new javax.swing.GroupLayout(jPanelbackground);
        jPanelbackground.setLayout(jPanelbackgroundLayout);
        jPanelbackgroundLayout.setHorizontalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelBackgroundPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelInvisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPaneReport3, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(714, Short.MAX_VALUE))
        );
        jPanelbackgroundLayout.setVerticalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelbackgroundLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTabbedPaneReport3, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelBackgroundPhoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelbackgroundLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jPanelInvisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
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

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel2MouseClicked



    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void DeleteBookbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBookbtnActionPerformed

        Scanner sc = new Scanner(System.in);
        
        int selection = BooksTable.getSelectedRow();
        String stringId = BooksTable.getModel().getValueAt(selection, 0).toString();
        System.out.println(stringId);
        int id = Integer.parseInt(stringId);
        for (Books b : books) {
            if (b.getId() == id) {
                
                String notes = JOptionPane.showInputDialog(null, "Snälla berätta anlädning för att radera bok");                
                queryMethods.deleteBook(b, notes);
                fillBookLogTable();

            }
            fillBooksTable();
        }
    }//GEN-LAST:event_DeleteBookbtnActionPerformed

    private void NewBookbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewBookbtnActionPerformed
        // TODO add your handling code here:
        AddBook addBook = new AddBook();
        this.setVisible(false);
        addBook.setVisible(true);
    }//GEN-LAST:event_NewBookbtnActionPerformed

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

    }//GEN-LAST:event_jbtnManageCardsActionPerformed

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
        UsersTable.setToolTipText(null);
    }//GEN-LAST:event_jbtnBlockedCardsActionPerformed
    
   
    private void jbtnShowBorrowedBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnShowBorrowedBooksActionPerformed
        // TODO add your handling code here:
        if(UsersTable.getSelectedRow() == -1){
        JOptionPane.showMessageDialog(this, "Du har inte valt användare");
        }else{
        DefaultListModel list = new DefaultListModel();
        jListBorrowedBooks.setModel(list);
        String lineDivision = "";
        
        int cardID = (int) UsersTable.getValueAt(UsersTable.getSelectedRow(), 0);
        
        for (int i = 0; i < qMethods.getBorrowedBooksByCardId(cardID).size(); i++) {
                            
                lineDivision = "<html>"+qMethods.getBorrowedBooksByCardId(cardID).get(i).getTitle() +  "<br>" +
                     qMethods.getBorrowedBooksByCardId(cardID).get(i).getAuthor() + "<br>" +
                     "ISBN: "+ qMethods.getBorrowedBooksByCardId(cardID).get(i).getIsbn()+ "<br>" +
                     "Återlämning: "+qMethods.getAllBorrowedBooks().get(i).getReturnDate().toString()+"<br/>" ;
                
            list.addElement(lineDivision) ;
            
            
            
        }
            if(list.isEmpty()){
                JOptionPane.showMessageDialog(this, "Användare har inga böcker");
                jPanelInvisible.setVisible(false);
            }else{
                  jPanelInvisible.setVisible(true);
            }
        }
    }//GEN-LAST:event_jbtnShowBorrowedBooksActionPerformed

    private void jLabelSearchUsersIcon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchUsersIcon3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelSearchUsersIcon3MouseClicked

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
    jPanelInvisible.setVisible(false);

    }//GEN-LAST:event_btnCloseActionPerformed

    private void jbtnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReturnActionPerformed
        // TODO add your handling code here:
        if (jListBorrowedBooks.getSelectedValue() == null){
            JOptionPane.showMessageDialog(this, "Du har inte valt en bok");
        }else{
        String bookInfo = jListBorrowedBooks.getSelectedValue();
        
        for (int i = 0 ; i < queryMethods.getAllBooks().size() ; i ++){
            if(bookInfo.contains(queryMethods.getAllBooks().get(i).getIsbn())){
                queryMethods.returnBook(queryMethods.getAllBooks().get(i).getId());
            }
        }
        jbtnShowBorrowedBooks.doClick();
    }
    }//GEN-LAST:event_jbtnReturnActionPerformed



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
    private javax.swing.JTable UsersTable;
    private javax.swing.JTextField Usertxt;
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelBackgroundPhoto;
    private javax.swing.JLabel jLabelSearchBookingsText3;
    private javax.swing.JLabel jLabelSearchBooksIcon;
    private javax.swing.JLabel jLabelSearchLendingText;
    private javax.swing.JLabel jLabelSearchUsersIcon3;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JList<String> jListBorrowedBooks;
    private javax.swing.JPanel jPanelInvisible;
    private javax.swing.JPanel jPanelTabBookings3;
    private javax.swing.JPanel jPanelTabLendings;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPaneReport3;
    private javax.swing.JButton jbtnBlockedCards;
    private javax.swing.JButton jbtnManageCards;
    private javax.swing.JButton jbtnReturn;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JButton jbtnShowBorrowedBooks;
    // End of variables declaration//GEN-END:variables
}
