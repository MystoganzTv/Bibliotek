/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.ArrayList;
import java.util.List;
import se.model.Books;
import se.model.DeletedBook;

/**
 *
 * @author danny
 */
public interface DatabaseService {
    
    boolean isPersonNumberTaken(String personalNumber);
    ArrayList<String> getAllAdminPersonIds();
    List<Books> getAllBooks();
    ArrayList<String>getAllLibrariansIds();
    ArrayList<String>getAllGuestsIds();
    boolean isEmailTaken(String email);
    ArrayList<DeletedBook>findDeletedBooks();

}
