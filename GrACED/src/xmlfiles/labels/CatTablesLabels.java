package xmlfiles.labels;



/**
 * Enumerated class for the labels on a BOW file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum CatTablesLabels {
	ROOT("tns:root"),
	TABLE("tns:tables"),
	TABLE_NAME("tns:tableName"),
	CLASS("tns:class"),
	TABLE_CLASS("tns:tableClassification"),
	COLUMN_CLASS("tns:columnClassification"),
	CLASS_NAME("tns:className"),
	CLASS_PERCENT("tns:classPercent"),
	COLUMN("tns:column"),
	COLUMN_NAME("tns:columnName");
	
	private String label;

	
	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	CatTablesLabels(String l) {
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
	public static CatTablesLabels getLabel(String n){
		if(ROOT.getLabel().equals(n))
			return ROOT;
		else if(TABLE.getLabel().equals(n))
			return TABLE;
		else if(TABLE_NAME.getLabel().equals(n))
			return TABLE_NAME;
		else if(CLASS.getLabel().equals(n))
			return CLASS;
		else if(TABLE_CLASS.getLabel().equals(n))
			return TABLE_CLASS;
		else if(COLUMN_CLASS.getLabel().equals(n))
			return COLUMN_CLASS;
		else if(CLASS_NAME.getLabel().equals(n))
			return CLASS_NAME;
		else if(CLASS_PERCENT.getLabel().equals(n))
			return CLASS_PERCENT;
		else if(COLUMN.getLabel().equals(n))
			return COLUMN;
		else if(COLUMN_NAME.getLabel().equals(n))
			return COLUMN_NAME;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
