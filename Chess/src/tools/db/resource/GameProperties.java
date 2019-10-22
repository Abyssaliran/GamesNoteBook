package tools.db.resource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.TreeMap;

public class GameProperties extends TreeMap<String, String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final GameProperties INSTANCE = new GameProperties();

	static final String[] MAIN_PROPERTIS = { 
			"Event", // (Название турнира или матча)
			"Site",  // (Место проведения партии)
			"Date",  // (Дата начала партии)
			"Round", // (Номер раунда в турнире)
			"White", // (Имя игрока белыми фигурами)
			"Black", // (Имя игрока чёрными фигурами)
			"Result" // (Результат партии)
	};

	String movesText = "";

	public boolean isMain(String tag) {
		return 0 <= Arrays.binarySearch(MAIN_PROPERTIS, tag);
	}

	public void save(PrintWriter w) {
		entrySet().forEach(e -> w.format("[%s \"%s\"]%n", e.getKey(), e.getValue()));
	}

	@Override
	public String toString() {
		StringWriter ss = new StringWriter();

		save(new PrintWriter(ss));

		return ss.toString();
	}

	public String createTableSQL(String tableName) {
		StringWriter ss = new StringWriter();
		PrintWriter w = new PrintWriter(ss);

		w.format("DROP TABLE IF EXISTS %s; %n", tableName);
		w.format("CREATE TABLE %s ( %n", tableName);
    	
    	Arrays.stream(MAIN_PROPERTIS)
    	      .forEach(e -> w.format(" %s VARCHAR(50) NOT NULL, %n", e));
    	
    	w.format(" moves VARCHAR(50) NOT NULL %n)");
    	
    	return ss.toString();
    }
}
