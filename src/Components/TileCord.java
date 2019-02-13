package Components;

import poj.Component.Component;
public class TileCord implements Component
{
	public int row, col, modValue;
	public TileCord(int row, int col)
	{
		this.row = row;
		this.col = col;
		// this.modValue = modValue;
	}
	public void print()
	{
		System.out.println("TileCord Component");
		System.out.println("row = " + row + " ,col = " + col);
		System.out.println("END Render Component");
	}
}
