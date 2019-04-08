package poj.Render;

/**
 * ImageLoader -- loads an image from the the file system with the string as the
 * filepath.
 * Date: February 20, 2019
 * @version 1.0
 * @author Jared and code from:
 * https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
 * https://stackoverflow.com/questions/196890/java2d-performance-issues
 */

import poj.Logger.LogLevels;
import poj.Logger.Logger;

import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
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
	public static BufferedImage loadUnoptimized(String str)
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
				"Error loading image -- ensure the filepath is correct. The inputted file path should be relative to the project's root directory. This is currently looking for file: "
				+ f.getAbsoluteFile());
			return null;
		}
	}

	public static BufferedImage load(String str)
	{

		return ImageLoader.toCompatibleImage(loadUnoptimized(str));
	}

	// This code was taken from the following website:
	// https://stackoverflow.com/questions/196890/java2d-performance-issues
	public static BufferedImage toCompatibleImage(BufferedImage image)
	{
		GraphicsConfiguration gfxConfig =
			GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();

		if (image.getColorModel().equals(gfxConfig.getColorModel()))
			return image;

		BufferedImage newImage = gfxConfig.createCompatibleImage(
			image.getWidth(), image.getHeight(),
			image.getTransparency());

		Graphics2D g2d = newImage.createGraphics();

		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		return newImage;
	}
}
