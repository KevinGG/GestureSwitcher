package gesture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import static utility.ObjectOutputUtility.console;
import com.github.sarxos.webcam.Webcam;

final public class WebCamGesture implements Gesture{
	public static final WebCamGesture WEB_CAM_GESTURE = new WebCamGesture();
	
	private static int GT_NONE_ENV_LOWER_BOUND;
	private static int GT_NONE_ENV_UPPER_BOUND;
	private static int GT_NONE_ENV_OFFSET = 90000;
	
	private Webcam webcam;
	
	private WebCamGesture() {
		openWebCam();
	}

	@Override
	public GestureTypes determineGesture() {
		int avgRgbSum = imgAvgRgbSumCal(webcam.getImage());
		if(avgRgbSum >= GT_NONE_ENV_UPPER_BOUND + GT_NONE_ENV_OFFSET || avgRgbSum <= GT_NONE_ENV_LOWER_BOUND - GT_NONE_ENV_OFFSET){
			return GestureTypes.GT_HAND_SWIPE_DOWN;
		}else{
			return GestureTypes.GT_NONE;
		}
	}
	
	final private void openWebCam(){
		webcam = Webcam.getDefault();
		webcam.open();
	}
	
	final public void deconstructor(){
		webcam.close();
	}

	@Override
	public void detectEnvironment() {
		console("Start detecting environment... Please don't do any gestures during this process!");
		int GT_NONE_ENV_DETECTION_COUNT = 0;
		final int initAvgRgbSum = imgAvgRgbSumCal(webcam.getImage());
		GT_NONE_ENV_LOWER_BOUND = initAvgRgbSum;
		GT_NONE_ENV_UPPER_BOUND = initAvgRgbSum;
		++GT_NONE_ENV_DETECTION_COUNT;
		for(;GT_NONE_ENV_DETECTION_COUNT <= 10;++GT_NONE_ENV_DETECTION_COUNT){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				console("Thread Interrupted Exception!");
			}
			final int avgRgbSum = imgAvgRgbSumCal(webcam.getImage());
			if(avgRgbSum > GT_NONE_ENV_UPPER_BOUND){
				GT_NONE_ENV_UPPER_BOUND = avgRgbSum;
				continue;
			}
			if(avgRgbSum < GT_NONE_ENV_LOWER_BOUND - GT_NONE_ENV_OFFSET){
				GT_NONE_ENV_LOWER_BOUND = avgRgbSum;
				continue;
			}
		}
		console("Detection of environment done.");
		console("Lower Bound: " + GT_NONE_ENV_LOWER_BOUND + "Upper Bound: " + GT_NONE_ENV_UPPER_BOUND);
	}
	
	/**
	 * Calculate the average RGB sum value for an image
	 * @param {@link BufferedImage} image
	 * @return integer value of the average RGB 
	 */
	final private int imgAvgRgbSumCal(final BufferedImage image){
		final int imgWidth = image.getWidth();
		final int imgHeight = image.getHeight();
		final int[] imgBuffRgb = image.getRGB(0, 0, imgWidth, imgHeight, null, 0, imgWidth);
		int avgRgbSum = 0;
		for(int i : imgBuffRgb){
			Color c = new Color(i);
			avgRgbSum += (c.getRed()+c.getGreen()+c.getBlue())/3;
		}
		//console(avgRgbSum);
		return avgRgbSum;
	}
}
