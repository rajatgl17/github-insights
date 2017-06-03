package in.rajatgoel.github_insights.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.rajatgoel.github_insights.model.DBConn;

@WebServlet("/LastUpdatedOn")
public class LastUpdatedOn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LastUpdatedOn() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		LocalDate date = null;

		DBConn dbconn = new DBConn();
		Connection conn = dbconn.getConnection();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select MAX(date) as date from repo_counts");

			while (rs.next()) {
				date = LocalDate.parse(rs.getString("date"));
			}

		} catch (SQLException e) {
			response.sendError(500);
		}
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy");
		out.println(date.format(format));
	}

}
