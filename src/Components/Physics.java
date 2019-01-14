package Components;

import poj.Component;

public class Physics extends Component
{
	int a;

	public Physics(int a)
	{
		this.a = a;
	}

	public void print()
	{
		System.out.println("Physics Component");
		System.out.println(a);
		System.out.println("END Physics Component");
	}
}
