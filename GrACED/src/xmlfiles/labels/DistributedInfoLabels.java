package xmlfiles.labels;



/**
 * Enumerated class for the labels on a Distributed Information file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum DistributedInfoLabels {
	ROOT("tns:root"),
	TOTAL_TABLES("tns:totalTables"),
	ANSI_CLASS("tns:ansiClass"),
	AVERAGE_PERCENT("tns:averagePercent"),
	DEFAULT_PROPORTION("tns:defaultProportion"),
	MAX_PERCENT("tns:maxPercent"),
	CLASS_NAME("tns:className"),
	MIN_PERCENT("tns:minPercent"),
	TABLE_QTY("tns:tableQty"),
	TABLE_NAME("tns:tableName"),
	TABLE_PERCENT("tns:tpercent"),
	TABLE("tns:table");
	
	private String label;

	
	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	DistributedInfoLabels(String l) {
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
	public static DistributedInfoLabels getLabel(String n){
		if(ROOT.getLabel().equals(n))
			return ROOT;
		else if(TOTAL_TABLES.getLabel().equals(n))
			return TOTAL_TABLES;
		else if(ANSI_CLASS.getLabel().equals(n))
			return ANSI_CLASS;
		else if(AVERAGE_PERCENT.getLabel().equals(n))
			return AVERAGE_PERCENT;
		else if(DEFAULT_PROPORTION.getLabel().equals(n))
			return DEFAULT_PROPORTION;
		else if(MAX_PERCENT.getLabel().equals(n))
			return MAX_PERCENT;
		else if(CLASS_NAME.getLabel().equals(n))
			return CLASS_NAME;
		else if(MIN_PERCENT.getLabel().equals(n))
			return MIN_PERCENT;
		else if(TABLE_QTY.getLabel().equals(n))
			return TABLE_QTY;
		else if(TABLE_NAME.getLabel().equals(n))
			return TABLE_NAME;
		else if(TABLE_PERCENT.getLabel().equals(n))
			return TABLE_PERCENT;
		else if(TABLE.getLabel().equals(n))
			return TABLE;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
