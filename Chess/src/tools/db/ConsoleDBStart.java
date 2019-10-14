package tools.db;

import java.io.File;
import java.util.logging.Logger;

import tools.db.report.HTMLReporter;
import tools.db.report.HTMLReporterDefault;

public class ConsoleDBStart {
	private static Logger log = Logger.getLogger(ConsoleDBStart.class.toString());
 
	public static void main(String[] args) {
		log.info("ConsoleDBStart start"); 
				
		dbHtmlReportDemo();
		
		log.info("ConsoleDBStart end");
	}
	
	private static void dbHtmlReportDemo() {
		File htmlReports = new File("./html_reports");
		htmlReports.mkdirs();
		
		dbHtmlReportDemoH2(htmlReports);
		dbHtmlReportDemoHSQLDB(htmlReports);
//		dbHtmlReportDemoOracle(htmlReports);
	}

	static void dbHtmlReportDemoH2(File htmlReports) {
		String htmlReportsPath = htmlReports.getAbsolutePath();
		HTMLReporter r = new HTMLReporterDefault(htmlReportsPath);
		
		String drv = "org.h2.Driver";
		String url = "jdbc:h2:~/test";
		String uid = "sa";
		String pwd = "";
		r.reportDataBase(drv, url, uid, pwd);
	}

	static void dbHtmlReportDemoHSQLDB(File htmlReports) {
		String htmlReportsPath = htmlReports.getAbsolutePath();
		HTMLReporter r = new HTMLReporterDefault(htmlReportsPath);
		
		String drv = "org.hsqldb.jdbc.JDBCDriver";
//		String url = "jdbc:hsqldb:file:/opt/db/testdb";
		String url = "jdbc:hsqldb:mem:mymemdb";
		String uid = "SA";
		String pwd = "";
		r.reportDataBase(drv, url, uid, pwd);
	}

	static void dbHtmlReportDemoOracle(File htmlReports) {
		String htmlReportsPath = htmlReports.getAbsolutePath();
		HTMLReporter r = new HTMLReporterDefault(htmlReportsPath);
		
		String drv = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@//localhost:1521/XE";
		String uid = "SYSTEM"; 
		String pwd = "sql"; 
		r.reportDataBase(drv, url, uid, pwd);
	}
}