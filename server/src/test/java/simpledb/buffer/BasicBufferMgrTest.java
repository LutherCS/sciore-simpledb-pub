package simpledb.buffer;

import java.util.Arrays;
import java.util.ArrayList;
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

    private BufferMgr instance4;
    private BufferMgr instance5;

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
        instance4 = SimpleDB.bufferMgr();
        instance4 = new BufferMgr(4);
        Buffer buff10, buff20, buff30, buff40, buff50;

        buff10 = instance4.pin(new Block("tempbuffer", 10));
        buff20 = instance4.pin(new Block("tempbuffer", 20));
        buff30 = instance4.pin(new Block("tempbuffer", 30));
        buff40 = instance4.pin(new Block("tempbuffer", 40));
        instance4.unpin(buff20);
        buff50 = instance4.pin(new Block("tempbuffer", 50));
        instance4.unpin(buff40);
        instance4.unpin(buff10);
        instance4.unpin(buff30);
        instance4.unpin(buff50);

//        buff10 = buff20 = buff30 = buff40 = buff50 = null;
        instance5 = SimpleDB.bufferMgr();
        instance5 = new BufferMgr(5);
        buff10 = instance5.pin(new Block("tempbuffer", 10));
        buff20 = instance5.pin(new Block("tempbuffer", 20));
        buff30 = instance5.pin(new Block("tempbuffer", 30));
        buff40 = instance5.pin(new Block("tempbuffer", 40));
        instance5.unpin(buff20);
        buff50 = instance5.pin(new Block("tempbuffer", 50));
        instance5.unpin(buff40);
        instance5.unpin(buff10);
        instance5.unpin(buff30);
        instance5.unpin(buff50);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of Naive Buffer selection strategy, of class BasicBufferMgr.
     */
    @Test
    public void testNaiveStrategy4() {
        System.out.println("Naive Strategy (4 buffers)");
        Integer[] expected = new Integer[] {60, 70, 30, 40};
        ArrayList<Integer> result = new ArrayList();
        
        instance4.setStrategy(0);

        instance4.pin(new Block("tempbuffer", 60));
        instance4.pin(new Block("tempbuffer", 70));

        for (Buffer b: instance4.getBuffers()) {
            result.add(b.block().number());
        }
        
        assertTrue(Arrays.equals(expected, result.toArray(new Integer[result.size()])));
     }

    /**
     * Test of Naive Buffer selection strategy, of class BasicBufferMgr.
     */
    @Test
    public void testNaiveStrategy5() {
        System.out.println("Naive Strategy (5 buffers)");
        Integer[] expected = new Integer[] {60, 70, 30, 40, 50};
        ArrayList<Integer> result = new ArrayList();
        
        instance5.setStrategy(0);

        instance5.pin(new Block("tempbuffer", 60));
        instance5.pin(new Block("tempbuffer", 70));

        for (Buffer b: instance5.getBuffers()) {
            result.add(b.block().number());
        }
        
        assertTrue(Arrays.equals(expected, result.toArray(new Integer[result.size()])));
     }

    /**
     * Test of FIFO Buffer selection strategy, of class BasicBufferMgr.
     */
    @Test
    public void testFIFOStrategy4() {
        System.out.println("FIFO Strategy (4 buffers)");
        Integer[] expected = new Integer[] {60, 50, 70, 40};
        ArrayList<Integer> result = new ArrayList();
        
        instance4.setStrategy(1);

        instance4.pin(new Block("tempbuffer", 60));
        instance4.pin(new Block("tempbuffer", 70));

        for (Buffer b: instance4.getBuffers()) {
            result.add(b.block().number());
        }
        
        assertTrue(Arrays.equals(expected, result.toArray(new Integer[result.size()])));
    }

    /**
     * Test of FIFO Buffer selection strategy, of class BasicBufferMgr.
     */
    @Test
    public void testFIFOStrategy5() {
        System.out.println("FIFO Strategy (5 buffers)");
        Integer[] expected = new Integer[] {60, 70, 30, 40, 50};
        ArrayList<Integer> result = new ArrayList();
        
        instance5.setStrategy(1);

        instance5.pin(new Block("tempbuffer", 60));
        instance5.pin(new Block("tempbuffer", 70));

        for (Buffer b: instance5.getBuffers()) {
            result.add(b.block().number());
        }
        
        assertTrue(Arrays.equals(expected, result.toArray(new Integer[result.size()])));
    }

    /**
     * Test of LRU Buffer selection strategy, of class BasicBufferMgr.
     */
    @Test
    public void testLRUStrategy4() {
        System.out.println("LRU Strategy (4 buffers)");
        Integer[] expected = new Integer[] {70, 50, 30, 60};
        ArrayList<Integer> result = new ArrayList();
        
        instance4.setStrategy(2);

        instance4.pin(new Block("tempbuffer", 60));
        instance4.pin(new Block("tempbuffer", 70));

        for (Buffer b: instance4.getBuffers()) {
            result.add(b.block().number());
        }
        
        assertTrue(Arrays.equals(expected, result.toArray(new Integer[result.size()])));
    }

    /**
     * Test of LRU Buffer selection strategy, of class BasicBufferMgr.
     */
    @Test
    public void testLRUStrategy5() {
        System.out.println("LRU Strategy (5 buffers)");
        Integer[] expected = new Integer[] {10, 60, 30, 70, 50};
        ArrayList<Integer> result = new ArrayList();
        
        instance5.setStrategy(2);

        instance5.pin(new Block("tempbuffer", 60));
        instance5.pin(new Block("tempbuffer", 70));

        for (Buffer b: instance5.getBuffers()) {
            result.add(b.block().number());
        }
        
        assertTrue(Arrays.equals(expected, result.toArray(new Integer[result.size()])));
    }

    /**
     * Test of Clock Buffer selection strategy, of class BasicBufferMgr.
     */
    @Test
    public void testClockStrategy4() {
        System.out.println("Clock Strategy (4 buffers)");
        Integer[] expected = new Integer[] {10, 50, 60, 70};
        ArrayList<Integer> result = new ArrayList();
        
        instance4.setStrategy(3);

        instance4.pin(new Block("tempbuffer", 60));
        instance4.pin(new Block("tempbuffer", 70));

        for (Buffer b: instance4.getBuffers()) {
            result.add(b.block().number());
        }
        
        assertTrue(Arrays.equals(expected, result.toArray(new Integer[result.size()])));
    }

    /**
     * Test of Clock Buffer selection strategy, of class BasicBufferMgr.
     */
    @Test
    public void testClockStrategy5() {
        System.out.println("Clock Strategy (5 buffers)");
        Integer[] expected = new Integer[] {60, 70, 30, 40, 50};
        ArrayList<Integer> result = new ArrayList();
        
        instance5.setStrategy(3);

        instance5.pin(new Block("tempbuffer", 60));
        instance5.pin(new Block("tempbuffer", 70));

        for (Buffer b: instance5.getBuffers()) {
            result.add(b.block().number());
        }
        
        assertTrue(Arrays.equals(expected, result.toArray(new Integer[result.size()])));
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
