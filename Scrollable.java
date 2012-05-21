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
			// Statement sta = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // some tests with CONCUR_UPDATABLE

			// Create a Statement for scrollable and updatable ResultSet
			Statement sta = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// Catch the ResultSet object
			ResultSet res = sta.executeQuery("SELECT * FROM tb_teste a, tb_teste2 b WHERE a.col1 = b.col1");

			// Check ResultSet's scrollability
			if (res.getType() == ResultSet.TYPE_FORWARD_ONLY) 
			{
				System.out.println("ResultSet non-scrollable.");
			} 
			else 
			{
				System.out.println("ResultSet scrollable.");
			}

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

			res.absolute(10);

       			res.updateString("col2", "HelloWorld"); 
       			res.updateRow(); 

			
			res.first();
			int myIntSum =0;
			while(!res.isAfterLast())
			{
				int myIntNum = res.getInt("col1");
				myIntSum+= myIntNum;
				String myNum  = res.getString(1);
				String myNum2 = res.getString("col1");
				String myNum3 = res.getString("col2");
				System.out.println("   "+myNum+"  " + myNum2 + " -> "+ myNum3);
				res.next();
			}
			System.out.println("Soma de col1:"+ myIntSum);

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
