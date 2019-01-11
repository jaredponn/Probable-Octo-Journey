package poj.test;
import poj.Component;
import org.junit.Test;
import static org.junit.Assert.*;
public class UnitTest
{
	@Test public void test1()
	{
		Component<Integer> a = new Component<Integer>(10);
		assertEquals(a.get_packed_data().size(), 0);
		// assertEquals(a.get_packed_indicies(), 0);
		assertEquals(a.get_sparse_vector().size(), 0);
		assertEquals(a.get_packed_indicies().size(), 0);
	}
}
