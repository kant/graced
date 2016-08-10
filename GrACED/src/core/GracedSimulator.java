package core;



import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.environment.Environment;
import frsf.cidisi.faia.simulator.ReactiveBasedAgentSimulator;




/**
 * Main simulator of the agent behavior.
 * Implements @see ReactiveBasedAgentSimulator from FAIA framework.
 * @author Ing. Melina C. Vidoni - 2014.
 *
 */
public class GracedSimulator extends ReactiveBasedAgentSimulator {
	private int total;
	private int current;
	private String currentTablename;


	
	
	
	/**
	 * Default constructor of the class.
	 * @param environment Reference to the environment.
	 * @param agent Reference to the agent.
	 */
	public GracedSimulator(Environment environment, Agent agent) {
		// Lets call the parent's constructor
		super(environment, agent);
		
		// Add the reference
		currentTablename = "";
		total = this.getEnvironment().getTablenamesCount();
		current = 0;
	}

	
	/**
	 * Returns the class converted to string.
	 */
    @Override
    public String getSimulatorName() {
    	return "Simulador Gr.A.C.E.D";
    }
    
    
    
    
    @Override
    public void start() {   		
		// The environment generates a perception
		TablenamePerception percept = (TablenamePerception) this.getEnvironment().getPercept();
		percept.setIndex( current+1 );
		currentTablename = percept.getTablename().getName();	
		
		// The agent now sees the perception
		this.getAgent().see( percept );
		
		// Get the Classify Action
		ClassifyAction action = (ClassifyAction) this.getAgent().selectAction();
		action.execute( this.getAgent().getAgentState() , this.getEnvironment().getEnvironmentState() );

		// Then get the Hyphenation Action
		HyphenationAction haction = (HyphenationAction) this.getAgent().selectAction();
		haction.execute( this.getAgent().getAgentState() , this.getEnvironment().getEnvironmentState() );
		
		
		// Update the count
		current++;
    }
    
    
    
    /**
     * Returns the environment of this simulator.
     * @returns the database environment already casted.
     */
    public DatabaseEnvironment getEnvironment() {
    	return (DatabaseEnvironment) super.getEnvironment();
    }
    
    
    
    /**
     * Returns the agent of this simulator.
     * @return the one and only graced agent, already casted.
     */
    public GracedAgent getAgent() {
    	return (GracedAgent) super.getAgents().get(0);
    }
    
    
    
    /**
     * Method that evaluates if the agent failed.
     */
    @Override
    public boolean agentFailed(Action a) {
    	// TODO complete agentFailed method.
    	return false;
    }
    
    
    
    /**
     * This method is executed in the mail loop of the simulation when the
     * agent returns an action.
     * @param agent
     * @param action
     */
    @Override
    public void actionReturned(Agent agent, Action action) {
    	// TODO complete actionReturned method.
	}
    
    
    
    
    @Override
    public boolean agentSucceeded(Action a) {
    	// TODO complete agentSuceed
    	return (current == total);
    }

    
   
    /**
     * Getter for the current number of table being processed.
     * @return The index of the current table.
     */
    public int getCurrent() {
    	return current;
    }
    
    
    
    
    /**
     * Getter for the total number of tables.
     * @return The number of total tables in the database.
     */
    public int getTotal() {
    	return total;
    }
    
    
    public String getTablename() {
    	return currentTablename;
    }
}
