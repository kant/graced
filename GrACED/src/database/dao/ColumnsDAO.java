package database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import database.DatabaseException;
import database.ErpDB;
import database.enums.TypesDB;
import database.objects.ColumnName;



/**
 * DAO class for getting the column names of a table in the database.
 * @author Ing. Melina C. Vidoni - 2014
 */
public class ColumnsDAO {
	
	
	/**
	 * Main method for getting the names of the column names for a given table.
	 * @return the list of all column names in object form.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	public static LinkedList<ColumnName> getColumns(String table) throws DatabaseException, SQLException {
		// Get the database
		ErpDB erpDB = ErpDB.getInstance();
		
		// Call the appropiate method
		if( TypesDB.POSTGRESQL.getName().equals( erpDB.getType() ) ) {
			return getPostgresColumns(table);
		}
		else if( TypesDB.ORACLE.getName().equals( erpDB.getType() ) ) {
			return getOracleColumns(table);
		}
		else if( TypesDB.MYSQL.getName().equals( erpDB.getType() ) ) {
			return getMysqlColumns(table);
		}
		else if( TypesDB.SQLSERVER.getName().equals( erpDB.getType() ) ) {
			return getSqlserverColumns(table);
		}
		else
			return null;
	}

	
	
	/**
	 * Getting columns names with PostgreSQL's sintaxis.
	 * @param table The table name.
	 * @return The list of all column names of the selected table.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	private static LinkedList<ColumnName> getPostgresColumns(String table) throws DatabaseException, SQLException {
		// Get a connection
		ErpDB erpDB = ErpDB.getInstance();
		erpDB.connect();
		
		// Prepare the query
		String query = "SELECT column_name FROM information_schema.columns WHERE table_name =\'" 
				+ table + "\';";
		
		// Execute query
		ResultSet rs = erpDB.query(query);
		
		// Create the list
		LinkedList<ColumnName> list = new LinkedList<ColumnName>();
		
		// Now save the info
		while( rs.next() ) {
			// Get the column name
			ColumnName column = new ColumnName( rs.getString("column_name") );
			
			// Add to the list
			list.add(column);
		}
		
		// Close the connection
		erpDB.close();
		
		// Return the list
		return list;
	}
	
	
	
	
	/**
	 * Getting columns names with MySQL's sintaxis.
	 * @param table The table name.
	 * @return The list of all column names of the selected table.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	private static LinkedList<ColumnName> getMysqlColumns(String table) throws DatabaseException, SQLException {
		// Get a connection
		ErpDB erpDB = ErpDB.getInstance();
		erpDB.connect();
				
		// Prepare the query
		String query = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS"
					+ " WHERE table_name = \'" + table +"\' ORDER BY column_name ASC;";
			
		// Execute query
		ResultSet rs = erpDB.query(query);
			
		// Create the list
		LinkedList<ColumnName> list = new LinkedList<ColumnName>();
			
		// Now save the info
		while( rs.next() ) {
			// Get the column name
			list.add( new ColumnName(rs.getString("column_name")) );
		}
				
		// Close the connection
		erpDB.close();
		
		// Return the list
		return list;
	}
	
	
	
	
	/**
	 * Getting columns names with Oracle's sintaxis.
	 * @param table The table name.
	 * @return The list of all column names of the selected table.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	private static LinkedList<ColumnName> getOracleColumns(String table) throws DatabaseException, SQLException {
		// Get a connection
		ErpDB erpDB = ErpDB.getInstance();
		erpDB.connect();
				
		// Create the list
		LinkedList<ColumnName> list = new LinkedList<ColumnName>();
				
		// Prepare the query
		String query = "SELECT column_name FROM user_tab_cols WHERE table_name =\'" 
					+ table + "\' ORDER BY column_name ASC";
			
		// Execute query
		ResultSet rs = erpDB.query(query);
				
		
		// Now save the info
		while( rs.next() ) {
			// Get the column name
			list.add( new ColumnName(rs.getString("column_name")) );
		}

		
		// Close the connection
		erpDB.close();
		
		// Return the list
		return list;
	}
	
	
	
	/**
	 * Getting columns names with SQL Server's sintaxis.
	 * @param table The table name.
	 * @return The list of all column names of the selected table.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	private static LinkedList<ColumnName> getSqlserverColumns(String table) {
		// TODO Complete SQL Server method.
		return new LinkedList<ColumnName>();
	}

}
