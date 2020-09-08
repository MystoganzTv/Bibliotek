/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.database;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.model.Admin;
import se.model.Books;
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
 * @author Zsombor
 */
public class QueryMethodsTest {
    
    public QueryMethodsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertEmail method, of class QueryMethods.
     */
    @Test
    public void testInsertEmail() {
        System.out.println("insertEmail");
        String email = "";
        QueryMethods instance = new QueryMethods();
        instance.insertEmail(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmailTaken method, of class QueryMethods.
     */
    @Test
    public void testIsEmailTaken() {
        System.out.println("isEmailTaken");
        String email = "";
        QueryMethods instance = new QueryMethods();
        boolean expResult = false;
        boolean result = instance.isEmailTaken(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertGuest method, of class QueryMethods.
     */
    @Test
    public void testInsertGuest() {
        System.out.println("insertGuest");
        String firstName = "";
        String lastName = "";
        String socialNumber = "";
        String password = "";
        String email = "";
        QueryMethods instance = new QueryMethods();
        int expResult = 0;
        int result = instance.insertGuest(firstName, lastName, socialNumber, password, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertAdmin method, of class QueryMethods.
     */
    @Test
    public void testInsertAdmin() {
        System.out.println("insertAdmin");
        String firstName = "";
        String lastName = "";
        String socialNumber = "";
        String password = "";
        String email = "";
        QueryMethods instance = new QueryMethods();
        int expResult = 0;
        int result = instance.insertAdmin(firstName, lastName, socialNumber, password, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertLibrarian method, of class QueryMethods.
     */
    @Test
    public void testInsertLibrarian() {
        System.out.println("insertLibrarian");
        String firstName = "";
        String lastName = "";
        String socialNumber = "";
        String password = "";
        String email = "";
        QueryMethods instance = new QueryMethods();
        int expResult = 0;
        int result = instance.insertLibrarian(firstName, lastName, socialNumber, password, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findDeletedBooks method, of class QueryMethods.
     */
    @Test
    public void testFindDeletedBooks() {
        System.out.println("findDeletedBooks");
        QueryMethods instance = new QueryMethods();
        ArrayList<DeletedBook> expResult = null;
        ArrayList<DeletedBook> result = instance.findDeletedBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findDeletedEBooks method, of class QueryMethods.
     */
    @Test
    public void testFindDeletedEBooks() {
        System.out.println("findDeletedEBooks");
        QueryMethods instance = new QueryMethods();
        ArrayList<DeletedBook> expResult = null;
        ArrayList<DeletedBook> result = instance.findDeletedEBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAdmins method, of class QueryMethods.
     */
    @Test
    public void testFindAdmins() {
        System.out.println("findAdmins");
        QueryMethods instance = new QueryMethods();
        ArrayList<Admin> expResult = null;
        ArrayList<Admin> result = instance.findAdmins();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findLibrarians method, of class QueryMethods.
     */
    @Test
    public void testFindLibrarians() {
        System.out.println("findLibrarians");
        QueryMethods instance = new QueryMethods();
        ArrayList<Librarian> expResult = null;
        ArrayList<Librarian> result = instance.findLibrarians();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findGuests method, of class QueryMethods.
     */
    @Test
    public void testFindGuests() {
        System.out.println("findGuests");
        QueryMethods instance = new QueryMethods();
        ArrayList<Guest> expResult = null;
        ArrayList<Guest> result = instance.findGuests();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBooks method, of class QueryMethods.
     */
    @Test
    public void testFindBooks() {
        System.out.println("findBooks");
        QueryMethods instance = new QueryMethods();
        ArrayList<Books> expResult = null;
        ArrayList<Books> result = instance.findBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEBooks method, of class QueryMethods.
     */
    @Test
    public void testFindEBooks() {
        System.out.println("findEBooks");
        QueryMethods instance = new QueryMethods();
        ArrayList<E_Books> expResult = null;
        ArrayList<E_Books> result = instance.getAllEBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findCategories method, of class QueryMethods.
     */
    @Test
    public void testFindCategories() {
        System.out.println("findCategories");
        QueryMethods instance = new QueryMethods();
        ArrayList<Category> expResult = null;
        ArrayList<Category> result = instance.findCategories();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginChecker method, of class QueryMethods.
     */
    @Test
    public void testLoginChecker() {
        System.out.println("loginChecker");
        String user = "";
        String username = "";
        String password = "";
        QueryMethods instance = new QueryMethods();
        String expResult = "";
        String result = instance.loginChecker(user, username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBooks method, of class QueryMethods.
     */
    @Test
    public void testGetAllBooks() {
        System.out.println("getAllBooks");
        QueryMethods instance = new QueryMethods();
        List<Books> expResult = null;
        List<Books> result = instance.getAllBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllEBooks method, of class QueryMethods.
     */
    @Test
    public void testGetAllEBooks() {
        System.out.println("getAllEBooks");
        QueryMethods instance = new QueryMethods();
        List<E_Books> expResult = null;
        List<E_Books> result = instance.getAllEBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteGuest method, of class QueryMethods.
     */
    @Test
    public void testDeleteGuest() {
        System.out.println("deleteGuest");
        Guest guest = null;
        QueryMethods instance = new QueryMethods();
        instance.deleteGuest(guest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteAdmin method, of class QueryMethods.
     */
    @Test
    public void testDeleteAdmin() {
        System.out.println("deleteAdmin");
        Admin admin = null;
        QueryMethods instance = new QueryMethods();
        instance.deleteAdmin(admin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteLibrarian method, of class QueryMethods.
     */
    @Test
    public void testDeleteLibrarian() {
        System.out.println("deleteLibrarian");
        Librarian librarian = null;
        QueryMethods instance = new QueryMethods();
        instance.deleteLibrarian(librarian);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addBook method, of class QueryMethods.
     */
    @Test
    public void testAddBook() {
        System.out.println("addBook");
        Books b = null;
        QueryMethods instance = new QueryMethods();
        instance.addBook(b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteBook method, of class QueryMethods.
     */
    @Test
    public void testDeleteBook() {
        System.out.println("deleteBook");
        Books b = null;
        String notes = "";
        QueryMethods instance = new QueryMethods();
        instance.deleteBook(b, notes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addEBook method, of class QueryMethods.
     */
    @Test
    public void testAddEBook() {
        System.out.println("addEBook");
        E_Books b = null;
        QueryMethods instance = new QueryMethods();
        instance.addEBook(b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteE_Book method, of class QueryMethods.
     */
    @Test
    public void testDeleteE_Book() {
        System.out.println("deleteE_Book");
        E_Books b = null;
        QueryMethods instance = new QueryMethods();
        instance.deleteE_Book(b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of blockedCards method, of class QueryMethods.
     */
    @Test
    public void testBlockedCards() {
        System.out.println("blockedCards");
        QueryMethods instance = new QueryMethods();
        ArrayList<LibraryCards> expResult = null;
        ArrayList<LibraryCards> result = instance.blockedCards();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlockedCards method, of class QueryMethods.
     */
    @Test
    public void testGetBlockedCards() {
        System.out.println("getBlockedCards");
        QueryMethods instance = new QueryMethods();
        ArrayList<LibraryCards> expResult = null;
        ArrayList<LibraryCards> result = instance.getBlockedCards();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllCards method, of class QueryMethods.
     */
    @Test
    public void testGetAllCards() {
        System.out.println("getAllCards");
        QueryMethods instance = new QueryMethods();
        ArrayList<LibraryCards> expResult = null;
        ArrayList<LibraryCards> result = instance.getAllCards();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGuestsLibraryCardsByGuestList method, of class QueryMethods.
     */
    @Test
    public void testGetGuestsLibraryCardsByGuestList() {
        System.out.println("getGuestsLibraryCardsByGuestList");
        ArrayList<Guest> guests = null;
        QueryMethods instance = new QueryMethods();
        ArrayList<LibraryCards> expResult = null;
        ArrayList<LibraryCards> result = instance.getGuestsLibraryCardsByGuestList(guests);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateLibraryCards method, of class QueryMethods.
     */
    @Test
    public void testUpdateLibraryCards() {
        System.out.println("updateLibraryCards");
        int entry = 0;
        int userId = 0;
        String category = "";
        QueryMethods instance = new QueryMethods();
        instance.updateLibraryCards(entry, userId, category);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBooksByTitle method, of class QueryMethods.
     */
    @Test
    public void testFindBooksByTitle() {
        System.out.println("findBooksByTitle");
        String title = "";
        QueryMethods instance = new QueryMethods();
        ArrayList<Books> expResult = null;
        ArrayList<Books> result = instance.findBooksByTitle(title);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBooksByCategory method, of class QueryMethods.
     */
    @Test
    public void testFindBooksByCategory() {
        System.out.println("findBooksByCategory");
        String category = "";
        QueryMethods instance = new QueryMethods();
        ArrayList<Books> expResult = null;
        ArrayList<Books> result = instance.findBooksByCategory(category);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBooksByAuthor method, of class QueryMethods.
     */
    @Test
    public void testFindBooksByAuthor() {
        System.out.println("findBooksByAuthor");
        String author = "";
        QueryMethods instance = new QueryMethods();
        ArrayList<Books> expResult = null;
        ArrayList<Books> result = instance.findBooksByAuthor(author);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBookByIsbn method, of class QueryMethods.
     */
    @Test
    public void testFindBookByIsbn() {
        System.out.println("findBookByIsbn");
        String isbn = "";
        QueryMethods instance = new QueryMethods();
        Books expResult = null;
        Books result = instance.findBookByIsbn(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBooksByIsbn method, of class QueryMethods.
     */
    @Test
    public void testFindBooksByIsbn() {
        System.out.println("findBooksByIsbn");
        String isbn = "";
        QueryMethods instance = new QueryMethods();
        ArrayList<Books> expResult = null;
        ArrayList<Books> result = instance.findBooksByIsbn(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEBookByIsbn method, of class QueryMethods.
     */
    @Test
    public void testFindEBookByIsbn() {
        System.out.println("findEBookByIsbn");
        String isbn = "";
        QueryMethods instance = new QueryMethods();
        E_Books expResult = null;
        E_Books result = instance.findEBookByIsbn(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEBooksByTitle method, of class QueryMethods.
     */
    @Test
    public void testFindEBooksByTitle() {
        System.out.println("findEBooksByTitle");
        String title = "";
        QueryMethods instance = new QueryMethods();
        ArrayList<E_Books> expResult = null;
        ArrayList<E_Books> result = instance.findEBooksByTitle(title);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEBooksByAuthor method, of class QueryMethods.
     */
    @Test
    public void testFindEBooksByAuthor() {
        System.out.println("findEBooksByAuthor");
        String author = "";
        QueryMethods instance = new QueryMethods();
        ArrayList<E_Books> expResult = null;
        ArrayList<E_Books> result = instance.findEBooksByAuthor(author);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findEBooksByCategory method, of class QueryMethods.
     */
    @Test
    public void testFindEBooksByCategory() {
        System.out.println("findEBooksByCategory");
        String category = "";
        QueryMethods instance = new QueryMethods();
        ArrayList<E_Books> expResult = null;
        ArrayList<E_Books> result = instance.findEBooksByCategory(category);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRemovedBooks method, of class QueryMethods.
     */
    @Test
    public void testGetRemovedBooks() {
        System.out.println("getRemovedBooks");
        QueryMethods instance = new QueryMethods();
        ArrayList<DeletedBook> expResult = null;
        ArrayList<DeletedBook> result = instance.getRemovedBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of borrowBooks method, of class QueryMethods.
     */
    @Test
    public void testBorrowBooks() {
        System.out.println("borrowBooks");
        int bookId = 0;
        int libraryCardId = 0;
        QueryMethods instance = new QueryMethods();
        instance.borrowBooks(bookId, libraryCardId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of borrowEBooks method, of class QueryMethods.
     */
    @Test
    public void testBorrowEBooks() {
        System.out.println("borrowEBooks");
        int eBookId = 0;
        int libraryCardId = 0;
        QueryMethods instance = new QueryMethods();
        instance.borrowEBooks(eBookId, libraryCardId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findLibrarycardByEmail method, of class QueryMethods.
     */
    @Test
    public void testFindLibrarycardByEmail() {
        System.out.println("findLibrarycardByEmail");
        String guestEmail = "";
        QueryMethods instance = new QueryMethods();
        LibraryCards expResult = null;
        LibraryCards result = instance.findLibrarycardByEmail(guestEmail);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBorrowedBooks method, of class QueryMethods.
     */
    @Test
    public void testGetAllBorrowedBooks() {
        System.out.println("getAllBorrowedBooks");
        QueryMethods instance = new QueryMethods();
        ArrayList<BorrowedBooks> expResult = null;
        ArrayList<BorrowedBooks> result = instance.getAllBorrowedBooks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBorrowedBooksByCardId method, of class QueryMethods.
     */
    @Test
    public void testGetBorrowedBooksByCardId() {
        System.out.println("getBorrowedBooksByCardId");
        int libraryCardId = 0;
        QueryMethods instance = new QueryMethods();
        ArrayList<Books> expResult = null;
        ArrayList<Books> result = instance.getBorrowedBooksByCardId(libraryCardId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnBook method, of class QueryMethods.
     */
    @Test
    public void testReturnBook() {
        System.out.println("returnBook");
        int bookId = 0;
        QueryMethods instance = new QueryMethods();
        instance.returnBook(bookId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSeminar method, of class QueryMethods.
     */
    @Test
    public void testFindSeminar() {
        System.out.println("findSeminar");
        QueryMethods instance = new QueryMethods();
        ArrayList<Seminar> expResult = null;
        ArrayList<Seminar> result = instance.findSeminar();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSeminar method, of class QueryMethods.
     */
    @Test
    public void testAddSeminar() {
        System.out.println("addSeminar");
        Seminar seminar = null;
        QueryMethods instance = new QueryMethods();
        instance.addSeminar(seminar);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of bookSeminar method, of class QueryMethods.
     */
    @Test
    public void testBookSeminar() {
        System.out.println("bookSeminar");
        Guest g = new Guest();
        g.setId(1);
        Seminar s = new Seminar();
        s.setId(2);
        QueryMethods instance = new QueryMethods();
        instance.bookSeminar(g, s);
    }

    /**
     * Test of groupAllBooksByIsbn method, of class QueryMethods.
     */
    @Test
    public void testGroupAllBooksByIsbn() {
        System.out.println("groupAllBooksByIsbn");
        QueryMethods instance = new QueryMethods();
        ArrayList<Books> expResult = null;
        ArrayList<Books> result = instance.groupAllBooksByIsbn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBorrowedBookByBookId method, of class QueryMethods.
     */
    @Test
    public void testFindBorrowedBookById() {
        System.out.println("findBorrowedBookById");
        int id = 0;
        QueryMethods instance = new QueryMethods();
        Books expResult = null;
        Books result = instance.findBorrowedBookByBookId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
