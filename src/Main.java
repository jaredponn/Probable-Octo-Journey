import Components.Render;
import Components.Physics;

import poj.ComponentList;
import poj.PackedVector;
import poj.HList.*;

import java.util.ArrayList;


public class Main
{

	public static final void main(String[] args)
	{


		Physics a = new Physics(2);
		Render b = new Render(1, 2);
		ComponentList test = new ComponentList();

		test.registerComponent(Physics.class);
		test.registerComponent(Render.class);

		{

			test.getComponent(Physics.class)
				.add_element_at_sparse_vector(0,
							      new Physics(1));

			// notice this is an error!
			//	test.getComponent(Physics.class)
			//		.add_element_at_sparse_vector(0,
			//					      new
			// Render(1, 2));
		}

		{

			PackedVector<Render> tmp =
				(PackedVector<Render>)test.getComponent(
					b.getClass());
			tmp.add_element_at_sparse_vector(1, new Render(1, 3));

			System.out.println(
				tmp.get_data_from_sparse_vector(1).getClass());
		}

		HCons<Integer, HCons<String, HNil>> c = HList.hcons(
			new Integer(3), HList.hcons("asdf", HList.hnil()));
		System.out.println(c.head());
		System.out.println(c.tail().head());
		System.out.println(c.tail().tail());
	}
}
