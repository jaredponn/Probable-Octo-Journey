package poj.HList;

public class HTypeVisitor implements HVisitor
{

	public <T, Ts extends HList<Ts>> HCons<T, Ts> hvisit(HCons<T, Ts> n)
	{
		return n;
	}

	public HNil hvisit(HNil n)
	{
		return n;
	}
}
