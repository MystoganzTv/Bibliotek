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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author danny
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({se.view.AddBookIT.class, se.view.StartPageIT.class, se.view.LibrarianViewIT.class, se.view.NewUserViewIT.class, se.view.AdminHomePageIT.class, se.view.AdminUpdateIT.class})
public class ViewITSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
