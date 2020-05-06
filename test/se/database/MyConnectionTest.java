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
        
        try {
            Connection conn = MyConnection.getConnection();
            Statement stmt = conn.createStatement();
            
            assertTrue("MyConnectionTest failed.", stmt.execute("SELECT 1 FROM admins"));
            
            stmt.close();
            conn.close();
        } catch(Exception e) {
            fail("MyConnectionTest failed.");
        }
    }
    
}
