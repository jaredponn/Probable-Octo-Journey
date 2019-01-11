package poj.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import poj.Component;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestComponent
{
	public static void main(String[] args)
	{
		Result result = JUnitCore.runClasses(UnitTest.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.getFailureCount());
		System.out.println(result.wasSuccessful());
	}
}
class UnitTest
{
	@Test void test1()
	{
		Component<Integer> a = new Component<Integer>(10);
		assertEquals(a.get_packed_data().size(), 0);
		assertEquals(a.get_packed_indicies(), 0);
		assertEquals(a.get_sparse_vector().size(), 0);
		assertEquals(a.get_packed_indicies().size(), 0);
	}
}
