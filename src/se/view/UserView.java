/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.awt.Color;
import java.awt.Point;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import static java.time.Instant.now;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import se.database.QueryMethods;
import se.model.Booking;
import se.model.Books;
import se.model.BorrowEBooks;
import se.model.BorrowedBooks;
import se.model.E_Books;
import se.model.Guest;
import se.model.LibraryCards;
import se.model.Seminar;

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

    private ArrayList<Integer> borrowedBooksId = new ArrayList<>();
    private ArrayList<BorrowEBooks> borrowedEBooks = new ArrayList<>();

    public UserView() {
        initComponents();
        setLocationRelativeTo(null);
        jPanelInvisible.setVisible(false);
        fillSortimentTable();

        jTextFieldSearchSortiment.setToolTipText("Skriv titel, författare eller kategori");

        fillMyBorrowingsTable();

        fillSeminarsTable();
    }

    public UserView(String guestEmail) {
        initComponents();
        setLocationRelativeTo(null);
        jPanelInvisible.setVisible(false);

        fillSortimentTable();

        fillSeminarsTable();
        jTextFieldSearchSortiment.setToolTipText("Skriv titel, författare eller kategori");

        this.guestEmail = guestEmail;

        fillMyBorrowingsTable();
        fillMyReservationsTable();

        jLabelTitle.setText("Inloggad Gäst: " + guestFullName());
        jtableSortiment.setAutoCreateRowSorter(true);
        jtableMyBorrowings.setAutoCreateRowSorter(true);
    }

    public String guestFullName() {
        String guestFullName = "";
        int guestId = qm.findLibrarycardByEmail(this.guestEmail).getId();
        for (int i = 0; i < qm.getAllCards().size(); i++) {
            if (qm.getAllCards().get(i).getGuestId() == guestId) {
                guestFullName = qm.getAllCards().get(i).getFullname();
            }
        }
        int indexOfSpace = guestFullName.indexOf(" ");
        String firstName = guestFullName.substring(0, 1).toUpperCase()
                + guestFullName.substring(1, indexOfSpace);

        String lastName = guestFullName.substring(indexOfSpace + 1, indexOfSpace + 2).toUpperCase()
                + guestFullName.substring(indexOfSpace + 2);
        return firstName + " " + lastName;

    }

    public void fillSeminarsTable() {
        DefaultTableModel model = (DefaultTableModel) seminarsTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(7);
        ArrayList<Seminar> seminars = qm.findSeminar();

        for (int i = 0; i < seminars.size(); i++) {
            model.addRow(new Object[]{seminars.get(i).getTitle(), seminars.get(i).getSpeaker(),
                seminars.get(i).getLocation(), seminars.get(i).getStartDate(),
                seminars.get(i).getCountVisitor(), seminars.get(i).getSeminariumDescription(),
                seminars.get(i).getProgramDescription()});
        }

        seminarsTable.getColumnModel().getColumn(0).setHeaderValue("Titel");
        seminarsTable.getColumnModel().getColumn(1).setHeaderValue("Talare");
        seminarsTable.getColumnModel().getColumn(2).setHeaderValue("Plats");
        seminarsTable.getColumnModel().getColumn(3).setHeaderValue("Start");
        seminarsTable.getColumnModel().getColumn(4).setHeaderValue("Platser Kvar");
        seminarsTable.getColumnModel().getColumn(5).setHeaderValue("Beskrivning");
        seminarsTable.getColumnModel().getColumn(6).setHeaderValue("Program");

        seminarsTable.setModel(model);
    }

    public void fillSortimentTable() {
        DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();
        model.setRowCount(0);
        model.setColumnCount(8);

        String bookIsAvailable = "";
        ArrayList<Books> books = qm.groupAllBooksByIsbn();
        ArrayList<Books> booksByIsbn;
        ArrayList<Integer> borrowedBooksId = new ArrayList<>();

        for (int i = 0; i < qm.getAllBorrowedBooks().size(); i++) {
            borrowedBooksId.add(qm.getAllBorrowedBooks().get(i).getBookId());
        }

        for (int i = 0; i < books.size(); i++) {

            model.addRow(new Object[]{books.get(i).getTitle(), books.get(i).getAuthor(),
                books.get(i).getIsbn(), books.get(i).getPublisher(),
                books.get(i).getCategory(), "Bok", books.get(i).getPlacement()});
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            String isbn = (String) model.getValueAt(i, 2);
            booksByIsbn = qm.findBooksByIsbn(isbn);
            bookIsAvailable = "Ja";
            model.setValueAt(bookIsAvailable, i, 7);
            int countCopies = 0;

            for (int j = 0; j < booksByIsbn.size(); j++) {
                if (borrowedBooksId.contains(booksByIsbn.get(j).getId())) {
                    countCopies++;
                    if (countCopies == booksByIsbn.size()) {
                        bookIsAvailable = "Nej";
                        model.setValueAt(bookIsAvailable, i, 7);
                    }

                }
            }

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
        model.setColumnCount(5);
        int libraryCardId = qm.findLibrarycardByEmail(this.guestEmail).getId();
        Date toDayDate = new Date();
        String returnBookReminder = "";
        
        // books
        ArrayList<BorrowedBooks> borrowedBooks = qm.getAllBorrowedBooks();
        ArrayList<Books> allBooks = qm.getAllBooks();
        ArrayList<BorrowedBooks> borrowedBooksByCardId = new ArrayList<>();
        ArrayList<Books> borrowedBooksByCardIdInfo = new ArrayList<>();
        
        for (int i = 0; i < borrowedBooks.size(); i++) {
            if (borrowedBooks.get(i).getLibraryCardId() == libraryCardId) {
                borrowedBooksByCardId.add(borrowedBooks.get(i));
            }
        }
        for (int i = 0; i < allBooks.size(); i++) {
            for (int j = 0; j < borrowedBooksByCardId.size(); j++) {
                if (allBooks.get(i).getId() == borrowedBooksByCardId.get(j).getBookId()) {
                    borrowedBooksByCardIdInfo.add(allBooks.get(i));

                }
            }
        }

        for (int i = 0; i < borrowedBooksByCardIdInfo.size(); i++) {
            int dayDiff = borrowedBooksByCardId.get(i).getReturnDate().getDay() - toDayDate.getDay();
            int monthDiff = borrowedBooksByCardId.get(i).getReturnDate().getMonth() - toDayDate.getMonth();

            if (dayDiff <= 0 && monthDiff <= 0) {
                returnBookReminder = "Försenad";
                System.out.println(returnBookReminder);

            } else if (dayDiff == 1 && monthDiff == 0) {
                returnBookReminder = "1 dag kvar";
            } else if (dayDiff == 2 && monthDiff == 0) {
                returnBookReminder = "2 dagar kvar";
            } else {
                returnBookReminder = "";
            }

            model.addRow(new Object[]{borrowedBooksByCardIdInfo.get(i).getTitle(),
                borrowedBooksByCardIdInfo.get(i).getAuthor(),
                borrowedBooksByCardIdInfo.get(i).getIsbn(),
                "Bok",
                borrowedBooksByCardId.get(i).getReturnDate() + "  " + returnBookReminder});

        }

        // ebooks
        ArrayList<BorrowEBooks> borrowedEBooks = qm.getAllBorrowedEBooks();
        ArrayList<E_Books> allEBooks = qm.getAllEBooks();
        ArrayList<BorrowEBooks> borrowedEBooksByCardId = new ArrayList<>();
        ArrayList<E_Books> borrowedEBooksByCardIdInfo = new ArrayList<>();
        for (int i = 0; i < borrowedEBooks.size(); i++) {
            if (borrowedEBooks.get(i).getLibraryCardId() == libraryCardId) {
                borrowedEBooksByCardId.add(borrowedEBooks.get(i));
            }
        }
        for (int i = 0; i < allEBooks.size(); i++) {
            for (int j = 0; j < borrowedEBooksByCardId.size(); j++) {
                if (allEBooks.get(i).getId() == borrowedEBooksByCardId.get(j).geteBookId()) {
                    borrowedEBooksByCardIdInfo.add(allEBooks.get(i));

                }
            }
        }

        for (int i = 0; i < borrowedEBooksByCardId.size(); i++) {
            int dayDiff = borrowedEBooksByCardId.get(i).getReturnDate().getDay() - toDayDate.getDay();
            int monthDiff = borrowedEBooksByCardId.get(i).getReturnDate().getMonth() - toDayDate.getMonth();

            if (dayDiff == 1 && monthDiff == 0) {
                returnBookReminder = "1 dag kvar";
            } else if (dayDiff == 2 && monthDiff == 0) {
                returnBookReminder = "2 dagar kvar";
            } else {
                returnBookReminder = "";
            }

            model.addRow(new Object[]{borrowedEBooksByCardIdInfo.get(i).getTitle(),
                borrowedEBooksByCardIdInfo.get(i).getAuthor(),
                borrowedEBooksByCardIdInfo.get(i).getIsbn(),
                "E-Bok",
                borrowedEBooksByCardId.get(i).getReturnDate() + "  " + returnBookReminder});

        }

        jtableMyBorrowings.getColumnModel().getColumn(0).setHeaderValue("Titel");
        jtableMyBorrowings.getColumnModel().getColumn(1).setHeaderValue("Författare");
        jtableMyBorrowings.getColumnModel().getColumn(2).setHeaderValue("ISBN");
        jtableMyBorrowings.getColumnModel().getColumn(3).setHeaderValue("Typ");
        jtableMyBorrowings.getColumnModel().getColumn(4).setHeaderValue("Återlämning");

    }

    public void fillMyReservationsTable() {
        ArrayList<Booking> allBookedSeminars = qm.getAllBookedSeminars();
        ArrayList<Booking> myBookedSeminars = new ArrayList<>();
        LibraryCards libraryCardId = qm.findLibrarycardByEmail(this.guestEmail);

        for (int i = 0; i < allBookedSeminars.size(); i++) {
            if (allBookedSeminars.get(i).getLibraryCardId() == libraryCardId.getId()) {
                myBookedSeminars.add(allBookedSeminars.get(i));
            }
        }

        ArrayList<Seminar> allSeminars = qm.findSeminar();
        DefaultTableModel model = (DefaultTableModel) MyReservationsTable.getModel();
        model.setColumnCount(4);
        model.setRowCount(0);

        for (int i = 0; i < allSeminars.size(); i++) {
            for (int j = 0; j < myBookedSeminars.size(); j++) {
                if (myBookedSeminars.get(j).getSeminarId() == allSeminars.get(i).getId()) {
                    model.addRow(new Object[]{allSeminars.get(i).getTitle(), allSeminars.get(i).getSpeaker(),
                        allSeminars.get(i).getLocation(), allSeminars.get(i).getStartDate()});
                }
            }
        }
        MyReservationsTable.getColumnModel().getColumn(0).setHeaderValue("Titel");
        MyReservationsTable.getColumnModel().getColumn(1).setHeaderValue("Föreläsare");
        MyReservationsTable.getColumnModel().getColumn(2).setHeaderValue("Plats");
        MyReservationsTable.getColumnModel().getColumn(3).setHeaderValue("Datum");

        MyReservationsTable.setModel(model);

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
        jLabel3 = new javax.swing.JLabel();
        jPanelInvisible = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        jLabelAboutBook = new javax.swing.JLabel();
        lblProgram = new javax.swing.JLabel();
        jTabbedPaneReport = new javax.swing.JTabbedPane();
        jPanelTabBokaSeminarium = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        seminarsTable = new javax.swing.JTable();
        jbtnReserve = new javax.swing.JButton();
        jPanelTabBookings = new javax.swing.JPanel();
        jLabelSearchBookingsText = new javax.swing.JLabel();
        jTextFieldSearchSortiment = new javax.swing.JTextField();
        jLabelSearchSortiment = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtableSortiment = new javax.swing.JTable();
        jbtnBorrow = new javax.swing.JButton();
        jbtnAboutBook = new javax.swing.JButton();
        jbtnUpdate = new javax.swing.JButton();
        jPanelTabLendings = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtableMyBorrowings = new javax.swing.JTable();
        jbtnUpdate1 = new javax.swing.JButton();
        jPanelTabStock = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        MyReservationsTable = new javax.swing.JTable();
        jbtnUpdateMyReservation = new javax.swing.JButton();
        jbtnCancelMyReservation = new javax.swing.JButton();
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

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/home_50px.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel3.setText("Logga ut");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
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
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                        .addComponent(jLabelTitle)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel5))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(17, 17, 17))
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
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelTitleLayout.createSequentialGroup()
                                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addGap(2, 2, 2)))))
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
                .addContainerGap()
                .addGroup(jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInvisibleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClose))
                    .addGroup(jPanelInvisibleLayout.createSequentialGroup()
                        .addComponent(jLabelAboutBook, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(lblProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelInvisibleLayout.setVerticalGroup(
            jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInvisibleLayout.createSequentialGroup()
                .addContainerGap(174, Short.MAX_VALUE)
                .addGroup(jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAboutBook, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnClose)
                .addGap(21, 21, 21))
        );

        jTabbedPaneReport.setForeground(new java.awt.Color(105, 131, 170));
        jTabbedPaneReport.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N

        seminarsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        seminarsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seminarsTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(seminarsTable);

        jbtnReserve.setText("Boka");
        jbtnReserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReserveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabBokaSeminariumLayout = new javax.swing.GroupLayout(jPanelTabBokaSeminarium);
        jPanelTabBokaSeminarium.setLayout(jPanelTabBokaSeminariumLayout);
        jPanelTabBokaSeminariumLayout.setHorizontalGroup(
            jPanelTabBokaSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabBokaSeminariumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabBokaSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
                    .addGroup(jPanelTabBokaSeminariumLayout.createSequentialGroup()
                        .addComponent(jbtnReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelTabBokaSeminariumLayout.setVerticalGroup(
            jPanelTabBokaSeminariumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBokaSeminariumLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jbtnReserve)
                .addGap(70, 70, 70))
        );

        jTabbedPaneReport.addTab("Boka Seminarium", jPanelTabBokaSeminarium);

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

        jbtnUpdate.setText("Uppdatera");
        jbtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabBookingsLayout = new javax.swing.GroupLayout(jPanelTabBookings);
        jPanelTabBookings.setLayout(jPanelTabBookingsLayout);
        jPanelTabBookingsLayout.setHorizontalGroup(
            jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 911, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createSequentialGroup()
                        .addComponent(jLabelSearchBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSearchSortiment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSearchSortiment))
                    .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                        .addComponent(jbtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnAboutBook, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addGap(32, 32, 32)
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnBorrow)
                    .addComponent(jbtnAboutBook)
                    .addComponent(jbtnUpdate))
                .addGap(72, 72, 72))
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
        jtableMyBorrowings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableMyBorrowingsMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jtableMyBorrowings);

        jbtnUpdate1.setText("Uppdatera");
        jbtnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdate1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabLendingsLayout = new javax.swing.GroupLayout(jPanelTabLendings);
        jPanelTabLendings.setLayout(jPanelTabLendingsLayout);
        jPanelTabLendingsLayout.setHorizontalGroup(
            jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnUpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 911, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelTabLendingsLayout.setVerticalGroup(
            jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addComponent(jbtnUpdate1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(68, 68, 68))
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
        MyReservationsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MyReservationsTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(MyReservationsTable);

        jbtnUpdateMyReservation.setText("Uppdatera");
        jbtnUpdateMyReservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateMyReservationActionPerformed(evt);
            }
        });

        jbtnCancelMyReservation.setText("Avboka");
        jbtnCancelMyReservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelMyReservationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabStockLayout = new javax.swing.GroupLayout(jPanelTabStock);
        jPanelTabStock.setLayout(jPanelTabStockLayout);
        jPanelTabStockLayout.setHorizontalGroup(
            jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabStockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabStockLayout.createSequentialGroup()
                        .addComponent(jbtnUpdateMyReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnCancelMyReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 729, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)))
        );
        jPanelTabStockLayout.setVerticalGroup(
            jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnUpdateMyReservation)
                        .addComponent(jbtnCancelMyReservation)))
                .addGap(68, 68, 68))
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
                        .addComponent(jTabbedPaneReport, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
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

    private void jLabelSearchSortimentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchSortimentMouseClicked
        // TODO add your handling code here:
        ArrayList<Books> foundBooks = new ArrayList<>();
        ArrayList<E_Books> foundeBooks = new ArrayList<>();
        List<E_Books> eBooks = new ArrayList<>();
        eBooks = qm.getAllEBooks();
        String searchWord = jTextFieldSearchSortiment.getText().toLowerCase();
        String bookIsAvailable = "";
        ArrayList<Books> bookListIsbn = qm.groupAllBooksByIsbn();

        ArrayList<Books> booksByIsbn = new ArrayList<>();

        ArrayList<Integer> borrowedBooksId = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();
        model.setRowCount(0);
        model.setColumnCount(8);

        bookListIsbn.stream().filter((b) -> b.getTitle().toLowerCase().contains(searchWord) || b.getAuthor().toLowerCase().contains(searchWord)
                || b.getCategory().toLowerCase().equals(searchWord) || b.getIsbn().equals(searchWord)).forEach(foundBooks::add);
        eBooks.stream().filter((b) -> b.getTitle().toLowerCase().contains(searchWord) || b.getAuthor().toLowerCase().contains(searchWord)
                || b.getCategory().toLowerCase().equals(searchWord) || b.getIsbn().equals(searchWord)).forEach(foundeBooks::add);

        for (int i = 0; i < qm.getAllBorrowedBooks().size(); i++) {
            borrowedBooksId.add(qm.getAllBorrowedBooks().get(i).getBookId());
        }

        if (!foundBooks.isEmpty()) {
            //           DefaultTableModel model = new DefaultTableModel(colNamesBooks, 0);

            for (int i = 0; i < foundBooks.size(); i++) {

                model.addRow(new Object[]{foundBooks.get(i).getTitle(), foundBooks.get(i).getAuthor(),
                    foundBooks.get(i).getIsbn(), foundBooks.get(i).getPublisher(),
                    foundBooks.get(i).getCategory(), "Bok", foundBooks.get(i).getPlacement(), bookIsAvailable});
            }

//            for (int i = 0; i < model.getRowCount(); i++) {
//                String isbn = (String) model.getValueAt(i, 2);
//                boolean allIsBorrowed = false;
//                ArrayList<Books> borrowedBooksListIsbn = qm.findBooksByIsbn(isbn);
//
//                for (int j = 0; j < borrowedBooksListIsbn.size(); j++) {
//
//                    if (!borrowedBooksId.contains(borrowedBooksListIsbn.get(j).getId())) {
//                        allIsBorrowed = false;
//                    } else {
//                        allIsBorrowed = true;
//                    }
//
//                }
//
//                if (allIsBorrowed) {
//                    bookIsAvailable = "Nej";
//                } else {
//                    bookIsAvailable = "Ja";
//                }
//                model.setValueAt(bookIsAvailable, i, 7);
//            }
            for (int i = 0; i < model.getRowCount(); i++) {
                String isbn = (String) model.getValueAt(i, 2);
                booksByIsbn = qm.findBooksByIsbn(isbn);
                bookIsAvailable = "Ja";
                model.setValueAt(bookIsAvailable, i, 7);
                int countCopies = 0;

                for (int j = 0; j < booksByIsbn.size(); j++) {
                    if (borrowedBooksId.contains(booksByIsbn.get(j).getId())) {
                        countCopies++;
                        if (countCopies == booksByIsbn.size()) {
                            bookIsAvailable = "Nej";
                            model.setValueAt(bookIsAvailable, i, 7);
                        }

                    }
                }

            }

            for (E_Books ebook : foundeBooks) {
                model.addRow(new Object[]{ebook.getTitle(), ebook.getAuthor(), ebook.getIsbn(), ebook.getPublisher(),
                    ebook.getCategory(), "E-Bok", "", "Ja"});
            }

            jtableSortiment.setModel(model);
            jTextFieldSearchSortiment.setText("");
        } else {
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

    private void jbtnBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBorrowActionPerformed

        try {
        DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();
        String bookIsbn = model.getValueAt(jtableSortiment.getSelectedRow(), 2).toString();
        ArrayList<Books> bookIdList = new ArrayList<>();
        int eBookId = 0;

        for (int i = 0; i < qm.getAllBorrowedBooks().size(); i++) {
            borrowedBooksId.add(qm.getAllBorrowedBooks().get(i).getBookId());
        }
        borrowedEBooks = qm.getAllBorrowedEBooks();

        try {
            bookIdList = qm.findBooksByIsbn(bookIsbn);
            eBookId = qm.findEBookByField("Isbn", bookIsbn).getId();

        } catch (Exception e) {
        }

        int libraryCardId = qm.findLibrarycardByEmail(this.guestEmail).getId();

        boolean cardIsBlocked = false;
        boolean bookIsLent = false;
        for (int i = 0; i < qm.blockedCards().size(); i++) {
            if (qm.getBlockedCards().get(i).getId() == libraryCardId) {
                cardIsBlocked = true;
            }
        }

        int countCopies = 0;
        for (int i = 0; i < qm.getAllBorrowedBooks().size(); i++) {
            for (int j = 0; j < bookIdList.size(); j++) {
                if (qm.getAllBorrowedBooks().get(i).getBookId() == bookIdList.get(j).getId()) {
                    countCopies++;
                    if (countCopies == bookIdList.size()) {
                        bookIsLent = true;
                    }
                }
            }

        }

        String type = model.getValueAt(jtableSortiment.getSelectedRow(), 5).toString();

        if (type == "Bok") {

            if (cardIsBlocked == true) {
                JOptionPane.showMessageDialog(null, "Lånekortet är spärrat");
            } else if (bookIsLent == true) {
                JOptionPane.showMessageDialog(null, "Boken är utlånad");
            } else {

                int input = JOptionPane.showConfirmDialog(null, "Är du säker du vill låna boken?",
                        "Bekräftelse", JOptionPane.YES_NO_OPTION);

                if (input == JOptionPane.YES_OPTION) {

                    int countBookCopies = 0;

                    for (int j = 0; j < bookIdList.size(); j++) {

                        if (!borrowedBooksId.contains(bookIdList.get(j).getId())) {
                            countBookCopies++;

                            if (countBookCopies == 1) {
                                qm.borrowBooks(bookIdList.get(j).getId(), libraryCardId);

                            }
                        }
                    }

                }
            }
        }

        if (type == "E-Bok") {
            if (cardIsBlocked == true) {
                JOptionPane.showMessageDialog(null, "Lånekortet är spärrat");
            } else {
                int input = JOptionPane.showConfirmDialog(null, "Är du säker du vill låna eboken?",
                        "Bekräftelse", JOptionPane.YES_NO_OPTION);

                if (input == JOptionPane.YES_OPTION) {

                    System.out.println(eBookId + " libraryCard: " + libraryCardId);
                    boolean matchedEBook = false;
                    for (int i = 0; i < borrowedEBooks.size(); i++) {
                        if (borrowedEBooks.get(i).geteBookId() == eBookId && borrowedEBooks.get(i).getLibraryCardId() == libraryCardId) {
                            matchedEBook = true;
                        }
                    }

                    if (matchedEBook) {
                        JOptionPane.showMessageDialog(this, "Du har redan lånat E-Boken");
                    } else {
                        qm.borrowEBooks(eBookId, libraryCardId);
                    }
                }
            }

        }
        jbtnUpdate.doClick();
        }catch(Exception e){
                
                }
    }//GEN-LAST:event_jbtnBorrowActionPerformed

    private void jbtnAboutBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAboutBookActionPerformed
        // TODO add your handling code here:
        if (jtableSortiment.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Du har inte valt en bok");
        } else {
            DefaultTableModel model = (DefaultTableModel) jtableSortiment.getModel();

            String bookIsbn = model.getValueAt(jtableSortiment.getSelectedRow(), 2).toString().trim();

            jPanelInvisible.setVisible(true);

            for (int i = 0; i < qm.getAllBooks().size(); i++) {
                if (qm.getAllBooks().get(i).getIsbn().trim().equals(bookIsbn)) {
                    jLabelAboutBook.setText("<html>" + qm.getAllBooks().get(i).getDesc() + "</html>");
                }
            }

        }
    }//GEN-LAST:event_jbtnAboutBookActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        jPanelInvisible.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void jbtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateActionPerformed
        fillSortimentTable();
    }//GEN-LAST:event_jbtnUpdateActionPerformed

    private void jbtnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdate1ActionPerformed
        fillMyBorrowingsTable();
    }//GEN-LAST:event_jbtnUpdate1ActionPerformed

    private void jbtnUpdateMyReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateMyReservationActionPerformed
        // TODO add your handling code here:
        fillMyReservationsTable();
    }//GEN-LAST:event_jbtnUpdateMyReservationActionPerformed

    private void jbtnCancelMyReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelMyReservationActionPerformed
        try {
            int selection = MyReservationsTable.getSelectedRow();
            String title = MyReservationsTable.getModel().getValueAt(selection, 0).toString();
            Guest g = qm.findGuestByMail(guestEmail);
            int option = JOptionPane.showConfirmDialog(this, "Vill du avboka seminariet?");
            if (option == JOptionPane.YES_OPTION) {
                qm.cancelSeminarReservation(g, title);
                fillMyReservationsTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Du har inte valt bokning!");
        }

    }//GEN-LAST:event_jbtnCancelMyReservationActionPerformed

    private void jbtnReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReserveActionPerformed

        try {
            int selection = seminarsTable.getSelectedRow();
            String title = seminarsTable.getModel().getValueAt(selection, 0).toString();
            System.out.println(title);

            LibraryCards g = qm.findLibrarycardByEmail(guestEmail);
            Seminar s = qm.findSeminarByTitle(title);

            qm.bookSeminar(g, s);
            fillSeminarsTable();
        } catch (Exception e) {
            System.out.println("du måste välja ett seminarium");
        }
    }//GEN-LAST:event_jbtnReserveActionPerformed

    private void seminarsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seminarsTableMouseClicked

        DefaultTableModel model = (DefaultTableModel) seminarsTable.getModel();

        String seminarDesc = model.getValueAt(seminarsTable.getSelectedRow(), 5).toString().trim();
        String seminarProgram = model.getValueAt(seminarsTable.getSelectedRow(), 6).toString().trim();

        jPanelInvisible.setVisible(true);

        for (int i = 0; i < qm.findSeminar().size(); i++) {
            if (qm.findSeminar().get(i).getSeminariumDescription().trim().equals(seminarDesc)) {
                jLabelAboutBook.setText("<html>Beskrivning: <br>" + qm.findSeminar().get(i).getSeminariumDescription() + "</html>");
                lblProgram.setText("<html>Program: <br>" + qm.findSeminar().get(i).getProgramDescription() + "</html>");
            }
        }
    }//GEN-LAST:event_seminarsTableMouseClicked

    private void MyReservationsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyReservationsTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MyReservationsTableMouseClicked

    private void jtableMyBorrowingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableMyBorrowingsMouseClicked
        // TODO add your handling code here:
        int id;
        if (jtableMyBorrowings.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Du har inte valt en bok");

        } else {
            DefaultTableModel model = (DefaultTableModel) jtableMyBorrowings.getModel();

            String bookType = model.getValueAt(jtableMyBorrowings.getSelectedRow(), 3).toString();

            String bookIsbn = model.getValueAt(jtableMyBorrowings.getSelectedRow(), 2).toString().trim();

            for (int i = 0; i < qm.getAllEBooks().size(); i++) {
                if (qm.getAllEBooks().get(i).getIsbn().trim().equals(bookIsbn)) {
                    id = qm.getAllEBooks().get(i).getId();

                    if (bookType.equals("E-Bok")) {
                        DisplayEbook displayebook = new DisplayEbook(id, guestEmail);
                        displayebook.setVisible(true);
                        this.setVisible(false);
                    }
                }
            }
        }

    }//GEN-LAST:event_jtableMyBorrowingsMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel3MouseClicked

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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelAboutBook;
    private javax.swing.JLabel jLabelSearchBookingsText;
    private javax.swing.JLabel jLabelSearchSortiment;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelInvisible;
    private javax.swing.JPanel jPanelTabBokaSeminarium;
    private javax.swing.JPanel jPanelTabBookings;
    private javax.swing.JPanel jPanelTabLendings;
    private javax.swing.JPanel jPanelTabStock;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPaneReport;
    private javax.swing.JTextField jTextFieldSearchSortiment;
    private javax.swing.JButton jbtnAboutBook;
    private javax.swing.JButton jbtnBorrow;
    private javax.swing.JButton jbtnCancelMyReservation;
    private javax.swing.JButton jbtnReserve;
    private javax.swing.JButton jbtnUpdate;
    private javax.swing.JButton jbtnUpdate1;
    private javax.swing.JButton jbtnUpdateMyReservation;
    private javax.swing.JTable jtableMyBorrowings;
    private javax.swing.JTable jtableSortiment;
    private javax.swing.JLabel lblProgram;
    private javax.swing.JTable seminarsTable;
    // End of variables declaration//GEN-END:variables
}
