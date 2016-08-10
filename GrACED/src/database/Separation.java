package database;

import database.enums.SeparationChars;
import database.enums.WordSeparation;




/**
 * Singleton class that contains all the information about
 * how to separate the words. It is a singleton class.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class Separation {
	private static Separation instance = new Separation();
	
	private WordSeparation type;
	private SeparationChars characters;
	private boolean deletePrefix;
	private String prefix;
	private WordSeparation extraSeparation;
	private SeparationChars extraChars;

	
	
	/**
	 * Default constructor of the class.
	 */
	private Separation() { }
	
	
	/**
	 * Getter method to get the instance, given that it is a singleton class.
	 * @return The current and only instance of the class.
	 */
	public static Separation getInstance() {
		return instance;
	}

	

	/**
	 * Getter for the type of separation.
	 * @return a WordSeparation enumarated class.
	 */
	public WordSeparation getType() {
		return type;
	}

	
	/**
	 * Getter for the separation chars.
	 * @return a SeparationChars enumerated class.
	 */
	public SeparationChars getCharacters() {
		return characters;
	}
	
	
	/**
	 * Getter to know if the separation needs a prefix or not.
	 * @return @true if needs to delete a prefix, or @false
	 * in the other hand.
	 */
	public boolean needsDeletePrefix() {
		return deletePrefix;
	}

	
	/**
	 * Getter for the prefix to be deleted.
	 * @return a String with the prefix.
	 */
	public String getPrefix() {
		return prefix;
	}
	
	
	/**
	 * If the main type is a mixed one, this is the extra
	 * separation type that will be used.
	 * @return a WordSeparation enumerated instance.
	 */
	public WordSeparation getExtraSeparation() {
		return extraSeparation;
	}
	
	
	/**
	 * If after the prefix we have a character separation, this
	 * is the character that would be used.
	 * @return
	 */
	public SeparationChars getExtraChars() {
		return extraChars;
	}
	
	
	
	/**
	 * Setter for adding a the separation chars if the type
	 * is Character.
	 * @param c The character to be added.
	 */
	public void setCharacters(SeparationChars c) {
		characters = c;
	}

	
	/**
	 * Setter for the main type of separation.
	 * @param t The type to be setted.
	 */
	public void setType(WordSeparation t) {
		type = t;
	}

	
	/**
	 * Setter for the boolean, in case it is a mixed type
	 * of separation, and it needs to get a prefix deleted.
	 * @param d @true if a prefix needs to be deleted, and
	 * @false in the other hand.
	 */
	public void setDeletePrefix(boolean d) {
		deletePrefix = d;
	}

	
	/**
	 * Setter for the prefix that needs to be deleted.
	 * @param p The String with the prefix to be deleted.
	 */
	public void setPrefix(String p) {
		prefix = p;
	}

	
	/**
	 * Setter for the extra separation type, if the main one
	 * is a Mixed type.
	 * @param e The extra type to set.
	 */
	public void setExtraSeparation(WordSeparation e) {
		extraSeparation = e;
	}

	
	/**
	 * Setter for the extra chars, if the extra separation type
	 * is characters.
	 * @param e The separation to be setted up.
	 */
	public void setExtraChars(SeparationChars e) {
		extraChars = e;
	}
}
