/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import static java.time.Instant.now;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import se.database.QueryMethods;
import se.model.Books;
import se.model.BorrowedBooks;
import se.model.E_Books;

/**
 *
 * @author danny
 */
public class UserView extends javax.swing.JFrame {

    /**
     * Creates new form UserView
     */
    QueryMethods qm = new QueryMethods();
    private String guestEmail;
    
    public UserView() {
        initComponents();
        setLocationRelativeTo(null);
        jPanelInvisible.setVisible(false);
        fillSortimentTable();
        
        jTextFieldSearchSortiment.setToolTipText("Skriv titel, författare eller kategori");
        
        fillMyBorrowingsTable();
        
    }
    
    public UserView(String guestEmail){
        initComponents();
        setLocationRelativeTo(null);
        jPanelInvisible.setVisible(false);
        fillSortimentTable();
        
        jTextFieldSearchSortiment.setToolTipText("Skriv titel, författare eller kategori");
        
        this.guestEmail = guestEmail;

        fillMyBorrowingsTable();
        
        jLabelTitle.setText("Inloggad Gäst: "+ guestFullName());
    }
    
    public String guestFullName(){
        String guestFullName = "";
        int guestId =  qm.findLibrarycardByEmail(this.guestEmail).getId();
        for (int i = 0 ; i < qm.getAllCards().size() ; i ++){
        if ( qm.getAllCards().get(i).getGuestId() == guestId ){
            guestFullName = qm.getAllCards().get(i).getFullname();
        }
        }
        int indexOfSpace = guestFullName.indexOf(" ");
        String firstName = guestFullName.substring(0,1).toUpperCase() 
                            + guestFullName.substring(1, indexOfSpace);
        
        String lastName = guestFullName.substring(indexOfSpace + 1, indexOfSpace + 2).toUpperCase() 
                            + guestFullName.substring(indexOfSpace + 2);
        return  firstName + " " + lastName ;
    }
 
    public void fillSortimentTable() {
        DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();
        model.setRowCount(0);
        model.setColumnCount(8);
        
//        for (int i = 0; i < qm.getAllBooks().size(); i++) {
//            model.addRow(new Object[]{qm.getAllBooks().get(i).getTitle(), qm.getAllBooks().get(i).getAuthor(),
//                qm.getAllBooks().get(i).getIsbn(), qm.getAllBooks().get(i).getPublisher(), 
//                qm.getAllBooks().get(i).getCategory(), "Bok", qm.getAllBooks().get(i).getPlacement(),});
//        }

        String bookIsAvailable = "";
        ArrayList<Books> books = qm.groupAllBooksByIsbn();
        ArrayList<Books> booksByIsbn ;
        ArrayList<Integer> borrowedBooksId = new ArrayList<>();

        
        for (int i = 0 ; i < qm.getAllBorrowedBooks().size() ; i++){
            borrowedBooksId.add(qm.getAllBorrowedBooks().get(i).getBookId());
        }
        

        for (int i = 0; i < books.size(); i++) {

            model.addRow(new Object[]{books.get(i).getTitle(), books.get(i).getAuthor(),
                books.get(i).getIsbn(), books.get(i).getPublisher(), 
                books.get(i).getCategory(),"Bok", books.get(i).getPlacement()});
        }
        
        
        for (int i = 0 ; i < model.getRowCount() ; i++){
            String isbn = (String) model.getValueAt(i, 2);
            booksByIsbn = qm.findBooksByIsbn(isbn);
            boolean allIsBorrowed = false;
            
            for (int j = 0 ; j < booksByIsbn.size() ; j ++){
                
                if(!borrowedBooksId.contains(booksByIsbn.get(j).getId())){
                    allIsBorrowed = false;
                }else{
                    allIsBorrowed = true;
                }
                
                }
            
                if(allIsBorrowed){
                bookIsAvailable = "Nej";
                }else{
                bookIsAvailable = "Ja";
                }
            model.setValueAt(bookIsAvailable, i, 7);
        }
        

        for (int i = 0; i < qm.getAllEBooks().size(); i++) {
            model.addRow(new Object[]{qm.getAllEBooks().get(i).getTitle(), qm.getAllEBooks().get(i).getAuthor(),
                qm.getAllEBooks().get(i).getIsbn(), qm.getAllEBooks().get(i).getPublisher(), 
                qm.getAllEBooks().get(i).getCategory(), "E-Bok", "", "Ja"});
        }
        jtableSortiment.getColumnModel().getColumn(0).setHeaderValue("Titel");
        jtableSortiment.getColumnModel().getColumn(1).setHeaderValue("Författare");
        jtableSortiment.getColumnModel().getColumn(2).setHeaderValue("ISBN");
        jtableSortiment.getColumnModel().getColumn(3).setHeaderValue("Förlag");
        jtableSortiment.getColumnModel().getColumn(4).setHeaderValue("Kategori");
        jtableSortiment.getColumnModel().getColumn(5).setHeaderValue("Typ");
        jtableSortiment.getColumnModel().getColumn(6).setHeaderValue("Placering");
        jtableSortiment.getColumnModel().getColumn(7).setHeaderValue("Tillgänglig");

        jtableSortiment.getColumn("Kategori").setPreferredWidth(150);
        jtableSortiment.getColumn("Typ").setPreferredWidth(40);
        jtableSortiment.getColumn("Placering").setPreferredWidth(50);

    }
     
