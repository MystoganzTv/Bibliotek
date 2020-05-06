/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.view;

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
public class AdminHomePageIT {
    
    public AdminHomePageIT() {
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
     * Test of fillAdminTable method, of class AdminHomePage.
     */
    @Test
    public void testFillAdminTable() {
        System.out.println("fillAdminTable");
        AdminHomePage instance = new AdminHomePage();
        instance.fillAdminTable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillGuestTable method, of class AdminHomePage.
     */
    @Test
    public void testFillGuestTable() {
        System.out.println("fillGuestTable");
        AdminHomePage instance = new AdminHomePage();
        instance.fillGuestTable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillLibrarianTable method, of class AdminHomePage.
     */
    @Test
    public void testFillLibrarianTable() {
        System.out.println("fillLibrarianTable");
        AdminHomePage instance = new AdminHomePage();
        instance.fillLibrarianTable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class AdminHomePage.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        AdminHomePage.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
