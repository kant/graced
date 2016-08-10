package database.dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import database.DatabaseException;
import database.ErpDB;
import database.enums.TypesDB;
import database.objects.TableName;



/**
 * DAO class for getting the tablenames of a database.
 * @author Ing. Melina C. Vidoni - 2014
 */
public class TablesDAO {
	
	
	
	/**
	 * Main method for getting the names of the tablenames for a given system.
	 * @return the list of all tablenames in object form
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	public static LinkedList<TableName> getTablenames() throws DatabaseException, SQLException {
		// Get the database
		ErpDB erpDB = ErpDB.getInstance();
		
		// Call the appropiate method
		if( TypesDB.POSTGRESQL.getName().equals( erpDB.getType() ) ) {
			return getPostgresTables();
		}
		else if( TypesDB.ORACLE.getName().equals( erpDB.getType() ) ) {
			return getOracleTables();
		}
		else if( TypesDB.MYSQL.getName().equals( erpDB.getType() ) ) {
			return getMysqlTables();
		}
		else if( TypesDB.SQLSERVER.getName().equals( erpDB.getType() ) ) {
			return getSqlserverTables();
		}
		else
			return null;
	}

	
	
	/**
	 * Getting tablenames with PostgreSQL's sintaxis.
	 * @return The list of all tablenames of the selected database.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	private static LinkedList<TableName> getPostgresTables() throws DatabaseException, SQLException {
		// Create the list
		LinkedList<TableName> tablenames = new LinkedList<TableName>();
		
		// Create a connection
		ErpDB erpDB = ErpDB.getInstance();
		erpDB.connect();
		
		// Create a query
		String query = "SELECT table_name FROM information_schema.tables WHERE table_schema LIKE 'public' ORDER BY table_name ASC;";
		
		// Execute query
		ResultSet rs = erpDB.query(query);
		
		// Save the tablenames
		while( rs.next() ) {
			// Add it to the list
			tablenames.add( new TableName( rs.getString("table_name") ) );
		}
		
		// Close the connection
		erpDB.close();
		
		// Return the list
		return tablenames;
	}


	/**
	 * Getting tablenames with Oracle's sintaxis.
	 * @return The list of all tablenames of the selected database.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	private static LinkedList<TableName> getOracleTables() throws DatabaseException, SQLException {
		// Create the list
		LinkedList<TableName> tablenames = new LinkedList<TableName>();
		
		// Create a connection
		ErpDB erpDB = ErpDB.getInstance();
		erpDB.connect();
		
		// Create a query
		String query = "SELECT table_name FROM user_tables ORDER BY table_name ASC";
		
		// Execute query
		ResultSet rs = erpDB.query(query);
		
		// Save the tablenames
		while( rs.next() ) {
			// Add it to the list
			tablenames.add( new TableName( rs.getString("table_name") ) );
		}
		
		// Close the connection
		erpDB.close();
		
		// Return the list
		return tablenames;
	}
	
	
	/**
	 * Getting tablenames with SQL Server's sintaxis.
	 * @return The list of all tablenames of the selected database.
	 */
	private static LinkedList<TableName> getSqlserverTables() {
		// TODO complete SQL Server tablename getting method
		return new LinkedList<TableName>();
	}

	
	/**
	 * Getting tablenames with MySQL's sintaxis.
	 * @return The list of all tablenames of the selected database.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	private static LinkedList<TableName> getMysqlTables() throws DatabaseException, SQLException {
		// Create the ERP
		ErpDB erpDB = ErpDB.getInstance();
		erpDB.connect();
		
		// Get the metadata
		DatabaseMetaData dbm = erpDB.getMetadata();
		
		// Prepare the types
		String[] types = {"TABLE"};
		 
		// Query
		ResultSet rs = dbm.getTables(null,null,"%",types);
		
		// Prepare the result
		LinkedList<TableName> list = new LinkedList<TableName>();
		
		// While we have results
		while ( rs.next() ) {
			// Add it to the list
			list.add( new TableName(rs.getString("TABLE_NAME")) );		
		}
		
		// Close the connection
		erpDB.close();
		
		// Return the list
		return list;
	}

}
