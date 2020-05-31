/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import se.database.MyConnection;
import se.database.QueryMethods;
import se.model.Books;
import se.model.BorrowedBooks;
import se.model.E_Books;

/**
 *
 * @author adde
 */
public class ViewBooks extends javax.swing.JFrame {
    
    private List<Books> books = new ArrayList<>();
    private List<E_Books> eBooks = new ArrayList<>();
    private QueryMethods queryMethods;
    private String[] colNamesBooks = {"Titel", "Författare", "ISBN", "Förlag","Kategori", "Placering", "Tillgänglig"};
    private String[] colNamesEBooks = {"Titel", "Författare", "ISBN", "Förlag","Kategori"};
    private ArrayList<Integer> borrowedBooksId = new ArrayList<>();


    /**
     * Creates new form ViewBooks
     */
    public ViewBooks() {
        initComponents();
        setLocationRelativeTo(null);
        jPanelInvisible.setVisible(false);

        queryMethods = new QueryMethods();
        books = queryMethods.getAllBooks();
        eBooks = queryMethods.getAllEBooks();
        fillBooksTable();
        fillEBooksTable();
    }
    
    public void fillEBooksTable(){
        ArrayList<E_Books> allEBooks = new ArrayList<>();
        String searchWord = Bookstxt.getText().toLowerCase();
        eBooks.stream().filter((e)-> e.getTitle().toLowerCase().contains(searchWord) || e.getAuthor().toLowerCase().contains(searchWord)
                                || e.getCategory().toLowerCase().equals(searchWord) || e.getIsbn().equals(searchWord)).forEach(allEBooks::add);
        
        DefaultTableModel model = new DefaultTableModel(colNamesEBooks, 0);
            
        for(E_Books e : allEBooks){
            model.addRow(new Object[]{e.getTitle(),e.getAuthor(), e.getIsbn(),e.getPublisher(),e.getCategory()});
            }
               eBooksTable.setModel(model);
    } 
    
    public void fillBooksTable(){
        DefaultTableModel model = new DefaultTableModel(colNamesBooks, 0);

        BooksTable.setModel(model);
        String bookIsAvailable = "";
        ArrayList<Books> books = queryMethods.groupAllBooksByIsbn();
        ArrayList<Books> booksByIsbn ;
        
        for (int i = 0 ; i < queryMethods.getAllBorrowedBooks().size() ; i++){
            borrowedBooksId.add(queryMethods.getAllBorrowedBooks().get(i).getBookId());
        }
        

        for (int i = 0; i < books.size(); i++) {

            model.addRow(new Object[]{books.get(i).getTitle(), books.get(i).getAuthor(),
                books.get(i).getIsbn(), books.get(i).getPublisher(), 
                books.get(i).getCategory(), books.get(i).getPlacement(), bookIsAvailable});
        }
        

        
        for (int i = 0 ; i < model.getRowCount() ; i++){
            String isbn = (String) model.getValueAt(i, 2);
            booksByIsbn = queryMethods.findBooksByIsbn(isbn);
            bookIsAvailable = "Ja";
            model.setValueAt(bookIsAvailable, i, 6);
            int countCopies = 0;

            
            for (int j = 0 ; j < booksByIsbn.size() ; j ++){
                if(borrowedBooksId.contains(booksByIsbn.get(j).getId())){
                    countCopies++;
                    if(countCopies == booksByIsbn.size()){
                    bookIsAvailable = "Nej";
                    model.setValueAt(bookIsAvailable, i, 6); }
                    
                }}
               
                    

            }
            
               
        }
        
    
    
    public void sortBooksByTitle(List<Books> books){
        Collections.sort(books, (b1,b2) -> b1.getTitle().compareTo(b2.getTitle()));
     
    }
    
    public void sortBooksByTitleReverse(List<Books> books){
        Collections.sort(books, Collections.reverseOrder((b1,b2)-> b1.getTitle().compareTo(b2.getTitle())));
    }
    
    public void sortBooksByAuthor(List<Books> books){
        Collections.sort(books, (b1,b2) -> b1.getAuthor().compareTo(b2.getAuthor()));
    }
    
    public void sortBooksByAuthorReverse(List<Books> books){
        Collections.sort(books, Collections.reverseOrder((b1,b2)-> b1.getAuthor().compareTo(b2.getAuthor())));
    }
    
    public void sortBooksByCategory(List<Books> books){
        Collections.sort(books, (b1,b2) -> b1.getCategory().compareTo(b2.getCategory()));
    }
    
