/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.main;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danny
 */
public class ValidationTest {
    
    public ValidationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isValidName method, of class Validation.
     */
    @Test
    public void testIsValidName() {
        System.out.println("isValidName");
        String name = "Danny";
        boolean expResult = true;
        boolean result = Validation.isValidName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidID method, of class Validation.
     */
    @Test
    public void testIsValidID() {
        System.out.println("isValidID");
        String ID = "123";
        boolean expResult = true;
        boolean result = Validation.isValidID(ID);
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidPN method, of class Validation.
     */
    @Test
    public void testIsValidPN() {
        System.out.println("isValidPN");
        String PN = "198302094554";
        boolean expResult = true;
        boolean result = Validation.isValidPN(PN);
        assertEquals(expResult, result);

    }

    /**
     * Test of isValidPassword method, of class Validation.
     */
    @Test
    public void testIsValidPassword() {
        System.out.println("isValidPassword");
        String password = "asd.34";
        boolean expResult = true;
        boolean result = Validation.isValidPassword(password);
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidEmail method, of class Validation.
     */
    @Test
    public void testIsValidEmail() {
        System.out.println("isValidEmail");
        String email = "harris@libsys.se";
        boolean expResult = true;
        boolean result = Validation.isValidEmail(email);
        assertEquals(expResult, result);
    }
    
}
