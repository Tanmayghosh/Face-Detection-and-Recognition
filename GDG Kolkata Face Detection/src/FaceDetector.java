import java.io.File;
import java.io.FilenameFilter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {

	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("\nRunning FaceDetector");

		CascadeClassifier faceDetector = new CascadeClassifier(
				"src/Classifiers/haarcascade_frontalface_alt.xml");

		String path = "src/Pictures";

		

		File root = new File("src/Pictures");
		FilenameFilter pngFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".png");
			}
		};

		File[] imageFiles = root.listFiles(pngFilter);

		int i = 0;
		for (File image1 : imageFiles) {

			i++;

			System.out.print(image1.getAbsolutePath());
			// System.out.print(FaceDetector.class.getResource(image1.getAbsolutePath()));
			Mat image = Highgui.imread(image1.getAbsolutePath());

			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(image, faceDetections);

			System.out.println(String.format("Detected %s faces",
					faceDetections.toArray().length));
			int j = 0;
			for (Rect rect : faceDetections.toArray()) {
				Core.rectangle(image, new Point(rect.x, rect.y), new Point(
						rect.x + rect.width, rect.y + rect.height), new Scalar(
						0, 255, 0));

				// Mat imgMat =
				// Highgui.imread(FaceDetector.class.getResource("Pics/ouput"+i+".png").getPath().substring(1),
				// Highgui.CV_LOAD_IMAGE_GRAYSCALE);

				// image = new Mat(imgMat, rect);
				image = new Mat(image, rect);

				Mat resizeimage = new Mat();
				Size sz = new Size(300, 300);
				Imgproc.resize(image, resizeimage, sz);

				String filename = "Faces/" + i + j + "-" + image1.getName();
				System.out.println(String.format("Writing %s", filename));
				Highgui.imwrite(filename, resizeimage);

			}

		}
	}
}