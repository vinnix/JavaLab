
//package xxspiralutilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class xxQueueSizeUtilities 
{
	public static String getQueueSize()
	{
		try 
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException cnfe) 
		{
			System.err.println("Couldn't find driver class:");
			System.out.println("Couldn't find driver class:");
			cnfe.printStackTrace();
		}
		System.out.println("Able to locate the jmake a connection.");
		Connection postGresConn = null;
		try 
		{
			postGresConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "pgsql", "pgsql");
		} 
		catch (SQLException se) 
		{
			System.out.println("Couldn't connect: print out a stack trace and exit.");
			se.printStackTrace();
			System.exit(1);
		}

		if (postGresConn != null)
			System.out.println("Successfully connected to Postgres Database");
		else
			System.out.println("We should never get here.");

		try 
		{
			Statement stGetCount = postGresConn.createStatement();
			ResultSet rs = stGetCount.executeQuery("SELECT * from tb_teste;");
            
			while(rs.next())
	    		{
				System.out.println(rs.getString(1));
	    		}
            		return "OK";
        	} 
		catch (SQLException e)
		{
            		System.out.println("Could not create statement in JDBC");
            		e.printStackTrace();

        	}
        	return "SUCCESS";
    	}

    	public static void main(String[] args) 
	{
        	String textString = getQueueSize();
        	System.out.println("Result is " + textString);
    	}
}

