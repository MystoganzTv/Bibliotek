/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.ArrayList;
import java.util.List;
import se.database.QueryMethods;
import se.model.Admin;
import se.model.Booking;
import se.model.Books;
import se.model.BorrowEBooks;
import se.model.BorrowedBooks;
import se.model.Category;
import se.model.DeletedBook;
import se.model.E_Books;
import se.model.Guest;
import se.model.Librarian;
import se.model.LibraryCards;
import se.model.Seminar;

/**
 *
 * @author danny
 */
public class JDBCDatabaseServiceImp implements DatabaseService{

    QueryMethods queryMethods = new QueryMethods();
    
    private DatabaseService databaseService;
    
    public JDBCDatabaseServiceImp(DatabaseService databaseDervice){
        this.databaseService = databaseService;
    }
    
    @Override
    public boolean isPersonNumberTaken(String personalNumber) {
      return  queryMethods.isPersonNumberTaken(personalNumber);     
    }

    @Override
    public ArrayList<String> getAllAdminPersonIds() {
        return queryMethods.getAllAdminPersonIds();
    }
    
    @Override
    public ArrayList<String> getAllLibrariansIds() {
        return queryMethods.getAllLibrariansIds();
    }
    
    @Override
    public ArrayList<String> getAllGuestsIds() {
        return queryMethods.getAllGuestsIds();
    }
    
    @Override
    public List<Books> getAllBooks() {
        return queryMethods.getAllBooks();
    }
    
    @Override
    public boolean isEmailTaken(String email) {
      return  queryMethods.isEmailTaken(email);     
    }
            
    @Override
    public ArrayList<DeletedBook> findDeletedBooks() {
        return queryMethods.findDeletedBooks();
    }

    @Override
    public ArrayList<DeletedBook> findDeletedEboks() {
        return queryMethods.findDeletedEBooks();
    }

    @Override
    public ArrayList<Admin> findAdmins() {
        return queryMethods.findAdmins();
    }

    @Override
    public ArrayList<Librarian> findLibrarians() {
        return queryMethods.findLibrarians();
    }

    @Override
    public Guest findGuestByMail(String email) {
        return queryMethods.findGuestByMail(email);
    }

    @Override
    public ArrayList<Guest> findGuests() {
        return queryMethods.findGuests();
    }

    @Override
    public ArrayList<Category> findCategories() {
        return queryMethods.findCategories();
    }

    @Override
    public String loginChecker(String user, String username, String password) {
        return queryMethods.loginChecker(user, username, password);
    }

    @Override
    public boolean deleteGuest(Guest guest) {
        return queryMethods.deleteGuest(guest);
    }


    @Override
    public void deleteAdmin(Admin admin) {
       queryMethods.deleteAdmin(admin);
    }

  
    @Override
    public void deleteLibrarian(Librarian librarian) {
        queryMethods.deleteLibrarian(librarian);
    }

    @Override
    public void addBook(Books b) {
        queryMethods.addBook(b);
    }

    @Override
    public void deleteBook(Books b, String notes) {
        queryMethods.deleteBook(b, notes);
    }

    @Override
    public void addEBook(E_Books b) {
        queryMethods.addEBook(b);
    }

    @Override
    public void deleteE_Book(E_Books b) {
        queryMethods.deleteE_Book(b);
    }

    @Override
    public ArrayList<LibraryCards> blockedCards() {
        return queryMethods.blockedCards();
    }

    @Override
    public ArrayList<LibraryCards> getBlockedCards() {
        return queryMethods.getBlockedCards();
    }

    @Override
    public int getLibaryCardIdByGuestId(int guestId) {
       return queryMethods.getLibaryCardIdByGuestId(guestId);
    }

    @Override
    public ArrayList<LibraryCards> getAllCards() {
        return queryMethods.getAllCards();
    }

    @Override
    public ArrayList<LibraryCards> getGuestsLibraryCardsByGuestList(ArrayList<Guest> guests) {
        return queryMethods.getGuestsLibraryCardsByGuestList(guests);
    }

    @Override
    public void createLibraryCard(int guestId) {
        queryMethods.createLibraryCard(guestId);
    }

    @Override
    public void updateLibraryCards(int entry, int userId, String category) {
        queryMethods.updateLibraryCards(entry, userId, category);
    }

    @Override
    public ArrayList<Books> findBooksByField(String field, String input) {
       return queryMethods.findBooksByField(field, input);
    }

    @Override
    public ArrayList<Books> findBooksByIsbn(String isbn) {
        return queryMethods.findBooksByIsbn(isbn);
    }

    @Override
    public E_Books findEBookByField(String field, String input) {
        return queryMethods.findEBookByField(field, input);
    }

    @Override
    public ArrayList<DeletedBook> getRemovedBooks() {
        return queryMethods.getRemovedBooks();
    }

    @Override
    public void borrowBooks(int bookId, int libraryCardId) {
        queryMethods.borrowBooks(bookId, libraryCardId);
    }

    @Override
    public void borrowEBooks(int eBookId, int libraryCardId) {
        queryMethods.borrowEBooks(eBookId, libraryCardId);
    }

    @Override
    public LibraryCards findLibrarycardByEmail(String guestEmail) {
       return queryMethods.findLibrarycardByEmail(guestEmail);
    }

    @Override
    public ArrayList<BorrowedBooks> getAllBorrowedBooks() {
        return queryMethods.getAllBorrowedBooks();
    }

    @Override
    public ArrayList<BorrowEBooks> getAllBorrowedEBooks() {
        return queryMethods.getAllBorrowedEBooks();
    }

    @Override
    public ArrayList<Books> getBorrowedBooksByCardId(int libraryCardId) {
        return queryMethods.getBorrowedBooksByCardId(libraryCardId);
    }

    @Override
    public void returnBook(int bookId) {
        queryMethods.returnBook(bookId);
    }

    @Override
    public ArrayList<Seminar> findSeminar() {
        return queryMethods.findSeminar();
    }

    @Override
    public Seminar findSeminarByTitle(String title) {
        return queryMethods.findSeminarByTitle(title);
    }

    @Override
    public void addSeminar(Seminar seminar) {
         queryMethods.addSeminar(seminar);
    }

    @Override
    public void bookSeminar(LibraryCards g, Seminar s) {
        queryMethods.bookSeminar(g, s);
    }

    @Override
    public void cancelSeminarReservation(Guest g, String title) {
        queryMethods.cancelSeminarReservation(g, title);
    }

    @Override
    public ArrayList<Books> groupAllBooksByIsbn() {
       return queryMethods.groupAllBooksByIsbn();
    }

    @Override
    public Books findBorrowedBookByBookId(int bookId) {
       return queryMethods.findBorrowedBookByBookId(bookId);
    }

    @Override
    public ArrayList<String> readBook(int idEbook) {
        return queryMethods.readBook(idEbook);
    }

    @Override
    public ArrayList<Booking> getAllBookedSeminars() {
        return queryMethods.getAllBookedSeminars();
    }
}
