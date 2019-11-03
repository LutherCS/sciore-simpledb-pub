package simpledb.server;

import simpledb.remote.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class Startup {
    protected void startRMIRegistery(int port) {
        try {
            final Registry rmiRegistry = LocateRegistry.createRegistry(port);
            Runtime.getRuntime().addShutdownHook(new UnbindRMIRegistry(rmiRegistry));
        } catch (RemoteException e) {
            System.out.println("Failed to bind RMI Registry on port:" + port);
            System.out.println("Please try another port or terminate the existing Registry.");
        }
    }

    public static void parseArgs(String... args) throws Exception {
        String dbFileName;
        int port;

        if (args.length < 1) {
            System.err.println("Usage:");
            System.err.println("\t Startup <filename> [<port>]");
            return;
        }

        dbFileName = args[0];

        if (args.length < 2) {
            port = 1099;
        } else {
            port = Integer.parseInt(args[1]);
        }

        System.out.println("Starting up server - "+dbFileName+":"+port);
        new Startup(dbFileName,port);
    }

    public Startup(String dbFileName, int port) throws Exception {
        //Programmatically engage RMI backend
        startRMIRegistery(port);

        // configure and initialize the database
        SimpleDB.init(dbFileName);

        // post the server entry in the rmi registry
        RemoteDriver d = new RemoteDriverImpl();
        Naming.rebind("//localhost:"+port+"/simpledb", d);

        System.out.println("database server ready");
    }

    public static void main(String args[]) throws Exception {
        Startup.parseArgs(args);
    }

    private class UnbindRMIRegistry extends Thread {
        private final Registry rmiRegistry;

        UnbindRMIRegistry(Registry rmiRegistry) {
            this.rmiRegistry = rmiRegistry;
        }

        @Override
        public void run() {
            try {
                System.out.println("Disengaging RMI Registry.");
                UnicastRemoteObject.unexportObject(rmiRegistry, true);
            } catch (NoSuchObjectException e) {
                // no-op
            }
        }
    }
}
