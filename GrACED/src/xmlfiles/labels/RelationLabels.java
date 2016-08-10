package xmlfiles.labels;





/**
 * Enumerated class for the labels on the Relation.xml file.
 * @author Ing. Melina C. Vidoni - 2014
 * @see RelationSchema.xsd, Relation.xml
 *
 */
public enum RelationLabels {
	ROOT("tns:root"),
	CHILD_NODE("tns:childNode"),
	NODE_NAME("tns:nodeName"),
	PARENT_NODE("tns:parentNode"),
	PROP_X("tns:propX"),
	PROP_Y_GIVEN_X("tns:propYgivenX");
	
	private String label;

	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	RelationLabels(String l) {
		label = l;
	}
	
	/**
	 * Getter method for the label in xml.
	 * @return The xml label.
	 */
	public String getLabel() {
		return label;
	}
	
	

	
	
	/**
	 * Method that allows the system to get a enum that matches with the received label.
	 * @param n The label to be compared with.
	 * @return The enum object if it exists, or a null object.
	 */
	public static RelationLabels getLabel(String n){
		if(ROOT.getLabel().equals(n))
			return ROOT;
		else if(CHILD_NODE.getLabel().equals(n))
			return CHILD_NODE;
		else if(NODE_NAME.getLabel().equals(n))
			return NODE_NAME;
		else if(PARENT_NODE.getLabel().equals(n))
			return PARENT_NODE;
		else if(PROP_X.getLabel().equals(n))
			return PROP_X;
		else if(PROP_Y_GIVEN_X.getLabel().equals(n))
			return PROP_Y_GIVEN_X;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
