package in.rajatgoel.github_insights.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Insert {
	public void InsertValues(int id, LocalDate locald, String day, Long count, String table_name) {

		try {
			Date date = Date.valueOf(locald);
			DBConn dbc = new DBConn();
			Connection conn = dbc.getConnection();
			String sql = "INSERT INTO " + table_name + " (pl_fk_id, date, day, count) VALUES (?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setDate(2, date);
			stmt.setString(3, day);
			stmt.setLong(4, count);
			stmt.executeUpdate();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
