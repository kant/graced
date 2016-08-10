package xmlfiles.labels;



/**
 * Enumerated class for the labels on a Synonym file.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public enum SynonymLabels {
	SYNONYM("tns:synonym"),
	MAIN_WORD("tns:mainWord"),
	WORD_NAME("tns:word"),
	ORIGINAL("tns:originalWord");
	
	private String label;

	
	
	
	/**
	 * Default constructor of the class.
	 * @param l A name.
	 */
	SynonymLabels(String l) {
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
	public static SynonymLabels getLabel(String n){
		if(SYNONYM.getLabel().equals(n))
			return SYNONYM;
		else if(MAIN_WORD.getLabel().equals(n))
			return MAIN_WORD;
		else if(WORD_NAME.getLabel().equals(n))
			return WORD_NAME;
		else if(ORIGINAL.getLabel().equals(n))
			return ORIGINAL;
		return null;
	}
	
	
	
	/**
	 * Method that converts the current enum on a string.
	 */
	public String toString() {
		return getLabel();
	}
	
}
