import poj.Component;
import Components.Render;


public class Main
{

	public static final void main(String[] args)
	{
		Component<Integer> a = new Component<Integer>(1);
		Render b = new Render();

		System.out.println(b.getClass());
		System.out.println(a.getClass());
	}
}
