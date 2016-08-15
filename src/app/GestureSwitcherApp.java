package app;

import gesture.GestureRecognizer;
import utility.GestureSwitcherUtility;

final public class GestureSwitcherApp {
	public static final GestureSwitcherApp GESTURE_SWITCHER_APP = new GestureSwitcherApp();

	private GestureSwitcherApp() {
		this.appStatus = AppStatus.APP_READY;
	}

	public enum AppStatus {
		APP_READY, APP_RUNNING, APP_STOPPED
	}

	private AppStatus appStatus;
	private GestureRecognizer gestureRecognizer;

	final public void startApp() {
		this.appStatus = AppStatus.APP_RUNNING;
		gestureRecognizer = GestureRecognizer.GESTURE_RECOGNIZER;
	}
	
	final public void gestureSwitch(){
		gestureRecognizer.switchByGestureRecognized();
	}
	
	final public void stopApp() {
		gestureRecognizer.deconstructor();
	}

	final public boolean isAppStopped() {
		return appStatus == AppStatus.APP_STOPPED;
	}
}

class MainApp {
	public static void main(String[] argv) throws InterruptedException {
		//GestureSwitcherApp.GESTURE_SWITCHER_APP.startApp();
		for (; !GestureSwitcherApp.GESTURE_SWITCHER_APP.isAppStopped();) {
			//GestureSwitcherApp.GESTURE_SWITCHER_APP.gestureSwitch();
			GestureSwitcherUtility.showDesktop();
			Thread.sleep(9000);
			//GestureSwitcherApp.GESTURE_SWITCHER_APP.stopApp();
		}
	}
}