package simpledb.query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;
import static java.sql.Types.*;

/**
 *
 * @author hangde01
 */
public class ExtendScanTest {
    
    public ExtendScanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        SimpleDB.init("studentdb");
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

    @Test
    public void testExtendScan() {
        System.out.println("EXTEND"); 
        Transaction tx = new Transaction();
        Plan studentTblPlan = new TablePlan("student", tx);
        tx.commit(); 
        
        Plan extendPlan = new ExtendPlan(studentTblPlan, "gradclass", INTEGER, 4);
        Scan extendScan = extendPlan.open(); 
        assertEquals(true,extendScan.hasField("gradclass")); 

        Plan extendPlan2 = new ExtendPlan(studentTblPlan, "initial", VARCHAR, 1);
        Scan extendScan2 = extendPlan2.open(); 
        assertEquals(true,extendScan2.hasField("initial")); 
    }
    
} 