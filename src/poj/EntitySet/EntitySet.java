package poj.EntitySet;

import poj.Component;

public class EntitySet implements Component
{
	private EntitySetType entityRunTimeTypeRep;
	private EntitySetMemberComponents entityMemberVariables;

	public void print()
	{
		entityRunTimeTypeRep.print();
	}

	public EntitySet()
	{
		entityRunTimeTypeRep = new EntitySetType(this.getClass());
		entityMemberVariables.addComponentToSet(entityRunTimeTypeRep);
	}

	public <U extends Component> void addComponent(U a)
	{
		entityMemberVariables.addComponentToSet(a);
	}

	final public EntitySetType getEnttiySetType()
	{
		return entityRunTimeTypeRep;
	}

	final public EntitySetMemberComponents getComponents()
	{
		return entityMemberVariables;
	}
}
