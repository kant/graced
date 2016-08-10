package database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.enums.TypesDB;



/**
 * Class that manages the connection with the ERP database.
 * This uses the JDBC connection driver.
 * @author Ing. Melina C- Vidoni - 2014
 *
 */
public class ErpDB {
	/*
	 * Database Instance
	 */
	private static ErpDB instance = new ErpDB();
	
	/*
	 * Needded connection vars.
	 */
	private Connection connection;
	private String database = "";
	private String user = "";
	private String password = "";
	private String server = "";
	private TypesDB type;
	private String location = "";
	private String port = "";
	 
	/**
	 * Empty constructor does nothing.
	 */
	private ErpDB() { }
	

	public void setDB(String db) {
		database = db;
	}

	public void setUser(String u) {
		user = u;
	}

	public void setPassword(String p) {
		password = p;
	}
	
	public void setLocation(String l) {
		location = l;
	}
	
	public void setPort(String p) {
		port = p;
	}

	
	public void setServer() {
		// Get the server
		server = type.getServer();
		
		// If it is Oracle...
		if( type.equals( TypesDB.ORACLE ) ){
			// Write the server like this.
			server = server + user + "/" + password + "@//" + location + ":" + port + "/" + database ;
		}
		// In other case
		else
			// Write the server like this
			server = server + location + ":" + port + "/" + database + "?user=" + user +"&amp;password=" + password;
	}
	
	
	public void setType(String t) {
		type = TypesDB.getLabel(t);
	}

	public String getType() {
		return type.getName();
	}
	
	public String getName() {
		return database;
	}
	
	
	/**
	 * Method that returns the database metadata. Used in MySQL connections.
	 * @return @see DatabaseMetaData used in MySQL connections.
	 * @throws SQLException
	 */
	public DatabaseMetaData getMetadata() throws SQLException {
		return connection.getMetaData();
	}


	/**
	 * Method to obtain an instance, given that the class
	 * is implemented as a singleton.
	 * @return Current instance of the class.
	 */
	public static ErpDB getInstance() {
		return instance;
	}
	 
	

	/**
	 * Tries to connect to the database.
	 * @throws DatabaseException
	 */
	public void connect() throws DatabaseException {
		try {
			Class.forName(type.getDriver());
			
			// Connection
			connection = DriverManager.getConnection(server, user, password);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Cannot connect to the database. \nPlease contact the admin.");
		}
	}
	 
	
	

	/**
	 * Method to close the connection with the database.
	 * @param rs Result set that we want to get.
	 * @throws DatabaseException
	 */
	public void close(ResultSet rs) throws DatabaseException {
		if(rs !=null) {
			try {
				rs.close();
			}
			catch(Exception e) {
				throw new DatabaseException("Cannot close the connection with the database.");
			}
		}
	}
	 
	
		
	
	/**
	 * Method that allows to query the tables, returning the result set instanced
	 * in an array with the row list, the one that can be directly inserted into
	 * a java table.
	 * @param s Query to be executed.
	 * @param columns Number of columns on the java table.
	 * @param rowList List where the rows are going to be loaded.
	 * @throws SQLException
	 */
	public void query(String s, int columns, ArrayList<Object[]> rowList) throws SQLException {
		Statement instruction = connection.createStatement();
		// Executing the query
		ResultSet rs = instruction.executeQuery(s);
			
		// Preparing the return
		while(rs.next()) {
			Object[] row = new Object[columns];
			for(int i=0; i<columns; i++) {
				row[i] = rs.getObject(i+1);
			}
			rowList.add(row);			
		}

	}
	
	

	/**
	 * Method that allows to query tables, returning directly the
	 * result set, and you can work with it to get the data.
	 * @param s String with the SQL query to be executed.
	 * @return result set loaded with the queried data.
	 * @throws SQLException
	 */
	public ResultSet query(String s) throws SQLException {
		Statement instruction = connection.createStatement();
		return instruction.executeQuery(s);
	}
	

	
	
	/**
	 * Method that updates a table, using an insert-type sentence.
	 * @param insert SQL sentence to be executed.
	 * @throws SQLException
	 */
	public void update(String insert) throws SQLException {
		Statement instruction = connection.createStatement();
		instruction.executeUpdate(insert);
	}

	 
	
	/**
	 * Method that close an existent connection with the database.
	 */
	public void close() {
		if(connection != null) {
			try {
				connection.close();
			}
			catch(Exception e) { }
		}
	}




}
