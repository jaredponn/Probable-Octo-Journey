import Components.Render;
import Components.Physics;

import poj.ComponentList;
import poj.PackedVector;
import poj.HList.*;
import poj.EntitySetMemberComponents;
// https://sourcemaking.com/design_patterns/visitor/java/1


public class Main
{
	public static final void main(String[] args)
	{

		// HCons<Integer, HCons<String, HNil>> c = HList.hcons(
		HList<HCons<Integer, HCons<String, HNil>>> c = HList.hcons(
			new Integer(3), HList.hcons("asdf", HList.hnil()));


		HTypeVisitor down = new HTypeVisitor();
		HList<HNil> a = HList.hnil();
		// System.out.println(c.head());
		// System.out.println(c.tail().head());
		// System.out.println(c.tail().tail());

		EntitySetMemberComponents f =
			new EntitySetMemberComponents(new Render(3, 3));
		f.addComponentToSet(new Physics(3));
		f.addComponentToSet(new Physics(9));
		f.printSet();


		c.accept(down);
	}
}
