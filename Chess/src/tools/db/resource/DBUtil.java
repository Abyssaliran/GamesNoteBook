package tools.db.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Locale;

public class DBUtil {

	public static Connection getConnection() throws SQLException {
//		Connection con = getConnectionH2();
		Connection con = getConnectionHSQLDB();
		con.setAutoCommit(true);
		
		return con;
	}

	public static Connection getConnectionH2() throws SQLException {
		String drv = "org.h2.Driver";
		String url = "jdbc:h2:~/test";
		String uid = "sa";
		String pwd = "";
		try {
			Class.forName(drv);
			Locale.setDefault(Locale.ENGLISH);
		} catch (ClassNotFoundException e) {
			return null;
		}
		return DriverManager.getConnection(url, uid, pwd);
//		return DriverManager.getConnection("jdbc:h2:gamesnotebook");
	}

	public static Connection getConnectionHSQLDB() throws SQLException {
		String drv = "org.hsqldb.jdbc.JDBCDriver";
//	String url = "jdbc:hsqldb:file:/opt/db/testdb";
		String url = "jdbc:hsqldb:mem:mymemdb";
		String uid = "SA";
		String pwd = "";
		try {
			Class.forName(drv);
			Locale.setDefault(Locale.ENGLISH);
		} catch (ClassNotFoundException e) {
			return null;
		}
		return DriverManager.getConnection(url, uid, pwd);
	}


	/**
	 * Output result set of any query to HTML file.
	 * 
	 * @param message
	 *            comment for the result set.
	 * @param rs
	 *            the result set.
	 */
	public static void outResultSet(String message, ResultSet rs, PrintWriter w) {
		w.println("<h3> " + message + " </h3>");
		w.println("<table>");

		ResultSetMetaData rsmd = null;

		try {
			rsmd = rs.getMetaData();

			int numberOfColumns = rsmd.getColumnCount();

			// =============================
			// = Output result set header. =
			// =============================
			w.println("<tr class =\"trTitle\">");
			for (int i = 1; i <= numberOfColumns; i++) {
				String columnName = rsmd.getColumnLabel(i);

				w.println("  <td>");
				w.print(columnName);
				w.println("  </td>");
			} // for
			w.println("</tr>");

			// ==============================
			// = Output result set content. =
			// ==============================
			while (rs.next()) {
				w.println("<tr>");
				for (int k = 1; k <= numberOfColumns; k++) {
					String columnValue = rs.getString(k);
					if (columnValue == null)
						columnValue = "";
					
					columnValue = columnValue.replace("<", "&lt;");
					columnValue = columnValue.replace(">", "&gt;");

					w.println("  <td>");
					w.print("" + columnValue);
					w.println("  </td>");
				} // for
				w.println("</tr>");
			} // while
		} // try
		catch (SQLException e) {
			w.println("</tr></table><br><br>");
			System.err.println("outResultSet(...);\n" + e);
		}
		w.println("</table><br><br>");
	}

	public static void dumpResultSet(String message, ResultSet rs) throws FileNotFoundException {
		File f = new File(".", message + ".html");
		PrintWriter pw = new PrintWriter(f);
		
		pw.format("<html>%n<body>%n%n");
		outResultSet(message, rs, pw);
		pw.format("</body>%n</html>%n%n");
	} 
}
