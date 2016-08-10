package core;

import database.objects.TableName;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;




/**
 * Class that has all the means to make the agent get a perception
 * from the environment it is in. Implements @see Perception from
 * the FAIA framwork.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class TablenamePerception extends Perception {
	private TableName tablename;
	private int index;
	
	
	
	/**
	 * Default constructor of the class.
	 */
	public TablenamePerception(TableName t) {
		// Add the tablename
		tablename = t; 
		
		// Set the index
		index = 0;
	}


	/**
	 * Returns the instance in a String format.
	 */
	@Override
    public String toString(){
    	return tablename.getName();
    }
	
	
	
	/**
	 * Returns the current tablename of the Perception.
	 * @return this 
	 */
	public TableName getTablename() {
		return tablename;
	}


	@Override
	public void initPerception(Agent agent, Environment environment) {
		// TODO initPerception Graced
	}


	/**
	 * Getter for index variable.
	 * @return The index of this perception.
	 */
	public int getIndex() {
		return index;
	}


	/**
	 * Setter for the index variable.
	 * @param index Number to be saved on the variable.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	

}
