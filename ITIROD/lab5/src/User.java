import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	static DataBase db = new DataBase();
	public int Id;
	
	public String Name;
	public int Height;
	public int Weight;
    
	public User()
	{
	}
	
	public User(ResultSet rs) throws SQLException
	{
		if(rs.next())
		{
			Id = rs.getInt(1);
			Name = rs.getString(2);
			Height = rs.getInt(3);
			Weight = rs.getInt(4);
		}	
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
