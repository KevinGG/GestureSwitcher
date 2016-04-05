package gesture;

import static utility.ObjectOutputUtility.console;
import com.github.sarxos.webcam.*;
/**
 * This version of implementation is based on hand detection only,
 * ignoring all information from the outside world.
 * 
 * The detectEnvironment method would focus on detecting hand.
 * 
 * @author NK044575
 *
 */
public class WebCamGestureVerHandDetection implements Gesture{
	public static final WebCamGestureVerHandDetection WEB_CAM_GESTURE = new WebCamGestureVerHandDetection();
	
	private WebCamGestureVerHandDetection() {
		
		
	}
	
	@Override
	public void detectEnvironment() {
		console("Please put hand in front of the camera.");
		
	}

	@Override
	public GestureTypes determineGesture() {
		return GestureTypes.GT_NONE;
	}
	
}
