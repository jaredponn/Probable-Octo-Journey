package Components;

import poj.Component.Component;

public class SingleIntComponent implements Component
{

	int focus1;

	public SingleIntComponent()
	{
	}

	public SingleIntComponent(int n)
	{
		focus1 = n;
	}

	public void addFocus1(int n)
	{
		focus1 += n;
	}

	public int getFocus1()
	{
		return focus1;
	}

	public void setFocus1(int n)
	{
		focus1 = n;
	}

	public void print()
	{
		System.out.println("SingleIntComponent: focus1: " + focus1);
	}
}
