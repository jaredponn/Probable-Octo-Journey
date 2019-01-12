package poj.HList;

public class HCons<U, Us extends HList<Us>> extends HList<HCons<U, Us>>
{
	private U head;
	private Us tail;

	public HCons(U n, Us ns)
	{
		head = n;
		tail = ns;
	}
	final public U head()
	{
		return head;
	}
	final public Us tail()
	{
		return tail;
	}
}
