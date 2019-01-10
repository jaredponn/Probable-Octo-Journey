import java.util.ArrayList;


class Component<T>
{

	public ArrayList<T> sparse_vector;
	public ArrayList<T> packed_indices;
	public ArrayList<T> packed_data;

	public Component(int capacity)
	{
		sparse_vector = new ArrayList<T>(capacity);
		packed_indices = new ArrayList<T>(capacity);
		packed_data = new ArrayList<T>(capacity);
	}
}

