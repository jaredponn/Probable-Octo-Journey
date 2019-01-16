package Components;

import poj.Component;

public class Render implements Component
{
	private int m_a;
	private int m_b;

	public Render(int a, int b)
	{
		m_a = a;
		m_b = b;
	}

	public void print()
	{
		System.out.println("Render Component");
		System.out.println(m_a);
		System.out.println(m_b);
		System.out.println("END Render Component");
	}
}
