package poj.test;

/**
 * Packed Vector tests
 *
 * date March 10, 2019
 * @author Haiyang He
 * @version 1.0
 */

import poj.PackedVector;
import org.junit.Test;
import static org.junit.Assert.*;
public class PackedVectorTest
{


	/**
	 * Constructor tests
	 */
	@Test public void trivialTest()
	{
		PackedVector<Integer> a = new PackedVector<Integer>(10);
		assertEquals(a.get_packed_data().size(), 0);
		// assertEquals(a.get_packed_indicies(), 0);
		assertEquals(a.get_sparse_vector().size(), 10);
		assertEquals(a.get_packed_indicies().size(), 0);
	}

	/**
	 * adding tests
	 */
	@Test public void addingInPackedVector()
	{
		PackedVector<Integer> a = new PackedVector<Integer>(10);
		a.add_element_at_sparse_vector(5, 38);
		// sparse_vector and packed_indicies should point with each
		// other!!
		assertEquals(Integer.valueOf(a.get_packed_data().get(0)),
			     Integer.valueOf(38));
		assertEquals(Integer.valueOf(a.get_sparse_vector().get(5)),
			     Integer.valueOf(0));
		assertEquals(Integer.valueOf(a.get_packed_indicies().get(0)),
			     Integer.valueOf(5));

		a.add_element_at_sparse_vector(9, 50);
		// sparse_vector and packed_indicies should point with each
		// other!!
		assertEquals(Integer.valueOf(a.get_packed_data().get(1)),
			     Integer.valueOf(50));
		assertEquals(Integer.valueOf(a.get_sparse_vector().get(9)),
			     Integer.valueOf(1));
		assertEquals(Integer.valueOf(a.get_packed_indicies().get(1)),
			     Integer.valueOf(9));


		a.add_element_at_sparse_vector(2, 88);
		// sparse_vector and packed_indicies should point with each
		// other!!
		assertEquals(Integer.valueOf(a.get_packed_data().get(2)),
			     Integer.valueOf(88));
		assertEquals(Integer.valueOf(a.get_sparse_vector().get(2)),
			     Integer.valueOf(2));
		assertEquals(Integer.valueOf(a.get_packed_indicies().get(2)),
			     Integer.valueOf(2));


		assertEquals(Integer.valueOf(a.get_packed_data().get(1)),
			     Integer.valueOf(50));
		assertEquals(Integer.valueOf(a.get_sparse_vector().get(9)),
			     Integer.valueOf(1));
		assertEquals(Integer.valueOf(a.get_packed_indicies().get(1)),
			     Integer.valueOf(9));
	}

	/**
	 *  get data from sparse vector
	 */
	@Test public void getDataFromSparseVector()
	{

		PackedVector<Integer> a = new PackedVector<Integer>(10);
		a.add_element_at_sparse_vector(1, 10);
		a.add_element_at_sparse_vector(5, 20);
		a.add_element_at_sparse_vector(9, 30);

		assertEquals(Integer.valueOf(a.get_data_from_sparse_vector(1)),
			     Integer.valueOf(10));
		assertEquals(Integer.valueOf(a.get_data_from_sparse_vector(5)),
			     Integer.valueOf(20));
		assertEquals(Integer.valueOf(a.get_data_from_sparse_vector(9)),
			     Integer.valueOf(30));
	}

	/**
	 *  deleting from sparse vector
	 */
	@Test public void DeletingInPackedVector()
	{

		PackedVector<Integer> a = new PackedVector<Integer>(10);
		a.add_element_at_sparse_vector(1, 10);
		a.add_element_at_sparse_vector(5, 20);
		a.add_element_at_sparse_vector(9, 30);

		a.delete_element_at_sparse_vector(5);
		assertEquals(Integer.valueOf(a.get_data_from_sparse_vector(9)),
			     Integer.valueOf(30));
		assertEquals(Integer.valueOf(a.get_packed_indicies().get(1)),
			     Integer.valueOf(9));


		assertEquals(Integer.valueOf(a.get_data_from_sparse_vector(1)),
			     Integer.valueOf(10));
		assertEquals(Integer.valueOf(a.get_packed_indicies().get(0)),
			     Integer.valueOf(1));
		assertEquals(Integer.valueOf(a.get_packed_data().get(0)),
			     Integer.valueOf(10));

		// assertEquals(Integer.valueOf(a.get_data_from_sparse_vector(5)),
		// Integer.valueOf(-1));
		a.delete_element_at_sparse_vector(1);

		assertEquals(Integer.valueOf(a.get_data_from_sparse_vector(9)),
			     Integer.valueOf(30));
		assertEquals(Integer.valueOf(a.get_packed_indicies().get(0)),
			     Integer.valueOf(9));
		assertEquals(Integer.valueOf(a.get_packed_data().get(0)),
			     Integer.valueOf(30));
	}


	/**
	 *  adding and deleting
	 */
	@Test public void AddAndDeletingInPackedVector()
	{

		PackedVector<Integer> a = new PackedVector<Integer>(10);
		a.add_element_at_sparse_vector(1, 10);
		a.add_element_at_sparse_vector(5, 20);
		a.add_element_at_sparse_vector(9, 30);

		a.delete_element_at_sparse_vector(5);
		a.delete_element_at_sparse_vector(9);
		a.add_element_at_sparse_vector(4, 100);
		assertEquals(Integer.valueOf(a.get_packed_data().get(1)),
			     Integer.valueOf(100));
		assertEquals(Integer.valueOf(a.get_packed_data().get(0)),
			     Integer.valueOf(10));
	}
}
