/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.function.Predicate;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.SelectionMode;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import se.database.MyConnection;
import se.database.QueryMethods;
import se.model.ComboItem;
import se.model.Admin;
import se.model.Guest;
import se.model.Librarian;
import se.database.QueryMethods;
import se.main.Validation;
import se.model.Books;
import se.model.E_Books;
import se.model.DeletedBook;

//tadevos test
/**
 *
 * @author enriq
 */
public class AdminHomePage extends javax.swing.JFrame {

    private QueryMethods queryMethods;
    private String[] colNames = {"ID", "Förnamn", "Efternamn", "Personnummer", "Email"};
    private String query = null;
    private QueryMethods qMethods = new QueryMethods();
    private ArrayList<Guest> guests;
    private ArrayList<Librarian> librarians;
    private ArrayList<Admin> admins;

    private ArrayList<String> choiceList;
    private ArrayList<DeletedBook> deletedBook;
    private ArrayList<DeletedBook> deletedEBook;
    
    private ArrayList<Books> books;
    private ArrayList<E_Books> eBooks;
    private String[] colNamesSort = {"Titel", "Författare", "Kategory", "Pris", "Inläggnings Datum", "Typ"};
    
        
    private PreparedStatement ps;
    Connection con;
    
    private String adminEmail;
    /**
     * Creates new form StartPage1
     */
    public AdminHomePage() {

        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        queryMethods = new QueryMethods();
        guests = qMethods.findGuests();
        books = qMethods.findBooks();
        eBooks = qMethods.findEBooks();

        jTabbedPaneEdit.setVisible(false);
        jTabbedPaneReport.setVisible(false);
        jPanelRegister.setVisible(false);

        boxUsers.addItem(new ComboItem("Administratör", "1"));
        boxUsers.addItem(new ComboItem("Bibliotekarie", "2"));
        boxUsers.addItem(new ComboItem("Gäst", "3"));

        this.choiceList = new ArrayList<>();
        this.deletedBook = new ArrayList<>();
        this.deletedEBook = new ArrayList<>();

        //basic setup
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
//        btnRegister.setBackground(new java.awt.Color(142, 198, 197));
//        btnClose.setBackground(new java.awt.Color(142, 198, 197));

        fillGuestTable();
        fillLibrarianTable();
        fillAdminTable();
        showBookType();
        fillBookLogTable();
        fillBooks_EBooksTable();

        
    }
    
    public AdminHomePage(String adminEmail) {

        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        queryMethods = new QueryMethods();
        guests = qMethods.findGuests();

        jTabbedPaneEdit.setVisible(false);
        jTabbedPaneReport.setVisible(false);
        jPanelRegister.setVisible(false);

        boxUsers.addItem(new ComboItem("Administratör", "1"));
        boxUsers.addItem(new ComboItem("Bibliotekarie", "2"));
        boxUsers.addItem(new ComboItem("Gäst", "3"));

        this.choiceList = new ArrayList<>();
        this.deletedBook = new ArrayList<>();
        this.deletedEBook = new ArrayList<>();

//        basic setup
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        btnRegister.setBackground(new java.awt.Color(142, 198, 197));
        btnClose.setBackground(new java.awt.Color(142, 198, 197));

        fillGuestTable();
        fillLibrarianTable();
        fillAdminTable();
        showBookType();
        fillBookLogTable();
        fillBooks_EBooksTable();

        
        this.adminEmail = adminEmail;
        jLabelTitle.setText("Inloggad Admin: "+ adminFullName());
    }
    
       public String adminFullName(){
        String adminFirstName = "";
        String adminLastName = "";
        for (int i = 0 ; i < qMethods.findAdmins().size() ; i ++){
            if ( qMethods.findAdmins().get(i).getEmail().trim().equals(this.adminEmail) ){
            adminFirstName = qMethods.findAdmins().get(i).getFirstName();
            adminLastName = qMethods.findAdmins().get(i).getLastName();
        }
        }
        String firstName = adminFirstName.substring(0, 1).toUpperCase() 
                            + adminFirstName.substring(1);
        
        String lastName = adminLastName.substring(0, 1).toUpperCase() 
                            + adminLastName.substring(1);
        return  firstName + " " + lastName ;
    }
       
       public void fillBooks_EBooksTable() {
        //ArrayList<Books> books = qMethods.findBooks();
        books = queryMethods.findBooks();
        eBooks = queryMethods.findEBooks();
        String bookType = "Bok";
        String ebookType = "E-bok";
        DefaultTableModel model = new DefaultTableModel(colNamesSort, 0);       
        model.setRowCount(0);
        
        for (int i = 0; i < books.size(); i++) {            
            model.addRow(new Object[]{ books.get(i).getTitle(), books.get(i).getAuthor(),
                 books.get(i).getCategory(), books.get(i).getPurchase_price(), books.get(i).getDate(), bookType});
        }
        
        for (int i = 0; i < eBooks.size(); i++) {
            model.addRow(new Object[]{eBooks.get(i).getTitle(), eBooks.get(i).getAuthor(),
                  eBooks.get(i).getCategory(), eBooks.get(i).getPurchase_price(),eBooks.get(i).getDate(), ebookType });
        }
        
        StockTable.setModel(model);
        StockTable.setAutoCreateRowSorter(true);
              
    }         


