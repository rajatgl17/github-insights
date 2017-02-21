package in.rajatgoel.github_insights.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;

public class DBUpdater implements Runnable {

	private volatile boolean keepAlive = true;
	public int count;

	@Override
	public void run() {
		try {
			updateRepoCount();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		keepAlive = false;
	}

	private void updateRepoCount() throws IOException {
		HashMap<String, LocalDate> langDateMap = new HashMap<String, LocalDate>();
		HashMap<String, Integer> langIdMap = new HashMap<String, Integer>();
		GithubAPI gapi = new GithubAPI();
		Insert ins = new Insert();

		String sql = "SELECT pl.pl_id as id, name, MAX(rc.date) as date " + "FROM programming_languages as pl "
				+ "LEFT JOIN repo_counts as rc ON pl.pl_id = pl_fk_id " + "WHERE status = 1 " + "GROUP BY pl.pl_id";
		try {
			DBConn dbc = new DBConn();
			Connection conn = dbc.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				LocalDate ld;
				String date = rs.getString("date");
				if (rs.wasNull())
					ld = LocalDate.of(2008, 1, 1);
				else
					ld = LocalDate.parse(date).plusDays(1);

				langDateMap.put(rs.getString("name"), ld);
				langIdMap.put(rs.getString("name"), rs.getInt("id"));
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (String language : langDateMap.keySet()) {
			LocalDate end = LocalDate.now();
			LocalDate start = langDateMap.get(language);
			while (start.isBefore(end)) {
				String urlString = "https://api.github.com/search/repositories?q=language:" + language + "+created:"
						+ start;
				Long count = gapi.count(urlString);
				int id = langIdMap.get(language);
				ins.InsertValues(id, start, start.getDayOfWeek().toString(), count);
				start = start.plusDays(1);
				try {
					Thread.sleep(61*100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
