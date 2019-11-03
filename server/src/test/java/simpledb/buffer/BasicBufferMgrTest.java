package simpledb.buffer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import simpledb.file.Block;
import simpledb.server.SimpleDB;

/**
 * @author yasiro01
 */
public class BasicBufferMgrTest {
  private BufferMgr instance;
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  public BasicBufferMgrTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
    SimpleDB.init("testdb");
    instance = SimpleDB.bufferMgr();
    instance = new BufferMgr(4);
    Buffer buff10 = instance.pin(new Block("tempbuffer", 10));
    Buffer buff20 = instance.pin(new Block("tempbuffer", 20));
    Buffer buff30 = instance.pin(new Block("tempbuffer", 30));
    Buffer buff40 = instance.pin(new Block("tempbuffer", 40));
    instance.unpin(buff20);
    Buffer buff50 = instance.pin(new Block("tempbuffer", 50));
    instance.unpin(buff40);
    instance.unpin(buff10);
    instance.unpin(buff30);
    instance.unpin(buff50);
  }
  
  @After
  public void tearDown() {
  }
  
  /**
   * Test of Naive Buffer selection strategy, of class BasicBufferMgr.
   */
  @Test
  public void testNaiveStrategy() {
    int expectedResult, result;
    System.out.println("Naive Strategy");
    instance.setStrategy(0);
    instance.pin(new Block("tempbuffer", 60));
    instance.pin(new Block("tempbuffer", 70));

    expectedResult = 60;
    result = instance.getBuffers()[0].block().number();
    assertEquals(expectedResult, result);
    expectedResult = 70;
    result = instance.getBuffers()[1].block().number();
    assertEquals(expectedResult, result);
  }
  /**
   * Test of FIFO Buffer selection strategy, of class BasicBufferMgr.
   */
  @Test
  public void testFIFOStrategy() {
    int expectedResult, result;
    System.out.println("FIFO Strategy");
    instance.setStrategy(1);
    instance.pin(new Block("tempbuffer", 60));
    instance.pin(new Block("tempbuffer", 70));

    expectedResult = 60;
    result = instance.getBuffers()[0].block().number();
    assertEquals(expectedResult, result);
    expectedResult = 70;
    result = instance.getBuffers()[2].block().number();
    assertEquals(expectedResult, result);
  }
  /**
   * Test of LRU Buffer selection strategy, of class BasicBufferMgr.
   */
  @Test
  public void testLRUStrategy() {
    int expectedResult, result;
    System.out.println("LRU Strategy");
    instance.setStrategy(2);
    instance.pin(new Block("tempbuffer", 60));
    instance.pin(new Block("tempbuffer", 70));

    expectedResult = 60;
    result = instance.getBuffers()[3].block().number();
    assertEquals(expectedResult, result);
    expectedResult = 70;
    result = instance.getBuffers()[0].block().number();
    assertEquals(expectedResult, result);
  }
  /**
   * Test of Clock Buffer selection strategy, of class BasicBufferMgr.
   */
  @Test
  public void testClockStrategy() {
    int expectedResult, result;
    System.out.println("Clock Strategy");
    instance.setStrategy(3);
    instance.pin(new Block("tempbuffer", 60));
    instance.pin(new Block("tempbuffer", 70));

    expectedResult = 60;
    result = instance.getBuffers()[2].block().number();
    assertEquals(expectedResult, result);
    expectedResult = 70;
    result = instance.getBuffers()[3].block().number();
    assertEquals(expectedResult, result);
  }
  /**
   * Test of flushAll method, of class BasicBufferMgr.
   */
  @Test
  @Ignore
  public void testFlushAll() {
    System.out.println("flushAll");
    int txnum = 0;
    BasicBufferMgr instance = null;
    instance.flushAll(txnum);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of pin method, of class BasicBufferMgr.
   */
  @Test
  @Ignore
  public void testPin() {
    System.out.println("pin");
    Block blk = null;
    BasicBufferMgr instance = null;
    Buffer expResult = null;
    Buffer result = instance.pin(blk);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of pinNew method, of class BasicBufferMgr.
   */
  @Test
  @Ignore
  public void testPinNew() {
    System.out.println("pinNew");
    String filename = "";
    PageFormatter fmtr = null;
    BasicBufferMgr instance = null;
    Buffer expResult = null;
    Buffer result = instance.pinNew(filename, fmtr);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of unpin method, of class BasicBufferMgr.
   */
  @Test
  @Ignore
  public void testUnpin() {
    System.out.println("unpin");
    Buffer buff = null;
    BasicBufferMgr instance = null;
    instance.unpin(buff);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of available method, of class BasicBufferMgr.
   */
  @Test
  @Ignore
  public void testAvailable() {
    System.out.println("available");
    BasicBufferMgr instance = null;
    int expResult = 0;
    int result = instance.available();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
   
}
