@IF NOT EXIST server\target\server-2.9-SNAPSHOT.jar (
    @CALL mvn clean package
)
set CLASSPATH=server\target\server-2.9-SNAPSHOT.jar
@java simpledb.server.Startup %1
