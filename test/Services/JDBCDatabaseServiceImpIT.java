/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import se.database.QueryMethods;
import se.database.QueryMethodsTest;
import se.model.Admin;
import se.model.Booking;
import se.model.Books;
import se.model.Category;
import se.model.DeletedBook;
import se.model.E_Books;
import se.model.Guest;
import se.model.Librarian;
import se.model.LibraryCards;

/**
 *
 * @author danny
 */
public class JDBCDatabaseServiceImpIT {
    
// Kommentera bort mocks för att testa på Andreas sätt.    
//    DatabaseService databaseServiceMock = Mockito.mock(DatabaseService.class);
//    JDBCDatabaseServiceImp databaseServiceImp = new JDBCDatabaseServiceImp(databaseServiceMock);

    JDBCDatabaseServiceImp databaseServiceImp; 
    QueryMethods qm = Mockito.mock(QueryMethods.class);
    
    public JDBCDatabaseServiceImpIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        databaseServiceImp = new JDBCDatabaseServiceImp(qm);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testIsPersonNumberTaken(){
        String personNumber = "200501155434";
        
        when(databaseServiceMock.isPersonNumberTaken(personNumber)).thenReturn(true);
        
        boolean actual = databaseServiceImp.isPersonNumberTaken(personNumber);
        
        assertTrue(actual);
        
    }
    
    @Test
    public void testGetAllAdminPersonIds(){
        ArrayList<String> value = new ArrayList<>();
        value.add("");
        value.add("XXXXXXXXXXXX");
        
        when(databaseServiceMock.getAllAdminPersonIds()).thenReturn(value);
            
        ArrayList<String> actual = databaseServiceImp.getAllAdminPersonIds();
        
        assertEquals(value, actual);
        
    }
    
    @Test
    public void testGetAllLibrariansIds(){
        ArrayList<String> value = new ArrayList<>();
        value.add("");
        value.add("XXXXXXXXXXXX");
        
        when(databaseServiceMock.getAllLibrariansIds()).thenReturn(value);
            
        ArrayList<String> actual = databaseServiceImp.getAllLibrariansIds();
        
        assertEquals(value, actual);
        
    }
            
    @Test
    public void testGetAllGuestsIds(){
        ArrayList<String> value = new ArrayList<>();
        value.add("");
        value.add("XXXXXXXXXXXX");
        
        when(databaseServiceMock.getAllGuestsIds()).thenReturn(value);
            
        ArrayList<String> actual = databaseServiceImp.getAllGuestsIds();
        
        assertEquals(value, actual);
        
    }
            
    @Test
    public void testIsEmailTaken(){
        String email = "alfons.bolt@libsys.se";
        when(databaseServiceMock.isEmailTaken(email)).thenReturn(true);
        
        boolean actual = databaseServiceImp.isEmailTaken(email);
        
        assertTrue(actual);
        
    }
    
    @Test
    public void deletedGuest(){
        
       Guest guest1 = new Guest();
       databaseServiceImp.deleteGuest(guest1);
       verify(qm, times(1)).deleteGuest(any()); 
    }
    
    @Test
    public void insertGuest(){
         
        when(qm.insertGuest(anyString(),anyString() , anyString(), anyString(), anyString())).thenReturn(2);
        int returnInt = databaseServiceImp.insertGuest(anyString(), anyString(),anyString(), anyString(), anyString());
        assertEquals(2, returnInt);
        verify(qm, times(1)).insertGuest(anyString(), anyString(),anyString(), anyString(), anyString());
    }
    
    @Test
    public void updateLibraryCards(){
        
        databaseServiceImp.updateLibraryCards(anyInt(), anyInt(), anyString());
        verify(qm, times(1)).updateLibraryCards(anyInt(), anyInt(), anyString());
        
    }
    
    @Test
    public void deleteAdmin(){
        
        Admin admin1 = new Admin();
        databaseServiceImp.deleteAdmin(admin1);
        verify(qm, times(1)).deleteAdmin(any());
    }
    
