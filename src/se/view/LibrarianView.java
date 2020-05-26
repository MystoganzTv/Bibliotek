/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.SelectionMode;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import se.database.QueryMethods;
import se.main.Validation;
import se.model.Admin;
import se.model.Guest;
import se.model.Books;
import se.model.DeletedBook;
import se.model.E_Books;
import se.model.LibraryCards;

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
    private ArrayList<E_Books> eBooks;
    private ArrayList<DeletedBook> deletedBook;
    private String librarianEmail;
    private DefaultListModel returnBooksListModel = new DefaultListModel();
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
        eBooks = qMethods.findEBooks();

        fillBooksTable();
        fillUsersTable();

        jbtnManageCards.setToolTipText("Tryck här för att redigera lånekort");
        UsersTable.setToolTipText("Tryck på funktionknappen för att redigera");
        
        returnBooksList.setSelectionMode(1);
        returnBooksList.setModel(returnBooksListModel);

    }
    public LibrarianView(String librarianEmail) {
        initComponents();
        setLocationRelativeTo(null);
        jPanelInvisible.setVisible(false);
        setResizable(false);
        this.librarianEmail = librarianEmail;

        queryMethods = new QueryMethods();
        books = qMethods.findBooks();
        eBooks = qMethods.findEBooks();

        fillBooksTable();
        fillUsersTable();

        jbtnManageCards.setToolTipText("Tryck här för att redigera lånekort");
        UsersTable.setToolTipText("Tryck på funktionknappen för att redigera");
        jLabelTitle.setText("Inloggad Bibliotekarie: "+ librarianFullName());
        
        returnBooksList.setSelectionMode(1);
        returnBooksList.setModel(returnBooksListModel);

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
        UsersTable.getColumnModel().getColumn(3).setHeaderValue("Anledning");

        UsersTable.getColumn("Id").setPreferredWidth(25);
        UsersTable.getColumn("Namn").setPreferredWidth(100);
        UsersTable.getColumn("Spärrad").setPreferredWidth(60);
        UsersTable.getColumn("Anledning").setPreferredWidth(150);

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
        returnBooksPanel = new javax.swing.JTabbedPane();
        jPanelTabBookings3 = new javax.swing.JPanel();
        jLabelSearchBookingsText3 = new javax.swing.JLabel();
        Usertxt = new javax.swing.JTextField();
        jLabelSearchUsersIcon3 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        UsersTable = new javax.swing.JTable();
        jbtnShowBorrowedBooks = new javax.swing.JButton();
        jbtnBlockedCards = new javax.swing.JButton();
        jbtnManageCards = new javax.swing.JButton();
        jbtnRestore = new javax.swing.JButton();
        jPanelTabLendings = new javax.swing.JPanel();
        jLabelSearchLendingText = new javax.swing.JLabel();
        Bookstxt = new javax.swing.JTextField();
        jLabelSearchBooksIcon = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        BooksTable = new javax.swing.JTable();
        NewBookbtn = new javax.swing.JButton();
        DeleteBookbtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        bookIdTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        returnBooksList = new javax.swing.JList<>();
        addBookToReturnListButton = new javax.swing.JButton();
        returnBooksInListButton = new javax.swing.JButton();
        removeFromReturnBooksList = new javax.swing.JButton();
        jPanelInvisible = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListBorrowedBooks = new javax.swing.JList<>();
        jbtnReturn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        returnBooksPanel.setForeground(new java.awt.Color(105, 131, 170));
        returnBooksPanel.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N

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
        jbtnManageCards.setText("Spärra Lånekort");
        jbtnManageCards.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        jbtnManageCards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnManageCardsActionPerformed(evt);
            }
        });

        jbtnRestore.setBackground(new java.awt.Color(244, 244, 244));
        jbtnRestore.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jbtnRestore.setText("Återställa Lånekort");
        jbtnRestore.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        jbtnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRestoreActionPerformed(evt);
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
                        .addComponent(jLabelSearchBookingsText3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Usertxt, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSearchUsersIcon3))
                    .addGroup(jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanelTabBookings3Layout.createSequentialGroup()
                            .addComponent(jbtnShowBorrowedBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(115, 115, 115)
                            .addComponent(jbtnManageCards, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(117, 117, 117)
                            .addComponent(jbtnBlockedCards, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(117, 117, 117)
                            .addComponent(jbtnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 913, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanelTabBookings3Layout.setVerticalGroup(
            jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookings3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchUsersIcon3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Usertxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSearchBookingsText3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTabBookings3Layout.createSequentialGroup()
                        .addGroup(jPanelTabBookings3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnShowBorrowedBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnBlockedCards, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnManageCards, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68))
                    .addGroup(jPanelTabBookings3Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136))))
        );

        returnBooksPanel.addTab("Användare", jPanelTabBookings3);

        jLabelSearchLendingText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchLendingText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchLendingText.setText("Sök");

        jLabelSearchBooksIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchBooksIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchBooksIconMouseClicked(evt);
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
                        .addComponent(NewBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteBookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 699, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                        .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                                .addComponent(jLabelSearchLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Bookstxt)))
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
                    .addComponent(Bookstxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSearchLendingText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        returnBooksPanel.addTab("Böcker", jPanelTabLendings);

        jLabel1.setText("Bok ID:");

        jScrollPane1.setViewportView(returnBooksList);

        addBookToReturnListButton.setText("Ok");
        addBookToReturnListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookToReturnListButtonActionPerformed(evt);
            }
        });

        returnBooksInListButton.setText("Lämna tillbaka");
        returnBooksInListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnBooksInListButtonActionPerformed(evt);
            }
        });

        removeFromReturnBooksList.setText("Ångra");
        removeFromReturnBooksList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFromReturnBooksListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addBookToReturnListButton, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(bookIdTextField))
                .addGap(102, 102, 102)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(removeFromReturnBooksList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(returnBooksInListButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(391, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bookIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addBookToReturnListButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnBooksInListButton)
                    .addComponent(removeFromReturnBooksList))
                .addContainerGap(165, Short.MAX_VALUE))
        );

        returnBooksPanel.addTab("Återlämning", jPanel1);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
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
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelBackgroundPhoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(returnBooksPanel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelInvisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelbackgroundLayout.setVerticalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelbackgroundLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelBackgroundPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(returnBooksPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelbackgroundLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanelInvisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jbtnManageCardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnManageCardsActionPerformed
        // TODO add your handling code here:
         UsersTable.setToolTipText("Du kan nu redigera kolumnera Spärrad och Kategori");
        String[] blocked = { "Många sena böcker", "Många försvunna böcker", "Stöld", "Andra" };
        String selectedValue = (String)JOptionPane.showInputDialog( null, "Ange Anledning", "Spärra Lånekort",
        JOptionPane.QUESTION_MESSAGE, null, blocked, blocked[ 3 ] );
        int userId = (int) UsersTable.getValueAt(UsersTable.getSelectedRow(), 0);

        JComboBox blockedBox = new JComboBox(blocked);
        UsersTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(blockedBox));
        
        
   queryMethods.updateLibraryCards(1, userId, selectedValue);
                    

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
        UsersTable.getColumnModel().getColumn(2).setHeaderValue("Anledning");
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
                            
                lineDivision = "<html>"+qMethods.getBorrowedBooksByCardId(cardID).get(i).getId()+ ". "
                        +qMethods.getBorrowedBooksByCardId(cardID).get(i).getTitle() +  "<br>" +
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
        
         ArrayList<Guest> guests = queryMethods.findGuests();
         String userToFind = Usertxt.getText().toLowerCase().trim();
         String[] columns = {"ID", "Namn","Spärrad", "category" };
         ArrayList<LibraryCards> cards = new ArrayList<>();
         DefaultTableModel model = new DefaultTableModel(columns,0);
         
        if (!userToFind.isEmpty()) {
            ArrayList<Guest> foundGuests = new ArrayList<>();

            guests.stream().filter((g) -> g.getLastName().toLowerCase().contains(userToFind)
                    || g.getFirstName().toLowerCase().contains(userToFind) || g.getEmail().equalsIgnoreCase(userToFind)
                    || g.getPersonId().equals(userToFind)).forEach(foundGuests::add);

            if (!foundGuests.isEmpty()) {
                
                cards = queryMethods.getGuestsLibraryCardsByGuestList(foundGuests);
                for(LibraryCards card : cards){
                 String entry = "";
                 
                 if(card.getEntry() == 1){
                     entry = "Ja";
                 }else {
                     entry = "Nej";
                 }
                model.addRow(new Object[]{card.getGuestId(), card.getFullname(), entry, card.getCategory()});
                }
                
                }
                UsersTable.setModel(model);
                Usertxt.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ingen användare kunde hittas");

            }
        
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
        int indexOfPoint = jListBorrowedBooks.getSelectedValue().indexOf(".");
        String bookInfo = jListBorrowedBooks.getSelectedValue().substring(6, indexOfPoint);
        int bookId = Integer.parseInt(bookInfo);
        queryMethods.returnBook(bookId);
//        for (int i = 0 ; i < queryMethods.getAllBooks().size() ; i ++){
//            if(bookInfo.contains(queryMethods.getAllBooks().get(i).getIsbn())){
//                queryMethods.returnBook(queryMethods.getAllBooks().get(i).getId());
//            }
//        }
        
        jbtnShowBorrowedBooks.doClick();
    }
    }//GEN-LAST:event_jbtnReturnActionPerformed

    private void addBookToReturnListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookToReturnListButtonActionPerformed
        // TODO add your handling code here:
        
        if(Validation.isValidID(bookIdTextField.getText().trim())){
            Books book = queryMethods.findBorrowedBookById(Integer.parseInt(bookIdTextField.getText().trim()));
            if(book.getId() != -1){
               returnBooksListModel.addElement(book);
               bookIdTextField.setText("");
                
            }else {
                JOptionPane.showMessageDialog(this, "Kunde inte hitta utlånad bok med id: " +  bookIdTextField.getText());
            }
        }else{
            JOptionPane.showMessageDialog(this, "Vänligen fyll i ett korrekt ID");
        }
    }//GEN-LAST:event_addBookToReturnListButtonActionPerformed

    private void returnBooksInListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnBooksInListButtonActionPerformed
        
        for(int i = 0; i < returnBooksListModel.getSize(); i++){
           // String element = returnBooksListModel.getElementAt(i).toString();
           Books book = (Books)returnBooksListModel.getElementAt(i);
           
           queryMethods.returnBook(book.getId());
           
        }
        returnBooksListModel.clear();
    }//GEN-LAST:event_returnBooksInListButtonActionPerformed

    private void removeFromReturnBooksListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFromReturnBooksListActionPerformed
        
        int index = returnBooksList.getSelectedIndex();
        
        returnBooksListModel.remove(index);
    }//GEN-LAST:event_removeFromReturnBooksListActionPerformed

    private void jLabelSearchBooksIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchBooksIconMouseClicked

       
        ArrayList<Books> foundBooks = new ArrayList<>();
        ArrayList<E_Books> foundEBooks = new ArrayList<>();
        
        String searchWord = Bookstxt.getText().toLowerCase();
        
        books.stream().filter((b)-> b.getAuthor().contains(searchWord) || b.getTitle().contains(searchWord)
                                || b.getCategory().equals(searchWord) || b.getIsbn().equals(searchWord)
                                ).forEach(foundBooks::add);
       
                                
        eBooks.stream().filter((eBook)-> eBook.getAuthor().contains(searchWord) || eBook.getTitle().contains(searchWord)
                                || eBook.getCategory().equals(searchWord) || eBook.getIsbn().equals(searchWord)
                                    ).forEach(foundEBooks::add);
        
        DefaultTableModel searchModel = new DefaultTableModel(colNames, 0);
        
           //private String[] colNames = {"Id", "Titel", "Författare", "ISBN", "Förlag", "Inköp Pris", "Kategori", "Placering"};
        for(Books b : foundBooks){
            searchModel.addRow(new Object[]{b.getId(), b.getTitle(),b.getAuthor(),b.getIsbn(),b.getPublisher(), b.getPurchase_price(), b.getCategory(), b.getPlacement()});
        }
        for(E_Books eBook : foundEBooks){
            searchModel.addRow(new Object[]{eBook.getId(), eBook.getTitle(),eBook.getAuthor(),eBook.getIsbn(),eBook.getPublisher(), eBook.getPurchase_price(), eBook.getCategory(), "E-Bok"});
        }
        
        BooksTable.setModel(searchModel);
        


    }//GEN-LAST:event_jLabelSearchBooksIconMouseClicked

    private void jbtnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRestoreActionPerformed
        // TODO add your handling code here:
        String[] restore = { "Vill du återställa Lånekortet?"  };
        String selectedValue = (String)JOptionPane.showInputDialog( null, " ", "Återställa Lånekort",
        JOptionPane.QUESTION_MESSAGE, null, restore, restore[ 0 ] );
        int userId = (int) UsersTable.getValueAt(UsersTable.getSelectedRow(), 0);

        JComboBox blockedBox = new JComboBox(restore);
        UsersTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(blockedBox));
        
        
   queryMethods.updateLibraryCards(0, userId, selectedValue);
                    

        
    }//GEN-LAST:event_jbtnRestoreActionPerformed



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
    private javax.swing.JButton addBookToReturnListButton;
    private javax.swing.JTextField bookIdTextField;
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelInvisible;
    private javax.swing.JPanel jPanelTabBookings3;
    private javax.swing.JPanel jPanelTabLendings;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JButton jbtnBlockedCards;
    private javax.swing.JButton jbtnManageCards;
    private javax.swing.JButton jbtnRestore;
    private javax.swing.JButton jbtnReturn;
    private javax.swing.JButton jbtnShowBorrowedBooks;
    private javax.swing.JButton removeFromReturnBooksList;
    private javax.swing.JButton returnBooksInListButton;
    private javax.swing.JList<String> returnBooksList;
    private javax.swing.JTabbedPane returnBooksPanel;
    // End of variables declaration//GEN-END:variables
}
