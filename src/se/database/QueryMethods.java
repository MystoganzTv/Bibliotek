/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import se.model.Admin;
import se.model.Books;
import se.model.Category;
import se.model.DeletedBook;
import se.model.E_Books;
import se.model.Guest;
import se.model.Librarian;
import se.model.LibraryCards;

/**
 *
 * @author Erik Ringblom
 */
public class QueryMethods {

    Connection con = null;

    String query = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public QueryMethods() {

    }

    public void insertEmail(String email) {
        con = MyConnection.getConnection();

        query = "INSERT INTO emails (email) VALUES (?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.execute();
        } catch (SQLException e) {

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isEmailTaken(String email) {

        con = MyConnection.getConnection();
        boolean check = false;

        try {
            Statement statement = con.createStatement();
            System.out.println(email);
            query = "SELECT email FROM emails WHERE email=" + "\"" + email + "\"";
            rs = statement.executeQuery(query);

            while (rs.next()) {
                if (rs.getString(1).equals(email)) {
                    check = true;
                } else {
                    check = false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }

    public int insertGuest(String firstName, String lastName, String socialNumber, String password, String email) {
        int idGuest = 0;

        insertEmail(email);
        con = MyConnection.getConnection();

        query = "INSERT INTO guests (first_name, last_name, person_id, password, email) VALUES (?,?,?,?,?)";

        try {
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, socialNumber);
            ps.setString(4, password);
            ps.setString(5, email);

            int update = ps.executeUpdate();
            if (update == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGuest = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, firstName + " " + lastName + " har registrerats!");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idGuest;

    }

    public int insertAdmin(String firstName, String lastName, String socialNumber, String password, String email) {

        insertEmail(email);
        int idAdmin = 0;
        con = MyConnection.getConnection();
        query = "INSERT INTO admins (first_name, last_name, person_id, password, email) VALUES (?,?,?,?,?)";

        try {
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, socialNumber);
            ps.setString(4, password);
            ps.setString(5, email);

            int update = ps.executeUpdate();
            if (update == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idAdmin = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, firstName + " " + lastName + " har registrerats!");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idAdmin;

    }

    public int insertLibrarian(String firstName, String lastName, String socialNumber, String password, String email) {

        insertEmail(email);
        int idLibrarian = 0;
        con = MyConnection.getConnection();
        query = "INSERT INTO librarians (first_name, last_name, person_id, password) VALUES (?,?,?,?,?)";

        try {
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, socialNumber);
            ps.setString(4, password);
            ps.setString(5, email);
            int update = ps.executeUpdate();
            if (update == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idLibrarian = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, firstName + " " + lastName + " har registrerats!");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return idLibrarian;

    }

    public ArrayList<Admin> findAdmins() {

        try {
            MyConnection tryConnect = new MyConnection();
            ArrayList<Admin> admins = new ArrayList<Admin>();
            Admin currentAdmin;

            Connection conn = tryConnect.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("SELECT * FROM admins");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentAdmin = new Admin(results.getString("first_name"),
                        results.getString("last_name"),
                        results.getString("person_id"),
                        results.getString("password"),
                        Integer.parseInt(results.getString("id")),
                        results.getString("email"));
                admins.add(currentAdmin);
                currentAdmin = null;
            }

            conn.close();
            stmt.close();

            return admins;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }

        return null;
    }

    public ArrayList<Librarian> findLibrarians() {

        try {

            ArrayList<Librarian> librarians = new ArrayList<Librarian>();
            Librarian currentLibrarian;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM librarians");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentLibrarian = new Librarian(Integer.parseInt(results.getString("id")),
                        results.getString("first_name"),
                        results.getString("last_name"),
                        results.getString("person_id"),
                        results.getString("password"),
                        results.getString("email"));
                librarians.add(currentLibrarian);
                currentLibrarian = null;
            }

            con.close();
            stmt.close();

            return librarians;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }

        return null;
    }

    public ArrayList<Guest> findGuests() {

        try {

            ArrayList<Guest> guests = new ArrayList<Guest>();
            Guest currentGuest;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM guests");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentGuest = new Guest(Integer.parseInt(results.getString("id")),
                        results.getString("first_name"),
                        results.getString("last_name"),
                        results.getString("person_id"),
                        results.getString("password"),
                        results.getString("email"));
                guests.add(currentGuest);
                currentGuest = null;
            }

            con.close();
            stmt.close();

            return guests;
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }

