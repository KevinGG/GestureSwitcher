package gesture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import static utility.ObjectOutputUtility.console;
import com.github.sarxos.webcam.Webcam;

public class WebCamGesture implements Gesture {
	public static final WebCamGesture WEB_CAM_GESTURE = new WebCamGesture();

	private static int GT_NONE_ENV_LOWER_BOUND;
	private static int GT_NONE_ENV_UPPER_BOUND;
	private static int GT_HAND_LOWER_BOUND;
	private static int GT_HAND_UPPER_BOUND;
	private static final int GT_NONE_ENV_OFFSET = 90000;
	private static final int GT_HAND_OFFSET = 10000;
	private static final double GT_HAND_RATE_THRESHOLD = 0.5;

	private Webcam webcam;

	private WebCamGesture() {
		openWebCam();
	}

	@Override
	public GestureTypes determineGesture() {
		final BufferedImage image = webcam.getImage();
		if(handPixelRate(image) < GT_HAND_RATE_THRESHOLD){
			return GestureTypes.GT_NONE;
		}
		int avgRgbSum = imgAvgRgbSumCal(image);
		if (!isWithinRange(avgRgbSum, GT_NONE_ENV_LOWER_BOUND, GT_NONE_ENV_OFFSET, GT_NONE_ENV_UPPER_BOUND, GT_NONE_ENV_OFFSET)) {
			return GestureTypes.GT_HAND_SWIPE_DOWN;
		} else {
			return GestureTypes.GT_NONE;
		}
	}

	final private void openWebCam() {
		webcam = Webcam.getDefault();
		webcam.open();
	}

	final public void deconstructor() {
		webcam.close();
	}

	@Override
	public void detectEnvironment() {
		console("Start detecting environment... Please don't do any gestures during this process!");
		int GT_NONE_ENV_DETECTION_COUNT = 0;
		final int initAvgRgbSumEnv = imgAvgRgbSumCal(webcam.getImage());
		GT_NONE_ENV_LOWER_BOUND = initAvgRgbSumEnv;
		GT_NONE_ENV_UPPER_BOUND = initAvgRgbSumEnv;
		++GT_NONE_ENV_DETECTION_COUNT;
		for (; GT_NONE_ENV_DETECTION_COUNT <= 10; ++GT_NONE_ENV_DETECTION_COUNT) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				console("Thread Interrupted Exception!");
			}
			final int avgRgbSum = imgAvgRgbSumCal(webcam.getImage());
			if (avgRgbSum > GT_NONE_ENV_UPPER_BOUND) {
				GT_NONE_ENV_UPPER_BOUND = avgRgbSum;
				continue;
			}
			if (avgRgbSum < GT_NONE_ENV_LOWER_BOUND) {
				GT_NONE_ENV_LOWER_BOUND = avgRgbSum;
				continue;
			}
		}
		console("Please center your palm before the web camera ...");
		GT_NONE_ENV_DETECTION_COUNT = 0;
		final int initAvgRgbSumHand = imgAvgRgbSumCal(webcam.getImage());
		GT_NONE_ENV_LOWER_BOUND = initAvgRgbSumHand;
		GT_NONE_ENV_UPPER_BOUND = initAvgRgbSumHand;
		++GT_NONE_ENV_DETECTION_COUNT;
		for (; GT_NONE_ENV_DETECTION_COUNT <= 10; ++GT_NONE_ENV_DETECTION_COUNT) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				console("Thread Interrupted Exception!");
			}
			final int avgRgbSum = imgAvgRgbSumCal(webcam.getImage());
			if (avgRgbSum > GT_HAND_UPPER_BOUND) {
				GT_HAND_UPPER_BOUND = avgRgbSum;
				continue;
			}
			if (avgRgbSum < GT_HAND_LOWER_BOUND) {
				GT_HAND_LOWER_BOUND = avgRgbSum;
				continue;
			}
		}
		console("Detection of environment done.");
		console("Lower Bound: " + GT_NONE_ENV_LOWER_BOUND + "Upper Bound: " + GT_NONE_ENV_UPPER_BOUND);
	}

	/**
	 * Calculate the average RGB sum value for an image
	 * 
	 * @param {@link
	 * 			BufferedImage} image
	 * @return integer value of the average RGB
	 */
	final private int imgAvgRgbSumCal(final BufferedImage image) {
		final int imgWidth = image.getWidth();
		final int imgHeight = image.getHeight();
		final int[] imgBuffRgb = image.getRGB(0, 0, imgWidth, imgHeight, null, 0, imgWidth);
		int avgRgbSum = 0;
		for (int i : imgBuffRgb) {
			final Color c = new Color(i);
			avgRgbSum += avgRgb(c);
		}
		// console(avgRgbSum);
		return avgRgbSum;
	}

	/**
	 * Calculate the rate of hand pixels among all pixels in the current image.
	 * @param {@link {@link BufferedImage} image
	 * @return Rate of hand pixels among all pixels in current image. 
	 */
	final private double handPixelRate(final BufferedImage image){
		final int imgWidth = image.getWidth();
		final int imgHeight = image.getHeight();
		final int[] imgBuffRgb = image.getRGB(0, 0, imgWidth, imgHeight, null, 0, imgWidth);
		int handCount = 0;
		for(int i : imgBuffRgb){
			final Color c = new Color(i);
			if(isWithinRange(avgRgb(c), GT_HAND_LOWER_BOUND, GT_HAND_OFFSET, GT_HAND_UPPER_BOUND, GT_HAND_OFFSET)){
				++handCount;
			}
		}
		return handCount/(imgWidth*imgHeight);
	}

	final private int avgRgb(Color color) {
		return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
	}
	
	final private boolean isWithinRange(int testValue, int lowerBound, int lowerBoundOffset, int upperBound, int upperBoundOffset){
		if(testValue > lowerBound - lowerBoundOffset && testValue < upperBound + upperBoundOffset){
			return true;
		}
		return false;
	}
}
