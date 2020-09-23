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
import se.model.Books;
import se.model.DeletedBook;
import se.model.E_Books;
import se.model.Guest;
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
    @Test
    public void getAllCards(){
        ArrayList<LibraryCards> libraryCards = new ArrayList<LibraryCards>();
        libraryCards.add(new LibraryCards(1,1,"test","test",1));
        libraryCards.add(new LibraryCards(2,1,"test","test",0));
        when(qm.getAllCards()).thenReturn(libraryCards);
        ArrayList<LibraryCards> retrieveList = databaseServiceImp.getAllCards();
        assertEquals(libraryCards.size(), retrieveList.size());
        assertEquals(1, retrieveList.get(0).getId());
         verify(qm, times(1)).getAllCards();
        
        
    }
    @Test
    public void getGuestsLibraryCardsByGuestList(){
        ArrayList<Guest> guests = new ArrayList<Guest>();
        ArrayList<LibraryCards> librarycards = new ArrayList<LibraryCards>();
        guests.add(new Guest(1,"erik","ringbolm","1998050555","test","test"));
        librarycards.add(new LibraryCards(1,1,"test","test",1));
        when(qm.getGuestsLibraryCardsByGuestList(guests)).thenReturn(librarycards);
        assertEquals(guests.get(0).getId(), librarycards.get(0).getGuestId());
      
    }
    
}
