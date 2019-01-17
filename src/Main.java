import Components.Render;
import Components.Physics;

import poj.EngineState;
import Components.*;
import Systems.*;
import EntitySets.*;


public class Main
{
	public static final void main(String[] args)
	{
		EngineState test = new EngineState();

		test.registerComponent(Physics.class);
		test.registerComponent(Render.class);

		test.registerSet(A.class);
		test.registerSet(B.class);

		test.spawnEntitySet(new A());
		test.spawnEntitySet(new A());
		test.spawnEntitySet(new A());
		test.spawnEntitySet(new B());

		for (int i = test.components.getInitialSetIndex(A.class);
		     test.components.isValidEntity(i);
		     i = test.components.getNextSetIndex(A.class, i)) {
			Systems.printPhysics(test.components.getComponentAt(
				Physics.class, i));
		}

		System.out.println();

		for (int i = test.components.getInitialSetIndex(A.class);
		     test.components.isValidEntity(i);
		     i = test.components.getNextSetIndex(A.class, i)) {
			Systems.incrementPhysics(test.components.getComponentAt(
				Physics.class, i));
		}
		System.out.println();

		for (int i = test.components.getInitialSetIndex(A.class);
		     test.components.isValidEntity(i);
		     i = test.components.getNextSetIndex(A.class, i)) {
			Systems.printPhysics(test.components.getComponentAt(
				Physics.class, i));
		}

		System.out.println();

		for (int i = test.components.getInitialSetIndex(B.class);
		     test.components.isValidEntity(i);
		     i = test.components.getNextSetIndex(B.class, i)) {
			Systems.printPhysics(test.components.getComponentAt(
				Physics.class, i));
		}

		System.out.println();

		for (int i = test.components.getInitialSetIndex(B.class);
		     test.components.isValidEntity(i);
		     i = test.components.getNextSetIndex(B.class, i)) {
			Systems.incrementPhysics(test.components.getComponentAt(
				Physics.class, i));
		}

		for (int i = test.components.getInitialSetIndex(B.class);
		     test.components.isValidEntity(i);
		     i = test.components.getNextSetIndex(B.class, i)) {
			Systems.printPhysics(test.components.getComponentAt(
				Physics.class, i));
		}

		System.out.println();

		for (int i = test.components.getInitialSetIndex(B.class);
		     test.components.isValidEntity(i);
		     i = test.components.getNextSetIndex(B.class, i)) {
			Systems.printRender(test.components.getComponentAt(
				Render.class, i));
		}

		System.out.println();
	}
}
