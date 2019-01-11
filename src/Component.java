import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


class Component<T>
{

	public ArrayList<Integer> sparse_vector;
	public ArrayList<Integer> packed_indices;
	public ArrayList<T> packed_data;
	public int next_free_index;

	public Component(int capacity)
	{
		// TODO only set the capacity, NOT the size!!??????
		sparse_vector = new ArrayList<Integer>(
			Collections.nCopies(capacity, -1));
		packed_indices = new ArrayList<Integer>(capacity);
		packed_data = new ArrayList<T>(capacity);
		next_free_index = 0;
	}

	public void add_element_at_sparse_vector(final int index, final T val)
	{
		if (index >= sparse_vector.size()) {
			System.out.println(
				"MAJOR ERROR IN PACKEDVECTOR. Too many entities in this engine! increase buffer size.");
			return;
		} else if (sparse_vector.get(index) == -1) {
			System.out.println(
				"MAJOR ERROR IN PACKEDVECTOR. You are adding an entity at this index, but an entity already exist at this index.");
			return;
		}
		sparse_vector.set(index, next_free_index);
		packed_indices.add(index);
		packed_data.add(val);

		++next_free_index;
	}

	public void delete_element_at_sparse_vector(final int index)
	{
		if (sparse_vector.get(index) == -1) {
			System.out.println(
				"MINOR error in packedvector. You are deleting an entity that had already been deleted. The program should continue to work normally.");
			return;
		}
		int toBeDeletedIndexInPackedIndicies = sparse_vector.get(index);
		int lastElementInPackedIndicies =
			packed_indices.get(packed_data.size() - 1);

		Collections.swap(
			sparse_vector, index,
			sparse_vector.get(lastElementInPackedIndicies));
		Collections.swap(packed_indices,
				 toBeDeletedIndexInPackedIndicies,
				 packed_indices.size() - 1);
		Collections.swap(packed_data, toBeDeletedIndexInPackedIndicies,
				 packed_data.size() - 1);

		packed_data.remove(packed_data.size() - 1);
		packed_indices.remove(packed_indices.size() - 1);

		// TODO the value at sparsevector line up with the index of the
		// index??
		sparse_vector.set(index, -1);
		// OR sparse_vector.set(toBeDeleted, -1); ??????????
		--next_free_index;
	}
}
