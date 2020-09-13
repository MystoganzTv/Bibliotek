/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.List;
import se.model.Books;

/**
 *
 * @author danny
 */
public interface DatabaseService {
    
    boolean isPersonNumberTaken(String personalNumber);
    List<Books> getAllBooks();
    
}
