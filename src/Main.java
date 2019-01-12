import Components.Render;
import Components.Physics;
import poj.ComponentList;
import poj.PackedVector;

import java.util.ArrayList;


public class Main
{

	public static final void main(String[] args)
	{


		Physics a = new Physics(2);
		Render b = new Render(1, 2);
		ComponentList test = new ComponentList();

		test.registerComponent(a.getClass());
		test.registerComponent(b.getClass());

		PackedVector tmp = test.getComponent(a.getClass());
		tmp.add_element_at_sparse_vector(1, new Physics(1));

		System.out.println(
			tmp.get_data_from_sparse_vector(1).getClass());
	}
}
