package poj.test;
import poj.PackedVector;
import org.junit.Test;
import static org.junit.Assert.*;
public class UnitTest
{
	@Test public void trivialTest()
	{
		PackedVector<Integer> a = new PackedVector<Integer>(10);
		assertEquals(a.get_packed_data().size(), 0);
		// assertEquals(a.get_packed_indicies(), 0);
		assertEquals(a.get_sparse_vector().size(), 10);
		assertEquals(a.get_packed_indicies().size(), 0);
	}
	@Test public void addingInPackedVector()
	{
		PackedVector<Integer> a = new PackedVector<Integer>(10);
		a.add_element_at_sparse_vector(5, 38);
		// sparse_vector and packed_indicies should point with each
		// other!!
		assertEquals(Integer.valueOf(a.get_packed_data().get(0)),
			     Integer.valueOf(38));
		// assertEquals(Integer.a.get_sparse_vector().get(5), 0);
		// assertEquals(a.get_packed_indicies().get(0), 5);
	}
}
