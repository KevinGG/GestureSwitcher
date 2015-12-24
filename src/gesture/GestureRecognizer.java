package gesture;

import utility.GestureSwitcherUtility;

final public class GestureRecognizer {
	public static final GestureRecognizer GESTURE_RECOGNIZER = new GestureRecognizer();

	private Gesture gesture;

	private GestureRecognizer() {
		gesture = WebCamGesture.WEB_CAM_GESTURE;
	}

	final public void switchByGestureRecognized() {
		switch (gesture.determineGesture()) {
		case GT_NONE:
			break;
		case GT_HAND_SWIPE_DOWN:
			GestureSwitcherUtility.showDesktop();
			break;
		case GT_HAND_SWIPE_UP:
			GestureSwitcherUtility.promptRunApp();
			break;
		default:
			break;
		}
	}
	
	final public void deconstructor(){
		((WebCamGesture)gesture).deconstructor();
	}
}
