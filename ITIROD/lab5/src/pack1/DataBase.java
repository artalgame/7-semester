package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBase {
	// Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=TEST_DB;user=admin;password=sql";
    Connection con = null;
    
	public DataBase()
	{
		 try {
	         // Establish the connection.
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	         con = DriverManager.getConnection(connectionUrl);
	      }

	      // Handle any errors that may have occurred.
	      catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	}
	
	public User getUserById(int id) throws SQLException
	{
		 String SQL = "SELECT * FROM Users WHERE Id="+id;
		 Statement stmt = con.createStatement();
         try {
			return new User(stmt.executeQuery(SQL));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
         finally {
	      }
	}
	
	public void deleteUser(int id) throws SQLException
	{
		try
		{
			String SQL = "DELETE FROM Users WHERE Id="+id;
			Statement stmt = con.createStatement();
			stmt.executeQuery(SQL);
		}
		catch(Exception ex){}
	}
	
	public void updateUser(User user) throws SQLException{
		try
		{
			String SQL = "UPDATE Users SET Name='"+user.Name+"' WHERE Id =" +user.Id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
		}
		catch(Exception ex){
		}
	}
	
	public void insertUser(User user) throws SQLException{
		try{
			String SQL = "INSERT INTO Users (Id, Name, Height, Weight) VALUES("+user.Id+", '"+user.Name+"', "+user.Height+", "+user.Weight+")";
			Statement stmt = con.createStatement();
			stmt.executeQuery(SQL);
		}
		catch(Exception ex)
		{}
	}
	
	public void cleanTable() throws SQLException
	{
		try
		{
			String SQL = "DELETE FROM Users";
			Statement stmt = con.createStatement();
			stmt.executeQuery(SQL);
		}
		catch(Exception ex){}
	}
	
	public void printState() throws SQLException
	{
		String SQL = "SELECT * FROM Users";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        // Iterate through the data in the result set and display it.
        while (rs.next()) {
           System.out.println(rs.getString(1) + " " + rs.getString(2)+ " " +rs.getString(3)+ " " +rs.getString(4));
        }
	}
	
	
	public void close()
	{
		 if (con != null) try { con.close(); } catch(Exception e) {};
	}
}
