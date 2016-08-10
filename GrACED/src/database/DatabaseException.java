package database;


/**
 * Exception used when there is problems with the database, related
 * to connection, non existent tables, bad named columns, and so on.
 * This does not consider exceptions SQL-like.
 * @author Ing. Melina C. Vidoni - 2013
 */

public class DatabaseException extends Exception {
	private static final long serialVersionUID = 4601636233297970132L;
	
	/**
	 * Default constructor.
	 * @param msg Used error message.
	 */
	public DatabaseException(String msg){
		super(msg);
	}

}
