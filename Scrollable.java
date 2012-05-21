/**
 *  Running some simple tests with JDBC and PostgreSQL
 * 
 */
import java.sql.*;
public class Scrollable 
{
	public static void main(String [] args) 
	{
    		Connection con = null;

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



		try 
		{
      			con = DriverManager.getConnection("jdbc:postgresql://localhost/postgres","pgsql","pgsql");

			// Create a Statement for scrollable ResultSet
			Statement sta = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Catch the ResultSet object
			ResultSet res = sta.executeQuery("SELECT * FROM tb_teste");

			// Check ResultSet's scrollability
			if (res.getType() == ResultSet.TYPE_FORWARD_ONLY) 
			{
				System.out.println("ResultSet non-scrollable.");
			} 
			else 
			{
				System.out.println("ResultSet scrollable.");
			}

			System.out.println("List of Profiles:");

			// Move the cursor to the last row
			res.last();

			System.out.println("Total de linhas:"+ res.getRow());
			// Stop the loop when the cursor is positioned before the first row
			while (!res.isBeforeFirst()) 
			{
				//while (res.previous()) {
				//String firstName = res.getString("FirstName");
				//String lastName = res.getString("LastName");
				//System.out.println("   "+firstName+" "+lastName);
				String myNum = res.getString(1);
				System.out.println("   "+myNum);

				// Move the cursor backward one row
        			res.previous();
      			}

			res.first();
			while(res.next())
			{
				String myNum = res.getString(1);
				System.out.println("   "+myNum);
			}


			// Close ResultSet and Statement
			res.close();
			sta.close();

			con.close();
		}
		catch (Exception e) 
		{
			System.err.println("Exception: "+e.getMessage());
		}
	}
}
