package Components;
/**
 * GUIGraphicDisplayable.
 * @author Jared Pon
 * March 1, 2019
 * v 0.0
 */

import java.util.ArrayList;
import poj.Render.*;

public interface GUIGraphicDisplayable {

	/**
	 * getRenderObjectGraphics
	 * @return array list of render objects
	 */
	public ArrayList<RenderObject> getRenderObjectGraphics();
}
