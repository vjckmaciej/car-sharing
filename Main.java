package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
public class Main {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:file:";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";
    public static void main(String[] args) {
        Args arguments = new Args();
        new CommandLine(arguments).parse(args);
        String DB_NAME = arguments.databaseName;
        System.out.println(DB_NAME);
        String DB_PATH = "./src/carsharing/db/" + (DB_NAME == null ? "carsharing" : DB_NAME);
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL + DB_PATH);
            conn.setAutoCommit(true);
            //STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE   COMPANY " +
                    "(ID INTEGER, " +
                    " NAME VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try

    }

    static class Args {
        @Option(names = {"--databaseFileName", "-databaseFileName"}, description = "Database name (default: carsharing)")
        String databaseName = "carsharing";
    }
}