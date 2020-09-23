/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.ArrayList;
import java.util.List;
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
public interface DatabaseService {
    
    //Enrique Delete
    //Anna Add
    //Tadevos update
    //Erik Librarycards
    //Danny Books
    //Simon Resten
    
    boolean isPersonNumberTaken(String personalNumber);//färdig
    ArrayList<String> getAllAdminPersonIds();//färdig
    List<Books> getAllBooks();// färdigt testat
    ArrayList<String>getAllLibrariansIds();//färdig
    ArrayList<String>getAllGuestsIds();//färdig
    boolean isEmailTaken(String email);//färdig
    ArrayList<DeletedBook>findDeletedBooks();//färdig
    ArrayList<DeletedBook>findDeletedEboks();
    ArrayList<Admin> findAdmins();    //färdig på Andreas Sätt
    ArrayList<Librarian> findLibrarians(); // färdig på Andreas Sätt
    Guest findGuestByMail(String email); // färdig på Andreas Sätt
    ArrayList<Guest> findGuests(); // färdig på Andreas Sätt
    ArrayList<Category> findCategories(); // färdig på Andreas Sätt
    String loginChecker(String user, String username, String password);//färdig
    boolean deleteGuest(Guest guest);//färdig på Andreas Sätt
    void deleteAdmin(Admin admin); //färdig på Andreas Sätt
    void deleteLibrarian(Librarian librarian);//färdig på Andreas Sätt
    void addBook(Books b);
    void deleteBook(Books b, String notes);
    void addEBook(E_Books b);
    void deleteE_Book(E_Books b);
    ArrayList<LibraryCards> blockedCards();
    ArrayList<LibraryCards> getBlockedCards();
    int getLibaryCardIdByGuestId(int guestId);
    ArrayList<LibraryCards> getAllCards();
    ArrayList<LibraryCards> getGuestsLibraryCardsByGuestList(ArrayList<Guest> guests);
    void createLibraryCard(int guestId);
    void updateLibraryCards(int entry, int userId, String category); //färdig på Andreas Sätt
    ArrayList<Books> findBooksByField(String field, String input);// oanvänd metod
    ArrayList<Books> findBooksByIsbn(String isbn);// färdig
    E_Books findEBookByField(String field, String input);//färdig på Andreas Sätt
    ArrayList<DeletedBook> getRemovedBooks();
    void borrowBooks(int bookId, int libraryCardId);
    void borrowEBooks(int eBookId, int libraryCardId);
    LibraryCards findLibrarycardByEmail(String guestEmail);
    ArrayList<BorrowedBooks> getAllBorrowedBooks();
    ArrayList<BorrowEBooks> getAllBorrowedEBooks();
    ArrayList<Books> getBorrowedBooksByCardId(int libraryCardId);//färdig
    void returnBook(int bookId);
    ArrayList<Seminar> findSeminar();
    Seminar findSeminarByTitle(String title);
    void addSeminar(Seminar seminar);
    void bookSeminar(LibraryCards g, Seminar s);
    void cancelSeminarReservation(Guest g, String title);
    ArrayList<Books> groupAllBooksByIsbn();
    Books findBorrowedBookByBookId(int bookId);
    ArrayList<String> readBook(int idEbook);
    ArrayList<Booking> getAllBookedSeminars();//färdig på Andreas Sätt
    int insertGuest(String firstName, String lastName, String socialNumber, String password, String email);//färdig på Andreas Sätt
}
