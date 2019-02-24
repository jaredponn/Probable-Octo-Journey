package poj;

public class Pair<T, U>
{
	public T fst;
	public U snd;

	public Pair(T t, U u)
	{
		fst = t;
		snd = u;
	}

	public T fst()
	{
		return fst;
	}

	public U snd()
	{
		return snd;
	}
}
