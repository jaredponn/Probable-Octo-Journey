package poj.EntitySet;

import poj.Component.Component;

/**
 * Wrapper for Java's run time rep of the types
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
