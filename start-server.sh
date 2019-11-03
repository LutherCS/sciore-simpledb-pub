if [ ! -e "server/target/server-2.9-SNAPSHOT.jar" ]
then
  mvn clean package
fi
java -cp server/target/server-2.9-SNAPSHOT.jar simpledb.server.Startup $1
