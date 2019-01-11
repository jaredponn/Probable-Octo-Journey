package poj.test;

<<<<<<< HEAD
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import poj.Component;
=======
>>>>>>> 4de8033b16b862b12101f941acbe81cc49f091eb
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
