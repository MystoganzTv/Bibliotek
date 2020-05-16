
package se.database;

import java.sql.*;


public class MyConnection {

    
    public static  Connection getConnection(){
        
        Connection myDbConn = null;
        
    
        try{

      //    String url = "jdbc:mysql://libsys.mysql.database.azure.com:3306/libsys?useSSL=true&requireSSL=false";
      //    myDbConn = DriverManager.getConnection(url, "java@libsys", "Projekt!");
      
            String url ="jdbc:mysql://localhost:3306/library?useSSL=true&requireSSL=false";
            myDbConn = DriverManager.getConnection(url, "root", "root");
            return myDbConn;
        
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            
            return null;
        }
       
       
    }

}
