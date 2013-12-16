import static org.junit.Assert.*;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;


public class MainTest {

	@Test
	public void testGetUserById() throws SQLException {
		try{
			User user = new DataBase().getUserById(1);
			Assert.assertEquals("Shura     ", user.Name);
		}
		catch(Exception ex){}
	}

	@Test
	public void testDeleteUser() throws SQLException {
		try
		{
			DataBase db = new DataBase();
			db.deleteUser(2);
			Assert.assertTrue(null == db.getUserById(2).Name);
		}
		catch(Exception ex){}
	}

}
