import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.opencv_contrib.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class testrecognizer {

	public static void main(String[] args) {

		System.loadLibrary("opencv_java249");

		VideoCapture camera = new VideoCapture(0);

		if (!camera.isOpened()) {
			System.out.println("Error");
		} else {
			final Mat frame1 = new Mat();
			if (camera.read(frame1)) {

				Highgui.imwrite("verify", frame1);
			}

		}

		Mat frame = new Mat();

		camera.read(frame);
		System.out.println("Frame Obtained");
		System.out.println("Captured Frame Width " + frame.width());
		// Highgui.imwrite("verify.png", frame);

		// Highgui.imwrite("src/verify.png", frame);

		Mat image = Highgui.imread("src/TESTIMAGE.png");

		CascadeClassifier faceDetector = new CascadeClassifier(
				"src/Classifiers/haarcascade_frontalface_alt.xml");

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces",
				faceDetections.toArray().length));

		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
					+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));

			image = new Mat(image, rect);

			Mat resizeimage = new Mat();
			Size sz = new Size(300, 300);
			Imgproc.resize(image, resizeimage, sz);

			String filename = "Verify_f.png";
			System.out.println(String.format("Writing %s", filename));
			Highgui.imwrite(filename, resizeimage);
		}

	}
}