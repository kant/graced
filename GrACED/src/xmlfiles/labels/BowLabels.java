package xmlfiles.labels;



/**
 * Enumerated class for the labels on a BOW file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum BowLabels {
	BOW("tns:bow"),
	BOW_NAME("tns:bowName"),
	WORD("tns:word"),
	WORD_NAME("tns:word"),
	SYNONYMS("tns:hasSynonyms"),
	FILE("tns:synonymsFile"),
	WEIGHT("tns:wordWeight");
	
	private String label;

	
	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	BowLabels(String l) {
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
	public static BowLabels getLabel(String n){
		if(BOW.getLabel().equals(n))
			return BOW;
		else if(WORD.getLabel().equals(n))
			return WORD;
		else if(WORD_NAME.getLabel().equals(n))
			return WORD_NAME;
		else if(SYNONYMS.getLabel().equals(n))
			return SYNONYMS;
		else if(FILE.getLabel().equals(n))
			return FILE;
		else if(WEIGHT.getLabel().equals(n))
			return WEIGHT;
		else if(BOW_NAME.getLabel().equals(n))
			return BOW_NAME;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
