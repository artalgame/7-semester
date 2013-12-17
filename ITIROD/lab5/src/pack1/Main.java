package pack1;

import java.sql.*;
import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;


public class Main {
   private static final Logger log4j = LogManager.getLogger(Main.class.getName());
   
   public static void main(String[] args) throws SQLException {
	User u1 = new User();
	u1.Id=1;
	u1.Name="Sasha";
	u1.Height=186;
	u1.Weight=83;
	
	log4j.error("Start log");
	
	User u2 = new User();
	u2.Id=2;
	u2.Name="Dima";
	u2.Height=175;
	u2.Weight=65;

	User u3 = new User();
	u3.Id=3;
	u3.Name="Vadim";
	u3.Height=176;
	u3.Weight=79;
	
	 DataBase db = new DataBase();
	 db.cleanTable();
	 db.insertUser(u1);
	 db.insertUser(u2);
	 db.insertUser(u3);
	 
	 db.printState();
	 
	 u1.Name="Shura";
	 db.updateUser(u1);
	 
	 db.printState();
	 
	 db.deleteUser(u2.Id);
	 
	 db.printState();
	 
	 User u4 = db.getUserById(1);
	 
	 System.out.println("Name: "+u4.Name);	 
	 log4j.error("End log");
   }
}