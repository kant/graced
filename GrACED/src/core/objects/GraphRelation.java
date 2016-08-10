package core.objects;

import java.util.LinkedList;



/**
 * Class with the information about a category and their relation with its
 * parent nodes on the ANSI/ISA-95 graph of the knowledge base of GrACED.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class GraphRelation {
	private String name;
	private double propX;
	private LinkedList<ParentGraphNode> parentList;
	
	
	
	/**
	 * Default constructor of the class.
	 * @param n The name of the node.
	 * @param px The proportion of X of the node.
	 */
	public GraphRelation(String n, double px) {
		parentList = new LinkedList<ParentGraphNode>();
		name = n;
		propX = px;

	}


	/**
	 * Getter for the name of the relation.
	 * @return The name of the relation in this instance.
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Setter for the name of the relation in graph form.
	 * @param n The custom name to be saved.
	 */
	public void setName(String n) {
		name = n;
	}

	
	/**
	 * Getter to get the proportion of x.
	 * @return The double value saved as the proportion of X.
	 */
	public double getPropX() {
		return propX;
	}

	
	/**
	 * Setter to change the value of the proportion of x.
	 * @param px The new value for the proportion of x.
	 */
	public void setPropX(double px) {
		propX = px;
	}

	
	/**
	 * Getter for the parent graph node list.
	 * @return The whole list of parent nodes.
	 */
	public LinkedList<ParentGraphNode> getParentList() {
		return parentList;
	}

	
	/**
	 * Setter to add a new parent node to the end of the parent list.
	 * @param parent The new parent to be added.
	 */
	public void addParent(ParentGraphNode parent) {
		parentList.addLast(parent);
	}

	

	/**
	 * Method that evaluates and gets the bigger belonging this
	 * class has to a given parent category, without identifying
	 * the category it is.
	 * @return The belonging proportion to a parent category.
	 */
	public double getBiggerBayes() {
		// Make a flag
		double bayes = 0.0;
		
		// Go trough each parent
		for(ParentGraphNode pgn : parentList) {
			// Compare the bayes
			if( pgn.getPropXgivenYforTable() > bayes ) {
				// Save it
				bayes = pgn.getPropXgivenYforTable();
			}
		}
		
		// Then return it
		return bayes;
	}
	

}
