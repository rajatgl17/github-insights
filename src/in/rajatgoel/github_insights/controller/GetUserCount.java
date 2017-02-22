package in.rajatgoel.github_insights.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.rajatgoel.github_insights.model.DBConn;

@WebServlet("/GetUserCount")
public class GetUserCount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetUserCount() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		DBConn dbconn = new DBConn();
		Connection conn = dbconn.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT date,count FROM user_counts WHERE pl_fk_id = 43");
			
			while(rs.next()){
				out.println(rs.getString("date")+","+rs.getInt("count"));
			}
			
		} catch (SQLException e) {
			response.sendError(400);
		}
	}

}
