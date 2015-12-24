package gesture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import static utility.ObjectOutputUtility.console;
import com.github.sarxos.webcam.Webcam;

final public class WebCamGesture implements Gesture{
	public static final WebCamGesture WEB_CAM_GESTURE = new WebCamGesture();
	
	private Webcam webcam;
	
	private WebCamGesture() {
		openWebCam();
	}

	@Override
	public GestureTypes determineGesture() {
		final BufferedImage image = webcam.getImage();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final int[] dataBuffInt = image.getRGB(0, 0, width, height, null, 0, width);
		int AverageRGBSum = 0;
		for(int i : dataBuffInt){
			Color c = new Color(i);
			AverageRGBSum += (c.getRed()+c.getGreen()+c.getBlue())/3;
		}
		console(AverageRGBSum);
		if(AverageRGBSum <= GT_NONE_CRITERIA + GT_NONE_CRITERIA_OFFSET && AverageRGBSum >= GT_NONE_CRITERIA - GT_NONE_CRITERIA_OFFSET){
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
}
