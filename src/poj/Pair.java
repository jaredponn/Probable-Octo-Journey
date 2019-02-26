package poj;

public class Pair<T, U>
{
	public T fst;
	public U snd;
	/**
	 * Constructor
	 * @param  t fst
	 * @param  u snd
	 */
	public Pair(T t, U u)
	{
		fst = t;
		snd = u;
	}

	/**
	 * returns the fst part of the pair
	 * @return  fst
	 */
	public T fst()
	{
		return fst;
	}

	/**
	 * returns the snd part of the pair
	 * @return  snd
	 */
	public U snd()
	{
		return snd;
	}

	public void setPair(T newT, U newU)
	{
		this.fst = newT;
		this.snd = newU;
	}
}
