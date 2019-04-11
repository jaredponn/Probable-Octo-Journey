package Components;

public class DoubleIntComponent extends SingleIntComponent
{
	int focus2;

	public DoubleIntComponent()
	{
	}

	public DoubleIntComponent(int a, int b)
	{
		super(a);
		focus2 = b;
	}

	public void addFocus2(int n)
	{
		focus2 += n;
	}

	public int getFocus2()
	{
		return focus2;
	}

	public void setFocus2(int n)
	{
		focus2 = n;
	}

	public void print()
	{
		System.out.println("DoubleIntComponent: focus1: " + focus1
				   + ", focus2: " + focus2);
	}
}
