package core;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.reactive.ReactiveBasedAgent;




/**
 * Main class representing the agent that has all the knowledge
 * to make the classification work.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class GracedAgent extends ReactiveBasedAgent {
	private boolean categorized = false;

	
	
	/**
	 * Default constructor of the class.
	 */
	public GracedAgent() {
		// Call the super method
		super();
		
		// Lets set a new agent state
		this.setAgentState( new GracedAgentState() );
	}
	
	
	
    /**
     * This method receive perceptions from the simulator.
     * @param p The received perception.
     */
	@Override
	public void see(Perception p) {
		// Get the state and throw it the perception
		this.getAgentState().updateState( p );
	}
	
	
	
	
	/**
	 * Returns the state of the agent.
	 * @return already casted state of the agent.
	 */
    public GracedAgentState getAgentState() {
    	// Get the state using the paren'ts method and cast it
        return (GracedAgentState) super.getAgentState();
    }
	
	
	
    /**
     * This is a method executed by the simulator to ask the agent for an
     * action.
     * @return The action chosen by the agent.
     */
	@Override
	public Action selectAction() {
		// If it itsn't categorized
		if( !categorized ) {
			// Change the value and send the classifying action
			categorized = true;
			return new ClassifyAction();
		}
		// In the other hand
		else {
			// Send the Hyphenation action...
			categorized = false;
			return new HyphenationAction();
		}
	}

	
	
}
