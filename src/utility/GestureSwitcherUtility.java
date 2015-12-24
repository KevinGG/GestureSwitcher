package utility;

import java.awt.event.KeyEvent;

public class GestureSwitcherUtility {
	
	private GestureSwitcherUtility(){
		throw new AssertionError();
	}
	
	/**
	 * Show the desktop
	 */
	public static void showDesktop(){
		RobotUtility.oncePressKeys(KeyEvent.VK_WINDOWS, KeyEvent.VK_D);
	}
	
	/**
	 * Open the prompt for "Run" in windows OS 
	 */
	public static void promptRunApp() {
		RobotUtility.oncePressKeys(KeyEvent.VK_WINDOWS, KeyEvent.VK_R);
	}

}
	