package poj.HList;

public class HListUtil
{
	public static <T, Ts extends HList<Ts>, R, F extends Function<T, R>>
		R hmap(HCons<T, Ts> list)
	{
		F.f();
	}
}
