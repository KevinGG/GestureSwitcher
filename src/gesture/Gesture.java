package gesture;

public interface Gesture {
	public enum GestureTypes{
		GT_NONE,
		GT_HAND_SWIPE_DOWN,
		GT_HAND_SWIPE_UP
	}
	

	public static int GT_NONE_ENVIRONMENT;
	
	public static int GT_NONE_OFFSET;
	
	public GestureTypes determineGesture();
	
	public void detectEnvironment();
}
