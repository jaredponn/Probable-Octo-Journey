package poj.EntitySet;

import poj.Component.Component;


/**
 * EntitySet -- a way to contain an entity set for easy insertion of new
 * entities in the game engine. Please see src/EntitySets/ExamplePlayerSet.java
 * for a usage example.
 * Date: February 20, 2019
 * @author Jared
 * @version 1.0
 */
public class EntitySet implements Component
{
	private EntitySetType entityRunTimeTypeRep;
	private EntitySetMemberComponents entityMemberVariables;

	/**
	 * print method for the entityRunTimeTypeRep. Helpful for debugging
	 */
	public void print()
	{
		entityRunTimeTypeRep.print();
	}

	/**
	 * Constructs an entity set. Default adds its own type to the
	 * EntityMEmberSetComponents.
	 */
	public EntitySet()
	{
		entityRunTimeTypeRep = new EntitySetType(this.getClass());
		entityMemberVariables =
			new EntitySetMemberComponents(entityRunTimeTypeRep);
	}

	/**
	 * Adds a component of type U
	 * @param a new component to be added
	 */
	public <U extends Component> void addComponent(U a)
	{
		entityMemberVariables.addComponentToSet(a);
	}


	/**
	 * gets Javas runtime rep of the type
	 * @param a new component to be added
	 * @return java's run time rep of the type
	 */
	final public Class<?> getEntitySetType()
	{
		return entityRunTimeTypeRep.entityRunTimeTypeRep;
	}


	/**
	 * gets Javas runtime rep of the type
	 * @return returns the HList of the components
	 */
	final public EntitySetMemberComponents getComponents()
	{
		return entityMemberVariables;
	}
}
