/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.List;
import se.database.QueryMethods;
import se.model.Books;

/**
 *
 * @author danny
 */
public class JDBCDatabaseServiceImp implements DatabaseService{

    QueryMethods queryMethods = new QueryMethods();
    @Override
    public boolean isPersonNumberTaken(String personalNumber) {
      return  queryMethods.isPersonNumberTaken(personalNumber);     
    }

    @Override
    public List<Books> getAllBooks() {
        return queryMethods.getAllBooks();
    }
    
}
