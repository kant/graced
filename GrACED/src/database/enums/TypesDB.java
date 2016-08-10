package database.enums;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;





/**
 * Enum class for all the available databases.
 * @author ALDO
 *
 */
public enum TypesDB {
	POSTGRESQL ("PostgreSQL", "jdbc:postgresql://", "org.postgresql.Driver"),
	ORACLE ("Oracle", "jdbc:oracle:thin:", "oracle.jdbc.OracleDriver"),
	MYSQL ("MySQL", "jdbc:mysql://", "com.mysql.jdbc.Driver"),
	SQLSERVER ("SQL Server", "jdbc:sqlserver://", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
	;
	
	private String showableName;
	private String server;
	private String driver;

	
	
	/**
	 * Default constructor of the class.
	 * @param n A name.
	 * @param s A server.
	 * @param d A driver.
	 */
	TypesDB(String n, String s, String d) {
		showableName = n;
		server = s;
		driver = d;
	}
	

	
	/**
	 * A method to get an observable list, ready to fill a combobox.
	 * @return The filled observable list.
	 */
	public static ObservableList<TypesDB> getAll() {
		// Create the list
		List<TypesDB> originalList = new LinkedList<TypesDB>();
		originalList.add(POSTGRESQL);
		originalList.add(ORACLE);
		originalList.add(SQLSERVER);
		originalList.add(MYSQL);
		
		// Change it to observable
		ObservableList<TypesDB> list = FXCollections.observableList(originalList);
		
		// Return
		return list;
	}
	
	
	
	
	/**
	 * Getter method for the name of the database.
	 * @return The name of the database.
	 */
	public String getName() {
		return showableName;
	}
	
	
	/**
	 * Getter method for the server information.
	 * @return The server string.
	 */
	public String getServer() {
		return server;
	}
	
	
	/**
	 * Getter method for the driver information.
	 * @return The driver string.
	 */
	public String getDriver() {
		return driver;
	}
	
	
	
	/**
	 * Method that allows the system to get a enum that matches with the received label.
	 * @param n The label to be compared with.
	 * @return The enum object if it exists, or a null object.
	 */
	public static TypesDB getLabel(String n){
		if(POSTGRESQL.getName().equals(n))
			return POSTGRESQL;
		else if(ORACLE.getName().equals(n))
			return ORACLE;
		else if(MYSQL.getName().equals(n))
			return MYSQL;
		else if(SQLSERVER.getName().equals(n))
			return SQLSERVER;
		return null;
	}
	
	
	
	/**
	 * Returns the object as a string.
	 */
	public String toString() {
		return getName();
	}
	
}
