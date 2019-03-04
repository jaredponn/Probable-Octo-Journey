package poj.Render;

import poj.Logger.LogLevels;
import poj.Logger.Logger;

import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class ImageLoader
{

	/**
	 * loads an image from the the file system with the string as the
	 * filepath
	 *
	 * @param  str  filepath
	 * @return      BufferedImage of the type
	 */
	public static BufferedImage load(String str)
	{

		File f = new File(str);
		try {
			BufferedImage imagebuf = ImageIO.read(f);

			if (imagebuf.getType()
			    == BufferedImage.TYPE_BYTE_INDEXED) {
				Logger.logMessage(
					"Reducing the TYPE_BYTE_INDEXED image to TYPE_4BYTE_AGBR",
					LogLevels.VERBOSE);

				BufferedImage tmp = new BufferedImage(
					imagebuf.getWidth(),
					imagebuf.getHeight(),
					BufferedImage.TYPE_4BYTE_ABGR_PRE);
				tmp.getGraphics().drawImage(imagebuf, 0, 0,
							    null);

				return tmp;
			}
			return imagebuf;

		} catch (IOException e) {
			Logger.lassert(
				true,
				"Error loading image -- ensure the filepath is correct. The inputted file path should be relative to the project's root directory. This is currently looking for file: "
					+ f.getAbsoluteFile());
			return null;
		}
	}
}
