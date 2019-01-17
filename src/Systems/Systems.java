package Systems;

import Components.*;

public class Systems
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
