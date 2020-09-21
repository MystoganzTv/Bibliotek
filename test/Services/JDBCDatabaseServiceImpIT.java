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
import se.model.Books;
import se.model.DeletedBook;

/**
 *
 * @author danny
 */
public class JDBCDatabaseServiceImpIT {
    
    DatabaseService databaseServiceMock = Mockito.mock(DatabaseService.class);
    JDBCDatabaseServiceImp databaseServiceImp = new JDBCDatabaseServiceImp(databaseServiceMock);
    
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
        
        when(databaseServiceMock.loginChecker(value, actual, actual)).
                thenReturn(databaseServiceImp.loginChecker(value, actual, actual));
        assertEquals(value, actual);
    }
    
    @Test
    public void testGetAllBooks(){
        List<Books> value = new ArrayList<>();
        List<Books> actual = databaseServiceImp.getAllBooks();
        
        value.add(new Books(5, "The talented Mr. Ripley", "Patricia Highsmith",
               "123414123123", "Coward-McCann (United States)", 400,"Kriminalroman", "GF4", "The Talented Mr. Ripley (1999) In late 1950s"
                + "New York, Tom Ripley, a young underachiever, is sent to Italy to retrieve Dickie "
                + "  Greenleaf, a rich and spoiled millionaire playboy " 
                + " But when the errand fails, Ripley takes extreme measures"));
        when(databaseServiceMock.getAllBooks()).thenReturn(databaseServiceImp.getAllBooks());
        
        assertEquals(value.get(0).getIsbn(), actual.get(3).getIsbn());
    }
}