    public void sortBooksByCategoryReverse(List<Books> books){
        Collections.sort(books, Collections.reverseOrder((b1,b2) -> b1.getCategory().compareTo(b2.getCategory())));
    }
    
    public void sortBooksByPublisher(List<Books> books){
        Collections.sort(books, (b1,b2)-> b1.getPublisher().compareTo(b2.getPublisher()));
    }
    
    public void sortBooksByPublisherReverse(List<Books> books){
        Collections.sort(books, Collections.reverseOrder((b1,b2) -> b1.getPublisher().compareTo(b2.getPublisher())));
    }
    
    public void sortEBooksByTitle(List<E_Books> eBooks){
        Collections.sort(eBooks, (b1,b2) -> b1.getTitle().compareTo(b2.getTitle()));
     
    }
    
    public void sortEBooksByTitleReverse(List<E_Books> eBooks){
        Collections.sort(eBooks, Collections.reverseOrder((b1,b2)-> b1.getTitle().compareTo(b2.getTitle())));
    }
    
    public void sortEBooksByAuthor(List<E_Books> eBooks){
        Collections.sort(eBooks, (b1,b2) -> b1.getAuthor().compareTo(b2.getAuthor()));
    }
    
    public void sortEBooksByAuthorReverse(List<E_Books> eBooks){
        Collections.sort(eBooks, Collections.reverseOrder((b1,b2)-> b1.getAuthor().compareTo(b2.getAuthor())));
    }
    
    public void sortEBooksByCategory(List<E_Books> eBooks){
        Collections.sort(eBooks, (b1,b2) -> b1.getCategory().compareTo(b2.getCategory()));
    }
    
    public void sortEBooksByCategoryReverse(List<E_Books> eBooks){
        Collections.sort(eBooks, Collections.reverseOrder((b1,b2) -> b1.getCategory().compareTo(b2.getCategory())));
    }
    
    public void sortEBooksByPublisher(List<E_Books> eBooks){
        Collections.sort(eBooks, (b1,b2)-> b1.getPublisher().compareTo(b2.getPublisher()));
    }
    
