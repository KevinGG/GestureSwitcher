package utility;

import static utility.ObjectOutputUtility.console;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Collection;
import java.util.HashSet;

public class RobotUtility {
	private RobotUtility() {
		throw new AssertionError();
	}

	private static final Robot robot = getRobotInstance();
	private static Collection<Integer> holdingKeys = getHoldingKeys();

	private static Robot getRobotInstance() {
		try {
			final Robot robot = new Robot();
			return robot;
		} catch (AWTException e) {
			console("Failed to create robot instance, returning null object");
			return null;
		}
	}
	
	private static Collection<Integer> getHoldingKeys() {
		return new HashSet<Integer>();
	}

	private static boolean validEnvironment() {
		if (robot == null)
			return false;
		// other validations here ...
		return true;
	}
	
	private static void consoleLogInvalidKeyWithHoldingKeysCleared(String processName, int position, String varargName, int invalidKeyCode){
		console("When "+ processName + ", invalid key found, position: "+varargName+"[" + position + "] with key code value: " + invalidKeyCode);
		// if invalid key is found, the program would be interrupted but still running, so need to release all holding keys
		releaseHoldingKeys();
	}
	
	public static void holdKeys(int...keyEventList){
		if(!validEnvironment()) return;
		int i = 0;
		try{
			for(; i<keyEventList.length; ++i){
				robot.keyPress(keyEventList[i]);
				if(!holdingKeys.contains(keyEventList[i])){
					holdingKeys.add(keyEventList[i]);
				}
			}
		}catch(IllegalArgumentException e){
			consoleLogInvalidKeyWithHoldingKeysCleared("holding keys", i, "keyEventList", keyEventList[i]);
		}
	}
	
	public static void releaseHoldingKeys(){
		for(Integer key : holdingKeys){
			robot.keyRelease(key.intValue());
		}
		holdingKeys.clear();
	}

	public static void oncePressKeys(int... keyEventList) {
		if (!validEnvironment())
			return;
		//iterator, also used for debug message output
		int i = 0;
		try{
			// multiple-key presses
			for (; i < keyEventList.length; ++i) {
				robot.keyPress(keyEventList[i]);
			}
			// multiple-key releases
			for (i = 0; i < keyEventList.length; ++i) {
				robot.keyRelease(keyEventList[i]);
			}
		}catch(IllegalArgumentException e){
			consoleLogInvalidKeyWithHoldingKeysCleared("pressing keys", i, "keyEventList", keyEventList[i]);
		}
	}
	
	public static void repeatPressKeys(int rate, int... keyEventList){
		for(int i = 0; i < rate; ++i){
			oncePressKeys(keyEventList);
		}
	}
}
