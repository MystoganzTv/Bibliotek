/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.main;

import java.util.ArrayList;
import java.util.List;
import se.database.QueryMethods;
import se.model.Books;
import se.model.E_Books;
import se.view.StartPage;

/**
 *
 * @author tadev
 */
public class Main {
    public static void main(String[] args) {
        
        
        QueryMethods qm = new QueryMethods();
        
       ArrayList<E_Books> eBooks;
       
       eBooks = qm.findEBooksByAuthor("adde");
        System.out.println(eBooks.get(0).getAuthor());
        
        eBooks = qm.findEBooksByCategory("historia");
        System.out.println(eBooks.get(0).getAuthor());
        
        eBooks = qm.findEBooksByTitle("hej");
        System.out.println(eBooks.get(0).getAuthor());
        
        StartPage startPage = new StartPage();
        startPage.setVisible(true);
        
       
    }
    
}