    public void showBookType() {

        deletedBook = qMethods.getRemovedBooks();

        con = MyConnection.getConnection();
        query = "SELECT DISTINCT bookType FROM deleted_books GROUP BY bookType";

        try {
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String choise = rs.getString("bookType");
                if (choise != null) {
                    choiceList.add(choise);
                }
            }

            jComboBoxType.setModel(new DefaultComboBoxModel<>(choiceList.toArray(new String[0])));

        } catch (SQLException ex) {
            Logger.getLogger(AdminHomePage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminHomePage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void fillBookLogTable() {
        deletedBook = qMethods.findDeletedBooks();
        String[] colname = {"id", "Titel", "Förfatare", "ISBN", "Inköpspris", "Kategori", "Förlag", "Anländning"};
        DefaultTableModel defaultModel = new DefaultTableModel(colname, 0);

        defaultModel.setRowCount(0);
        for (int i = 0; i < deletedBook.size(); i++) {
            defaultModel.addRow(new Object[]{deletedBook.get(i).getId(),
                deletedBook.get(i).getTitle(),
                deletedBook.get(i).getAuthor(),
                deletedBook.get(i).getIsbn(),
                deletedBook.get(i).getPurchasePrice(),
                deletedBook.get(i).getCategory(),
                deletedBook.get(i).getPublisher(),
                deletedBook.get(i).getNotes()});
        }

        BookLogTable.setModel(defaultModel);
        BookLogTable.setRowSelectionAllowed(true);
    }

    public void fillEBookLogTable() {
        deletedEBook = qMethods.findDeletedEBooks();
        String[] colname = {"id", "Titel", "Förfatare", "ISBN", "Inköpspris", "Förlag", "Anlädning"};
        DefaultTableModel defaultModel = new DefaultTableModel(colname, 0);

        defaultModel.setRowCount(0);
        for (int i = 0; i < deletedBook.size(); i++) {
            defaultModel.addRow(new Object[]{deletedEBook.get(i).getId(),
                deletedEBook.get(i).getTitle(),
                deletedEBook.get(i).getAuthor(),
                deletedEBook.get(i).getIsbn(),
                deletedEBook.get(i).getPurchasePrice(),
                deletedEBook.get(i).getPublisher(),
                deletedEBook.get(i).getNotes()});
        }

        BookLogTable.setModel(defaultModel);
        BookLogTable.setRowSelectionAllowed(true);
    }

    public void fillAdminTable() {
        admins = qMethods.findAdmins();

        DefaultTableModel defaultModel = new DefaultTableModel(colNames, 0);

        defaultModel.setRowCount(0);
        for (int i = 0; i < admins.size(); i++) {
            defaultModel.addRow(new Object[]{admins.get(i).getId(), admins.get(i).getFirstName(), admins.get(i).getLastName(),
                admins.get(i).getPersonId(), admins.get(i).getEmail(), admins.get(i).getPassword()});
        }
        adminTable.setModel(defaultModel);
        adminTable.setRowSelectionAllowed(true);

    }

    public void fillGuestTable() {

        guests = queryMethods.findGuests();
        DefaultTableModel model = new DefaultTableModel(colNames, 0);

        model.setRowCount(0);
        for (int i = 0; i < guests.size(); i++) {
            model.addRow(new Object[]{guests.get(i).getId(), guests.get(i).getFirstName(), guests.get(i).getLastName(),
                guests.get(i).getPersonId(), guests.get(i).getEmail(), guests.get(i).getPassword()});
        }
        guestTable.setModel(model);
        guestTable.setRowSelectionAllowed(true);

    }

    public void fillLibrarianTable() {
        librarians = qMethods.findLibrarians();

        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        model.setRowCount(0);
        for (int i = 0; i < librarians.size(); i++) {
            model.addRow(new Object[]{librarians.get(i).getId(), librarians.get(i).getFirstName(), librarians.get(i).getLastName(),
                librarians.get(i).getPersonId(), librarians.get(i).getEmail(), librarians.get(i).getPassword()});
        }
        librarianTable.setModel(model);
        librarianTable.setRowSelectionAllowed(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelbackground = new javax.swing.JPanel();
        jLabelBackgroundPhoto = new javax.swing.JLabel();
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelLogo1 = new javax.swing.JLabel();
        jLabelLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLayeredPaneWorkArea = new javax.swing.JLayeredPane();
        jTabbedPaneEdit = new javax.swing.JTabbedPane();
        jPanelTabUser = new javax.swing.JPanel();
        jLabelSearchUserText = new javax.swing.JLabel();
        jTextFieldSearchUser = new javax.swing.JTextField();
        jLabelSearchUserIcon = new javax.swing.JLabel();
        jLabelEditUserIcon = new javax.swing.JLabel();
        jLabelEditUserText = new javax.swing.JLabel();
        jLabelEraseUserIcon = new javax.swing.JLabel();
        jLabelEraseUserText = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        guestTable = new javax.swing.JTable();
        jLabelUpdateUserIcon = new javax.swing.JLabel();
        jLabelUpdateUserText = new javax.swing.JLabel();
        jPanelTabLibrarian = new javax.swing.JPanel();
        jLabelSearchLibrarianText = new javax.swing.JLabel();
        jTextFieldSearchLibrarian = new javax.swing.JTextField();
        jLabelSearchLibrarianIcon = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        librarianTable = new javax.swing.JTable();
        jLabelUpdateLibrarianIcon = new javax.swing.JLabel();
        jLabelEraseLibrarianIcon = new javax.swing.JLabel();
        jLabelEraseLibrarianText = new javax.swing.JLabel();
        jLabelUpdateLibrarianText = new javax.swing.JLabel();
        jLabelEditLibrarianText = new javax.swing.JLabel();
        jLabelEditLibrarianIcon = new javax.swing.JLabel();
        jPanelTabAdmin = new javax.swing.JPanel();
        jLabelSearchAdminText = new javax.swing.JLabel();
        jTextFieldSearchAdmin = new javax.swing.JTextField();
        jLabelSearchAdminIcon = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        adminTable = new javax.swing.JTable();
        jLabelEraseAdminIcon = new javax.swing.JLabel();
        jLabelEraseAdminText = new javax.swing.JLabel();
        jLabelUpdateAdminText = new javax.swing.JLabel();
        jLabelUpdateAdminIcon = new javax.swing.JLabel();
        jLabelEditAdminIcon = new javax.swing.JLabel();
        jLabelEditAdminText = new javax.swing.JLabel();
        jTabbedPaneReport = new javax.swing.JTabbedPane();
        jPanelTabBookings = new javax.swing.JPanel();
        jLabelSearchBookingsText = new javax.swing.JLabel();
        jTextFieldSearchBooking = new javax.swing.JTextField();
        jLabelSearchBookingsIcon = new javax.swing.JLabel();
        jLabelEditBookingsIcon = new javax.swing.JLabel();
        jLabelEditBookingsText = new javax.swing.JLabel();
        jLabelEraseBookingsIcon = new javax.swing.JLabel();
        jLabelEraseBookingsText = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        BookingsTable = new javax.swing.JTable();
        jLabelUpdateBookingsIcon = new javax.swing.JLabel();
        jLabelUpdateBookingsText = new javax.swing.JLabel();
        jPanelTabLendings = new javax.swing.JPanel();
        jLabelSearchLendingText = new javax.swing.JLabel();
        jTextFieldSearchLending = new javax.swing.JTextField();
        jLabelSearchLendingIcon = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        LendingTable = new javax.swing.JTable();
        jLabelUpdateLendingIcon = new javax.swing.JLabel();
        jLabelEraseLendingIcon = new javax.swing.JLabel();
        jLabelEraseLendingText = new javax.swing.JLabel();
        jLabelUpdateLendingText = new javax.swing.JLabel();
        jLabelEditLendingText = new javax.swing.JLabel();
        jLabelEditLendingIcon = new javax.swing.JLabel();
        jPanelTabStock = new javax.swing.JPanel();
        jLabelSearchStockText = new javax.swing.JLabel();
        jTextFieldSearchStock = new javax.swing.JTextField();
        jLabelSearchStockIcon = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        StockTable = new javax.swing.JTable();
        jLabelEraseStockIcon = new javax.swing.JLabel();
        jLabelEraseStockText = new javax.swing.JLabel();
        jLabelUpdateStockText = new javax.swing.JLabel();
        jLabelUpdateStockIcon = new javax.swing.JLabel();
        jLabelEditStockIcon = new javax.swing.JLabel();
        jLabelEditStockText = new javax.swing.JLabel();
        jPanelTabDeletedBooks = new javax.swing.JPanel();
        jLabelSearchText = new javax.swing.JLabel();
        searchDeletedBookText = new javax.swing.JTextField();
        searchRemovedBooks = new javax.swing.JLabel();
        jLabelRecoveryIcon = new javax.swing.JLabel();
        jLabelEditText = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        BookLogTable = new javax.swing.JTable();
        jLabelEditIcon = new javax.swing.JLabel();
        jLabelRecoveryText = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox<>();
        jPanelAdminToolRegister = new javax.swing.JPanel();
        jPanelAdminTools = new javax.swing.JPanel();
        jLabelEditUsersImg = new javax.swing.JLabel();
        jLabelRegister = new javax.swing.JLabel();
        jLabelReport = new javax.swing.JLabel();
        jLabelEditUsers = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanelRegister = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        boxUsers = new javax.swing.JComboBox();
        txtFirstname = new javax.swing.JTextField();
        txtLastname = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPN = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelbackground.setBackground(new java.awt.Color(244, 244, 244));

        jLabelBackgroundPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/AdminHome_small.jpg"))); // NOI18N

        jPanelTitle.setBackground(new java.awt.Color(133, 102, 170));

        jLabelTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(64, 186, 213));
        jLabelTitle.setText("Admin  ");
        jLabelTitle.setPreferredSize(new java.awt.Dimension(362, 30));

        jLabelLogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo letras libro.png"))); // NOI18N

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo libro.png"))); // NOI18N
        jLabelLogo.setPreferredSize(new java.awt.Dimension(250, 134));
        jLabelLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelLogoMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/home_80px.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLogo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 782, Short.MAX_VALUE)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelLogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabelLogo1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPaneEdit.setForeground(new java.awt.Color(105, 131, 170));
        jTabbedPaneEdit.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N

        jLabelSearchUserText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchUserText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchUserText.setText("Sök");

        jLabelSearchUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchUserIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchUserIconMouseClicked(evt);
            }
        });

        jLabelEditUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Redigera_80px.png"))); // NOI18N
        jLabelEditUserIcon.setAlignmentY(1.0F);
        jLabelEditUserIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditUserIconMouseClicked(evt);
            }
        });

        jLabelEditUserText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEditUserText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEditUserText.setText("Redigera");
        jLabelEditUserText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditUserTextMouseClicked(evt);
            }
        });

        jLabelEraseUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/trash_can_80px.png"))); // NOI18N
        jLabelEraseUserIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseUserIconMouseClicked(evt);
            }
        });

        jLabelEraseUserText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEraseUserText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEraseUserText.setText("Ta bort");
        jLabelEraseUserText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseUserTextMouseClicked(evt);
            }
        });

        guestTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(guestTable);

        jLabelUpdateUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_80px.png"))); // NOI18N

        jLabelUpdateUserText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateUserText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateUserText.setText("Uppdatera");
        jLabelUpdateUserText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateUserTextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabUserLayout = new javax.swing.GroupLayout(jPanelTabUser);
        jPanelTabUser.setLayout(jPanelTabUserLayout);
        jPanelTabUserLayout.setHorizontalGroup(
            jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTabUserLayout.createSequentialGroup()
                        .addGroup(jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEditUserIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelTabUserLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelEditUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelTabUserLayout.createSequentialGroup()
                                .addComponent(jLabelUpdateUserIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelEraseUserIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelTabUserLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelUpdateUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelEraseUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabUserLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSearchUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSearchUserIcon)
                .addContainerGap())
        );
        jPanelTabUserLayout.setVerticalGroup(
            jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabUserLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchUserIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelEraseUserIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditUserIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUpdateUserIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTabUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUpdateUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEraseUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jTabbedPaneEdit.addTab("Medlemmar", jPanelTabUser);

        jLabelSearchLibrarianText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchLibrarianText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchLibrarianText.setText("Sök");

        jLabelSearchLibrarianIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchLibrarianIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchLibrarianIconMouseClicked(evt);
            }
        });

        librarianTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(librarianTable);

        jLabelUpdateLibrarianIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_80px.png"))); // NOI18N

        jLabelEraseLibrarianIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/trash_can_80px.png"))); // NOI18N
        jLabelEraseLibrarianIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseLibrarianIconMouseClicked(evt);
            }
        });

        jLabelEraseLibrarianText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEraseLibrarianText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEraseLibrarianText.setText("Ta bort");
        jLabelEraseLibrarianText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseLibrarianTextMouseClicked(evt);
            }
        });

        jLabelUpdateLibrarianText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateLibrarianText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateLibrarianText.setText("Uppdatera");
        jLabelUpdateLibrarianText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateLibrarianTextMouseClicked(evt);
            }
        });

        jLabelEditLibrarianText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEditLibrarianText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEditLibrarianText.setText("Redigera");
        jLabelEditLibrarianText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditLibrarianTextMouseClicked(evt);
            }
        });

        jLabelEditLibrarianIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Redigera_80px.png"))); // NOI18N
        jLabelEditLibrarianIcon.setAlignmentY(1.0F);
        jLabelEditLibrarianIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditLibrarianIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabLibrarianLayout = new javax.swing.GroupLayout(jPanelTabLibrarian);
        jPanelTabLibrarian.setLayout(jPanelTabLibrarianLayout);
        jPanelTabLibrarianLayout.setHorizontalGroup(
            jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabLibrarianLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTabLibrarianLayout.createSequentialGroup()
                        .addGroup(jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEditLibrarianIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelTabLibrarianLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelEditLibrarianText, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelTabLibrarianLayout.createSequentialGroup()
                                .addComponent(jLabelUpdateLibrarianIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelEraseLibrarianIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelTabLibrarianLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelUpdateLibrarianText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelEraseLibrarianText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLibrarianLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSearchLibrarianText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldSearchLibrarian, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSearchLibrarianIcon)
                .addContainerGap())
        );
        jPanelTabLibrarianLayout.setVerticalGroup(
            jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLibrarianLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchLibrarianIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearchLibrarian, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchLibrarianText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelEraseLibrarianIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditLibrarianIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUpdateLibrarianIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTabLibrarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUpdateLibrarianText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEraseLibrarianText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditLibrarianText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jTabbedPaneEdit.addTab("Bibliotekarier", jPanelTabLibrarian);

        jLabelSearchAdminText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchAdminText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchAdminText.setText("Sök");

        jLabelSearchAdminIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchAdminIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchAdminIconMouseClicked(evt);
            }
        });

        adminTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(adminTable);

        jLabelEraseAdminIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/trash_can_80px.png"))); // NOI18N
        jLabelEraseAdminIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseAdminIconMouseClicked(evt);
            }
        });

        jLabelEraseAdminText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEraseAdminText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEraseAdminText.setText("Ta bort");
        jLabelEraseAdminText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseAdminTextMouseClicked(evt);
            }
        });

        jLabelUpdateAdminText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateAdminText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateAdminText.setText("Uppdatera");
        jLabelUpdateAdminText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateAdminTextMouseClicked(evt);
            }
        });

        jLabelUpdateAdminIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_80px.png"))); // NOI18N

        jLabelEditAdminIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Redigera_80px.png"))); // NOI18N
        jLabelEditAdminIcon.setAlignmentY(1.0F);
        jLabelEditAdminIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditAdminIconMouseClicked(evt);
            }
        });

        jLabelEditAdminText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEditAdminText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEditAdminText.setText("Redigera");
        jLabelEditAdminText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditAdminTextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabAdminLayout = new javax.swing.GroupLayout(jPanelTabAdmin);
        jPanelTabAdmin.setLayout(jPanelTabAdminLayout);
        jPanelTabAdminLayout.setHorizontalGroup(
            jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTabAdminLayout.createSequentialGroup()
                        .addGroup(jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEditAdminIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelTabAdminLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelEditAdminText, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelTabAdminLayout.createSequentialGroup()
                                .addComponent(jLabelUpdateAdminIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelEraseAdminIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelTabAdminLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabelUpdateAdminText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelEraseAdminText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabAdminLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSearchAdminText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldSearchAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSearchAdminIcon)
                .addContainerGap())
        );
        jPanelTabAdminLayout.setVerticalGroup(
            jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabAdminLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchAdminIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearchAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchAdminText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelEraseAdminIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditAdminIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUpdateAdminIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTabAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUpdateAdminText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEraseAdminText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditAdminText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jTabbedPaneEdit.addTab("Administratörer", jPanelTabAdmin);

        jTabbedPaneReport.setForeground(new java.awt.Color(105, 131, 170));
        jTabbedPaneReport.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N

        jLabelSearchBookingsText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchBookingsText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchBookingsText.setText("Sök");

        jLabelSearchBookingsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchBookingsIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchBookingsIconMouseClicked(evt);
            }
        });

        jLabelEditBookingsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/edit_50px.png"))); // NOI18N
        jLabelEditBookingsIcon.setAlignmentY(1.0F);
        jLabelEditBookingsIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditBookingsIconMouseClicked(evt);
            }
        });

        jLabelEditBookingsText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEditBookingsText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEditBookingsText.setText("Redigera");
        jLabelEditBookingsText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditBookingsTextMouseClicked(evt);
            }
        });

        jLabelEraseBookingsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/trash_can_50px.png"))); // NOI18N
        jLabelEraseBookingsIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseBookingsIconMouseClicked(evt);
            }
        });

        jLabelEraseBookingsText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEraseBookingsText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEraseBookingsText.setText("Ta bort");
        jLabelEraseBookingsText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseBookingsTextMouseClicked(evt);
            }
        });

        BookingsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(BookingsTable);

        jLabelUpdateBookingsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_50px.png"))); // NOI18N

        jLabelUpdateBookingsText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateBookingsText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateBookingsText.setText("Uppdatera");
        jLabelUpdateBookingsText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateBookingsTextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabBookingsLayout = new javax.swing.GroupLayout(jPanelTabBookings);
        jPanelTabBookings.setLayout(jPanelTabBookingsLayout);
        jPanelTabBookingsLayout.setHorizontalGroup(
            jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                        .addGap(445, 445, 445)
                        .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createSequentialGroup()
                                .addComponent(jLabelEditBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                                .addComponent(jLabelEditBookingsIcon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                                .addComponent(jLabelUpdateBookingsIcon)
                                .addGap(27, 27, 27)
                                .addComponent(jLabelEraseBookingsIcon))
                            .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                                .addComponent(jLabelUpdateBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelEraseBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createSequentialGroup()
                                .addComponent(jLabelSearchBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldSearchBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelSearchBookingsIcon)))))
                .addContainerGap())
        );
        jPanelTabBookingsLayout.setVerticalGroup(
            jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchBookingsIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearchBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelEraseBookingsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelUpdateBookingsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelEditBookingsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEraseBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUpdateBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditBookingsText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jTabbedPaneReport.addTab("Bokningar", jPanelTabBookings);

        jLabelSearchLendingText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchLendingText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchLendingText.setText("Sök");

        jLabelSearchLendingIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N

        LendingTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(LendingTable);

        jLabelUpdateLendingIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_50px.png"))); // NOI18N

        jLabelEraseLendingIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/trash_can_50px.png"))); // NOI18N
        jLabelEraseLendingIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseLendingIconMouseClicked(evt);
            }
        });

        jLabelEraseLendingText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEraseLendingText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEraseLendingText.setText("Ta bort");
        jLabelEraseLendingText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseLendingTextMouseClicked(evt);
            }
        });

        jLabelUpdateLendingText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateLendingText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateLendingText.setText("Uppdatera");
        jLabelUpdateLendingText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateLendingTextMouseClicked(evt);
            }
        });

        jLabelEditLendingText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEditLendingText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEditLendingText.setText("Redigera");
        jLabelEditLendingText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditLendingTextMouseClicked(evt);
            }
        });

        jLabelEditLendingIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/edit_50px.png"))); // NOI18N
        jLabelEditLendingIcon.setAlignmentY(1.0F);
        jLabelEditLendingIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditLendingIconMouseClicked(evt);
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
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                                        .addComponent(jLabelEditLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPanelTabLendingsLayout.createSequentialGroup()
                                        .addComponent(jLabelEditLendingIcon)
                                        .addGap(23, 23, 23)))
                                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelUpdateLendingIcon)
                                    .addComponent(jLabelUpdateLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelEraseLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelEraseLendingIcon))
                                .addGap(11, 11, 11))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelSearchLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldSearchLending, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSearchLendingIcon)))
                .addContainerGap())
        );
        jPanelTabLendingsLayout.setVerticalGroup(
            jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchLendingIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearchLending, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelEraseLendingIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelUpdateLendingIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelEditLendingIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEraseLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUpdateLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditLendingText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        jTabbedPaneReport.addTab("Utlåning", jPanelTabLendings);

        jLabelSearchStockText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchStockText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchStockText.setText("Sök");

        jLabelSearchStockIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchStockIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchStockIconMouseClicked(evt);
            }
        });

        StockTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(StockTable);

        jLabelEraseStockIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/trash_can_50px.png"))); // NOI18N
        jLabelEraseStockIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseStockIconMouseClicked(evt);
            }
        });

        jLabelEraseStockText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEraseStockText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEraseStockText.setText("Ta bort");
        jLabelEraseStockText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEraseStockTextMouseClicked(evt);
            }
        });

        jLabelUpdateStockText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelUpdateStockText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelUpdateStockText.setText("Uppdatera");
        jLabelUpdateStockText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateStockTextMouseClicked(evt);
            }
        });

        jLabelUpdateStockIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Update_50px.png"))); // NOI18N

        jLabelEditStockIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/edit_50px.png"))); // NOI18N
        jLabelEditStockIcon.setAlignmentY(1.0F);
        jLabelEditStockIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditStockIconMouseClicked(evt);
            }
        });

        jLabelEditStockText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEditStockText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEditStockText.setText("Redigera");
        jLabelEditStockText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditStockTextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabStockLayout = new javax.swing.GroupLayout(jPanelTabStock);
        jPanelTabStock.setLayout(jPanelTabStockLayout);
        jPanelTabStockLayout.setHorizontalGroup(
            jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabStockLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createSequentialGroup()
                        .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createSequentialGroup()
                                .addComponent(jLabelEditStockText, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanelTabStockLayout.createSequentialGroup()
                                .addComponent(jLabelEditStockIcon)
                                .addGap(23, 23, 23)))
                        .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUpdateStockIcon)
                            .addComponent(jLabelUpdateStockText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEraseStockText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelEraseStockIcon))
                        .addGap(11, 11, 11))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createSequentialGroup()
                        .addComponent(jLabelSearchStockText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldSearchStock, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelSearchStockIcon)))
                .addContainerGap())
        );
        jPanelTabStockLayout.setVerticalGroup(
            jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSearchStockIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearchStock, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchStockText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelEraseStockIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelEditStockIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelUpdateStockIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEraseStockText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUpdateStockText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditStockText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        jTabbedPaneReport.addTab("Lager", jPanelTabStock);

        jLabelSearchText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchText.setText("Sök");

        searchRemovedBooks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        searchRemovedBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchRemovedBooksMouseClicked(evt);
            }
        });

        jLabelRecoveryIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/reset_50px.png"))); // NOI18N
        jLabelRecoveryIcon.setAlignmentY(1.0F);
        jLabelRecoveryIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRecoveryIconMouseClicked(evt);
            }
        });

        jLabelEditText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEditText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEditText.setText("Redigera");
        jLabelEditText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditTextMouseClicked(evt);
            }
        });

        BookLogTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(BookLogTable);

        jLabelEditIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/edit_50px.png"))); // NOI18N

        jLabelRecoveryText.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelRecoveryText.setForeground(new java.awt.Color(105, 131, 170));
        jLabelRecoveryText.setText("Återställ");
        jLabelRecoveryText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRecoveryTextMouseClicked(evt);
            }
        });

        jComboBoxType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTypeItemStateChanged(evt);
            }
        });
        jComboBoxType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxTypeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabDeletedBooksLayout = new javax.swing.GroupLayout(jPanelTabDeletedBooks);
        jPanelTabDeletedBooks.setLayout(jPanelTabDeletedBooksLayout);
        jPanelTabDeletedBooksLayout.setHorizontalGroup(
            jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabDeletedBooksLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabDeletedBooksLayout.createSequentialGroup()
                        .addComponent(jLabelSearchText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabDeletedBooksLayout.createSequentialGroup()
                                .addComponent(searchDeletedBookText, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchRemovedBooks))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabDeletedBooksLayout.createSequentialGroup()
                            .addComponent(jLabelEditIcon)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelRecoveryIcon)
                            .addGap(11, 11, 11))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabDeletedBooksLayout.createSequentialGroup()
                            .addComponent(jLabelEditText, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabelRecoveryText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTabDeletedBooksLayout.setVerticalGroup(
            jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabDeletedBooksLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchRemovedBooks, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchDeletedBookText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabDeletedBooksLayout.createSequentialGroup()
                        .addComponent(jLabelRecoveryIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelTabDeletedBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelEditText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelRecoveryText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelTabDeletedBooksLayout.createSequentialGroup()
                        .addComponent(jLabelEditIcon)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        jTabbedPaneReport.addTab("Borttagna Böcker", jPanelTabDeletedBooks);

        jLayeredPaneWorkArea.setLayer(jTabbedPaneEdit, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneWorkArea.setLayer(jTabbedPaneReport, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPaneWorkAreaLayout = new javax.swing.GroupLayout(jLayeredPaneWorkArea);
        jLayeredPaneWorkArea.setLayout(jLayeredPaneWorkAreaLayout);
        jLayeredPaneWorkAreaLayout.setHorizontalGroup(
            jLayeredPaneWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
            .addGroup(jLayeredPaneWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPaneWorkAreaLayout.createSequentialGroup()
                    .addContainerGap(26, Short.MAX_VALUE)
                    .addGroup(jLayeredPaneWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPaneReport, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTabbedPaneEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(33, Short.MAX_VALUE)))
        );
        jLayeredPaneWorkAreaLayout.setVerticalGroup(
            jLayeredPaneWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jLayeredPaneWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneWorkAreaLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPaneReport, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPaneEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jPanelAdminToolRegister.setLayout(new java.awt.BorderLayout());

        jPanelAdminTools.setBackground(new java.awt.Color(244, 244, 244));
        jPanelAdminTools.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Admin verktyg", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18), new java.awt.Color(105, 131, 170))); // NOI18N
        jPanelAdminTools.setAlignmentY(1.0F);
        jPanelAdminTools.setLayout(new java.awt.GridBagLayout());

        jLabelEditUsersImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Redigera_80px.png"))); // NOI18N
        jLabelEditUsersImg.setAlignmentY(1.0F);
        jLabelEditUsersImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditUsersImgMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(52, 18, 0, 0);
        jPanelAdminTools.add(jLabelEditUsersImg, gridBagConstraints);

        jLabelRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/add_user_80px.png"))); // NOI18N
        jLabelRegister.setAlignmentY(1.0F);
        jLabelRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRegisterMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(52, 12, 0, 0);
        jPanelAdminTools.add(jLabelRegister, gridBagConstraints);

        jLabelReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/account_80px.png"))); // NOI18N
        jLabelReport.setAlignmentY(1.0F);
        jLabelReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelReportMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(52, 18, 0, 0);
        jPanelAdminTools.add(jLabelReport, gridBagConstraints);

        jLabelEditUsers.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelEditUsers.setForeground(new java.awt.Color(105, 131, 170));
        jLabelEditUsers.setText("Redigera");
        jLabelEditUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEditUsersMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 35, 0);
        jPanelAdminTools.add(jLabelEditUsers, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(105, 131, 170));
        jLabel2.setText("Registrera");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 17, 35, 0);
        jPanelAdminTools.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(105, 131, 170));
        jLabel3.setText("Rapportering");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 35, 30);
        jPanelAdminTools.add(jLabel3, gridBagConstraints);

        jPanelAdminToolRegister.add(jPanelAdminTools, java.awt.BorderLayout.PAGE_START);

        jPanelRegister.setBackground(new java.awt.Color(244, 244, 244));
        jPanelRegister.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registreringsformulär", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 18), new java.awt.Color(105, 131, 170))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(105, 131, 170));
        jLabel10.setText("Typ av användare:");

        boxUsers.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        boxUsers.setForeground(new java.awt.Color(105, 131, 170));

        jLabel11.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(105, 131, 170));
        jLabel11.setText("Lösenord:");

        jLabel12.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(105, 131, 170));
        jLabel12.setText("PN:");

        jLabel13.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(105, 131, 170));
        jLabel13.setText("Email:");

        jLabel14.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(105, 131, 170));
        jLabel14.setText("Efternamn:");

        jLabel15.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(105, 131, 170));
        jLabel15.setText("Förnamn:");

        btnRegister.setText("Registrera");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnClose.setText("Stäng");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRegisterLayout = new javax.swing.GroupLayout(jPanelRegister);
        jPanelRegister.setLayout(jPanelRegisterLayout);
        jPanelRegisterLayout.setHorizontalGroup(
            jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(81, 81, 81)
                                .addComponent(txtPN))
                            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(38, 38, 38)
                                .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(66, 66, 66)
                                .addComponent(txtEmail))
                            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(btnRegister)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(jPasswordField1)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRegisterLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(boxUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel15)
                                .addGap(47, 47, 47)
                                .addComponent(txtFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(28, 28, 28)))
                .addGap(6, 6, 6))
        );
        jPanelRegisterLayout.setVerticalGroup(
            jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel10))
                    .addComponent(boxUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel15))
                    .addComponent(txtFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel14))
                    .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel13))
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel12))
                    .addComponent(txtPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegister)
                    .addComponent(btnClose))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanelAdminToolRegister.add(jPanelRegister, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanelbackgroundLayout = new javax.swing.GroupLayout(jPanelbackground);
        jPanelbackground.setLayout(jPanelbackgroundLayout);
        jPanelbackgroundLayout.setHorizontalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelBackgroundPhoto)
                .addGap(7, 7, 7)
                .addComponent(jPanelAdminToolRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLayeredPaneWorkArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelbackgroundLayout.setVerticalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabelBackgroundPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanelAdminToolRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLayeredPaneWorkArea, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelbackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed

        String userType = boxUsers.getSelectedItem().toString();

        String firstName = txtFirstname.getText();
        String lastName = txtLastname.getText();
        String PN = txtPN.getText();
        String password = jPasswordField1.getText();
        String email = txtEmail.getText();

        if (queryMethods.isEmailTaken(email)) {
            JOptionPane.showMessageDialog(this, "Upptagen Email");
        } else if (!Validation.isValidName(firstName)) {
            JOptionPane.showMessageDialog(this, "Felaktig inmatning för förnamn");
        } else if (!Validation.isValidName(lastName)) {
            JOptionPane.showMessageDialog(this, "Felaktig inmatning för efternamn");
        } else if (!Validation.isValidPN(PN)) {
            JOptionPane.showMessageDialog(this, "Felaktig inamtning för personnummer");
        } else if (!Validation.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Felaktig inmatning för lösenord");
        } else if (!Validation.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Felaktig inmatning för email");
        } else {
            switch (userType) {
                case "Administratör":
                    queryMethods.insertAdmin(firstName, lastName, PN, password, email);
                    break;
                case "Bibliotikarie":
                    queryMethods.insertLibrarian(firstName, lastName, PN, password, email);
                    break;
                case "Gäst":
                    queryMethods.insertGuest(firstName, lastName, PN, password, email);
                    break;
            }

        }
        txtFirstname.setText("");
        txtLastname.setText("");
        txtPN.setText("");
        jPasswordField1.setText("");
        txtEmail.setText("");

        fillGuestTable();
        fillLibrarianTable();
        fillAdminTable();

    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed

        jPanelRegister.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        jPanelRegister.setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

        jTabbedPaneReport.setVisible(true);
        jTabbedPaneEdit.setVisible(false);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabelEditUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditUsersMouseClicked
        jTabbedPaneEdit.setVisible(true);
        jTabbedPaneReport.setVisible(false);
        fillGuestTable();
        fillLibrarianTable();
        fillAdminTable();

    }//GEN-LAST:event_jLabelEditUsersMouseClicked

    private void jLabelEditUsersImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditUsersImgMouseClicked
        jTabbedPaneEdit.setVisible(true);
        jTabbedPaneReport.setVisible(false);
        fillGuestTable();
        fillLibrarianTable();
        fillAdminTable();
    }//GEN-LAST:event_jLabelEditUsersImgMouseClicked

    private void jLabelEditBookingsIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditBookingsIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditBookingsIconMouseClicked

    private void jLabelEditBookingsTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditBookingsTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditBookingsTextMouseClicked

    private void jLabelEraseBookingsTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseBookingsTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEraseBookingsTextMouseClicked

    private void jLabelUpdateBookingsTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateBookingsTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelUpdateBookingsTextMouseClicked

    private void jLabelEraseLendingTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseLendingTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEraseLendingTextMouseClicked

    private void jLabelUpdateLendingTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateLendingTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelUpdateLendingTextMouseClicked

    private void jLabelEditLendingTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditLendingTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditLendingTextMouseClicked

    private void jLabelEditLendingIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditLendingIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditLendingIconMouseClicked

    private void jLabelEraseStockTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseStockTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEraseStockTextMouseClicked

    private void jLabelUpdateStockTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateStockTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelUpdateStockTextMouseClicked

    private void jLabelEditStockIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditStockIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditStockIconMouseClicked

    private void jLabelEditStockTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditStockTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditStockTextMouseClicked

    private void jLabelEditUserIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditUserIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditUserIconMouseClicked

    private void jLabelEditUserTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditUserTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditUserTextMouseClicked

    private void jLabelEraseUserTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseUserTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEraseUserTextMouseClicked

    private void jLabelUpdateUserTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateUserTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelUpdateUserTextMouseClicked

    private void jLabelEraseLibrarianTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseLibrarianTextMouseClicked

        int selection = librarianTable.getSelectedRow();

        String stringId = librarianTable.getModel().getValueAt(selection, 0).toString();

        int id = Integer.parseInt(stringId);

        for (Librarian l : librarians) {
            if (l.getId() == id) {
                System.out.println(l.getFirstName());
                queryMethods.deleteLibrarian(l);

            }
            fillLibrarianTable();
        }
    }//GEN-LAST:event_jLabelEraseLibrarianTextMouseClicked

    private void jLabelUpdateLibrarianTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateLibrarianTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelUpdateLibrarianTextMouseClicked

    private void jLabelEditLibrarianTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditLibrarianTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditLibrarianTextMouseClicked

    private void jLabelEditLibrarianIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditLibrarianIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditLibrarianIconMouseClicked

    private void jLabelEraseAdminTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseAdminTextMouseClicked

        int selection = adminTable.getSelectedRow();

        String stringId = adminTable.getModel().getValueAt(selection, 0).toString();

        int id = Integer.parseInt(stringId);

        for (Admin a : admins) {
            if (a.getId() == id) {
                System.out.println(a.getFirstName());
                queryMethods.deleteAdmin(a);

            }
            fillAdminTable();
        }
    }//GEN-LAST:event_jLabelEraseAdminTextMouseClicked

    private void jLabelUpdateAdminTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateAdminTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelUpdateAdminTextMouseClicked

    private void jLabelEditAdminIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditAdminIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditAdminIconMouseClicked

    private void jLabelEditAdminTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditAdminTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditAdminTextMouseClicked

    private void jLabelReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelReportMouseClicked

        jTabbedPaneReport.setVisible(true);
        jTabbedPaneEdit.setVisible(false);
    }//GEN-LAST:event_jLabelReportMouseClicked

    private void jLabelRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRegisterMouseClicked
        jPanelRegister.setVisible(true);
    }//GEN-LAST:event_jLabelRegisterMouseClicked

    private void jLabelLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelLogoMouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabelLogoMouseClicked

    private void jLabelEraseBookingsIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseBookingsIconMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabelEraseBookingsIconMouseClicked

    private void jLabelEraseUserIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseUserIconMouseClicked
        // TODO add your handling code here:

        if (jTabbedPaneEdit.isEnabledAt(0)) {
            int selection = guestTable.getSelectedRow();

            String stringId = guestTable.getModel().getValueAt(selection, 0).toString();

            int id = Integer.parseInt(stringId);

            for (Guest g : guests) {
                if (g.getId() == id) {
                    System.out.println(g.getFirstName());
                    queryMethods.deleteGuest(g);

                }
                fillGuestTable();

            }
        }

    }//GEN-LAST:event_jLabelEraseUserIconMouseClicked

    private void jLabelEraseLendingIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseLendingIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEraseLendingIconMouseClicked

    private void jLabelEraseStockIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseStockIconMouseClicked
        //TODO add your handling code here:
    }//GEN-LAST:event_jLabelEraseStockIconMouseClicked

    private void jLabelEraseLibrarianIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseLibrarianIconMouseClicked
        // TODO add your handling code here:
        int selection = librarianTable.getSelectedRow();

        String stringId = librarianTable.getModel().getValueAt(selection, 0).toString();

        int id = Integer.parseInt(stringId);

        for (Librarian l : librarians) {
            if (l.getId() == id) {
                System.out.println(l.getFirstName());
                queryMethods.deleteLibrarian(l);

            }
            fillLibrarianTable();
        }

    }//GEN-LAST:event_jLabelEraseLibrarianIconMouseClicked

    private void jLabelEraseAdminIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEraseAdminIconMouseClicked
        // TODO add your handling code here:
        int selection = adminTable.getSelectedRow();

        String stringId = adminTable.getModel().getValueAt(selection, 0).toString();

        int id = Integer.parseInt(stringId);

        for (Admin a : admins) {
            if (a.getId() == id) {
                System.out.println(a.getFirstName());
                queryMethods.deleteAdmin(a);

            }
            fillAdminTable();
        }
    }//GEN-LAST:event_jLabelEraseAdminIconMouseClicked


    private void jLabelSearchUserIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchUserIconMouseClicked
        // TODO add your handling code here:
        String userToFind = jTextFieldSearchUser.getText().toLowerCase().trim();

        if (!userToFind.isEmpty()) {
            ArrayList<Guest> foundGuests = new ArrayList<>();

            guests.stream().filter((g) -> g.getLastName().toLowerCase().contains(userToFind)
                    || g.getFirstName().toLowerCase().contains(userToFind) || g.getEmail().equalsIgnoreCase(userToFind)
                    || g.getPersonId().equals(userToFind)).forEach(foundGuests::add);

            if (!foundGuests.isEmpty()) {
                DefaultTableModel model = new DefaultTableModel(colNames, 0);

                for (Guest g : foundGuests) {
                    model.addRow(new Object[]{g.getId(), g.getFirstName(), g.getLastName(), g.getPersonId(), g.getEmail()});
                }
                guestTable.setModel(model);
                jTextFieldSearchUser.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ingen användare kunde hittas");

            }
        }
    }//GEN-LAST:event_jLabelSearchUserIconMouseClicked

    private void jLabelSearchLibrarianIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchLibrarianIconMouseClicked
        // TODO add your handling code here:

        String librarianToFind = jTextFieldSearchLibrarian.getText().toLowerCase();

        if (!librarianToFind.isEmpty()) {
            ArrayList<Librarian> foundLibrarians = new ArrayList<>();

            librarians.stream().filter((l) -> l.getLastName().toLowerCase().contains(librarianToFind)
                    || l.getFirstName().toLowerCase().contains(librarianToFind) || l.getPersonId().equals(librarianToFind)
                    || l.getEmail().toLowerCase().equals(librarianToFind)).forEach(foundLibrarians::add);

            if (!foundLibrarians.isEmpty()) {
                DefaultTableModel model = new DefaultTableModel(colNames, 0);

                for (Librarian l : foundLibrarians) {
                    model.addRow(new Object[]{l.getId(), l.getFirstName(), l.getLastName(), l.getPersonId(), l.getEmail()});
                }
                librarianTable.setModel(model);
                jTextFieldSearchLibrarian.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ingen användare kunde hittas");
            }
        }
    }//GEN-LAST:event_jLabelSearchLibrarianIconMouseClicked

    private void jLabelSearchAdminIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchAdminIconMouseClicked
        // TODO add your handling code here:

        String adminToFind = jTextFieldSearchAdmin.getText().toLowerCase();

        if (!adminToFind.isEmpty()) {
            ArrayList<Admin> foundAdmins = new ArrayList<>();

            admins.stream().filter((a) -> a.getFirstName().toLowerCase().contains(adminToFind)
                    || a.getLastName().toLowerCase().contains(adminToFind)
                    || a.getPersonId().equals(adminToFind)
                    || a.getEmail().toLowerCase().equals(adminToFind)).forEach(foundAdmins::add);

            if (!foundAdmins.isEmpty()) {
                DefaultTableModel model = new DefaultTableModel(colNames, 0);

                for (Admin a : foundAdmins) {
                    model.addRow(new Object[]{a.getId(), a.getFirstName(), a.getLastName(), a.getPersonId(), a.getEmail()});
                }
                adminTable.setModel(model);
                jTextFieldSearchAdmin.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ingen användare kunde hittas");
            }
        }
    }//GEN-LAST:event_jLabelSearchAdminIconMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel1MouseClicked


    private void jLabelRecoveryIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecoveryIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelRecoveryIconMouseClicked

    private void jLabelEditTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEditTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelEditTextMouseClicked

    private void jLabelRecoveryTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecoveryTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelRecoveryTextMouseClicked

    private void jLabelSearchStockIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchStockIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelSearchStockIconMouseClicked


    private void jLabelSearchBookingsIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchBookingsIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelSearchBookingsIconMouseClicked

    private void jComboBoxTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxTypeMouseClicked

    }//GEN-LAST:event_jComboBoxTypeMouseClicked

    private void jComboBoxTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTypeItemStateChanged
        if (jComboBoxType.getSelectedItem().toString().equals("Book")) {
            System.out.println("Book received");
            fillBookLogTable();
        } else {
            System.out.println("Ebook received");
            fillEBookLogTable();
        }
    }//GEN-LAST:event_jComboBoxTypeItemStateChanged

    private void searchRemovedBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchRemovedBooksMouseClicked
        
        String removedBookToFind = searchDeletedBookText.getText().toLowerCase();
        
        ArrayList<DeletedBook> deletedBooks = queryMethods.findDeletedBooks();
        
        if(!removedBookToFind.isEmpty()){
            ArrayList<DeletedBook> foundDeletedBooks = new ArrayList<>();
            
            deletedBooks.stream().filter((deletedBook) -> deletedBook.getTitle().toLowerCase().contains(removedBookToFind) 
                                        || deletedBook.getAuthor().toLowerCase().contains(removedBookToFind)
                                        || deletedBook.getIsbn().equals(removedBookToFind)
                                        || deletedBook.getCategory().equals(removedBookToFind)).forEach(foundDeletedBooks::add);
            
            if(!foundDeletedBooks.isEmpty()){
                
               String[] colname = {"id", "Titel", "Förfatare", "ISBN", "Inköpspris", "Kategori", "Förlag", "Beskrivning"};
               DefaultTableModel defaultModel = new DefaultTableModel(colname, 0);
               
               for(DeletedBook deletedBook : foundDeletedBooks){
                   defaultModel.addRow(new Object[]{deletedBook.getId(), deletedBook.getTitle(), deletedBook.getAuthor(), deletedBook.getIsbn()
                   ,deletedBook.getPurchasePrice(), deletedBook.getCategory(), deletedBook.getPublisher(), deletedBook.getDesc()});
               }
               BookLogTable.setModel(defaultModel);
               searchDeletedBookText.setText("");
            }else {
                JOptionPane.showMessageDialog(this, "Kunde inte hitta ett objekt som matchar din sökning");
            }
            
        }
        
    }//GEN-LAST:event_searchRemovedBooksMouseClicked

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
            java.util.logging.Logger.getLogger(AdminHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BookLogTable;
    private javax.swing.JTable BookingsTable;
    private javax.swing.JTable LendingTable;
    private javax.swing.JTable StockTable;
    private javax.swing.JTable adminTable;
    private javax.swing.JComboBox boxUsers;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRegister;
    private javax.swing.JTable guestTable;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelBackgroundPhoto;
    private javax.swing.JLabel jLabelEditAdminIcon;
    private javax.swing.JLabel jLabelEditAdminText;
    private javax.swing.JLabel jLabelEditBookingsIcon;
    private javax.swing.JLabel jLabelEditBookingsText;
    private javax.swing.JLabel jLabelEditIcon;
    private javax.swing.JLabel jLabelEditLendingIcon;
    private javax.swing.JLabel jLabelEditLendingText;
    private javax.swing.JLabel jLabelEditLibrarianIcon;
    private javax.swing.JLabel jLabelEditLibrarianText;
    private javax.swing.JLabel jLabelEditStockIcon;
    private javax.swing.JLabel jLabelEditStockText;
    private javax.swing.JLabel jLabelEditText;
    private javax.swing.JLabel jLabelEditUserIcon;
    private javax.swing.JLabel jLabelEditUserText;
    private javax.swing.JLabel jLabelEditUsers;
    private javax.swing.JLabel jLabelEditUsersImg;
    private javax.swing.JLabel jLabelEraseAdminIcon;
    private javax.swing.JLabel jLabelEraseAdminText;
    private javax.swing.JLabel jLabelEraseBookingsIcon;
    private javax.swing.JLabel jLabelEraseBookingsText;
    private javax.swing.JLabel jLabelEraseLendingIcon;
    private javax.swing.JLabel jLabelEraseLendingText;
    private javax.swing.JLabel jLabelEraseLibrarianIcon;
    private javax.swing.JLabel jLabelEraseLibrarianText;
    private javax.swing.JLabel jLabelEraseStockIcon;
    private javax.swing.JLabel jLabelEraseStockText;
    private javax.swing.JLabel jLabelEraseUserIcon;
    private javax.swing.JLabel jLabelEraseUserText;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelLogo1;
    private javax.swing.JLabel jLabelRecoveryIcon;
    private javax.swing.JLabel jLabelRecoveryText;
    private javax.swing.JLabel jLabelRegister;
    private javax.swing.JLabel jLabelReport;
    private javax.swing.JLabel jLabelSearchAdminIcon;
    private javax.swing.JLabel jLabelSearchAdminText;
    private javax.swing.JLabel jLabelSearchBookingsIcon;
    private javax.swing.JLabel jLabelSearchBookingsText;
    private javax.swing.JLabel jLabelSearchLendingIcon;
    private javax.swing.JLabel jLabelSearchLendingText;
    private javax.swing.JLabel jLabelSearchLibrarianIcon;
    private javax.swing.JLabel jLabelSearchLibrarianText;
    private javax.swing.JLabel jLabelSearchStockIcon;
    private javax.swing.JLabel jLabelSearchStockText;
    private javax.swing.JLabel jLabelSearchText;
    private javax.swing.JLabel jLabelSearchUserIcon;
    private javax.swing.JLabel jLabelSearchUserText;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUpdateAdminIcon;
    private javax.swing.JLabel jLabelUpdateAdminText;
    private javax.swing.JLabel jLabelUpdateBookingsIcon;
    private javax.swing.JLabel jLabelUpdateBookingsText;
    private javax.swing.JLabel jLabelUpdateLendingIcon;
    private javax.swing.JLabel jLabelUpdateLendingText;
    private javax.swing.JLabel jLabelUpdateLibrarianIcon;
    private javax.swing.JLabel jLabelUpdateLibrarianText;
    private javax.swing.JLabel jLabelUpdateStockIcon;
    private javax.swing.JLabel jLabelUpdateStockText;
    private javax.swing.JLabel jLabelUpdateUserIcon;
    private javax.swing.JLabel jLabelUpdateUserText;
    private javax.swing.JLayeredPane jLayeredPaneWorkArea;
    private javax.swing.JPanel jPanelAdminToolRegister;
    private javax.swing.JPanel jPanelAdminTools;
    private javax.swing.JPanel jPanelRegister;
    private javax.swing.JPanel jPanelTabAdmin;
    private javax.swing.JPanel jPanelTabBookings;
    private javax.swing.JPanel jPanelTabDeletedBooks;
    private javax.swing.JPanel jPanelTabLendings;
    private javax.swing.JPanel jPanelTabLibrarian;
    private javax.swing.JPanel jPanelTabStock;
    private javax.swing.JPanel jPanelTabUser;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPaneEdit;
    private javax.swing.JTabbedPane jTabbedPaneReport;
    private javax.swing.JTextField jTextFieldSearchAdmin;
    private javax.swing.JTextField jTextFieldSearchBooking;
    private javax.swing.JTextField jTextFieldSearchLending;
    private javax.swing.JTextField jTextFieldSearchLibrarian;
    private javax.swing.JTextField jTextFieldSearchStock;
    private javax.swing.JTextField jTextFieldSearchUser;
    private javax.swing.JTable librarianTable;
    private javax.swing.JTextField searchDeletedBookText;
    private javax.swing.JLabel searchRemovedBooks;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstname;
    private javax.swing.JTextField txtLastname;
    private javax.swing.JTextField txtPN;
    // End of variables declaration//GEN-END:variables
}
