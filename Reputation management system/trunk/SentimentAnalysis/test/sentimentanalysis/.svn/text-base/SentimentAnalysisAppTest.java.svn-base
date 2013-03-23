/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimentanalysis;

import sentimentanalysis.view.SentimentAnalysisView;
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
public class SentimentAnalysisAppTest {
    
    public SentimentAnalysisAppTest() {
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
     * Test of startup method, of class SentimentAnalysisApp.
     */
    @Test
    public void testStartup() {
        System.out.println("startup");
        try {
            SentimentAnalysisApp instance = new SentimentAnalysisApp();
            instance.startup();
        } catch (Exception e) {
            fail("Application threw an exception.");
        }
    }

    
    /**
     * Test of getView method, of class SentimentAnalysisApp.
     */
    @Test
    public void testGetView() {
        System.out.println("getView");
        SentimentAnalysisView result = SentimentAnalysisApp.getView();
        assert(result != null);
    }
}
