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

@WebServlet("/GetRepoCount")
public class GetRepoCount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetRepoCount() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lang1 = "43", lang2 = "44", way = "relative";
		long sum1 = 0, sum2 = 0;

		if (request.getParameterMap().containsKey("lang1"))
			lang1 = request.getParameter("lang1");
		if (request.getParameterMap().containsKey("lang2"))
			lang2 = request.getParameter("lang2");
		if (request.getParameterMap().containsKey("way"))
			way = request.getParameter("way");

		PrintWriter out = response.getWriter();

		DBConn dbconn = new DBConn();
		Connection conn = dbconn.getConnection();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + "date, " + "SUM(CASE WHEN pl_fk_id=" + lang1
					+ " THEN count ELSE 0 END) c1, " + "SUM(CASE WHEN pl_fk_id=" + lang2 + " THEN count ELSE 0 END) c2 "
					+ "FROM repo_counts " + "group by date");
			if (way.equals("relative")) {
				while (rs.next()) {
					out.print(rs.getString("date") + "," + rs.getInt("c1") + "," + rs.getInt("c2") + "\n");
				}
			} else if (way.equals("absolute")) {
				while (rs.next()) {
					sum1+=rs.getInt("c1");
					sum2+=rs.getInt("c2");
					out.print(rs.getString("date") + "," + sum1 + "," + sum2 + "\n");
				}
			}

		} catch (SQLException e) {
			response.sendError(400);
		}
	}

}
