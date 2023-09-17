package simpledb.query;

import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

/**
 * Create serverless studentdb
 * @author yasiro01
 */
public class CreateStudentDB {
  public static void main(String[] args) {
    SimpleDB.init("studentdb");
    String qry;
    // Creating DB tables
    Transaction txCreate = new Transaction();
    qry = "create table student(SId int, SName varchar(10), MajorId int, GradYear int)";
    SimpleDB.planner().executeUpdate(qry, txCreate);
    txCreate.commit();
    qry = "create table dept(DId int, DName varchar(8))";
    SimpleDB.planner().executeUpdate(qry, txCreate);
    txCreate.commit();
    qry = "create table course(CId int, Title varchar(20), DeptId int)";
    SimpleDB.planner().executeUpdate(qry, txCreate);
    txCreate.commit();
    qry = "create table SECTION(SectId int, CourseId int, Prof varchar(8), YearOffered int)";
    SimpleDB.planner().executeUpdate(qry, txCreate);
    txCreate.commit();
    qry = "create table ENROLL(EId int, StudentId int, SectionId int, Grade varchar(2))";
    SimpleDB.planner().executeUpdate(qry, txCreate);
    txCreate.commit();
    
    // Adding data to the DB
    Transaction txInsert = new Transaction();
    // Populating the table *student*
    qry = "insert into student(SId, SName, MajorId, GradYear) values ";
    String[] studvals = {"(1, 'joe', 10, 2004)",
                        "(2, 'amy', 20, 2004)",
                        "(3, 'max', 10, 2005)",
                        "(4, 'sue', 20, 2005)",
                        "(5, 'bob', 30, 2003)",
                        "(6, 'kim', 20, 2001)",
                        "(7, 'art', 30, 2004)",
                        "(8, 'pat', 20, 2001)",
                        "(9, 'lee', 10, 2004)"};
    for (String studval : studvals) {
      SimpleDB.planner().executeUpdate(qry + studval, txInsert);
    }
    txInsert.commit();
    // Populating the table *dept*
    qry = "insert into dept(DId, DName) values ";
    String[] deptvals = {"(10, 'compsci')",
                        "(20, 'math')",
                        "(30, 'drama')"};
    for (String deptval : deptvals) {
      SimpleDB.planner().executeUpdate(qry + deptval, txInsert);
    }
    txInsert.commit();
    // Populating the table *course*
    qry = "insert into course(CId, Title, DeptId) values ";
    String[] coursevals = {"(12, 'db systems', 10)",
                          "(22, 'compilers', 10)",
                          "(32, 'calculus', 20)",
                          "(42, 'algebra', 20)",
                          "(52, 'acting', 30)",
                          "(62, 'elocution', 30)"};
    for (String courseval : coursevals) {
      SimpleDB.planner().executeUpdate(qry + courseval, txInsert);
    }
    txInsert.commit();
    // Populating the table *section*
    qry = "insert into section(SectId, CourseId, Prof, YearOffered) values ";
    String[] sectvals = {"(13, 12, 'turing', 2004)",
                        "(23, 12, 'turing', 2005)",
                        "(33, 32, 'newton', 2000)",
                        "(43, 32, 'einstein', 2001)",
                        "(53, 62, 'brando', 2001)"};
    for (String sectval : sectvals) {
      SimpleDB.planner().executeUpdate(qry + sectval, txInsert);
    }
    txInsert.commit();
    // Populating the table *enroll*
    qry = "insert into enroll(EId, StudentId, SectionId, Grade) values ";
    String[] enrollvals = {"(14, 1, 13, 'A')",
                          "(24, 1, 43, 'C' )",
                          "(34, 2, 43, 'B+')",
                          "(44, 4, 33, 'B' )",
                          "(54, 4, 53, 'A' )",
                          "(64, 6, 53, 'A' )"};
    for (String enrollval : enrollvals) {
      SimpleDB.planner().executeUpdate(qry + enrollval, txInsert);
    }
    txInsert.commit();
  }
}
