package xmlfiles.labels;





/**
 * Enumerated class for the labels on the Graph file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum GraphLabels {
	ROOT("tns:root"),
	NODE("tns:node"),
	NODE_NAME("tns:nodeName"),
	COLUMN_BOW("tns:columnameBow"),
	TABLE_BOW("tns:tablenameBow"),
	USABLE("tns:usable");
	
	private String label;

	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	GraphLabels(String l) {
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
	public static GraphLabels getLabel(String n){
		if(ROOT.getLabel().equals(n))
			return ROOT;
		else if(NODE.getLabel().equals(n))
			return NODE;
		else if(NODE_NAME.getLabel().equals(n))
			return NODE_NAME;
		else if(COLUMN_BOW.getLabel().equals(n))
			return COLUMN_BOW;
		else if(TABLE_BOW.getLabel().equals(n))
			return TABLE_BOW;
		else if(USABLE.getLabel().equals(n))
			return USABLE;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
