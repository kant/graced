package core;

import java.sql.SQLException;
import java.util.LinkedList;

import database.DatabaseException;
import database.ErpDB;
import database.Separation;
import database.dao.ColumnsDAO;
import database.dao.SeparationDAO;
import database.dao.TablesDAO;
import database.objects.ColumnName;
import database.objects.TableName;
import frsf.cidisi.faia.state.EnvironmentState;




/**
 * Class that represents the state of the environment in a certain
 * moment of time. Implements @see EnvironmentState from FAIA framework.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class DatabaseEnvironmentState extends EnvironmentState {
	private ErpDB database;
	private Separation separation;
	private LinkedList<TableName> tablenameList;

	
	public DatabaseEnvironmentState() {
		// Get the instance of the database.
		database = ErpDB.getInstance();
		
		// Get the instance of the separation
		separation = Separation.getInstance();
		
		// Call the init state
		initState();
	}
		
	
	
    /**
     * This method is used in two places:
     *   1. To set the initial state (the real one) of the world, seen
     *      by the simulator.
     *   2. To set the initial state of the agent.
     */
	@Override
	public void initState() {
		// Clean the tablename list
		tablenameList = new LinkedList<TableName>();
	}

	
	
	/**
	 * Method that fills the tablename list with all the tablename information.
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public void getAllTablenames() throws DatabaseException, SQLException {
		// First, get the basic tablenames
		tablenameList = TablesDAO.getTablenames();
		
		// Now go through each of them
		for( TableName t : tablenameList ) {
			// Separate the tablename
			LinkedList<String> splitted = SeparationDAO.separate(t.getName(), separation);
			
			// Add it
			t.addSplittedName(splitted);
		}
	}
	
	
	
	/**
	 * Returns the environment state in string format.
	 */
	@Override
	public String toString() {
		return "Database: " + database.getName()
				+ " with " + tablenameList.size() + " unclassified tables.";
	}


	/**
	 * Returns the database reference.
	 * @return the ERP database reference.
	 */
	public ErpDB getDatabase() {
		return database;
	}

	
	
	/**
	 * Returns the current size of the tablenameList.
	 * @return The size of the tablename.
	 */
	public int getTablenameCount() {
		return tablenameList.size();
	}



	/**
	 * Removes and returns the first element of the list.
	 * @return First table of the list.
	 * @throws DatabaseException 
	 * @throws SQLException 
	 */
	public TableName getFirstTablename() throws DatabaseException, SQLException {
		// Get the table
		TableName table =  tablenameList.removeFirst();
		
		// Get the columns
		table.setColumnsNames( ColumnsDAO.getColumns( table.getName() ) );
		
		// Separate the columns names
		separateColumnsNames( table.getColumnames() );
		
		// Return the table
		return table;
	}



	
	/**
	 * Method that adds the splitted name for each column name
	 * to the object where it is saved.
	 * @param columnames The list of columnames to get separated.
	 */
	private void separateColumnsNames(LinkedList<ColumnName> columnames) {
		// For each columname
		for( ColumnName cn : columnames ) {
			// Get the separated word
			LinkedList<String> separated = SeparationDAO.separate(cn.getName(), separation);
			
			// Now add the separated name
			cn.addSplittedName(separated);
		}
	}



	/**
	 * Getter for the separation method.
	 * @return Gets a separation type.
	 */
	public Separation getSeparation() {
		return separation;
	}





}
