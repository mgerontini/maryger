/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis.sentiment;

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
public class LexiconTest {
    
    private Lexicon instance;
    
    public LexiconTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
     * Test of Lexicon method, of class Lexicon.
     */
    @Test
    public void testLexicon() throws Exception {
        System.out.println("Lexicon");
        instance = new Lexicon();
    }

    
    /**
     * Test of get method, of class Lexicon.
     */
    @Test
    public void testGet() throws Exception {
        System.out.println("get");
        String word         = "whimper";
        Lexicon instance    = new Lexicon();
        Double result_neg   = -1.0;
        Double result_neu   = 0.0;
        Double result_pos   = 1.0;
        
        Double result       = instance.get(word);
        assertEquals(result_neg, result);
        
        result              = instance.get("largeley");
        assertEquals(result_neu, result);
        
        result              = instance.get("abide"); // The dude abides.
        assertEquals(result_pos, result);
    }
}
