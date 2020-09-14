/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    
}