    @Test
    public void findLibrarians() {
        
        ArrayList<Librarian> librarianTest = new ArrayList<Librarian>();
        librarianTest.add(new Librarian(1));
        librarianTest.add(new Librarian(2));
        when(qm.findLibrarians()).thenReturn(librarianTest);
        ArrayList<Librarian> retrieveList = databaseServiceImp.findLibrarians();
        assertEquals(librarianTest.size(), retrieveList.size());
        assertEquals(1, retrieveList.get(0).getId());
        verify(qm, times(1)).findLibrarians();
    }
    
    @Test
    public void addBook() {
        
        Books b = new Books();
        databaseServiceImp.addBook(b);
        verify(qm, times(1)).addBook(any());
    }
    
    @Test
    public void findGuestByMail() {
        
        Guest guest = new Guest();
        guest.setEmail("email@mail.com");
        
        when(qm.findGuestByMail(anyString())).thenReturn(guest);
        Guest g = databaseServiceImp.findGuestByMail(anyString());
        assertEquals(g.getId(), guest.getId());
        verify(qm, times(1)).findGuestByMail(anyString());
    }
    
    @Test
    public void getBlockedCards() {
        
        ArrayList<LibraryCards> blockedTest = new ArrayList<LibraryCards>();
        blockedTest.add(new LibraryCards(1, 1, "asd", "asd", 1));
        blockedTest.add(new LibraryCards(2, 2, "asd", "asd", 1));
        when(qm.getBlockedCards()).thenReturn(blockedTest);
        ArrayList<LibraryCards> retrieveList = databaseServiceImp.getBlockedCards();
        assertEquals(blockedTest.size(), retrieveList.size());
        assertEquals(1, retrieveList.get(0).getEntry());
        verify(qm, times(1)).getBlockedCards();
    }
    
    @Test
    public void findGuests() {
        
        ArrayList<Guest> guestTest = new ArrayList<Guest>();
        guestTest.add(new Guest(1));
        guestTest.add(new Guest(2));
        when(qm.findGuests()).thenReturn(guestTest);
        ArrayList<Guest> retrieveList = databaseServiceImp.findGuests();
        assertEquals(guestTest.size(), retrieveList.size());
        assertEquals(1, retrieveList.get(0).getId());
        verify(qm, times(1)).findGuests();
    }
    
    @Test
    public void findCategories() {
        
        ArrayList<Category> categoryTest = new ArrayList<Category>();
        categoryTest.add(new Category("matte"));
        categoryTest.add(new Category("fysik"));
        when(qm.findCategories()).thenReturn(categoryTest);
        ArrayList<Category> retrieveList = databaseServiceImp.findCategories();
        assertEquals(categoryTest.size(), retrieveList.size());
        assertEquals("matte", retrieveList.get(0).getCategory());
        verify(qm, times(1)).findCategories();
    }
    
    @Test
    public void deleteLibrarian(){
        
        Librarian lb = new Librarian();
        databaseServiceImp.deleteLibrarian(lb);
        verify(qm, times(1)).deleteLibrarian(any());
    }
    
    @Test
    public void deleteBook(){
    
        Books book = new Books();
        databaseServiceImp.deleteBook(book,"PEPE");
        verify(qm, times(1)).deleteBook(book,"PEPE");
    
    }
    
    @Test
    public void findEBookByField(){
        
        E_Books e_book = new E_Books();
        e_book.setId(54);
        
        when(qm.findEBookByField(anyString(),anyString())).thenReturn(e_book);
        E_Books eb = databaseServiceImp.findEBookByField(anyString(), anyString());
        assertEquals(eb.getId(), e_book.getId());
        verify(qm, times(1)).findEBookByField(anyString(), anyString());
        
    }
    @Test
    public void findAdmins(){
        ArrayList<Admin> adminTest = new ArrayList<Admin>();
        adminTest.add(new Admin(1));
        adminTest.add(new Admin(2));
        when(qm.findAdmins()).thenReturn(adminTest);
        ArrayList<Admin> retrieveList = databaseServiceImp.findAdmins();
        assertEquals(adminTest.size(), retrieveList.size());
        assertEquals(1, retrieveList.get(0).getId());
        verify(qm, times(1)).findAdmins();
    }
    @Test
    public void getAllBookedSeminars(){
        ArrayList<Booking> bookingTest = new ArrayList<Booking>();
        bookingTest.add(new Booking(1));
        when(qm.getAllBookedSeminars()).thenReturn(bookingTest);
        ArrayList<Booking>retrieveList = databaseServiceImp.getAllBookedSeminars();
        assertEquals(bookingTest.size(), retrieveList.size());
        assertEquals(1, retrieveList.get(0).getId());
        verify(qm, times(1)).getAllBookedSeminars();

    
    }
    
