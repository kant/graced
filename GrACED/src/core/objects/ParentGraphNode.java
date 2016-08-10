package core.objects;


/**
 * Class with the information of a parent of another node, and the relations between them
 * that were saved originaly on the Relation.xml file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class ParentGraphNode {
	private String name;
	private double propX;
	private double propYgivenX;
	private double propXgivenYforTable;
	
	
	
	/**
	 * Default constructor of the class.
	 * @param n Value for the name.
	 * @param px Value for the proportion of X.
	 * @param pyx Value for the proportion of Y given X.
	 */
	public ParentGraphNode(String n, double px, double pyx) {
		name = n;
		propX = px;
		propYgivenX = pyx;
		propXgivenYforTable = 0.0;
	}



	/**
	 * Getter for the name of the parent node.
	 * @return The current name of the instance.
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Setter to change the name of the instance.
	 * @param n The name that will replace the old one.
	 */
	public void setName(String n) {
		name = n;
	}

	
	/**
	 * Getter to get the proportion of occurences of x.
	 * @return The proportion of X.
	 */
	public double getPropX() {
		return propX;
	}

	
	/**
	 * Setter to set a new proportion of occurence for X.
	 * @param pX The double value of the new proportion.
	 */
	public void setPropX(double pX) {
		propX = pX;
	}

	
	/**
	 * Getter to get the proportion of Y given X.
	 * @return The double value of said proportion.
	 */
	public double getPropYgivenX() {
		return propYgivenX;
	}

	
	/**
	 * Setter to change the value of the proportion of Y given X.
	 * @param pYX The new value to be changed.
	 */
	public void setPropYgivenX(double pYX) {
		propYgivenX = pYX;
	}


	/**
	 * Getter of the proportion of X given Y for a said table. This can be
	 * considered as a getter for the bayes value calculated, because this
	 * is the left side of the bayes equation.
	 * @return The value of the left side of bayes' equation.
	 */
	public double getPropXgivenYforTable() {
		return propXgivenYforTable;
	}

	
	/**
	 * Setter to save the value after externally calculating the bayes' equation.
	 * @param pxyt The new value to be saved.
	 */
	public void setPropXgivenYforTable(double pxyt) {
		propXgivenYforTable = pxyt;
	}
}
