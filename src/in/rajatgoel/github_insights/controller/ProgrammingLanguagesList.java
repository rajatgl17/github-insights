package in.rajatgoel.github_insights.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import in.rajatgoel.github_insights.model.DBConn;

@WebServlet("/ProgrammingLanguagesList")
public class ProgrammingLanguagesList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProgrammingLanguagesList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		DBConn dbconn = new DBConn();
		Connection conn = dbconn.getConnection();
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select pl_id as id, name from programming_languages where status = 1");
			
			while(rs.next()){
				hm.put(rs.getString("name"), rs.getInt("id"));
			}
		} catch (SQLException e) {
			response.sendError(400);
		}
		
		JSONObject obj = new JSONObject(hm);
		out.println(obj);
	}

}
