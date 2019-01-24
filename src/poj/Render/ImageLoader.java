package poj.Render;

import poj.Logger.LogLevels;
import poj.Logger.Logger;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ImageLoader
{

	public static BufferedImage load(String str)
	{

		File f = new File(str);
		try {
			BufferedImage tmp = ImageIO.read(f);
			return tmp;

		} catch (IOException e) {
			Logger.lassert(
				true,
				"Error loading image -- ensure the filepath is correct. The inputted file path should be relative to the project's root directory. This is currently looking for file: "
					+ f.getAbsoluteFile());
			return null;
		}
	}
}
