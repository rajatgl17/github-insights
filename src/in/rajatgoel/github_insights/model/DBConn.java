package in.rajatgoel.github_insights.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	
	private Connection conn = null;
	private static String username = "root";
	private static String password = "";
	private static String dbUrl = "jdbc:mysql://localhost:3306/github-insights";
	
	public DBConn(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl,username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return conn;
	}
}
