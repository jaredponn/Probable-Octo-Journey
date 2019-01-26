package EntityTransforms;

import Components.*;

public class EntitySetTransforms
{

	public static void printPhysics(Physics p)
	{
		System.out.println("physics print: " + p.a);
	}

	public static void incrementPhysics(Physics p)
	{
		p.a += 1;
	}

	public static void printRender(Render r)
	{
		System.out.println("redner print " + r);
	}
}
