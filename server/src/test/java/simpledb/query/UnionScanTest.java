
package simpledb.query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import simpledb.metadata.StatInfo;
import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

/**
 *
 * @author charlyman
 */
public class UnionScanTest {
    
  public UnionScanTest(){
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
  public void testUnionScan() {
    System.out.println("UNION");
    Transaction tx = new Transaction();
    Plan studentTblPlan = new TablePlan("student", tx);
    Plan studentTblPlan2 = new TablePlan("student", tx);
    tx.commit();
    Plan UnionPlan = new UnionPlan(studentTblPlan, studentTblPlan2);
    Scan unionScan = UnionPlan.open();
    int records = 0;
    while (unionScan.next()) {
      for (String field: UnionPlan.schema().fields()) {
        System.out.printf("%10s", unionScan.getVal(field).toString());
      }
      System.out.println();
      records++;
    }
    assertEquals(9, records);
}
    
}
