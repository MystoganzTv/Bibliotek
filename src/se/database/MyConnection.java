
package se.database;

import java.sql.*;


public class MyConnection {

    
    public static  Connection getConnection(){
        
        Connection myDbConn = null;
        
    
        try{
        // get connection to database

//
//        String url ="jdbc:mysql://libsys.mysql.database.azure.com:3306/libsys?useSSL=true&requireSSL=false"; 
//        myDbConn = DriverManager.getConnection(url, "java@libsys", "Projekt!");
////        
//        connection tilll lokalt databas


       // String url ="jdbc:mysql://libsys.mysql.database.azure.com:3306/libsys?useSSL=true&requireSSL=false"; 
       // myDbConn = DriverManager.getConnection(url, "java@libsys", "Projekt!");
        
        //connection tilll lokalt databas

        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost/libsystest?autoReconnect=true&useSSL=false";
        myDbConn = DriverManager.getConnection(url, "root", "root");
         return myDbConn;
        
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            
            return null;
        }
       
       
    }

}
