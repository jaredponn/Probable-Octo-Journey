package poj.Render;

public interface RenderObject {
	public default Class<?> getRenderObjectType()
	{
		return this.getClass();
	}
}