    public void sortEBooksByPublisherReverse(List<E_Books> eBooks){
        Collections.sort(eBooks, Collections.reverseOrder((b1,b2) -> b1.getPublisher().compareTo(b2.getPublisher())));
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
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelImg = new javax.swing.JLabel();
        jTabbedPaneReport = new javax.swing.JTabbedPane();
        jPanelTabBookings = new javax.swing.JPanel();
        jLabelSearchBookingsText1 = new javax.swing.JLabel();
        jbtnAboutBook = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        BooksTable = new javax.swing.JTable();
        Bookstxt = new javax.swing.JTextField();
        jLabelSearchBookIcon = new javax.swing.JLabel();
        jPanelTabLendings = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        eBooksTable = new javax.swing.JTable();
        jbtnAboutBook1 = new javax.swing.JButton();
        eBookstxt = new javax.swing.JTextField();
        jLabelSearcheBookIcon = new javax.swing.JLabel();
        jLabelSearchBookingsText2 = new javax.swing.JLabel();
        jPanelInvisible = new javax.swing.JPanel();
        jLabelAboutBook = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelbackground.setBackground(new java.awt.Color(244, 244, 244));

        jPanelTitle.setBackground(new java.awt.Color(133, 102, 170));

        jLabelTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(64, 186, 213));
        jLabelTitle.setText("Böcker");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo libro.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/Logo letras libro.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/home_50px.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1212, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                        .addComponent(jLabelTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTitleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabelTitle))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabelImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/viewBook_Small.jpg"))); // NOI18N

        jTabbedPaneReport.setForeground(new java.awt.Color(105, 131, 170));
        jTabbedPaneReport.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N

        jLabelSearchBookingsText1.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchBookingsText1.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchBookingsText1.setText("Sök");

        jbtnAboutBook.setText("Om Boken");
        jbtnAboutBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAboutBookActionPerformed(evt);
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

        Bookstxt.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        Bookstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookstxtActionPerformed(evt);
            }
        });

        jLabelSearchBookIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearchBookIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearchBookIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabBookingsLayout = new javax.swing.GroupLayout(jPanelTabBookings);
        jPanelTabBookings.setLayout(jPanelTabBookingsLayout);
        jPanelTabBookingsLayout.setHorizontalGroup(
            jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                        .addComponent(jLabelSearchBookingsText1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Bookstxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelSearchBookIcon))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
                    .addGroup(jPanelTabBookingsLayout.createSequentialGroup()
                        .addComponent(jbtnAboutBook, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTabBookingsLayout.setVerticalGroup(
            jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabBookingsLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabBookingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelSearchBookingsText1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Bookstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelSearchBookIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jbtnAboutBook)
                .addGap(21, 21, 21))
        );

        jTabbedPaneReport.addTab("Böcker", jPanelTabBookings);

        eBooksTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(eBooksTable);

        jbtnAboutBook1.setText("Om Boken");
        jbtnAboutBook1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAboutBook1ActionPerformed(evt);
            }
        });

        eBookstxt.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(142, 198, 197)));
        eBookstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eBookstxtActionPerformed(evt);
            }
        });

        jLabelSearcheBookIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/se/image/search_24px.png"))); // NOI18N
        jLabelSearcheBookIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelSearcheBookIconMouseClicked(evt);
            }
        });

        jLabelSearchBookingsText2.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabelSearchBookingsText2.setForeground(new java.awt.Color(105, 131, 170));
        jLabelSearchBookingsText2.setText("Sök");

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
                                .addComponent(jbtnAboutBook1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 1041, Short.MAX_VALUE))
                            .addComponent(jScrollPane5))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                        .addComponent(jLabelSearchBookingsText2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eBookstxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelSearcheBookIcon)
                        .addGap(28, 28, 28))))
        );
        jPanelTabLendingsLayout.setVerticalGroup(
            jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabLendingsLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabLendingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(eBookstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSearchBookingsText2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelSearcheBookIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jbtnAboutBook1)
                .addGap(46, 46, 46))
        );

        jTabbedPaneReport.addTab("E-Böcker", jPanelTabLendings);

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
                .addGroup(jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInvisibleLayout.createSequentialGroup()
                        .addComponent(jLabelAboutBook, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInvisibleLayout.createSequentialGroup()
                        .addComponent(btnClose)
                        .addGap(26, 26, 26))))
        );
        jPanelInvisibleLayout.setVerticalGroup(
            jPanelInvisibleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInvisibleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAboutBook, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addGap(35, 35, 35)
                .addComponent(btnClose)
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout jPanelbackgroundLayout = new javax.swing.GroupLayout(jPanelbackground);
        jPanelbackground.setLayout(jPanelbackgroundLayout);
        jPanelbackgroundLayout.setHorizontalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                        .addComponent(jLabelImg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPaneReport, javax.swing.GroupLayout.PREFERRED_SIZE, 1146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelInvisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelbackgroundLayout.setVerticalGroup(
            jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelbackgroundLayout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelImg, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelbackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanelInvisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTabbedPaneReport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelbackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelSearchBookIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearchBookIconMouseClicked

        ArrayList<Books> foundBooks = new ArrayList<>();
        String searchWord = Bookstxt.getText().toLowerCase();
        String bookIsAvailable = "";
        ArrayList<Books> bookListIsbn  = queryMethods.groupAllBooksByIsbn();
        
        bookListIsbn.stream().filter((b)-> b.getTitle().toLowerCase().contains(searchWord) || b.getAuthor().toLowerCase().contains(searchWord)
            || b.getCategory().toLowerCase().equals(searchWord) || b.getIsbn().equals(searchWord)).forEach(foundBooks::add);
        
        for (int i = 0 ; i < queryMethods.getAllBorrowedBooks().size() ; i++){
            borrowedBooksId.add(queryMethods.getAllBorrowedBooks().get(i).getBookId());
        }
        
        if(!foundBooks.isEmpty()){
            DefaultTableModel model = new DefaultTableModel(colNamesBooks, 0);

            for (int i = 0; i < foundBooks.size(); i++) {

            model.addRow(new Object[]{foundBooks.get(i).getTitle(), foundBooks.get(i).getAuthor(),
                foundBooks.get(i).getIsbn(), foundBooks.get(i).getPublisher(), 
                foundBooks.get(i).getCategory(), foundBooks.get(i).getPlacement(), bookIsAvailable});
            }
            ArrayList<Books> booksByIsbn ;
            for (int i = 0 ; i < model.getRowCount() ; i++){
            String isbn = (String) model.getValueAt(i, 2);
            booksByIsbn = queryMethods.findBooksByIsbn(isbn);
            bookIsAvailable = "Ja";
            model.setValueAt(bookIsAvailable, i, 6);
            int countCopies = 0;

            
            for (int j = 0 ; j < booksByIsbn.size() ; j ++){
                if(borrowedBooksId.contains(booksByIsbn.get(j).getId())){
                    countCopies++;
                    if(countCopies == booksByIsbn.size()){
                    bookIsAvailable = "Nej";
                    model.setValueAt(bookIsAvailable, i, 6); }
                    
                }}
               
                    

            }

 
            BooksTable.setModel(model);
            Bookstxt.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Ingen bok matchade din sökning");
        }
    }//GEN-LAST:event_jLabelSearchBookIconMouseClicked

    private void jbtnAboutBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAboutBookActionPerformed
        // TODO add your handling code here:
        if( BooksTable.getSelectedRow() == -1 ){
            JOptionPane.showMessageDialog(this, "Du har inte valt en bok");
        }else{
            DefaultTableModel model = (DefaultTableModel) BooksTable.getModel();

            String bookIsbn = model.getValueAt(BooksTable.getSelectedRow(), 2).toString().trim();

            jPanelInvisible.setVisible(true);

            for (int i = 0 ; i < queryMethods.findBooks().size() ; i++){
                if(queryMethods.findBooks().get(i).getIsbn().trim().equals(bookIsbn)){
                    jLabelAboutBook.setText("<html>"+queryMethods.findBooks().get(i).getDesc()+"</html>");
                }
            }

        }
    }//GEN-LAST:event_jbtnAboutBookActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        jPanelInvisible.setVisible(false);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void jbtnAboutBook1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAboutBook1ActionPerformed
        // TODO add your handling code here:
                if( eBooksTable.getSelectedRow() == -1 ){
            JOptionPane.showMessageDialog(this, "Du har inte valt en bok");
        }else{
            DefaultTableModel model = (DefaultTableModel) eBooksTable.getModel();

            String bookIsbn = model.getValueAt(eBooksTable.getSelectedRow(), 2).toString().trim();

            jPanelInvisible.setVisible(true);

            for (int i = 0 ; i < queryMethods.getAllEBooks().size() ; i++){
                if(queryMethods.getAllEBooks().get(i).getIsbn().trim().equals(bookIsbn)){
                    jLabelAboutBook.setText("<html>"+queryMethods.getAllEBooks().get(i).getDesc()+"</html>");
                }
            }

        }
    }//GEN-LAST:event_jbtnAboutBook1ActionPerformed

    private void BookstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookstxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BookstxtActionPerformed

    private void eBookstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eBookstxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eBookstxtActionPerformed

    private void jLabelSearcheBookIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelSearcheBookIconMouseClicked
        // TODO add your handling code here:
        ArrayList<E_Books> foundBooks = new ArrayList<>();
        String searchWord = eBookstxt.getText().toLowerCase();

        eBooks.stream().filter((e)-> e.getTitle().toLowerCase().contains(searchWord) || e.getAuthor().toLowerCase().contains(searchWord)
            || e.getCategory().toLowerCase().equals(searchWord) || e.getIsbn().equals(searchWord)).forEach(foundBooks::add);

        if(!foundBooks.isEmpty()){
            DefaultTableModel model = new DefaultTableModel(colNamesEBooks, 0);

            for(E_Books e : foundBooks){
                model.addRow(new Object[]{e.getTitle(),e.getAuthor(), e.getIsbn(),e.getPublisher(),e.getCategory()});
            }
            eBooksTable.setModel(model);
            eBookstxt.setText("");
        }else {
            JOptionPane.showMessageDialog(this, "Ingen bok matchade din sökning");
        }
    }//GEN-LAST:event_jLabelSearcheBookIconMouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        StartPage sp = new StartPage();
        sp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel4MouseClicked

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
            java.util.logging.Logger.getLogger(ViewBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewBooks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BooksTable;
    private javax.swing.JTextField Bookstxt;
    private javax.swing.JButton btnClose;
    private javax.swing.JTable eBooksTable;
    private javax.swing.JTextField eBookstxt;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelAboutBook;
    private javax.swing.JLabel jLabelImg;
    private javax.swing.JLabel jLabelSearchBookIcon;
    private javax.swing.JLabel jLabelSearchBookingsText1;
    private javax.swing.JLabel jLabelSearchBookingsText2;
    private javax.swing.JLabel jLabelSearcheBookIcon;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelInvisible;
    private javax.swing.JPanel jPanelTabBookings;
    private javax.swing.JPanel jPanelTabLendings;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelbackground;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPaneReport;
    private javax.swing.JButton jbtnAboutBook;
    private javax.swing.JButton jbtnAboutBook1;
    // End of variables declaration//GEN-END:variables
}
