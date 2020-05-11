/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.model.Books;
import se.model.E_Books;

/**
 *
 * @author adde
 */
public class ViewBooksTest {
    
    List<Books> books = new ArrayList<>();
    List<E_Books> eBooks = new ArrayList<>();
    ViewBooks instance = new ViewBooks();
    
    public ViewBooksTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        

        books.add(new Books(3, "Be polite", "Andreas", "isbn", "A publisher", 0.0, "Music", "M1"));
        books.add(new Books(1, "Hello World", "J.R.R Tolkien", "isbn", "B publisher", 0.0, "History", "H2"));
        books.add(new Books(2, "A is first", "Zlatan", "isbn", "Z publisher", 0.0, "Sports", "S3"));

        
        eBooks.add(new E_Books(3, "Be polite", "Andreas", "isbn", "A publisher", 0.0, "Music"));
        eBooks.add(new E_Books(1, "Hello World", "J.R.R Tolkien", "isbn", "B publisher", 0.0, "History"));
        eBooks.add(new E_Books(2, "A is first", "Zlatan", "isbn", "Z publisher", 0.0, "Sports"));
        
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sortBooksByTitle method, of class ViewBooks.
     */
    @Test
    public void testSortBooksByTitle() {
        System.out.println("sortBooksByTitle");
        
        instance.sortBooksByTitle(books);
        
        assertEquals("A is first", books.get(0).getTitle());
        
    }
    
    @Test
    public void testSortBooksByTitleReverseOrder(){
        
        instance.sortBooksByTitleReverse(books);
        
        assertEquals("Hello World", books.get(0).getTitle());
    }

    /**
     * Test of sortBooksByAuthor method, of class ViewBooks.
     */
    @Test
    public void testSortBooksByAuthor() {
        
        instance.sortBooksByAuthor(books);
       
        assertEquals("Andreas", books.get(0).getAuthor());
    }

    /**
     * Test of sortBooksByAuthorReverse method, of class ViewBooks.
     */
    @Test
    public void testSortBooksByAuthorReverse() {
        
        instance.sortBooksByAuthorReverse(books);
    
        assertEquals("Zlatan", books.get(0).getAuthor());
    }
    
    @Test
    public void testSortBooksByCategory(){
        
        instance.sortBooksByCategory(books);
        
        assertEquals("History", books.get(0).getCategory());
    }
    
    @Test
    public void testSortBooksByCategoryReverse(){
        
        instance.sortBooksByCategoryReverse(books);
        
        assertEquals("Sports", books.get(0).getCategory());
    }
    
    @Test
    public void testSortBooksByPublisher(){
        
        instance.sortBooksByPublisher(books);
        
        assertEquals("A publisher", books.get(0).getPublisher());
    }
    
    @Test
    public void testSortBooksByPublisherReverse(){
        
        instance.sortBooksByPublisherReverse(books);
        
        assertEquals("Z publisher", books.get(0).getPublisher());
    }

   
   
    /**
     * Test of sortEBooksByTitle method, of class ViewBooks.
     */
    @Test
    public void testSortEBooksByTitle() {
        
        instance.sortEBooksByTitle(eBooks);
        
        assertEquals("A is first", eBooks.get(0).getTitle());
    }

    /**
     * Test of sortEBooksByTitleReverse method, of class ViewBooks.
     */
    @Test
    public void testSortEBooksByTitleReverse() {
       
        instance.sortEBooksByTitleReverse(eBooks);
        
        assertEquals("Hello World", eBooks.get(0).getTitle());
    }

    /**
     * Test of sortEBooksByAuthor method, of class ViewBooks.
     */
    @Test
    public void testSortEBooksByAuthor() {
        
        instance.sortEBooksByAuthor(eBooks);
        
        assertEquals("Andreas", eBooks.get(0).getAuthor());
    }

    /**
     * Test of sortEBooksByAuthorReverse method, of class ViewBooks.
     */
    @Test
    public void testSortEBooksByAuthorReverse() {
        
        instance.sortEBooksByAuthorReverse(eBooks);
        
        assertEquals("Zlatan", eBooks.get(0).getAuthor());
    }

    /**
     * Test of sortEBooksByCategory method, of class ViewBooks.
     */
    @Test
    public void testSortEBooksByCategory() {
        
        instance.sortEBooksByCategory(eBooks);
        
        assertEquals("History", eBooks.get(0).getCategory());
    }

    /**
     * Test of sortEBooksByCategoryReverse method, of class ViewBooks.
     */
    @Test
    public void testSortEBooksByCategoryReverse() {
       
        instance.sortEBooksByCategoryReverse(eBooks);
        
        assertEquals("Sports", eBooks.get(0).getCategory());
    }

    /**
     * Test of sortEBooksByPublisher method, of class ViewBooks.
     */
    @Test
    public void testSortEBooksByPublisher() {
        
        instance.sortEBooksByPublisher(eBooks);
        
        assertEquals("A publisher", eBooks.get(0).getPublisher());
    }

    /**
     * Test of sortEBooksByPublisherReverse method, of class ViewBooks.
     */
    @Test
    public void testSortEBooksByPublisherReverse() {
       
        instance.sortEBooksByPublisherReverse(eBooks);
        
        assertEquals("Z publisher", eBooks.get(0).getPublisher());
    }

    /**
     * Test of main method, of class ViewBooks.
     */
   

}
