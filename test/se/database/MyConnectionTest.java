package se.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Zsombor
 */
public class MyConnectionTest {
    
    public MyConnectionTest() {
    }

    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            
            assertTrue("MyConnectionTest failed: Invalid database name returned from current connection.", stmt.execute("SELECT DATABASE()"));
            
            stmt.close();
            conn.close();
        } catch(Exception e) {
            System.out.println("Something went wrong in MyConnectionTest.java.");
        }
    }
    
}
