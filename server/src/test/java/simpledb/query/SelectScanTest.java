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
 * @author yasiro01
 */
public class SelectScanTest {
  
  public SelectScanTest() {
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
  public void testDBCreation() {
    System.out.println("CREATE");
    Transaction tx = new Transaction();
    Schema sch = SimpleDB.mdMgr().getTableInfo("student", tx).schema();
    tx.commit();
    assertEquals(4, sch.fields().size());
  }
  
  @Test
  public void testStudentData() {
    System.out.println("INSERT");
    Transaction tx = new Transaction();
    TableInfo tblInfo = SimpleDB.mdMgr().getTableInfo("student", tx);
    StatInfo statInfo = SimpleDB.mdMgr().getStatInfo("student", tblInfo, tx);
    tx.commit();
    assertEquals(9, statInfo.recordsOutput());
  }
  
  @Test
  public void testSelectAllScan() {
    System.out.println("SELECT");
    Transaction tx = new Transaction();
    String qry = "select sname from student";
    Plan p = SimpleDB.planner().createQueryPlan(qry, tx);
    Scan s = p.open();
    tx.commit();
    assertEquals(9, p.recordsOutput());
  }
  
  @Test
  public void testSelectByConstantScan() {
    System.out.println("SELECT");
    Transaction tx = new Transaction();
    String qry = "select sname from student where majorid = 10";
    Plan p = SimpleDB.planner().createQueryPlan(qry, tx);
    Scan s = p.open();
    tx.commit();
    assertEquals(2, p.recordsOutput());
  }
  
}