    @Test
    public void testfindDeletedBooks(){
        ArrayList<DeletedBook> value = new ArrayList<DeletedBook>();
        ArrayList<DeletedBook> actual = databaseServiceImp.findDeletedBooks();
        
        value.add(new DeletedBook(5, "The talented Mr. Ripley", "Patricia Highsmith", "Book",
                "123414123123",400, 
              "Kriminalroman", "Coward-McCann (United States)", 
           "", "försavnn"));
        when(databaseServiceMock.findDeletedBooks()).thenReturn(databaseServiceImp.findDeletedBooks());
        
        assertEquals(value.get(0).getIsbn(), actual.get(1).getIsbn());
        
    }
    
    @Test
    public void testLoginChecker(){
        String value = "johan.viktor@libsys.se";
        String actual = databaseServiceImp.loginChecker("admins", "johan.viktor@libsys.se", "1234");

        assertEquals(value, actual);
    }
    
    @Test
    public void testGetAllBooks(){
        List<Books> value = new ArrayList<>();
        List<Books> actual = databaseServiceImp.getAllBooks();
        
        value.add(new Books(5, "The talented Mr. Ripley", "Patricia Highsmith",
               "123414123123", "Coward-McCann (United States)", 400,"Kriminalroman", "GF4",
                "The Talented Mr. Ripley (1999) In late 1950s"
                + "New York, Tom Ripley, a young underachiever, is sent to Italy to retrieve Dickie "
                + "  Greenleaf, a rich and spoiled millionaire playboy " 
                + " But when the errand fails, Ripley takes extreme measures"));
        when(databaseServiceMock.getAllBooks()).thenReturn(databaseServiceImp.getAllBooks());
        
        assertEquals(value.get(0).getIsbn(), actual.get(3).getIsbn());
    }
    
    @Test
    public void testFindBooksByIsbn(){
        List<Books> value = new ArrayList<>();
        List<Books> actual = databaseServiceImp.findBooksByIsbn("123414123123");
        
        value.add(new Books(5, "The talented Mr. Ripley", "Patricia Highsmith",
               "123414123123", "Coward-McCann (United States)", 400,"Kriminalroman", "GF4",
                "The Talented Mr. Ripley (1999) In late 1950s"
                + "New York, Tom Ripley, a young underachiever, is sent to Italy to retrieve Dickie "
                + "  Greenleaf, a rich and spoiled millionaire playboy " 
                + " But when the errand fails, Ripley takes extreme measures"));
           
        assertEquals(value.get(0).getAuthor(), actual.get(0).getAuthor());
    }
    
    @Test
    public void testGetBorrowedBooksByCardId(){
        List<Books> value = new ArrayList<>();
        List<Books> actual = databaseServiceImp.getBorrowedBooksByCardId(2);
        
         value.add(new Books(3, "Le Malade Imaginaire", "Moliere",
               "9788853607881", "Raben & Sjögren", 300,"Klassiker", "HD3",
                "Le Malade imaginaire est la dernière \n" +
                " comédie écrite par Molière. Argan, le « malade imaginaire », est veuf, \n" +
                " il s'est remarié avec Béline qui simule des soins attentifs, mais\n" +
                "  n'attend en réalité que la mort de son mari pour pouvoir hérite"));
           
        assertEquals(value.get(0).getAuthor(), actual.get(0).getAuthor());
    }
    
}
