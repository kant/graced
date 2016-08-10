package core;

import java.util.LinkedList;

import database.objects.Category;
import database.objects.TableName;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.reactive.ReactiveBasedAgentState;





/**
 * State class of the Graced Agent that will keep all the information
 * in one place, and will be updated when it does something that matters.
 * Implements @see ReactiveBasedAgentState from FAIA framework.
 * @author Ing. Melina C. Vidoni
 *
 */
public class GracedAgentState extends ReactiveBasedAgentState {
	private String ansiStandard;
	
	private TableName currentTablename;
	private LinkedList<Category> temporalClassification;
	
	private int currentCategIndex;
	
	
	
	
	/**
	 * Default constructor of the class.
	 */
	public GracedAgentState() {
		// Set the route to the graph
		ansiStandard = "files/Graph.xml";
		currentCategIndex = 0;
		
		// Call the initialization method
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
		// Clean everything
		currentTablename = new TableName("");
		temporalClassification = new LinkedList<Category>();
	}
	
	
	
	/**
	 * Sets the current tablename to be classified.
	 * @param ct Current tablename.
	 */
	public void setCurrentTablename(TableName ct) {
		currentTablename = ct;
	}
	
	
	/**
	 * Method that adds a new classification to the list.
	 * @param tp The classification to be added.
	 */
	public void addClassification(Category tp) {
		temporalClassification.add( tp );
	}
	
	

	
    /**
     * This method updates the agent's state when receive a perception
     * from the simulator. 
     */
	@Override
	public void updateState(Perception p) {
		// Cast the perception
		TablenamePerception tp = (TablenamePerception) p;
		
		// Add the info
		currentTablename = tp.getTablename();
		currentCategIndex = tp.getIndex();
		temporalClassification = new LinkedList<Category>();
	}

	
	
    /**
     * This method is used to print the state in the console.
     */
	@Override
	public String toString() {
		// Returns the state on a string format
		return "Analizing table: " + currentTablename.getName() 
				+ " with " + temporalClassification.size() + " temporal categories.";
	}

	
	
	


	public String getAnsiStandard() {
		return ansiStandard;
	}

	public int getCurrentCategIndex() {
		return currentCategIndex;
	}

	public TableName getCurrentTablename() {
		return currentTablename;
	}

	public LinkedList<Category> getTemporalClassification() {
		return temporalClassification;
	}






}
