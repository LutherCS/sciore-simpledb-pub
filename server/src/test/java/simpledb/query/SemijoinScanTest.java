package simpledb.query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

/**
 *
 * @author yasiro01
 */
public class SemijoinScanTest {
  
  public SemijoinScanTest() {
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
  public void testJoinScan() {
    System.out.println("SEMIJOIN");
    Transaction tx = new Transaction();
    Plan studentTblPlan = new TablePlan("student", tx);
    Plan deptTblPlan = new TablePlan("dept", tx);
    tx.commit();
    Plan semijoinPlan = new SemijoinPlan(studentTblPlan, deptTblPlan,
            new Predicate(
                    new Term(
                      new FieldNameExpression("majorid"),
                      new FieldNameExpression("did"))));
    Scan semijoinScan = semijoinPlan.open();
    int records = 0;
    while (semijoinScan.next()) {
      for (String field: semijoinPlan.schema().fields()) {
        System.out.printf("%10s", semijoinScan.getVal(field).toString());
      }
      System.out.println();
      records++;
    }
    assertEquals(9, records);
    assertFalse(semijoinScan.hasField("did"));
    assertFalse(semijoinScan.hasField("dname"));
  }
}