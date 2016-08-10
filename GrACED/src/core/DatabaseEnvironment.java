package core;



import java.sql.SQLException;

import database.DatabaseException;
import database.objects.TableName;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;




/**
 * Main class that represents the environment of the agent. This is the
 * database that the agent needs to classify. Extends @see Environment
 * class from FAIA framework.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class DatabaseEnvironment extends Environment {
	
	
	/**
	 * Default constructor of the class.
	 */
	public DatabaseEnvironment() {
		// Add the Environment state
	    this.setEnvironmentState( new DatabaseEnvironmentState() );
	}
	


	/**
	 * Method that gets all the table names and adds it to the state.
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public void getTablenames() throws DatabaseException, SQLException {
		// Get the state
		DatabaseEnvironmentState des = (DatabaseEnvironmentState) this.getEnvironmentState();
		
		// Get the tablenames
		des.getAllTablenames();
	}

		
	
	/**
	 * Return the tablename count of the environment state.
	 * @return the quantity of unclassified tables.
	 */
	public int getTablenamesCount() {
		return ((DatabaseEnvironmentState) this.getEnvironmentState()).getTablenameCount();
	}
	
	
	
    /**
     * This method will return a perception made by the subclass of Environment
     * @return the perception obtained.
     */
	@Override
	public Perception getPercept() {	
		try {
			// Get the state
			DatabaseEnvironmentState des = (DatabaseEnvironmentState) this.getEnvironmentState();
			
			// Get the first tablename
			TableName table = des.getFirstTablename();
			
			// Create the perception
			TablenamePerception perception = new TablenamePerception(table);
			
			// Return the perception
			return perception;
		} 
		catch (DatabaseException e) {
			// TODO db exception
			e.printStackTrace();
		} 
		catch (SQLException e) {
			// TODO sql exception
			e.printStackTrace();
		}
		
		// In case something goes worng
		return null;
	}



}
