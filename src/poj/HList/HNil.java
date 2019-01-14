package poj.HList;

public class HNil extends HList<HNil>
{

	public HNil()
	{
	}

	public Class<?> accept(HVisitor v)
	{
		return v.hvisit(this).getClass();
	}
}

