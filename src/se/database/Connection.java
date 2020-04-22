
package se.database;

import java.sql.*;


public class Connection {

    
    public static void main(String[] args){
        
        Connection myDbConn = null;
        
    
        try{
        // get connection to database
        String url ="jdbc:mysql://libsys.mysql.database.azure.com:3306/lib_db?useSSL=true&requireSSL=false"; 
        myDbConn = (Connection) DriverManager.getConnection(url, "java@libsys", "Project!");
        if(myDbConn != null)
        {
            System.out.println("connected to database");
        }
        
        }catch(Exception e)
        {
            System.out.println("connected");
        }
    }

}
