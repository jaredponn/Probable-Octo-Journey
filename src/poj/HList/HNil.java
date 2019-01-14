package poj.HList;

public class HNil extends HList<HNil>
{

	public HNil()
	{
	}

	public void accept(HVisitor v)
	{
		v.hvisit(this);
	}
}

