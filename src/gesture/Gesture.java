package gesture;

public interface Gesture {
	/**
	 * A naive enumeration definition for gesture types
	 */
	public enum GestureTypes{
		GT_NONE,
		GT_HAND_SWIPE_DOWN,
		GT_HAND_SWIPE_UP
	}
	
	/**
	 * Detects the environment data from current sensor source.
	 * This should always get implemented and executed first before other gesture operations.
	 * The detected environment information should be excluded from gesture recognition.
	 */
	public void detectEnvironment();
	
	/**
	 * Determines the gesture type from current sensor source
	 * @return enumeration representing a gesture type determined by the implementation of this interface
	 */
	public GestureTypes determineGesture();
}