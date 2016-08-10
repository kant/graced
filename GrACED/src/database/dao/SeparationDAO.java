package database.dao;



import java.util.LinkedList;
import database.Separation;
import database.enums.WordSeparation;




/**
 * DAO class with several static methods that allows to split a word
 * using all the entered information.
 * @author Ing. Melina C. Vidoni - 2014
 *
 */
public class SeparationDAO {
	
	
	
	/**
	 * Main method that selects which word separation will be used.
	 * @param name The word to separate.
	 * @param sep The separation information.
	 * @return The separated word.
	 */
	public static LinkedList<String> separate(String name, Separation sep) {
		// If it is pascal or camel, call the method
		if( sep.getType().equals( WordSeparation.PASCAL_CASE ) || sep.getType().equals( WordSeparation.CAMEL_CASE )) {
			// Call and return
			return separateCamelPascal(name);
		}
		
		// Else, if it is a character, call it, too
		else if( sep.getType().equals( WordSeparation.SPECIAL_CHARACTER ) ) {
			// Call and return
			return separateCharacter(name, sep.getCharacters().toString());
		}
		
		// Else, if it is a mixed one...
		else if( sep.getType().equals( WordSeparation.PREFIX_CASING ) ) {
			// If we need to remove a prefix
			if( sep.needsDeletePrefix() ) {
				// Send it...
				return separateMixed( name.replace(sep.getPrefix(), ""), sep);
			}
			
			// If we don't need to remove a prefix
			else {
				return separateMixed(name, sep);
			}
		}
		
		// The default case
		return new LinkedList<String>();
	}

	
	
	
	
	/**
	 * Method that separates using a mixed separation.
	 * @param name The word to be splitted.
	 * @param sep All other splitting information.
	 * @return The already splitted word.
	 */
	private static LinkedList<String> separateMixed(String name, Separation sep) {
		// Now check the other separation type
		if( sep.getExtraSeparation().equals( WordSeparation.PASCAL_CASE ) 
				|| sep.getExtraSeparation().equals( WordSeparation.CAMEL_CASE )) {
			
			// return direclty from their method
			return separateCamelPascal( name );
		}
		
		// Else, if it needs a new character
		else if( sep.getExtraSeparation().equals( WordSeparation.SPECIAL_CHARACTER ) ) {
			// Return directly from this other method
			return separateCharacter( name, sep.getExtraChars().toString() );
		}
		
		// If all fails, return empty
		return new LinkedList<String>();
	}




	
	/**
	 * Method that separates a word by a special character.
	 * @param name The word to be separated.
	 * @param character The special character to be used.
	 * @return The splitted word.
	 */
	private static LinkedList<String> separateCharacter(String name, String character) {
		// First, get the pattern
		String[] splitted = name.split(character);

		// Prepare the list
		LinkedList<String> list = new LinkedList<String>();
		
		// Now go through each of them
		for(int i=0; i<splitted.length; i++) {
			
			// And add them to the list
			list.add( splitted[i].toLowerCase() );
		}
		
		// Return the new list
		return list;
	}



	
	/**
	 * Method that separates the word using camel or pascal casing.
	 * @param name The word to be separated.
	 * @return The already splitted word.
	 */
	private static LinkedList<String> separateCamelPascal(String name) {
		// First, get the pattern
		String[] splitted = name.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

		// Prepare the list
		LinkedList<String> list = new LinkedList<String>();
		
		// Now go through each of them
		for(int i=0; i<splitted.length; i++) {
			// And add them to the list
			list.add( splitted[i].toLowerCase() );
		}
		
		// Return the new list
		return list;
	}
	
	
	

}
