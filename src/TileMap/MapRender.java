package TileMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import poj.linear.Matrix;
import poj.linear.MatrixCord;

public class MapRender extends Matrix<Integer>
{
	public ArrayList<Matrix<Integer>> mapLayers =
		new ArrayList<Matrix<Integer>>();
	public int rowsOfTileSet, colsOfTileSet, tileHeight, tileWidth;

	public void addMapLayer(String maplayerLocation)
		throws FileNotFoundException
	{
		Scanner mapReader = new Scanner(new File(maplayerLocation));
		Matrix<Integer> tempLayer;
		ArrayList<Integer> tempIntList = new ArrayList<Integer>();
		int numRows = 0;
		while (mapReader.hasNextLine()) {
			++numRows;
			String line = mapReader.nextLine();
			String tempList[] = line.split(",");
			for (int i = 0; i < tempList.length; ++i) {
				tempIntList.add(Integer.parseInt(tempList[i]));
			}
		}
		tempLayer = new Matrix<Integer>(tempIntList, numRows);
		mapLayers.add(tempLayer);
		mapReader.close();
	}

	public void addTileSet(String tileLocation) throws FileNotFoundException
	{
		Scanner mapReader = new Scanner(new File(tileLocation));
		while (mapReader.hasNextLine()) {
			String line = mapReader.nextLine();
			String tempList[] = line.split("\"");
			if (tempList.length > 1) {
				switch (tempList[1]) {
				case "columns":
					colsOfTileSet = Integer.parseInt(
						tempList[2].substring(
							1, tempList[2].length()
								   - 1));
					break;
				// case "imageheight":
				// break;
				// case "imagewidth":
				// break;
				case "tilecount":
					rowsOfTileSet =
						Integer.parseInt(
							tempList[2].substring(
								1,
								tempList[2].length()
									- 1))
						/ colsOfTileSet;
					break;
				case "tileheight":
					tileHeight = Integer.parseInt(
						tempList[2].substring(
							1, tempList[2].length()
								   - 1));
					break;
				case "tilewidth":
					tileWidth = Integer.parseInt(
						tempList[2].substring(
							1, tempList[2].length()
								   - 1));
					break;
				}
			}
		}
		mapReader.close();
	}

	public void printMapLayer()
	{
		for (int i = 0; i < mapLayers.size(); ++i) {
			for (int j = 0; j < mapLayers.get(i).rows; ++j) {
				System.out.println("row " + j);
				for (int k = 0; k < mapLayers.get(i).cols;
				     ++k) {
					System.out.print(
						mapLayers.get(i).getDataWithIndex(
							mapLayers.get(i).cols
								* j
							+ k)
						+ ", ");
				}
				System.out.println();
			}
		}
	}
}
