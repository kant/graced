package xmlfiles.labels;



/**
 * Enumerated class for the labels on a Types file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum ResultsTypesLabels {
	ROOT("tns:RootType"),
	TOTAL_QTY("tns:totalQty"),
	
	TYPES_TYPE("tns:type"),
	TYPE_NAME("tns:typeName"),
	
	TABLE_TYPE("tns:table"),
	TABLE_NAME("tns:tableName"),
	
	CATEGORY_TYPE("tns:category"),
	CATEGORY_NAME("tns:categoryName");
	
	private String label;

	
	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	ResultsTypesLabels(String l) {
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
	public static ResultsTypesLabels getLabel(String n){
		if(ROOT.getLabel().equals(n))
			return ROOT;
		else if(TOTAL_QTY.getLabel().equals(n))
			return TOTAL_QTY;
		else if(TYPES_TYPE.getLabel().equals(n))
			return TYPES_TYPE;
		else if(TYPE_NAME.getLabel().equals(n))
			return TYPE_NAME;
		else if(TABLE_TYPE.getLabel().equals(n))
			return TABLE_TYPE;
		else if(TABLE_NAME.getLabel().equals(n))
			return TABLE_NAME;
		else if(CATEGORY_TYPE.getLabel().equals(n))
			return CATEGORY_TYPE;
		else if(CATEGORY_NAME.getLabel().equals(n))
			return CATEGORY_NAME;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
