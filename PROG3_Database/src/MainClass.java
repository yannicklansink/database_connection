import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainClass {

	public static void main(String[] args) {
		DataBaseApplication databeest = new DataBaseApplication();
		if ((databeest.loadDataBaseDriver("com.mysql.cj.jdbc.Driver"))
				&& (databeest.makeConnection())) {
			databeest.doSomeQuerying();
		}
	}
}

class DataBaseApplication {

	private Connection m_Conn;
	
	public DataBaseApplication() {
		m_Conn = null;
	}
	
	public void doSomeQuerying() {
		String query = "select * from hemelobject limit 5";
		
		try (Statement stmt = m_Conn.createStatement()) {
		      ResultSet rs = stmt.executeQuery(query);
		      while (rs.next()) {
		        String objectNaam = rs.getString("objectnaam");
		        String satellietVan = rs.getString("satellietVan");
		        System.out.println(objectNaam + ", satellite of " + satellietVan);
		      }
		    } catch (SQLException e) {
		      System.err.print(e);
		    }
		
	}

	public boolean makeConnection() {
		String url = "jdbc:mysql://localhost:3306/outerspace_local";
		String username = "root";
		String password = "wachtwoord";
		
		try {
			m_Conn = DriverManager.getConnection(url, username, password);
			return true;
		} catch (SQLException e) {
			System.out.println("Kan geen verbinding maken met de database.");
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean loadDataBaseDriver(String string) {
		try {
			Class.forName(string);
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("iets gaat fout");
			return false;
		}
	}
	
	public Connection getCon() {
		return this.m_Conn;
	}

}