     public void fillMyBorrowingsTable() {
        DefaultTableModel model = (DefaultTableModel) jtableMyBorrowings.getModel();
        model.setRowCount(0);
        model.setColumnCount(4);
        int libraryCardId = qm.findLibrarycardByEmail(this.guestEmail).getId();
        ArrayList<Date> borrowedBooksDates = new ArrayList<>();
        Date toDayDate = new Date();
        String returnBookReminder = "";
        
        for (int i = 0 ; i < qm.getAllBorrowedBooks().size() ; i++){
            if(qm.getAllBorrowedBooks().get(i).getLibraryCardId() == libraryCardId){
                borrowedBooksDates.add(qm.getAllBorrowedBooks().get(i).getReturnDate());
            }
        }
        
        
        for (int i = 0; i < qm.getBorrowedBooksByCardId(libraryCardId).size(); i++) {
            int dayDiff = borrowedBooksDates.get(i).getDay() - toDayDate.getDay();
            int monthDiff = borrowedBooksDates.get(i).getMonth() - toDayDate.getMonth();
            if(dayDiff <= 0 && monthDiff <= 0){
                returnBookReminder =  "Försenad";
                System.out.println(returnBookReminder);
                
            }
            else if(dayDiff == 1 && monthDiff == 0){
                returnBookReminder = "1 dag kvar";
            }
            else if(dayDiff == 2 && monthDiff == 0){
                returnBookReminder = "2 dagar kvar";
            }
            
            model.addRow(new Object[]{qm.getBorrowedBooksByCardId(libraryCardId).get(i).getTitle(),
                                      qm.getBorrowedBooksByCardId(libraryCardId).get(i).getAuthor(),
                                      qm.getBorrowedBooksByCardId(libraryCardId).get(i).getIsbn(), 
                                      qm.getAllBorrowedBooks().get(i).getReturnDate() + "  " + returnBookReminder});
            
        }
        
//        for (int i = 0; i < qm.getAllEBooks().size(); i++) {
//            model.addRow(new Object[]{qm.getAllEBooks().get(i).getTitle(), qm.getAllEBooks().get(i).getAuthor(),
//                qm.getAllEBooks().get(i).getIsbn(), qm.getAllEBooks().get(i).getPublisher(), 
//                qm.getAllEBooks().get(i).getCategory(), "E-Bok"});
//        }
        jtableMyBorrowings.getColumnModel().getColumn(0).setHeaderValue("Titel");
        jtableMyBorrowings.getColumnModel().getColumn(1).setHeaderValue("Författare");
        jtableMyBorrowings.getColumnModel().getColumn(2).setHeaderValue("ISBN");
        jtableMyBorrowings.getColumnModel().getColumn(3).setHeaderValue("Återlämning");
        

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
        jPanel1 = new javax.swing.JPanel();
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanelInvisible = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        jLabelAboutBook = new javax.swing.JLabel();
        jTabbedPaneReport = new javax.swing.JTabbedPane();
        jPanelTabBookings = new javax.swing.JPanel();
        jLabelSearchBookingsText = new javax.swing.JLabel();
        jTextFieldSearchSortiment = new javax.swing.JTextField();
        jLabelSearchSortiment = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtableSortiment = new javax.swing.JTable();
        jLabelUpdateSortimentIcon = new javax.swing.JLabel();
        jLabelUpdateSortimentText = new javax.swing.JLabel();
        jbtnBorrow = new javax.swing.JButton();
        jbtnAboutBook = new javax.swing.JButton();
        jPanelTabLendings = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtableMyBorrowings = new javax.swing.JTable();
        jLabelUpdateMyBorrowingsIcon = new javax.swing.JLabel();
        jLabelUpdateMyBorrowingsText = new javax.swing.JLabel();
        jPanelTabStock = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        MyReservationsTable = new javax.swing.JTable();
        jLabelEraseMyReservationsIcon = new javax.swing.JLabel();
        jLabelEraseMyReservationsText = new javax.swing.JLabel();
        jLabelUpdateMyReservationsText = new javax.swing.JLabel();
        jLabelUpdateMyReservationsIcon = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelbackground.setBackground(new java.awt.Color(244, 244, 244));

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1309, Short.MAX_VALUE)
        );

        jPanelTitle.setBackground(new java.awt.Color(105, 131, 170));

        jLabelTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabelTitle.setText("Inloggad som Gäst");

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

        btnClose.setText("Stäng");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelInvisibleLayout = new javax.swing.GroupLayout(jPanelInvisible);
        jPanelInvisible.setLayout(jPanelInvisibleLayout);
        jPanelInvisibleLayout.setHorizontalGroup(
            jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInvisibleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addContainerGap())
            .addGroup(jPanelInvisibleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAboutBook, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanelInvisibleLayout.setVerticalGroup(
            jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInvisibleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelAboutBook, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnClose)
                .addGap(21, 21, 21))
        );

        jTabbedPaneReport.setForeground(new java.awt.Color(105, 131, 170));
        jTabbedPaneReport.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N

        jLabelSearchBookingsText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchBookingsText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchBookingsText.setText("Sök");

        jLabelSearchSortiment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchSortiment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchSortimentMouseClicked(evt);
            }
        });

        jtableSortiment.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jtableSortiment);

        jLabelUpdateSortimentIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_50px.png"))); // NOI18N
        jLabelUpdateSortimentIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateSortimentIconMouseClicked(evt);
            }
        });

        jLabelUpdateSortimentText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateSortimentText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateSortimentText.setText("Uppdatera");
        jLabelUpdateSortimentText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateSortimentTextMouseClicked(evt);
            }
        });

        jbtnBorrow.setText("Låna");
        jbtnBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBorrowActionPerformed(evt);
            }
        });

        jbtnAboutBook.setText("Om Boken");
        jbtnAboutBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAboutBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabBookingsLayout = new javax.swing.GroupLayout(jPanelTabBookings);
        jPanelTabBookings.setLayout(jPanelTabBookingsLayout);
        jPanelTabBookingsLayout.setHorizontalGroup(
            jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                        .addComponent(jbtnBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnAboutBook, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 571, Short.MAX_VALUE)
                        .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabelUpdateSortimentIcon))
                            .addComponent(jLabelUpdateSortimentText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68))
                    .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                        .addComponent(jLabelSearchBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSearchSortiment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSearchSortiment)))
                .addContainerGap())
        );
        jPanelTabBookingsLayout.setVerticalGroup(
            jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchSortiment, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearchSortiment, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelUpdateSortimentIcon)
                    .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnBorrow)
                        .addComponent(jbtnAboutBook)))
                .addGap(4, 4, 4)
                .addComponent(jLabelUpdateSortimentText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        jTabbedPaneReport.addTab("Sortiment", jPanelTabBookings);

        jtableMyBorrowings.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jtableMyBorrowings);

        jLabelUpdateMyBorrowingsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_50px.png"))); // NOI18N
        jLabelUpdateMyBorrowingsIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateMyBorrowingsIconMouseClicked(evt);
            }
        });

        jLabelUpdateMyBorrowingsText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateMyBorrowingsText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateMyBorrowingsText.setText("Uppdatera");
        jLabelUpdateMyBorrowingsText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateMyBorrowingsTextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabLendingsLayout = new javax.swing.GroupLayout(jPanelTabLendings);
        jPanelTabLendings.setLayout(jPanelTabLendingsLayout);
        jPanelTabLendingsLayout.setHorizontalGroup(
            jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                .addGap(518, 518, 518)
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelUpdateMyBorrowingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                        .addComponent(jLabelUpdateMyBorrowingsIcon)
                        .addGap(12, 12, 12)))
                .addContainerGap(333, Short.MAX_VALUE))
            .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanelTabLendingsLayout.setVerticalGroup(
            jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelUpdateMyBorrowingsIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelUpdateMyBorrowingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jTabbedPaneReport.addTab("Mina Lån", jPanelTabLendings);

        MyReservationsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(MyReservationsTable);

        jLabelEraseMyReservationsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/trash_can_50px.png"))); // NOI18N
        jLabelEraseMyReservationsIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseMyReservationsIconMouseClicked(evt);
            }
        });

        jLabelEraseMyReservationsText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEraseMyReservationsText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEraseMyReservationsText.setText("Ta bort");
        jLabelEraseMyReservationsText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseMyReservationsTextMouseClicked(evt);
            }
        });

        jLabelUpdateMyReservationsText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateMyReservationsText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateMyReservationsText.setText("Uppdatera");
        jLabelUpdateMyReservationsText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateMyReservationsTextMouseClicked(evt);
            }
        });

        jLabelUpdateMyReservationsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_50px.png"))); // NOI18N

        javax.swing.GroupLayout jPanelTabStockLayout = new javax.swing.GroupLayout(jPanelTabStock);
        jPanelTabStock.setLayout(jPanelTabStockLayout);
        jPanelTabStockLayout.setHorizontalGroup(
            jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabStockLayout.createSequentialGroup()
                .addContainerGap(772, Short.MAX_VALUE)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelUpdateMyReservationsIcon)
                    .addComponent(jLabelUpdateMyReservationsText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelEraseMyReservationsText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEraseMyReservationsIcon))
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6))
        );
        jPanelTabStockLayout.setVerticalGroup(
            jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelEraseMyReservationsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelUpdateMyReservationsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEraseMyReservationsText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUpdateMyReservationsText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        jTabbedPaneReport.addTab("Mina Reservationer", jPanelTabStock);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Userview.png"))); // NOI18N

        javax.swing.GroupLayout jPanelbackgroundLayout = new javax.swing.GroupLayout(jPanelbackground);
        jPanelbackground.setLayout(jPanelbackgroundLayout);
        jPanelbackgroundLayout.setHorizontalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addGap(1014, 1014, 1014)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPaneReport, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelInvisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelbackgroundLayout.setVerticalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelbackgroundLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTabbedPaneReport)
                        .addComponent(jPanelInvisible, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1))
                .addGap(109, 109, 109)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jLabelUpdateMyReservationsTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateMyReservationsTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelUpdateMyReservationsTextMouseClicked

    private void jLabelEraseMyReservationsTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseMyReservationsTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEraseMyReservationsTextMouseClicked

    private void jLabelEraseMyReservationsIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseMyReservationsIconMouseClicked
        //TODO add your handling code here:
    }//GEN-LAST:event_jLabelEraseMyReservationsIconMouseClicked

    private void jLabelUpdateMyBorrowingsTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateMyBorrowingsTextMouseClicked
        // TODO add your handling code here:
        fillMyBorrowingsTable();
    }//GEN-LAST:event_jLabelUpdateMyBorrowingsTextMouseClicked

    private void jLabelUpdateSortimentTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateSortimentTextMouseClicked
        // TODO add your handling code here:
        fillSortimentTable();
    }//GEN-LAST:event_jLabelUpdateSortimentTextMouseClicked

    private void jLabelSearchSortimentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchSortimentMouseClicked
        // TODO add your handling code here:
        ArrayList<Books> foundBooks = new ArrayList<>();
        ArrayList<E_Books> foundeBooks = new ArrayList<>();
        List<E_Books> eBooks = new ArrayList<>();

        String searchWord = jTextFieldSearchSortiment.getText().toLowerCase();
        String bookIsAvailable = "";
        ArrayList<Books> bookListIsbn  = qm.groupAllBooksByIsbn();
        ArrayList<Integer> borrowedBooksId = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();
        model.setRowCount(0);
        model.setColumnCount(8);
        
        bookListIsbn.stream().filter((b)-> b.getTitle().toLowerCase().contains(searchWord) || b.getAuthor().toLowerCase().contains(searchWord)
            || b.getCategory().toLowerCase().equals(searchWord) || b.getIsbn().equals(searchWord)).forEach(foundBooks::add);
        
        for (int i = 0 ; i < qm.getAllBorrowedBooks().size() ; i++){
            borrowedBooksId.add(qm.getAllBorrowedBooks().get(i).getBookId());
        }
        
        if(!foundBooks.isEmpty() ){
 //           DefaultTableModel model = new DefaultTableModel(colNamesBooks, 0);

            for (int i = 0; i < foundBooks.size(); i++) {

            model.addRow(new Object[]{foundBooks.get(i).getTitle(), foundBooks.get(i).getAuthor(),
                foundBooks.get(i).getIsbn(), foundBooks.get(i).getPublisher(), 
                foundBooks.get(i).getCategory(), "Bok" ,foundBooks.get(i).getPlacement(), bookIsAvailable});
            }

            for (int i = 0 ; i < model.getRowCount() ; i++){
            String isbn = (String) model.getValueAt(i, 2);
            boolean allIsBorrowed = false;
            ArrayList<Books> borrowedBooksListIsbn = qm.findBooksByIsbn(isbn);
            
                for (int j = 0 ; j < borrowedBooksListIsbn.size() ; j ++){
                
                    if(!borrowedBooksId.contains(borrowedBooksListIsbn.get(j).getId())){
                    allIsBorrowed = false;
                    }else{
                    allIsBorrowed = true;
                    }
                
                }
            
            if(allIsBorrowed){
                bookIsAvailable = "Nej";
            }else{
                bookIsAvailable = "Ja";
            }
            model.setValueAt(bookIsAvailable, i, 7);
        }
 
            jtableSortiment.setModel(model);
            jTextFieldSearchSortiment.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Ingen bok matchade din sökning");
        }
        
        jtableSortiment.getColumnModel().getColumn(0).setHeaderValue("Titel");
        jtableSortiment.getColumnModel().getColumn(1).setHeaderValue("Författare");
        jtableSortiment.getColumnModel().getColumn(2).setHeaderValue("ISBN");
        jtableSortiment.getColumnModel().getColumn(3).setHeaderValue("Förlag");
        jtableSortiment.getColumnModel().getColumn(4).setHeaderValue("Kategori");
        jtableSortiment.getColumnModel().getColumn(5).setHeaderValue("Typ");
        jtableSortiment.getColumnModel().getColumn(6).setHeaderValue("Placering");
        jtableSortiment.getColumnModel().getColumn(7).setHeaderValue("Tillgänglig");

        
