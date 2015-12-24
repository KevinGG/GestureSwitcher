package utility;

import java.awt.event.KeyEvent;
import org.apache.commons.lang3.*;
import static utility.ObjectOutputUtility.console;

public class GestureSwitcherUtility {
	
	private GestureSwitcherUtility(){
		throw new AssertionError();
	}
	
	/**
	 * Show the desktop
	 */
	public static void showDesktop(){
		if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX){
			console("MAC OS");
			RobotUtility.oncePressKeys(KeyEvent.VK_F11);
		}
		if(SystemUtils.IS_OS_WINDOWS){
			console("Windows OS");
			RobotUtility.oncePressKeys(KeyEvent.VK_WINDOWS, KeyEvent.VK_D);
		}
	}
	
	/**
	 * Open the prompt for "Run" in windows OS 
	 */
	public static void promptRunApp() {
		RobotUtility.oncePressKeys(KeyEvent.VK_WINDOWS, KeyEvent.VK_R);
	}

}
	