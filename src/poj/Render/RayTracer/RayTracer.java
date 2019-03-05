package poj.Render.RayTracer;

/**
 * RayTracer for the raytracer -- LONG DEPRECATED MORE OR A LESS A TOY THAT WE
 * STARTED WITH Date: February 10, 2019
 * @author
 * http://www.realtimerendering.com/raytracing/Ray%20Tracing%20in%20a%20Weekend.pdf
 * @version      1.0
 */

import poj.linear.*;
import poj.Logger.*;

public class RayTracer
{
	private Vector3f horizonColor0;
	private Vector3f horizonColor1;

	// defualt beautfiul blue ti white
	public RayTracer()
	{
		this.horizonColor0 = new Vector3f(1.0f, 1.0f, 1.0f);
		this.horizonColor1 = new Vector3f(0.5f, 0.7f, 1.0f);
	}

	public RayTracer(Vector3f h0, Vector3f h1)
	{
		if (Vector3f.abs(h0) > 1) {

		} else {
		}

		if (Vector3f.abs(h1) > 1) {
		}
		this.horizonColor0 = h0;
		this.horizonColor1 = h1;
	}

	public RayTracer(Rgb h0, Rgb h1)
	{
		this.horizonColor0 =
			new Vector3f(h0.getR() / (float)Rgb.MAX_VALUE,
				     h0.getG() / (float)Rgb.MAX_VALUE,
				     h0.getB() / (float)Rgb.MAX_VALUE);
		this.horizonColor1 =
			new Vector3f(h1.getR() / (float)Rgb.MAX_VALUE,
				     h1.getG() / (float)Rgb.MAX_VALUE,
				     h1.getB() / (float)Rgb.MAX_VALUE);
	}


	// lerp function -- for the horizons. t for direction of the y scale,
	// and a and be are the horizon colors lerp(t, a, b) = (1-t)*a + t*b
	public Rgb getColor(final Ray r)
	{
		Vector3f unitd = Vector3f.getUnitVectorByCopy(r.getDirection());
		float t = 0.5f * (unitd.getY() + 1.0f);

		return new Rgb(this.horizonColor0.pureElemMul(1.0f - t).pureAdd(
			this.horizonColor1.pureElemMul(t)));
	}

	public void setHorizon0(Vector3f v)
	{
	}
}
