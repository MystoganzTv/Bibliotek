
package se.database;

import java.sql.*;


public class Connection {

    
    public static  Connection getConnection(){
        
        Connection myDbConn = null;
        
    
        try{
        // get connection to database
        String url ="jdbc:mysql://libsys.mysql.database.azure.com:3306/lib_db?useSSL=true&requireSSL=false"; 
        myDbConn = (Connection) DriverManager.getConnection(url, "java@libsys", "Projekt!");
        if(myDbConn != null)
        {
            System.out.println("connected to database");
        }
         return myDbConn;
        
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            
            return null;
        }
       
       
    }

}
