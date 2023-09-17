package simpledb.buffer;

import simpledb.server.SimpleDB;
import simpledb.file.Block;
import simpledb.buffer.*;

public class BufferMgrTest {
	public static void main(String[] args) throws Exception {
		SimpleDB.init("testdb");
		BufferMgr bm = SimpleDB.bufferMgr();
		int mytxid = 1; // should be obtained from tx mgr
		int lsn = 0;    // should be obtained from log mgr

		Block blk = new Block("testfile", 0);
		Buffer buff = bm.pin(blk);

		int ival = buff.getInt(20);
		String sval = buff.getString(40);
 		System.out.println("current value at location 20 = "
 							+ ival);
 		System.out.println("current value at location 40 = "
 							+ sval);

		buff.setInt(20, ival+1, mytxid, lsn);
		buff.setString(40, sval+"1", mytxid, lsn);
		bm.unpin(buff);

		Buffer buff2 = bm.pin(blk);
 		System.out.println("new value at location 20 = "
 							+ buff2.getInt(20));
 		System.out.println("new value at location 40 = "
 							+ buff2.getString(40));
 		bm.unpin(buff2);

		bm.flushAll(mytxid);  // ensure that changes are on disk
	}
}