package poj.EntitySet;

import poj.Component.Component;

public class EntitySetType implements Component
{
	public Class<?> entityRunTimeTypeRep;

	EntitySetType(Class<?> a)
	{
		entityRunTimeTypeRep = a;
	}

	public void print()
	{
		System.out.println(this.getClass());
	}
}