        return null;
    }

    public ArrayList<Books> findBooks() {

        try {

            ArrayList<Books> books = new ArrayList<Books>();
            Books currentBooks;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM books");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentBooks = new Books(Integer.parseInt(results.getString("id")),
                        results.getString("title"),
                        results.getString("author"),
                        results.getString("isbn"),
                        results.getString("publisher"),
                        results.getDouble("purchase_price"),
                        results.getString("category"),
                        results.getString("placement"));
                books.add(currentBooks);
                currentBooks = null;
            }

            con.close();
            stmt.close();

            return books;
        } catch (SQLException e) {
            System.out.println("Något gick fel: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Category> findCategories() {

        try {

            ArrayList<Category> categories = new ArrayList<Category>();
            Category currentCat;

            con = MyConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SELECT * FROM categories");

            ResultSet results = stmt.getResultSet();
            while (results.next()) {
                currentCat = new Category(results.getString("category"));
                categories.add(currentCat);
                currentCat = null;
            }

            con.close();
            stmt.close();

            return categories;
        } catch (SQLException e) {
            System.out.println("Något gick fel: " + e.getMessage());
        }

        return null;
    }

    public String loginChecker(String user, String username, String password) {
        String exist = " select email, password from " + user + " where email = '" + username + "'"
                + "and password = '" + password + "';";
        PreparedStatement check;
        String email = "";

        try {
            check = MyConnection.getConnection().prepareStatement(exist);

            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (Exception e) {
            System.out.println(e.toString() + " loginChecker()");
        }
        return email;

    }

    public List<Books> getAllBooks() {

        try {
            List<Books> books = new ArrayList<>();

            String bookQuery = "SELECT id, title, author, isbn, publisher, purchase_price, category FROM books";

            con = MyConnection.getConnection();
            ps = con.prepareStatement(bookQuery);
            rs = ps.executeQuery();

            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                books.add(book);

            }
            return books;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public List<E_Books> getAllEBooks() {

        try {
            List<E_Books> e_books = new ArrayList<>();

            String bookQuery = "SELECT id, title, author, isbn, publisher, purchase_price, category FROM e_books";

            con = MyConnection.getConnection();
            ps = con.prepareStatement(bookQuery);
            rs = ps.executeQuery();

            while (rs.next()) {
                E_Books book = new E_Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                e_books.add(book);

            }

            return e_books;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                rs.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public void deleteGuest(Guest guest) {

        con = MyConnection.getConnection();

        String deleteEmailQuery = "DELETE FROM emails WHERE email=" + "'" + guest.getEmail() + "'";

        String deleteLibraryCard = "DELETE FROM librarycards WHERE guests_id=?";

        String deleteGuest = "DELETE FROM guests WHERE id=?";

        try {
            ps = con.prepareStatement(deleteEmailQuery);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            ps = con.prepareStatement(deleteLibraryCard);
            ps.setInt(1, guest.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            ps = con.prepareStatement(deleteGuest);
            ps.setInt(1, guest.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void deleteAdmin(Admin admin) {

        con = MyConnection.getConnection();

        String deleteEmailQuery = "DELETE FROM emails WHERE email=" + "'" + admin.getEmail() + "'";

        String deleteAdmin = "DELETE FROM admins WHERE id=?";

        try {
            ps = con.prepareStatement(deleteEmailQuery);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ps = con.prepareStatement(deleteAdmin);
            ps.setInt(1, admin.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void deleteLibrarian(Librarian librarian) {

        con = MyConnection.getConnection();

        String deleteEmailQuery = "DELETE FROM emails WHERE email=" + "'" + librarian.getEmail() + "'";

        String deleteLibrarian = "DELETE FROM librarians WHERE id=?";

        try {
            ps = con.prepareStatement(deleteEmailQuery);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ps = con.prepareStatement(deleteLibrarian);
            ps.setInt(1, librarian.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void addBook(Books b) {

        con = MyConnection.getConnection();

        try {
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO books(title, author, isbn, publisher, purchase_price, category)"
                    + " VALUES ('" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getIsbn() + "', '"
                    + b.getPublisher() + "', " + b.getPurchase_price() + ", '" + b.getCategory() + "')");
            stmt.close();
            con.close();

        } catch (Exception e) {

            
            JOptionPane.showMessageDialog(null, "Boken har sparats!");
        }
    }

    public void deleteBook(Books b) {

        con = MyConnection.getConnection();

        String deleteBookQuery = "DELETE FROM books WHERE id=?";

        try {
            ps = con.prepareStatement(deleteBookQuery);
            ps.setInt(1, b.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Något gick fel när du har försökt att radera den bok: " + e.getMessage());
        }

    }

    public void addEBook(E_Books b) {

        MyConnection tryConnect = new MyConnection();
        con = MyConnection.getConnection();

        try {
            Statement stmt = con.createStatement();
            stmt.execute("INSERT INTO e_books(title, author, isbn, publisher, purchase_price, category) "
                    + " VALUES('" + b.getTitle() + "', '" + b.getAuthor() + "', '" + b.getIsbn() + "', '"
                    + b.getPublisher() + "', " + b.getPurchase_price() + ", '" + b.getCategory() + "')");
            stmt.close();
            con.close();

        } catch (Exception e) {

            
            JOptionPane.showMessageDialog(null, "E-Boken har sparats!");
        }
    }

    public void deleteE_Book(E_Books b) {

        con = MyConnection.getConnection();

        String deleteE_BookQuery = "DELETE FROM e-books WHERE id=?";

        try {
            ps = con.prepareStatement(deleteE_BookQuery);
            ps.setInt(1, b.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Något gick fel när du har försökt att radera denna e-bok: " + e.getMessage());
        }

    }

    public ArrayList<LibraryCards> blockedCards() {

        String blockedListQuery = "select concat( first_name,\" \", last_name)as fullname, "
                + "guests.id, category from guests join \n"
                + "librarycards on guests.id = librarycards.guests_id\n"
                + "where entry = 1;";

        ArrayList<LibraryCards> blockedCards = new ArrayList<LibraryCards>();
        LibraryCards currentList;

        con = MyConnection.getConnection();
        PreparedStatement check;

        try {

            check = con.prepareStatement(blockedListQuery);
            ResultSet rs = check.executeQuery();
            System.out.println();
            while (rs.next()) {
                currentList = new LibraryCards(rs.getString("fullname"),
                        rs.getInt("guests.id"),
                        rs.getString("category"));

                blockedCards.add(currentList);
            }
            con.close();
            check.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return blockedCards;

    }

    public ArrayList<LibraryCards> getAllCards() {
        String query = "select guests_id, concat(first_name, ' ', last_name)as fullname,\n"
                + "entry, category from librarycards join guests on guests_id = guests.id;";

        ArrayList<LibraryCards> allCardsList = new ArrayList<LibraryCards>();
        LibraryCards list;

        con = MyConnection.getConnection();
        PreparedStatement ps;

        try {
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list = new LibraryCards(rs.getInt("guests_id"),
                        rs.getString("fullname"),
                        rs.getInt("entry"),
                        rs.getString("category"));
                allCardsList.add(list);
            }

        } catch (Exception e) {
            System.out.println(e.toString() + " getAllCards()");
        }
        return allCardsList;
    }

    // entry is a boolean type in database, 0 = false 1 = true
    public void updateLibraryCards(int entry, int userId, String category) {
        String query = "";
        if (entry == 1) {
            query = "UPDATE librarycards SET entry = 1, category = '" + category + "' WHERE id = " + userId + ";";
        } else if (entry == 0) {
            query = "UPDATE librarycards SET entry = 0, category = '' WHERE id = " + userId + ";";
        }

        con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(query);
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.toString() + " updateLibraryCards()");
        }

    }

    //returning Arraylist of books, might be empty if no match!
    public ArrayList<Books> findBooksByTitle(String title) {
        ArrayList<Books> foundBooks = new ArrayList<>();

        String query = "SELECT * FROM books WHERE title LIKE '%" + title + "%'";

        con = MyConnection.getConnection();

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                foundBooks.add(book);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            try {
                con.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundBooks;
    }

    //returning Arraylist of books, might be empty if no match!
    public ArrayList<Books> findBooksByCategory(String category) {
        ArrayList<Books> foundBooks = new ArrayList<>();
        String query = "SELECT * FROM books WHERE category = '" + category + "'";

        con = MyConnection.getConnection();

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                foundBooks.add(book);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return foundBooks;
    }

    //returning Arraylist of books, might be empty if no match!
    public ArrayList<Books> findBooksByAuthor(String author) {
        ArrayList<Books> foundBooks = new ArrayList<>();

        con = MyConnection.getConnection();
        String query = "SELECT * FROM books WHERE author LIKE '%" + author + "%'";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                foundBooks.add(book);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundBooks;
    }

    // Returning NULL if no book found, Catch nullpointer when trying to find book!
    public Books findBookByIsbn(String isbn) {
        String query = "SELECT * FROM books WHERE isbn = '" + isbn + "'";

        con = MyConnection.getConnection();
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setIsbn(rs.getString(4));
                book.setPublisher(rs.getString(5));
                book.setPurchase_price(rs.getDouble(6));
                book.setCategory(rs.getString(7));
                book.setPlacement(rs.getString(8));
                return book;
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        return null;

    }

    public ArrayList<E_Books> findEBooksByTitle(String title) {

        ArrayList<E_Books> foundEBooks = new ArrayList<>();
        con = MyConnection.getConnection();
        String query = "SELECT * FROM e_books WHERE title LIKE '%" + title + "%'";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                E_Books eBook = new E_Books();
                eBook.setId(rs.getInt(1));
                eBook.setTitle(rs.getString(2));
                eBook.setAuthor(rs.getString(3));
                eBook.setIsbn(rs.getString(4));
                eBook.setPublisher(rs.getString(5));
                eBook.setPurchase_price(rs.getDouble(6));
                eBook.setCategory(rs.getString(7));
                foundEBooks.add(eBook);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundEBooks;
    }

    public ArrayList<E_Books> findEBooksByAuthor(String author) {

        ArrayList<E_Books> foundEBooks = new ArrayList<>();
        con = MyConnection.getConnection();
        String query = "SELECT * FROM e_books WHERE author LIKE '%" + author + "%'";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                E_Books eBook = new E_Books();
                eBook.setId(rs.getInt(1));
                eBook.setTitle(rs.getString(2));
                eBook.setAuthor(rs.getString(3));
                eBook.setIsbn(rs.getString(4));
                eBook.setPublisher(rs.getString(5));
                eBook.setPurchase_price(rs.getDouble(6));
                eBook.setCategory(rs.getString(7));
                foundEBooks.add(eBook);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundEBooks;
    }

    public ArrayList<E_Books> findEBooksByCategory(String category) {
        ArrayList<E_Books> foundEBooks = new ArrayList<>();

        con = MyConnection.getConnection();

        String query = "SELECT * FROM e_books WHERE category = '" + category + "'";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                E_Books eBook = new E_Books();
                eBook.setId(rs.getInt(1));
                eBook.setTitle(rs.getString(2));
                eBook.setAuthor(rs.getString(3));
                eBook.setIsbn(rs.getString(4));
                eBook.setPublisher(rs.getString(5));
                eBook.setPurchase_price(rs.getDouble(6));
                eBook.setCategory(rs.getString(7));
                foundEBooks.add(eBook);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return foundEBooks;
    }

    public ArrayList<DeletedBook> getRemovedBooks() {

        con = MyConnection.getConnection();
        ArrayList<DeletedBook> RemovedBooks = new ArrayList<>();

        try {
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM deleted_books");
            while (rs.next()) {
                DeletedBook deletedBook = new DeletedBook();
                deletedBook.setId(rs.getInt(1));
                deletedBook.setTitle(rs.getString("title"));
                deletedBook.setAuthor(rs.getString("author"));
                deletedBook.setPublisher(rs.getString("publisher"));
                deletedBook.setIsbn(rs.getString("isbn"));
                deletedBook.setBookType(rs.getString("bookType"));
                deletedBook.setPurchasePrice(rs.getString("purchase_price"));
                deletedBook.setCategory(rs.getString("category"));
                deletedBook.setNotes(rs.getString("notes"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
