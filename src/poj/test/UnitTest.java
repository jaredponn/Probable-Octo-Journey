package poj.test;
import poj.Component;
import org.junit.Test;
import static org.junit.Assert.*;
public class UnitTest
{
	@Test public void trivialTest()
	{
		Component<Integer> a = new Component<Integer>(10);
		assertEquals(a.get_packed_data().size(), 0);
		// assertEquals(a.get_packed_indicies(), 0);
		assertEquals(a.get_sparse_vector().size(), 10);
		assertEquals(a.get_packed_indicies().size(), 0);
	}
	@Test public void addingInPackedVector()
	{
		Component<Integer> a = new Component<Integer>(10);
		a.add_element_at_sparse_vector(5, 38);
		// sparse_vector and packed_indicies should point with each
		// other!!
		assertEquals(a.get_packed_data().get(0), 38);
		assertEquals(a.get_sparse_vector().get(5), 0);
		assertEquals(a.get_packed_indicies().get(0), 5);
	}
}
