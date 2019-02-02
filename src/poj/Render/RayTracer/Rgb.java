package poj.Render.RayTracer;

import poj.Logger.*;
import poj.linear.*;

public class Rgb
{
	public static int MAX_VALUE = 255;
	private int[] rgb;

	public Rgb()
	{
		this.rgb = new int[3];
	}
	public Rgb(int r, int g, int b)
	{
		this.rgb = new int[3];
		setR(r);
		setG(g);
		setB(b);
	}

	public Rgb(Vector3f v)
	{
		this.rgb = new int[3];
		setR((int)(((float)Rgb.MAX_VALUE + 0.99) * v.getX()));
		setG((int)(((float)Rgb.MAX_VALUE) + 0.99 * v.getY()));
		setB((int)(((float)Rgb.MAX_VALUE) + 0.99 * v.getZ()));
	}

	public int getR()
	{
		return this.rgb[0];
	}
	public int getG()
	{
		return this.rgb[1];
	}
	public int getB()
	{
		return this.rgb[2];
	}

	public void setR(int n)
	{
		setRgbVal(0, n);
	}
	public void setG(int n)
	{
		setRgbVal(1, n);
	}
	public void setB(int n)
	{
		setRgbVal(2, n);
	}

	public void addToR(int n)
	{
		int tmp = getR() + n;
		setR(tmp);
	}

	public void addToG(int n)
	{
		int tmp = getG() + n;
		setG(tmp);
	}

	public void addToB(int n)
	{
		int tmp = getB() + n;
		setB(tmp);
	}

	public void addToAll(int n)
	{
		addToR(n);
		addToG(n);
		addToB(n);
	}

	public String toString()
	{
		return rgb[0] + " " + rgb[1] + " " + rgb[2];
	}

	private void setRgbVal(int rgb, int n)
	{
		if (n < 0) {
			Logger.logMessage("setting" + intToRgb(rgb)
						  + " to be less than 0 in Rgb",
					  LogLevels.VERBOSE);
			this.rgb[rgb] = 0;
			return;
		}

		if (n > Rgb.MAX_VALUE) {
			Logger.logMessage("setting" + intToRgb(rgb)
						  + " to be less than 0 in Rgb",
					  LogLevels.VERBOSE);
			this.rgb[rgb] = Rgb.MAX_VALUE;
			return;
		}

		this.rgb[rgb] = n;
	}

	private String intToRgb(int n)
	{
		switch (n) {
		case 0:
			return "r";
		case 1:
			return "g";
		case 2:
			return "b";
		default:
			return "ERROR IN COLOR";
		}
	}
}

