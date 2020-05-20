/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.main;

/**
 *
 * @author adde
 */
public class Validation {
    
    
    public static boolean isValidName(String name){
        
        if(name.isEmpty()){
            return false;
        }
        
      return name.matches("^[a-zA-Z]+$");
        
    }
    
    public static boolean isValidID(String ID){
        
        if(ID.isEmpty()){
            return false;
        }
        
        return ID.matches("^[1-9]+$");
    }
    
    public static boolean isValidPN(String PN){
        
        if(PN.isEmpty()){
            
            return false;
        }
        //YYYYMMDDXXXX
        return PN.matches("^[1][9][0-9][0-9][0-1][0-9][0-3][0-9][0-9][0-9][0-9][0-9]|^[2][0][0-1][0-9][0-1][0-9][0-3][0-9][0-9][0-9][0-9][0-9]$");
    }
    
    public static boolean isValidPassword(String password){
        
        if(password.isEmpty()){
            
            return false;
        }
        if(password.length() < 3){
            
            return false;
        }
        return true;
       
    }
    
    public static boolean isValidEmail(String email){
        
        if(email.isEmpty()){
            return false;
        }
        int breakpoint = email.indexOf('@');
        String firstPartOfEmail = email.substring(0,breakpoint);
        
        if(!firstPartOfEmail.matches("^[a-zA-Z0-9]+$")){
            
            return false;
        }
        if(!email.substring(breakpoint).equals("@libsys.se")){
            
            return false;
    
        }
        return true;
    }
    
}