//        
//        
//        ArrayList<Books> authorSearch = qm.findBooksByAuthor(jTextFieldSearchSortiment.getText());
//        ArrayList<E_Books> authorSearchE = qm.findEBooksByAuthor(jTextFieldSearchSortiment.getText());
//        ArrayList<Books> titleSearch = qm.findBooksByTitle(jTextFieldSearchSortiment.getText().trim());
//        ArrayList<E_Books> titleSearchE = qm.findEBooksByTitle(jTextFieldSearchSortiment.getText().trim());
//        ArrayList<Books> categorySearch = qm.findBooksByCategory(jTextFieldSearchSortiment.getText().trim());
//        ArrayList<E_Books> categorySearchE = qm.findEBooksByCategory(jTextFieldSearchSortiment.getText().trim());
//
//        DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();
//
//        model.setRowCount(0);
//        model.setColumnCount(7);
//        
//        if (jTextFieldSearchSortiment.getText().equals("")){
//            fillSortimentTable();
//        }else {
//        for (int i = 0; i < authorSearch.size() ; i++) {
//            model.addRow(new Object[]{authorSearch.get(i).getTitle(), authorSearch.get(i).getAuthor(),
//                authorSearch.get(i).getIsbn(), authorSearch.get(i).getPublisher(), 
//                authorSearch.get(i).getCategory(), "Bok", authorSearch.get(i).getPlacement()});
//            model.addRow(new Object[]{authorSearchE.get(i).getTitle(), authorSearchE.get(i).getAuthor(),
//                authorSearchE.get(i).getIsbn(), authorSearchE.get(i).getPublisher(), 
//                authorSearchE.get(i).getCategory(), "E-Bok", ""});
//        }
//        
//        for (int i = 0 ; i < titleSearch.size() ; i++){
//            model.addRow(new Object[]{titleSearch.get(i).getTitle(), titleSearch.get(i).getAuthor(),
//                titleSearch.get(i).getIsbn(), titleSearch.get(i).getPublisher(), 
//                titleSearch.get(i).getCategory(), "Bok", titleSearch.get(i).getPlacement()});
//            model.addRow(new Object[]{titleSearchE.get(i).getTitle(), titleSearchE.get(i).getAuthor(),
//                titleSearchE.get(i).getIsbn(), titleSearchE.get(i).getPublisher(), 
//                titleSearchE.get(i).getCategory(), "E-Bok", ""});
//        }
//        for (int i = 0 ; i < categorySearch.size() ; i++){
//            model.addRow(new Object[]{categorySearch.get(i).getTitle(), categorySearch.get(i).getAuthor(),
//                categorySearch.get(i).getIsbn(), categorySearch.get(i).getPublisher(), 
//                categorySearch.get(i).getCategory(), "Bok", categorySearch.get(i).getPlacement()});
//            model.addRow(new Object[]{categorySearchE.get(i).getTitle(), categorySearchE.get(i).getAuthor(),
//                categorySearchE.get(i).getIsbn(), categorySearchE.get(i).getPublisher(), 
//                categorySearchE.get(i).getCategory(), "E-Bok", ""});
//        }
//        
//        }
//        jtableSortiment.getColumnModel().getColumn(0).setHeaderValue("Titel");
//        jtableSortiment.getColumnModel().getColumn(1).setHeaderValue("Författare");
//        jtableSortiment.getColumnModel().getColumn(2).setHeaderValue("ISBN");
//        jtableSortiment.getColumnModel().getColumn(3).setHeaderValue("Förlag");
//        jtableSortiment.getColumnModel().getColumn(4).setHeaderValue("Kategori");
//        jtableSortiment.getColumnModel().getColumn(5).setHeaderValue("Typ");
//        jtableSortiment.getColumnModel().getColumn(6).setHeaderValue("Placering");
//
//        jtableSortiment.getColumn("Kategori").setPreferredWidth(150);
//        jtableSortiment.getColumn("Typ").setPreferredWidth(40);
//        jtableSortiment.getColumn("Placering").setPreferredWidth(50);
         
    }//GEN-LAST:event_jLabelSearchSortimentMouseClicked

    private void jLabelUpdateSortimentIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateSortimentIconMouseClicked
        // TODO add your handling code here:
        fillSortimentTable();
    }//GEN-LAST:event_jLabelUpdateSortimentIconMouseClicked

    private void jbtnBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBorrowActionPerformed
        
        DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();
        String bookIsbn = model.getValueAt(jtableSortiment.getSelectedRow(), 2).toString();
        int bookId = 0;
        int eBookId = 0;
        try{
        bookId = qm.findBookByIsbn(bookIsbn).getId();
        }catch(Exception e){
            eBookId = qm.findEBookByIsbn(bookIsbn).getId();
        }
        
        
        int libraryCardId = qm.findLibrarycardByEmail(this.guestEmail).getId();
        
        boolean cardIsBlocked = false;
        boolean bookIsLent = false;
        for (int i = 0 ; i < qm.blockedCards().size() ; i ++){
            if (qm.getBlockedCards().get(i).getId() == libraryCardId){
                cardIsBlocked = true;
            }
        }
        
        for (int i = 0; i < qm.getAllBorrowedBooks().size() ; i++){
            if ( qm.getAllBorrowedBooks().get(i).getBookId() == bookId){
                bookIsLent = true;
            }
        }
        
        String type = model.getValueAt(jtableSortiment.getSelectedRow(), 5).toString();
        
        if(type == "Bok"){
            
            if ( cardIsBlocked == true){
            JOptionPane.showMessageDialog(null, "Lånekortet är spärrat");
        }
            else if (bookIsLent == true){
            JOptionPane.showMessageDialog(null, "Boken är utlånad");
        }   else{
        
            int input = JOptionPane.showConfirmDialog(null, "Är du säker du vill låna boken?", 
                "Bekräftelse",JOptionPane.YES_NO_OPTION);
            
            if(input == JOptionPane.YES_OPTION){ 
                 qm.borrowBooks(bookId, libraryCardId);
        }
        }
        }
        
        if(type == "E-Bok"){
            if ( cardIsBlocked == true){
            JOptionPane.showMessageDialog(null, "Lånekortet är spärrat");
        }   
            else{
            int input = JOptionPane.showConfirmDialog(null, "Är du säker du vill låna eboken?", 
                "Bekräftelse",JOptionPane.YES_NO_OPTION);
            
            if(input == JOptionPane.YES_OPTION){ 
                 qm.borrowEBooks(eBookId, libraryCardId);
            }
        }
        
        }
    }//GEN-LAST:event_jbtnBorrowActionPerformed

    private void jLabelUpdateMyBorrowingsIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateMyBorrowingsIconMouseClicked
        // TODO add your handling code here:
        fillMyBorrowingsTable();
    }//GEN-LAST:event_jLabelUpdateMyBorrowingsIconMouseClicked

    private void jbtnAboutBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAboutBookActionPerformed
        // TODO add your handling code here:
        if( jtableSortiment.getSelectedRow() == -1 ){
            JOptionPane.showMessageDialog(this, "Du har inte valt en bok");
        }else{
        DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();

        String bookIsbn = model.getValueAt(jtableSortiment.getSelectedRow(), 2).toString().trim();
        
        
        jPanelInvisible.setVisible(true);

        for (int i = 0 ; i < qm.findBooks().size() ; i++){
            if(qm.findBooks().get(i).getIsbn().trim().equals(bookIsbn)){
                jLabelAboutBook.setText("<html>"+qm.findBooks().get(i).getDesc()+"</html>");
            }  
        }
        
        }
    }//GEN-LAST:event_jbtnAboutBookActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        jPanelInvisible.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

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
            java.util.logging.Logger.getLogger(UserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable MyReservationsTable;
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelAboutBook;
    private javax.swing.JLabel jLabelEraseMyReservationsIcon;
    private javax.swing.JLabel jLabelEraseMyReservationsText;
    private javax.swing.JLabel jLabelSearchBookingsText;
    private javax.swing.JLabel jLabelSearchSortiment;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUpdateMyBorrowingsIcon;
    private javax.swing.JLabel jLabelUpdateMyBorrowingsText;
    private javax.swing.JLabel jLabelUpdateMyReservationsIcon;
    private javax.swing.JLabel jLabelUpdateMyReservationsText;
    private javax.swing.JLabel jLabelUpdateSortimentIcon;
    private javax.swing.JLabel jLabelUpdateSortimentText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelInvisible;
    private javax.swing.JPanel jPanelTabBookings;
    private javax.swing.JPanel jPanelTabLendings;
    private javax.swing.JPanel jPanelTabStock;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPaneReport;
    private javax.swing.JTextField jTextFieldSearchSortiment;
    private javax.swing.JButton jbtnAboutBook;
    private javax.swing.JButton jbtnBorrow;
    private javax.swing.JTable jtableMyBorrowings;
    private javax.swing.JTable jtableSortiment;
    // End of variables declaration//GEN-END:variables
}
