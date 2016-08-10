package xmlfiles.labels;



/**
 * Enumerated class for the labels on a unclassified tables
 * results file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum UncatTablesLabels {
	ROOT("tns:root"),
	TABLE("tns:table"),
	TABLE_NAME("tns:tableName");
	
	private String label;

	
	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	UncatTablesLabels(String l) {
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
	public static UncatTablesLabels getLabel(String n){
		if(ROOT.getLabel().equals(n))
			return ROOT;
		else if(TABLE.getLabel().equals(n))
			return TABLE;
		else if(TABLE_NAME.getLabel().equals(n))
			return TABLE_NAME;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
