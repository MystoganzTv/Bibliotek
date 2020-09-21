/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.database;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
     * Test of bookSeminar method, of class QueryMethods.
     */
    @Test
    public void testBookSeminar() {
        System.out.println("bookSeminar");
        LibraryCards g = new LibraryCards();
        g.setId(1);
        Seminar s = new Seminar();
        s.setId(2);
        QueryMethods instance = new QueryMethods();
        instance.bookSeminar(g, s);
    }


    
}
