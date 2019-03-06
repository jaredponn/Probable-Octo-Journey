package poj.EntitySet;

import poj.Component.Component;

/**
 * EntitySetType -- wrapper for Java's run time rep of the types. Should not be
 * used externally. Date: February 20, 2019
 * @author Jared
 * @version 1.0
 */

public class EntitySetType implements Component
{
	public Class<?> entityRunTimeTypeRep;


	/**
	 * Constructor
	 * @param a type of the object
	 */
	EntitySetType(Class<?> a)
	{
		entityRunTimeTypeRep = a;
	}

	/**
	 * Prints the run time rep of the object
	 */
	public void print()
	{
		System.out.println(this.getClass());
	}
}
