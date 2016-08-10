package xmlfiles.labels;



/**
 * Enumerated class for the labels on the word separation result file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum HyphenationLabels {
	ROOT("tns:root"),
	BAD_SEPARATION("tns:badSeparation"),
	GOOD_SEPARATION("tns:goodSeparation"),
	TABLE_NAMES("tns:tableNames"),
	COLUMN_NAMES("tns:columnNames"),
	NAME("tns:name"),
	WORD("tns:word");
	
	private String label;

	
	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	HyphenationLabels(String l) {
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
	public static HyphenationLabels getLabel(String n){
		if(ROOT.getLabel().equals(n))
			return ROOT;
		else if(GOOD_SEPARATION.getLabel().equals(n))
			return GOOD_SEPARATION;
		else if(TABLE_NAMES.getLabel().equals(n))
			return TABLE_NAMES;
		else if(COLUMN_NAMES.getLabel().equals(n))
			return COLUMN_NAMES;
		else if(NAME.getLabel().equals(n))
			return NAME;
		else if(WORD.getLabel().equals(n))
			return WORD;
		else if(BAD_SEPARATION.getLabel().equals(n))
			return BAD_SEPARATION;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
