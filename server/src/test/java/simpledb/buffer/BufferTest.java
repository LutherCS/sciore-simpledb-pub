package simpledb.buffer;

import simpledb.server.SimpleDB;
import simpledb.file.Block;
import simpledb.buffer.*;
import static simpledb.file.Page.*;

public class BufferTest {
   private static BufferMgr bm = SimpleDB.bufferMgr();
   private static String filename = "tempbuffer";
   
   public static void test() {
      System.out.println("BEGIN BUFFER PACKAGE TEST");
      testBuffer();
      testBufferMgr();
      testDeadlock();
   }
   
   private static void testBuffer() {
      Block  blk   = new Block(filename, 3);
      Buffer buff  = bm.pin(blk);
      
      // fill the buffer with some values, but don't bother to log them
      int pos = 0;
      while(true) {
         if (pos+INT_SIZE >= BLOCK_SIZE)
            break;
         int val = 1000 + pos;
         buff.setInt(pos, val, 1, -1);
         pos += INT_SIZE;
         String s = "value" + pos;
         int strlen = STR_SIZE(s.length());
         if (pos+strlen >= BLOCK_SIZE)
            break;
         buff.setString(pos, s, 1, -1);
         pos += strlen;
      }
      bm.unpin(buff);
      
      Buffer buff2 = bm.pin(blk);
      while(true) {
         if (pos+INT_SIZE >= BLOCK_SIZE)
            break;
         int val = 1000 + pos;
         if (val != buff.getInt(pos))
            System.out.println("*****BufferTest: bad getInt");
         pos += INT_SIZE;
         String s = "value" + pos;
         int strlen = STR_SIZE(s.length());
         if (pos+strlen >= BLOCK_SIZE)
            break;
         if (!s.equals(buff.getString(pos)))
            System.out.println("*****BufferTest: bad getString");
         pos += strlen;
      }
      bm.unpin(buff);
   }
   
   private static void testBufferMgr() {
      int avail1 = bm.available();
      Block blk1 = new Block(filename, 0);
      Buffer buff1 = bm.pin(blk1);
      int avail2 = bm.available();
      if (avail1 != avail2+1)
         System.out.println("*****BufferTest: bad available");
      Block blk2 = new Block(filename, 1);
      Buffer buff2 = bm.pin(blk2);
      int avail3 = bm.available();
      if (avail2 != avail3+1)
         System.out.println("*****BufferTest: bad available");
      Block blk3 = new Block(filename, 0);
      Buffer buff3 = bm.pin(blk3);
      int avail4 = bm.available();
      if (avail3 != avail4)
         System.out.println("*****BufferTest: bad available");
      bm.unpin(buff1);
      int avail5 = bm.available();
      if (avail4 != avail5)
         System.out.println("*****BufferTest: bad available");
      bm.unpin(buff2);
      int avail6 = bm.available();
      if (avail5 != avail6-1)
         System.out.println("*****BufferTest: bad available");
      bm.unpin(buff3);
      int avail7 = bm.available();
      if (avail6 != avail7-1 || avail7 != avail1)
         System.out.println("*****BufferTest: bad available");
   }
   
   private static void testDeadlock() {
      int avail = bm.available();
      Block[] blocks = new Block[avail];
      Buffer[] buffers = new Buffer[avail];
      for (int i=0; i<avail; i++) {
         blocks[i] = new Block(filename, i);
         buffers[i] = bm.pin(blocks[i]);
      }
      try {
         Buffer buff = bm.pin(new Block(filename, avail));
         System.out.println("*****BufferTest: didn't catch deadlock");
         bm.unpin(buff);
      }
      catch (BufferAbortException e) {}
      for (int i=0; i<avail; i++)
         bm.unpin(buffers[i]);
   }
}
