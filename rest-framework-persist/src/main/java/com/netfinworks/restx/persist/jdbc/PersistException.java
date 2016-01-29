/**
 * 
 */
package com.netfinworks.restx.persist.jdbc;

/**
 * @author bigknife
 *
 */
public class PersistException extends RuntimeException {

	private static final long serialVersionUID = 8878699717574908551L;

	/**
	 * 
	 */
	public PersistException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public PersistException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public PersistException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public PersistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
