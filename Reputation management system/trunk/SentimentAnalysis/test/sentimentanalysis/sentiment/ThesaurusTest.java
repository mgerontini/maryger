/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.sentiment;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import sentimentanalysis.search.Query;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthijs
 */
public class ThesaurusTest {
    
    private static Query   query;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        query = new Query("Apple");        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getRelated method, of class Thesaurus.
     */
    @Test
    public void testGetRelated() {
        System.out.println("getRelated");
        Thesaurus instance = new Thesaurus(query);
        HashMap expResult = null;
        HashMap result = instance.getRelated();
        
        try {
            FileInputStream    fis  = new FileInputStream("test/output/ThesaurusTest.getRelated.result");
            ObjectInputStream  in   = new ObjectInputStream(fis);
            expResult               = (HashMap) in.readObject();
            in.close();
        } catch (Exception e) {
            fail("Could not read getRelated expected result.");
        }
        assert(result != null);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult, result);
    }
}
